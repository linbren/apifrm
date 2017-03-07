package net.business.system.entity;

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
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
/**
* TS_ROLE 实体类
* @author yiting lin
* created 2016-12-15 11:44:19
*/ 
@SuppressWarnings("serial")
@Entity
@Table(name = "TS_ROLE", schema = "")
public class TsRole implements java.io.Serializable {
    /** ID*/ 
	@Id
 	@GeneratedValue(strategy=GenerationType.IDENTITY) 
    @Column(name = "ID")
    private String id;
    /** APP_ID*/ 
    @Column(name = "APP_ID")
    private String appId;
    /** ROLE_NAME*/ 
    @Column(name = "ROLE_NAME")
    private String roleName;
    /** STATUS*/ 
    @Column(name = "STATUS")
    private String status;
    /** REMARK*/ 
    @Column(name = "REMARK")
    private String remark;
    /** TS_USER_ROLE*/
    @ManyToMany(mappedBy="roles",fetch=FetchType.LAZY,targetEntity=TsUser.class)
    @Cascade({CascadeType.SAVE_UPDATE,CascadeType.REMOVE})
    private Set<TsUser> users = new HashSet<TsUser>();
    /** TS_ROLE_FUNC*/
    @ManyToMany(fetch = FetchType.LAZY,targetEntity=TsFunction.class)
    @Cascade({CascadeType.REFRESH})
	@JoinTable(name = "TS_ROLE_FUNC", joinColumns = {@JoinColumn(name="ROLE_ID")},
	inverseJoinColumns = {@JoinColumn(name="FUNC_ID")})
    private Set<TsFunction> functions = new HashSet<TsFunction>();
    
    /**
    * setId
    * @author yiting lin
    * @created 2016-12-15 11:44:19
    * @param ID
    */ 
    public void setId(String id){
        this.id=id;
    }
    /**
    * getId
    * @author yiting lin
    * @created 2016-12-15 11:44:19
    * @return int identity
    */ 
    public String getId(){
        return id;
    }
    /**
    * setAppId
    * @author yiting lin
    * @created 2016-12-15 11:44:19
    * @param APP_ID
    */ 
    public void setAppId(String appId){
        this.appId=appId;
    }
    /**
    * getAppId
    * @author yiting lin
    * @created 2016-12-15 11:44:19
    * @return String
    */ 
    public String getAppId(){
        return appId;
    }
    /**
    * setRoleName
    * @author yiting lin
    * @created 2016-12-15 11:44:19
    * @param ROLE_NAME
    */ 
    public void setRoleName(String roleName){
        this.roleName=roleName;
    }
    /**
    * getRoleName
    * @author yiting lin
    * @created 2016-12-15 11:44:19
    * @return String
    */ 
    public String getRoleName(){
        return roleName;
    }
    /**
    * setStatus
    * @author yiting lin
    * @created 2016-12-15 11:44:19
    * @param STATUS
    */ 
    public void setStatus(String status){
        this.status=status;
    }
    /**
    * getStatus
    * @author yiting lin
    * @created 2016-12-15 11:44:19
    * @return String
    */ 
    public String getStatus(){
        return status;
    }
    /**
    * setRemark
    * @author yiting lin
    * @created 2016-12-15 11:44:19
    * @param REMARK
    */ 
    public void setRemark(String remark){
        this.remark=remark;
    }
    /**
    * getRemark
    * @author yiting lin
    * @created 2016-12-15 11:44:19
    * @return String
    */ 
    public String getRemark(){
        return remark;
    }
    /**
     * getFunctions
     * @author zhangxin
     * @created 2016-12-15 11:44:19
     * @return Set<TsFunction>
     */
	public Set<TsFunction> getFunctions() {
		return functions;
	}
	/**
	 * setFunctions
	 * @author zhangxin
	 * @created 2016-12-15 11:44:19
	 * @param Set<TsFunction>
	 */
	public void setFunctions(Set<TsFunction> functions) {
		this.functions = functions;
	}
	/**
	 * getUsers
	 * @author zhangxin
	 * @created 2016-12-15 11:45:23
	 * @return Set<TsUser>
	 */
	public Set<TsUser> getUsers() {
		return users;
	}
	/**
	 * setUsers
	 * @author zhangxin
	 * @created 2016-12-15 11:45:23
	 * @param Set<TsUser>
	 */
	public void setUsers(Set<TsUser> users) {
		this.users = users;
	}
    
    
}

