package cn.edu.tju.bigdata.controller.app.service.resource;

import cn.edu.tju.bigdata.controller.index.BaseController;
import cn.edu.tju.bigdata.entity.DangerFormMap;
import cn.edu.tju.bigdata.mapper.DangerMapper;
import cn.edu.tju.bigdata.plugin.PageView;
import cn.edu.tju.bigdata.util.Common;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;

/**
 * @author lucas
 * Created by lucas on 2016/10/5.
 */
@Controller
@RequestMapping("/danger")
public class DangerController extends BaseController {
    @Inject
    private DangerMapper dangerMapper;
    @RequestMapping("/list")
    public String listUI(Model model) throws Exception {
        model.addAttribute("res", findByRes());
        return Common.BACKGROUND_PATH + "/system/danger/list";
    }

    @ResponseBody
    @RequestMapping("/findByPage")
    public PageView findByPage(String pageNow,
                               String pageSize, String column, String sort) throws Exception {
        DangerFormMap  dangerFormMap = getFormMap(DangerFormMap.class);
        String order = "";
        if(Common.isNotEmpty(column)){
            order = " order by "+column+" "+sort;
        }else{
            order = " order by id asc";
        }
        dangerFormMap.put("$orderby", order);
        pageNow = "1";
        dangerFormMap=toFormMap(dangerFormMap, pageNow, pageSize,dangerFormMap.getStr("orderby"));
        // System.out.println("案例1111："+dangerMapper.findByPage(dangerFormMap));
        pageView.setRecords(dangerMapper.findByPage(dangerFormMap));
        System.out.println("获取case案例的数据。。。"+pageView.getRecords());
        return pageView;
    }


}
