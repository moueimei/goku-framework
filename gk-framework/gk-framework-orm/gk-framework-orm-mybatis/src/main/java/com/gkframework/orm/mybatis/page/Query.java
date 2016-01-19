package com.gkframework.orm.mybatis.page;


import com.gkframework.model.page.Pagination;

import java.util.HashMap;
import java.util.Map;

/**
 * 封装查询蚕食和查询条件
 * 
 * @author moueimei
 * 
 */
public class Query {
	
	private Map<String, Object> queryParams;
	private Pagination page;

	public Map<String, Object> getQueryParams() {
		return queryParams;
	}

	public void setQueryParams(Map<String, Object> queryParams) {
		this.queryParams = queryParams;
	}

	public Pagination getPage() {
		return page;
	}

	public void setPage(Pagination page) {
		this.page = page;
	}
	
	public Query(){}
	
	public Query(Pagination page){
		this.page = page;
	}
	
	public Query(Pagination page, Map<String, Object> queryParams){
		this.page = page;
		this.queryParams = queryParams;
	}
	
	public static Query create() {
		return new Query();
	}

	public static Query create(Pagination page, Map<String, Object> queryParams) {
		return new Query(page, queryParams);
	}
	
	public static Query create(Pagination page) {
		return new Query(page);
	}
	
	public void addQueryParam(String key, Object value) {
		if(null == queryParams) {
			queryParams = new HashMap<String, Object>();
		}
		queryParams.put(key, value);
	}
	

}