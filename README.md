# InfectStatisticWeb
结对第二次作业——某次疫情统计可视化的实现

+ 这个作业属于哪个课程
[2020春丨w班](https://edu.cnblogs.com/campus/fzu/2020SpringW)
+ 这个作业要求在哪里
[作业要求](https://edu.cnblogs.com/campus/fzu/2020SpringW/homework/10400)
+ 结对学号
221701312 221701328
+ 这个作业的目标
用web技术来实现原型中的功能
+ 作业正文
[作业正文](https://www.cnblogs.com/rachalblog/p/12489655.html)

+ ### 项目介绍
    本项目是基于web的疫情统计可视化项目，是上一次疫情统计可视化原型设计的实现。
    + ##### 功能
        + 全国疫情数据展示
        + 现存确诊中国地区可视化展示
        + 累计确诊中国地区可视化展示
        + 全国新增确诊趋势图
        + 全国现存确诊、累计确诊趋势图
        + 全国死亡、治愈趋势图
        + 全国省级行政区疫情数据统计表展示
        + 地区疫情数据展示
        + 地区现存确诊趋势图
        + 地区新增确诊、治愈、死亡趋势图
        + 地区累计确诊、治愈、死亡趋势图
        + 辟谣与防护
        + 其他功能开发中。。。。。。
+ ### 构建&运行方法
    + 请使用IntelliJ IDEA来构建项目。过程如下：
    + 新建Web Application项目，版本4.0，配置运行环境为tomcat9，配置编译环境为JDK1.8及以上版本
    + 将源码中的src目录下所有文件、文件夹放到新建项目的src目录中
    + 将源码中的web目录下所有文件、文件夹放到新建项目的web目录中
    + 在新建项目的web/WEB-INF目录下新建classes目录，并配置classes目录为.class文件输出目录
    + 运行项目
+ ### 爬虫使用方法
    + 新建一个Java项目，将源码中src/util目录下的JsoupTool和Patterns两个类放到新建的项目中
    + 项目引入jsoup1.11.3.jar包，下载地址<https://www.mvnjar.com/org.jsoup/jsoup/1.11.3/detail.html>
    + 新建main方法初始化JsoupTool类，传入生成日志的目标路径
    + 运行