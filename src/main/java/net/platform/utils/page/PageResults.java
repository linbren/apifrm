package net.platform.utils.page;

import java.util.List;
/**
 * 分页封装类
 * 用于做分页查询的基础类，封装了一些分页的相关属性
 * @author 
 * @version v1.0
 * @param <T>
 */
public class PageResults<T> {
 
    // 下一页
    private int pageNo;
 
    // 当前页
    private int currentPage;
 
    // 每页行数
    private int pageSize;
 
    // 总条数  兼容easyUI
    private int total;
 
    // 总页数
    private int pageCount;
 
    // 记录  兼容easyUI
    private List<T> rows;
 
    public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public int getPageCount() {
        return pageCount;
    }
 
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
 
    public int getPageNo() {
        if (pageNo <= 0) {
            return 1;
        } else{
            return pageNo > pageCount ? pageCount : pageNo;
        }
    }
 
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
 

 
    public int getCurrentPage() {
        return currentPage;
    }
 
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
 
    public int getPageSize() {
        return pageSize;
    }
 
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize <= 0 ? 10 : pageSize;
    }
 

 
    public void resetPageNo() {
        pageNo = currentPage + 1;
        pageCount = total % pageSize == 0 ? total / pageSize
                : total / pageSize + 1;
    }
 
}
