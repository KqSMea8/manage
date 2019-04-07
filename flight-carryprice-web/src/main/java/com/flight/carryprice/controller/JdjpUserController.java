package com.flight.carryprice.controller;

import com.flight.carryprice.common.exception.SystemException;
import com.flight.carryprice.common.util.Base64Util;
import com.flight.carryprice.common.util.JacksonUtil;
import com.flight.carryprice.config.ParamConfig;
import com.flight.carryprice.entity.*;
import com.flight.carryprice.service.JdjpUserService;
import com.flight.carryprice.vo.ExcheckTreeVo;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @Description 用户相关的操作
 * @see
 * @author wanghy 商旅事业部
 * @version 1.0.0
 * @date 2019年2月25日 下午6:21:21
 * @updateTime
 */
@Controller
@RequestMapping("/userManager")
public class JdjpUserController extends BaseController {

	private static Logger LOGGER = Logger.getLogger(JdjpUserController.class);

	@Resource
	private JdjpUserService jdjpUserService;

	/**
	 * 查询用户列表
	 * 
	 * @return
	 */
	@RequestMapping("/user/userList")
	public String userList(HttpServletRequest req, ModelMap model, AlUsers queryUser) {
		// 查询用户
		PageInfo<AlUsers> pageInfo = jdjpUserService.userPagination(getPageIndex(req), getPageSize(req), queryUser);

		// 查询所有角色
		List<AlPopedomTblrole> roleList = jdjpUserService.queryAllRole(ParamConfig.APP_CODE);

		model.put("pageInfo", pageInfo);
		model.put("queryUser", queryUser);
		model.put("roleList", roleList);

		return "user/userList";
	}

	/**
	 * 进入编辑用户信息页面
	 * 
	 * @param model
	 * @param userId
	 * @return
	 */
	@RequestMapping("/user/toUpdateUser")
	public String toUpdateUser(HttpServletRequest req,ModelMap model, Integer userId) {
		// 查询用户with角色
		AlUsers user = jdjpUserService.findUserByIdWithRole(userId);
		if (user != null && user.getRoleList() != null) {
			String roleStr = "";
			List<AlPopedomTblrole> roleList = user.getRoleList();

			for (int i = 0, count = roleList.size(); i < count; i++) {
				AlPopedomTblrole role = roleList.get(i);
				if (role != null && role.getRolecode() != null) {
					roleStr = roleStr + role.getRolecode();
					if (i != roleList.size() - 1) {
						roleStr = roleStr + ",";
					}
				}

			}
			model.put("roleString", roleStr);
			model.put("user", user);
		}

		return "user/updateUser";
	}

	/**
	 * 添加员工信息页面中的角色
	 * 
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/user/queryRolesForAddUser")
	@ResponseBody
	public Object queryRolesForAddUser(HttpServletRequest req,ModelMap modelMap) {
		// 查询所有角色
		List<AlPopedomTblrole> roleList = jdjpUserService.queryAllRole(ParamConfig.APP_CODE);
		return roleList;
	}

	/**
	 * 修改员工信息
	 * 
	 * @param jsonStr
	 * @param newRoleCodes
	 * @return
	 */
	@RequestMapping("/user/doUpdateUser")
	@ResponseBody
	public Object doUpdateUser(HttpServletRequest req, ModelMap model, String jsonStr, String newRoleCodes) {
		AlUsers user = JacksonUtil.json2Obj(jsonStr, AlUsers.class);

		int flag = 0;

		try {

			// 修改用户和用户角色
			jdjpUserService.updateUserWithRole(user, newRoleCodes);
			// SessionUserListener.removeUserSession(user.getId() + ""); //
			// 移除这个用户的session，让此用户重新登录
			flag = 1;
		} catch (Exception e) {
			LOGGER.error("修改员工信息出错啦...", e);
			throw new SystemException("修改员工信息出错");
		}

		return flag;
	}

	/**
	 * 进入添加员工信息界面
	 * 
	 * @return
	 */
	@RequestMapping("/user/toAddUser")
	public String toAddUser(HttpServletRequest req, ModelMap model) {
		return "user/addUser";
	}

	/**
	 * 添加员工信息
	 * 
	 * @return
	 */
	@RequestMapping("/user/doAddUser")
	@ResponseBody
	public Object doAddUser(HttpServletRequest req, ModelMap model, String jsonStr, String newRoleCodes) {
		int flag = 0;

		// 注册用户
		AlUsers user = JacksonUtil.json2Obj(jsonStr, AlUsers.class);

		String password = user.getPassword();
		if (StringUtils.isNotEmpty(password)) {
			try {
				String encryptPSWD = Base64Util
						.encryptBASE64(MessageDigest.getInstance("MD5").digest(password.getBytes("UTF8"))).trim();
				user.setPassword(encryptPSWD);

				// 添加用户和角色
				flag = jdjpUserService.addUserWithRole(user, newRoleCodes);

			} catch (Exception e) {
				LOGGER.error("添加员工信息出错", e);
				throw new SystemException("添加员工信息出错");
			}
		}

		return flag;
	}

	/**
	 * 个人用户信息管理
	 * 
	 * @return
	 */
	@RequestMapping("/myUser/myUserManager")
	public String myUserManager(HttpServletRequest req, HttpSession session, ModelMap model) {
		AlUsers user = getCurrentUser(session);
		model.put("user", user);

		return "user/myUserManager";
	}

	/**
	 * 修改当前用户的密码
	 * 
	 * @return
	 */
	@RequestMapping("/myUser/updateMyUserPassword")
	@ResponseBody
	public Object updateMyUserPassword(HttpServletRequest req, ModelMap model, Integer userId, String password, String newPassword) {
		int flag = 0;
		try {
			String hashPassword = Base64Util
					.encryptBASE64(MessageDigest.getInstance("MD5").digest(password.getBytes("UTF8"))).trim();
			String hashNewPassword = Base64Util
					.encryptBASE64(MessageDigest.getInstance("MD5").digest(newPassword.getBytes("UTF8"))).trim();

			// 修改密码
			flag = jdjpUserService.updateMyUserPassword(userId, hashPassword, hashNewPassword);
		} catch (Exception e) {
			LOGGER.error("修改个人用户信息出错", e);
			throw new SystemException("修改个人用户信息出错");
		}

		return flag;
	}

	/**
	 * 角色管理列表
	 * 
	 * @return
	 */
	@RequestMapping("/role/roleList")
	public String roleList(HttpServletRequest req, ModelMap model, AlPopedomTblrole queryRole) {
		PageInfo<AlPopedomTblrole> pageInfo = jdjpUserService.rolePagination(getPageIndex(req), getPageSize(req),
				queryRole);

		// 查询所有角色，查询条件
		List<AlPopedomTblrole> roleList = jdjpUserService.queryAllRole(ParamConfig.APP_CODE);

		model.put("pageInfo", pageInfo);
		model.put("queryRole", queryRole);
		model.put("roleList", roleList);

		return "user/roleList";
	}

	/**
	 * 进入新增角色页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/role/toAddRole")
	public String toAddRole(HttpServletRequest req,ModelMap model) {
		return "user/addRole";
	}

	/**
	 * 查询所有的菜单及模块
	 * 
	 * @return
	 */
	@RequestMapping("/role/queryAllMenuWithModel")
	@ResponseBody
	public Object queryAllMenuWithModel(HttpServletRequest req,ModelMap model,Integer roleId) {
		// 查询所有的menu
		List<AlPopedomTblmenu> menuList = jdjpUserService.queryAllMenuWithModel(ParamConfig.APP_CODE);

		List<ExcheckTreeVo> treeList = new ArrayList<ExcheckTreeVo>(); // 树节点

		// 因父子id可能重复，父id格式为p_id,子id格式为 c_id

		for (AlPopedomTblmenu menu : menuList) {
			ExcheckTreeVo pTree = new ExcheckTreeVo("p_" + menu.getMenucode(), menu.getMenuname(), true); // 父节点
			treeList.add(pTree);

			List<AlPopedomTblmodule> moduleList = menu.getModuleList();
			for (AlPopedomTblmodule module : moduleList) {
				boolean isChecked = false;
				if (roleId != null) { // roleId不为空，则表示是修改页面查询，需要选中当前的权限
					// 根据roleId查询角色所拥有的权限
					AlPopedomTblrole role = jdjpUserService.findRoleById(roleId);
					List<AlPopedomTblrolepopedom> currentRolePopedom = jdjpUserService
							.queryModuleByRoleCode(role.getRolecode());

					for (AlPopedomTblrolepopedom rolepopedom : currentRolePopedom) {
						if (module.getModulecode().equals(rolepopedom.getModulecode())) {
							isChecked = true;
						}
					}
				}

				ExcheckTreeVo cTree = new ExcheckTreeVo("c_" + module.getModulecode(), "p_" + module.getMenucode(),
						module.getModulename(), isChecked); // 子节点
				treeList.add(cTree);
			}
		}

		return treeList;
	}

	/**
	 * 执行添加角色
	 * 
	 * @return
	 */
	@RequestMapping("/role/doAddrole")
	@ResponseBody
	public Object doAddrole(HttpServletRequest req,ModelMap model,String rolename, String nodeStr) {
		int flag = 0;
		try {

			if (StringUtils.isBlank(rolename) || StringUtils.isBlank(nodeStr)) { // 信息不全
				flag = -1;
				return flag;
			}

			// 查询选择的权限菜单
			List<AlPopedomTblmodule> moduleList = new ArrayList<AlPopedomTblmodule>();
			String[] nodeArr = nodeStr.split(",");
			for (int i = 0, count = nodeArr.length; i < count; i++) {
				// 根据nodoArr查询菜单表
				AlPopedomTblmodule module = jdjpUserService.findByModuleCode(Integer.parseInt(nodeArr[i]));
				moduleList.add(module);
			}
			if (moduleList.size() <= 0) {
				flag = -1;
				return flag;
			}

			// 添加角色
			jdjpUserService.addRole(rolename, moduleList);
			flag = 1;
		} catch (Exception e) {
			LOGGER.error("添加角色失败", e);
			throw new SystemException("添加角色失败");
		}
		return flag;
	}

	/**
	 * 进入修改角色页面
	 * 
	 * @return
	 */
	@RequestMapping("/role/toUpdateRole")
	public String toUpdateRole(HttpServletRequest req,ModelMap model, Integer roleId) {
		AlPopedomTblrole role = jdjpUserService.findRoleById(roleId);
		model.put("role", role);
		return "user/updateRole";
	}

	/**
	 * 执行修改角色
	 * 
	 * @return
	 */
	@RequestMapping("/role/doUpdaterole")
	@ResponseBody
	public Object doUpdaterole(HttpServletRequest req,ModelMap model, Integer roleId, String rolename, String nodeStr) {
		int flag = 0;
		try {

			if (StringUtils.isBlank(rolename) || StringUtils.isBlank(nodeStr)) { // 信息不全
				flag = -1;
				return flag;
			}

			// 查询选择的权限菜单
			List<AlPopedomTblmodule> moduleList = new ArrayList<AlPopedomTblmodule>();
			String[] nodeArr = nodeStr.split(",");
			for (int i = 0, count = nodeArr.length; i < count; i++) {
				// 根据nodoArr查询菜单表
				AlPopedomTblmodule module = jdjpUserService.findByModuleCode(Integer.parseInt(nodeArr[i]));
				moduleList.add(module);
			}
			if (moduleList.size() <= 0) {
				flag = -1; // 信息不全
				return flag;
			}

			AlPopedomTblrole role = jdjpUserService.findRoleById(roleId);
			if (role == null) {
				flag = -2; // 查询不到要修改的角色
				return flag;
			}
			role.setRolename(rolename);

			// 修改角色
			jdjpUserService.updateRole(role, moduleList);
			flag = 1;
		} catch (Exception e) {
			LOGGER.error("修改角色失败", e);
			throw new SystemException("修改角色失败");
		}
		return flag;
	}

	/**
	 * 禁用角色
	 * 
	 * @param roleId
	 * @return
	 */
	@RequestMapping("/role/disableRole")
	@ResponseBody
	public Object disableRole(HttpServletRequest req,ModelMap model, Integer roleId) {
		int flag = 0;
		try {
			jdjpUserService.disableRole(roleId);
			flag = 1;
		} catch (Exception e) {
			LOGGER.error("", e);
		}
		return flag;
	}

	/**
	 * 启用角色
	 * 
	 * @param roleId
	 * @return
	 */
	@RequestMapping("/role/enableRole")
	@ResponseBody
	public Object enableRole(HttpServletRequest req,ModelMap model,Integer roleId) {
		int flag = 0;
		try {
			jdjpUserService.enableRole(roleId);
			flag = 1;
		} catch (Exception e) {
			LOGGER.error("", e);
		}
		return flag;
	}

	/**
	 * 禁用用户
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping("/user/disableUser")
	@ResponseBody
	public Object disableUser(HttpServletRequest req,ModelMap model,Integer userId) {
		int flag = 0;
		try {
			jdjpUserService.disableUser(userId);
			flag = 1;
		} catch (Exception e) {
			LOGGER.error("", e);
		}
		return flag;
	}

	/**
	 * 启用用户
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping("/user/enableUser")
	@ResponseBody
	public Object enableUser(HttpServletRequest req,ModelMap model,Integer userId) {
		int flag = 0;
		try {
			jdjpUserService.enableUser(userId);
			flag = 1;
		} catch (Exception e) {
			LOGGER.error("", e);
		}
		return flag;
	}

	/**
	 * 重置密码
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping("/user/resetPwd")
	@ResponseBody
	public Object resetPwd(HttpServletRequest req,ModelMap model,@RequestParam(required = true) Integer userId) {
		int flag = 0;
		try {
			final String pwd = "123456";
			String hashPassword = Base64Util
					.encryptBASE64(MessageDigest.getInstance("MD5").digest(pwd.getBytes("UTF8"))).trim();

			AlUsers user = new AlUsers();
			user.setId(userId);
			user.setPassword(hashPassword);
			jdjpUserService.updateByPrimaryKeySelective(user);
			flag = 1;
		} catch (Exception e) {
			LOGGER.error("", e);
			throw new SystemException("系统异常");
		}
		return flag;
	}

	/**
	 * 删除用户
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping("/user/deletedUser")
	@ResponseBody
	public Object deletedUser(HttpServletRequest req,ModelMap model,@RequestParam(required = true) Integer userId) {
		int flag = 0;
		try {
			jdjpUserService.deleteByPrimaryKey(userId);
			flag = 1;
		} catch (Exception e) {
			LOGGER.error("", e);
			throw new SystemException("系统异常");
		}
		return flag;
	}
}
