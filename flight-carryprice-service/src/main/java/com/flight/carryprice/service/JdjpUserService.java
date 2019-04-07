package com.flight.carryprice.service;

import com.flight.carryprice.common.service.BaseService;
import com.flight.carryprice.entity.*;
import com.github.pagehelper.PageInfo;
import java.util.List;
import java.util.Map;

public interface JdjpUserService extends BaseService<AlUsers> {

	/**
	 * 根据名称和系统编号查询用户，如果此用户不是该系统的用户，则返回null
	 * 
	 * @param userName
	 * @param appCode
	 * @return
	 */
	public AlUsers findUserByUserNameAndAppCode(String userName, Integer appCode);

	/**
	 * 初始化左侧菜单，并存贮到session中
	 * 
	 * @param userCode
	 * @param session
	 */
	public Map<String, Object> initLeftMenu(Integer appCode, String userCode);

	/**
	 * 用户分页列表
	 * 
	 * @param queryUser
	 * @return
	 */
	public PageInfo<AlUsers> userPagination(Integer pageNo, Integer pageSize, AlUsers queryUser);

	/**
	 * 根据用户id查询用户，并携带角色信息
	 * 
	 * @param userId
	 * @return
	 */
	public AlUsers findUserByIdWithRole(Integer userId);

	/**
	 * 查询所有角色
	 * 
	 * @param appCode
	 * @return
	 */
	public List<AlPopedomTblrole> queryAllRole(Integer appCode);

	/**
	 * 修改用户和用户角色
	 * 
	 * @param user
	 */
	public void updateUserWithRole(AlUsers user, String newRoleCodes);

	/**
	 * 添加用户信息并分配角色
	 * 
	 * @return -1：用户名已存在 -2：没有角色信息 1：成功
	 */
	public Integer addUserWithRole(AlUsers user, String newRoleCodes);

	/**
	 * 修改个人密码
	 * 
	 * @param userId
	 * @param password
	 * @param newPassword
	 * @return -1：信息不全 -2：原密码输入错误 -3：用户不存在 1：成功
	 */
	public Integer updateMyUserPassword(Integer userId, String hashPassword, String hashNewPassword);

	/**
	 * 分页查询角色列表
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param queryRole
	 * @return
	 */
	public PageInfo<AlPopedomTblrole> rolePagination(Integer pageNo, Integer pageSize, AlPopedomTblrole queryRole);

	/**
	 * 查询所有的模块及菜单
	 * 
	 * @return
	 */
	public List<AlPopedomTblmenu> queryAllMenuWithModel(Integer appCode);

	/**
	 * 添加角色
	 * 
	 * @param rolename
	 *            角色名称
	 * @param moduleList
	 *            角色权限
	 * @return
	 */
	public void addRole(String rolename, List<AlPopedomTblmodule> moduleList);

	/**
	 * 根据moduleCode查询
	 * 
	 * @param moduleCode
	 * @return
	 */
	public AlPopedomTblmodule findByModuleCode(Integer moduleCode);

	/**
	 * 根据id查询角色
	 * 
	 * @param roleId
	 *            角色id
	 * @return
	 */
	public AlPopedomTblrole findRoleById(Integer roleId);

	/**
	 * 根据角色id查询权限
	 * 
	 * @param roleId
	 * @return
	 */
	public List<AlPopedomTblrolepopedom> queryModuleByRoleCode(Integer roleCode);

	/**
	 * 修改角色
	 * 
	 * @param role
	 *            角色
	 * @param moduleList
	 *            角色权限
	 * @return
	 */
	public void updateRole(AlPopedomTblrole role, List<AlPopedomTblmodule> moduleList);

	/**
	 * 禁用角色
	 * 
	 * @param roleId
	 * @return
	 */
	public void disableRole(Integer roleId);

	/**
	 * 启用角色
	 * 
	 * @param roleId
	 * @return
	 */
	public void enableRole(Integer roleId);

	/**
	 * 禁用用户
	 * 
	 * @param userId
	 * @return
	 */
	public void disableUser(Integer userId);

	/**
	 * 启用用户
	 * 
	 * @param userId
	 * @return
	 */
	public void enableUser(Integer userId);
}
