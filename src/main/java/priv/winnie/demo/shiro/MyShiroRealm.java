package priv.winnie.demo.shiro;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import priv.winnie.demo.entity.SysPermission;
import priv.winnie.demo.entity.SysRole;
import priv.winnie.demo.entity.SysUser;
import priv.winnie.demo.service.UserService;

public class MyShiroRealm extends AuthorizingRealm {
	
	@Resource
    private UserService userService;
	
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    	
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        SysUser user = (SysUser) principals.getPrimaryPrincipal();
        // 根据userId 查询roleList
        List<SysRole> roleList = userService.getRoleListById(user.getId());
        for (SysRole role: roleList) {
            authorizationInfo.addRole(role.getName());
            // 根据roleId 查询permissionList
            List<SysPermission> permissionList = userService.getPermissionList(role.getId());
            for (SysPermission p: permissionList) {
                authorizationInfo.addStringPermission(p.getName());
            }
        }
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        if (username == null) {
        	throw new AccountException("请输入登录账号！");
        }
        SysUser user = userService.findByUsername(username);
        if (user == null) {
        	throw new UnknownAccountException("您所输入的帐号[" + username + "]不存在或者已被删除！");
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassWord(), getName());
        return info;
    }

}
