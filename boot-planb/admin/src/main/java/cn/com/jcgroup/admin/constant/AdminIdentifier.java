package cn.com.jcgroup.admin.constant;

/**
 * 系统常量
 * @author LiuYong on 17/6/3 下午3:41.
 */
public class AdminIdentifier {

    public static final String SESSION_USER = "user";

    public static final String USERNAME = "username";
    
    public static class Cron{
        
        //基金任务每天凌晨俩点执行
        public static final String SYN_PRIVATE_FUND = "0 0 2 * * *";
        //同步人员信息
        public static final String SYN_HUMAN_RESOUCE = "0 0 3 * * *";
    }
    
    public static class Image{
        
        //项目banner路径格式
        public static final String PROJECT_BANNER_PATH = "/project/{year}-{mon}-{day}/banner/{random}{.suffix}";
        //项目缩略图路径格式
        public static final String PROJECT_ABBR_PATH = "/project/{year}-{mon}-{day}/abbr/{random}{.suffix}";
        //合营公司股权架构
        public static final String COMPANY_GQJG_PATH = "/company/{year}-{mon}-{day}/gqjg/{random}{.suffix}";
    }
    
    public static class Url{
        
        /**************************项目相关**************************/
        //项目
        public static final String PROJECT = "/project";

        /**************************项目列表**************************/
        //项目列表
        public static final String PROJECT_LIST = "/list";
        //项目修改
        public static final String PROJECT_UPDATE = "/update";
        //项目查询
        public static final String PROJECT_SEARCH = "/search";
        //项目修改
        public static final String PROJECT_DELETE = "/delete";
        //项目新建
        public static final String PROJECT_CREATE = "/create";

        /**************************项目信息**************************/
        //激励情况（总金额）- 修改
        public static final String ENCOURAGE_TOTAL_UPDATE = "/info/encourageTotal/update";
        //激励情况（记录）- 修改
        public static final String ENCOURAGE_RECORD_UPDATE = "/info/encourageRecord/update";
        //激励情况 - 查询
        public static final String ENCOURAGE_QUERY = "/info/encourage/query";
        //激励情况 - 列表
        public static final String ENCOURAGE_LIST = "/info/encourage/list";
        //激励情况 - 列表
        public static final String ENCOURAGE_ADD= "/info/encourage/add";
        //激励情况 - 列表
        public static final String ENCOURAGE_DELETE= "/info/encourage/delete";

        //激励文件 - 上传
        public static final String ENCOURAGE_FILE_UPLOAD = "/info/encourageFile/upload";
        //激励文件 - 修改
        public static final String ENCOURAGE_FILE_UPDATE = "/info/encourageFile/update";
        //激励文件 - 列表
        public static final String ENCOURAGE_FILE_LIST = "/info/encourageFile/list";
        //资产形成 - 修改
        public static final String ASSET_UPDATE = "/info/asset/update";
        //资产形成 - 查询
        public static final String ASSET_QUERY = "/info/asset/query";
        //项目概况 - 修改
        public static final String PROJECT_OVERVIEW_UPDATE = "/info/overview/update";
        //项目概况 - 查询
        public static final String PROJECT_OVERVIEW_QUERY = "/info/overview/query";

        /**************************项目文档**************************/
        //文档
        public static final String DOCUMENT = "/document";
        //上传文档-待定
        public static final String DOCUMENT_UPLOAD = "/doc/upload";
        //图片库-修改
        public static final String DOCUMENT_POTO_UPDATE = "/photo/update";
        //图片库-查询
        public static final String DOCUMENT_PHOTO_LIST = "/photo/list";
        //文档库列表
        public static final String DOCUMENT_DOC_LIST = "/doc/list";
        //文档库-导出
        public static final String DOCUMENT_DOC_EXPORT = "/doc/export";
        //文档详情-修改
        public static final String DOCUMENT_DOC_DETAIL_UPDATE = "/doc/detail/update";
        //文档详情-查询
        public static final String DOCUMENT_DOC_DETAIL_QUERY = "/doc/detail/query";

        /**************************工程进度**************************/
        //工程
        public static final String SUB_PROJECT = "/project/subproject";
        //工程列表
        public static final String SUB_PROJECT_LIST = "/list";
        //新建工程
        public static final String SUB_PROJECT_CREATE = "/create";
        //工程修改
        public static final String SUB_PROJECT_UPDATE ="/update";
        //工程查询
        public static final String SUB_PROJECT_SEARCH ="/search";
        //工程删除
        public static final String SUB_PROJECT_DELETE ="/delete";

        //工程量-列表
        public static final String SUB_PROJECT_AMOUNT_LIST = "/amount/list";
        //工程量-修改
        public static final String SUB_PROJECT_AMOUNT_EDIT = "/amount/edit";
        //工程量-删除
        public static final String SUB_PROJECT_AMOUNT_DELETE = "/amount/delete";
        //工程量-添加
        public static final String SUB_PROJECT_AMOUNT_ADD = "/amount/add";

        //已付资金-列表
        public static final String SUB_PROJECT_PAID_AMOUNT_LIST = "/paid/amount/list";
        //已付资金-编辑
        public static final String SUB_PROJECT_PAID_AMOUNT_EDIT = "/paid/admont/edit";
        //已付资金-添加
        public static final String SUB_PROJECT_PAID_AMOUNT_ADD = "/paid/amount/add";
        //已付资金-删除
        public static final String SUB_PROJECT_PAID_AMOUNT_DELETE = "/paid/amount/delete";

        //应付台账-列表
        public static final String SUB_PROJECT_ACCOUNT_PAYFOR_LIST = "/account/payfor/list";
        //应付台账-编辑
        public static final String SUB_PROJECT_ACCOUNT_PAYFOR_EDIT= "/account/payfor/edit";
        //应付台账-删除
        public static final String SUB_PROJECT_ACCOUNT_PAYFOR_DELETE= "/account/payfor/delete";
        //应收台账-添加
        public static final String SUB_PROJECT_ACCOUNT_PAYAFOR_ADD = "/account/payfor/add";
        //应付台账-显示
        public static final String SUB_PROJECT_ACCOUNT_PAYFOR_SHOW = "/account/payfor/show";

        //应收台账-列表
        public static final String SUB_PROJECT_ACCOUNT_PAYABLE_LIST = "/account/payable/list";
        //应收台账-编辑
        public static final String SUB_PROJECT_ACCOUNT_PAYABLE_EDIT = "/account/payable/edit";
        //应收台账-添加
        public static final String SUB_PROJECT_ACCOUNT_PAYABLE_ADD = "/account/payable/add";
        //应收台账-删除
        public static final String SUB_PROJECT_ACCOUNT_PAYABLE_DELETE= "/account/payable/delete";
        //应付台账-显示
        public static final String SUB_PROJECT_ACCOUNT_PAYABLE_SHOW = "/account/payable/show";

        //月度台账-列表
        public static final String SUB_PROJECT_MONTHLY_ACCOUNT_LIST = "/monthly/account/list";
        //月度台账-编辑
        public static final String SUB_PROJECT_MONTHLY_ACCOUNT_EDIT = "/monthly/account/edit";
        //月度台账-删除
        public static final String SUB_PROJECT_MONTHLY_ACCOUNT_DELETE= "/monthly/account/delete";
        //月度台账-显示
        public static final String SUB_PROJECT_MONTHLY_ACCOUNT_SHOW = "/monthly/account/show";

        /**************************合营公司**************************/

        //合营公司
        public static final String COMPANY = "/company";
        //合营公司-列表
        public static final String COMPANY_LIST = "/list";
        //合营公司-删除
        public static final String COMPANY_DELETE = "/delete";
        //合营公司-创建
        public static final String COMPANY_CREATE = "/create";
        //合营公司-搜索
        public static final String COMPANY_SEARCH = "/search";
        //合营公司-编辑
        public static final String COMPANY_EDIT = "/edit";
        //合营公司概况-显示
        public static final String COMPANY_OVERVIEW_LIST = "/overview/list";
        //合营公司概况-编辑
        public static final String COMPANY_OVERVIEW_EDIT = "/overview/edit";

        //合营公司-人员-列表
        public static final String COMPANY_STAFF_LIST = "/staff/list";
        //合营公司-人员-编辑
        public static final String COMPANY_STAFF_EDIT = "/staff/update";
        //合营公司-人员-删除
        public static final String COMPANY_STAFF_DELETE = "/staff/delete";
        //合营公司-人员-添加
        public static final String COMPANY_STAFF_ADD = "/staff/add";

        //合营公司-机构-列表
        public static final String COMPANY_AGENCY_LIST = "/agency/list";
        //合营公司-机构-编辑
        public static final String COMPANY_AGENCY_EDIT = "/agency/edit";
        //合营公司-机构-删除
        public static final String COMPANY_AGENCY_DELETE = "/agency/delete";
        //合营公司-机构-添加
        public static final String COMPANY_AGENCY_ADD = "/agency/add";
        //合营公司-机构-单条信息-显示
        public static final String COMPANY_AGENCY_SHOW = "/agency/show";

        //合营公司-其他-列表
        public static final String COMPANY_OTHER_LIST = "/other/list";
        //合营公司-其他-编辑
        public static final String COMPANY_OTHER_EDIT = "/other/edit";
        //合营公司-其他-添加
        public static final String COMPANY_OTHER_ADD = "/other/add";
        //合营公司-其他-删除
        public static final String COMPANY_OTHER_DELETE = "/other/delete";
        //合营公司-其他-显示
        public static final String COMPANY_OTHER_SHOW = "/other/show";

        //合营公司-金融机构-列表
        public static final String COMPANY_FINANCE_AGENCY_LIST = "/finance/agency/list";
        //合营公司-金融机构-编辑
        public static final String COMPANY_FINANCE_AGENCY_EDIT = "/finance/agency/edit";
        //合营公司-金融机构-删除
        public static final String COMPANY_FINANCE_AGENCY_DELETE = "/finance/agency/delete";
        //合营公司-金融机构-添加
        public static final String COMPANY_FINANCE_AGENCY_ADD = "/finance/agency/add";
        //合营公司-金融机构-单条信息-显示
        public static final String COMPANY_FINANCE_AGENCY_SHOW = "/finance/agency/show";

        //合营公司-融资款-列表
        public static final String COMPANY_RZK_QUERY = "/rzk/query";
        //合营公司-融资款-编辑
        public static final String COMPANY_RZK_UPDATE = "/rzk/update";
        //合营公司-融资款-删除
        public static final String COMPANY_RZK_DELETE = "/rzk/delete";
        //合营公司-融资款-添加
        public static final String COMPANY_RZK_ADD = "/rzk/add";

        //合营公司-私募-列表
        public static final String COMPANY_PRIVATE_FUND_LIST = "/private/fund/list";
        //合营公司-私募-删除
        public static final String COMPANY_PRIVATE_FUND_DELETE = "/private/fund/delete";
        //合营公司-私募-删除
        public static final String COMPANY_PRIVATE_FUND_SHOW = "/private/fund/show";
        //合营公司-私募-删除
        public static final String COMPANY_PRIVATE_FUND_UPDATE = "/private/fund/update";

        /**********************人才管理模块****************************/
        //员工
        public static final String STAFF ="/staff";
        //员工修改
        public static final String STAFF_INFO_UPDATE ="/info/update";
        //员工列表
        public static final String STAFF_INFO_LIST ="/info/list";
        //员工同步
        public static final String STAFF_INFO_SYNCHRONIZE ="/info/synchronize";

        /**********************业务分析模块****************************/
        //业务
        public static final String BUSINESS ="/business";
        //出差列表
        public static final String  BUSINESS_TRAVEL_LIST="/travel/list";
        //出差或接待列表修改
        public static final String BUSINESS_LIST_UPDATE ="/list/update";
        //出差或接待明细
        public static final String BUSINESS_DETAIL_QUERY ="/detail/query";
        //出差或接待明细修改
        public static final String BUSINESS_DETAIL_UPDATE ="/detail/update";
        
        //出差接待数据同步
        public static final String BUSINESS_SYNCHRONIZE ="/synchronize";
        
        //接待列表
        public static final String  BUSINESS_RECEPTION_LIST="/reception/list";
        

        /**********************定时任务****************************/
        //基金同步
        public static final String SYN_FUND ="/synFund";
        //人员信息同步
        public static final String SYN_STAFF ="/synStaff";
        //人员异动信息同步
        public static final String SYN_STAFF_FLOW ="/synStaffFlowInfo";
    }
}
