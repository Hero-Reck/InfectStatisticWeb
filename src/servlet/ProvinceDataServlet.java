package servlet;

import data.TotalData;
import util.MyUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/ProvinceData")
public class ProvinceDataServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        TotalData totalData = (TotalData) request.getSession().getAttribute("totalData");
        String province = request.getParameter("province");
        String date = request.getParameter("date");
        if (date == null) {
            writer.write(totalData.getAllProvinceJson(province, MyUtil.getNewestDate()));
        } else {
            writer.write(totalData.getAllProvinceJson(province,date));
        }
        writer.flush();
        writer.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
