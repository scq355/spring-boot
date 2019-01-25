package cn.com.jcgroup.service.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * 项目表
 *
 * @author LiuYong on 17/5/25 下午3:59.
 */
@Entity
@Table(name = "pb_project", uniqueConstraints = {@UniqueConstraint(columnNames = "proCode", name = "u_proCode")})
public class PbProject {

    @Id
    @SequenceGenerator(name = "proId", sequenceName = "seq_project_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "proId")
    private int id;                 //ID
    // 7
    private String proCode;         //项目编码
    private String proNameAbbr;     //项目简称
    private String proName;         //项目全称
    private String isShow;          //是否显示
    private Date createTime;        //创建时间
    private Date updateTime;        //更新时间
    @Column(columnDefinition = "number(19, 0)", nullable = true)
    private Long docNum = 0L;         //文档数量

    //基本信息 11
    private String department;      //所属部门
    private String projectManager;  //项目负责人(项目经理)
    private String storageLevel;    //入库级别
    private String isCore;          //属于核心项目
    private String region;          //区（省市区）
    private String affiCity;        //项目城市
    private String affiProvince;    //所属省份
    private String projectAddress;  //具体地址
    private String projectNature;   //项目性质
    @Column(columnDefinition = "number(19, 0)", nullable = true)
    private Long collectCapital = 0L;    //募集资金
    @Column(columnDefinition = "number(19, 0)", nullable = true)
    private Long scales = 0L;            //项目规模（投资规模）
    private String specialProportion; //投资回本期占特许经营比例

    //项目期限 4
    private String projectPeriod;   //项目期限
    private String constructionPeriod; //建设期
    private String operationPeriod; //运营期
    private String repurchasePeriod; //回购期限

    //收益预测 4
    private String expectProfit;    //收益预测
    private String bankPercent;     //同期银行利率
    private String fundCapital;     //基金销售综合成本
    private String investPercent;   //回购利率

    //项目面积 3
    private String buildingArea;    //建筑面积
    private String landArea;        //用地面积
    private String planArea;        //规划面积

    //其他信息  5
    private String goverPointDown;  //政府工程点位下浮
    private String constrPointDown; //施工方点位下浮
    private String repurchaseMethod;//回购方式
    private String repurchaseBase;  //回购基数
    private String repurchasePercent;  //回购基数
    private String cooperContent;   //合作内容

    //其他信息（前端系统）6
    private String locations;       //项目位置(ArrayString["纬度，经度"])
    private String bannerImgs;      //banner图
    private String abbr;            //缩略图
    private String levels;          //项目等级(激励等级)
    private String innerLevel; //内部级别
    private String projectStatus;   //项目状态
    @Column(columnDefinition = "number(19, 0)", nullable = true)
    private Long encourageMoney = 0L;    //激励总金额（分为单位）

    public PbProject() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProCode() {
        return proCode;
    }

    public void setProCode(String proCode) {
        this.proCode = proCode;
    }

    public String getProNameAbbr() {
        return proNameAbbr;
    }

    public void setProNameAbbr(String proNameAbbr) {
        this.proNameAbbr = proNameAbbr;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getDocNum() {
        return docNum;
    }

    public void setDocNum(Long docNum) {
        this.docNum = docNum;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    public String getStorageLevel() {
        return storageLevel;
    }

    public void setStorageLevel(String storageLevel) {
        this.storageLevel = storageLevel;
    }

    public String getIsCore() {
        return isCore;
    }

    public void setIsCore(String isCore) {
        this.isCore = isCore;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAffiCity() {
        return affiCity;
    }

    public void setAffiCity(String affiCity) {
        this.affiCity = affiCity;
    }

    public String getAffiProvince() {
        return affiProvince;
    }

    public void setAffiProvince(String affiProvince) {
        this.affiProvince = affiProvince;
    }

    public String getProjectAddress() {
        return projectAddress;
    }

    public void setProjectAddress(String projectAddress) {
        this.projectAddress = projectAddress;
    }

    public String getProjectNature() {
        return projectNature;
    }

    public void setProjectNature(String projectNature) {
        this.projectNature = projectNature;
    }

    public Long getCollectCapital() {
        return collectCapital;
    }

    public void setCollectCapital(Long collectCapital) {
        this.collectCapital = collectCapital;
    }

    public Long getScales() {
        return scales;
    }

    public void setScales(Long scales) {
        this.scales = scales;
    }

    public String getSpecialProportion() {
        return specialProportion;
    }

    public void setSpecialProportion(String specialProportion) {
        this.specialProportion = specialProportion;
    }

    public String getProjectPeriod() {
        return projectPeriod;
    }

    public void setProjectPeriod(String projectPeriod) {
        this.projectPeriod = projectPeriod;
    }

    public String getConstructionPeriod() {
        return constructionPeriod;
    }

    public void setConstructionPeriod(String constructionPeriod) {
        this.constructionPeriod = constructionPeriod;
    }

    public String getOperationPeriod() {
        return operationPeriod;
    }

    public void setOperationPeriod(String operationPeriod) {
        this.operationPeriod = operationPeriod;
    }

    public String getRepurchasePeriod() {
        return repurchasePeriod;
    }

    public void setRepurchasePeriod(String repurchasePeriod) {
        this.repurchasePeriod = repurchasePeriod;
    }

    public String getExpectProfit() {
        return expectProfit;
    }

    public void setExpectProfit(String expectProfit) {
        this.expectProfit = expectProfit;
    }

    public String getBankPercent() {
        return bankPercent;
    }

    public void setBankPercent(String bankPercent) {
        this.bankPercent = bankPercent;
    }

    public String getFundCapital() {
        return fundCapital;
    }

    public void setFundCapital(String fundCapital) {
        this.fundCapital = fundCapital;
    }

    public String getInvestPercent() {
        return investPercent;
    }

    public void setInvestPercent(String investPercent) {
        this.investPercent = investPercent;
    }

    public String getBuildingArea() {
        return buildingArea;
    }

    public void setBuildingArea(String buildingArea) {
        this.buildingArea = buildingArea;
    }

    public String getLandArea() {
        return landArea;
    }

    public void setLandArea(String landArea) {
        this.landArea = landArea;
    }

    public String getPlanArea() {
        return planArea;
    }

    public void setPlanArea(String planArea) {
        this.planArea = planArea;
    }

    public String getGoverPointDown() {
        return goverPointDown;
    }

    public void setGoverPointDown(String goverPointDown) {
        this.goverPointDown = goverPointDown;
    }

    public String getConstrPointDown() {
        return constrPointDown;
    }

    public void setConstrPointDown(String constrPointDown) {
        this.constrPointDown = constrPointDown;
    }

    public String getRepurchaseMethod() {
        return repurchaseMethod;
    }

    public void setRepurchaseMethod(String repurchaseMethod) {
        this.repurchaseMethod = repurchaseMethod;
    }

    public String getRepurchaseBase() {
        return repurchaseBase;
    }

    public void setRepurchaseBase(String repurchaseBase) {
        this.repurchaseBase = repurchaseBase;
    }

    public String getRepurchasePercent() {
        return repurchasePercent;
    }

    public void setRepurchasePercent(String repurchasePercent) {
        this.repurchasePercent = repurchasePercent;
    }

    public String getCooperContent() {
        return cooperContent;
    }

    public void setCooperContent(String cooperContent) {
        this.cooperContent = cooperContent;
    }

    public String getLocations() {
        return locations;
    }

    public void setLocations(String locations) {
        this.locations = locations;
    }

    public String getBannerImgs() {
        return bannerImgs;
    }

    public void setBannerImgs(String bannerImgs) {
        this.bannerImgs = bannerImgs;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public String getLevels() {
        return levels;
    }

    public void setLevels(String levels) {
        this.levels = levels;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public Long getEncourageMoney() {
        return encourageMoney;
    }

    public void setEncourageMoney(Long encourageMoney) {
        this.encourageMoney = encourageMoney;
    }

    public String getInnerLevel() {
        return innerLevel;
    }

    public void setInnerLevel(String innerLevel) {
        this.innerLevel = innerLevel;
    }
}
