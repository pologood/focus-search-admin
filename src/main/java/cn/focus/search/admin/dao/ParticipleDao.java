package cn.focus.search.admin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.focus.search.admin.model.Participle;


public interface ParticipleDao {

	/**
     * 更新分词数据
     * @param participle
     * @return
     * @throws Exception
     */
    public int updateParticiple(Participle participle)throws Exception;


    /**
     * 批量获取未分词数据
     * @param Id
     * @return
     * @throws Exception
     */
    public List<Participle> getParticipleList(@Param("participles")String  participles,@Param("status")int  status)throws Exception;

}
