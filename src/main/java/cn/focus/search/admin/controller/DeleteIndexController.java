package cn.focus.search.admin.controller;

import cn.focus.search.admin.utils.JSONUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by haotianliang on 27/03/2017.
 */
@Controller
@RequestMapping("/deleteIndex")
public class DeleteIndexController {
    private Logger logger = LoggerFactory.getLogger(ParticipleManagerController.class);

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
            /*
                接口部分，输入BaseId，进入判断
             */
            if (StringUtils.isBlank(baseId)) {
                return JSONUtils.badResult("failed");
            }
            return JSONUtils.ok();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return JSONUtils.badResult("failed");
        }
    }

    @RequestMapping(value = "deleteIndexByCityId", method = RequestMethod.POST)
    @ResponseBody
    public String deleteIndexByCityId(HttpServletRequest request) {
        try {
            String cityId = request.getParameter("CityId");
            /*
                接口部分，输入CityId，进入判断
             */
            if (StringUtils.isBlank(cityId)) {
                return JSONUtils.badResult("failed");
            }
            return JSONUtils.ok();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return JSONUtils.badResult("failed");
        }
    }
}

