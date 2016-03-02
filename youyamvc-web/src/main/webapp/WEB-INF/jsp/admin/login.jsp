<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html class="bg-black">
<head>
    <base href="${CTX}">
    <%@include file="../common/head.jsp"%>
    <title>AdminLTE | Log in</title>
    <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
    <!-- bootstrap 3.0.2 -->
    <link href="assets/admin/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <!-- font Awesome -->
    <link href="assets/admin/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
    <!-- Theme style -->
    <link href="assets/admin/css/AdminLTE.css" rel="stylesheet" type="text/css" />

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->

</head>
<body class="bg-black">

<div class="form-box" id="login-box">
    <div class="header">登录</div>
    <form action="admin" method="post">
        <div class="body bg-gray">
            <div class="form-group">
                <input type="text" name="userName" class="form-control" placeholder="用户名" value="admin"/>
            </div>
            <div class="form-group">
                <input type="password" name="password" class="form-control" placeholder="密码" value="admin"/>
            </div>
            <div class="form-group">
                <input type="text" name="checkCode" class="form-control" placeholder="验证码" value=""/>
            </div>
            <div class="form-group">
                <img alt="验证码看不清，换一张" src="admin/checkCode" id="checkCode" onclick="changeImg(this)">
            </div>
        </div>
        <div class="footer">
            <button type="submit" class="btn bg-olive btn-block">登录</button>
        </div>
    </form>


</div>


<!-- jQuery 2.0.2 -->
<script src="assets/admin/js/jquery/jquery-2.0.2.js"></script>
<!-- Bootstrap -->
<script src="assets/admin/js/bootstrap.min.js" type="text/javascript"></script>
<script type="text/javascript">
    //刷新验证码
    function changeImg(obj){
        document.getElementById(obj.id).src="admin/checkCode?d="+Math.random();
    }
</script>
</body>
</html>