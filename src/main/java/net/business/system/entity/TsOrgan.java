package net.business.system.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
/**
* TS_ORGAN 实体类
* @author yiting lin
* created 2017-01-10 15:50:00
*/ 
@SuppressWarnings("serial")
@Entity
@Table(name = "TS_ORGAN", schema = "")
public class TsOrgan implements java.io.Serializable {
    /** ID*/ 
	@Id
    @GeneratedValue(generator="assigned") 
	@GenericGenerator(name = "assigned", strategy = "assigned")
    @Column(name = "ID")
    private String id;
    /** ORG_NAME*/ 
    @Column(name = "ORG_NAME")
    private String orgName;
    /** PARENT_ID*/ 
    @Column(name = "PARENT_ID")
    private String parentId;
    /** CHARGER*/ 
    @Column(name = "CHARGER")
    private String charger;
    /** TELPHONE*/ 
    @Column(name = "TELPHONE")
    private String telphone;
    /** FAX*/ 
    @Column(name = "FAX")
    private String fax;
    /** ADDRESS*/ 
    @Column(name = "ADDRESS")
    private String address;
    /** LONGITUDE*/ 
    @Column(name = "LONGITUDE")
    private Double longitude;
    /** LATITUDE*/ 
    @Column(name = "LATITUDE")
    private Double latitude;
    /** AREA_ID*/ 
    /*@Column(name = "AREA_ID")
    private String areaId;*/
    /** SORT_CODE*/ 
    @Column(name = "SORT_CODE")
    private Integer sortCode;
    /** REMARK*/ 
    @Column(name = "REMARK")
    private String remark;
    @ManyToOne(targetEntity=TsArea.class,fetch=FetchType.EAGER)
    @JoinColumn(name="AREA_ID")
    @Cascade({CascadeType.REFRESH})
    private TsArea area = new TsArea();
    
    @OneToMany(mappedBy="organ",fetch=FetchType.EAGER,
    		targetEntity=TsUser.class)
    private Set<TsUser> users = new HashSet<TsUser>();
    
    @OneToOne(fetch=FetchType.LAZY,targetEntity=TsOrganExt.class)
    @Cascade({CascadeType.SAVE_UPDATE,CascadeType.REMOVE})
    @PrimaryKeyJoinColumn
    private TsOrganExt organExt = new TsOrganExt();
    
    /**
    * setId
    * @author yiting lin
    * @created 2017-01-10 15:50:00
    * @param ID
    */ 
    public void setId(String id){
        this.id=id;
    }
    /**
    * getId
    * @author yiting lin
    * @created 2017-01-10 15:50:00
    * @return String
    */ 
    public String getId(){
        return id;
    }
    /**
    * setOrgName
    * @author yiting lin
    * @created 2017-01-10 15:50:00
    * @param ORG_NAME
    */ 
    public void setOrgName(String orgName){
        this.orgName=orgName;
    }
    /**
    * getOrgName
    * @author yiting lin
    * @created 2017-01-10 15:50:00
    * @return String
    */ 
    public String getOrgName(){
        return orgName;
    }
    /**
    * setParentId
    * @author yiting lin
    * @created 2017-01-10 15:50:00
    * @param PARENT_ID
    */ 
    public void setParentId(String parentId){
        this.parentId=parentId;
    }
    /**
    * getParentId
    * @author yiting lin
    * @created 2017-01-10 15:50:00
    * @return String
    */ 
    public String getParentId(){
        return parentId;
    }
    /**
    * setCharger
    * @author yiting lin
    * @created 2017-01-10 15:50:00
    * @param CHARGER
    */ 
    public void setCharger(String charger){
        this.charger=charger;
    }
    /**
    * getCharger
    * @author yiting lin
    * @created 2017-01-10 15:50:00
    * @return String
    */ 
    public String getCharger(){
        return charger;
    }
    /**
    * setTelphone
    * @author yiting lin
    * @created 2017-01-10 15:50:00
    * @param TELPHONE
    */ 
    public void setTelphone(String telphone){
        this.telphone=telphone;
    }
    /**
    * getTelphone
    * @author yiting lin
    * @created 2017-01-10 15:50:00
    * @return String
    */ 
    public String getTelphone(){
        return telphone;
    }
    /**
    * setFax
    * @author yiting lin
    * @created 2017-01-10 15:50:00
    * @param FAX
    */ 
    public void setFax(String fax){
        this.fax=fax;
    }
    /**
    * getFax
    * @author yiting lin
    * @created 2017-01-10 15:50:00
    * @return String
    */ 
    public String getFax(){
        return fax;
    }
    /**
    * setAddress
    * @author yiting lin
    * @created 2017-01-10 15:50:00
    * @param ADDRESS
    */ 
    public void setAddress(String address){
        this.address=address;
    }
    /**
    * getAddress
    * @author yiting lin
    * @created 2017-01-10 15:50:00
    * @return String
    */ 
    public String getAddress(){
        return address;
    }
    /**
    * setLongitude
    * @author yiting lin
    * @created 2017-01-10 15:50:00
    * @param LONGITUDE
    */ 
    public void setLongitude(Double longitude){
        this.longitude=longitude;
    }
    /**
    * getLongitude
    * @author yiting lin
    * @created 2017-01-10 15:50:00
    * @return Double
    */ 
    public Double getLongitude(){
        return longitude;
    }
    /**
    * setLatitude
    * @author yiting lin
    * @created 2017-01-10 15:50:00
    * @param LATITUDE
    */ 
    public void setLatitude(Double latitude){
        this.latitude=latitude;
    }
    /**
    * getLatitude
    * @author yiting lin
    * @created 2017-01-10 15:50:00
    * @return Double
    */ 
    public Double getLatitude(){
        return latitude;
    }
    /**
    * setSortCode
    * @author yiting lin
    * @created 2017-01-10 15:50:00
    * @param SORT_CODE
    */ 
    public void setSortCode(Integer sortCode){
        this.sortCode=sortCode;
    }
    /**
    * getSortCode
    * @author yiting lin
    * @created 2017-01-10 15:50:00
    * @return Integer
    */ 
    public Integer getSortCode(){
        return sortCode;
    }
    /**
    * setRemark
    * @author yiting lin
    * @created 2017-01-10 15:50:00
    * @param REMARK
    */ 
    public void setRemark(String remark){
        this.remark=remark;
    }
    /**
    * getRemark
    * @author yiting lin
    * @created 2017-01-10 15:50:00
    * @return String
    */ 
    public String getRemark(){
        return remark;
    }
    /**
     * getArea
     * @author yiting lin
     * @created 2017-01-10 15:50:00
     * @return TsArea
     */
	public TsArea getArea() {
		return area;
	}
	/**
     * setArea
     * @author yiting lin
     * @created 2017-01-10 15:50:00
     * @return AREA
     */
	public void setArea(TsArea area) {
		this.area = area;
	}
	 /**
     * getUsers
     * @author yiting lin
     * @created 2017-01-10 15:50:00
     * @return Set<TsUser>
     */
	public Set<TsUser> getUsers() {
		return users;
	}
	/**
     * setUsers
     * @author yiting lin
     * @created 2017-01-10 15:50:00
     * @return USERS
     */
	public void setUsers(Set<TsUser> users) {
		this.users = users;
	}
	public TsOrganExt getOrganExt() {
		return organExt;
	}
	public void setOrganExt(TsOrganExt organExt) {
		this.organExt = organExt;
	}
    
    
}

