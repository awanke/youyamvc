/**
* Created by hdy.
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/

function buildReqParam(){
    var requestParam = {date:new Date().getTime()}
    setRequestParamById(requestParam,'idFirst')
    setRequestParamById(requestParam,'userNameFirst')
    setRequestParamById(requestParam,'mobileFirst')
    setRequestParamById(requestParam,'realNameFirst')
    setRequestParamById(requestParam,'accountLevelFirst')
    //排序
    var orderBy = buildOrderBy()
    if(typeof orderBy =='undefined' || orderBy!=''){
        requestParam.orderBy = orderBy;
    }
    return requestParam
}

/*pageIndex 当前是第几页 1:第一页*/
function  getItemPage(pageIndex){
    var template =
        '<tr id="tr{id}"><td><input type="checkbox" value="{id}" class="tdcheckbox"></td><td>{rankNum}</td>'+
        '<td>{id}</td>' +
        '<td>{userName}</td>' +
        '<td>{mobile}</td>' +
        '<td>{nickname}</td>' +
        '<td>{realName}</td>' +
        '<td>{scoreAmount}</td>' +
        '<td>{moneyAmount}</td>' +
        '<td>{registTime}</td>' +
        '<td>{lastLoginTime}</td>' +
        '<td>{accountStatus}</td>' +
        '<td>{sex}</td>' +
        '<td>{birthday}</td>' +
        '<td>{babySex}</td>' +
        '<td>{babyBirthday}</td>' +
        '<td>{babyTwoSex}</td>' +
        '<td>{babyTwoBirthday}</td>' +
        '<td>{babyThreeSex}</td>' +
        '<td>{babyThreeBirthday}</td>' +
        '<td>{accountLevel}</td>' +
        '<td><a href="admin/user_web/detail/{id}">编辑</a>' +
        '<a href="javascript:{}" onclick="deleteItem(\'user_web\',{id})">删除</a></td></tr>';

    var pageSize = 20;//每页多少条记录
    var pageCount = parseInt($("#pageCount").val());//总共多少条记录
    var url ='admin/user_web/page/'+pageIndex+'/'+pageCount;

    $.ajax({
        url:url,
        type:'get',
        dataType:'json',
        data:buildReqParam(),
        cache:false,
        beforeSend:function(xhr){},
        complete:function(xhr){},
        success:function(data){
            var rankNum = calcRankNum(pageIndex,pageSize);
            $.each(data.result, function(index, val){
                if(index =="pageCount"){
                    $("#pageCount").val(pageCount)
                    pageCount = val;
                }
                if(index == "pageList"){
                    var templateHtml = "";
                    $.each(val, function(index1, value){
                        //排名
                        value.rankNum = ++rankNum;
                        //自定义输出
                        var accountStatusMap = {"":"全部","0":"禁用","1":"启用"}
                        value.accountStatus = accountStatusMap[value.accountStatus+""]
                        var sexMap = {"":"全部","0":"女","1":"男"}
                        value.sex = sexMap[value.sex+""]
                        var babySexMap = {"":"全部","0":"女","1":"男"}
                        value.babySex = babySexMap[value.babySex+""]
                        var babyTwoSexMap = {"":"全部","0":"女","1":"男"}
                        value.babyTwoSex = babyTwoSexMap[value.babyTwoSex+""]
                        var babyThreeSexMap = {"":"全部","0":"女","1":"男"}
                        value.babyThreeSex = babyThreeSexMap[value.babyThreeSex+""]
                        var accountLevelMap = {"":"全部","1":"1级","2":"二级"}
                        value.accountLevel = accountLevelMap[value.accountLevel+""]
                        //赋值替换
                        var tm = template;
                        for(var key in value){
                            var reg = new RegExp('{'+key+'}','g');
                            tm = tm.replace(reg , value[key]);
                        }
                        templateHtml+=tm;
                    });
                    $("#tbody").html(templateHtml);
                }
            });
            //构造分页html
            appendPagingBarHtml("pagination",pageIndex,pageCount,pageSize)
        }
    });
}
