package net.platform.utils.page;

/**
 * 
 */
public class Ztree {
    /**
     * id
     */
    private String id;
    /**
     * pid
     */
    private String pId;
    /**
     * 名称
     */
    private String name;
    /**
     * 是否打开节点（true or false）
     */
    private Boolean open = false;
    /**
     * 是否打勾（true or false）
     */
    private Boolean checked = false;
    /**
     * 是否有复选框（true or false）
     */
    private Boolean nocheck = false;
    /**
     * type
     */
    private String data;
    /**
     * 是否禁用（true or false）
     */
    private Boolean chkDisabled = false;
    
    public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
     * url
     */
    private String url;
    private String target;
    private String icon;
    private String iconSkin;
    
    /**
     * @return the nocheck
     */
    public Boolean getNocheck() {

        return nocheck;
    }

    /**
     * @param nocheck
     *            the nocheck to set
     */
    public void setNocheck(Boolean nocheck) {

        this.nocheck = nocheck;
    }

    /**
     * @return the id
     */
    public String getId() {

        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {

        this.id = id;
    }

    /**
     * @return the pId
     */
    public String getpId() {

        return pId;
    }

    /**
     * @param pId
     *            the pId to set
     */
    public void setpId(String pId) {

        this.pId = pId;
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

    /**
     * @return the open
     */
    public Boolean getOpen() {

        return open;
    }

    /**
     * @param open
     *            the open to set
     */
    public void setOpen(Boolean open) {

        this.open = open;
    }

    /**
     * @return the checked
     */
    public Boolean getChecked() {

        return checked;
    }

    /**
     * @param checked
     *            the checked to set
     */
    public void setChecked(Boolean checked) {

        this.checked = checked;
    }

	public Boolean getChkDisabled() {
		return chkDisabled;
	}

	public void setChkDisabled(Boolean chkDisabled) {
		this.chkDisabled = chkDisabled;
	}

	public String getIconSkin() {
		return iconSkin;
	}

	public void setIconSkin(String iconSkin) {
		this.iconSkin = iconSkin;
	}

}
