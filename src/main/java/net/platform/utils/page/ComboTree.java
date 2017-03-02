package net.platform.utils.page;

import java.util.List;
import java.util.Map;

/**
 * 
 * 功能描述：combotree模型
 * 
 */
public class ComboTree implements java.io.Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    /**
     * 树节点id
     */
    private String id;
    /**
     * 树节点名称
     */
    private String text;
    /**
     * 前面的小图标样式
     */
    private String iconCls;
    /**
     * 是否勾选状态
     */
    private Boolean checked = false;
    /**
     * 其他参数
     */
    private Map<String, Object> attributes;
    /**
     * 子节点
     */
    private List<ComboTree> children;
    /**
     * 是否展开(open,closed)
     */
    private String state = "closed";
    /**
     * 父类id
     */
    private String parentId;
    /**
     * code
     */
    private String code;

    public String getCode() {

        return code;
    }

    public void setCode(String code) {

        this.code = code;
    }

    /**
     * 
     * 功能描述：获取父类id
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * 
     * 功能描述：设置父类id
     * @param parentId
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * 
     * 构造函数：combotree模型
     * 
     * @param id
     * @param parentId
     * @param name
     */
    public ComboTree(String id, String parentId, String name) {
        super();
        this.id = id;
        this.parentId = parentId;
        this.text = name;
    }

    /**
     * 
     * 构造函数：combotree模型
     * 
     * @param id
     * @param parentId
     * @param name
     */
    public ComboTree(String id, String parentId, String name, String code) {
        super();
        this.id = id;
        this.parentId = parentId;
        this.text = name;
        this.code = code;
    }

    public ComboTree() {
		// TODO Auto-generated constructor stub
	}

	/**
     * 
     * 功能描述：获取id
     * @return String
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * 功能描述：设置id
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * 功能描述：获取文本名称
     * @return
     */
    public String getText() {
        return text;
    }

    /**
     * 
     * 功能描述：设置文本内容
     * @param text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * 
     * 功能描述：选择对象
     * @return
     */
    public Boolean getChecked() {
        return checked;
    }

    /**
     * 
     * 功能描述：设置选择对象
     * @param checked
     */
    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    /**
     * 
     * 功能描述：获取属性
     * 
     * @author 
     * @return
     */
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    /**
     * 
     * 功能描述：设置属性
     * 
     * @author 
     * 
     * @param attributes
     */
    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    /**
     * 
     * 功能描述：设置子类
     * 
     * @author 
     * @return
     */
    public List<ComboTree> getChildren() {
        return children;
    }

    /**
     * 
     * 功能描述：设置子类
     * 
     * @author 
     * 
     * @param map
     */
    public void setChildren( List<ComboTree> map) {
        this.children = map;
//        if (this.children != null) {
//            for (int i = 0; i < this.children.size(); i++) {
//                ComboTree tree = children.get(i);
//                tree.setChildren(map);
//            }
//        } else {
//            this.setState("open");
//        }
    }

  
    /**
     * 
     * 功能描述：获取状态
     * 
     * @author 
     * 
     * @return
     */
    public String getState() {
        return state;
    }

    /**
     * 
     * 功能描述：设置状态
     * 
     * @author 
     * 
     * @param state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * 
     * 功能描述：获取图片
     * 
     * @author 
     * 
     * @return
     */
    public String getIconCls() {
        return iconCls;
    }

    /**
     * 
     * 功能描述：设置图片样式
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2016-7-18 下午03:44:39
     *        </p>
     * @param iconCls
     */
    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

}
