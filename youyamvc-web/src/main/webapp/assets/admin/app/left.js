//version 2016-03-23
/**
 * Created by Administrator on 2015/3/17.
 *
 */
var $ = jQuery;
$(document).ready(function(){
    $.getJSON("admin/loginMessage",{},function(data){
        $("#magicalCoderUserName").text(data.result.userName)
    })
    var aid = localStorage.aid
    var a = $("#"+aid);
    var li = a.parent();
    var ul = li.parent();
    var treeviewLi = ul.parent();
    li.addClass("active bg-yellow color-palette")
    ul.addClass("menu-open")
    treeviewLi.addClass("active")

    $("#leftNavigate").find("ul").find('a').bind('click',function(){
        var aid = $(this).attr("id")
        localStorage.aid = aid
        //setCookie("aid",aid, '1 HOUR','','/db/admin');
    })
})