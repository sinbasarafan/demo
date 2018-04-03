package priv.winnie.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import priv.winnie.demo.entity.SysPermission;
import priv.winnie.demo.entity.SysRole;
import priv.winnie.demo.entity.SysUser;
import priv.winnie.demo.mapper.TestMapper;
import priv.winnie.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private TestMapper testMapper;

	@Override
	public List<SysRole> getRoleListById(Long id) {
		return testMapper.getRoleListById(id);
	}

	@Override
	public List<SysPermission> getPermissionList(Long id) {
		return testMapper.getPermissionList(id);
	}

	@Override
	public SysUser findByUsername(String username) {
		return testMapper.findByUsername(username);
	}

}
