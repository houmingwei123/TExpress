package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname) {
        RouteDao routeDao = new RouteDaoImpl();
        RouteImgDao routeImgDao = new RouteImgDaoImpl();
        //封装pageBean
        PageBean pb = new PageBean();
        // 总记录数
        int totalCount;
        totalCount = routeDao.findTotalCount(cid, rname);
        pb.setTotalCount(totalCount);
        //总页数
        int totalPage;
        totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1;
        pb.setTotalPage(totalPage);
        //当前页数据
        List<Route> list;
        System.out.println("数据库查询时当前页面：" + currentPage);
        list = routeDao.findByPage(cid, (currentPage - 1) * pageSize, pageSize, rname);
        pb.setList(list);
        //当前页
        pb.setCurrentPage(currentPage);
        //每页显示的条数
        pb.setPageSize(pageSize);
        return pb;

    }

    @Override
    public Route findOne(String rid, String sid) {
        RouteDao routeDao = new RouteDaoImpl();
        //查询单个路线信息
        Route route = routeDao.findOne(sid);

        //设置图片列表
        RouteImgDao routeImgDao = new RouteImgDaoImpl();
        List<RouteImg> routeImgs = routeImgDao.findImg(rid);
        route.setRouteImgList(routeImgs);
        //设置卖家
        SellerDao sellerDao = new SellerDaoImpl();
        Seller seller = sellerDao.findSeller(sid);
        route.setSeller(seller);
        return route;

    }
}
