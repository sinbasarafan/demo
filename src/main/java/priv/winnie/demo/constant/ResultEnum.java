package priv.winnie.demo.constant;

public enum ResultEnum {
	
	SUCCESS("1", "成功"),
	FAIL("0", "失败"),
	ERROR("-1", "异常"),
	UN_LOGIN("-2", "未登录");
	
	private String code;
    private String message;
    
    private ResultEnum(String code, String message) {
    	this.code = code;
    	this.message = message;
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
