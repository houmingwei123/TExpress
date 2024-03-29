package cn.itcast.travel.dao.impl;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public int findTotalCount(int cid,String rname) {
//        String sql = "select count(*) from tab_route where cid=?";
        String  sql = "select count(*) from tab_route where 1=1 ";
        List params = new ArrayList();
        StringBuilder sb = new StringBuilder(sql);
        if(cid!=0){
            sb.append(" and cid =? ");
            params.add(cid);
        }
        if(rname!=null && rname.length()>0&&!rname.equals("null")){
            sb.append(" and rname like ?");
            params.add("%"+rname+"%");
        }
        sql = sb.toString();
        return template.queryForObject(sql, Integer.class,params.toArray());
    }

    @Override
    public List<Route> findByPage(int cid, int start, int pagesize,String rname) {

        String sql = "select * from tab_route where 1=1 ";
        List params = new ArrayList();
        StringBuilder sb = new StringBuilder(sql);
        if(cid!=0){
            sb.append("and cid = ? ");
            params.add(cid);
        }
        if(rname!=null&&rname.length()>0&&!rname.equals("null")){
            sb.append("and rname like ? ");
            params.add("%"+rname+"%");
        }
        System.out.println("查询的关键字为："+rname);
        sb.append(" limit ?,?");
        params.add(start);
        params.add(pagesize);
        sql = sb.toString();
        return  template.query(sql,new BeanPropertyRowMapper<Route>(Route.class),params.toArray());
    }


    @Override
    public Route findOne(String rid) {
        String sql = "select * from tab_route where rid =?";
        return  template.queryForObject(sql,new BeanPropertyRowMapper<Route>(Route.class),rid);
    }
}
