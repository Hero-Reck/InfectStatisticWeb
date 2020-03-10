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
        
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        OutputStream out = response.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
        String path = getServletConfig().getServletContext().getRealPath("/");
        LogDao dao = new LogDao(path+"log\\");
        TotalData totalData = new TotalData();
        totalData.initData(dao);
        writer.write(totalData.getTotalDataJson("2020-02-02"));
        writer.write("\n*************************\n");
        writer.write(totalData.getNationalCumJson("2020-02-02"));
        writer.write("\n*************************\n");
        writer.write(totalData.getNationalExiJson("2020-02-02"));
        writer.write("\n*************************\n");
        writer.write(totalData.getNInfSusIncJson());
        writer.write("\n*************************\n");
        writer.write(totalData.getNInfSusExiJson());
        writer.write("\n*************************\n");
        writer.write(totalData.getNDeadCureExiJson());
        writer.flush();
        writer.close();
    }
}
