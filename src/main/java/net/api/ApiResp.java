package net.api;


public class ApiResp {

    /**
     * 提示编码
     */
    private int ret = RetMsg.API_SUCCESS;
    /**
     * 提示信息
     */
    private String msg = RetMsg.retMsg.get(RetMsg.API_SUCCESS);
	/**
     * 信息主体
     */
    private Object items = null;
    public int getRet() {
 		return ret;
 	}
 	public void setRet(int ret) {
 		this.ret = ret;
 	}
 	public String getMsg() {
 		return msg;
 	}
 	public void setMsg(String msg) {
 		this.msg = msg;
 	}
 	public Object getItems() {
 		return items;
 	}
 	public void setItems(Object items) {
 		this.items = items;
 	}

}
