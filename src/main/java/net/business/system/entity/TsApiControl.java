package net.business.system.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
* TS_API_CONTROL 实体类
* @author yiting lin
* created 2017-04-14 15:26:40
*/ 
@SuppressWarnings("serial")
@Entity
@Table(name = "TS_API_CONTROL", schema = "")
public class TsApiControl implements java.io.Serializable {
    /** API_NAME*/ 
	@Id
    @GeneratedValue(generator="assigned") 
	@GenericGenerator(name = "assigned", strategy = "assigned")  
    @Column(name = "API_NAME")
    private String apiName;
    /** API_DESC*/ 
    @Column(name = "API_DESC")
    private String apiDesc;
    /** API_VERSION*/ 
    @Column(name = "API_VERSION")
    private String apiVersion;
    /** API_STATUS*/ 
    @Column(name = "API_STATUS")
    private String apiStatus;
    /** CREATE_TIME*/ 
    @Column(name = "CREATE_TIME")
    private Date createTime;
    /** EXPIRE_TIME*/ 
    @Column(name = "EXPIRE_TIME")
    private Date expireTime;
    /**
    * setApiName
    * @author yiting lin
    * @created 2017-04-14 15:26:40
    * @param API_NAME
    */ 
    public void setApiName(String apiName){
        this.apiName=apiName;
    }
    /**
    * getApiName
    * @author yiting lin
    * @created 2017-04-14 15:26:40
    * @return String
    */ 
    public String getApiName(){
        return apiName;
    }
    /**
    * setApiDesc
    * @author yiting lin
    * @created 2017-04-14 15:26:40
    * @param API_DESC
    */ 
    public void setApiDesc(String apiDesc){
        this.apiDesc=apiDesc;
    }
    /**
    * getApiDesc
    * @author yiting lin
    * @created 2017-04-14 15:26:40
    * @return String
    */ 
    public String getApiDesc(){
        return apiDesc;
    }
    /**
    * setApiVersion
    * @author yiting lin
    * @created 2017-04-14 15:26:40
    * @param API_VERSION
    */ 
    public void setApiVersion(String apiVersion){
        this.apiVersion=apiVersion;
    }
    /**
    * getApiVersion
    * @author yiting lin
    * @created 2017-04-14 15:26:40
    * @return String
    */ 
    public String getApiVersion(){
        return apiVersion;
    }
    /**
    * setApiStatus
    * @author yiting lin
    * @created 2017-04-14 15:26:40
    * @param API_STATUS
    */ 
    public void setApiStatus(String apiStatus){
        this.apiStatus=apiStatus;
    }
    /**
    * getApiStatus
    * @author yiting lin
    * @created 2017-04-14 15:26:40
    * @return String
    */ 
    public String getApiStatus(){
        return apiStatus;
    }
    /**
    * setCreateTime
    * @author yiting lin
    * @created 2017-04-14 15:26:40
    * @param CREATE_TIME
    */ 
    public void setCreateTime(Date createTime){
        this.createTime=createTime;
    }
    /**
    * getCreateTime
    * @author yiting lin
    * @created 2017-04-14 15:26:40
    * @return datetime
    */ 
    public Date getCreateTime(){
        return createTime;
    }
    /**
    * setExpireTime
    * @author yiting lin
    * @created 2017-04-14 15:26:40
    * @param EXPIRE_TIME
    */ 
    public void setExpireTime(Date expireTime){
        this.expireTime=expireTime;
    }
    /**
    * getExpireTime
    * @author yiting lin
    * @created 2017-04-14 15:26:40
    * @return datetime
    */ 
    public Date getExpireTime(){
        return expireTime;
    }
}

