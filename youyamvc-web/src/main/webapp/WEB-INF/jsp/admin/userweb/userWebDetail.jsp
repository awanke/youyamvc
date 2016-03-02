

<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%--
 Created by hdy.
 如果你改变了此类 read 请将此行删除
 799374340@qq.com
--%><!DOCTYPE html>
<html>
<head>
    <%@include file="../../common/head.jsp"%>
    <title>AdminLTE | Dashboard</title>
    <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
    <%@include file="../include/head.jsp"%>
</head>
<body class="skin-blue">
    <header class="header">
        <%@include file="../include/top.jsp"%>
    </header>
    <div class="wrapper row-offcanvas row-offcanvas-left">
        <aside class="left-side sidebar-offcanvas">
            <%@include file="../include/left.jsp"%>
        </aside>
        <aside class="right-side">
            <section class="content-header">
                <h1>
                    用户详情
                    <small></small>
                </h1>
                <ol class="breadcrumb">
                    <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                    <li><a href="#">Tables</a></li>
                    <li class="active">Data tables</li>
                </ol>
            </section>
            <section class="content">
                <div class="row">
                    <div class="col-md-12">
                        <div class="box box-primary">
                            <form role="form" action="admin/user_web/save" method="post">
                                <div class="box-header">
                                    <h3 class="box-title">
                                        <c:choose><c:when test="${userWeb.id==null}">新增</c:when><c:otherwise>编辑</c:otherwise></c:choose>
                                    </h3>
                                </div>
                                <input type="hidden" name="id" value="${userWeb.id}">
                                <div class="box-body">
                                            <div class="form-group ">
                                                <label for="twoCodeImgSrc">
                                                    二维码图片
                                                </label>
                                         <img id="twoCodeImgSrcReview"  src="${userWeb.twoCodeImgSrc}" />
                                         <input type="text" class="form-control "
                                                      id="twoCodeImgSrc" name="twoCodeImgSrc"
                                                      value="${userWeb.twoCodeImgSrc}" placeholder="">
                                         <input type="file" id="twoCodeImgSrcFile"
                                                     name="myfiles" onchange="uploadFile('twoCodeImgSrc','userweb')" class="file" />
                                            </div>
                                            <div class="form-group ">
                                                <label for="userPassword">
                                                    登录密码存储加密后的值
                                                </label>
                                     <input type="text" class="form-control " id="userPassword"
                                            name="userPassword" title="登录密码存储加密后的值"  placeholder="请输入登录密码存储加密后的值"
                                            value="${userWeb.userPassword}">
                                            </div>
                                            <div class="form-group ">
                                                <label for="headImgSrc">
                                                    头像地址
                                                </label>
                                     <input type="text" class="form-control " id="headImgSrc"
                                            name="headImgSrc" title="头像地址"  placeholder="请输入头像地址"
                                            value="${userWeb.headImgSrc}">
                                            </div>
                                            <div class="form-group ">
                                                <label for="userName">
                                                    登录名称
                                                </label>
                                     <input type="text" class="form-control " id="userName"
                                            name="userName" title="登录名称"  placeholder="请输入登录名称"
                                            value="${userWeb.userName}">
                                            </div>
                                            <div class="form-group ">
                                                <label for="mobile">
                                                    手机号码
                                                </label>
                                     <input type="text" class="form-control " id="mobile"
                                            name="mobile" title="手机号码"  placeholder="请输入手机号码"
                                            value="${userWeb.mobile}">
                                            </div>
                                            <div class="form-group ">
                                                <label for="nickname">
                                                    昵称
                                                </label>
                                     <input type="text" class="form-control " id="nickname"
                                            name="nickname" title="昵称"  placeholder="请输入昵称"
                                            value="${userWeb.nickname}">
                                            </div>
                                            <div class="form-group ">
                                                <label for="realName">
                                                    用户真名
                                                </label>
                                     <input type="text" class="form-control " id="realName"
                                            name="realName" title="用户真名"  placeholder="请输入用户真名"
                                            value="${userWeb.realName}">
                                            </div>
                                            <div class="form-group ">
                                                <label for="scoreAmount">
                                                    积分余额
                                                </label>
                                     <input type="text" class="form-control " id="scoreAmount"
                                            name="scoreAmount" title="积分余额"  placeholder="请输入积分余额"
                                            value="${userWeb.scoreAmount}">
                                            </div>
                                            <div class="form-group ">
                                                <label for="moneyAmount">
                                                    现金余额
                                                </label>
                                     <input type="text" class="form-control " id="moneyAmount"
                                            name="moneyAmount" title="现金余额"  placeholder="请输入现金余额"
                                            value="${userWeb.moneyAmount}">
                                            </div>
                                            <div class="form-group ">
                                                <label for="registTime">
                                                    注册时间
                                                </label>
                                     <input id="registTime" type="text" name="registTime" class="Wdate form-control"                                             onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-%d %H:%m:%s'})"
                                            value="<fmt:formatDate value="${ userWeb.registTime }"
                                            pattern="yyyy-MM-dd HH:mm:ss"/>" style="width: 170px;">                                            </div>
                                            <div class="form-group ">
                                                <label for="lastLoginTime">
                                                    最后登录时间
                                                </label>
                                     <input id="lastLoginTime" type="text" name="lastLoginTime" class="Wdate form-control"                                             onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-%d %H:%m:%s'})"
                                            value="<fmt:formatDate value="${ userWeb.lastLoginTime }"
                                            pattern="yyyy-MM-dd HH:mm:ss"/>" style="width: 170px;">                                            </div>
                                            <div class="form-group ">
                                                <label for="accountStatus">
                                                    账号状态
                                                </label>
                                         <select class="form-control" id="accountStatus" name="accountStatus" title="账号状态"  >
                                                             <option
                                                 <c:if test="${ userWeb.accountStatus == 0 }">selected</c:if>
                                                 value="0" >禁用</option>
                                                             <option
                                                 <c:if test="${ userWeb.accountStatus == 1 }">selected</c:if>
                                                 value="1" >启用</option>
                                         </select>
                                            </div>
                                            <div class="form-group ">
                                                <label for="sex">
                                                    性别
                                                </label>
                                         <select class="form-control" id="sex" name="sex" title="性别"  >
                                                             <option
                                                 <c:if test="${ userWeb.sex == 0 }">selected</c:if>
                                                 value="0" >女</option>
                                                             <option
                                                 <c:if test="${ userWeb.sex == 1 }">selected</c:if>
                                                 value="1" >男</option>
                                         </select>
                                            </div>
                                            <div class="form-group ">
                                                <label for="birthday">
                                                    生日
                                                </label>
                                     <input id="birthday" type="text" name="birthday" class="Wdate form-control"                                             onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-%d %H:%m:%s'})"
                                            value="<fmt:formatDate value="${ userWeb.birthday }"
                                            pattern="yyyy-MM-dd HH:mm:ss"/>" style="width: 170px;">                                            </div>
                                            <div class="form-group ">
                                                <label for="babySex">
                                                    大宝宝性别
                                                </label>
                                         <select class="form-control" id="babySex" name="babySex" title="大宝宝性别"  >
                                                             <option
                                                 <c:if test="${ userWeb.babySex == 0 }">selected</c:if>
                                                 value="0" >女</option>
                                                             <option
                                                 <c:if test="${ userWeb.babySex == 1 }">selected</c:if>
                                                 value="1" >男</option>
                                         </select>
                                            </div>
                                            <div class="form-group ">
                                                <label for="babyBirthday">
                                                    大宝宝生日
                                                </label>
                                     <input id="babyBirthday" type="text" name="babyBirthday" class="Wdate form-control"                                             onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-%d %H:%m:%s'})"
                                            value="<fmt:formatDate value="${ userWeb.babyBirthday }"
                                            pattern="yyyy-MM-dd HH:mm:ss"/>" style="width: 170px;">                                            </div>
                                            <div class="form-group ">
                                                <label for="babyTwoSex">
                                                    二宝宝性别
                                                </label>
                                         <select class="form-control" id="babyTwoSex" name="babyTwoSex" title="二宝宝性别"  >
                                                             <option
                                                 <c:if test="${ userWeb.babyTwoSex == 0 }">selected</c:if>
                                                 value="0" >女</option>
                                                             <option
                                                 <c:if test="${ userWeb.babyTwoSex == 1 }">selected</c:if>
                                                 value="1" >男</option>
                                         </select>
                                            </div>
                                            <div class="form-group ">
                                                <label for="babyTwoBirthday">
                                                    二宝宝生日
                                                </label>
                                     <input id="babyTwoBirthday" type="text" name="babyTwoBirthday" class="Wdate form-control"                                             onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-%d %H:%m:%s'})"
                                            value="<fmt:formatDate value="${ userWeb.babyTwoBirthday }"
                                            pattern="yyyy-MM-dd HH:mm:ss"/>" style="width: 170px;">                                            </div>
                                            <div class="form-group ">
                                                <label for="babyThreeSex">
                                                    三宝宝性别
                                                </label>
                                         <select class="form-control" id="babyThreeSex" name="babyThreeSex" title="三宝宝性别"  >
                                                             <option
                                                 <c:if test="${ userWeb.babyThreeSex == 0 }">selected</c:if>
                                                 value="0" >女</option>
                                                             <option
                                                 <c:if test="${ userWeb.babyThreeSex == 1 }">selected</c:if>
                                                 value="1" >男</option>
                                         </select>
                                            </div>
                                            <div class="form-group ">
                                                <label for="babyThreeBirthday">
                                                    三宝宝生日
                                                </label>
                                     <input id="babyThreeBirthday" type="text" name="babyThreeBirthday" class="Wdate form-control"                                             onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-%d %H:%m:%s'})"
                                            value="<fmt:formatDate value="${ userWeb.babyThreeBirthday }"
                                            pattern="yyyy-MM-dd HH:mm:ss"/>" style="width: 170px;">                                            </div>
                                            <div class="form-group ">
                                                <label for="accountLevel">
                                                    账号等级
                                                </label>
                                         <select class="form-control" id="accountLevel" name="accountLevel" title="账号等级"  >
                                                             <option
                                                 <c:if test="${ userWeb.accountLevel == 1 }">selected</c:if>
                                                 value="1" >1级</option>
                                                             <option
                                                 <c:if test="${ userWeb.accountLevel == 2 }">selected</c:if>
                                                 value="2" >二级</option>
                                         </select>
                                            </div>
                                </div><!-- /.box-body -->
                                <div class="box-footer">
                                    <button type="submit" class="btn btn-primary">保存</button>
                                    <button type="button" onclick="history.go(-1);"  class="btn btn-primary">返回</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </section>
        </aside>
    </div>
    <%@include file="../include/tail.jsp"%>

    <script charset="utf-8" src="assets/admin/app/base.js"></script>
    <script language="javascript" type="text/javascript" src="${CTX}assets/admin/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="assets/admin/js/ajaxfileupload.js"></script>
</body>
</html>
