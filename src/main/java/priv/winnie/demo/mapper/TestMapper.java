package priv.winnie.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import priv.winnie.demo.entity.SysPermission;
import priv.winnie.demo.entity.SysRole;
import priv.winnie.demo.entity.SysUser;

@Mapper
public interface TestMapper {
	
	@Select("select id, email, nick_name, pass_word, user_name, create_time, last_login_time, status from sys_user")
	@Results({
        @Result(property = "nickName", column = "nick_name"),
        @Result(property = "passWord", column = "pass_word"),
        @Result(property = "userName", column = "user_name"),
        @Result(property = "createTime", column = "create_time"),
        @Result(property = "lastLoginTime", column = "last_login_time")
    })
    List<SysUser> selectAll();
	
	@Select("select id, email, nick_name, pass_word, user_name, create_time, last_login_time, status from sys_user where id = #{id}")
	@Results({
        @Result(property = "nickName", column = "nick_name"),
        @Result(property = "passWord", column = "pass_word"),
        @Result(property = "userName", column = "user_name"),
        @Result(property = "createTime", column = "create_time"),
        @Result(property = "lastLoginTime", column = "last_login_time")
    })
	SysUser selectById(@Param("id") Long id);

	@Select("select * from sys_role where id in (select role_id from sys_user_role where user_id = #{id})")
	List<SysRole> getRoleListById(@Param("id") Long id);
	
	@Select("select * from sys_permission where id in (select permission_id from sys_role_permission where role_id = #{id})")
	List<SysPermission> getPermissionList(@Param("id") Long id);

	@Select("select * from sys_user where user_name = #{username}")
	@Results({
        @Result(property = "nickName", column = "nick_name"),
        @Result(property = "passWord", column = "pass_word"),
        @Result(property = "userName", column = "user_name"),
        @Result(property = "createTime", column = "create_time"),
        @Result(property = "lastLoginTime", column = "last_login_time")
    })
	SysUser findByUsername(@Param("username") String username);

}
