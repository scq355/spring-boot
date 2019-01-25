package cn.com.jcgroup.planb.manage;

import cn.com.jcgroup.planb.common.PageInfo;
import cn.com.jcgroup.service.enums.AgencyEnum;
import cn.com.jcgroup.service.enums.AssetCompositionEnum;
import cn.com.jcgroup.service.enums.CompanyEnum;
import cn.com.jcgroup.service.enums.ProjectLevelEnum;
import cn.com.jcgroup.service.enums.ProjectStatusEnum;
import cn.com.jcgroup.service.service.ProjectService;
import cn.com.jcgroup.service.util.DateUtil;
import cn.com.jcgroup.service.util.JsonUtil;
import cn.com.jcgroup.service.util.MathUtil;
import cn.com.jcgroup.service.util.NumberUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.Map;

/**
 * 工程相关
 *
 * @author LiuYong on 17/6/6 下午10:16.
 */
@Service
public class ProjectManage {

    private static final Logger LOG = LoggerFactory.getLogger(ProjectManage.class);

    @Autowired
    private ProjectService projectService;

    /**
     * 项目基本信息
     */
    public JSONObject buildProjectInfoByProCode(String proCode) {
        JSONObject resObj = new JSONObject();
        JSONObject proObj = projectService.findProjectBasicInfo(proCode);
        if (proObj != null) {
            resObj.put("hbqzb", proObj.getString("specialProportion"));
            resObj.put("hgfs", proObj.getString("repurchaseMethod"));
            resObj.put("hgjs", proObj.getString("repurchaseBase"));
            resObj.put("hznr", proObj.getString("cooperContent"));
            //数据类型转换
            Long mjzj = proObj.getLong("collectCapital");
            resObj.put("mjzj", NumberUtil.unitHundredMillion(mjzj == null ? 0 : mjzj));
            resObj.put("nbpj", proObj.getString("innerLevel"));
            resObj.put("sgxfdw", proObj.getString("constrPointDown"));
            resObj.put("syyc", proObj.getString("expectProfit"));
            resObj.put("xmcs", proObj.getString("affiCity"));
            Long xmgm = proObj.getLong("scales");
            resObj.put("xmgm", NumberUtil.unitHundredMillion(xmgm == null ? 0: xmgm));
            resObj.put("xmjl", proObj.getString("projectManager"));
            resObj.put("xmkjb", proObj.getString("storageLevel"));
            String projectPeriod = StringUtils.isBlank(proObj.getString("projectPeriod")) ? "-":proObj.getString("projectPeriod");
            String constructionPeriod = StringUtils.isBlank(proObj.getString("constructionPeriod")) ? "-":proObj.getString("constructionPeriod");
            String operationPeriod = StringUtils.isBlank(proObj.getString("operationPeriod")) ? "-":proObj.getString("operationPeriod");
            String repurchasePeriod = StringUtils.isBlank(proObj.getString("repurchasePeriod")) ? "-":proObj.getString("repurchasePeriod");
            String periodInfo = "总期限("+projectPeriod+")、"+"建设期("+constructionPeriod+")、"+"回购期("+repurchasePeriod+")、"+"运营期("+operationPeriod+")";
            resObj.put("xmqx", periodInfo);
            resObj.put("xmxz", proObj.getString("projectNature"));
            resObj.put("zfxfdw", proObj.getString("goverPointDown"));
        }
        return resObj;
    }


    /**
     * 根据项目编码返回激励信息
     *
     * @param projectCode
     * @return
     */
    public JSONObject buildEncourageInfoByProCode(String projectCode) {
        JSONArray resultArray;
        JSONArray jsonArray = projectService.findEncouragesByProCode(projectCode);
        JSONObject resultObject = new JSONObject();
        if (jsonArray != null && !jsonArray.isEmpty()) {
            resultArray = new JSONArray();
            int size =  jsonArray.size();
            JSONObject jsonObject;
            for (int i = 0; i < size; i++) {
                jsonObject = new JSONObject();
                JSONObject tempJsonObject = jsonArray.getJSONObject(i);
                Integer moneyFlag = tempJsonObject.getInteger("moneyFlag");
                jsonObject.put("money_flag", moneyFlag != null &&(moneyFlag == 1));
                String prePercent = tempJsonObject.getString("prePercent");
                jsonObject.put("pre_percent", MathUtil.format(MathUtil.div(prePercent == null ? 0 : Double.valueOf(prePercent),100)));
                Date preTime = tempJsonObject.getDate("preDate");
                jsonObject.put("pre_time", preTime == null ? "":DateUtil.formatDate("yyyy.MM",preTime));
                Long realMoney = tempJsonObject.getLong("realMoney");
                jsonObject.put("real_money", realMoney == null ? "0" : NumberUtil.unitTenThousand(realMoney));
                String realPercent = tempJsonObject.getString("realPercent");
                jsonObject.put("real_percent", MathUtil.format(MathUtil.div(realPercent == null ? 0 : Double.valueOf(realPercent),100)));
                Integer realStatus = tempJsonObject.getInteger("realStatus");
                jsonObject.put("real_status",realStatus != null && (realStatus == 1));
                jsonObject.put("stage", tempJsonObject.getString("stage"));
                resultArray.add(jsonObject);
            }
            //查询
            JSONObject temp = projectService.queryEncourageByProCode(projectCode);
            resultObject.put("level", "");
            resultObject.put("money", "0");
            if(temp != null){
                Long money = temp.getLong("encourageMoney");
                resultObject.put("money",money == null ? "0" : NumberUtil.unitTenThousand(money));
                resultObject.put("level",temp.getString("levels"));
            }
            resultObject.put("info", resultArray);
        }
        return resultObject;
    }

    /**
     * 根据项目所在省份分组进行项目个数统计
     *
     * @author LiuYong
     */
    public JSONArray buildProjectNumberByProvinceGroup() {
        JSONArray resultArray = null;
        JSONArray jsonArray = projectService.queryProjectNumberByProvinceGroup();
        if (jsonArray != null && !jsonArray.isEmpty()) {
            resultArray = new JSONArray();
            int size = jsonArray.size();
            JSONObject jsonObject;
            for (int i = 0; i < size; i++) {
                jsonObject = new JSONObject();
                JSONObject tempJsonObject = jsonArray.getJSONObject(i);
                LOG.debug("[项目管理服务]统计数据如下:{}", tempJsonObject.toJSONString());
                jsonObject.put("project_number", tempJsonObject.get("number"));
                jsonObject.put("province", tempJsonObject.get("affiProvince"));
                resultArray.add(jsonObject);
            }
        }
        return resultArray;
    }

    /**
     * 根据项目状态分组进行项目规模统计
     *
     * @author LiuYong
     */
    public JSONObject buildProjectScaleByStatusGroup() {
        JSONObject jsonObject = null;
        JSONArray jsonArray = projectService.queryProjectScaleByStatusGroup();
        if (jsonArray != null && !jsonArray.isEmpty()) {
            jsonObject = new JSONObject();
            int size = jsonArray.size();
            jsonObject.put("negotiate_scale", "0");
            jsonObject.put("sign_scale", "0");
            jsonObject.put("begin_scale", "0");
            jsonObject.put("operate_scale", "0");
            for (int i = 0; i < size; i++) {
                JSONObject tempJsonObject = jsonArray.getJSONObject(i);
                LOG.debug("[项目管理服务]统计项目规模如下:{}", tempJsonObject.toJSONString());
                ProjectStatusEnum projectStatusEnum = (ProjectStatusEnum) tempJsonObject.get("projectStatus");
                Long tempSacle = tempJsonObject.getLong("totalScale");
                //以亿为单位
                String scale = NumberUtil.unitHundredMillion(tempSacle);
                if (projectStatusEnum == ProjectStatusEnum.NEGOTIATE) {
                    jsonObject.put("negotiate_scale", scale);
                } else if (projectStatusEnum == ProjectStatusEnum.SIGNED) {
                    jsonObject.put("sign_scale", scale);
                } else if (projectStatusEnum == ProjectStatusEnum.START) {
                    jsonObject.put("begin_scale", scale);
                } else if (projectStatusEnum == ProjectStatusEnum.OPERATION) {
                    jsonObject.put("operate_scale", scale);
                }
            }
        }
        return jsonObject;
    }

    /**
     * 根据省份查询项目简略信息
     *
     * @author LiuYong
     */
    public JSONArray buildProjectInfoByByProvince(String affiProvince) {
        JSONArray jsonArray = null;
        if (StringUtils.isBlank(affiProvince)) {
            return null;
        }
        JSONArray tempJsonArray = projectService.queryProjectInfoByByProvince(affiProvince);
        if (tempJsonArray != null && !tempJsonArray.isEmpty()) {
            jsonArray = new JSONArray();
            int size = tempJsonArray.size();
            JSONObject jsonObject;
            for (int i = 0; i < size; i++) {
                jsonObject = new JSONObject();
                JSONObject tempJsonObject = tempJsonArray.getJSONObject(i);
                LOG.debug("[项目管理服务]项目简略信息如下:{}", tempJsonObject.toJSONString());
                jsonObject.put("project_code", tempJsonObject.get("proCode"));
                Long scale = tempJsonObject.getLong("scale");
                //转换单位为亿
                jsonObject.put("project_scale", NumberUtil.unitHundredMillion(scale));
                jsonObject.put("project_name", tempJsonObject.get("proName"));
                jsonObject.put("project_name_abbr", tempJsonObject.get("proNameAbbr"));
                String location = tempJsonObject.getString("location");
                if(StringUtils.isNotBlank(location)){
                    try{
                        JSONArray locationArray = JSONArray.parseArray(location);
                        jsonObject.put("project_location", locationArray);
                    }catch (Exception e){
                        LOG.error("[项目管理服务]经纬度异常,proCode={}",tempJsonObject.get("proCode"));
                        jsonObject.put("project_location", null);
                    }
                }else {
                    jsonObject.put("project_location", null);
                }
                //缩略图
                String abbr =  tempJsonObject.getString("abbr");
                if(StringUtils.isNotBlank(abbr)){
                    try{
                        JSONArray abbrArray = JSONArray.parseArray(abbr);
                        jsonObject.put("abbr_image", abbrArray);
                    }catch (Exception e){
                        LOG.error("[项目管理服务]缩略图异常,proCode={}",tempJsonObject.get("proCode"));
                        jsonObject.put("abbr_image", null);
                    }
                }else {
                    jsonObject.put("abbr_image", null);
                }
                jsonObject.put("status_key", tempJsonObject.get("projectStatus"));
                CompanyEnum companyEnum = CompanyEnum.convertToEnum(tempJsonObject.getString("department"));
                jsonObject.put("department", companyEnum == null ? "" :companyEnum.getName());
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
    public JSONObject buildInfoByDepartmentAndStatusGroup() {
        JSONObject jsonObject = new JSONObject();
        //初始化项目个数 and 初始化项目规模
        int length = CompanyEnum.values().length-1;
        JSONArray beginArray = new JSONArray(JsonUtil.initJsonArray(length, "0"));
        JSONArray beginNumArray = new JSONArray(JsonUtil.initJsonArray(length, 0));
        JSONArray negotiateArray = new JSONArray(JsonUtil.initJsonArray(length, "0"));
        JSONArray negotiateNumArray = new JSONArray(JsonUtil.initJsonArray(length, 0));
        JSONArray operateArray = new JSONArray(JsonUtil.initJsonArray(length, "0"));
        JSONArray operateNumArray = new JSONArray(JsonUtil.initJsonArray(length, 0));
        JSONArray signArray = new JSONArray(JsonUtil.initJsonArray(length, "0"));
        JSONArray signNumArray = new JSONArray(JsonUtil.initJsonArray(length, 0));
        JSONArray jsonArray = projectService.queryInfoByDepartmentAndStatusGroup();
        if (jsonArray != null && !jsonArray.isEmpty()) {
            int size = jsonArray.size();
            JSONObject tempJsonObject;
            for (int i = 0; i < size; i++) {
                tempJsonObject = jsonArray.getJSONObject(i);
                LOG.debug("[项目管理服务]项目规模、项目个数统计信息如下:{}", tempJsonObject.toJSONString());
                CompanyEnum companyEnum = (CompanyEnum) tempJsonObject.get("department");
                int index = CompanyEnum.index(companyEnum);
                if (index != -1) {
                    ProjectStatusEnum projectStatusEnum = (ProjectStatusEnum) tempJsonObject.get("projectStatus");
                    Long tempScale = tempJsonObject.getLong("sumScale");
                    String scale = NumberUtil.unitHundredMillion(tempScale);
                    Long number = tempJsonObject.getLong("totalNumbers");
                    if (projectStatusEnum == ProjectStatusEnum.NEGOTIATE) {
                        negotiateArray.set(index, scale);
                        negotiateNumArray.set(index, number);
                    } else if (projectStatusEnum == ProjectStatusEnum.SIGNED) {
                        signArray.set(index, scale);
                        signNumArray.set(index, number);
                    } else if (projectStatusEnum == ProjectStatusEnum.START) {
                        beginArray.set(index, scale);
                        beginNumArray.set(index, number);
                    } else if (projectStatusEnum == ProjectStatusEnum.OPERATION) {
                        operateArray.set(index, scale);
                        operateNumArray.set(index, number);
                    }
                }
            }
        }
        //项目个数
        jsonObject.put("begin", beginArray);
        jsonObject.put("negotiate", negotiateArray);
        jsonObject.put("operate", operateArray);
        jsonObject.put("sign", signArray);
        //项目规模
        jsonObject.put("begin_num", beginNumArray);
        jsonObject.put("negotiate_num", negotiateNumArray);
        jsonObject.put("operate_num", operateNumArray);
        jsonObject.put("sign_num", signNumArray);
        return jsonObject;
    }

    /**
     * 根据项目状态分组进行项目个数统计
     *
     * @author LiuYong
     */
    public JSONArray buildProjectNumberByStatusGroup() {
        JSONArray jsonArray = new JSONArray();
        //初始化模板
        for(ProjectStatusEnum projectStatusEnum : ProjectStatusEnum.values()){
            JSONObject jsonObject = new JSONObject();
            jsonArray.add(jsonObject);
            jsonObject.put("status_key", projectStatusEnum.getStatus());
            jsonObject.put("number", 0);
            jsonObject.put("project_status", projectStatusEnum.getInfo());
        }
        JSONArray tempJsonArray = projectService.queryProjectNumberByStatusGroup();
        if (tempJsonArray != null && !tempJsonArray.isEmpty()) {
            int size = tempJsonArray.size();
            for (int i = 0; i < size; i++) {
                JSONObject tempJsonObject = tempJsonArray.getJSONObject(i);
                LOG.debug("[项目管理服务]根据项目状态分组进行项目个数统计如下:{}", tempJsonObject.toJSONString());
                ProjectStatusEnum projectStatusEnum = (ProjectStatusEnum) tempJsonObject.get("projectStatus");
                for(Object object : jsonArray){
                    JSONObject temp = (JSONObject) object;
                    String key = temp.getString("status_key");
                    if(key.equalsIgnoreCase(projectStatusEnum.getStatus())){
                        temp.put("status_key", projectStatusEnum.getStatus());
                        temp.put("number", tempJsonObject.get("number"));
                        temp.put("project_status", projectStatusEnum.getInfo());
                        break;
                    }
                }
                
            }
        }
        return jsonArray;
    }

    /**
     * 根据文件级别进行分组统计激励文件个数
     *
     * @author LiuYong
     */
    public JSONObject buildEncourageNumberByLevelGroup() {
        JSONObject jsonObject = null;
        JSONArray jsonArray = projectService.queryEncourageNumberByLevelGroup();
        if (jsonArray != null && !jsonArray.isEmpty()) {
            jsonObject = new JSONObject();
            jsonObject.put("count_A", 0);
            jsonObject.put("count_B", 0);
            jsonObject.put("count_C", 0);
            jsonObject.put("count_D", 0);
            int size = jsonArray.size();
            JSONObject tempJsonObject;
            for (int i = 0; i < size; i++) {
                tempJsonObject = jsonArray.getJSONObject(i);
                LOG.debug("[项目管理服务]根据文件级别进行分组统计激励文件个数:{}", tempJsonObject.toJSONString());
                ProjectLevelEnum projectLevelEnum = (ProjectLevelEnum) tempJsonObject.get("level");
                Long number = tempJsonObject.getLong("number");
                if (projectLevelEnum == ProjectLevelEnum.A) {
                    jsonObject.put("count_A", number);
                } else if (projectLevelEnum == ProjectLevelEnum.B) {
                    jsonObject.put("count_B", number);
                } else if (projectLevelEnum == ProjectLevelEnum.C) {
                    jsonObject.put("count_C", number);
                } else if (projectLevelEnum == ProjectLevelEnum.D) {
                    jsonObject.put("count_D", number);
                }
            }
        }
        return jsonObject;
    }

    /**
     * 根据公司、评级分组进行项目个数统计
     *
     * @author LiuYong
     */
    public JSONObject buildEncourageNumberByCompanyAndLevelGroup() {
        JSONObject jsonObject = new JSONObject();
        //初始化项目个数 and 初始化项目规模
        int length = CompanyEnum.values().length - 1;
        JSONArray levelA = new JSONArray(JsonUtil.initJsonArray(length, 0));
        JSONArray levelB = new JSONArray(JsonUtil.initJsonArray(length, 0));
        JSONArray levelC = new JSONArray(JsonUtil.initJsonArray(length, 0));
        JSONArray levelD = new JSONArray(JsonUtil.initJsonArray(length, 0));
        JSONArray jsonArray = projectService.queryEncourageNumberByCompanyAndLevelGroup();
        if (jsonArray != null && !jsonArray.isEmpty()) {
            int size = jsonArray.size();
            JSONObject tempJsonObject;
            for (int i = 0; i < size; i++) {
                tempJsonObject = jsonArray.getJSONObject(i);
                LOG.debug("[项目管理服务]根据公司、评级分组进行项目个数统计信息如下:{}", tempJsonObject.toJSONString());
                CompanyEnum companyEnum = (CompanyEnum) tempJsonObject.get("department");
                int index = CompanyEnum.index(companyEnum);
                if (index != -1) {
                    ProjectLevelEnum projectLevelEnum = (ProjectLevelEnum) tempJsonObject.get("level");
                    Long number = tempJsonObject.getLong("number");
                    if (projectLevelEnum == ProjectLevelEnum.A) {
                        levelA.set(index, number);
                    } else if (projectLevelEnum == ProjectLevelEnum.B) {
                        levelB.set(index, number);
                    } else if (projectLevelEnum == ProjectLevelEnum.C) {
                        levelC.set(index, number);
                    } else if (projectLevelEnum == ProjectLevelEnum.D) {
                        levelD.set(index, number);
                    }
                }
            }
        }
        //项目个数
        jsonObject.put("level_A", levelA);
        jsonObject.put("level_B", levelB);
        jsonObject.put("level_C", levelC);
        jsonObject.put("level_D", levelD);
        return jsonObject;
    }

    /**
     * 根据项目编码进行分页拉取里程碑文件
     *
     * @author LiuYong
     */
    public JSONObject buildMileStoneByProjectCode(String projectCode, int page) {
        JSONObject jsonObject;
        Map<String, Object> map = projectService.queryMileStoneByProjectCode(projectCode, page);
        if (map == null || map.isEmpty()) {
            return null;
        }
        jsonObject = new JSONObject();
        JSONObject pageJsonObject = (JSONObject) map.get("pageInfo");
        //组装page信息
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNo(page);
        pageInfo.setPageSize(pageJsonObject.getIntValue("pageSize"));
        pageInfo.setHasNext(pageJsonObject.getBooleanValue("hasNext"));
        jsonObject.put("pageInfo", pageInfo);
        JSONArray jsonArray = (JSONArray) map.get("data");
        int size = jsonArray.size();
        JSONArray resultJsonArray = new JSONArray(size);
        for (int i = 0; i < size; i++) {
            JSONObject tempJsonObject = jsonArray.getJSONObject(i);
            Date date = tempJsonObject.getDate("signTime");
            tempJsonObject.put("date", date == null ? "" : DateUtil.formatDate("yyyy.MM.dd", date));
            tempJsonObject.put("file_name", tempJsonObject.get("fileName"));
            tempJsonObject.put("file_url", tempJsonObject.get("path"));
            tempJsonObject.put("file_code", tempJsonObject.get("fileCode"));
            resultJsonArray.add(tempJsonObject);
        }
        jsonObject.put("data", resultJsonArray);
        return jsonObject;
    }

    /**
     * 根据文件编码查询文件展示字段
     *
     * @author LiuYong
     */
    public JSONArray buildFileFiledByFileCode(String fileCode) {
        JSONArray jsonArray = null;
        JSONArray fileFieldList = projectService.queryFileFiledByFileCode(fileCode);
        if (fileFieldList != null && !fileFieldList.isEmpty()) {
            int size = fileFieldList.size();
            JSONObject jsonObject;
            jsonArray = new JSONArray(size);
            for (int i = 0; i < size; i++) {
                jsonObject = new JSONObject();
                JSONObject tempJsonObject = fileFieldList.getJSONObject(i);
                jsonObject.put("name", tempJsonObject.get("fieldName"));
                jsonObject.put("value", tempJsonObject.get("fieldValue"));
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

    /**
     * 根据项目编号查询资产组成
     *
     * @author LiuYong
     */
    public JSONObject buildAssetCompositionByProCode(String proCode) {
        JSONObject jsonObject = new JSONObject();
        //初始化模板
        JSONArray currentArray = new JSONArray();
        JSONObject temp;
        for (AssetCompositionEnum assetCompositionEnum : AssetCompositionEnum.values()) {
            temp = new JSONObject();
            temp.put("key", assetCompositionEnum.getType());
            temp.put("name", assetCompositionEnum.getInfo());
            temp.put("value", "0");
            temp.put("money","0");
            currentArray.add(temp);
        }
        JSONArray totalArray = JsonUtil.deepClone(currentArray);
        //查询资产
        Date current = DateUtil.getFirstDay(DateUtil.getMonth(new Date())+1);
        JSONArray tempCurrentArray = projectService.queryAssetByProCodeAndDate(current, proCode);
        //查询current总资产
        long totalCurrent = 0;
        if (tempCurrentArray != null && !tempCurrentArray.isEmpty()) {
            for (Object object : tempCurrentArray) {
                JSONObject tempJsonObject = (JSONObject) object;
                totalCurrent += tempJsonObject.getLongValue("assetValue");
            }
            //赋值给每个字断
            for (Object object : tempCurrentArray) {
                JSONObject tempJsonObject = (JSONObject) object;
                String assetType = tempJsonObject.getString("assetType");
                for (Object tempObject : currentArray) {
                    JSONObject currentObject = (JSONObject) tempObject;
                    if (currentObject.getString("key").equalsIgnoreCase(assetType)) {
                        Long assetValue = tempJsonObject.getLongValue("assetValue");
                        currentObject.put("value", MathUtil.format(MathUtil.div(assetValue, totalCurrent)));
                        currentObject.put("money", NumberUtil.unitTenThousand(assetValue));
                        break;
                    }
                }
            }
        }
        jsonObject.put("current", currentArray);
        jsonObject.put("current_value", NumberUtil.unitTenThousand(totalCurrent));
        //查询总资产
        JSONArray tempTotalArray = projectService.queryTotalAssetByProCode(proCode);
        //查询总资产
        long totalValue = 0;
        if (tempTotalArray != null && !tempTotalArray.isEmpty()) {
            for (Object object : tempTotalArray) {
                JSONObject totalObject = (JSONObject) object;
                totalValue += totalObject.getLongValue("totalAsset");
            }
            //赋值给每个字断
            for (Object object : tempTotalArray) {
                JSONObject tempJsonObject = (JSONObject) object;
                String assetType = tempJsonObject.getString("assetType");
                for (Object tempObject : totalArray) {
                    JSONObject totalObject = (JSONObject) tempObject;
                    if (totalObject.getString("key").equalsIgnoreCase(assetType)) {
                        Long totalAsset = tempJsonObject.getLongValue("totalAsset");
                        totalObject.put("value", MathUtil.format(MathUtil.div(totalAsset, totalValue)));
                        totalObject.put("money", NumberUtil.unitTenThousand(totalAsset));
                        break;
                    }
                }
            }
        }
        jsonObject.put("grand_total", totalArray);
        jsonObject.put("total_value", NumberUtil.unitTenThousand(totalValue));
        return jsonObject;
    }

    /**
     * 机构募集&私募募集&今年新增
     *
     * @author LiuYong
     */
    public JSONObject buildStatistic(String type){
        JSONObject jsonObject = null;
        if("1".equalsIgnoreCase(type)){//机构
            Date start = DateUtil.getFirstDay(1);
            Long totalAsset = projectService.queryTotalAssetByType(start, AgencyEnum.AGENCY.getCode());
            Long totalOther = projectService.queryTotalAssetByType(start,AgencyEnum.OTHER.getCode());
            Long totalFinanceAgency = projectService.queryTotalAssetByType(start,AgencyEnum.FINANCE_AGENCY.getCode());
            Long current = totalAsset + totalOther + totalFinanceAgency;
            jsonObject = new JSONObject();
            jsonObject.put("current",NumberUtil.unitHundredMillion(current));
            jsonObject.put("total","350");
            jsonObject.put("percent",MathUtil.format(MathUtil.div(current,350*100000000L*100)));
        }else if("2".equalsIgnoreCase(type)){//私募
            jsonObject = new JSONObject();
            Date start = DateUtil.getFirstDay(1);
            Long totalPrivate = projectService.queryTotalAssetByType(start, AgencyEnum.PRIVATE_FUND.getCode());
            jsonObject.put("current",NumberUtil.unitHundredMillion(totalPrivate));
            jsonObject.put("total","150");
            jsonObject.put("percent",MathUtil.format(MathUtil.div(totalPrivate,150*100000000L*100)));
        }else if("3".equalsIgnoreCase(type)){//今年新增
            jsonObject = new JSONObject();
//            Long total = projectService.queryTotalAsset();
            Long total = 500 * NumberUtil.UNIT_HUNDRED_MILLION;
            jsonObject.put("total",NumberUtil.unitHundredMillion(total));
            Date start = DateUtil.getFirstDay(1);
            Date end = DateUtil.getFirstDay(12);
            Long yearTotal = projectService.queryTotalAssetByYear(start,end);
            jsonObject.put("current",NumberUtil.unitHundredMillion(yearTotal));
            if(total == 0){//分母不能为0
                jsonObject.put("percent","0");
            }else{
                jsonObject.put("percent",MathUtil.format(MathUtil.div(yearTotal,total)));
            }
        }
        return jsonObject;
    }

    /**
     * 各部门资产形成
     *
     * @author LiuYong
     */
    public JSONArray buildTotalAssetPercent(){
        JSONArray jsonArray = new JSONArray();
        JSONObject template;
        //查询各部门项目列表
        JSONObject depJsonObject = projectService.queryPojectCodeByDepartmentGroup();
        //查询总资产
        Long total = projectService.queryTotalAsset();
        //初始化模板
        for (CompanyEnum department:CompanyEnum.values()) {
            if(department != CompanyEnum.ALL){
                template = new JSONObject();
                String depCode = department.getCode();
                template.put("depart_name",department.getName());
                template.put("depart_key",depCode);
                template.put("percent","0");
                JSONArray proCodeList = (JSONArray) depJsonObject.get(depCode);
                if( proCodeList!= null && !proCodeList.isEmpty()){
                    Long depMoney = projectService.queryTotalByProCodeList(JSONArray.parseArray(proCodeList.toJSONString(),String.class));
                    template.put("percent",MathUtil.format(MathUtil.div(depMoney,total)));
                    template.put("money",NumberUtil.unitHundredMillion(depMoney));
                }
                jsonArray.add(template);
            }
        }
        //排序 升序
        jsonArray.sort(new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                JSONObject jsonObject1 = (JSONObject)o1;
                JSONObject jsonObject2 = (JSONObject)o2;
                return jsonObject1.getIntValue("depart_key") - jsonObject2.getIntValue("depart_key");
            }
        });
        return jsonArray;
    }

}
