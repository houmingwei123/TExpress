package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Category;

import java.util.List;

public interface CategoryDao {
    //查询所有的导航数据
    public List<Category> findAll();

}
