package servlet;

import dao.LogDao;
import data.TotalData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet("/NationalData")
public class NationalDataServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        System.out.println(request.getParameter("date"));
        PrintWriter writer = response.getWriter();
        String path = getServletConfig().getServletContext().getRealPath("/") + "log";
        LogDao dao = new LogDao(path);
        TotalData totalData = new TotalData();
        totalData.initData(dao);
        writer.write(totalData.getNationalCumJson("2020-02-02"));
//        writer.write("{\"type\":\"福建\"},{\"type\":\"湖北\"}");
        writer.flush();
        writer.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String path = getServletConfig().getServletContext().getRealPath("/") + "log";
        LogDao dao = new LogDao(path);
        TotalData totalData = new TotalData();
        totalData.initData(dao);
        writer.write(totalData.getNationalAllJson("2020-02-02"));
        writer.flush();
        writer.close();
    }
}
