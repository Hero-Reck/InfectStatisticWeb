package util;

import java.io.*;
import java.util.ArrayList;
import java.net.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.util.regex.Matcher;

public class JsoupTool {
    public static String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.14; rv:49.0) Gecko/20100101 Firefox/49.0";
    public static String HOST = "i.snssdk.com";
    public static String REFERER = "https://i.snssdk.com/feoffline/hot_list/template/hot_list/forum_tab.html?activeWidget=1";
    ArrayList<String> url = new ArrayList<>();
    ArrayList<String> provinceName = new ArrayList<String>();

    public JsoupTool(String logPath){
        getUrlAndProvince();
        for(int i = 0;i < url.size();i++){
            getFiles(url.get(i),provinceName.get(i),logPath);
        }
    }

    public void getUrlAndProvince(){
        String link = "https://ncov.dxy.cn/ncovh5/view/pneumonia";
        String resultBody = null,tempResult;
        Document doc = null;
        int position1,position2,i = 0;
        try{
            doc = Jsoup.connect(link).userAgent(USER_AGENT).header("Host",HOST).header("Referer",REFERER).get();
        }catch (Exception e){
            e.printStackTrace();
        }
        resultBody = doc.getElementById("getAreaStat").toString();
        Matcher matcher = Patterns.WEB_URL.matcher(resultBody);
        StringBuffer buffer = new StringBuffer();
        matcher.find();
        while(matcher.find()){
            buffer.append(matcher.group());
            buffer.append("\n");
            i++;
        }
        String[] urls = buffer.toString().split("\n");
        for(i = 0;i < urls.length;i++){
            url.add(urls[i]);
        }

        resultBody = resultBody.replaceAll("\"","").replaceAll(",","");
        for(i = 0;i < urls.length;i++) {
            position1 = resultBody.indexOf("provinceShortName");
            resultBody = resultBody.substring(position1);
            position2 = resultBody.indexOf("currentConfirmedCount");
            position1 = 0;
            tempResult = resultBody.substring(position1, position2).substring(18);
            resultBody = resultBody.substring(position2+21);
            provinceName.add(tempResult);
        }
    }

    public void getFiles(String path,String provinceName,String logPath){
        ArrayList<String> date = new ArrayList<>();
        ArrayList<Integer> confirmedIncr =  new ArrayList<>();
        ArrayList<Integer> curedIncr = new ArrayList<>();
        ArrayList<Integer> deadIncr = new ArrayList<>();
        int HttpResult,position1,position2,position3,position4;;
        String result = null,temp1,temp2,temp3,temp4,line;
        try {
            URL url = new URL(path);
            URLConnection urlconn = url.openConnection();
            urlconn.connect();
            HttpURLConnection httpconn = (HttpURLConnection) urlconn;
            HttpResult = httpconn.getResponseCode();
            if (HttpResult != HttpURLConnection.HTTP_OK) {
                System.out.println("无法连接");
            } else {
                InputStreamReader isReader = new InputStreamReader(urlconn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(isReader);
                StringBuffer buffer = new StringBuffer();
                line = reader.readLine(); // 读取第一行
                while (line != null) { // 如果 line 为空说明读完了
                    buffer.append(line); // 将读到的内容添加到 buffer 中
                    buffer.append(" "); // 添加换行符
                    line = reader.readLine(); // 读取下一行
                }
                result = buffer.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        position1 = result.indexOf("[");
        position2 = result.indexOf("]");
        result = result.substring(position1 + 1,position2);
        String[] strs = result.split("}");
        strs[0] = "  " + strs[0];
        for(String str:strs){
            str = str.substring(2);
            str = str.replaceAll(",","  ").replaceAll("\"","")
                    .replaceAll("\\{","");
            position3 = str.indexOf("dateId");
            position4 = str.indexOf("deadCount");
            temp1 = str.substring(position3,position4).substring(7);
            temp1 = temp1.substring(0,4) + "-" + temp1.substring(4,6) + "-" + temp1.substring(6,8);
            position3 = str.indexOf("confirmedIncr");
            position4 = str.indexOf("curedCount");
            temp2 = str.substring(position3,position4).substring(14).trim();
            position3 = str.indexOf("deadIncr");
            temp3 = str.substring(position3).substring(9).trim();
            position3 = str.indexOf("curedIncr");
            position4 = str.indexOf("currentConfirmedCount");
            temp4 = str.substring(position3,position4).substring(10).trim();
            date.add(temp1);
            confirmedIncr.add(Integer.valueOf(temp2));
            deadIncr.add(Integer.valueOf(temp3));
            curedIncr.add(Integer.valueOf(temp4));
        }
        for(int j = 0;j < date.size();j++){
            String filePath = logPath + date.get(j) + ".log.txt",content;
            File newFile=new File(filePath);
            try{
                if(!newFile.exists()){
                    newFile.createNewFile();
                }
                FileWriter fw = new FileWriter(filePath,true);
                BufferedWriter bw = new BufferedWriter(fw);
                if(confirmedIncr.get(j) != 0) {
                    content = String.format("%s 新增 感染患者 %d人\n", provinceName, confirmedIncr.get(j));
                    bw.write(content);
                }
                if(deadIncr.get(j) != 0) {
                    content = String.format("%s 死亡 %d人\n", provinceName, deadIncr.get(j));
                    bw.write(content);
                }
                if(curedIncr.get(j) != 0) {
                    content = String.format("%s 治愈 %d人\n", provinceName, curedIncr.get(j));
                    bw.write(content);
                }
                bw.close();
                fw.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}

