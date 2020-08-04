package cn.com.jcgroup.admin.manage;

import cn.com.jcgroup.admin.dto.AccountPayAbleDto;
import cn.com.jcgroup.admin.dto.AccountPayForDto;
import cn.com.jcgroup.service.domain.PbBillPayAble;
import cn.com.jcgroup.service.service.SubProjectService;
import cn.com.jcgroup.service.util.DateUtil;
import cn.com.jcgroup.service.util.NumberUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;

import java.util.Date;

/**
 * Description: 账单（应收台账，应付台账，月度台账）
 * User: sunchangqing
 * Date: 2017-06-19
 * Time: 下午9:40
 */
@Service
public class AccountManage {

    @Autowired
    private SubProjectService subProjectService;

    /**
     * 根据id获取月度台账信息
     */
    public JSONObject monthlyAccountShow(int id) {
        JSONObject jsonObject = subProjectService.queryMonthPaper(id);
        JSONObject resObj = new JSONObject();
        if (jsonObject != null) {
            resObj.put("sub_pro_code", jsonObject.getString("subProCode"));
            resObj.put("paper_name", jsonObject.getString("paperName"));
            resObj.put("paper_link", jsonObject.getString("paperLink"));
            resObj.put("paper_ext", jsonObject.getString("paperExt"));
            String createTime = DateUtil.formatDate("yyyy.MM.dd", (Date) jsonObject.get("createTime"));
            resObj.put("create_time", createTime);
            String isShwow = jsonObject.getString("isShow");
            if (StringUtils.isNotBlank(isShwow) && isShwow.equals("1")) {
                resObj.put("is_show", true);
            } else {
                resObj.put("is_show", false);
            }
            return resObj;
        }
        return null;

    }

    /**
     * 应付台账更新
     */
    public void accountPayforEdit(AccountPayForDto accountPayForDto) {
        if (accountPayForDto != null) {
            JSONObject accountPayForObj = new JSONObject();
            Long contractMoney = NumberUtil.convertToPointWithUnit(accountPayForDto.getContract_money(), 1L);
            accountPayForObj.put("contractMoney", contractMoney);
            Long unpaidAmount = NumberUtil.convertToPointWithUnit(accountPayForDto.getUnpaid_amount(), 1L);
            accountPayForObj.put("unpaidAmount", unpaidAmount);
            Long settleAmount = NumberUtil.convertToPointWithUnit(accountPayForDto.getSettle_accounts(), 1L);
            accountPayForObj.put("settleAccount", settleAmount);
            Long payMoney = NumberUtil.convertToPointWithUnit(accountPayForDto.getPay_money(), 1L);
            accountPayForObj.put("payMoney", payMoney);
            Long invoiceAmount = NumberUtil.convertToPointWithUnit(accountPayForDto.getInvoice_amount(), 1L);
            accountPayForObj.put("invoiceAmount", invoiceAmount);

            accountPayForObj.put("expectedEndTime", accountPayForDto.getExpected_end_time());
            Date payTime = DateUtil.parseDate(accountPayForDto.getPay_time(),"yyyy.MM.dd");
            accountPayForObj.put("payTime", payTime);
            Date signTime = DateUtil.parseDate(accountPayForDto.getSign_time(), "yyyy.MM.dd");
            accountPayForObj.put("signTime", signTime);

            accountPayForObj.put("otherInfo", accountPayForDto.getRemark());
            accountPayForObj.put("contractName", accountPayForDto.getContract_name());
            accountPayForObj.put("pricingType", accountPayForDto.getValuation_type());
            accountPayForObj.put("contractCode", accountPayForDto.getContract_code());
            Long accumulatedPayable = NumberUtil.convertToPointWithUnit(accountPayForDto.getAgreement_money(), 1L);
            accountPayForObj.put("accumulatedPayable", accumulatedPayable);
            accountPayForObj.put("cooperA", accountPayForDto.getCooper_a());
            accountPayForObj.put("cooperB", accountPayForDto.getCooper_b());
            accountPayForObj.put("cooperC", accountPayForDto.getCooper_c());
            if (accountPayForDto.isIs_show()) {
                accountPayForObj.put("isShow", "1");
            } else {
                accountPayForObj.put("isShow", "0");
            }
            accountPayForObj.put("subProCode", accountPayForDto.getSub_pro_code());
            accountPayForObj.put("summaryContract", accountPayForDto.getSummary_contract());
            accountPayForObj.put("id", accountPayForDto.getId());
            subProjectService.editAccountPayFor(accountPayForObj);
        }
    }

    /**
     * 应收台账更新
     */
    public JSONObject accountPayableEdit(AccountPayAbleDto accountPayAbleDto) {
        JSONObject accountPayAbleObj = new JSONObject();
        if (accountPayAbleDto != null) {

            Long amountPaid = NumberUtil.convertToPointWithUnit(accountPayAbleDto.getAmount_paid(), 1L);
            accountPayAbleObj.put("amountPaid", amountPaid);
            Long agreementMoney = NumberUtil.convertToPointWithUnit(accountPayAbleDto.getAgreement_money(), 1L);
            accountPayAbleObj.put("agreementMoney", agreementMoney);
            Long contractMoney = NumberUtil.convertToPointWithUnit(accountPayAbleDto.getContract_money(), 1L);
            accountPayAbleObj.put("contractMoney", contractMoney);
            Long payMoney = NumberUtil.convertToPointWithUnit(accountPayAbleDto.getPay_money(), 1L);
            accountPayAbleObj.put("payMoney", payMoney);

            Date signTime = DateUtil.parseDate(accountPayAbleDto.getSign_time(), "yyyy.MM.dd");
            accountPayAbleObj.put("signTime", signTime);

            Date payTime = DateUtil.parseDate(accountPayAbleDto.getPay_time(), "yyyy.MM.dd");
            accountPayAbleObj.put("payTime", payTime);

            Date realAmountPaid = DateUtil.parseDate(accountPayAbleDto.getPayable_time(), "yyyy.MM.dd");
            accountPayAbleObj.put("realAmountPaid", realAmountPaid);

            if (accountPayAbleDto.isIs_show()) {
                accountPayAbleObj.put("isShow", "1");
            } else {
                accountPayAbleObj.put("isShow", "0");
            }
            accountPayAbleObj.put("id", accountPayAbleDto.getId());

            accountPayAbleObj.put("cooperationPeriod", accountPayAbleDto.getCooperation_period());
            if (accountPayAbleDto.isInvoice_provide()) {
                accountPayAbleObj.put("invoiceProvide", "1");
            } else {
                accountPayAbleObj.put("invoiceProvide", "0");
            }

            accountPayAbleObj.put("otherInfo", accountPayAbleDto.getOther_info());
            accountPayAbleObj.put("payableDetails", accountPayAbleDto.getPayable_details());
            accountPayAbleObj.put("payableNode", accountPayAbleDto.getPayable_node());
            accountPayAbleObj.put("subProCode", accountPayAbleDto.getSub_pro_code());
            accountPayAbleObj.put("summaryContract", accountPayAbleDto.getSummary_contract());
            accountPayAbleObj.put("contractCode", accountPayAbleDto.getContract_code());
            accountPayAbleObj.put("contractName", accountPayAbleDto.getContract_name());
            accountPayAbleObj.put("cooperA", accountPayAbleDto.getCooper_a());
            accountPayAbleObj.put("cooperB", accountPayAbleDto.getCooper_b());
            accountPayAbleObj.put("payableWay", accountPayAbleDto.getPayable_way());
            accountPayAbleObj.put("payableItem", accountPayAbleDto.getPayable_item());
            subProjectService.editAccountPayAble(accountPayAbleObj);
        }
        return null;
    }

    /**
     * 月度台账列表
     */
    public JSONArray monthlyAccountList(String subProCode, int page) {
        JSONArray resArray = new JSONArray();
        JSONArray monthlyAccountArray = subProjectService.queryMonthPaperBySubProCode(subProCode);
        if (monthlyAccountArray != null) {
            int monthAccountNum = monthlyAccountArray.size();
            for (int i = 0; i < monthAccountNum; i++) {
                JSONObject jsonObject = new JSONObject();
                JSONObject monthPaperObj = monthlyAccountArray.getJSONObject(i);
                if (monthPaperObj != null) {
                    String yearMonth = DateUtil.formatDate("yyyy.MM.dd", (Date) monthPaperObj.get("createTime"));
                    jsonObject.put("year_month", yearMonth);
                    String isShow = monthPaperObj.getString("isShow");
                    if (StringUtils.isNotBlank(isShow) && isShow.equals("1")) {
                        jsonObject.put("is_show", true);
                    } else {
                        jsonObject.put("is_show", false);
                    }
                    jsonObject.put("paper_ext", monthPaperObj.getString("paperExt"));
                    jsonObject.put("paper_name", monthPaperObj.getString("paperName"));
                    jsonObject.put("id", monthPaperObj.getString("id"));
                    resArray.add(jsonObject);
                }
            }
        }
        return resArray;
    }

    /**
     * 添加应收台账
     */
    public PbBillPayAble accountPayableAdd(AccountPayAbleDto accountPayAbleDto) {
        JSONObject accountPayAbleObj = new JSONObject();
        if (accountPayAbleDto != null) {
            //元->分
            Long amountPaid = NumberUtil.convertToPointWithUnit(accountPayAbleDto.getAmount_paid(), 1L);
            accountPayAbleObj.put("amountPaid", amountPaid);
            //元->分
            Long agreementMoney = NumberUtil.convertToPointWithUnit(accountPayAbleDto.getAgreement_money(), 1L);
            accountPayAbleObj.put("agreementMoney", agreementMoney);
            //元->分
            Long contarctMoney = NumberUtil.convertToPointWithUnit(accountPayAbleDto.getContract_money(), 1L);
            accountPayAbleObj.put("contractMoney", contarctMoney);
            //元->分
            Long payMoney = NumberUtil.convertToPointWithUnit(accountPayAbleDto.getPay_money(), 1L);
            accountPayAbleObj.put("payMoney", payMoney);

            accountPayAbleObj.put("contractCode", accountPayAbleDto.getContract_code());
            accountPayAbleObj.put("contractName", accountPayAbleDto.getContract_name());
            accountPayAbleObj.put("cooperA", accountPayAbleDto.getCooper_a());
            accountPayAbleObj.put("cooperB", accountPayAbleDto.getCooper_b());
            accountPayAbleObj.put("payableWay", accountPayAbleDto.getPayable_way());
            accountPayAbleObj.put("payableItem", accountPayAbleDto.getPayable_item());
            Date payTime = DateUtil.parseDate(accountPayAbleDto.getPay_time(), "yyyy.MM.dd");
            accountPayAbleObj.put("payTime", payTime);
            if (accountPayAbleDto.isIs_show()) {
                accountPayAbleObj.put("isShow", "1");
            } else {
                accountPayAbleObj.put("isShow", "0");
            }
            Date signTime = DateUtil.parseDate(accountPayAbleDto.getSign_time(), "yyyy.MM.dd");
            accountPayAbleObj.put("signTime", signTime);
            accountPayAbleObj.put("cooperationPeriod", accountPayAbleDto.getCooperation_period());
            boolean invoiceProvide = accountPayAbleDto.isInvoice_provide();
            if (invoiceProvide) {
                accountPayAbleObj.put("invoiceProvide", "1");
            } else {
                accountPayAbleObj.put("invoiceProvide", "0");
            }
            accountPayAbleObj.put("otherInfo", accountPayAbleDto.getOther_info());
            accountPayAbleObj.put("payableDetails", accountPayAbleDto.getPayable_details());
            accountPayAbleObj.put("payableNode", accountPayAbleDto.getPayable_node());
            Date realAmountPaid = DateUtil.parseDate(accountPayAbleDto.getPayable_time(), "yyyy.MM.dd");
            accountPayAbleObj.put("realAmountPaid", realAmountPaid);
            accountPayAbleObj.put("subProCode", accountPayAbleDto.getSub_pro_code());
            accountPayAbleObj.put("summaryContract", accountPayAbleDto.getSummary_contract());
            return subProjectService.insertBillPayable(accountPayAbleObj);
        }
        return null;
    }

    /**
     * 添加应付台账
     */
    public String accountPayforAdd(AccountPayForDto accountPayForDto) {
        if (accountPayForDto != null) {
            JSONObject accountPayForObj = new JSONObject();
            accountPayForObj.put("subProCode", accountPayForDto.getSub_pro_code());
            Date signTime = DateUtil.parseDate(accountPayForDto.getSign_time(), "yyyy.MM.dd");
            accountPayForObj.put("signTime", signTime);

            accountPayForObj.put("otherInfo", accountPayForDto.getRemark());
            accountPayForObj.put("contractName", accountPayForDto.getContract_name());
            accountPayForObj.put("pricingType", accountPayForDto.getValuation_type());
            accountPayForObj.put("settleAccount", accountPayForDto.getSettle_accounts());
            accountPayForObj.put("contractCode", accountPayForDto.getContract_code());
            accountPayForObj.put("cooperA", accountPayForDto.getCooper_a());
            accountPayForObj.put("cooperB", accountPayForDto.getCooper_b());
            accountPayForObj.put("cooperC", accountPayForDto.getCooper_c());
            accountPayForObj.put("expectedEndTime", accountPayForDto.getExpected_end_time());

            Long contarctMoney = NumberUtil.convertToPointWithUnit(accountPayForDto.getContract_money(), 1L);
            accountPayForObj.put("contractMoney", contarctMoney);
            Long unpaidAmount = NumberUtil.convertToPointWithUnit(accountPayForDto.getUnpaid_amount(), 1L);
            accountPayForObj.put("unpaidAmount", unpaidAmount);
            Long payMoney = NumberUtil.convertToPointWithUnit(accountPayForDto.getPay_money(), 1L);
            accountPayForObj.put("payMoney", payMoney);
            Long invoceAmount = NumberUtil.convertToPointWithUnit(accountPayForDto.getInvoice_amount(), 1L);
            accountPayForObj.put("invoiceAmount", invoceAmount);
            Long agreementMoney = NumberUtil.convertToPointWithUnit(accountPayForDto.getAgreement_money(), 1L);
            accountPayForObj.put("accumulatedPayable", agreementMoney);
            Date payTime = DateUtil.parseDate(accountPayForDto.getPay_time(), "yyyy.MM.dd");
            accountPayForObj.put("payTime", payTime);

            if (accountPayForDto.isIs_show()) {
                accountPayForObj.put("isShow", "1");
            } else {
                accountPayForObj.put("isShow", "0");
            }
            accountPayForObj.put("summaryContract", accountPayForDto.getSummary_contract());
            return subProjectService.insertBillPayfor(accountPayForObj);
        }
        return null;
    }


    /**
     * 根据ID显示应付台账信息
     */
    public JSONObject accountPayforInfoShow(int id) {
        JSONObject jsonObject = subProjectService.queryBillPayforByContractCode(id);
        JSONObject resObject = new JSONObject();
        if (jsonObject != null) {

            Long contractMoneyLongForm = (Long) jsonObject.get("contractMoney");
            if (contractMoneyLongForm != null) {
                String contractMoney = NumberUtil.unitMoney(contractMoneyLongForm);
                resObject.put("contract_money", contractMoney);
            }

            Long invoiceAmountLongForm = (Long) jsonObject.get("invoiceAmount");
            if (invoiceAmountLongForm != null) {
                String invoiceAmount = NumberUtil.unitMoney(invoiceAmountLongForm);
                resObject.put("invoice_amount", invoiceAmount);
            }

            Long payMoneyLongFormed = (Long) jsonObject.get("payMoney");
            if (payMoneyLongFormed != null) {
                String payMoney = NumberUtil.unitMoney(payMoneyLongFormed);
                resObject.put("pay_money", payMoney);
            }

            Long unpaidMoneyLongForm = (Long) jsonObject.get("unpaidAmount");
            if (unpaidMoneyLongForm != null) {
                String unpaidMoney = NumberUtil.unitMoney(unpaidMoneyLongForm);
                resObject.put("unpaid_amount", unpaidMoney);
            }

            Long settleAccountLongFprm = (Long) jsonObject.get("settleAccount");
            if (settleAccountLongFprm != null) {
                String settleAccount = NumberUtil.unitMoney(settleAccountLongFprm);
                resObject.put("settle_accounts", settleAccount);
            }

            Long accumulatedPayableLongFormed = (Long) jsonObject.get("accumulatedPayable");
            if (accumulatedPayableLongFormed != null) {
                String accumulatedPayable = NumberUtil.unitMoney(accumulatedPayableLongFormed);
                resObject.put("agreement_money", accumulatedPayable);

            }

            resObject.put("expected_end_time", jsonObject.getString("expectedEndTime"));

            String payTimeString = DateUtil.formatDate("yyyy.MM.dd", (Date) jsonObject.get("payTime"));
            resObject.put("pay_time", payTimeString);

            String signTimeString = DateUtil.formatDate("yyyy.MM.dd", (Date) jsonObject.get("signTime"));
            resObject.put("sign_time", signTimeString);

            resObject.put("contract_code", jsonObject.getString("contractCode"));
            resObject.put("contract_name", jsonObject.getString("contractName"));
            resObject.put("cooper_a", jsonObject.getString("cooperA"));
            resObject.put("cooper_b", jsonObject.getString("cooperB"));
            resObject.put("cooper_c", jsonObject.getString("cooperC"));
            resObject.put("remark", jsonObject.getString("otherInfo"));
            resObject.put("valuation_type", jsonObject.getString("pricingType"));
            resObject.put("sub_pro_code", jsonObject.getString("subProCode"));
            resObject.put("summary_contract", jsonObject.getString("summaryContract"));
            resObject.put("id", jsonObject.getString("id"));
            String isShow = jsonObject.getString("isShow");
            if (StringUtils.isNotBlank(isShow) && isShow.equals("1")) {
                resObject.put("is_show", true);
            } else {
                resObject.put("is_show", false);
            }
        }
        return resObject;
    }

    /**
     * 根据ID显示应收台账信息
     */
    public JSONObject accountPayableInfoShow(int id) {
        JSONObject jsonObject = subProjectService.queryBillPayableByContractCode(id);
        JSONObject resObject = new JSONObject();
        if (jsonObject != null) {

            Long agreementMoneyLongForm = (Long) jsonObject.get("agreementMoney");
            if (agreementMoneyLongForm != null) {
                String agreementMoney = NumberUtil.unitMoney(agreementMoneyLongForm);
                resObject.put("agreement_money", agreementMoney);
            }

            Date payDate = (Date) jsonObject.get("payTime");
            if (payDate != null) {
                String payDateStr = DateUtil.formatDate("yyyy.MM.dd", payDate);
                resObject.put("pay_time", payDateStr);
            }

            Long payMoneyLongForm = Long.parseLong(jsonObject.getString("payMoney"));
            if (payMoneyLongForm != null) {
                String payMoney = NumberUtil.unitMoney(payMoneyLongForm);
                resObject.put("pay_money", payMoney);
            }

            Long contractMoneyLongForm = (Long) jsonObject.get("contractMoney");
            if (contractMoneyLongForm != null) {
                String contractMoney = NumberUtil.unitMoney(contractMoneyLongForm);
                resObject.put("contract_money", contractMoney);
            }

            String isShow = jsonObject.getString("isShow");
            if (StringUtils.isNotBlank(isShow) && isShow.equals("1")) {
                resObject.put("is_show", true);
            } else {
                resObject.put("is_show", false);
            }

            Date signDate = (Date) jsonObject.get("signTime");
            if (signDate != null) {
                String signTimeStr = DateUtil.formatDate("yyyy.MM.dd", signDate);
                resObject.put("sign_time", signTimeStr);
            }

            resObject.put("amount_paid", jsonObject.getString("amountPaid"));
            resObject.put("contract_name", jsonObject.getString("contractName"));
            resObject.put("contract_code", jsonObject.getString("contractCode"));
            resObject.put("cooper_a", jsonObject.getString("cooperA"));
            resObject.put("cooper_b", jsonObject.getString("cooperB"));
            resObject.put("payable_way", jsonObject.getString("payableWay"));
            resObject.put("payable_item", jsonObject.getString("payAbleItem"));
            resObject.put("cooperation_period", jsonObject.getString("cooperationPeriod"));
            String invoiceProvide = jsonObject.getString("invoiceProvide");
            if (StringUtils.isNotBlank(invoiceProvide) && invoiceProvide.equals("1")) {
                resObject.put("invoice_provide", true);
            } else {
                resObject.put("invoice_provide", false);
            }
            resObject.put("other_info", jsonObject.getString("otherInfo"));
            resObject.put("payable_details", jsonObject.getString("payableDetails"));
            resObject.put("payable_node", jsonObject.getString("payableNode"));
            resObject.put("payable_time", jsonObject.getString("realAmountPaid"));
            resObject.put("summary_contract", jsonObject.getString("summaryContract"));
        }
        return resObject;
    }

    /**
     * 根据合同编号删除应付台账
     */
    public void accountPayforDelete(int id) {
        subProjectService.billPayForDelete(id);
    }

    /**
     * 根据合同编号删除应收台账
     */
    public void accountPayableDelete(int id) {
        subProjectService.billPayAbleDelete(id);
    }

    /**
     * 根据工程编码获取应付台账
     */
    public JSONArray accountPayforList(String subProCode, int page) {
        JSONArray resArray = new JSONArray();
        JSONArray accountPayforArray = subProjectService.queryBillPayforBySubProCode(subProCode);
        if (accountPayforArray != null && !(accountPayforArray.isEmpty())) {
            int accountPayforNum = accountPayforArray.size();
            for (int i = 0; i < accountPayforNum; i++) {
                JSONObject tempAccount = new JSONObject();
                JSONObject jsonObject = accountPayforArray.getJSONObject(i);
                if (jsonObject != null) {
                    tempAccount.put("id", jsonObject.getString("id"));
                    tempAccount.put("contract_code", jsonObject.getString("contractCode"));
                    tempAccount.put("contract_name", jsonObject.getString("contractName"));
                    tempAccount.put("cooper_a", jsonObject.getString("cooperA"));
                    tempAccount.put("cooper_b", jsonObject.getString("cooperB"));
                    tempAccount.put("summary_contract", jsonObject.getString("summaryContract"));

                    Long contractMoneyLongForm = (Long) jsonObject.get("contractMoney");
                    if (contractMoneyLongForm != null) {
                        String contractMoney = NumberUtil.unitMoney(contractMoneyLongForm);
                        tempAccount.put("contract_money", contractMoney);
                    }

                    Long payMoneyLongFormed = (Long) jsonObject.get("payMoney");
                    if (payMoneyLongFormed != null) {
                        String payMoney = NumberUtil.unitMoney(payMoneyLongFormed);
                        tempAccount.put("pay", payMoney);
                    }

                    Date payDate = (Date) jsonObject.get("payTime");
                    if (payDate != null) {
                        String payDateStr = DateUtil.formatDate("yyyy.MM.dd", payDate);
                        tempAccount.put("pay_time", payDateStr);
                    }

                    String isShow = jsonObject.getString("isShow");
                    if (StringUtils.isNotBlank(isShow) && isShow.equals("1")) {
                        tempAccount.put("is_show", true);
                    } else {
                        tempAccount.put("is_show", false);
                    }
                    resArray.add(tempAccount);
                }
            }
        }
        return resArray;
    }

    /**
     * 根据工程编码获取应收台账
     */
    public JSONArray accountPayableList(String subProCode, int page) {
        JSONArray resArray = new JSONArray();
        JSONArray accountPayableArray = subProjectService.queryPayabelByPySubProCode(subProCode);
        if (accountPayableArray != null && !(accountPayableArray.isEmpty())) {
            int accountPayforNum = accountPayableArray.size();
            for (int i = 0; i < accountPayforNum; i++) {
                JSONObject tempAccount = new JSONObject();
                JSONObject jsonObject = accountPayableArray.getJSONObject(i);
                if (jsonObject != null) {
                    tempAccount.put("id", jsonObject.getString("id"));
                    tempAccount.put("contract_code", jsonObject.getString("contractCode"));
                    tempAccount.put("contract_name", jsonObject.getString("contractName"));

                    Long contractMoneyLongForm = (Long) jsonObject.get("contractMoney");
                    if (contractMoneyLongForm != null) {
                        String contractMoney = NumberUtil.unitMoney(contractMoneyLongForm);
                        tempAccount.put("contract_money", contractMoney);
                    }

                    Long agreementMoneyLongForm = (Long) jsonObject.get("agreementMoney");
                    if (agreementMoneyLongForm != null) {
                        String agreementMoney = NumberUtil.unitMoney(agreementMoneyLongForm);
                        tempAccount.put("agreement_money", agreementMoney);
                    }

                    Long payMoneyLongForm = Long.parseLong(jsonObject.getString("payMoney"));
                    if (payMoneyLongForm != null) {
                        String payMoney = NumberUtil.unitMoney(payMoneyLongForm);
                        tempAccount.put("pay_money", payMoney);
                    }
                    Long ampuntPaidLongForm = Long.parseLong(jsonObject.getString("amountPaid"));
                    if (ampuntPaidLongForm != null) {
                        String ampuntPaid =NumberUtil.unitMoney(ampuntPaidLongForm);
                        tempAccount.put("amount_paid", ampuntPaid);
                    }
                    tempAccount.put("cooper_a", jsonObject.getString("cooperA"));
                    tempAccount.put("cooper_b", jsonObject.getString("cooperB"));
                    tempAccount.put("pay_able_item", jsonObject.getString("payAbleItem"));
                    String isShow = jsonObject.getString("isShow");
                    if (StringUtils.isNotBlank(isShow) && isShow.equals("1")) {
                        tempAccount.put("is_show", true);
                    } else {
                        tempAccount.put("is_show", false);
                    }
                    resArray.add(tempAccount);
                }
            }
        }
        return resArray;
    }
}
