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
                        break;
                    case "治愈":
                        num = MyUtil.parseData(data[2]);
                        datePartData.add(data[0],3, num);
                        cumList = cumData.get(data[0]);
                        cumList.set(3,cumList.get(3) + num);
                        break;
                }
            }
            datePartData.setInitData(cumData);
            datePartData.compute();
            statistics.put(date,datePartData);
        }
    }

    public Map<String,DatePartData> getStatistics() {
        return statistics;
    }

    private String getTotalDataJson(String date) {
        DatePartData data = statistics.get(date);
        List<Integer> increment = data.getNationalDataInc();
        List<Integer> exist = data.getNationalDataExi();
        StringBuilder jsonStr = new StringBuilder();
        jsonStr.append("[");
        jsonStr.append("{");
        jsonStr.append("\"type\":").append("\"现存确诊\"").append(",");
        jsonStr.append("\"num\":").append(exist.get(0)).append(",");
        jsonStr.append("\"compare\":").append(increment.get(0) - increment.get(2) - increment.get(3));
        jsonStr.append("}").append(",");
        jsonStr.append("{");
        jsonStr.append("\"type\":").append("\"累计确诊\"").append(",");
        jsonStr.append("\"num\":").append(data.getNationalCumInfect()).append(",");
        jsonStr.append("\"compare\":").append(increment.get(0));
        jsonStr.append("}").append(",");
        jsonStr.append("{");
        jsonStr.append("\"type\":").append("\"现存疑似\"").append(",");
        jsonStr.append("\"num\":").append(exist.get(1)).append(",");
        jsonStr.append("\"compare\":").append(increment.get(1));
        jsonStr.append("}").append(",");
        jsonStr.append("{");
        jsonStr.append("\"type\":").append("\"现存死亡\"").append(",");
        jsonStr.append("\"num\":").append(exist.get(2)).append(",");
        jsonStr.append("\"compare\":").append(increment.get(2));
        jsonStr.append("}").append(",");
        jsonStr.append("{");
        jsonStr.append("\"type\":").append("\"现存治愈\"").append(",");
        jsonStr.append("\"num\":").append(exist.get(3)).append(",");
        jsonStr.append("\"compare\":").append(increment.get(3));
        jsonStr.append("}");
        jsonStr.append("]");
        return jsonStr.toString();
    }

    private String getNationalCumJson(String date) {
        StringBuilder jsonStr = new StringBuilder();
        Map<String,Integer> cumData= statistics.get(date).getProvincesCumInfect();
        jsonStr.append("[");
        for (String province : cumData.keySet()) {
            jsonStr.append("{");
            jsonStr.append("\"name\":").append("\"").append(province).append("\"").append(",");
            jsonStr.append("\"value\":").append(cumData.get(province));
            jsonStr.append("},");
        }
        jsonStr.deleteCharAt(jsonStr.length() - 1);
        jsonStr.append("]");
        return jsonStr.toString();
    }

    private String getNationalExiJson(String date) {
        StringBuilder jsonStr = new StringBuilder();
        Map<String,List<Integer>> existData= statistics.get(date).getProvincesDataExi();
        jsonStr.append("[");
        for (String province : existData.keySet()) {
            jsonStr.append("{");
            jsonStr.append("\"name\":").append("\"").append(province).append("\"").append(",");
            jsonStr.append("\"value\":").append(existData.get(province).get(0));
            jsonStr.append("},");
        }
        jsonStr.deleteCharAt(jsonStr.length() - 1);
        jsonStr.append("]");
        return jsonStr.toString();
    }

    private String getNInfSusIncJson() {
        StringBuilder jsonStr = new StringBuilder();
        jsonStr.append("[");
        for (String date : statistics.keySet()) {
            List<Integer> incData = statistics.get(date).getNationalDataInc();
            jsonStr.append("{");
            jsonStr.append("\"xAxis\":").append("\"");
            jsonStr.append(date.substring(date.indexOf("-") + 1).replace("-","/"));
            jsonStr.append("\"").append(",");
            jsonStr.append("\"新增确诊\":").append(incData.get(0)).append(",");
            jsonStr.append("\"新增疑似\":").append(incData.get(1));
            jsonStr.append("}").append(",");
        }
        jsonStr.deleteCharAt(jsonStr.length() - 1);
        jsonStr.append("]");
        return jsonStr.toString();
    }

    private String getNInfSusExiJson() {
        StringBuilder jsonStr = new StringBuilder();
        jsonStr.append("[");
        for (String date : statistics.keySet()) {
            List<Integer> exiData = statistics.get(date).getNationalDataExi();
            jsonStr.append("{");
            jsonStr.append("\"xAxis\":").append("\"");
            jsonStr.append(date.substring(date.indexOf("-") + 1).replace("-","/"));
            jsonStr.append("\"").append(",");
            jsonStr.append("\"现存确诊\":").append(exiData.get(0)).append(",");
            jsonStr.append("\"现存疑似\":").append(exiData.get(1));
            jsonStr.append("}").append(",");
        }
        jsonStr.deleteCharAt(jsonStr.length() - 1);
        jsonStr.append("]");
        return jsonStr.toString();
    }

    private String getNDeadCureExiJson() {
        StringBuilder jsonStr = new StringBuilder();
        jsonStr.append("[");
        for (String date : statistics.keySet()) {
            List<Integer> deadCureData = statistics.get(date).getNationalDataExi();
            jsonStr.append("{");
            jsonStr.append("\"xAxis\":").append("\"");
            jsonStr.append(date.substring(date.indexOf("-") + 1).replace("-","/"));
            jsonStr.append("\"").append(",");
            jsonStr.append("\"死亡\":").append(deadCureData.get(2)).append(",");
            jsonStr.append("\"治愈\":").append(deadCureData.get(3));
            jsonStr.append("}").append(",");
        }
        jsonStr.deleteCharAt(jsonStr.length() - 1);
        jsonStr.append("]");
        return jsonStr.toString();
    }

    private String getTableDataJson(String date) {
        StringBuilder jsonStr = new StringBuilder();
        jsonStr.append("[");
        DatePartData data = statistics.get(date);
        Map<String,List<Integer>> exiData = data.getProvincesDataExi();
        Map<String,Integer> cumData = data.getProvincesCumInfect();
        for (String province : exiData.keySet()) {
            jsonStr.append("{");
            jsonStr.append("\"地区\":").append("\"").append(province).append("\"").append(",");
            jsonStr.append("\"现存确诊\":").append(exiData.get(province).get(0)).append(",");
            jsonStr.append("\"累计确诊\":").append(cumData.get(province)).append(",");
            jsonStr.append("\"死亡\":").append(exiData.get(province).get(2)).append(",");
            jsonStr.append("\"治愈\":").append(exiData.get(province).get(3));
            jsonStr.append("}").append(",");
        }
        jsonStr.deleteCharAt(jsonStr.length() - 1);
        jsonStr.append("]");
        return jsonStr.toString();
    }

    public String getAllNationalJson(String date) {
        StringBuilder jsonStr = new StringBuilder();
        jsonStr.append("[");
        jsonStr.append(getTotalDataJson(date)).append(",");
        jsonStr.append(getNationalExiJson(date)).append(",");
        jsonStr.append(getNationalCumJson(date)).append(",");
        jsonStr.append(getNInfSusIncJson()).append(",");
        jsonStr.append(getNInfSusExiJson()).append(",");
        jsonStr.append(getNDeadCureExiJson()).append(",");
        jsonStr.append(getTableDataJson(date));
        jsonStr.append("]");
        return jsonStr.toString();
    }

    private String getProvinceJson(String province,String date) {
        StringBuilder jsonStr = new StringBuilder();
        DatePartData data = statistics.get(date);
        List<Integer> exiData = data.getProvincesDataExi().get(province);
        List<Integer> incData = data.getProvincesDataInc().get(province);
        int cumData = data.getProvincesCumInfect().get(province);
        jsonStr.append("[").append("{");
        jsonStr.append("\"type\":").append("\"现存确诊\"").append(",");
        jsonStr.append("\"num\":").append(exiData.get(0)).append(",");
        jsonStr.append("\"compare\":").append(incData.get(0) - incData.get(2) - incData.get(3));
        jsonStr.append("}").append(",").append("{");
        jsonStr.append("\"type\":").append("\"累计确诊\"").append(",");
        jsonStr.append("\"num\":").append(cumData).append(",");
        jsonStr.append("\"compare\":").append(incData.get(0));
        jsonStr.append("}").append(",").append("{");
        jsonStr.append("\"type\":").append("\"死亡\"").append(",");
        jsonStr.append("\"num\":").append(exiData.get(2)).append(",");
        jsonStr.append("\"compare\":").append(incData.get(2));
        jsonStr.append("}").append(",").append("{");
        jsonStr.append("\"type\":").append("\"治愈\"").append(",");
        jsonStr.append("\"num\":").append(exiData.get(3)).append(",");
        jsonStr.append("\"compare\":").append(incData.get(3));
        jsonStr.append("}").append("]");
        return jsonStr.toString();
    }

    private String getPInfExiJson(String province) {
        StringBuilder jsonStr = new StringBuilder();
        jsonStr.append("[");
        for (String date : statistics.keySet()) {
            jsonStr.append("{");
            jsonStr.append("\"xAxis\":").append("\"");
            jsonStr.append(date.substring(date.indexOf("-") + 1).replace("-","/"));
            jsonStr.append("\"").append(",");
            jsonStr.append("\"现存确诊\":").append(statistics.get(date).getProvincesDataExi().get(province).get(0));
            jsonStr.append("}").append(",");
        }
        jsonStr.deleteCharAt(jsonStr.length() - 1);
        jsonStr.append("]");
        return jsonStr.toString();
    }

    private String getPInfDeadCureIncJson(String province) {
        StringBuilder jsonStr = new StringBuilder();
        jsonStr.append("[");
        for (String date : statistics.keySet()) {
            List<Integer> incData = statistics.get(date).getProvincesDataInc().get(province);
            jsonStr.append("{");
            jsonStr.append("\"xAxis\":").append("\"");
            jsonStr.append(date.substring(date.indexOf("-") + 1).replace("-","/"));
            jsonStr.append("\"").append(",");
            jsonStr.append("\"新增确诊\":").append(incData.get(0)).append(",");
            jsonStr.append("\"新增死亡\":").append(incData.get(2)).append(",");
            jsonStr.append("\"新增治愈\":").append(incData.get(3));
            jsonStr.append("}").append(",");
        }
        jsonStr.deleteCharAt(jsonStr.length() - 1);
        jsonStr.append("]");
        return jsonStr.toString();
    }

    private String getPInfDeadCureCumJson(String province) {
        StringBuilder jsonStr = new StringBuilder();
        jsonStr.append("[");
        for (String date : statistics.keySet()) {
            List<Integer> incData = statistics.get(date).getProvincesDataExi().get(province);
            int cumData = statistics.get(date).getProvincesCumInfect().get(province);
            jsonStr.append("{");
            jsonStr.append("\"xAxis\":").append("\"");
            jsonStr.append(date.substring(date.indexOf("-") + 1).replace("-","/"));
            jsonStr.append("\"").append(",");
            jsonStr.append("\"累计确诊\":").append(cumData).append(",");
            jsonStr.append("\"累计死亡\":").append(incData.get(2)).append(",");
            jsonStr.append("\"累计治愈\":").append(incData.get(3));
            jsonStr.append("}").append(",");
        }
        jsonStr.deleteCharAt(jsonStr.length() - 1);
        jsonStr.append("]");
        return jsonStr.toString();
    }

    public String getAllProvinceJson(String province,String date) {
        StringBuilder jsonStr = new StringBuilder();
        jsonStr.append("[");
        jsonStr.append(getProvinceJson(province,date)).append(",");
        jsonStr.append(getPInfExiJson(province)).append(",");
        jsonStr.append(getPInfDeadCureIncJson(province)).append(",");
        jsonStr.append(getPInfDeadCureCumJson(province));
        jsonStr.append("]");
        return jsonStr.toString();
    }
}
