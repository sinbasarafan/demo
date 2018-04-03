package priv.winnie.demo.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class SysRole implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1715997905773269974L;
	
	private Long id;
	private String name;
	private String type;

}
