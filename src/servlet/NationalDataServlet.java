package servlet;

import dao.LogDao;
import data.DatePartData;
import data.TotalData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;

@WebServlet("/NationalData")
public class NationalDataServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        OutputStream out = response.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
        String path = getServletConfig().getServletContext().getRealPath("/");
        LogDao dao = new LogDao(path+"log\\");
        TotalData totalData = new TotalData();
        totalData.initData(dao);
        Map<String, DatePartData> statistic = totalData.getStatistics();
        for(String date : statistic.keySet()) {
            DatePartData data = statistic.get(date);
            writer.write(date + ": ");
            writer.write("新增感染" + data.getNationalDataInc().get(0) + "人    ");
            writer.write("新增疑似" + data.getNationalDataInc().get(1) + "人    ");
            writer.write("新增死亡" + data.getNationalDataInc().get(2) + "人    ");
            writer.write("新增治愈" + data.getNationalDataInc().get(3) + "人    ");
            writer.write("累计感染" + data.getNationalDataCum().get(0) + "人    ");
            writer.write("累计疑似" + data.getNationalDataCum().get(1) + "人    ");
            writer.write("累计死亡" + data.getNationalDataCum().get(2) + "人    ");
            writer.write("累计治愈" + data.getNationalDataCum().get(3) + "人    ");
            writer.write("\n");
        }
        writer.flush();
        writer.close();
    }
}
