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
                用户列表
                <a class="btn btn-app" href="admin/user_web/detail"><i class="fa fa-edit"></i>新增</a>
            </h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i> 首页</a></li>
                <li><a href="#">li</a></li>
            </ol>
        </section>
        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-xs-12">
                    <div class="box">
                        <div class="box-header">
                            <div class="box-tools">
                                <div class="form">
                                    <div class="box-body">
                                        <div class="row">


                                         <div class="col-xs-2">
                                             <div class="form-group">
                                                 <label for="idFirst">主键 (=)</label>
                                                 <input type="text" class="form-control"
                                                  value="${ idFirst }"
                                                  id="idFirst" name="idFirst">
                                             </div>
                                         </div>

                                         <div class="col-xs-2">
                                             <div class="form-group">
                                                 <label for="userNameFirst">登录名称 (like)</label>
                                                 <input type="text" class="form-control"
                                                  value="${ userNameFirst }"
                                                  id="userNameFirst" name="userNameFirst">
                                             </div>
                                         </div>

                                         <div class="col-xs-2">
                                             <div class="form-group">
                                                 <label for="mobileFirst">手机号码 (like)</label>
                                                 <input type="text" class="form-control"
                                                  value="${ mobileFirst }"
                                                  id="mobileFirst" name="mobileFirst">
                                             </div>
                                         </div>

                                         <div class="col-xs-2">
                                             <div class="form-group">
                                                 <label for="realNameFirst">用户真名 (like)</label>
                                                 <input type="text" class="form-control"
                                                  value="${ realNameFirst }"
                                                  id="realNameFirst" name="realNameFirst">
                                             </div>
                                         </div>
                                         <div class="col-xs-2">
                                             <div class="form-group">
                                             <label for="accountLevelFirst">账号等级</label>
                                                 <select class="form-control"
                                                   id="accountLevelFirst" name="accountLevelFirst">
                                                             <option value="">全部</option>
                                                             <option value="1"
                                                                 <c:if test="${ accountLevel == 1 }">selected</c:if>
                                                              >1级
                                                             </option>
                                                             <option value="2"
                                                                 <c:if test="${ accountLevel == 2 }">selected</c:if>
                                                              >二级
                                                             </option>
                                                 </select>
                                             </div>
                                         </div>
                                            <div class="col-xs-2">
                                                <div class="form-group">
                                                    <label for="querySubmit">&nbsp;</label>
                                                    <button class="btn btn-primary form-control" id="querySubmit">查询</button>
                                                </div>
                                            </div>
                                            <input type="hidden" id="pageCount" value="0">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body table-responsive" style="overflow: auto">
                            <input type="button" onclick="batchDeleteItem('user_web')" value="删除选中项">

                            <table id="example2" class="table table-bordered table-hover dataTable">
                                <thead id="thead">
                                          <tr>
                                              <th><input type="checkbox" id="checkAll"></th>
                                              <th>序号</th>
                                             <th  class="sorting" orderField="id">
                                                  主键
                                              </th>
                                             <th  class="sorting" orderField="user_name">
                                                  登录名称
                                              </th>
                                             <th  class="sorting" orderField="mobile">
                                                  手机号码
                                              </th>
                                             <th  class="sorting" orderField="nickname">
                                                  昵称
                                              </th>
                                             <th  class="sorting" orderField="real_name">
                                                  用户真名
                                              </th>
                                             <th  class="sorting" orderField="score_amount">
                                                  积分余额
                                              </th>
                                             <th  class="sorting" orderField="money_amount">
                                                  现金余额
                                              </th>
                                             <th  class="sorting" orderField="regist_time">
                                                  注册时间
                                              </th>
                                             <th  class="sorting" orderField="last_login_time">
                                                  最后登录时间
                                              </th>
                                             <th  class="sorting" orderField="account_status">
                                                  账号状态
                                              </th>
                                             <th  class="sorting" orderField="sex">
                                                  性别
                                              </th>
                                             <th  class="sorting" orderField="birthday">
                                                  生日
                                              </th>
                                             <th  class="sorting" orderField="baby_sex">
                                                  大宝宝性别
                                              </th>
                                             <th  class="sorting" orderField="baby_birthday">
                                                  大宝宝生日
                                              </th>
                                             <th  class="sorting" orderField="baby_two_sex">
                                                  二宝宝性别
                                              </th>
                                             <th  class="sorting" orderField="baby_two_birthday">
                                                  二宝宝生日
                                              </th>
                                             <th  class="sorting" orderField="baby_three_sex">
                                                  三宝宝性别
                                              </th>
                                             <th  class="sorting" orderField="baby_three_birthday">
                                                  三宝宝生日
                                              </th>
                                             <th  class="sorting" orderField="account_level">
                                                  账号等级
                                              </th>
                                              <th>操作</th>
                                          </tr>
                                </thead>
                                <tbody id="tbody"></tbody>
                                <tfoot></tfoot>
                            </table>
                            <div class="row">
                                <div class="col-xs-6">
                                    <div class="dataTables_info"></div>
                                </div>
                                <div class="col-xs-6">
                                    <div class="dataTables_paginate paging_bootstrap">
                                        <ul class="pagination" id="pagination">
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- /.box-body -->
                    </div>
                    <!-- /.box -->
                </div>
            </div>
        </section>
        <!-- /.content -->
    </aside>
    <!-- /.right-side -->
</div>
<!-- ./wrapper -->
<%@include file="../include/tail.jsp"%>
<script language="javascript" type="text/javascript"
        src="${CTX}assets/admin/js/My97DatePicker/WdatePicker.js"></script>
<script src="assets/admin/app/list_page.js" type="text/javascript"></script>
<script src="assets/admin/app/userweb/userWebPaging.js" type="text/javascript"></script>
</body>
</html>
