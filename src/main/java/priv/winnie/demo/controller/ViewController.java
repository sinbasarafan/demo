package priv.winnie.demo.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@Api(tags ="页面跳转")
public class ViewController {
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	@ApiOperation(value = "跳转到登录页面", notes = "方法的备注说明")
    public String toLogin(HttpServletRequest request, HashMap<String, Object> map) {
		map.put("contextPath", request.getContextPath());
		log.info("contextPath : {}", request.getContextPath());
		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser.isAuthenticated()) {
			return "redirect:/index";
		}
        return "/login";
    }
	
	@RequestMapping(value = "/qrcode", method = RequestMethod.GET)
	@ApiOperation(value="跳转到二维码页面")
    public String toQrcode(HttpServletRequest request, HashMap<String, Object> map) {
		map.put("contextPath", request.getContextPath());
		log.info("contextPath : {}", request.getContextPath());
        return "/qrcode";
    }
	
	@RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
	@ApiOperation(value="跳转到首页")
    public String toIndex(HttpServletRequest request, HashMap<String, Object> map) {
		map.put("contextPath", request.getContextPath());
		log.info("contextPath : {}", request.getContextPath());
        return "/index";
    }
	
	@RequestMapping(value = "/scanLogin", method = RequestMethod.GET)
	@ApiOperation(value="跳转到扫码登录页面")
    public String toScanLogin(HttpServletRequest request, HashMap<String, Object> map) {
		map.put("contextPath", request.getContextPath());
		log.info("contextPath : {}", request.getContextPath());
		if (request.getSession().getAttribute("user") != null && StringUtils.isNotEmpty(request.getSession().getAttribute("user").toString())) {
			return "redirect:/index";
		}
        return "/scanLogin";
    }

}
