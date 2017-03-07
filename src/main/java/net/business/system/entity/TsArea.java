package net.business.system.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
/**
* TS_AREA 实体类
* @author yiting lin
* created 2016-12-15 15:19:58
*/ 
@SuppressWarnings("serial")
@Entity
@Table(name = "TS_AREA", schema = "")
public class TsArea implements java.io.Serializable {
    /** ID*/ 
    @Id
    @GeneratedValue(generator="assigned") 
	@GenericGenerator(name = "assigned", strategy = "assigned")
    @Column(name = "ID")
    private String id;
    /** AREA_NAME*/ 
    @Column(name = "AREA_NAME")
    private String areaName;
    /** PARENT_ID*/ 
    @Column(name = "PARENT_ID")
    private String parentId;
    /** 区号*/ 
    @Column(name = "AREA_CODE")
    private String areaCode;
    /** TELPHONE*/ 
    @Column(name = "TELPHONE")
    private String telphone;
    /** ZIP_CODE*/ 
    @Column(name = "ZIP_CODE")
    private String zipCode;
    /** AREA_LEVEL*/ 
    @Column(name = "AREA_LEVEL")
    private Integer areaLevel;
    /** IS_LEAF*/ 
    @Column(name = "IS_LEAF")
    private String isLeaf;
    /** LATITUDE*/ 
    @Column(name = "LATITUDE")
    private Double latitude;
    /** LONGITUDE*/ 
    @Column(name = "LONGITUDE")
    private Double longitude;
    /** SORT_CODE*/ 
    @Column(name = "SORT_CODE")
    private Integer sortCode;
    /** REMARK*/ 
    @Column(name = "REMARK")
    private String remark;
    @OneToMany(mappedBy="area",fetch=FetchType.EAGER)
    @Cascade(CascadeType.REFRESH)
    private Set<TsOrgan> organs = new HashSet<TsOrgan>();
    
    /**
    * setId
    * @author yiting lin
    * @created 2016-12-15 15:19:58
    * @param ID
    */ 
    public void setId(String id){
        this.id=id;
    }
    /**
    * getId
    * @author yiting lin
    * @created 2016-12-15 15:19:58
    * @return String
    */ 
    public String getId(){
        return id;
    }
    /**
    * setAreaName
    * @author yiting lin
    * @created 2016-12-15 15:19:58
    * @param AREA_NAME
    */ 
    public void setAreaName(String areaName){
        this.areaName=areaName;
    }
    /**
    * getAreaName
    * @author yiting lin
    * @created 2016-12-15 15:19:58
    * @return String
    */ 
    public String getAreaName(){
        return areaName;
    }
    /**
    * setParentId
    * @author yiting lin
    * @created 2016-12-15 15:19:58
    * @param PARENT_ID
    */ 
    public void setParentId(String parentId){
        this.parentId=parentId;
    }
    /**
    * getParentId
    * @author yiting lin
    * @created 2016-12-15 15:19:58
    * @return String
    */ 
    public String getParentId(){
        return parentId;
    }
    /**
    * setAreaCode
    * @author yiting lin
    * @created 2016-12-15 15:19:58
    * @param AREA_CODE
    */ 
    public void setAreaCode(String areaCode){
        this.areaCode=areaCode;
    }
    /**
    * getAreaCode
    * @author yiting lin
    * @created 2016-12-15 15:19:58
    * @return String
    */ 
    public String getAreaCode(){
        return areaCode;
    }
    /**
    * setTelphone
    * @author yiting lin
    * @created 2016-12-15 15:19:58
    * @param TELPHONE
    */ 
    public void setTelphone(String telphone){
        this.telphone=telphone;
    }
    /**
    * getTelphone
    * @author yiting lin
    * @created 2016-12-15 15:19:58
    * @return String
    */ 
    public String getTelphone(){
        return telphone;
    }
    /**
    * setZipCode
    * @author yiting lin
    * @created 2016-12-15 15:19:58
    * @param ZIP_CODE
    */ 
    public void setZipCode(String zipCode){
        this.zipCode=zipCode;
    }
    /**
    * getZipCode
    * @author yiting lin
    * @created 2016-12-15 15:19:58
    * @return String
    */ 
    public String getZipCode(){
        return zipCode;
    }
    /**
    * setAreaLevel
    * @author yiting lin
    * @created 2016-12-15 15:19:58
    * @param AREA_LEVEL
    */ 
    public void setAreaLevel(Integer areaLevel){
        this.areaLevel=areaLevel;
    }
    /**
    * getAreaLevel
    * @author yiting lin
    * @created 2016-12-15 15:19:58
    * @return int
    */ 
    public Integer getAreaLevel(){
        return areaLevel;
    }
    /**
    * setIsLeaf
    * @author yiting lin
    * @created 2016-12-15 15:19:58
    * @param IS_LEAF
    */ 
    public void setIsLeaf(String isLeaf){
        this.isLeaf=isLeaf;
    }
    /**
    * getIsLeaf
    * @author yiting lin
    * @created 2016-12-15 15:19:58
    * @return String
    */ 
    public String getIsLeaf(){
        return isLeaf;
    }
    /**
    * setLatitude
    * @author yiting lin
    * @created 2016-12-15 15:19:58
    * @param LATITUDE
    */ 
    public void setLatitude(Double latitude){
        this.latitude=latitude;
    }
    /**
    * getLatitude
    * @author yiting lin
    * @created 2016-12-15 15:19:58
    * @return Long
    */ 
    public Double getLatitude(){
        return latitude;
    }
    /**
    * setLongitude
    * @author yiting lin
    * @created 2016-12-15 15:19:58
    * @param LONGITUDE
    */ 
    public void setLongitude(Double longitude){
        this.longitude=longitude;
    }
    /**
    * getLongitude
    * @author yiting lin
    * @created 2016-12-15 15:19:58
    * @return Long
    */ 
    public Double getLongitude(){
        return longitude;
    }
    /**
    * setSortCode
    * @author yiting lin
    * @created 2016-12-15 15:19:58
    * @param SORT_CODE
    */ 
    public void setSortCode(Integer sortCode){
        this.sortCode=sortCode;
    }
    /**
    * getSortCode
    * @author yiting lin
    * @created 2016-12-15 15:19:58
    * @return int
    */ 
    public Integer getSortCode(){
        return sortCode;
    }
    /**
    * setRemark
    * @author yiting lin
    * @created 2016-12-15 15:19:58
    * @param REMARK
    */ 
    public void setRemark(String remark){
        this.remark=remark;
    }
    /**
    * getRemark
    * @author yiting lin
    * @created 2016-12-15 15:19:58
    * @return String
    */ 
    public String getRemark(){
        return remark;
    }
    /**
     * getOrgans
     * @author yiting lin
     * @created 2016-12-15 15:19:58
     * @return Set<TsOrgan>
     */ 
	public Set<TsOrgan> getOrgans() {
		return organs;
	}
	/**
     * setOrgans
     * @author yiting lin
     * @created 2016-12-15 15:19:58
     * @return REMARK
     */
	public void setOrgans(Set<TsOrgan> organs) {
		this.organs = organs;
	}
    
}

