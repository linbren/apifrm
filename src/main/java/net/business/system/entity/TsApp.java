package net.business.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
* TS_APP 实体类
* @author yiting lin
* created 2016-12-26 14:05:41
*/ 
@SuppressWarnings("serial")
@Entity
@Table(name = "TS_APP", schema = "")
public class TsApp implements java.io.Serializable {
    /** ID*/ 
	@Id
	@GeneratedValue(generator="assigned") 
	@GenericGenerator(name = "assigned", strategy = "assigned")
    @Column(name = "ID")
    private String id;
    /** APP_NAME*/ 
    @Column(name = "APP_NAME")
    private String appName;
    /** APP_URL*/ 
    @Column(name = "APP_URL")
    private String appUrl;
    /** APP_ICON*/ 
    @Column(name = "APP_ICON")
    private String appIcon;
    /** APP_TYPE*/ 
    @Column(name = "APP_TYPE")
    private String appType;
    /** APP_VERSION*/
    @Column(name = "APP_VERSION")
    private String appVersion;
    /** SORT_CODE*/ 
    @Column(name = "SORT_CODE")
    private Integer sortCode;
    /** IS_VALID*/ 
    @Column(name = "IS_VALID")
    private String isValid;
    /** REMARK*/ 
    @Column(name = "REMARK")
    private String remark;
    /**
    * setId
    * @author yiting lin
    * @created 2016-12-26 14:05:41
    * @param ID
    */ 
    public void setId(String id){
        this.id=id;
    }
    /**
    * getId
    * @author yiting lin
    * @created 2016-12-26 14:05:41
    * @return String
    */ 
    public String getId(){
        return id;
    }
    /**
    * setAppName
    * @author yiting lin
    * @created 2016-12-26 14:05:41
    * @param APP_NAME
    */ 
    public void setAppName(String appName){
        this.appName=appName;
    }
    /**
    * getAppName
    * @author yiting lin
    * @created 2016-12-26 14:05:41
    * @return String
    */ 
    public String getAppName(){
        return appName;
    }
    /**
    * setAppUrl
    * @author yiting lin
    * @created 2016-12-26 14:05:41
    * @param APP_URL
    */ 
    public void setAppUrl(String appUrl){
        this.appUrl=appUrl;
    }
    /**
    * getAppUrl
    * @author yiting lin
    * @created 2016-12-26 14:05:41
    * @return String
    */ 
    public String getAppUrl(){
        return appUrl;
    }
    /**
    * setAppIcon
    * @author yiting lin
    * @created 2016-12-26 14:05:41
    * @param APP_ICON
    */ 
    public void setAppIcon(String appIcon){
        this.appIcon=appIcon;
    }
    /**
    * getAppIcon
    * @author yiting lin
    * @created 2016-12-26 14:05:41
    * @return String
    */ 
    public String getAppIcon(){
        return appIcon;
    }
    /**
    * setAppType
    * @author yiting lin
    * @created 2016-12-26 14:05:41
    * @param APP_TYPE
    */ 
    public void setAppType(String appType){
        this.appType=appType;
    }
    /**
    * getAppType
    * @author yiting lin
    * @created 2016-12-26 14:05:41
    * @return String
    */ 
    public String getAppType(){
        return appType;
    }
    /**
    * setSortCode
    * @author yiting lin
    * @created 2016-12-26 14:05:41
    * @param SORT_CODE
    */ 
    public void setSortCode(Integer sortCode){
        this.sortCode=sortCode;
    }
    /**
    * getSortCode
    * @author yiting lin
    * @created 2016-12-26 14:05:41
    * @return int
    */ 
    public Integer getSortCode(){
        return sortCode;
    }
    /**
    * setIsValid
    * @author yiting lin
    * @created 2016-12-26 14:05:41
    * @param IS_VALID
    */ 
    public void setIsValid(String isValid){
        this.isValid=isValid;
    }
    /**
    * getIsValid
    * @author yiting lin
    * @created 2016-12-26 14:05:41
    * @return String
    */ 
    public String getIsValid(){
        return isValid;
    }
    /**
    * setRemark
    * @author yiting lin
    * @created 2016-12-26 14:05:41
    * @param REMARK
    */ 
    public void setRemark(String remark){
        this.remark=remark;
    }
    /**
    * getRemark
    * @author yiting lin
    * @created 2016-12-26 14:05:41
    * @return String
    */ 
    public String getRemark(){
        return remark;
    }
	public String getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
    
}

