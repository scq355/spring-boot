package cn.com.jcgroup.admin.manage;

import cn.com.jcgroup.admin.dto.ProjectEditInfoDto;
import cn.com.jcgroup.service.enums.ProjectStatusEnum;
import cn.com.jcgroup.service.service.ProjectDocService;
import cn.com.jcgroup.service.service.ProjectService;
import cn.com.jcgroup.service.util.DateUtil;
import cn.com.jcgroup.service.util.NumberUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Description: 项目相关
 * User: sunchangqing
 * Date: 2017-06-13
 * Time: 上午11:29
 */
@Service
public class ProjectManage {

    private static final Logger LOG = LoggerFactory.getLogger(ProjectManage.class);

    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProjectDocService projectDocService;

    /**
     * 项目查询
     */
    public JSONArray searchProject(String proName) {
        JSONArray jsonArray = new JSONArray();
        JSONArray resArray = projectService.searchProject(proName);
        if (resArray != null && !(resArray.isEmpty())) {
            int projectNum = resArray.size();
            for (int i = 0; i < projectNum; i++) {
                JSONObject jsonObject = new JSONObject();
                JSONObject projectObj = resArray.getJSONObject(i);
                if (projectObj != null) {
                    String isShow = projectObj.getString("isShow");
                    if (StringUtils.isNotBlank(isShow) && isShow.equals("1")) {
                        jsonObject.put("is_show", true);
                    } else {
                        jsonObject.put("is_show", false);
                    }
                    jsonObject.put("pro_abbr", projectObj.getString("proNameAbbr"));
                    jsonObject.put("pro_code", projectObj.getString("proCode"));
                    jsonObject.put("pro_name", projectObj.getString("proName"));
                    String signDate = DateUtil.formatDate("yyyy.MM.dd", (Date) projectObj.get("createTime"));
                    jsonObject.put("sign_time", signDate);
                    jsonObject.put("doc_number", projectObj.getString("docNum"));
                    jsonArray.add(jsonObject);
                }
            }
        }
        return jsonArray;
    }

    /**
     * 项目概览-修改
     */
    public void editProjectOverviewByProCode(ProjectEditInfoDto projectEditInfoDto) {
        JSONObject resObject = new JSONObject();
        resObject.put("constructionPeriod", projectEditInfoDto.getConstruction_period());
        resObject.put("affiProvince", projectEditInfoDto.getAffi_province());
        resObject.put("affiCity", projectEditInfoDto.getAffi_city());
        resObject.put("bankPercent", projectEditInfoDto.getBank_percent());
        resObject.put("buildingArea", projectEditInfoDto.getBuilding_area());
        resObject.put("collectCapital", NumberUtil.convertToPointWithUnit(projectEditInfoDto.getCollect_capital(),100000000L));
        resObject.put("constrPointDown", projectEditInfoDto.getConstr_point_down());
        resObject.put("cooperContent", projectEditInfoDto.getCooper_content());
        resObject.put("department", projectEditInfoDto.getDepartment());
        resObject.put("expectProfit", projectEditInfoDto.getExpect_profit());
        resObject.put("fundCapital", projectEditInfoDto.getFund_capital());
        resObject.put("goverPointDown", projectEditInfoDto.getGover_point_down());
        resObject.put("specialProportion", projectEditInfoDto.getInvest_percent());
        if (projectEditInfoDto.isIs_core()) {
            resObject.put("isCore", "1");
        } else {
            resObject.put("isCore", "0");
        }
        resObject.put("landArea", projectEditInfoDto.getLand_area());
        resObject.put("storageLevel", projectEditInfoDto.getStorage_level());
        resObject.put("operationPeriod", projectEditInfoDto.getOperation_period());
        resObject.put("planArea", projectEditInfoDto.getPlan_area());
        resObject.put("operationPeriod", projectEditInfoDto.getOperation_period());
        resObject.put("planArea", projectEditInfoDto.getPlan_area());
        resObject.put("proCode", projectEditInfoDto.getPro_code());
        resObject.put("projectAddress", projectEditInfoDto.getProject_address());
        resObject.put("projectManager", projectEditInfoDto.getProject_manager());
        resObject.put("projectNature", projectEditInfoDto.getProject_nature());
        resObject.put("region", projectEditInfoDto.getRegion());
        resObject.put("repurchaseBase", projectEditInfoDto.getRepurchase_base());
        resObject.put("repurchaseMethod", projectEditInfoDto.getRepurchase_method());
        resObject.put("repurchasePercent", projectEditInfoDto.getRepurchase_percent());
        resObject.put("repurchasePeriod", projectEditInfoDto.getRepurchase_period());
        //数据类型转换
        String scale = projectEditInfoDto.getScales();
        resObject.put("scales", NumberUtil.convertToPointWithUnit(scale,100000000L));
        resObject.put("projectPeriod", projectEditInfoDto.getProject_period());
        resObject.put("projectAddress", projectEditInfoDto.getProject_address());
        //项目状态
        ProjectStatusEnum projectStatusEnum = ProjectStatusEnum.convertToEnum(projectEditInfoDto.getProject_status());
        resObject.put("projectStatus", projectStatusEnum == null ? "" :projectStatusEnum.getStatus());
        resObject.put("innerLevel",projectEditInfoDto.getInner_level());
        projectService.updateProjectInfo(resObject);
    }

    /**
     * 项目概览-显示
     */
    public JSONObject buildProjectOverviewByProCode(String proCode) {
        JSONObject jsonObject = new JSONObject();
        JSONObject projectObject = projectService.findProjectBasicInfo(proCode);
        if (projectObject != null) {
            jsonObject.put("department", projectObject.getString("department"));
            jsonObject.put("project_manager", projectObject.getString("projectManager"));
            jsonObject.put("storage_level", projectObject.getString("storageLevel"));
            //TODO 有问题
            try {
                if (projectObject.getString("isCore").equals("1")) {
                    jsonObject.put("is_core", true);
                } else {
                    jsonObject.put("is_core", false);
                }
            } catch (Exception e) {
                LOG.error(e.getMessage());
            }
            jsonObject.put("region", projectObject.getString("region"));
            jsonObject.put("affi_city", projectObject.getString("affiCity"));
            jsonObject.put("affi_province", projectObject.getString("affiProvince"));
            jsonObject.put("project_nature", projectObject.getString("projectNature"));
            //募集资金
            Long capital = projectObject.getLong("collectCapital");
            jsonObject.put("collect_capital", capital == null ? "0" :  NumberUtil.unitHundredMillion(capital));
            //投资规模
            Long scale = projectObject.getLong("scales");
            jsonObject.put("scales", scale == null ? "0" : NumberUtil.unitHundredMillion(scale));
            jsonObject.put("special_proportion", projectObject.getString("specialProportion"));
            jsonObject.put("project_period", projectObject.getString("projectPeriod"));
            jsonObject.put("construction_period", projectObject.getString("constructionPeriod"));
            jsonObject.put("operation_period", projectObject.getString("operationPeriod"));
            jsonObject.put("repurchase_period", projectObject.getString("repurchasePeriod"));
            jsonObject.put("expect_profit", projectObject.getString("expectProfit"));
            jsonObject.put("bank_percent", projectObject.getString("bankPercent"));
            jsonObject.put("fund_capital", projectObject.getString("fundCapital"));
            jsonObject.put("invest_percent", projectObject.getString("investPercent"));
            jsonObject.put("building_area", projectObject.getString("buildingArea"));
            jsonObject.put("land_area", projectObject.getString("landArea"));
            jsonObject.put("plan_area", projectObject.getString("planArea"));
            jsonObject.put("gover_point_down", projectObject.getString("goverPointDown"));
            jsonObject.put("constr_point_down", projectObject.getString("constrPointDown"));
            jsonObject.put("repurchase_method", projectObject.getString("repurchaseMethod"));
            jsonObject.put("repurchase_base", projectObject.getString("repurchaseBase"));
            jsonObject.put("cooper_content", projectObject.getString("cooperContent"));
            jsonObject.put("project_address", projectObject.getString("projectAddress"));
            jsonObject.put("repurchase_percent", projectObject.getString("repurchasePercent"));
            jsonObject.put("project_status",projectObject.getString("projectStatus"));
            jsonObject.put("inner_level",projectObject.get("innerLevel"));
        }
        return jsonObject;
    }

    /**
     * 项目列表
     */
    public JSONArray queryProjectByProCode(int page) {
        JSONArray jsonArray = new JSONArray();
        JSONArray projectArray = projectService.listProject(page);
        for (int i = 0; i < projectArray.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            JSONObject projectObj = projectArray.getJSONObject(i);
            if (projectObj != null) {
                String proCode = projectObj.getString("proCode");
                int fileNum = projectDocService.queryFileNumByProCode(proCode);
                jsonObject.put("doc_number", fileNum);
                jsonObject.put("pro_abbr", projectObj.getString("proNameAbbr"));
                jsonObject.put("is_show", projectObj.getBooleanValue("isShow"));
                String createDate = DateUtil.formatDate("yyyy.MM", (Date) projectObj.get("createTime"));
                jsonObject.put("sign_time", createDate);
                jsonObject.put("pro_code", projectObj.getString("proCode"));
                jsonObject.put("pro_name", projectObj.getString("proName"));
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }
}
