package servlet;

import dao.LogDao;
import data.TotalData;

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
        writer.write(totalData.getAllProvinceJson("湖北","2020-02-02"));
        writer.flush();
        writer.close();
    }
}
