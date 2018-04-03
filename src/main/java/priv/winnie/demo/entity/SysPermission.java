package priv.winnie.demo.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class SysPermission implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 245040488687567142L;
	
	private Long id;
	private String url;
	private String name;

}
