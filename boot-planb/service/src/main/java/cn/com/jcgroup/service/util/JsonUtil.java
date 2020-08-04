package cn.com.jcgroup.service.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

/**
 * 关于json工具类
 *
 * @author LiuYong on 17/6/5 下午5:03.
 */
public class JsonUtil {

    /**
     * 初始化容量为{initialCapacity},数值为{value}的JsonArray
     *
     * @author LiuYong
     */
    public static JSONArray initJsonArray(int initialCapacity, Object value) {
        ArrayList arrayList = new ArrayList(initialCapacity);
        for (int i = 0; i < initialCapacity; i++) {
            arrayList.add(value);
        }
        return new JSONArray(arrayList);
    }
    
    /**
     * 对元素为jsonObject的json array进行深度克隆
     * @author LiuYong  
     */
    public static JSONArray deepClone(JSONArray jsonArray){
        JSONArray cloneArray = null;
        if(jsonArray != null){
            cloneArray = new JSONArray(jsonArray.size());
            for(Object o : jsonArray){
                JSONObject tempJsonObject = (JSONObject) o;
                cloneArray.add(tempJsonObject.clone());
            }
        }
        return cloneArray;
        
    }
}
