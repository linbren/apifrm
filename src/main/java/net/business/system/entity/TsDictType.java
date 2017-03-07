package net.business.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
/**
* TS_DICTTYPE 实体类
* @author yiting lin
* created 2016-12-30 09:56:16
*/ 
@SuppressWarnings("serial")
@Entity
@Table(name = "TS_DICTTYPE", schema = "")
public class TsDictType implements java.io.Serializable {
    /** ID*/ 
	@Id
    @Column(name = "ID")
    private String id;
    /** DICT_NAME*/ 
    @Column(name = "DICT_NAME")
    private String dictName;
    /** IS_VALID*/ 
    @Column(name = "IS_VALID")
    private String isValid;
    /**
    * setId
    * @author yiting lin
    * @created 2016-12-30 09:56:16
    * @param ID
    */ 
    public void setId(String id){
        this.id=id;
    }
    /**
    * getId
    * @author yiting lin
    * @created 2016-12-30 09:56:16
    * @return String
    */ 
    public String getId(){
        return id;
    }
    /**
    * setDictName
    * @author yiting lin
    * @created 2016-12-30 09:56:16
    * @param DICT_NAME
    */ 
    public void setDictName(String dictName){
        this.dictName=dictName;
    }
    /**
    * getDictName
    * @author yiting lin
    * @created 2016-12-30 09:56:16
    * @return String
    */ 
    public String getDictName(){
        return dictName;
    }
    /**
    * setIsValid
    * @author yiting lin
    * @created 2016-12-30 09:56:16
    * @param IS_VALID
    */ 
    public void setIsValid(String isValid){
        this.isValid=isValid;
    }
    /**
    * getIsValid
    * @author yiting lin
    * @created 2016-12-30 09:56:16
    * @return String
    */ 
    public String getIsValid(){
        return isValid;
    }
}

