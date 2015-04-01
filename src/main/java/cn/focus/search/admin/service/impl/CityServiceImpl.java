package cn.focus.search.admin.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.focus.search.admin.dao.CityDao;
import cn.focus.search.admin.model.City;
import cn.focus.search.admin.service.CityService;

/**
 * Created by zhaozhaozhang on 2014/12/22.
 */
@Service
public class CityServiceImpl implements CityService {
    private Logger logger = LoggerFactory.getLogger(CityServiceImpl.class);
    @Autowired
    private CityDao cityDao;

    @Override
    public int insertCity(City city) {
        int s = 0;
        try {
            s = cityDao.insertCity(city);
        } catch (Exception e) {
            logger.error("插入城市数据异常!", e);
        }
        return s;
    }

    @Override
    public int updateCity(City city) {
        int s = 0;
        try {
            s = cityDao.updateCity(city);
        } catch (Exception e) {
            logger.error("更新城市数据异常!", e);
        }
        return s;
    }

    @Override
    public int deleteCityById(long id){
        int s = 0;
        try {
            s = cityDao.deleteCityById(id);
        } catch (Exception e) {
            logger.error("删除城市数据异常!", e);
        }
        return s;
    }

    @Override
    public List<City> getCityList(int cityId) {
        List<City> list=new LinkedList<City>();
        try {
            list = cityDao.getCityList(cityId);
        } catch (Exception e) {
            logger.error("获取城市数据异常!", e);
        }
        return list;
    }

    /**
     * 测试spring 声明式事务测试过通，如果想使用事务，必需在方法里把异常thorws出去
     * 这样事务才起作用（切记，好久没有配置都是忘记这点了，弄了好久才想起来�?
     * @param city
     * @throws Exception
     */
    @Override
    public void updateOrInster(City city)throws Exception {
        this.insertCity(city);
        Integer.parseInt("ekeke");
        this.updateCity(city);

    }
}
