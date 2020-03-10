/**
 * 
 */
package it.unical.mat.moviesquik.persistence;

/**
 * @author Agostino
 *
 */
public class DataListPage
{
	public static final int POSTS_PAGE_LIMIT = 3;
	public static final int COMMENTS_PAGE_LIMIT = 2;
	
	public static final DataListPage DEFAULT_POSTS_PAGE = new DataListPage(POSTS_PAGE_LIMIT);
	public static final DataListPage DEFAULT_COMMENTS_PAGE = new DataListPage(COMMENTS_PAGE_LIMIT);
	
	public static final DataListPage INFINITE_PAGE = new DataListPage(Integer.MAX_VALUE);
	
	private final int pageIndex;
	private final int limit;
	
	public DataListPage( final int limit )
	{
		this.pageIndex = 0;
		this.limit = limit;
	}
	
	public DataListPage( final int pageIndex, final int limit )
	{
		this.pageIndex = pageIndex;
		this.limit = limit;
	}
	
	public int getPageIndex()
	{
		return pageIndex;
	}
	
	public int getOffset()
	{
		return pageIndex * limit;
	}
	
	public int getLimit()
	{
		return limit;
	}
}
