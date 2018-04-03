package priv.winnie.demo.config;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import priv.winnie.demo.shiro.CustomCredentialsMatcher;
import priv.winnie.demo.shiro.MyShiroRealm;

@Configuration
public class ShiroConfig {
	
	@Bean
	public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
		bean.setSecurityManager(securityManager);
		bean.setLoginUrl("/login");
		Map<String, String> filterChainMap = new LinkedHashMap<String, String>();
		filterChainMap.put("/css/**", "anon");
		filterChainMap.put("/img/**", "anon");
		filterChainMap.put("/js/**", "anon");
		filterChainMap.put("/login", "anon");
		filterChainMap.put("/*", "authc");
		bean.setFilterChainDefinitionMap(filterChainMap);
		return bean;
	}
	
	@Bean
	public CredentialsMatcher credentialsMatcher() {
        return new CustomCredentialsMatcher();
    }
	
	@Bean
	public AuthorizingRealm myShiroRealm(CredentialsMatcher credentialsMatcher) {
		AuthorizingRealm myShiroRealm = new MyShiroRealm();
		myShiroRealm.setCredentialsMatcher(credentialsMatcher);
		return myShiroRealm;
	}
	
	@Bean
	public SecurityManager securityManager(AuthorizingRealm myShiroRealm) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(myShiroRealm);
		return securityManager;
	}
	
	@Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
	
	@Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

}
