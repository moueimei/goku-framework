package com.gkframework.orm.mybatis.query;

import java.util.Map;

/**
 * 封装查询蚕食和查询条件
 * 
 * @author moueimei
 * 
 */
public class MapQuery extends BaseQuery  {
	
	private static final long serialVersionUID = -8249193845207112212L;

	@Override
	public void setDefaultPo(Object po) {
		this.put("defaultPo", po);
	}


	@Override
	public Object getDefaultPo() {
		return get("defaultPo");
	}

	/**
	 * 给定一个分页对象，创建查询对象<br>
	 */
	public static MapQuery create() {
		MapQuery query = new MapQuery();
		return query;
	}
	
	public static MapQuery create(Map<String, Object> params) {
		MapQuery query = new MapQuery();
		query.putAll(params);
		return query;
	}
	
	public static MapQuery create(Object... pairs) {
		MapQuery query = new MapQuery();
		query.addParameters(pairs);
		return query;
	}
}