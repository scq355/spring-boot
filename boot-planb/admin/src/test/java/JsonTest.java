import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Description: JSON
 * User: sunchangqing
 * Date: 2017-06-27
 * Time: 下午11:36
 */
public class JsonTest {

    private static final Logger log = LoggerFactory.getLogger(JsonTest.class);

    /**
     * json数组遍历
     */
    @Test
    public void testJsonString() {
        String jsonStr  = "[{\"productIssueScale\":3000000,\"establishDate\":\"2015-07-14\",\"productName\":\"第1期\"}]";
        List<HashMap> list = JSON.parseArray(jsonStr, HashMap.class);
        for (HashMap map : list) {
            Iterator iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                Object key = entry.getKey();
                Object value = entry.getValue();
                log.info("key : " + key.toString() + "value : " + value.toString());
            }
            map.get("productIssueScale");
        }
    }

    @Test
    public void testToJsonString() {
        String key_1 = "productIssueScale";
        String value_1 = "3000000";

        String key_2 = "establishDate";
        String value_2 = "2015-07-14";

        String key_3 = "productName";
        String value_3 = "第1期";

        Map<String, String> map = new HashMap<>();
        map.put(key_1, value_1);
        map.put(key_2, value_2);
        map.put(key_3, value_3);

        log.info(map.toString());
    }
}
