package servlet;

import dao.LogDao;
import data.TotalData;
import util.MyUtil;

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
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        TotalData totalData = (TotalData) request.getSession().getAttribute("totalData");
        String date = request.getParameter("date");
        if (totalData == null) {
            String path = getServletConfig().getServletContext().getRealPath("/") + "log";
            LogDao dao = new LogDao(path);
            totalData = new TotalData();
            totalData.initData(dao);
            request.getSession().setAttribute("totalData",totalData);
        }
        if (date == null) {
            writer.write(totalData.getAllNationalJson(MyUtil.getNewestDate()));
        } else {
            writer.write(totalData.getAllNationalJson(date));
        }
        writer.flush();
        writer.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
