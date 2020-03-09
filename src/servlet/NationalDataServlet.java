package servlet;

import dao.LogDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@WebServlet("/NationalData")
public class NationalDataServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        OutputStream out = response.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
        String path = getServletConfig().getServletContext().getRealPath("/");
        LogDao dao = new LogDao(path+"log\\");
        List<File> list = dao.getLogList();
        List<String> records = dao.getRecords(list.get(0));
        for(String record : records) {
            System.out.println(record);
            writer.write(record);
        }
        writer.flush();
        writer.close();
    }
}
