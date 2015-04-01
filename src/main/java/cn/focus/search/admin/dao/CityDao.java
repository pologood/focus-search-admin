package cn.focus.search.admin.dao;


import java.util.List;

import cn.focus.search.admin.model.City;

/**
 * Created by zhaozhaozhang on 2014/12/18.
 */

public interface CityDao {
    /**
     *插入城市数据
     * @param city
     * @return
     * @throws Exception
     */
    public int insertCity(City city)throws Exception;

    /**
     * 更新数据
     * @param city
     * @return
     * @throws Exception
     */
    public int updateCity(City city)throws Exception;

    /**
     * 删除数据
     * @param id
     * @return
     * @throws Exception
     */
    public int deleteCityById(long id) throws Exception;

    /**
     *批量获取数据
     * @param cityId
     * @return
     * @throws Exception
     */
    public List<City> getCityList(int cityId)throws Exception;
}
