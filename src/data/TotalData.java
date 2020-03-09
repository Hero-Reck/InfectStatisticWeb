package data;

import dao.LogDao;
import util.MyUtil;

import java.io.File;
import java.util.*;

public class TotalData {
    private Map<String, DatePartData> statistics;
    private Map<String, List<Integer>> cumData;

    public TotalData() {
        statistics = new LinkedHashMap<>();
        cumData = new HashMap<>();
        List<Integer> originData = new ArrayList<>();
        originData.add(0); //感染人数
        originData.add(0); //疑似人数
        originData.add(0); //死亡人数
        originData.add(0); //治愈人数
        cumData.put("全国",new ArrayList<>(originData));
        cumData.put("安徽",new ArrayList<>(originData));
        cumData.put("澳门",new ArrayList<>(originData));
        cumData.put("北京",new ArrayList<>(originData));
        cumData.put("重庆",new ArrayList<>(originData));
        cumData.put("福建",new ArrayList<>(originData));
        cumData.put("甘肃",new ArrayList<>(originData));
        cumData.put("广东",new ArrayList<>(originData));
        cumData.put("广西",new ArrayList<>(originData));
        cumData.put("贵州",new ArrayList<>(originData));
        cumData.put("河北",new ArrayList<>(originData));
        cumData.put("海南",new ArrayList<>(originData));
        cumData.put("河南",new ArrayList<>(originData));
        cumData.put("黑龙江",new ArrayList<>(originData));
        cumData.put("湖北",new ArrayList<>(originData));
        cumData.put("湖南",new ArrayList<>(originData));
        cumData.put("吉林",new ArrayList<>(originData));
        cumData.put("江苏",new ArrayList<>(originData));
        cumData.put("江西",new ArrayList<>(originData));
        cumData.put("辽宁",new ArrayList<>(originData));
        cumData.put("内蒙古",new ArrayList<>(originData));
        cumData.put("宁夏",new ArrayList<>(originData));
        cumData.put("青海",new ArrayList<>(originData));
        cumData.put("山东",new ArrayList<>(originData));
        cumData.put("山西",new ArrayList<>(originData));
        cumData.put("陕西",new ArrayList<>(originData));
        cumData.put("上海",new ArrayList<>(originData));
        cumData.put("四川",new ArrayList<>(originData));
        cumData.put("台湾",new ArrayList<>(originData));
        cumData.put("天津",new ArrayList<>(originData));
        cumData.put("香港",new ArrayList<>(originData));
        cumData.put("西藏",new ArrayList<>(originData));
        cumData.put("新疆",new ArrayList<>(originData));
        cumData.put("云南",new ArrayList<>(originData));
        cumData.put("浙江",new ArrayList<>(originData));
    }

    public void initData(LogDao dao) {
        List<File> logList = dao.getLogList();
        for(File log : logList) {
            String date = log.getName().substring(0,log.getName().indexOf("."));
            DatePartData datePartData = new DatePartData();
            List<String> records = dao.getRecords(log);
            List<Integer> cumList;
            int num;
            for(String record : records) {
                String[] data = record.split(" ");
                if(data.length < 2) {
                    continue;
                }
                switch (data[1]) {
                    case "新增":
                        if ("感染患者".equals(data[2])) {
                            num = MyUtil.parseData(data[3]);
                            datePartData.add(data[0],0, num);
                            cumList = cumData.get(data[0]);
                            cumList.set(0,cumList.get(0) + num);
                        } else if ("疑似患者".equals(data[2])) {
                            num = MyUtil.parseData(data[3]);
                            datePartData.add(data[0],1, MyUtil.parseData(data[3]));
                            cumList = cumData.get(data[0]);
                            cumList.set(1,cumList.get(1) + num);
                        }
                        break;
                    case "死亡":
                        num = MyUtil.parseData(data[2]);
                        datePartData.add(data[0],2, num);
                        cumList = cumData.get(data[0]);
                        cumList.set(2,cumList.get(2) + num);
                        cumList.set(0,cumList.get(0) - num);
                        break;
                    case "治愈":
                        num = MyUtil.parseData(data[2]);
                        datePartData.add(data[0],3, num);
                        cumList = cumData.get(data[0]);
                        cumList.set(3,cumList.get(3) + num);
                        cumList.set(0,cumList.get(0) - num);
                        break;
                }
            }
            datePartData.setCumData(cumData);
            datePartData.count();
            statistics.put(date,datePartData);
        }
    }

    public Map<String,DatePartData> getStatistics() {
        return statistics;
    }
}
