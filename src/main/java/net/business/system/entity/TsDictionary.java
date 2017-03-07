package net.business.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
* TS_DICTIONARY 实体类
* @author yiting lin
* created 2016-12-08 16:41:18
*/ 
@SuppressWarnings("serial")
@Entity
@Table(name = "TS_DICTIONARY", schema = "")
public class TsDictionary implements java.io.Serializable {
    /** ID*/ 
    @Id
 	@GeneratedValue(strategy=GenerationType.IDENTITY)  	
    @Column(name = "ID")
    private String id;
    /** DICT_TYPE*/ 
    @Column(name = "DICT_TYPE")
    private String dictType;
    /** ITEM_CODE*/ 
    @Column(name = "ITEM_CODE")
    private String itemCode;
    /** ITEM_NAME*/ 
    @Column(name = "ITEM_NAME")
    private String itemName;
    /** ITEM_VALUE*/ 
    @Column(name = "ITEM_VALUE")
    private String itemValue;
    /** PARENT_CODE*/ 
    @Column(name = "PARENT_CODE")
    private String parentCode;
    /** SORT_CODE*/ 
    @Column(name = "SORT_CODE")
    private Integer sortCode;
    /** REMARK*/ 
    @Column(name = "REMARK")
    private String remark;
    /**
    * setId
    * @author yiting lin
    * @created 2016-12-08 16:41:18
    * @param ID
    */ 
    public void setId(String id){
        this.id=id;
    }
    /**
    * getId
    * @author yiting lin
    * @created 2016-12-08 16:41:18
    * @return String
    */ 
    public String getId(){
        return id;
    }
    /**
    * setDictType
    * @author yiting lin
    * @created 2016-12-08 16:41:18
    * @param DICT_TYPE
    */ 
    public void setDictType(String dictType){
        this.dictType=dictType;
    }
    /**
    * getDictType
    * @author yiting lin
    * @created 2016-12-08 16:41:18
    * @return String
    */ 
    public String getDictType(){
        return dictType;
    }
    /**
    * setItemCode
    * @author yiting lin
    * @created 2016-12-08 16:41:18
    * @param ITEM_CODE
    */ 
    public void setItemCode(String itemCode){
        this.itemCode=itemCode;
    }
    /**
    * getItemCode
    * @author yiting lin
    * @created 2016-12-08 16:41:18
    * @return String
    */ 
    public String getItemCode(){
        return itemCode;
    }
    /**
    * setItemName
    * @author yiting lin
    * @created 2016-12-08 16:41:18
    * @param ITEM_NAME
    */ 
    public void setItemName(String itemName){
        this.itemName=itemName;
    }
    /**
    * getItemName
    * @author yiting lin
    * @created 2016-12-08 16:41:18
    * @return String
    */ 
    public String getItemName(){
        return itemName;
    }
    /**
    * setItemValue
    * @author yiting lin
    * @created 2016-12-08 16:41:18
    * @param ITEM_VALUE
    */ 
    public void setItemValue(String itemValue){
        this.itemValue=itemValue;
    }
    /**
    * getItemValue
    * @author yiting lin
    * @created 2016-12-08 16:41:18
    * @return String
    */ 
    public String getItemValue(){
        return itemValue;
    }
    /**
    * setParentCode
    * @author yiting lin
    * @created 2016-12-08 16:41:18
    * @param PARENT_CODE
    */ 
    public void setParentCode(String parentCode){
        this.parentCode=parentCode;
    }
    /**
    * getParentCode
    * @author yiting lin
    * @created 2016-12-08 16:41:18
    * @return String
    */ 
    public String getParentCode(){
        return parentCode;
    }
    /**
    * setSortCode
    * @author yiting lin
    * @created 2016-12-08 16:41:18
    * @param SORT_CODE
    */ 
    public void setSortCode(Integer sortCode){
        this.sortCode=sortCode;
    }
    /**
    * getSortCode
    * @author yiting lin
    * @created 2016-12-08 16:41:18
    * @return int
    */ 
    public Integer getSortCode(){
        return sortCode;
    }
    /**
    * setRemark
    * @author yiting lin
    * @created 2016-12-08 16:41:18
    * @param REMARK
    */ 
    public void setRemark(String remark){
        this.remark=remark;
    }
    /**
    * getRemark
    * @author yiting lin
    * @created 2016-12-08 16:41:18
    * @return String
    */ 
    public String getRemark(){
        return remark;
    }
}

