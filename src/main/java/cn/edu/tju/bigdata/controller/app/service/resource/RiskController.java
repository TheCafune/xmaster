package cn.edu.tju.bigdata.controller.app.service.resource;

import cn.edu.tju.bigdata.annotation.SystemLog;
import cn.edu.tju.bigdata.controller.index.BaseController;
import cn.edu.tju.bigdata.entity.ResFormMap;
import cn.edu.tju.bigdata.entity.RiskEvaluateDetailFormMap;
import cn.edu.tju.bigdata.entity.RiskEvaluateEntity;
import cn.edu.tju.bigdata.entity.RiskEvaluateIndexFormMap;
import cn.edu.tju.bigdata.entity.RiskFormMap;
import cn.edu.tju.bigdata.entity.RoleFormMap;
import cn.edu.tju.bigdata.entity.UserFormMap;
import cn.edu.tju.bigdata.entity.UserGroupsFormMap;
import cn.edu.tju.bigdata.exception.SystemException;
import cn.edu.tju.bigdata.mapper.RiskEvaluateDetailMapper;
import cn.edu.tju.bigdata.mapper.RiskEvaluateIndexMapper;
import cn.edu.tju.bigdata.mapper.RiskMapper;
import cn.edu.tju.bigdata.plugin.PageView;
import cn.edu.tju.bigdata.util.Common;
import cn.edu.tju.bigdata.util.PasswordHelper;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2016/10/8.
 */
@Controller
@RequestMapping("/risk")
public class RiskController extends BaseController {
	@Inject
	private RiskMapper riskMapper;
	@Inject
	private RiskEvaluateIndexMapper riskEvaluateIndexMapper;
	@Inject
	private RiskEvaluateDetailMapper riskEvaluateDetailMapper;

	@RequestMapping("toListQuotaPage")		//跳转到listQuota.jsp
	public String toListQuotaPage(Model model) throws Exception {
		model.addAttribute("res", findByRes());
		return Common.BACKGROUND_PATH + "/system/risk/listQuota";
	}

	@ResponseBody
	@RequestMapping("/findByPage")			//获取 指标管理 表数据
	public PageView findByPage(String pageNow, String pageSize, String column, String sort) throws Exception {
		RiskFormMap riskFormMap = getFormMap(RiskFormMap.class);
		riskFormMap = toFormMap(riskFormMap, pageNow, pageSize, riskFormMap.getStr("orderby"));
		riskFormMap.put("column", column);
		riskFormMap.put("sort", sort);
		pageView.setRecords(riskMapper.findRiskPage(riskFormMap));// 不调用默认分页,调用自已的mapper中findUserPage
		return pageView;
	}

	@RequestMapping("toAddQuotaPage")		//跳转到addQuota.jsp
	public String toAddQuotaPage(Model model) throws Exception {
		return Common.BACKGROUND_PATH + "/system/risk/addQuota";
	}

	@ResponseBody
	@RequestMapping("addQuota")
	@SystemLog(module = "风险管理", methods = "指标管理-新增指标") // 凡需要处理业务逻辑的.都需要记录操作日志
	@Transactional(readOnly = false) // 需要事务操作必须加入此注解
	public String addQuota(String txtGroupsSelect) {
		try {
			RiskFormMap riskFormMap = getFormMap(RiskFormMap.class);
			riskMapper.addEntity(riskFormMap);// 新增后返回新增信息
		} catch (Exception e) {
			throw new SystemException("添加指标异常");
		}
		return "success";
	}

	@RequestMapping("toEditQuotaPage")		//跳转到editQuota.jsp
	public String toEditQuotaPage(Model model) throws Exception {
		String id = getPara("id");
		if (Common.isNotEmpty(id)) {
			model.addAttribute("risk", riskMapper.findbyFrist("id", id, RiskFormMap.class));
		}
		return Common.BACKGROUND_PATH + "/system/risk/editQuota";
	}

	@ResponseBody
	@RequestMapping("editQuota")
	@Transactional(readOnly = false) // 需要事务操作必须加入此注解
	@SystemLog(module = "风险管理", methods = "指标管理-修改指标") // 凡需要处理业务逻辑的.都需要记录操作日志
	public String editQuota() throws Exception {
		RiskFormMap riskFormMap = getFormMap(RiskFormMap.class);
		riskMapper.editEntity(riskFormMap);
		return "success";
	}

	@ResponseBody
	@RequestMapping("doDeleteQuota")
	@Transactional(readOnly = false) // 需要事务操作必须加入此注解
	@SystemLog(module = "风险管理", methods = "指标管理-删除指标") // 凡需要处理业务逻辑的.都需要记录操作日志
	public String doDeleteQuota() throws Exception {
		String[] ids = getParaValues("ids");
		for (String id : ids) {
			riskMapper.deleteByAttribute("id", id, RiskFormMap.class);
		}
		return "success";
	}

	@RequestMapping("toEvaluatePage")		//跳转到evaluate.jsp
	public String toEvaluatePage(Model model) throws Exception {
		List<RiskFormMap> list = riskMapper.findAllRisk();
		List<RiskEvaluateEntity> map = new ArrayList<RiskEvaluateEntity>();
		//RiskEvaluateEntity并不对应一张数据表，用于填写评估表单与回传到回台
		for (RiskFormMap r : list) {
			RiskEvaluateEntity mapUtil = new RiskEvaluateEntity();
			mapUtil.setName((String) r.get("name"));
			mapUtil.setCritical_discharge((String) r.get("critical_discharge"));
			map.add(mapUtil);
		}
		System.out.println(list.size());
		//将评估项 放到model，前台遍历allRisk,动态生成评估表
		model.addAttribute("allRisk", map);
		model.addAttribute("res", findByRes());
		return Common.BACKGROUND_PATH + "/system/risk/evaluate";
	}

	@ResponseBody
	@RequestMapping("DoRiskEvaluate")
	@SystemLog(module = "风险管理", methods = "风险评估-新增评估") // 凡需要处理业务逻辑的.都需要记录操作日志
	@Transactional(readOnly = false) // 需要事务操作必须加入此注解
	public String DoRiskEvaluate(@RequestParam(value = "risks") String risks) throws Exception {
		//System.out.println(risks);
		//riskList 是数据库的评估项，用于与提交的数据进行比较
		List<RiskFormMap> riskList = riskMapper.findAllRisk();
		Map<String, Double> map = new HashMap<String, Double>();
		for (RiskFormMap r : riskList) {
			map.put((String) r.get("name"), Double.parseDouble((String) r.get("critical_discharge")));
		}
		
		//list 将提到上来的json字符串转换为标准的json
		JSONArray list = JSON.parseArray(risks);
		Iterator<Object> it = list.iterator();
		int standardNum = 0;
		int extraNum = 0;
		List<RiskEvaluateDetailFormMap> detailList = new ArrayList<RiskEvaluateDetailFormMap>();
		//遍历每个提交上来的评估指标
		while (it.hasNext()) {
			JSONObject ob = (JSONObject) it.next();
			if (ob.getString("name") != null) {
				// System.out.println(ob.getString("name"));
			}
			if (ob.getString("value") != null && !ob.getString("value").equals("")) {
				// System.out.println(ob.getString("value"));
				//将 有值 的提交指标 与 数据指标 进行比较
				double myValue = Double.parseDouble(ob.getString("value"));
				double value = map.get(ob.getString("name"));
				
				//将 temp 保存 到 List中，等到 简要信息Index保存后获取到 groupId，再将List中的每一个赋值以groupId并保存
				RiskEvaluateDetailFormMap temp = getFormMap(RiskEvaluateDetailFormMap.class);
				temp.set("name", ob.getString("name"));
				temp.set("value", myValue);
				int intResult = 0;
				if (myValue > value) {
					intResult = 1;
				}
				temp.set("result", intResult);
				detailList.add(temp);
				if (myValue > value) {
					extraNum++;
					System.out.println(ob.getString("name") + "超标");
				} else {
					standardNum++;
				}
			}
		}
		
		//riskEvaluateIndexFormMap 保存评估的 简要信息Index 到数据库
		RiskEvaluateIndexFormMap riskEvaluateIndexFormMap = new RiskEvaluateIndexFormMap();
		String user = SecurityUtils.getSubject().getPrincipal().toString();
		System.out.println(user);
		riskEvaluateIndexFormMap.set("accountName", user);
		riskEvaluateIndexFormMap.set("standard_num", standardNum);
		riskEvaluateIndexFormMap.set("unstandard_num", extraNum);
		riskEvaluateIndexMapper.addEntity(riskEvaluateIndexFormMap);
		System.out.println(riskEvaluateIndexFormMap.get("id"));
		Long groupId = Long.parseLong(riskEvaluateIndexFormMap.get("id").toString());
		for (RiskEvaluateDetailFormMap t : detailList) {	//将List中的每一个赋值以groupId并保存
			t.set("groupId", groupId);
			riskEvaluateDetailMapper.addEntity(t);
		}
		return "success";
	}

	@RequestMapping("toResultPage")			//跳转到listResult.jsp
	public String toResultPage(Model model) throws Exception {
		model.addAttribute("res", findByRes());
		return Common.BACKGROUND_PATH + "/system/risk/listResult";
	}

	@ResponseBody
	@RequestMapping("/findResultByPage")
	public PageView findResultByPage(String pageNow, String pageSize, String column, String sort) throws Exception {
		RiskEvaluateIndexFormMap riskEvaluateIndexFormMap = getFormMap(RiskEvaluateIndexFormMap.class);
		riskEvaluateIndexFormMap = toFormMap(riskEvaluateIndexFormMap, pageNow, pageSize,
				riskEvaluateIndexFormMap.getStr("orderby"));
		riskEvaluateIndexFormMap.put("column", column);
		riskEvaluateIndexFormMap.put("sort", sort);
		String user = SecurityUtils.getSubject().getPrincipal().toString();
		//获取 当前用户 的 评估简要记录
		riskEvaluateIndexFormMap.put("user", user);
		pageView.setRecords(riskEvaluateIndexMapper.findAllRiskEvaluateIndex(riskEvaluateIndexFormMap));// 不调用默认分页,调用自已的mapper中findUserPage
		return pageView;
	}

	@RequestMapping("toDetailPage")			//跳转到listDetail.jsp
	public String toDetailPage(Model model) throws Exception {
		String id = getPara("id");
		System.out.println(id);
		RiskEvaluateIndexFormMap riskEvaluateIndexFormMap = getFormMap(RiskEvaluateIndexFormMap.class);
		riskEvaluateIndexFormMap = riskEvaluateIndexMapper.findbyFrist("id", id, RiskEvaluateIndexFormMap.class);
		model.addAttribute("groupId",id);
		model.addAttribute("standard_num",riskEvaluateIndexFormMap.get("standard_num"));
		model.addAttribute("unstandard_num",riskEvaluateIndexFormMap.get("unstandard_num"));
		return Common.BACKGROUND_PATH + "/system/risk/listDetail";
	}
	
	@ResponseBody
	@RequestMapping("getDetail")
	public PageView getDetail(String pageNow, String pageSize, String column, String sort)
			throws Exception {
		//根据groupId 获取 评估的详细信息
		String groupIdStr = getPara("groupId");
		if (Common.isNotEmpty(groupIdStr)) {
			int groupId = Integer.parseInt(groupIdStr);
			System.out.println("groupId = " + groupId);
			RiskEvaluateDetailFormMap riskEvaluateDetailFormMap = getFormMap(RiskEvaluateDetailFormMap.class);
			riskEvaluateDetailFormMap = toFormMap(riskEvaluateDetailFormMap, pageNow, pageSize,
					riskEvaluateDetailFormMap.getStr("orderby"));
			riskEvaluateDetailFormMap.put("column", column);
			riskEvaluateDetailFormMap.put("sort", sort);
			riskEvaluateDetailFormMap.put("groupId", groupId);
			List<RiskEvaluateDetailFormMap> result = riskEvaluateDetailMapper.findbyUserId(riskEvaluateDetailFormMap);
			pageView.setRecords(result);// 不调用默认分页,调用自已的mapper中findUserPage
		}
		return pageView;
	}
	
	@ResponseBody
	@RequestMapping("doDeleteEvaluate")
	@Transactional(readOnly = false) // 需要事务操作必须加入此注解
	@SystemLog(module = "风险管理", methods = "风险评估-删除评估") // 凡需要处理业务逻辑的.都需要记录操作日志
	public String doDeleteEvaluate() throws Exception {
		String[] ids = getParaValues("ids");
		for (String id : ids) {
			riskEvaluateIndexMapper.deleteByAttribute("id", id, RiskEvaluateIndexFormMap.class);
			riskEvaluateDetailMapper.deleteByAttribute("groupId", id, RiskEvaluateDetailFormMap.class);
		}
		return "success";
	}
}
