package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteDao {
    //查询总记录数
    public int findTotalCount(int cid,String rname);
    /**
     * 根据页码查询
     */
     List<Route> findByPage(int cid, int start, int pagesize,String rname);

     Route findOne(String rid);
}
