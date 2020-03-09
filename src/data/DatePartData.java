package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatePartData {
    private Map<String, List<Integer>> provincesDataInc;
    private Map<String, List<Integer>> provincesDataCum;
    private List<Integer> nationalDataInc;
    private List<Integer> nationalDataCum;

    public DatePartData() {
        provincesDataInc = new HashMap<>();
        nationalDataInc = new ArrayList<>();
        nationalDataCum = new ArrayList<>();
        for(int i=0;i< 4;i++) {
            nationalDataInc.add(0);
            nationalDataCum.add(0);
        }
        List<Integer> originData = new ArrayList<>();
        originData.add(0); //感染人数
        originData.add(0); //疑似人数
        originData.add(0); //死亡人数
        originData.add(0); //治愈人数
        provincesDataInc.put("全国",new ArrayList<>(originData));
        provincesDataInc.put("安徽",new ArrayList<>(originData));
        provincesDataInc.put("澳门",new ArrayList<>(originData));
        provincesDataInc.put("北京",new ArrayList<>(originData));
        provincesDataInc.put("重庆",new ArrayList<>(originData));
        provincesDataInc.put("福建",new ArrayList<>(originData));
        provincesDataInc.put("甘肃",new ArrayList<>(originData));
        provincesDataInc.put("广东",new ArrayList<>(originData));
        provincesDataInc.put("广西",new ArrayList<>(originData));
        provincesDataInc.put("贵州",new ArrayList<>(originData));
        provincesDataInc.put("河北",new ArrayList<>(originData));
        provincesDataInc.put("海南",new ArrayList<>(originData));
        provincesDataInc.put("河南",new ArrayList<>(originData));
        provincesDataInc.put("黑龙江",new ArrayList<>(originData));
        provincesDataInc.put("湖北",new ArrayList<>(originData));
        provincesDataInc.put("湖南",new ArrayList<>(originData));
        provincesDataInc.put("吉林",new ArrayList<>(originData));
        provincesDataInc.put("江苏",new ArrayList<>(originData));
        provincesDataInc.put("江西",new ArrayList<>(originData));
        provincesDataInc.put("辽宁",new ArrayList<>(originData));
        provincesDataInc.put("内蒙古",new ArrayList<>(originData));
        provincesDataInc.put("宁夏",new ArrayList<>(originData));
        provincesDataInc.put("青海",new ArrayList<>(originData));
        provincesDataInc.put("山东",new ArrayList<>(originData));
        provincesDataInc.put("山西",new ArrayList<>(originData));
        provincesDataInc.put("陕西",new ArrayList<>(originData));
        provincesDataInc.put("上海",new ArrayList<>(originData));
        provincesDataInc.put("四川",new ArrayList<>(originData));
        provincesDataInc.put("台湾",new ArrayList<>(originData));
        provincesDataInc.put("天津",new ArrayList<>(originData));
        provincesDataInc.put("香港",new ArrayList<>(originData));
        provincesDataInc.put("西藏",new ArrayList<>(originData));
        provincesDataInc.put("新疆",new ArrayList<>(originData));
        provincesDataInc.put("云南",new ArrayList<>(originData));
        provincesDataInc.put("浙江",new ArrayList<>(originData));
    }

    public void setCumData(Map<String,List<Integer>> provincesDataCum) {
        this.provincesDataCum = provincesDataCum;
    }

    public void add(String province,int type,int num) {
            List<Integer> list = provincesDataInc.get(province);
            list.set(type,num);
    }

    public void count() {
        for(String province : provincesDataCum.keySet()) {
            for(int i = 0;i < 4;i++) {
                nationalDataInc.set(i,nationalDataInc.get(i) + provincesDataInc.get(province).get(i));
                nationalDataCum.set(i,nationalDataCum.get(i) + provincesDataCum.get(province).get(i));
            }
        }
    }

    public List<Integer> getNationalDataInc() {
        return nationalDataInc;
    }

    public List<Integer> getNationalDataCum() {
        return nationalDataCum;
    }
}
