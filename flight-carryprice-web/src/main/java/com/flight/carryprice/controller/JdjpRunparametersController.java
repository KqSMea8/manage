package com.flight.carryprice.controller;

import com.flight.carryprice.common.exception.SystemException;
import com.flight.carryprice.common.util.JacksonUtil;
import com.flight.carryprice.entity.JdjpRunparameters;
import com.flight.carryprice.service.JdjpRunparametersService;
import com.github.pagehelper.PageInfo;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
/**
 * 
 * @Description 配置参数操作相关方法
 * @see
 * @author wanghy 商旅事业部
 * @date 2019年3月1日 下午2:39:09
 * @updateTime
 */
@Controller
@RequestMapping("/basedata/runparam")
public class JdjpRunparametersController extends BaseController {

	@Resource
	private JdjpRunparametersService jdjpRunparametersService;
	/**
	 * 分页查询
	 * 
	 * @param req
	 * @param model
	 * @param queryBean
	 * @return
	 */
	@RequestMapping("/runparametersList")
	public String runparametersList(HttpServletRequest req, ModelMap model, JdjpRunparameters queryBean) {
		// 分页查询
		PageInfo<JdjpRunparameters> pageInfo = jdjpRunparametersService.pagination(getPageIndex(req), getPageSize(req),
				queryBean);

		model.put("pageInfo", pageInfo);
		model.put("queryBean", queryBean);

		return "basedata/runparam/runparametersList";
	}

	/**
	 * 进入添加配置参数页面
	 * 
	 * @return
	 */
	@RequestMapping("/toAddRunparameters")
	public String toAddRunparameters() {

		return "basedata/runparam/addRunparameters";
	}

	/**
	 * 执行新增配置
	 * 0 1 注释
	 * 查询重复 插入数据放一个serice
	 * @param session
	 * @param josnStr
	 * @return
	 */
	@RequestMapping("/doAddRunparameters")
	@ResponseBody
	public Object doAddRunparameters(HttpSession session, String josnStr) {
		JdjpRunparameters jdjpRunparameters = JacksonUtil.json2Obj(josnStr, JdjpRunparameters.class);
		if (jdjpRunparameters == null){
			return 0;
		}
		jdjpRunparameters.setOperator(getCurrentUser(session).getUsername());
		try {
			return jdjpRunparametersService.insertSelective(jdjpRunparameters);
		} catch (DuplicateKeyException e1){
			LOGGER.error("重复的名称", e1);
			return "重复的名称";
		}
	}

	/**
	 * 进入修改的页面
	 * 
	 * @param model
	 * @param runId
	 * @return
	 */
	@RequestMapping("/toUpdateRunparameters")
	public String toUpdateRunparameters(ModelMap model, Integer runId) {
		JdjpRunparameters jdjpRunparameters = jdjpRunparametersService.selectByPrimaryKey(runId);
		if (jdjpRunparameters == null){
			throw new SystemException("查询运行参数不存在");
		}
		model.put("run", jdjpRunparameters);
		return "basedata/runparam/updateRunparameters";
	}

	/**
	 * 执行修改配置
	 * 
	 * @param session
	 * @param josnStr
	 * @return
	 */
	@RequestMapping("/doUpdateRunparameters")
	@ResponseBody
	public Object doUpdateRunparameters(HttpSession session, String josnStr) {
		JdjpRunparameters JdjpRunparameters = JacksonUtil.json2Obj(josnStr, JdjpRunparameters.class);
		if (JdjpRunparameters == null){
			return 0;
		}
		JdjpRunparameters.setOperator(getCurrentUser(session).getUsername());
		return jdjpRunparametersService.updateByPrimaryKeySelective(JdjpRunparameters);
	}

	/**
	 * 删除配置参数
	 * 
	 * @param runId
	 * @return
	 */
	@RequestMapping("deleteRunparameters")
	@ResponseBody
	public Object deleteRunparameters(Integer runId) {
		return jdjpRunparametersService.deleteByPrimaryKey(runId);
	}
}
