package jcgroup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

public class JsonTest {

    @Test
    public void testJsonArray() {
        String jsonArray = "[{\"productIssueScale\":25600000.0,\"establishDate\":\"2015-10-21\",\"productName\":\"第1期\"},{\"productIssueScale\":21500000.0,\"establishDate\":\"2015-10-29\",\"productName\":\"第2期\"},{\"productIssueScale\":0.0,\"establishDate\":\"\",\"productName\":\"第3期\"}]";
//
//        JSONArray array = JSON.parseArray(jsonArray);
//
//        System.out.println(array.getJSONObject(0));
//        System.out.println(array.getJSONObject(1));
//        System.out.println(array.getJSONObject(2));
//        JSONArray resArray = new JSONArray();

//        for (int i = 0; i <  array.size(); i++) {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("amount", array.getJSONObject(i).getString("productIssueScale"));
//            jsonObject.put("num_periods", array.getJSONObject(i).getString("productName"));
//            jsonObject.put("time", array.getJSONObject(i).getString("establishDate"));
//
//            resArray.add(jsonObject);
//        }

//        for (int i = 0; i <  resArray.size(); i++) {
//            System.out.println(resArray.getJSONObject(i).toString());
//        }
//
        String otherStr = "[{\"name\": \"2015-10-21\",\"value\": \"第1期\"},{\"name\": \"2015-10-29\",\"value\": \"第2期\"},{\"name\": \"三个\",\"value\": \"第3期\"}]";
        JSONArray otherArray = JSON.parseArray(otherStr);
        JSONArray resArray = new JSONArray();
        for (int i = 0; i <  otherArray.size(); i++) {
            JSONObject otherObject = new JSONObject();
            otherObject.put("name", otherArray.getJSONObject(i).getString("name"));
            otherObject.put("value", otherArray.getJSONObject(i).getString("value"));
            resArray.add(otherObject);
        }
        for (int i = 0; i <  resArray.size(); i++) {
            System.out.println(resArray.getJSONObject(i).toString());
        }

    }
}
