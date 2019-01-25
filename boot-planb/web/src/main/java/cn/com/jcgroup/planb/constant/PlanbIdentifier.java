package cn.com.jcgroup.planb.constant;

/**
 * 一些常量
 *
 * @author LiuYong on 17/5/26 下午4:23.
 */
public class PlanbIdentifier {

    public static final String SESSION_USER = "user";

    public static final String USERNAME = "username";

    /**
     * url常量定义
     */
    public static class Url {
        //登录
        public static final String LOGIN = "/login";
        //登出
        public static final String LOGOUT = "/logout";
        //springmvc错误默认跳转URL
        public static final String ERROR = "/error";

        /**********************项目****************************/
        //项目
        public static final String PROJECT= "/project";
        //项目地图
        public static final String PROJECT_MAP = "/map";
        //项目概览
        public static final String PROJECT_OVERVIEW = "/overview";
        //激励情况
        public static final String PROJECT_TOTAL_ENCOURAGE = "/totalEncourage";
        //资产形成
        public static final String PROJECT_TOTAL_ASSET_PERCENT = "/totalAssetPercent";
        //机构募集&私募募集&今年新增
        public static final String PROJECT_STATISTIC = "/statistic";
        //按状态进行项目统计
        public static final String PROJECT_STATUS_COUNT = "/statusCount";
        //里程碑
        public static final String PROJECT_MILESTONE  = "/milestone";


        /**********************项目概览****************************/
        //合营公司信息
        public static final String PROJECT_COMPANY_INFO = "/xmgl/companyInfo";
        //合营公司列表
        public static final String PROJECT_COMPANY_LIST = "/xmgl/companyList";
        //项目基本信息
        public static final String PROJECT_BASIC_INFO = "/xmgl/basicinfo";
        //项目激励情况
        public static final String PROJECT_ENCOURAGE = "/xmgl/encourage";
        //项目相关文件
        public static final String PROJECT_FILE = "/xmgl/file";
        //项目Banner图
        public static final String PROJECT_BANNER = "/bannerList";

        /**********************资金流入明细****************************/
        //金融机构
        public static final String FINANCE_AGENCY = "/cash/financeAgency";
        //私募基金数据
        public static final String FINANCE_PRIVATE_FUND = "/cash/privateFund";
        //融资款汇总
        public static final String FINANCE_SUMMARY = "/cash/summary";
        //机构
        public static final String AGENCY_LIST = "/cash/agencyList";
        //机构&其他
        public static final String AGENCY_AND_OTHER = "/cash/agency";


        /**********************工程进度****************************/
        //完成情况
        public static final String SUB_PROJECT_PROCESS = "/subProject/process";
        //工程列表
        public static final String SUB_PROJECT_LIST = "/subProjectList";
        //已支付资金流水
        public static final String PAID_FLOW = "/subProject/paidFlow";
        //已支付资金统计
        public static final String TOTAL_PAID = "/subProject/totalPaid";
        //应付帐台
        public static final String ACCOUNT_PAYFOR = "/subProject/accountsPayable";
        //应收账台
        public static final String ACCOUNT_PAYABLE = "/subProject/accountsReceive";
        //月度账台
        public static final String MONTHLY_ACCOUNT = "/subProject/monthlyAccount";

        /**********************资金进度****************************/
        //资金进度
        public static final String CAPITAL_PROGRESS = "/project/zjjd";
        //财务报表
        public static final String FINANCE_REPORT = "/report";
        //资产形成
        public static final String ASSET_COMPOSTION = "/assetCompostion";
        //资金流   
        public static final String CASH_FLOW = "/cashflow";
        //资金流明细
        public static final String CASH_FLOW_HOVER = "/cashFlowHover";

        /**********************人才流动****************************/

        //人员流动
        public static final String STAFF_FLOW = "/staff/flow";
        //基本信息
        public static final String STAFF_BASIC_INFO = "/staff/basicInfo";
        //薪资年限
        public static final String ANNUAL_SALARY = "/staff/annualSalary";
        //部门列表
        public static final String DEPARTMENT_LIST = "/department/list";

        /**********************业务概览****************************/
        //业务
        public static final String BUSINESS = "/business";
        //出差
        public static final String TRAVEL = "/travel";
        //接待
        public static final String RECEPTION = "/reception";
        //数据报表(出差)
        public static final String REPORT_TRAVEL = "/report/travel";
        //数据报表(接待)
        public static final String REPORT_RECEPTION = "/report/reception";
        //出差接待top10条数据
        public static final String MAP_TOPTEN = "/map/topTen";
        //出差接待地图－点
        public static final String MAP_POINT = "/map/point";
        //出差接待地图－线
        public static final String MAP_LINE = "/map/line";

    }
    
    public static class Regex{
        //日期 eg: yyyy.MM
        public static final String YEAR_DOT_MONTH = "^(?:19|20|21)[0-9][0-9].(?:(?:0[1-9])|(?:1[0-2]))$";
        //1或2
        public static final String ONE_OR_TWO = "^[1-2]$";
        //非负整数
        public static final String NON_NEGATIVE_NUMBER = "^\\d+$";
    }

}
