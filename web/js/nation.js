const navBtn1 = $("#navBtn1");
const navBtn2 = $("#navBtn2");
const navBtn3 = $("#navBtn3");
const exitBtn = $("#exiBtn");
const cumBtn = $("#cumBtn");
const chartBtn1 = $("#chartBtn1");
const chartBtn2 = $("#chartBtn2");
const chartBtn3 = $("#chartBtn3");

navBtn1.click(function (e) {
    navBtn2.removeClass("btn-danger");
    navBtn3.removeClass("btn-danger");
    navBtn1.addClass("btn-danger");
    location.href="#charts";
});
navBtn2.click(function (e) {
    navBtn1.removeClass("btn-danger");
    navBtn3.removeClass("btn-danger");
    navBtn2.addClass("btn-danger");
    location.href="#nationalData";
});
navBtn3.click(function (e) {
    navBtn1.removeClass("btn-danger");
    navBtn2.removeClass("btn-danger");
    navBtn3.addClass("btn-danger");
    location.href="#message";
});
cumBtn.click(function () {
    exitBtn.removeClass("btn-danger");
    exitBtn.addClass("btn-primary");
    cumBtn.addClass("btn-danger");
    $("#existMap").css({
        display:"none"
    });
    $("#cumulativeMap").css({
        display:"block"
    });
});
exitBtn.click(function () {
    cumBtn.removeClass("btn-danger");
    cumBtn.addClass("btn-primary");
    exitBtn.addClass("btn-danger");
    $("#existMap").css({
        display:"block"
    });
    $("#cumulativeMap").css({
        display:"none"
    });
});
chartBtn1.click(function () {
    chartBtn2.removeClass("btn-danger");
    chartBtn3.removeClass("btn-danger");
    chartBtn1.addClass("btn-danger");
    $("#incSusInfChart").css({display:"block"});
    $("#exiSusInfChart").css({display:"none"});
    $("#deadCureChart").css({display:"none"});
});
chartBtn2.click(function () {
    chartBtn1.removeClass("btn-danger");
    chartBtn3.removeClass("btn-danger");
    chartBtn2.addClass("btn-danger");
    $("#incSusInfChart").css({display:"none"});
    $("#exiSusInfChart").css({display:"block"});
    $("#deadCureChart").css({display:"none"});
});
chartBtn3.click(function () {
    chartBtn1.removeClass("btn-danger");
    chartBtn2.removeClass("btn-danger");
    chartBtn3.addClass("btn-danger");
    $("#incSusInfChart").css({display:"none"});
    $("#exiSusInfChart").css({display:"none"});
    $("#deadCureChart").css({display:"block"});
});
$("#icon").click(function () {
    alert("数据仅供测试使用，并非真实数据");
});
let existMap = echarts.init(document.getElementById("existMap"));
let cumulativeMap = echarts.init(document.getElementById("cumulativeMap"));
let incSusInfChart = echarts.init(document.getElementById("incSusInfChart"));
let exiSusInfChart = echarts.init(document.getElementById("exiSusInfChart"));
let deadCureChart = echarts.init(document.getElementById("deadCureChart"));

loadNationalData();
function loadNationalData(date) {
    existMap.showLoading();
    cumulativeMap.showLoading();
    incSusInfChart.showLoading();
    exiSusInfChart.showLoading();
    deadCureChart.showLoading();
    console.log("start");
    $.ajax({
        type:"POST",
        url:"NationalData",
        data:{
            date:date
        },
        dataType:"json",
        success:function (data) {
            if(date == null) {
                $("#dateSpan").text(data[0][data[0].length - 1].date);
            } else {
                $("#dateSpan").text(date);
            }
            $("#dropdown").empty();
            for(var i =0;i < data[0].length;i++) {
                $("#dropdown").append("<li><button class='dateList btn'>"+data[0][i].date+"</button></li>");
            }
            $(".dateList").click(function (e) {
                loadNationalData($(this).html());
            });
            $("#infectExi").text(data[1][0].num);
            let compare1 = data[1][0].compare;
            $("#infectExiInc").text(compare1 >= 0 ? "+" + compare1 : "-" + compare1);
            $("#infectCum").text(data[1][1].num);
            let compare2 = data[1][1].compare;
            $("#infectCumInc").text(compare2 >= 0 ? "+" + compare2 : "-" + compare2);
            $("#susCum").text(data[1][2].num);
            let compare3 = data[1][2].compare;
            $("#susCumInc").text(compare3 >= 0 ? "+" + compare3 : "-" + compare3);
            $("#dead").text(data[1][3].num);
            let compare4 = data[1][3].compare;
            $("#deadInc").text(compare4 >= 0 ? "+" + compare4 : "-" + compare4);
            $("#cure").text(data[1][4].num);
            let compare5 = data[1][4].compare;
            $("#cureInc").text(compare5 >= 0 ? "+" + compare5 : "-" + compare5);
            existMap.setOption({
                backgroundColor: '#ffffff',
                title: {
                    text: '确诊病例数（排除治愈、死亡）',
                    subtext: '',
                    x:'right'
                },
                tooltip : {
                    trigger: 'item'
                },
                visualMap: {
                    show : true,
                    x: 'left',
                    y: 'bottom',
                    splitList: [
                        {start: 10000, end:100000},{start: 1000, end: 9999},
                        {start: 500, end: 999},{start: 100, end: 499},
                        {start: 10, end: 99},{start: 1, end: 9},{start: 0, end: 0}
                    ],
                    color: ['#581312', '#ad0005', '#ea0a0b','#e26b21', '#E2B089', '#EEFFB4', '#ffffff']
                },
                series: [{
                    name: '现存确诊',
                    type: 'map',
                    mapType: 'china',
                    roam: false,
                    label: {
                        normal: {
                            show: true  //省份名称
                        },
                        emphasis: {
                            show: false
                        }
                    },
                    itemStyle:{
                        normal:{
                            borderColor: 'rgba(0, 0, 0, 0.2)'
                        },
                        emphasis:{
                            areaColor: '#f3b027',
                            shadowOffsetX: 0,
                            shadowOffsetY: 0,
                            shadowBlur: 20,
                            borderWidth: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    },
                    data:data[2]  //数据
                }]
            });
            existMap.hideLoading();
            cumulativeMap.setOption({
                backgroundColor: '#ffffff',
                title: {
                    text: '累计病例数（包含治愈、死亡）',
                    subtext: '',
                    x:'right'
                },
                tooltip : {
                    trigger: 'item'
                },
                visualMap: {
                    show : true,
                    x: 'left',
                    y: 'bottom',
                    splitList: [
                        {start: 10000, end:100000},{start: 1000, end: 9999},
                        {start: 500, end: 999},{start: 100, end: 499},
                        {start: 10, end: 99},{start: 1, end: 9},{start: 0, end: 0}
                    ],
                    color: ['#581312', '#ad0005', '#ea0a0b','#e26b21', '#E2B089', '#EEFFB4', '#ffffff']
                },
                series: [{
                    name: '现存确诊',
                    type: 'map',
                    mapType: 'china',
                    roam: false,
                    label: {
                        normal: {
                            show: true  //省份名称
                        },
                        emphasis: {
                            show: false
                        }
                    },
                    itemStyle:{
                        normal:{
                            borderColor: 'rgba(0, 0, 0, 0.2)'
                        },
                        emphasis:{
                            areaColor: '#f3b027',
                            shadowOffsetX: 0,
                            shadowOffsetY: 0,
                            shadowBlur: 20,
                            borderWidth: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    },
                    data:data[3]  //数据
                }]
            });
            cumulativeMap.hideLoading();
            let xAxis = [];
            let incInfData = [];
            let incSusData = [];
            let exiSusData = [];
            let exiInfData = [];
            let deadData = [];
            let cureData = [];
            for(let i = 0;i<data[4].length;i++) {
                xAxis.push(data[4][i].xAxis);
                incInfData.push(data[4][i].incInf);
                incSusData.push(data[4][i].incSus);
                exiInfData.push(data[5][i].exiInf);
                exiSusData.push(data[5][i].exiSus);
                deadData.push(data[6][i].dead);
                cureData.push(data[6][i].cure);
            }
            incSusInfChart.setOption({
                color:['#dcf317', '#d14a61'],
                legend: {
                    data: ['新增疑似', '新增确诊', ]
                },
                xAxis: {
                    type: 'category',
                    data: xAxis,
                    name: '日期',
                    nameTextStyle: {
                        fontWeight: 600,
                        fontSize: 18
                    }
                },
                yAxis: {
                    type: 'value',
                    name: '人数',   // y轴名称
                    // y轴名称样式
                    nameTextStyle: {
                        fontWeight: 600,
                        fontSize: 18
                    }
                },
                label: {
                },
                tooltip: {
                    trigger: 'axis'   // axis   item   none三个值
                },
                series: [
                    {
                        name: '新增疑似',
                        data: incSusData,
                        type: 'line',
                        smooth:true
                    }, {
                        name: '新增确诊',
                        data: incInfData,
                        type: 'line',
                        smooth:true
                    },
                ]
            });
            incSusInfChart.hideLoading();
            exiSusInfChart.setOption({
                color:['#dcf317', '#d14a61'],
                legend: {
                    data: ['现存疑似', '现存确诊', ]
                },
                xAxis: {
                    type: 'category',
                    data: xAxis,
                    name: '日期',
                    nameTextStyle: {
                        fontWeight: 600,
                        fontSize: 18
                    }
                },
                yAxis: {
                    type: 'value',
                    name: '人数',
                    nameTextStyle: {
                        fontWeight: 600,
                        fontSize: 18
                    }
                },
                label: {
                },
                tooltip: {
                    trigger: 'axis'
                },
                series: [
                    {
                        name: '现存疑似',
                        data:exiSusData,
                        type: 'line',
                        smooth:true
                    }, {
                        name: '现存确诊',
                        data: exiInfData,
                        type: 'line',
                        smooth:true
                    },
                ]
            });
            exiSusInfChart.hideLoading();
            deadCureChart.setOption({
                color:['#05042f','#27f34c'],
                legend: {
                    data: ['死亡', '治愈', ]
                },
                xAxis: {
                    type: 'category',
                    data: xAxis,
                    name: '日期',
                    nameTextStyle: {
                        fontWeight: 600,
                        fontSize: 18
                    }
                },
                yAxis: {
                    type: 'value',
                    name: '人数',
                    nameTextStyle: {
                        fontWeight: 600,
                        fontSize: 18
                    }
                },
                label: {
                },
                tooltip: {
                    trigger: 'axis'
                },
                series: [
                    {
                        name: '死亡',
                        data: deadData,
                        type: 'line',
                        smooth:true
                    }, {
                        name: '治愈',
                        data: cureData,
                        type: 'line',
                        smooth:true
                    },
                ]
            });
            deadCureChart.hideLoading();
            let tableData = data[7];
            let temp;
            for (let i = 0 ;i < tableData.length; i++) {
                for (let j = 0;j < tableData.length - i - 1;j++) {
                    if(tableData[j].exiInf < tableData[j + 1].exiInf) {
                        temp = tableData[j];
                        tableData[j] = tableData[j + 1];
                        tableData[j + 1] = temp;
                    }
                }
            }
            $("#dataTable").empty();

            for(let i =0;i < tableData.length;i++) {
                $("#dataTable").append("<tr>" +
                    "<td><h4>" + tableData[i].area + "</h4></td>" +
                    "<td><h4>" + tableData[i].exiInf + "</h4></td>" +
                    "<td><h4>" + tableData[i].cumInf + "</h4></td>" +
                    "<td><h4>" + tableData[i].dead + "</h4></td>" +
                    "<td><h4>" + tableData[i].cure + "</h4></td>" +
                    "</tr>");
            }
        }
    });
    existMap.on("click",function (param) {
        location.href = "province.html?province=" + encodeURI(param.name);
    });
    cumulativeMap.on("click",function (param) {
        location.href = "province.html?province=" + encodeURI(param.name);
    })
}