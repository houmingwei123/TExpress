package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    RouteService service = new RouteServiceImpl();

    /**
     * 分页路线信息查询
     *
     * @Description:
     * @Param: * @param null
     * @Author: mingwei
     * @Date: 2019/5/14
     */
    protected void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rname = request.getParameter("rname");
        //rname = new String(rname.getBytes("iso-8859-1"),"utf-8");tomcat 7之前未解决编码问题的解决办法
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String CidStr = request.getParameter("cid");
        //处理参数
        int cid = 0;
        if (CidStr != null && CidStr.length() > 0 && !CidStr.equals("null")) {
            cid = Integer.parseInt(CidStr);
        }
        int currentPage = 0;
        if (currentPageStr != null && currentPageStr.length() > 0) {
            currentPage = Integer.parseInt(currentPageStr);
        } else {
            currentPage = 1;
        }
        int pageSize = 0;
        if (pageSizeStr != null && pageSizeStr.length() > 0) {
            pageSize = Integer.parseInt(pageSizeStr);
        } else {
            pageSize = 5;
        }
        PageBean<Route> pb = service.pageQuery(cid, currentPage, pageSize, rname);

        writeValue(response, pb);

    }

    protected void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RouteService routeService = new RouteServiceImpl();
        String rid = request.getParameter("rid");
        String sid = request.getParameter("sid");
        Route route=routeService.findOne(rid,sid);
        System.out.println(route.toString());
        writeValue(response,route);


    }


}
