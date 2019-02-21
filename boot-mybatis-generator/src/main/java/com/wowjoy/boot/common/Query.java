package com.wowjoy.boot.common;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author scq
 */
@Data
public class Query extends LinkedHashMap<String, Object> {
    private static final long serialVersionUID = 1L;
    /**
     * 当前页码
     */
    private int page;
    /**
     * 每页条数
     */
    private int limit;

    public Query(Map<String, Object> params) {
        this.putAll(params);

        this.page = Integer.parseInt(params.get("page").toString());
        this.limit = Integer.parseInt(params.get("limit").toString());
        this.put("offset", (page - 1) * limit);
        this.put("page", page);
        this.put("limit", limit);
    }
}
