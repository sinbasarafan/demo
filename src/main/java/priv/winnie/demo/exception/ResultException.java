package priv.winnie.demo.exception;

import priv.winnie.demo.constant.ResultEnum;

public class ResultException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9201978012903535508L;
	
	private String code;
	private Object object;
	
	public ResultException(String code, String message) {
		super(message);
		this.code = code;
	}
	
	public ResultException(String code, String message, Object object) {
		super(message);
		this.code = code;
		this.object = object;
	}
	
	public ResultException(ResultEnum result, Object object) {
		super(result.getMessage());
		this.code = result.getCode();
		this.object = object;
	}
	
	public ResultException(ResultEnum result) {
		super(result.getMessage());
		this.code = result.getCode();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}
	
}
