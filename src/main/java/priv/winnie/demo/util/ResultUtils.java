package priv.winnie.demo.util;

import priv.winnie.demo.constant.ResultEnum;
import priv.winnie.demo.entity.Result;

public class ResultUtils {
	
	public static Result<Object> success(Object object) {
		Result<Object> result = new Result<Object>();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMessage());
        result.setData(object);
        return result;
	}
	public static Result<Object> error(String code, String msg, Object object) {
		Result<Object> result = new Result<Object>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(object);
        return result;
	}
	
	public static Result<Object> success() {
        return success(null);
    }
	
	public static Result<Object> error(String code, String msg) {
        return error(code, msg, null);
    }

}
