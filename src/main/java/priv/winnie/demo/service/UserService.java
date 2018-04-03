package priv.winnie.demo.service;

import java.util.List;

import priv.winnie.demo.entity.SysPermission;
import priv.winnie.demo.entity.SysRole;
import priv.winnie.demo.entity.SysUser;

public interface UserService {

	List<SysRole> getRoleListById(Long id);

	List<SysPermission> getPermissionList(Long id);

	SysUser findByUsername(String username);

}
