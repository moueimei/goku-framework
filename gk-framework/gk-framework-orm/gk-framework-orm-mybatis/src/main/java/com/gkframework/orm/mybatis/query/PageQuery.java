package com.gkframework.orm.mybatis.query;

import com.gkframework.model.page.Paginable;

import java.util.List;
import java.util.Map;

/**
 * 封装查询蚕食和查询条件
 * 
 * @author moueimei
 * 
 */
public class PageQuery extends BaseQuery {
	
	private static final long serialVersionUID = -244289359960417314L;

	/**
	 * 给Dto压入第一个默认List对象<br>
	 * 为了方便存取(省去根据Key来存取和类型转换的过程)
	 * 
	 * @param pList
	 *            压入Dto的List对象
	 */
	public void setDefaultAList(List<?> pList) {
		put("defaultAList", pList);
	}

	/**
	 * 给Dto压入第二个默认List对象<br>
	 * 为了方便存取(省去根据Key来存取和类型转换的过程)
	 * 
	 * @param pList
	 *            压入Dto的List对象
	 */
	public void setDefaultBList(List<?> pList) {
		put("defaultBList", pList);
	}

	/**
	 * 获取第一个默认List对象<br>
	 * 为了方便存取(省去根据Key来存取和类型转换的过程)
	 */
	public List<?> getDefaultAList() {
		return (List<?>) get("defaultAList");
	}

	/**
	 * 获取第二个默认List对象<br>
	 * 为了方便存取(省去根据Key来存取和类型转换的过程)
	 */
	public List<?> getDefaultBList() {
		return (List<?>) get("defaultBList");
	}

	/**
	 * 给Dto压入第一个默认PO对象<br>
	 * 为了方便存取(省去根据Key来存取和类型转换的过程)
	 * 
	 * @param po
	 *            压入Dto的PO对象
	 */
	public void setDefaultPo(Object po) {
		put("defaultPo", po);
	}

	/**
	 * 获取第一个默认PO对象<br>
	 * 为了方便存取(省去根据Key来存取和类型转换的过程)
	 */
	public Object getDefaultPo() {
		return get("defaultPo");
	}



	/**
	 * 给定一个分页对象，创建查询对象<br>
	 * 
	 * @param page
	 *            QueryPage
	 */
	public static PageQuery create(Paginable page) {
		PageQuery query = new PageQuery();
		query.setPage(page);
		return query;
	}
	
	public static PageQuery create() {
		PageQuery query = new PageQuery();
		return query;
	}
	
	public static PageQuery create(Paginable page, Map<String, Object> params) {
		PageQuery query = new PageQuery();
		query.setPage(page);
		query.putAll(params);
		return query;
	}
	
	public static PageQuery create(Object... pairs) {
		PageQuery query = new PageQuery();
		query.addParameters(pairs);
		return query;
	}

}