package priv.winnie.demo.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class SysUser implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2755426816321248516L;
	
	private Long id;
	private String email;
	private String nickName;
	private String passWord;
	private String userName;
	private Date createTime;
	private Date lastLoginTime;
	private String status;

}
