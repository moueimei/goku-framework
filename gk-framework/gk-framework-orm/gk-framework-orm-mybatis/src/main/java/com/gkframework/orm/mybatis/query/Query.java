package com.gkframework.orm.mybatis.query;

import com.gkframework.model.Operator;
import com.gkframework.model.page.Paginable;

import java.util.Map;

/**
 * 数据传输对象接口<br>
 * @author xusm
 * @since 2011-05-03
 * @see Map
 */
public interface Query extends Map<String, Object> {
	    	
	/**
	 * 给Dto压入第一个默认PO对象<br>
	 * 为了方便存取(省去根据Key来存取和类型转换的过程)
	 * 
	 * @param po
	 *            压入Dto的PO对象
	 */
	public void setDefaultPo(Object po);
	
	/**
	 * 获取第一个默认id对象<br>
	 * 为了方便存取(省去根据Key来存取和类型转换的过程)
	 */
	public Object getDefaultPo();
	
	/**
	 * 给Pagination压入第一个默认Pagination对象<br>
	 * 为了方便存取(省去根据Key来存取和类型转换的过程)
	 * 
	 * @param page
	 *            压入Dto的Pagination对象
	 */
	public void setPage(Paginable page);
	
	/**
	 * 获取第一个默认Pagination对象<br>
	 * 为了方便存取(省去根据Key来存取和类型转换的过程)
	 */
	public Paginable getPage();
	
	/**
	 * 给Operater压入第一个默认Operater对象<br>
	 * 为了方便存取(省去根据Key来存取和类型转换的过程)
	 * 
	 * @param operater
	 *            压入Dto的Operater对象
	 */
	public void setDefaultOperater(Operator operater);
	
	/**
	 * 获取第一个默认Operater对象<br>
	 * 为了方便存取(省去根据Key来存取和类型转换的过程)
	 */
	public Operator getDefaultOperater();
	
	/**
	 * 给ids压入第一个默认ids对象<br>
	 * 为了方便存取(省去根据Key来存取和类型转换的过程)
	 * 
	 * @param ids
	 *            压入Dto的ids对象
	 */
	public void setDefaultIds(String ids);	
	
	/**
	 * 给id压入第一个默认id对象<br>
	 * 为了方便存取(省去根据Key来存取和类型转换的过程)
	 * 
	 * @param id
	 *            压入Dto的id对象
	 */
	public void setDefaultId(Object id);
	
	/**
	 * 获取第一个默认ids对象<br>
	 * 为了方便存取(省去根据Key来存取和类型转换的过程)
	 */
	public String getDefaultIds();

	/**
	 * 获取第一个默认id对象<br>
	 * 为了方便存取(省去根据Key来存取和类型转换的过程)
	 */
	public Object getDefaultId();
	
	
	//
	public void addParameters(Object... pairs);
	
	/**
	 * Add parameters to a new dto.
	 * 
	 * @param parameters
	 * @return A new URL 
	 */
    public void addParameters(Map<String, Object> parameters);
    
    public void addParameters(String key, Object value);
    
    public Object getParameter(String key);

    public String getParameter(String key, String defaultValue);
    
    public double getParameter(String key, double defaultValue);
    
    public float getParameter(String key, float defaultValue);

    public long getParameter(String key, long defaultValue);

    public int getParameter(String key, int defaultValue);

    public short getParameter(String key, short defaultValue);

    public byte getParameter(String key, byte defaultValue);
    
    public boolean getParameter(String key, boolean defaultValue);
		
}
