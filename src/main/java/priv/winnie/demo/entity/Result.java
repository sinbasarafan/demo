package priv.winnie.demo.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class Result<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1298621958516700076L;
	private String code;
	private String msg;
	private T data;

}
