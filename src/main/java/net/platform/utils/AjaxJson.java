package net.platform.utils;

import java.util.Map;

/**
 * 
 * 功能描述：ajax请求返回数据模型
 * 
 * @author 
 * 
 * @version 0.1
 *          <p>
 *          修改历史：(修改人，修改时间，修改原因/内容)
 *          </p>
 */
public class AjaxJson {

    /**
     * 是否成功
     */
    private boolean success = true;
    /**
     * 提示信息
     */
    private String msg = "操作成功";
    /**
     * 其他信息
     */
    private Object obj = null;
    /**
     * 其他参数
     */
    private Map<String, Object> attributes;

    /**
     * 全拼
     */
    private String pinyin;
    /**
     * 拼音首字母
     */
    private String pinyinhead;
    /**
     * 汉字
     */
    private String chines;
    /**
     * 编码
     */
    private String code;
    /**
     * 名称
     */
    private String name;

    /**
     * 方法getCode
     * 
     * @return 返回值String
     */
    public String getCode() {

        return code;
    }

    /**
     * 方法setCode
     * 
     * @param code
     *            传入参数
     */
    public void setCode(String code) {

        this.code = code;
    }

    /**
     * 方法getChines
     * 
     * @return 返回值String
     */
    public String getChines() {

        return chines;
    }

    /**
     * 方法setChines
     * 
     * @param chines
     *            传入参数
     */
    public void setChines(String chines) {

        this.chines = chines;
    }

    /**
     * 方法getPinyin
     * 
     * @return 返回值String
     */
    public String getPinyin() {

        return pinyin;
    }

    /**
     * 方法setPinyin
     * 
     * @param pinyin
     *            传入参数
     */
    public void setPinyin(String pinyin) {

        this.pinyin = pinyin;
    }

    /**
     * 方法getPinyinhead
     * 
     * @return 返回值String
     */
    public String getPinyinhead() {

        return pinyinhead;
    }

    /**
     * 方法setPinyinhead
     * 
     * @param pinyinhead
     *            传入参数
     */
    public void setPinyinhead(String pinyinhead) {

        this.pinyinhead = pinyinhead;
    }

    /**
     * 方法getAttributes
     * 
     * @return 返回值Object>
     */
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    /**
     * 
     * 功能描述：其他参数
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2016-7-18 下午03:55:06
     *        </p>
     * @param attributes
     */
    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    /**
     * 
     * 功能描述：提示信息
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2016-7-18 下午03:55:11
     *        </p>
     * @return
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 
     * 功能描述：提示信息
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2016-7-18 下午03:55:26
     *        </p>
     * @param msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 
     * 功能描述：其他信息
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2016-7-18 下午03:55:36
     *        </p>
     * @return
     */
    public Object getObj() {
        return obj;
    }

    /**
     * 
     * 功能描述：其他信息
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2016-7-18 下午03:56:15
     *        </p>
     * @param obj
     */
    public void setObj(Object obj) {
        this.obj = obj;
    }

    /**
     * 
     * 功能描述：是否成功
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2016-7-18 下午03:56:27
     *        </p>
     * @return
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * 
     * 功能描述：是否成功
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2016-7-18 下午03:56:31
     *        </p>
     * @param success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * @return the name
     */
    public String getName() {

        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {

        this.name = name;
    }

}
