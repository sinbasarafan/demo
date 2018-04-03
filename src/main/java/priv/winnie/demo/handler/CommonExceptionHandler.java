package priv.winnie.demo.handler;

import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;
import priv.winnie.demo.entity.Result;
import priv.winnie.demo.exception.ResultException;
import priv.winnie.demo.util.ResultUtils;

@ControllerAdvice
@Slf4j
public class CommonExceptionHandler {
	
	@ExceptionHandler(value = ResultException.class)
	@ResponseBody
	public Result<Object> ResultExceptionHandle(Exception e) {
		ResultException exception = (ResultException) e;
		return ResultUtils.error(exception.getCode(), exception.getMessage(), exception.getObject());
	}
	
	@ExceptionHandler({ UnauthenticatedException.class, UnauthorizedException.class })
	public String shiroExceptionHandle(Exception e) {
		return "redirect:/error";
	}
	
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public void Handle(Exception e) throws Exception {
		log.info("系统异常： {}", e.getMessage(), e);
        throw e;
	}

}
