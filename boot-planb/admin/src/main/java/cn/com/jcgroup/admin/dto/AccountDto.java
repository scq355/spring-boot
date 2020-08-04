package cn.com.jcgroup.admin.dto;

/**
 * Description: 台账（应收，应付，月度）
 * User: sunchangqing
 * Date: 2017-06-20
 * Time: 下午4:19
 */
public class AccountDto {
    private String contract_code;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContract_code() {
        return contract_code;
    }

    public void setContract_code(String contract_code) {
        this.contract_code = contract_code;
    }
}
