package cn.com.jcgroup.service.service;

import cn.com.jcgroup.service.constant.ServiceIdentifier;
import cn.com.jcgroup.service.domain.*;
import cn.com.jcgroup.service.enums.AgencyEnum;
import cn.com.jcgroup.service.enums.CompanyEnum;
import cn.com.jcgroup.service.enums.ProjectLevelEnum;
import cn.com.jcgroup.service.enums.ProjectStatusEnum;
import cn.com.jcgroup.service.repositories.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目相关服务
 *
 * @author LiuYong on 17/6/5 上午9:51.
 */
@Service
public class ProjectService {

    private static final Logger LOG = LoggerFactory.getLogger(ProjectService.class);

    @Autowired
    private PbProjectRepository pbProjectRepository;
    @Autowired
    private PbFileReponsitory pbFileReponsitory;
    @Autowired
    private PbEncourageRepository pbEncourageRepository;
    @Autowired
    private PbEncourageFileRepository pbEncourageFileRepository;
    @Autowired
    private PbFileFieldReponsitory pbFileFieldReponsitory;
    @Autowired
    private PbAssetCompositionReponsitory pbAssetCompositionReponsitory;
    @Autowired
    private PbProCompRelationRepository pbProCompRelationRepository;
    @Autowired
    private PbAgencyRepository pbAgencyRepository;
    @Autowired
    private PbFinanceAgencyRepository pbFinanceAgencyRepository;
    @Autowired
    private PbPrivateFundRepository pbPrivateFundRepository;

    /**
     * 修改激励情况
     */
    @Transactional
    public void updateEncourageMoneyByProCode(String proCode, String level, long encourageMoney) {
        try {
            pbProjectRepository.updateEncourageMoneyByProCode(proCode, level, encourageMoney);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }
    
    /**
     * 查询激励信息
     * 
     * @author LiuYong  
     */
    public JSONObject queryEncourageByProCode(String proCode){
        if(StringUtils.isBlank(proCode)){
            return null;
        }
        List<Object[]> objects = pbProjectRepository.findEncourageMoneyByProCode(proCode);
        JSONObject jsonObject = null;
        if(objects != null && !objects.isEmpty()){
            jsonObject = new JSONObject();
            jsonObject.put("encourageMoney",objects.get(0)[0]);
            jsonObject.put("levels",objects.get(0)[1]);
        }
        return jsonObject;
    }

    /**
     * 根据合营公司编码获取项目编码
     */
    public JSONArray queryProCodeByCompCode(String companyCode) {
        List<String> proCodeList = pbProCompRelationRepository.findAllByCompanyCode(companyCode);
        JSONArray proCodeArray = new JSONArray();
        int length = proCodeList.size();
        for (int i = 0; i < length; i++) {
            proCodeArray.add(proCodeList.get(i));
        }
        return proCodeArray;
    }

    /**
     * 项目列表
     */
    public JSONArray listProject(int page) {
        JSONArray jsonArray = new JSONArray();
        PageRequest pagebale = new PageRequest(page - 1, 100, new Sort(Sort.Direction.DESC, "createTime"));
        List<PbProject> projectList = pbProjectRepository.findAllInPage(pagebale);
        if (projectList != null && !(projectList.isEmpty())) {
            for (PbProject project : projectList) {
                JSONObject jsonObject = new JSONObject();
                if (project.getIsShow().equals("1")) {
                    jsonObject.put("isShow", true);
                } else {
                    jsonObject.put("isShow", false);
                }
                jsonObject.put("proNameAbbr", project.getProNameAbbr());
                jsonObject.put("proCode", project.getProCode());
                jsonObject.put("proName", project.getProName());
                jsonObject.put("createTime", project.getCreateTime());
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

    /**
     * 项目修改
     */
    @Transactional
    public int updateProject(String proAbbr, String proCode, String proName, boolean isShow) {
        Date updateTime = new Date();
        String showFlag;
        if (isShow) {
            showFlag = "1";
        } else {
            showFlag = "0";
        }
        return pbProjectRepository.updateProjectByProCode(proAbbr, proCode, proName, updateTime, showFlag);
    }

    /**
     * 新建项目
     */
    public String createProject(String proAbbr, String proCode, String proName, boolean isShow) {
        try {
            PbProject project = new PbProject();
            project.setId(pbProjectRepository.findSeqId());
            project.setProNameAbbr(proAbbr);
            project.setProCode(proCode);
            project.setProName(proName);
            project.setCreateTime(new Date());
            if (isShow) {
                project.setIsShow("1");
            } else {
                project.setIsShow("0");
            }
            project.setIsCore("0");
            PbProject resProject = pbProjectRepository.save(project);
            if (resProject != null) {
                return resProject.getProCode();
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return "craete_error";
    }



    /**
     * 项目概况-修改
     */
    @Transactional
    public void updateProjectInfo(JSONObject jsonObject) {
        if (jsonObject != null) {
            PbProject project = JSONObject.parseObject(jsonObject.toJSONString(), PbProject.class);
            project.setUpdateTime(new Date());
            pbProjectRepository.updateProjectInfoByProCode(project);
        }
    }

    /**
     * 根据项目编码获取激励信息
     */
    public JSONArray findEncouragesByProCode(String projectCode) {
        JSONArray jsonArray = new JSONArray();
        List<PbEncourage> info = pbEncourageRepository.findByProjectCode(projectCode,new Sort(Sort.Direction.DESC,"preDate"));
        if (info != null && !info.isEmpty()) {
            for (PbEncourage encourage : info) {
                jsonArray.add(JSON.toJSON(encourage));
            }
        }
        return jsonArray;
    }

    /**
     * 项目基本信息-显示
     */
    public JSONObject findProjectBasicInfo(String projectCode) {
        JSONObject jsonObject = new JSONObject();
        PbProject project = pbProjectRepository.findByProCode(projectCode);
        if (project != null) {
            jsonObject = (JSONObject) JSON.toJSON(project);
            return jsonObject;
        }
        return jsonObject;
    }

    /**
     * 获取banner图片信息
     */
    public JSONArray findBannerListByProjectCode(String projectCode) {
        JSONArray jsonArray = new JSONArray();
        List<String> bannerList = pbProjectRepository.findBannerImgsByProCode(projectCode);
        if (bannerList != null && !(bannerList.isEmpty())) {
            for (String banner : bannerList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", "banner图");
                JSONArray bannerArray = JSON.parseArray(banner);
                jsonObject.put("url", bannerArray);
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

    /**
     * 获取激励文件链接信息列表
     */
    public JSONArray queryFilesByProjectCode(String projectCode) {
        JSONArray jsonArray = null;
        List<PbEncourageFile> fileList = pbEncourageFileRepository.findByProCode(projectCode);
        if (fileList != null && !(fileList.isEmpty())) {
            jsonArray = new JSONArray();
            for (PbEncourageFile file : fileList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("fileName", file.getFileName());
                jsonObject.put("filePath", file.getFilePath());
                jsonObject.put("fileCode", file.getFileCode());
                jsonObject.put("fileExt", file.getFileExt());
                jsonObject.put("createTime", file.getCreateTime());
                jsonObject.put("proCode", file.getProCode());
                jsonObject.put("updateTime", file.getUpdateTime());
                jsonObject.put("isShow", file.getIsShow());
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

    /**
     * 根据项目所在省份分组进行项目个数统计
     *
     * @author LiuYong
     */
    public JSONArray queryProjectNumberByProvinceGroup() {
        JSONArray jsonArray = null;
        List<Object[]> list = pbProjectRepository.countProjectNumberByProvinceGroup();
        if (list != null && !list.isEmpty()) {
            jsonArray = new JSONArray();
            for (Object[] array : list) {
                JSONObject jsonObject = new JSONObject();
                LOG.debug("[项目服务]统计数据如下:{}", JSON.toJSONString(array));
                jsonObject.put("number", array[0]);
                jsonObject.put("affiProvince", array[1]);
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

    /**
     * 根据项目状态分组进行项目规模统计
     *
     * @author LiuYong
     */
    public JSONArray queryProjectScaleByStatusGroup() {
        JSONArray jsonArray = null;
        List<Object[]> list = pbProjectRepository.countProjectScaleByStatusGroup();
        if (list != null && !list.isEmpty()) {
            jsonArray = new JSONArray();
            JSONObject jsonObject;
            for (Object[] array : list) {
                jsonObject = new JSONObject();
                LOG.debug("[项目服务]统计项目规模如下:{}", JSON.toJSONString(array));
                String status = String.valueOf(array[0]);
                Long tempSacle = (Long) array[1];
                ProjectStatusEnum statusEnum = ProjectStatusEnum.convertToEnum(status);
                if (statusEnum == null) {
                    LOG.error("[[项目服务]项目状态无匹配类型,status={}", status);
                    continue;
                }
                jsonObject.put("projectStatus", statusEnum);
                jsonObject.put("totalScale", tempSacle);
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

    /**
     * 根据省份查询项目简略信息
     *
     * @author LiuYong
     */
    public JSONArray queryProjectInfoByByProvince(String affiProvince) {
        JSONArray jsonArray = null;
        if (StringUtils.isBlank(affiProvince)) {
            return jsonArray;
        }
        List<Object[]> info = pbProjectRepository.findProjectByProvince(affiProvince);
        if (info != null && !info.isEmpty()) {
            jsonArray = new JSONArray();
            for (Object[] array : info) {
                JSONObject jsonObject = new JSONObject();
                LOG.debug("[项目服务]项目简略信息如下:{}", JSON.toJSONString(array));
                jsonObject.put("proCode", array[0]);
                Long scale = (Long) array[1];
                jsonObject.put("scale", scale);
                jsonObject.put("proName", array[2]);
                jsonObject.put("location", array[3]);
                jsonObject.put("projectStatus", array[4]);
                jsonObject.put("department", array[5]);
                jsonObject.put("abbr", array[6]);
                jsonObject.put("proNameAbbr", array[7]);
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

    /**
     * 根据公司、项目类别分组进行规模、项目个数统计
     *
     * @author LiuYong
     */
    public JSONArray queryInfoByDepartmentAndStatusGroup() {
        JSONArray jsonArray = null;
        List<Object[]> list = pbProjectRepository.countProjectInfoByCompanyAndStatusGroup();
        if (list != null && !list.isEmpty()) {
            JSONObject jsonObject;
            jsonArray = new JSONArray();
            for (Object[] array : list) {
                jsonObject = new JSONObject();
                LOG.debug("[项目服务]项目规模、项目个数统计信息如下:{}", JSON.toJSONString(array));
                CompanyEnum companyEnum = CompanyEnum.convertToEnum(String.valueOf(array[0]));
                if (companyEnum == null) {
                    LOG.error("[项目服务]公司类别转换错误，company={}", String.valueOf(array[0]));
                    continue;
                }
                ProjectStatusEnum projectStatusEnum = ProjectStatusEnum.convertToEnum(String.valueOf(array[1]));
                if (projectStatusEnum == null) {
                    LOG.error("[项目服务]项目状态转换错误，company={}", String.valueOf(array[1]));
                    continue;
                }
                Long tempScale = (Long) array[2];
                Long number = (Long) array[3];
                jsonObject.put("department", companyEnum);
                jsonObject.put("projectStatus", projectStatusEnum);
                jsonObject.put("sumScale", tempScale);
                jsonObject.put("totalNumbers", number);
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

    /**
     * 根据项目状态分组进行项目个数统计
     *
     * @author LiuYong
     */
    public JSONArray queryProjectNumberByStatusGroup() {
        JSONArray jsonArray = null;
        List<Object[]> list = pbProjectRepository.countProjectNumberByStatusGroup();
        if (list != null && !list.isEmpty()) {
            jsonArray = new JSONArray();
            for (Object[] array : list) {
                JSONObject jsonObject = new JSONObject();
                LOG.debug("[项目服务]根据项目状态分组进行项目个数统计如下:{}", JSON.toJSONString(array));
                String status = String.valueOf(array[0]);
                ProjectStatusEnum projectStatusEnum = ProjectStatusEnum.convertToEnum(status);
                if (projectStatusEnum == null) {
                    LOG.error("[项目服务]项目状态转换错误,status={}", status);
                    continue;
                }
                jsonObject.put("projectStatus", projectStatusEnum);
                jsonObject.put("number", array[1]);
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

    /**
     * 根据文件级别进行分组统计激励文件个数
     *
     * @author LiuYong
     */
    public JSONArray queryEncourageNumberByLevelGroup() {
        JSONArray jsonArray = null;
        List<Object[]> list = pbProjectRepository.countEncourageNumberByLevelGroup();
        if (list != null && !list.isEmpty()) {
            jsonArray = new JSONArray();
            JSONObject jsonObject;
            for (Object[] array : list) {
                jsonObject = new JSONObject();
                LOG.debug("[项目服务]根据文件级别进行分组统计激励文件个数:{}", JSON.toJSONString(array));
                ProjectLevelEnum projectLevelEnum = ProjectLevelEnum.convertToEnum(String.valueOf(array[0]));
                if (projectLevelEnum == null) {
                    LOG.error("[项目服务]项目级别转换错误,level={}", String.valueOf(array[0]));
                }
                Long number = (Long) array[1];
                jsonObject.put("level", projectLevelEnum);
                jsonObject.put("number", number);
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

    /**
     * 根据公司、评级分组进行项目个数统计
     *
     * @author LiuYong
     */
    public JSONArray queryEncourageNumberByCompanyAndLevelGroup() {
        JSONArray jsonArray = null;
        List<Object[]> list = pbProjectRepository.countEncourageNumberByCompanyAndLevelGroup();
        if (list != null && !list.isEmpty()) {
            JSONObject jsonObject;
            jsonArray = new JSONArray();
            for (Object[] array : list) {
                jsonObject = new JSONObject();
                LOG.debug("[项目服务]根据公司、评级分组进行项目个数统计信息如下:{}", JSON.toJSONString(array));
                CompanyEnum companyEnum = CompanyEnum.convertToEnum(String.valueOf(array[0]));
                if (companyEnum == null) {
                    LOG.error("[项目服务]公司类别转换错误，company={}", String.valueOf(array[0]));
                    continue;
                }
                ProjectLevelEnum projectLevelEnum = ProjectLevelEnum.convertToEnum(String.valueOf(array[1]));
                if (projectLevelEnum == null) {
                    LOG.error("[项目服务]项目级别转换错误，level={}", String.valueOf(array[1]));
                    continue;
                }
                Long number = (Long) array[2];
                jsonObject.put("department", companyEnum);
                jsonObject.put("level", projectLevelEnum);
                jsonObject.put("number", number);
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

    /**
     * 根据项目编号分页查询里程碑
     *
     * @author LiuYong
     */
    public Map<String, Object> queryMileStoneByProjectCode(String projectCode, int page) {
        Map<String, Object> resultMap = null;
        if (StringUtils.isBlank(projectCode)) {
            return null;
        }
        if (page < 1) {
            //数据库中page从零开始
            LOG.error("[项目服务]请求页面数值不合法，page={}", page);
        }
        PageRequest pageable = new PageRequest(page - 1, ServiceIdentifier.FILE_PAGE_SIZE, new Sort(Sort.Direction.DESC, "signTime"));
        Slice<PbFile> pbFileSlice = pbFileReponsitory.findMileStoneByProjectCode(projectCode, pageable);
        if (pbFileSlice.hasContent()) {
            resultMap = new HashMap<>(2);
            List<PbFile> list = pbFileSlice.getContent();
            int size = list.size();
            JSONArray jsonArray = new JSONArray(size);
            JSONObject jsonObject;
            for (int i = 0; i < size; i++) {
                PbFile pbFile = list.get(i);
                jsonObject = new JSONObject();
                jsonObject.put("fileName", pbFile.getFileName());
                jsonObject.put("path", pbFile.getPath());
                jsonObject.put("signTime", pbFile.getSignTime());
                jsonObject.put("fileCode", pbFile.getFileCode());
                jsonArray.add(jsonObject);
            }
            resultMap.put("data", jsonArray);
            JSONObject pageInfo = new JSONObject();
            pageInfo.put("pageSize", pbFileSlice.getNumberOfElements());
            pageInfo.put("hasNext", pbFileSlice.hasNext());
            resultMap.put("pageInfo", pageInfo);
        }
        return resultMap;
    }

    /**
     * 根据文件编码查询文件展示字段
     *
     * @author LiuYong
     */
    public JSONArray queryFileFiledByFileCode(String fileCode) {
        JSONArray jsonArray = null;
        List<PbFileField> fileFieldList = pbFileFieldReponsitory.findByFileCode(fileCode, new Sort("showOrder"));
        if (fileFieldList != null && !fileFieldList.isEmpty()) {
            int size = fileFieldList.size();
            JSONObject jsonObject;
            jsonArray = new JSONArray(size);
            for (int i = 0; i < size; i++) {
                jsonObject = new JSONObject();
                PbFileField pbFileField = fileFieldList.get(i);
                jsonObject.put("fieldName", pbFileField.getFieldName());
                jsonObject.put("fieldValue", pbFileField.getFieldValue());
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

    /**
     * 根据项目编号和月份查看资产各个组成部分
     *
     * @author LiuYong
     */
    public JSONArray queryAssetByProCodeAndDate(Date date, String proCode) {
        JSONArray jsonArray = null;
        List<Object[]> list = pbAssetCompositionReponsitory.findByProCode(date, proCode);
        if (list != null && !list.isEmpty()) {
            jsonArray = new JSONArray();
            for (Object[] object : list) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("assetType", object[0]);
                jsonObject.put("assetValue", object[1]);
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

    /**
     * 根据项目编号按照类别分组统计资产
     *
     * @author LiuYong
     */
    public JSONArray queryTotalAssetByProCode(String proCode) {
        JSONArray jsonArray = null;
        List<Object[]> list = pbAssetCompositionReponsitory.countTotalByProCode(proCode);
        if (list != null && !list.isEmpty()) {
            jsonArray = new JSONArray();
            for (Object[] object : list) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("assetType", object[0]);
                jsonObject.put("totalAsset", object[1]);
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

    /**
     * 根据项目编号统计资产
     *
     * @author LiuYong
     */
    public Long queryTotalByProCodeList(List<String> proCodeList) {
        Long totalAsset = null;
        if (proCodeList != null && !proCodeList.isEmpty()) {
            totalAsset = pbAssetCompositionReponsitory.countTotalByProCodeList(proCodeList);
        }
        return totalAsset == null ? 0L : totalAsset;
    }

    /**
     * 查询各部门项目列表
     *
     * @author LiuYong
     */
    public JSONObject queryPojectCodeByDepartmentGroup() {
        JSONObject jsonObject = null;
        List<Object[]> result = pbProjectRepository.findPojectCodeByDepartmentGroup();
        if (result != null && !result.isEmpty()) {
            jsonObject = new JSONObject();
            JSONArray proCodeArray;
            int size = result.size();
            for (int i = 0; i < size; i++) {
                Object[] objects = result.get(i);
                String department = String.valueOf(objects[0]);
                if (StringUtils.isNotBlank(department)) {
                    if (jsonObject.containsKey(department)) {
                        proCodeArray = (JSONArray) jsonObject.get(department);
                        proCodeArray.add(objects[1]);
                    } else {
                        proCodeArray = new JSONArray();
                        proCodeArray.add(objects[1]);
                        jsonObject.put(department, proCodeArray);
                    }
                }
            }
        }
        return jsonObject;
    }

    /**
     * 统计所有资产
     *
     * @author LiuYong
     */
    public Long queryTotalAsset() {
        Long total = pbAssetCompositionReponsitory.countTotalAsset();
        return total == null ? 0L : total;
    }

    /**
     * 统计时间段内所有资产
     *
     * @author LiuYong
     */
    public Long queryTotalAssetByYear(Date start, Date end) {
        Long total = null;
        if (start != null && end != null) {
            total = pbAssetCompositionReponsitory.countTotalAssetByYear(start, end);
        }
        return total == null ? 0L : total;
    }

    /**
     * 统计时间段内所有机构资产(机构、其它、金融机构、私募) 
     *
     * @author LiuYong
     */
    public Long queryTotalAssetByType(Date start,String type) {
        Long total = null;
        AgencyEnum agencyEnum = AgencyEnum.convertToEnum(type);
        if(agencyEnum == null){
            return 0L;
        }
        if (start != null) {
            if(agencyEnum == AgencyEnum.AGENCY || agencyEnum== AgencyEnum.OTHER){
                total = pbAgencyRepository.countTotalAgencyMoney(start,type);
            }else if(agencyEnum == AgencyEnum.FINANCE_AGENCY){
                total = pbFinanceAgencyRepository.countTotalFinanceAgencyMoney(start);
            }else if(agencyEnum == AgencyEnum.PRIVATE_FUND){
                total = pbPrivateFundRepository.countTotalPrivateMoney();
            }
        }
        return total == null ? 0L : total;
    }

    /**
     * 项目查询
     */
    public JSONArray searchProject(String proName) {
        List<PbProject> projectList = pbProjectRepository.findAllByProNameLike(proName);
        JSONArray resArray = new JSONArray();
        if (projectList != null && !(projectList.isEmpty())) {
            for (PbProject project : projectList) {
                JSONObject jsonObject = (JSONObject) JSON.toJSON(project);
                resArray.add(jsonObject);
            }
        }
        return resArray;
    }

    /**
     * 删除项目
     */
    @Transactional
    public void deleteProject(String proCode) {
        try {
            pbProjectRepository.deleteByProCode(proCode);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
