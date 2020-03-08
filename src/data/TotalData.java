package data;

import dao.LogDao;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class TotalData {
    Map<Date, DatePartData> statistics;

    public TotalData() {
        statistics = new LinkedHashMap<>();
    }

    public void initData(LogDao dao) {

    }
}
