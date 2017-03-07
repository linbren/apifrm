package net.business.system.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
* TS_ORGAN_EXT 实体类
* @author yiting lin
* created 2017-01-12 10:27:36
*/ 
@SuppressWarnings("serial")
@Entity
@Table(name = "TS_ORGAN_EXT", schema = "")
public class TsOrganExt implements java.io.Serializable {
    /** ID*/ 
	@Id
    @Column(name = "ID")
	@GeneratedValue(generator="assigned") 
	@GenericGenerator(name = "assigned", strategy = "assigned")
    private String id;
    /** INDUSTRY*/ 
    @Column(name = "INDUSTRY")
    private String industry;
    /** ORG_SUMMARY*/ 
    @Column(name = "ORG_SUMMARY")
    private String orgSummary;
    /** ORG_LOGO*/ 
    @Column(name = "ORG_LOGO")
    private String orgLogo;
    /** ORG_MAP*/ 
    @Column(name = "ORG_MAP")
    private String orgMap;
    /** ORG_IMAGES*/ 
    @Column(name = "ORG_IMAGES")
    private String orgImages;
    /** WEB_URL*/ 
    @Column(name = "WEB_URL")
    private String webUrl;
    /** ORG_EMAIL*/ 
    @Column(name = "ORG_EMAIL")
    private String orgEmail;
    /** ZIP_CODE*/ 
    @Column(name = "ZIP_CODE")
    private String zipCode;
    /** HOT_LINE*/ 
    @Column(name = "HOT_LINE")
    private String hotLine;
    /** FOUND_DATE*/ 
    @Column(name = "FOUND_DATE")
    private String foundDate;
//    @OneToOne(mappedBy="organExt",fetch=FetchType.EAGER,targetEntity=TsOrgan.class)
//    private TsOrgan tsOrgan = new TsOrgan();
    
    /**
    * setId
    * @author yiting lin
    * @created 2017-01-12 10:27:36
    * @param ID
    */ 
    public void setId(String id){
        this.id=id;
    }
    /**
    * getId
    * @author yiting lin
    * @created 2017-01-12 10:27:36
    * @return String
    */ 
    public String getId(){
        return id;
    }
    /**
    * setIndustry
    * @author yiting lin
    * @created 2017-01-12 10:27:36
    * @param INDUSTRY
    */ 
    public void setIndustry(String industry){
        this.industry=industry;
    }
    /**
    * getIndustry
    * @author yiting lin
    * @created 2017-01-12 10:27:36
    * @return String
    */ 
    public String getIndustry(){
        return industry;
    }
    /**
    * setOrgSummary
    * @author yiting lin
    * @created 2017-01-12 10:27:36
    * @param ORG_SUMMARY
    */ 
    public void setOrgSummary(String orgSummary){
        this.orgSummary=orgSummary;
    }
    /**
    * getOrgSummary
    * @author yiting lin
    * @created 2017-01-12 10:27:36
    * @return String
    */ 
    public String getOrgSummary(){
        return orgSummary;
    }
    /**
    * setOrgLogo
    * @author yiting lin
    * @created 2017-01-12 10:27:36
    * @param ORG_LOGO
    */ 
    public void setOrgLogo(String orgLogo){
        this.orgLogo=orgLogo;
    }
    /**
    * getOrgLogo
    * @author yiting lin
    * @created 2017-01-12 10:27:36
    * @return String
    */ 
    public String getOrgLogo(){
        return orgLogo;
    }
    /**
    * setOrgMap
    * @author yiting lin
    * @created 2017-01-12 10:27:36
    * @param ORG_MAP
    */ 
    public void setOrgMap(String orgMap){
        this.orgMap=orgMap;
    }
    /**
    * getOrgMap
    * @author yiting lin
    * @created 2017-01-12 10:27:36
    * @return String
    */ 
    public String getOrgMap(){
        return orgMap;
    }
    /**
    * setOrgImages
    * @author yiting lin
    * @created 2017-01-12 10:27:36
    * @param ORG_IMAGES
    */ 
    public void setOrgImages(String orgImages){
        this.orgImages=orgImages;
    }
    /**
    * getOrgImages
    * @author yiting lin
    * @created 2017-01-12 10:27:36
    * @return String
    */ 
    public String getOrgImages(){
        return orgImages;
    }
    /**
    * setWebUrl
    * @author yiting lin
    * @created 2017-01-12 10:27:36
    * @param WEB_URL
    */ 
    public void setWebUrl(String webUrl){
        this.webUrl=webUrl;
    }
    /**
    * getWebUrl
    * @author yiting lin
    * @created 2017-01-12 10:27:36
    * @return String
    */ 
    public String getWebUrl(){
        return webUrl;
    }
    /**
    * setOrgEmail
    * @author yiting lin
    * @created 2017-01-12 10:27:36
    * @param ORG_EMAIL
    */ 
    public void setOrgEmail(String orgEmail){
        this.orgEmail=orgEmail;
    }
    /**
    * getOrgEmail
    * @author yiting lin
    * @created 2017-01-12 10:27:36
    * @return String
    */ 
    public String getOrgEmail(){
        return orgEmail;
    }
    /**
    * setZipCode
    * @author yiting lin
    * @created 2017-01-12 10:27:36
    * @param ZIP_CODE
    */ 
    public void setZipCode(String zipCode){
        this.zipCode=zipCode;
    }
    /**
    * getZipCode
    * @author yiting lin
    * @created 2017-01-12 10:27:36
    * @return String
    */ 
    public String getZipCode(){
        return zipCode;
    }
    /**
    * setHotLine
    * @author yiting lin
    * @created 2017-01-12 10:27:36
    * @param HOT_LINE
    */ 
    public void setHotLine(String hotLine){
        this.hotLine=hotLine;
    }
    /**
    * getHotLine
    * @author yiting lin
    * @created 2017-01-12 10:27:36
    * @return String
    */ 
    public String getHotLine(){
        return hotLine;
    }
    /**
    * setFoundDate
    * @author yiting lin
    * @created 2017-01-12 10:27:36
    * @param FOUND_DATE
    */ 
    public void setFoundDate(String foundDate){
        this.foundDate=foundDate;
    }
    /**
    * getFoundDate
    * @author yiting lin
    * @created 2017-01-12 10:27:36
    * @return String
    */ 
    public String getFoundDate(){
        return foundDate;
    }
/*	public TsOrgan getTsOrgan() {
		return tsOrgan;
	}
	public void setTsOrgan(TsOrgan tsOrgan) {
		this.tsOrgan = tsOrgan;
	}*/
    
    
}

