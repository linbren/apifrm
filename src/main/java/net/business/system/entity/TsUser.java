package net.business.system.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


/**
* TS_USER 实体类
* @author yiting lin
* created 2016-12-15 11:15:31
*/ 
@SuppressWarnings("serial")
@Entity
@Table(name = "TS_USER", schema = "")
public class TsUser implements java.io.Serializable {
    /** ID*/ 
    @Column(name = "ID")
    @Id
 	@GeneratedValue(strategy=GenerationType.IDENTITY)      
    private String id;
    /** APP_ID*/ 
    @Column(name = "APP_ID")
    private String appId;
    /** ORG_ID*/
    /*@Column(name = "ORG_ID")
    private String orgId;*/
    /** USER_CODE*/ 
    @Column(name = "USER_CODE")
    private String userCode;
    /** USER_NAME*/ 
    @Column(name = "USER_NAME")
    private String userName;
    /** USER_PASS*/ 
    @Column(name = "USER_PASS")
    private String userPass;
    /** USER_PHOTO*/ 
    @Column(name = "USER_PHOTO")
    private String userPhoto;
    /** ID_NUMBER*/ 
    @Column(name = "ID_NUMBER")
    private String idNumber;
    /** SEX*/ 
    @Column(name = "SEX")
    private String sex;
    /** E_MAIL*/ 
    @Column(name = "USER_EMAIL")
    private String userEmail;
    /** TELPHONE*/ 
    @Column(name = "TELPHONE")
    private String telphone;
    /** ADDRESS*/ 
    @Column(name = "ADDRESS")
    private String address;
    /** BIRTH_DATE*/ 
    @Column(name = "BIRTH_DATE")
    private String birthDate;
    /** SORT_CODE*/ 
    @Column(name = "SORT_CODE")
    private Integer sortCode;
    /** STATUS*/ 
    @Column(name = "STATUS")
    private String status;
    /** CREATE_TIME*/
    @Column(name = "CREATE_TIME")
    private Date createTime;
    /** REMARK*/ 
    @Column(name = "REMARK")
    private String remark;
    /** TS_USER_ROLE*/
    @ManyToMany(fetch = FetchType.EAGER,targetEntity=TsRole.class)
    @Cascade({CascadeType.SAVE_UPDATE,CascadeType.REFRESH})
    @JoinTable(name = "TS_USER_ROLE", joinColumns = {@JoinColumn(name="USER_ID")},
    inverseJoinColumns = {@JoinColumn(name="ROLE_ID")})
    private Set<TsRole> roles=new HashSet<TsRole>();
    
    @ManyToOne(targetEntity=TsOrgan.class,fetch=FetchType.EAGER)
    @JoinColumn(name="ORG_ID")
    @Cascade({CascadeType.REFRESH})
    private TsOrgan organ = new TsOrgan();
    
    /**
    * setId
    * @author yiting lin
    * @created 2016-12-15 11:15:31
    * @param ID
    */ 
    public void setId(String id){
        this.id=id;
    }
    /**
    * getId
    * @author yiting lin
    * @created 2016-12-15 11:15:31
    * @return int identity
    */ 
    public String getId(){
        return id;
    }
    /**
    * setAppId
    * @author yiting lin
    * @created 2016-12-15 11:15:31
    * @param APP_ID
    */ 
    public void setAppId(String appId){
        this.appId=appId;
    }
    /**
    * getAppId
    * @author yiting lin
    * @created 2016-12-15 11:15:31
    * @return String
    */ 
    public String getAppId(){
        return appId;
    }
    /**
     * getOrgId
     * @author yiting lin
     * @created 2016-12-15 11:15:31
     * @return String
     */
//    public String getOrgId() {
//		return orgId;
//	}
    /**
     * setOrgId
     * @author yiting lin
     * @created 2016-12-15 11:15:31
     * @param APP_ID
     */ 
//	public void setOrgId(String orgId) {
//		this.orgId = orgId;
//	}
	/**
    * setUserCode
    * @author yiting lin
    * @created 2016-12-15 11:15:31
    * @param USER_CODE
    */ 
    public void setUserCode(String userCode){
        this.userCode=userCode;
    }
    /**
    * getUserCode
    * @author yiting lin
    * @created 2016-12-15 11:15:31
    * @return String
    */ 
    public String getUserCode(){
        return userCode;
    }
    /**
    * setUserName
    * @author yiting lin
    * @created 2016-12-15 11:15:31
    * @param USER_NAME
    */ 
    public void setUserName(String userName){
        this.userName=userName;
    }
    /**
    * getUserName
    * @author yiting lin
    * @created 2016-12-15 11:15:31
    * @return String
    */ 
    public String getUserName(){
        return userName;
    }
    /**
    * setUserPass
    * @author yiting lin
    * @created 2016-12-15 11:15:31
    * @param USER_PASS
    */ 
    public void setUserPass(String userPass){
        this.userPass=userPass;
    }
    /**
    * getUserPass
    * @author yiting lin
    * @created 2016-12-15 11:15:31
    * @return String
    */ 
    public String getUserPass(){
        return userPass;
    }
    /**
    * setUserPhoto
    * @author yiting lin
    * @created 2016-12-15 11:15:31
    * @param USER_PHOTO
    */ 
    public void setUserPhoto(String userPhoto){
        this.userPhoto=userPhoto;
    }
    /**
    * getUserPhoto
    * @author yiting lin
    * @created 2016-12-15 11:15:31
    * @return String
    */ 
    public String getUserPhoto(){
        return userPhoto;
    }
    /**
    * setIdNumber
    * @author yiting lin
    * @created 2016-12-15 11:15:31
    * @param ID_NUMBER
    */ 
    public void setIdNumber(String idNumber){
        this.idNumber=idNumber;
    }
    /**
    * getIdNumber
    * @author yiting lin
    * @created 2016-12-15 11:15:31
    * @return String
    */ 
    public String getIdNumber(){
        return idNumber;
    }
    /**
    * setSex
    * @author yiting lin
    * @created 2016-12-15 11:15:31
    * @param SEX
    */ 
    public void setSex(String sex){
        this.sex=sex;
    }
    /**
    * getSex
    * @author yiting lin
    * @created 2016-12-15 11:15:31
    * @return String
    */ 
    public String getSex(){
        return sex;
    }
    /**
    * setEMail
    * @author yiting lin
    * @created 2016-12-15 11:15:31
    * @param E_MAIL
    */ 
    public void setUserEmail(String userEmail){
        this.userEmail=userEmail;
    }
    /**
    * getEMail
    * @author yiting lin
    * @created 2016-12-15 11:15:31
    * @return String
    */ 
    public String getUserEmail(){
        return userEmail;
    }
    /**
    * setTelphone
    * @author yiting lin
    * @created 2016-12-15 11:15:31
    * @param TELPHONE
    */ 
    public void setTelphone(String telphone){
        this.telphone=telphone;
    }
    /**
    * getTelphone
    * @author yiting lin
    * @created 2016-12-15 11:15:31
    * @return String
    */ 
    public String getTelphone(){
        return telphone;
    }
    /**
    * setAddress
    * @author yiting lin
    * @created 2016-12-15 11:15:31
    * @param ADDRESS
    */ 
    public void setAddress(String address){
        this.address=address;
    }
    /**
    * getAddress
    * @author yiting lin
    * @created 2016-12-15 11:15:31
    * @return String
    */ 
    public String getAddress(){
        return address;
    }
    /**
    * setBirthDate
    * @author yiting lin
    * @created 2016-12-15 11:15:31
    * @param BIRTH_DATE
    */ 
    public void setBirthDate(String birthDate){
        this.birthDate=birthDate;
    }
    /**
    * getBirthDate
    * @author yiting lin
    * @created 2016-12-15 11:15:31
    * @return String
    */ 
    public String getBirthDate(){
        return birthDate;
    }
    /**
    * setSortCode
    * @author yiting lin
    * @created 2016-12-15 11:15:31
    * @param SORT_CODE
    */ 
    public void setSortCode(Integer sortCode){
        this.sortCode=sortCode;
    }
    /**
    * getSortCode
    * @author yiting lin
    * @created 2016-12-15 11:15:31
    * @return int
    */ 
    public Integer getSortCode(){
        return sortCode;
    }
    /**
    * setStatus
    * @author yiting lin
    * @created 2016-12-15 11:15:31
    * @param STATUS
    */ 
    public void setStatus(String status){
        this.status=status;
    }
    /**
    * getStatus
    * @author yiting lin
    * @created 2016-12-15 11:15:31
    * @return String
    */ 
    public String getStatus(){
        return status;
    }
    /**
     * getCreateTime
     * @author yiting lin
     * @created 2016-12-15 11:15:31
     * @return String
     */ 
    public Date getCreateTime() {
		return createTime;
	}
    /**
     * setCreateTime
     * @author yiting lin
     * @created 2016-12-15 11:15:31
     * @param STATUS
     */ 
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
    * setRemark
    * @author yiting lin
    * @created 2016-12-15 11:15:31
    * @param REMARK
    */ 
    public void setRemark(String remark){
        this.remark=remark;
    }
    /**
    * getRemark
    * @author yiting lin
    * @created 2016-12-15 11:15:31
    * @return String
    */ 
    public String getRemark(){
        return remark;
    }
    /**
     * getRoles
     * @author zhangxin
     * @category 2016-12-15 11:15:31
     * @return Set<TsRole>
     */
	public Set<TsRole> getRoles() {
		return roles;
	}
	 /**
     * setRoles
     * @author zhangxin
     * @category 2016-12-15 11:15:31
     * @param ROLES
     */
	public void setRoles(Set<TsRole> roles) {
		this.roles = roles;
	}
	/**
     * getOrgan
     * @author zhangxin
     * @category 2016-12-15 11:15:31
     * @return TsOrgan
     */
	public TsOrgan getOrgan() {
		return organ;
	}
	/**
     * setOrgan
     * @author zhangxin
     * @category 2016-12-15 11:15:31
     * @param ORGAN
     */
	public void setOrgan(TsOrgan organ) {
		this.organ = organ;
	}
    
}

