package cn.focus.search.admin.dao.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.focus.search.admin.dao.CityDao;
import cn.focus.search.admin.model.City;

/**
 * Created by zhaozhaozhang on 2014/12/18.
 */

@Repository
public class CityDaoImpl  implements CityDao {
    @Autowired
    private SqlSessionTemplate sqlSession;

    @Override
    public int insertCity(City city) throws Exception {
        return sqlSession.insert("CityDao.insertCity",city);
    }

    @Override
    public int updateCity(City city) throws Exception {
        return sqlSession.update("CityDao.updateCity",city);
    }

    @Override
    public int deleteCityById(long id) throws Exception {
        return sqlSession.delete("CityDao.deleteCityById",id);
    }

    @Override
    public List<City> getCityList(int cityId) throws Exception {
        return sqlSession.selectList("CityDao.getCityList",cityId);
    }
}
