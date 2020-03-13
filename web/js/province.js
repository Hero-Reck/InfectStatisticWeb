let loc = location.href;
let n1 = loc.length;
let n2 = loc.indexOf('=');
let province = decodeURI(loc.substr(n2+1,n1-n2));
$("#province").html(province);

const chartBtn4 = $("#chartBtn4");
const chartBtn5 = $("#chartBtn5");
const chartBtn6 = $("#chartBtn6");
chartBtn4.click(function () {
    chartBtn5.removeClass("btn-danger");
    chartBtn6.removeClass("btn-danger");
    chartBtn4.addClass("btn-danger");
    $("#exiInfChart").css({display:"block"});
    $("#incInfDeadCureChart").css({display:"none"});
    $("#cumInfDeadCureChart").css({display:"none"});
});
chartBtn5.click(function () {
    chartBtn4.removeClass("btn-danger");
    chartBtn6.removeClass("btn-danger");
    chartBtn5.addClass("btn-danger");
    $("#exiInfChart").css({display:"none"});
    $("#incInfDeadCureChart").css({display:"block"});
    $("#cumInfDeadCureChart").css({display:"none"});
});
chartBtn6.click(function () {
    chartBtn4.removeClass("btn-danger");
    chartBtn5.removeClass("btn-danger");
    chartBtn6.addClass("btn-danger");
    $("#exiInfChart").css({display:"none"});
    $("#incInfDeadCureChart").css({display:"none"});
    $("#cumInfDeadCureChart").css({display:"block"});
});
$("#btnBack").click(function () {
    location.href="index.html";
});
$("#icon2").click(function () {
    alert("数据仅供测试使用，并非真实数据");
});

let exiInfChart = echarts.init(document.getElementById("exiInfChart"));
let incInfDeadCureChart = echarts.init(document.getElementById("incInfDeadCureChart"));
let cumInfDeadCureChart = echarts.init(document.getElementById("cumInfDeadCureChart"));
loadProvinceData();
function loadProvinceData(date) {
    exiInfChart.showLoading();
    incInfDeadCureChart.showLoading();
    cumInfDeadCureChart.showLoading();
    $.ajax({
        type:"POST",
        url:"ProvinceData",
        data:{
            date:date,
            province:province
        },
        dataType:"json",
        success:function (data) {
            if (date == null) {
                $("#dateSpan2").html(data[0][data[0].length - 1].date);
            } else {
                $("#dateSpan2").html(date);
            }
            let dropdown2 = $("#dropdown2");
            dropdown2.empty();
            for(let i =0;i < data[0].length;i++) {
                dropdown2.append("<li><button class='dateList2 btn'>"+data[0][i].date+"</button></li>");
            }
            $(".dateList2").click(function (e) {
                loadProvinceData($(this).html());
            });
            $("#infectExi2").text(data[1][0].num);
            let compare1 = data[1][0].compare;
            $("#infectExiInc2").text(compare1 >= 0 ? "+" + compare1 : "-" + compare1);
            $("#infectCum2").text(data[1][1].num);
            let compare2 = data[1][1].compare;
            $("#infectCumInc2").text(compare2 >= 0 ? "+" + compare2 : "-" + compare2);
            $("#dead2").text(data[1][2].num);
            let compare3 = data[1][2].compare;
            $("#deadInc2").text(compare3 >= 0 ? "+" + compare3 : "-" + compare3);
            $("#cure2").text(data[1][3].num);
            let compare4 = data[1][3].compare;
            $("#cureInc2").text(compare4 >= 0 ? "+" + compare4 : "-" + compare4);
            let xAxis = [];
            let exiInfData = [];
            let incInfData = [];
            let incDeadData = [];
            let incCureData = [];
            let cumInfData = [];
            let cumDeadData = [];
            let cumCureData = [];
            for (let i = 0;i < data[2].length;i++) {
                xAxis.push(data[2][i].xAxis);
                exiInfData.push(data[2][i].exiInf);
                incInfData.push(data[3][i].incInf);
                incDeadData.push(data[3][i].incDead);
                incCureData.push(data[3][i].incCure);
                cumInfData.push(data[4][i].cumInf);
                cumDeadData.push(data[4][i].cumDead);
                cumCureData.push(data[4][i].cumCure);
            }
            exiInfChart.setOption({
                color:['#d14a61'],
                legend: {
                    data: ['现存确诊']
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
                        name: '现存确诊',
                        data: exiInfData,
                        type: 'line',
                        smooth:true
                    }
                ]
            });
            exiInfChart.hideLoading();
            incInfDeadCureChart.setOption({
                color:[ '#d14a61', '#05042f','#27f34c'],
                legend: {
                    data: ['新增确诊', '新增死亡','新增治愈' ]
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
                        name: '新增确诊',
                        data: incInfData,
                        type: 'line',
                        smooth:true
                    }, {
                        name: '新增死亡',
                        data: incDeadData,
                        type: 'line',
                        smooth:true
                    },{
                        name: '新增治愈',
                        data: incCureData,
                        type: 'line',
                        smooth:true
                    }
                ]
            });
            incInfDeadCureChart.hideLoading();
            cumInfDeadCureChart.setOption({
                color:[ '#d14a61', '#05042f','#27f34c'],
                legend: {
                    data: ['累计确诊', '累计死亡','累计治愈' ]
                },
                xAxis: {
                    type: 'category',
                    data: xAxis,   // x轴数据
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
                        name: '累计确诊',
                        data:  cumInfData,
                        type: 'line',
                        smooth:true
                    }, {
                        name: '累计死亡',
                        data: cumDeadData,
                        type: 'line',
                        smooth:true
                    },{
                        name: '累计治愈',
                        data: cumCureData,
                        type: 'line',
                        smooth:true
                    }
                ]
            });
            cumInfDeadCureChart.hideLoading();
        }
    });
}