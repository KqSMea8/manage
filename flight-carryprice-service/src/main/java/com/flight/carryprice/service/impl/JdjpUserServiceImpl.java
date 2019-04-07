package com.flight.carryprice.service.impl;

import com.flight.carryprice.common.service.impl.BaseServiceImpl;
import com.flight.carryprice.config.ParamConfig;
import com.flight.carryprice.entity.*;
import com.flight.carryprice.manager.*;
import com.flight.carryprice.service.JdjpUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.persistence.Transient;
import java.util.*;

@Service
public class JdjpUserServiceImpl extends BaseServiceImpl<AlUsers> implements JdjpUserService {

	private final static Logger LOGGER = Logger.getLogger(JdjpUserServiceImpl.class);
	@Resource
	private JdjpAlTmpManager jdjpAlTmpManager;

	@Resource
	private JdjpAlUserManager jdjpAlUserManager;

	@Resource
	private JdjpAlPopedomTblmenuManager jdjpAlPopedomTblmenuManager;

	@Resource
	private JdjpAlPopedomTblmoduleManager jdjpAlPopedomTblmoduleManager;

	@Resource
	private JdjpAlPopedomTbluserroleManager jdjpAlPopedomTbluserroleManager;

	@Resource
	private JdjpAlPopedomTblroleManager jdjpAlPopedomTblroleManager;

	@Resource
	private JdjpAlPopedomTblrolepopedomManager jdjpAlPopedomTblrolepopedomManager;



	@Override
	public AlUsers findUserByUserNameAndAppCode(String userName, Integer appCode) {
		return jdjpAlUserManager.findUserByUserNameAndAppCode(userName, appCode);
	}

	@Override
	public Map<String, Object> initLeftMenu(Integer appCode, String userCode) {
		// 用户登陆时初始化左侧菜单
		List<AlPopedomTblmenu> menus = jdjpAlPopedomTblmenuManager.selectLeftMenu(appCode, userCode);
		List<AlPopedomTblmodule> modules = jdjpAlPopedomTblmoduleManager.selectLeftModule(appCode, userCode);

		// 菜单集合
		List<AlPopedomTblmenu> menus2 = new ArrayList<>();

		for (AlPopedomTblmenu menu : menus) {
			int i = menu.getMenucode().intValue();
			List<AlPopedomTblmodule> tempModuleLst = new ArrayList<>();
			for (AlPopedomTblmodule module : modules) {
				if (module.getMenucode().intValue() == i) {
					tempModuleLst.add(module);
				}
			}
			menu.setModuleList(tempModuleLst);
			if (tempModuleLst.size() > 0) {
				menus2.add(menu);
			}
		}

		Map<String, String> powerMap = new HashMap<String, String>(); // 权限map，key为模块名+菜单名，如（userManager/user/userList，key为userManager/user）
		for (AlPopedomTblmodule module : modules) {
			String url = module.getNewmoduleurl();
			if (url == null) {
				continue;
			}
			// 截取模块名+菜单名
			if (!url.contains("/")) {
				continue;
			}
			String modelName = url.substring(0, url.lastIndexOf("/"));

			powerMap.put(modelName, url);
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(ParamConfig.SESSION_MEMUS, menus2);
		result.put(ParamConfig.SESSION_URLS, powerMap);

		return result;
	}

	@Override
	public PageInfo<AlUsers> userPagination(Integer pageNo, Integer pageSize, AlUsers queryUser) {
		PageHelper.startPage(pageNo, pageSize);
		List<AlUsers> userList = jdjpAlUserManager.queryUserList(queryUser);
		// 查询用户的角色
		for (AlUsers user : userList) {
			List<AlPopedomTblrole> roleList = new ArrayList<>();
			// 查询用户角色关联表
			AlPopedomTbluserrole queryUserRole = new AlPopedomTbluserrole();
			queryUserRole.setUsercode(user.getUsercode());
			List<AlPopedomTbluserrole> userRoleList = jdjpAlPopedomTbluserroleManager.select(queryUserRole);
			for (AlPopedomTbluserrole userRole : userRoleList) {
				// 根据用户角色关联表查询角色表
				AlPopedomTblrole queryRole = new AlPopedomTblrole();
				queryRole.setRolecode(userRole.getRolecode());
				AlPopedomTblrole role = jdjpAlPopedomTblroleManager.selectOne(queryRole);

				roleList.add(role);
			}
			user.setRoleList(roleList);
		}
		PageInfo<AlUsers> pageInfo = new PageInfo<AlUsers>(userList);
		return pageInfo;
	}

	@Override
	public AlUsers findUserByIdWithRole(Integer userId) {
		// 查询用户
		AlUsers user = jdjpAlUserManager.selectByPrimaryKey(userId);
		if (user == null) {
			return null;
		}

		// 查询用户角色关联表
		AlPopedomTbluserrole queryUserRole = new AlPopedomTbluserrole();
		queryUserRole.setUsercode(user.getUsercode());
		List<AlPopedomTbluserrole> userRoleList = jdjpAlPopedomTbluserroleManager.select(queryUserRole);

		// 查询角色表
		List<AlPopedomTblrole> roleList = new ArrayList<AlPopedomTblrole>();

		for (AlPopedomTbluserrole userRole : userRoleList) {
			// 根据用户角色关联表查询角色表
			AlPopedomTblrole queryRole = new AlPopedomTblrole();
			queryRole.setRolecode(userRole.getRolecode());
			AlPopedomTblrole role = jdjpAlPopedomTblroleManager.selectOne(queryRole);

			roleList.add(role);
		}

		user.setRoleList(roleList);
		return user;
	}

	@Override
	public List<AlPopedomTblrole> queryAllRole(Integer appCode) {
		AlPopedomTblrole queryRole = new AlPopedomTblrole();
		queryRole.setAppcode(appCode);
		List<AlPopedomTblrole> roleList = jdjpAlPopedomTblroleManager.queryRoleList(queryRole);
		return roleList;
	}
	@Transient
	@Override
	public void updateUserWithRole(AlUsers user, String newRoleCodes) {
		// 修改用户
		jdjpAlUserManager.updateByPrimaryKeySelective(user);

		// 删除该用户的所有角色
		jdjpAlPopedomTbluserroleManager.deleteByUserCode(user.getUsercode());

		// 添加用户角色关联表
		if (StringUtils.isBlank(newRoleCodes)) {
			return;
		}
		String[] newRoleCodeArr = newRoleCodes.split(",");
		for (int i = 0, count = newRoleCodeArr.length; i < count; i++) {
			AlPopedomTbluserrole userRole = new AlPopedomTbluserrole(user.getUsercode(),
					Integer.parseInt(newRoleCodeArr[i]));

			jdjpAlPopedomTbluserroleManager.insert(userRole);
		}

	}

	@Transient
	@Override
	public Integer addUserWithRole(AlUsers user, String newRoleCodes) {

		// 判断用户名是否重复
		AlUsers queryUser = new AlUsers();
		queryUser.setUsername(user.getUsername());

		List<AlUsers> userList = jdjpAlUserManager.select(queryUser);
		if (userList.size() > 0) {
			return -1;
		}

		// 添加用户
		user.setAppid(ParamConfig.APP_CODE);
		user.setStatus(1);
		// 生成不重复userCode
		AlTmp tmp = new AlTmp();
		jdjpAlTmpManager.insert(tmp);
		user.setUsercode(tmp.getId());
		user.setCreatetime(new Date());

		jdjpAlUserManager.insert(user);

		// 添加用户角色关联表
		if (StringUtils.isBlank(newRoleCodes)) {
			return -2;
		}
		String[] newRoleCodeArr = newRoleCodes.split(",");
		for (int i = 0, count = newRoleCodeArr.length; i < count; i++) {
			AlPopedomTbluserrole userRole = new AlPopedomTbluserrole(user.getUsercode(),
					Integer.parseInt(newRoleCodeArr[i]));

			jdjpAlPopedomTbluserroleManager.insert(userRole);
		}

		return 1;
	}

	@Transient
	@Override
	public Integer updateMyUserPassword(Integer userId, String hashPassword, String hashNewPassword) {

		if (userId == null || StringUtils.isBlank(hashPassword) || StringUtils.isBlank(hashNewPassword)) { // 信息录入不全
			LOGGER.info("修改密码信息录入不全");
			return -1;
		}

		// 查询出要修改密码的用户
		AlUsers user = jdjpAlUserManager.selectByPrimaryKey(userId);
		if (user == null) {
			LOGGER.info("用户Id:" + userId + "，用户不存在,无法进行密码修改");
			return -3;
		}

		if (!hashPassword.equals(user.getPassword())) { // 原始密码输入错误
			LOGGER.info("原始密码输入错误");
			return -2;
		}

		user.setPassword(hashNewPassword);

		jdjpAlUserManager.updateByPrimaryKey(user); // 更新

		return 1;
	}

	@Override
	public PageInfo<AlPopedomTblrole> rolePagination(Integer pageNo, Integer pageSize, AlPopedomTblrole queryRole) {
		PageHelper.startPage(pageNo, pageSize);
		List<AlPopedomTblrole> roleList = jdjpAlPopedomTblroleManager.queryRoleList(queryRole);
		PageInfo<AlPopedomTblrole> pageInfo = new PageInfo<AlPopedomTblrole>(roleList);
		return pageInfo;
	}

	@Override
	public List<AlPopedomTblmenu> queryAllMenuWithModel(Integer appCode) {

		// 查询所有生效的主菜单
		AlPopedomTblmenu queryMenu = new AlPopedomTblmenu();
		queryMenu.setAppcode(appCode);
		queryMenu.setIsenabled((short) 0);
		List<AlPopedomTblmenu> menus = jdjpAlPopedomTblmenuManager.select(queryMenu);

		for (AlPopedomTblmenu menu : menus) {
			// 根据menuCode查询model
			AlPopedomTblmodule queryModule = new AlPopedomTblmodule();
			queryModule.setMenucode(menu.getMenucode());
			queryModule.setIsenabled((short) 0);

			List<AlPopedomTblmodule> modelList = jdjpAlPopedomTblmoduleManager.select(queryModule);

			menu.setModuleList(modelList);

		}
		return menus;
	}

	@Transient
	@Override
	public void addRole(String rolename, List<AlPopedomTblmodule> moduleList) {
		// 添加角色
		// 生成不重复roleCode
		AlTmp tmp = new AlTmp();
		jdjpAlTmpManager.insert(tmp);
		AlPopedomTblrole role = new AlPopedomTblrole(ParamConfig.APP_CODE, tmp.getId(), rolename);
		jdjpAlPopedomTblroleManager.insert(role);

		// 添加角色-权限关联表
		for (AlPopedomTblmodule module : moduleList) {

			AlPopedomTblrolepopedom rolePopedom = new AlPopedomTblrolepopedom(role.getRolecode(), module.getMenucode(),
					module.getModulecode());

			jdjpAlPopedomTblrolepopedomManager.insert(rolePopedom);
		}
	}

	@Override
	public AlPopedomTblmodule findByModuleCode(Integer moduleCode) {
		AlPopedomTblmodule queryModule = new AlPopedomTblmodule();
		queryModule.setModulecode(moduleCode);
		AlPopedomTblmodule module = jdjpAlPopedomTblmoduleManager.selectOne(queryModule);
		return module;
	}

	@Override
	public AlPopedomTblrole findRoleById(Integer roleId) {
		return jdjpAlPopedomTblroleManager.selectByPrimaryKey(roleId);
	}

	@Override
	public List<AlPopedomTblrolepopedom> queryModuleByRoleCode(Integer rolecode) {
		AlPopedomTblrolepopedom queryRolePopedom = new AlPopedomTblrolepopedom();
		queryRolePopedom.setRolecode(rolecode);

		List<AlPopedomTblrolepopedom> rolePopedomList = jdjpAlPopedomTblrolepopedomManager.select(queryRolePopedom);
		return rolePopedomList;
	}

	@Transient
	@Override
	public void updateRole(AlPopedomTblrole role, List<AlPopedomTblmodule> moduleList) {
		// 修改角色
		jdjpAlPopedomTblroleManager.updateByPrimaryKey(role);

		// 删除之前的角色-权限关联表
		jdjpAlPopedomTblrolepopedomManager.deleteByRoleCode(role.getRolecode());

		// 添加角色-权限关联表
		for (AlPopedomTblmodule module : moduleList) {

			AlPopedomTblrolepopedom rolePopedom = new AlPopedomTblrolepopedom(role.getRolecode(), module.getMenucode(),
					module.getModulecode());

			jdjpAlPopedomTblrolepopedomManager.insert(rolePopedom);
		}
	}

	@Override
	public void disableRole(Integer roleId) {
		AlPopedomTblrole role = jdjpAlPopedomTblroleManager.selectByPrimaryKey(roleId);
		role.setIsenabled((short) 1);
		jdjpAlPopedomTblroleManager.updateByPrimaryKey(role);
	}

	@Override
	public void enableRole(Integer roleId) {
		AlPopedomTblrole role = jdjpAlPopedomTblroleManager.selectByPrimaryKey(roleId);
		role.setIsenabled((short) 0);
		jdjpAlPopedomTblroleManager.updateByPrimaryKey(role);
	}

	@Override
	public void disableUser(Integer userId) {
		AlUsers user = jdjpAlUserManager.selectByPrimaryKey(userId);
		user.setStatus(2);
		jdjpAlUserManager.updateByPrimaryKey(user);
	}

	@Override
	public void enableUser(Integer userId) {
		AlUsers user = jdjpAlUserManager.selectByPrimaryKey(userId);
		user.setStatus(1);
		jdjpAlUserManager.updateByPrimaryKey(user);
	}

}
