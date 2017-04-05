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
 * Created by haotianliang on 24/03/2017.
 */
@Controller
@RequestMapping("/newIndex")
public class NewIndexController {
    private Logger logger = LoggerFactory.getLogger(ParticipleManagerController.class);
    private String url1 = "http://search-engine.focus-dev.cn/index/impressDataByPid?token_key=konichiwa&pid=";
    private String url2 = "http://search-engine.focus-dev.cn/index/impressDataByCityId?token_key=konichiwa&cityId=";
    private String url3 = "http://search-engine.focus-dev.cn/index/impressAllData?token_key=konichiwa";

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "updateIndex", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView showUpdateOption() {
        try {
            ModelAndView mv = new ModelAndView("new_index");
            return mv;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            ModelAndView mv = new ModelAndView("error");
            return mv;
        }
    }

    @RequestMapping(value = "updateIndexByBaseId", method = RequestMethod.POST)
    @ResponseBody
    public String updateIndexByBaseId(HttpServletRequest request) {
        try {
            String baseId = request.getParameter("baseId");
            if (StringUtils.isBlank(baseId)) {
                return JSONUtils.badResult("failed");
            }
            String urlTemp = url1 + baseId;
            return restTemplate.getForObject(urlTemp.toString(), String.class);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return JSONUtils.badResult("failed");
        }
    }

    @RequestMapping(value = "updateIndexByCityId", method = RequestMethod.POST)
    @ResponseBody
    public String updateIndexByCityId(HttpServletRequest request) {
        try {
            String cityId = request.getParameter("CityId");
            if (StringUtils.isBlank(cityId)) {
                return JSONUtils.badResult("failed");
            }
            String urlTemp = url1 + cityId;
            return restTemplate.getForObject(urlTemp.toString(), String.class);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return JSONUtils.badResult("failed");
        }
    }

    @RequestMapping(value = "updateAll", method = RequestMethod.POST)
    @ResponseBody
    public String updateAll(HttpServletRequest request) {
        return restTemplate.getForObject(url3.toString(), String.class);
    }
}
