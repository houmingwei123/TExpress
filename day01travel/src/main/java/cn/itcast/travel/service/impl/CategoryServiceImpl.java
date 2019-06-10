package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao = new CategoryDaoImpl();
    @Override
    public List<Category> findAll() {
        Jedis jedis = JedisUtil.getJedis();
        //Set<String> categorys = jedis.zrange("category",0,-1);
        //获取键和值
        Set<Tuple> categorys = jedis.zrangeWithScores("category",0,-1);
        List<Category> cs = null;
        if(categorys==null||categorys.size()==0){
            System.out.println("从数据库查询");
            cs = categoryDao.findAll();
            for (int i = 0; i < cs.size(); i++) {
//                String cname =cs.get(i).getCname();
//                category.add(cname);
                jedis.zadd("category",cs.get(i).getCid(),cs.get(i).getCname());
            }
        }
        else {
            System.out.println("从redis中查询");
            //将set集合中的数据传到List集合中
            cs = new ArrayList<Category>();
            for (Tuple tuple : categorys) {
                Category category = new Category();
                category.setCname(tuple.getElement());
                category.setCid((int)tuple.getScore());
                cs.add(category);
            }
        }
        return cs;
    }
}
