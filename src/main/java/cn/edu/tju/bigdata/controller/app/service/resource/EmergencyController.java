package cn.edu.tju.bigdata.controller.app.service.resource;

import cn.edu.tju.bigdata.annotation.SystemLog;
import cn.edu.tju.bigdata.controller.index.BaseController;
import cn.edu.tju.bigdata.entity.EmergencyFormMap;
import cn.edu.tju.bigdata.enums.EmDeletedMark;
import cn.edu.tju.bigdata.mapper.EmergencyMapper;
import cn.edu.tju.bigdata.plugin.PageView;
import cn.edu.tju.bigdata.util.Common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lucas on 2016/10/5.
 */
@Controller
@RequestMapping("/emergency")
public class EmergencyController extends  BaseController{

    @Autowired
    EmergencyMapper emergencyMapper;
    @RequestMapping("/list")
    public String manage(Model model) throws Exception {
        model.addAttribute("res", findByRes());
        return Common.BACKGROUND_PATH + "/app/emergency/list";
    }

    @ResponseBody
    @RequestMapping("/findByPage")
    public PageView findByPage(String pageNow, String pageSize) {
        EmergencyFormMap emergencyFormMap = getFormMap(EmergencyFormMap.class);
        emergencyFormMap = toFormMap(emergencyFormMap, pageNow, pageSize, emergencyFormMap.getStr("orderby"));
        emergencyFormMap.set("deleted_mark", EmDeletedMark.VALID.getCode());
        pageView.setRecords(emergencyMapper.findByPage(emergencyFormMap));
        return pageView;
    }

    @RequestMapping("/upload")
    public String upload(Model model) throws Exception {
        return Common.BACKGROUND_PATH + "/app/emergency/edit";
    }

    @ResponseBody
    @RequestMapping("/{id}/delete")
    @Transactional(readOnly = false)
    @SystemLog(module = "突发事件", methods = "事件删除")
    public String delete(@PathVariable Long id) throws Exception {
        EmergencyFormMap EmergencyFormMap = new EmergencyFormMap();
        EmergencyFormMap.set("id", id);
        EmergencyFormMap.set("deleted_mark", EmDeletedMark.INVALID.getCode());
        emergencyMapper.editEntity(EmergencyFormMap);
        return "success";
    }

    @RequestMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) throws Exception {
        model.addAttribute("emergency", emergencyMapper.findbyFrist("id", id.toString(), EmergencyFormMap.class));
        return Common.BACKGROUND_PATH + "/app/emergency/edit";
    }

    @RequestMapping("/add")
    public String add(Model model) throws Exception {
        return Common.BACKGROUND_PATH + "/app/emergency/edit";
    }


    @RequestMapping("/{id}/audit")
    public String audit(@PathVariable Long id, Model model) throws Exception {
        model.addAttribute("emergency", emergencyMapper.findbyFrist("id", id.toString(), EmergencyFormMap.class));
        return Common.BACKGROUND_PATH + "/app/emergency/audit";
    }

    @ResponseBody
    @RequestMapping("/{id}/check")
    public Boolean check(@PathVariable Long id, Model model) throws Exception {
        EmergencyFormMap EmergencyFormMap = emergencyMapper.findbyFrist("id", id.toString(), EmergencyFormMap.class);
        if (EmergencyFormMap != null && EmergencyFormMap.getInt("deleted_mark") == EmDeletedMark.TO_BE_AUDITED.getCode())
            return Boolean.TRUE;
        return Boolean.FALSE;
    }

    @ResponseBody
    @RequestMapping("/save")
    @Transactional(readOnly = false)
    @SystemLog(module = "突发事件", methods = "新增/修改事件")
    public String save() throws Exception {
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        EmergencyFormMap emergencyFormMap = getFormMap(EmergencyFormMap.class);
        if (emergencyFormMap.get("id") == null){ // save
           // emergencyFormMap.set("deleted_mark", EmDeletedMark.VALID.getCode());
           // emergencyFormMap.set("happenTime", simpleDateFormat.format(now));
            emergencyFormMap.set("happenTime", simpleDateFormat.format(now));
            emergencyMapper.addEntity(emergencyFormMap);
        } else { // update
           // emergencyFormMap.set("meta_updated", simpleDateFormat.format(now));
            emergencyMapper.editEntity(emergencyFormMap);
        }
        return "success";
    }
}
