package cn.focus.search.admin.controller;

import cn.focus.search.admin.utils.JSONUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by haotianliang on 27/03/2017.
 */
@Controller
@RequestMapping("/deleteIndex")
public class DeleteIndexController {
    private Logger logger = LoggerFactory.getLogger(ParticipleManagerController.class);
    private String url1 = "curl -XDELETE http://10.10.24.179:9202/eco_projsearch_alias/eco_project/";
    private String url2 = "http://search-engine.focus-dev.cn/index/deleteExpiredNewhouseData?endTimeStamp=";

    @Autowired
    private RestTemplate restTemplate;
    @RequestMapping(value = "deleteIndex", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView showDeleteOption() {
        try {
            ModelAndView mv = new ModelAndView("delete_index");
            return mv;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            ModelAndView mv = new ModelAndView("error");
            return mv;
        }
    }

    @RequestMapping(value = "deleteIndexByBaseId", method = RequestMethod.POST)
    @ResponseBody
    public String deleteIndexByBaseId(HttpServletRequest request) {
        try {
            String baseId = request.getParameter("BaseId");
            String command = url1 + baseId;
            //命令行执行curl命令
            Runtime.getRuntime().exec(command);
            if (StringUtils.isBlank(baseId)) {
                return JSONUtils.badResult("failed");
            }
            return JSONUtils.ok();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return JSONUtils.badResult("failed");
        }
    }

    @RequestMapping(value = "deleteIndexByEndTimeStamp", method = RequestMethod.POST)
    @ResponseBody
    public String deleteIndexByCityId(HttpServletRequest request) {
        try {
            String ByEndTimeStamp = request.getParameter("ByEndTimeStamp");
            String command = url2 + ByEndTimeStamp;
            if (StringUtils.isBlank(ByEndTimeStamp)) {
                return JSONUtils.badResult("failed");
            }
            return restTemplate.getForObject(command,String.class);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return JSONUtils.badResult("failed");
        }
    }
}

