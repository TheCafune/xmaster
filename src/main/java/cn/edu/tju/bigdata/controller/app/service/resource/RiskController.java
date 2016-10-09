package cn.edu.tju.bigdata.controller.app.service.resource;

import cn.edu.tju.bigdata.controller.index.BaseController;
import cn.edu.tju.bigdata.entity.RiskFormMap;
import cn.edu.tju.bigdata.entity.UserFormMap;
import cn.edu.tju.bigdata.mapper.RiskMapper;
import cn.edu.tju.bigdata.plugin.PageView;
import cn.edu.tju.bigdata.util.Common;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.inject.Inject;

/**
 * Created by Administrator on 2016/10/8.
 */
@Controller
@RequestMapping("/risk")
public class RiskController extends BaseController {
    @Inject
    private RiskMapper riskMapper;
    @RequestMapping("/list")
    public String listUI(Model model) throws Exception {
        model.addAttribute("res", findByRes());
        return Common.BACKGROUND_PATH + "/system/risk/list";
    }

    @ResponseBody
    @RequestMapping("findByPage")
    public PageView findByPage( String pageNow,
                                String pageSize,String column,String sort) throws Exception {
        RiskFormMap riskFormMap = getFormMap(RiskFormMap.class);
        riskFormMap=toFormMap(riskFormMap, pageNow, pageSize, riskFormMap.getStr("orderby"));
        riskFormMap.put("column", column);
        riskFormMap.put("sort", sort);
        pageView.setRecords(riskMapper.findRiskPage(riskFormMap));//不调用默认分页,调用自已的mapper中findUserPage
        //pageView.setRecords(riskMapper.findByPage(riskFormMap));//调用默认分页
        return pageView;
    }
    
    
    @RequestMapping("/evaluate")
    public String evaluate(Model model) throws Exception {
        model.addAttribute("res", findByRes());
        return Common.BACKGROUND_PATH + "/system/risk/evaluate";
    }
}
