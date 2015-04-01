package cn.focus.search.admin.service;


import java.util.List;

import cn.focus.search.admin.model.City;

/**
 * Created by zhaozhaozhang on 2014/12/22.
 */

public interface CityService {


    /**
     * 插入城市数据
     *
     * @param city
     * @return
     */
    public int insertCity(City city);

    /**
     * 更新数据
     *
     * @param city
     * @return
     */
    public int updateCity(City city);

    /**
     * 删除数据
     *
     * @param id
     * @return
     * @throws Exception
     */
    public int deleteCityById(long id);

    /**
     * 批量获取数据
     *
     * @param cityId
     * @return
     * @throws Exception
     */
    public List<City> getCityList(int cityId);

    /**
     * 测试spring 数据事库物配置是否正�?
     */
    public void updateOrInster(City city) throws Exception;
}
