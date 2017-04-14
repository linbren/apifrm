package net.business.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
/**
* TS_CITY_WEATHER 实体类
* @author yiting lin
* created 2017-03-09 09:26:52
*/ 
@SuppressWarnings("serial")
@Entity
@Table(name = "TS_CITY_WEATHER", schema = "")
public class TsCityWeather implements java.io.Serializable {
    /** id*/ 
	@Id
	@GeneratedValue(generator="assigned") 
	@GenericGenerator(name = "assigned", strategy = "assigned")
    @Column(name = "id")
    private String id;
    /** city*/ 
    @Column(name = "city")
    private String city;
    /** py*/ 
    @Column(name = "py")
    private String py;
    /** fpy*/ 
    @Column(name = "fpy")
    private String fpy;
    /**
    * setId
    * @author yiting lin
    * @created 2017-03-09 09:26:52
    * @param id
    */ 
    public void setId(String id){
        this.id=id;
    }
    /**
    * getId
    * @author yiting lin
    * @created 2017-03-09 09:26:52
    * @return String
    */ 
    public String getId(){
        return id;
    }
    /**
    * setCity
    * @author yiting lin
    * @created 2017-03-09 09:26:52
    * @param city
    */ 
    public void setCity(String city){
        this.city=city;
    }
    /**
    * getCity
    * @author yiting lin
    * @created 2017-03-09 09:26:52
    * @return String
    */ 
    public String getCity(){
        return city;
    }
    /**
    * setPy
    * @author yiting lin
    * @created 2017-03-09 09:26:52
    * @param py
    */ 
    public void setPy(String py){
        this.py=py;
    }
    /**
    * getPy
    * @author yiting lin
    * @created 2017-03-09 09:26:52
    * @return String
    */ 
    public String getPy(){
        return py;
    }
    /**
    * setFpy
    * @author yiting lin
    * @created 2017-03-09 09:26:52
    * @param fpy
    */ 
    public void setFpy(String fpy){
        this.fpy=fpy;
    }
    /**
    * getFpy
    * @author yiting lin
    * @created 2017-03-09 09:26:52
    * @return String
    */ 
    public String getFpy(){
        return fpy;
    }
}

