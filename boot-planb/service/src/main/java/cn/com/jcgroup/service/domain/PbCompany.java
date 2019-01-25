package cn.com.jcgroup.service.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Date;

/**
 * 合营公司
 *
 * @author LiuYong on 17/5/27 下午11:58.
 */
@Entity
@Table(name = "pb_company", uniqueConstraints = {@UniqueConstraint(columnNames = "companyCode", name = "u_companyCode")})
public class PbCompany {
    @Id
    @SequenceGenerator(name = "companyId", sequenceName = "seq_company_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "companyId")
    private int id;
    //基本信息
    private String companyCode;             //公司编码
    private String companyName;             //公司名称
    private String proCode;                 //项目编码
    private String isShow;                  //是否展示
    private Date createTime;                //创建时间
    private Date updateTime;                //更新时间

    //办公信息
    @Column(columnDefinition = "CLOB")
    private String officeImages;            //办公照片
    @Column(length = 1024)
    private String officeAddress;           //办公地址
    @Column(columnDefinition = "NUMBER(19) default 0 ")
    private long officeRent;                //办公租金
    private String officeSize;              //办公面积

    //公司高层
    @Column(length = 1024)
    private String ownershipStruct;         //股权结构
    @Column(length = 1024)
    private String director;                //董事
    private String seniorManager;           //监事
    private String supervisors;             //高级经理
    private String stockHolder;             //股东
    private String projectManager;          //项目经理
    private String legalPerson;             //公司法人

    //公司注册信息
    @Column(columnDefinition = "NUMBER(19) default 0 ")
    private long registerCapital;           //注册资本
    @Column(length = 1024)
    private String equityTransferAgree;     //股权转协议
    @Column(length = 1024)
    private String rulePromise;             //章程特殊约定
    @Column(length = 1024)
    private String profitDistrPlan;         //利润分配方案
    @Column(columnDefinition = "CLOB")
    private String hrInfo;                  //人事信息



    public PbCompany() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getProCode() {
        return proCode;
    }

    public void setProCode(String proCode) {
        this.proCode = proCode;
    }

    public String getOfficeSize() {
        return officeSize;
    }

    public void setOfficeSize(String officeSize) {
        this.officeSize = officeSize;
    }

    public String getOfficeAddress() {
        return officeAddress;
    }

    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
    }

    public long getOfficeRent() {
        return officeRent;
    }

    public void setOfficeRent(long officeRent) {
        this.officeRent = officeRent;
    }

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public long getRegisterCapital() {
        return registerCapital;
    }

    public void setRegisterCapital(long registerCapital) {
        this.registerCapital = registerCapital;
    }

    public String getStockHolder() {
        return stockHolder;
    }

    public void setStockHolder(String stockHolder) {
        this.stockHolder = stockHolder;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getSeniorManager() {
        return seniorManager;
    }

    public void setSeniorManager(String seniorManager) {
        this.seniorManager = seniorManager;
    }

    public String getSupervisors() {
        return supervisors;
    }

    public void setSupervisors(String supervisors) {
        this.supervisors = supervisors;
    }

    public String getEquityTransferAgree() {
        return equityTransferAgree;
    }

    public void setEquityTransferAgree(String equityTransferAgree) {
        this.equityTransferAgree = equityTransferAgree;
    }

    public String getRulePromise() {
        return rulePromise;
    }

    public void setRulePromise(String rulePromise) {
        this.rulePromise = rulePromise;
    }

    public String getProfitDistrPlan() {
        return profitDistrPlan;
    }

    public void setProfitDistrPlan(String profitDistrPlan) {
        this.profitDistrPlan = profitDistrPlan;
    }

    public String getHrInfo() {
        return hrInfo;
    }

    public void setHrInfo(String hrInfo) {
        this.hrInfo = hrInfo;
    }

    public String getOwnershipStruct() {
        return ownershipStruct;
    }

    public void setOwnershipStruct(String ownershipStruct) {
        this.ownershipStruct = ownershipStruct;
    }

    public String getOfficeImages() {
        return officeImages;
    }

    public void setOfficeImages(String officeImages) {
        this.officeImages = officeImages;
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
}
