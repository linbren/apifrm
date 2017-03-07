package net.business.system.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
* TS_LOG 实体类
* @author yiting lin
* created 2016-12-30 15:14:38
*/ 
@SuppressWarnings("serial")
@Entity
@Table(name = "TS_LOG", schema = "")
public class TsLog implements java.io.Serializable {
    /** ID*/ 
    @Column(name = "ID")
    @Id
 	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private String id;
    /** APP_ID*/ 
    @Column(name = "APP_ID")
    private String appId;
    /** USER_NAME*/ 
    @Column(name = "USER_NAME")
    private String userName;
    /** OPT_NAME*/ 
    @Column(name = "OPT_NAME")
    private String optName;
    /** OPT_CONTENT*/ 
    @Column(name = "OPT_CONTENT")
    private String optContent;
    /** REQUEST_URL*/ 
    @Column(name = "REQUEST_URL")
    private String requestUrl;
    /** REQUEST_IP*/ 
    @Column(name = "REQUEST_IP")
    private String requestIp;
    /** CREATE_TIME*/ 
    @Column(name = "CREATE_TIME")
    private String createTime;
    /**
    * setId
    * @author yiting lin
    * @created 2016-12-30 15:14:38
    * @param ID
    */ 
    public void setId(String id){
        this.id=id;
    }
    /**
    * getId
    * @author yiting lin
    * @created 2016-12-30 15:14:38
    * @return int identity
    */ 
    public String getId(){
        return id;
    }
    /**
    * setAppId
    * @author yiting lin
    * @created 2016-12-30 15:14:38
    * @param APP_ID
    */ 
    public void setAppId(String appId){
        this.appId=appId;
    }
    /**
    * getAppId
    * @author yiting lin
    * @created 2016-12-30 15:14:38
    * @return String
    */ 
    public String getAppId(){
        return appId;
    }
    /**
    * setUserName
    * @author yiting lin
    * @created 2016-12-30 15:14:38
    * @param USER_NAME
    */ 
    public void setUserName(String userName){
        this.userName=userName;
    }
    /**
    * getUserName
    * @author yiting lin
    * @created 2016-12-30 15:14:38
    * @return String
    */ 
    public String getUserName(){
        return userName;
    }
    /**
    * setOptName
    * @author yiting lin
    * @created 2016-12-30 15:14:38
    * @param OPT_NAME
    */ 
    public void setOptName(String optName){
        this.optName=optName;
    }
    /**
    * getOptName
    * @author yiting lin
    * @created 2016-12-30 15:14:38
    * @return String
    */ 
    public String getOptName(){
        return optName;
    }
    /**
    * setOptContent
    * @author yiting lin
    * @created 2016-12-30 15:14:38
    * @param OPT_CONTENT
    */ 
    public void setOptContent(String optContent){
        this.optContent=optContent;
    }
    /**
    * getOptContent
    * @author yiting lin
    * @created 2016-12-30 15:14:38
    * @return String
    */ 
    public String getOptContent(){
        return optContent;
    }
    /**
    * setRequestUrl
    * @author yiting lin
    * @created 2016-12-30 15:14:38
    * @param REQUEST_URL
    */ 
    public void setRequestUrl(String requestUrl){
        this.requestUrl=requestUrl;
    }
    /**
    * getRequestUrl
    * @author yiting lin
    * @created 2016-12-30 15:14:38
    * @return String
    */ 
    public String getRequestUrl(){
        return requestUrl;
    }
    /**
    * setRequestIp
    * @author yiting lin
    * @created 2016-12-30 15:14:38
    * @param REQUEST_IP
    */ 
    public void setRequestIp(String requestIp){
        this.requestIp=requestIp;
    }
    /**
    * getRequestIp
    * @author yiting lin
    * @created 2016-12-30 15:14:38
    * @return String
    */ 
    public String getRequestIp(){
        return requestIp;
    }
    /**
    * setCreateTime
    * @author yiting lin
    * @created 2016-12-30 15:14:38
    * @param CREATE_TIME
    */ 
    public void setCreateTime(String createTime){
        this.createTime=createTime;
    }
    /**
    * getCreateTime
    * @author yiting lin
    * @created 2016-12-30 15:14:38
    * @return datetime
    */ 
    public String getCreateTime(){
        return createTime;
    }
}

