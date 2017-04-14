package net.business.system.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
/**
* TS_FUNCTION 实体类
* @author yiting lin
* created 2016-12-13 15:44:29
*/ 
@SuppressWarnings("serial")
@Entity
@Table(name = "TS_FUNCTION", schema = "")
public class TsFunction  implements java.io.Serializable {
    /** ID*/ 
    @Column(name = "ID")
    @Id
 	@GeneratedValue(strategy=GenerationType.IDENTITY)  	    
    private String id;
    /** APP_ID*/ 
    @Column(name = "APP_ID")
    private String appId;
    /** FUNC_CODE*/ 
    @Column(name = "FUNC_CODE")
    private String funcCode;
    /** FUNC_NAME*/ 
    @Column(name = "FUNC_NAME")
    private String funcName;
    /** FUNC_URL*/ 
    @Column(name = "FUNC_URL")
    private String funcUrl;
    /** FUNC_METHOD*/ 
    @Column(name = "FUNC_METHOD")
    private String funcMethod;
    /** FUNC_ICON*/ 
    @Column(name = "FUNC_ICON")
    private String funcIcon;
    /** PARENT_CODE*/ 
    @Column(name = "PARENT_CODE")
    private String parentCode;
    /** FUNC_TYPE*/ 
    @Column(name = "FUNC_TYPE")
    private String funcType;
    /** PERMISSION*/ 
    @Column(name = "PERMISSION")
    private String permission;
    /** SORT_CODE*/ 
    @Column(name = "SORT_CODE")
    private Integer sortCode;
    /** IS_VALID*/ 
    @Column(name = "IS_VALID")
    private String isValid;
    /** REMARK*/ 
    @Column(name = "REMARK")
    private String remark;
    /** TS_ROLE_FUNCTION*/
    /*@ManyToMany(mappedBy="functions",fetch=FetchType.EAGER,targetEntity=TsRole.class,
    		cascade=CascadeType.REFRESH)
    private Set<TsRole> roles = new HashSet<TsRole>();*/
    
    /**
    * setId
    * @author yiting lin
    * @created 2016-12-13 15:44:29
    * @param ID
    */ 
    public void setId(String id){
        this.id=id;
    }
    /**
    * getId
    * @author yiting lin
    * @created 2016-12-13 15:44:29
    * @return int identity
    */ 
    public String getId(){
        return id;
    }
    /**
    * setAppId
    * @author yiting lin
    * @created 2016-12-13 15:44:29
    * @param APP_ID
    */ 
    public void setAppId(String appId){
        this.appId=appId;
    }
    /**
    * getAppId
    * @author yiting lin
    * @created 2016-12-13 15:44:29
    * @return String
    */ 
    public String getAppId(){
        return appId;
    }
    /**
    * setFuncCode
    * @author yiting lin
    * @created 2016-12-13 15:44:29
    * @param FUNC_CODE
    */ 
    public void setFuncCode(String funcCode){
        this.funcCode=funcCode;
    }
    /**
    * getFuncCode
    * @author yiting lin
    * @created 2016-12-13 15:44:29
    * @return String
    */ 
    public String getFuncCode(){
        return funcCode;
    }
    /**
    * setFuncName
    * @author yiting lin
    * @created 2016-12-13 15:44:29
    * @param FUNC_NAME
    */ 
    public void setFuncName(String funcName){
        this.funcName=funcName;
    }
    /**
    * getFuncName
    * @author yiting lin
    * @created 2016-12-13 15:44:29
    * @return String
    */ 
    public String getFuncName(){
        return funcName;
    }
    /**
    * setFuncUrl
    * @author yiting lin
    * @created 2016-12-13 15:44:29
    * @param FUNC_URL
    */ 
    public void setFuncUrl(String funcUrl){
        this.funcUrl=funcUrl;
    }
    /**
    * getFuncUrl
    * @author yiting lin
    * @created 2016-12-13 15:44:29
    * @return String
    */ 
    public String getFuncUrl(){
        return funcUrl;
    }
    /**
    * setFuncMethod
    * @author yiting lin
    * @created 2016-12-13 15:44:29
    * @param FUNC_METHOD
    */ 
    public void setFuncMethod(String funcMethod){
        this.funcMethod=funcMethod;
    }
    /**
    * getFuncMethod
    * @author yiting lin
    * @created 2016-12-13 15:44:29
    * @return String
    */ 
    public String getFuncMethod(){
        return funcMethod;
    }
    /**
    * setFuncIcon
    * @author yiting lin
    * @created 2016-12-13 15:44:29
    * @param FUNC_ICON
    */ 
    public void setFuncIcon(String funcIcon){
        this.funcIcon=funcIcon;
    }
    /**
    * getFuncIcon
    * @author yiting lin
    * @created 2016-12-13 15:44:29
    * @return String
    */ 
    public String getFuncIcon(){
        return funcIcon;
    }
    /**
    * setParentCode
    * @author yiting lin
    * @created 2016-12-13 15:44:29
    * @param PARENT_CODE
    */ 
    public void setParentCode(String parentCode){
        this.parentCode=parentCode;
    }
    /**
    * getParentCode
    * @author yiting lin
    * @created 2016-12-13 15:44:29
    * @return String
    */ 
    public String getParentCode(){
        return parentCode;
    }
    /**
    * setFuncType
    * @author yiting lin
    * @created 2016-12-13 15:44:29
    * @param FUNC_TYPE
    */ 
    public void setFuncType(String funcType){
        this.funcType=funcType;
    }
    /**
    * getFuncType
    * @author yiting lin
    * @created 2016-12-13 15:44:29
    * @return String
    */ 
    public String getFuncType(){
        return funcType;
    }
    /**
    * setPermission
    * @author yiting lin
    * @created 2016-12-13 15:44:29
    * @param PERMISSION
    */ 
    public void setPermission(String permission){
        this.permission=permission;
    }
    /**
    * getPermission
    * @author yiting lin
    * @created 2016-12-13 15:44:29
    * @return String
    */ 
    public String getPermission(){
        return permission;
    }
    /**
    * setSortCode
    * @author yiting lin
    * @created 2016-12-13 15:44:29
    * @param SORT_CODE
    */ 
    public void setSortCode(Integer sortCode){
        this.sortCode=sortCode;
    }
    /**
    * getSortCode
    * @author yiting lin
    * @created 2016-12-13 15:44:29
    * @return Integer
    */ 
    public Integer getSortCode(){
        return sortCode;
    }
    /**
    * setIsValid
    * @author yiting lin
    * @created 2016-12-13 15:44:29
    * @param IS_VALID
    */ 
    public void setIsValid(String isValid){
        this.isValid=isValid;
    }
    /**
    * getIsValid
    * @author yiting lin
    * @created 2016-12-13 15:44:29
    * @return String
    */ 
    public String getIsValid(){
        return isValid;
    }
    /**
    * setRemark
    * @author yiting lin
    * @created 2016-12-13 15:44:29
    * @param REMARK
    */ 
    public void setRemark(String remark){
        this.remark=remark;
    }
    /**
    * getRemark
    * @author yiting lin
    * @created 2016-12-13 15:44:29
    * @return String
    */ 
    public String getRemark(){
        return remark;
    }
    
}

