package com.gkframework.model.page;

/**
 * 分页接口
 * 
 * @author moueimei
 * 
 */
public interface Paginable {

	public final static int _DEFAULT_PAGE_SIZE = 20;
	
	/**
	 * 总记录数
	 * 
	 * @return
	 */
	public int getTotalCount();

	/**
	 * 总页数
	 * 
	 * @return
	 */
	public int getTotalPage();

	/**
	 * 每页记录数
	 * 
	 * @return
	 */
	public int getPageSize();

	/**
	 * 当前页号
	 * 
	 * @return
	 */
	public int getPageNo();

	/**
	 * 是否第一页
	 * 
	 * @return
	 */
	public boolean isFirstPage();

	/**
	 * 是否最后一页
	 * 
	 * @return
	 */
	public boolean isLastPage();

	/**
	 * 返回下页的页号
	 */
	public int getNextPage();

	/**
	 * 当前页第一条记录在总结果集中的位置,序号从1开始.
	 * @return
	 */
	public int getStart();

	/**
	 * 返回上页的页号
	 */
	public int getPrePage();

}
