<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <%@include file="../../common/head.jsp"%>
    <title>AdminLTE | Dashboard</title>
    <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
    <%@include file="../include/head.jsp"%>
</head>
<body class="skin-blue">
<!-- header logo: style can be found in header.less -->
<header class="header">
    <%@include file="../include/top.jsp"%>
</header>
<div class="wrapper row-offcanvas row-offcanvas-left">
    <!-- Left side column. contains the logo and sidebar -->
    <aside class="left-side sidebar-offcanvas">
        <%@include file="../include/left.jsp"%>
    </aside>
    <!-- Right side column. Contains the navbar and content of the page -->
    <aside class="right-side">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                详情
                <small>advanced tables</small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                <li><a href="#">Tables</a></li>
                <li class="active">Data tables</li>
            </ol>
        </section>
        <!-- Main content -->
        <section class="content">
            <div class="row">
                <!-- left column -->
                <div class="col-md-6">
                    <!-- general form elements -->
                    <div class="box box-primary">
                        <!-- form start -->
                        <form role="form" action="admin/adminUser/save" method="post">
                            <div class="box-header">
                                <h3 class="box-title">
                                    <c:choose><c:when test="${adminUser.id==null}">新增</c:when><c:otherwise>编辑</c:otherwise></c:choose>
                                </h3>
                            </div><!-- /.box-header -->
                            <input type="hidden" name="id" value="${adminUser.id}">
                            <div class="box-body">
                                <div class="form-group">
                                    <label for="userName">用户名</label>
                                    <input type="text" name="userName" value="${adminUser.userName}" class="form-control" id="userName" placeholder="">
                                </div>
                                <div class="form-group">
                                    <label for="password">密码</label>
                                    <input type="text" name="password" value="" class="form-control" id="password" placeholder="为了安全起见，编辑请输入最新密码  密码长度大于6位 否则更新失败">
                                </div>
                                <div class="form-group">
                                    <label for="realName">真名</label>
                                    <input type="text" name="realName" value="${adminUser.realName}" class="form-control" id="realName" placeholder="">
                                </div>
                                <div class="form-group">
                                    <label for="email">邮箱</label>
                                    <input type="text" name="email" value="${adminUser.email}" class="form-control" id="email" placeholder="">
                                </div>
                                <div class="form-group">
                                    <label for="telephone">座机号</label>
                                    <input type="text" name="telephone" value="${adminUser.telephone}" class="form-control" id="telephone" placeholder="">
                                </div>
                                <div class="form-group">
                                    <label for="mobilePhone">手机号</label>
                                    <input type="text" name="mobilePhone" value="${adminUser.mobilePhone}" class="form-control" id="mobilePhone" placeholder="">
                                </div>
                                <div class="form-group">
                                    <label for="address">手机号</label>
                                    <input type="text" name="address" value="${adminUser.address}" class="form-control" id="address" placeholder="">
                                </div>
                                <div class="form-group hidden">
                                    <label for="createTimeYmd">`create_time_ymd` int DEFAULT '0'</label>
                                    <input type="text" name="createTimeYmd" value="${adminUser.createTimeYmd}" class="form-control" id="createTimeYmd" placeholder="">
                                </div>
                                <div class="form-group hidden">
                                    <label for="createTimeHms">`create_time_hms` int DEFAULT '0'</label>
                                    <input type="text" name="createTimeHms" value="${adminUser.createTimeHms}" class="form-control" id="createTimeHms" placeholder="">
                                </div>
                                <div class="form-group hidden">
                                    <label for="modifiedTimeYmd">`modified_time_ymd` int DEFAULT '0'</label>
                                    <input type="text" name="modifiedTimeYmd" value="${adminUser.modifiedTimeYmd}" class="form-control" id="modifiedTimeYmd" placeholder="">
                                </div>
                                <div class="form-group hidden">
                                    <label for="modifiedTimeHms">`modified_time_hms` int DEFAULT '0'</label>
                                    <input type="text" name="modifiedTimeHms" value="${adminUser.modifiedTimeHms}" class="form-control" id="modifiedTimeHms" placeholder="">
                                </div>
                                <div class="form-group">
                                    <label for="superAdmin">超级管理员</label>
                                    <select class="form-control" name="superAdmin" id="superAdmin">
                                        <option value="0" <c:if test="${adminUser.superAdmin==0}">selected="selected" </c:if>>否</option>
                                        <option value="1" <c:if test="${adminUser.superAdmin==1}">selected="selected" </c:if>>是</option>
                                    </select>
                                </div>
                            </div><!-- /.box-body -->
                            <div class="box-footer">
                                <button type="submit" class="btn btn-primary">保存</button>
                                <button type="button" onclick="history.go(-1);"  class="btn btn-primary">返回</button>
                            </div>
                        </form>
                    </div><!-- /.box -->
                </div><!--/.col (left) -->
            </div>
        </section><!-- /.content -->
    </aside><!-- /.right-side -->
</div><!-- ./wrapper -->
<%@include file="../include/tail.jsp"%>
<script language="javascript" type="text/javascript" src="${CTX}assets/admin/js/My97DatePicker/WdatePicker.js"></script>
</body>
</html>
