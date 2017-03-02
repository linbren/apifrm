package net.platform.utils.page;

/**
 * 
 * 功能描述：分页模型
 * easyUI 默认
 * @author 
 * @Email 
 * @version 1.1
 *          <p>
 *          修改历史：(修改人，修改时间，修改原因/内容)
 *          </p>
 */
public class PageUtil {

    /**
     * 页面EASYUI的当前页
     */
    private int page;
    /**
     * 页面EASYUI的每页行数
     */
    private int rows;

    /**
     * 页面最大条数
     */
    private int pageSize;
    /**
     * 页面起始页
     */
    private int pageNumber;

    /**
     * @return the page
     */
    public int getPage() {
        return page;
    }

    /**
     * @param page
     *            the page to set
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * @return the rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * @param rows
     *            the rows to set
     */
    public void setRows(int rows) {
        this.rows = rows;
    }

    /**
     * @return the pageSize
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize
     *            the pageSize to set
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @return the pageNumber
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * @param pageNumber
     *            the pageNumber to set
     */
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
    public String toString(){
    	return "p"+this.page+"r"+this.rows;
    }

}
