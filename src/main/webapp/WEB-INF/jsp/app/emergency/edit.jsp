<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@include file="/common/common.jspf" %>
    <script type="text/javascript" src="${ctx}/js/app/emergency/edit.js"></script>

    <style type="text/css">
        .col-sm-3 {
            width: 15%;
            float: left;
        }

        .col-sm-9 {
            width: 85%;
            float: left;
        }
    </style>
</head>
<body>
<div class="l_err" style="width: 100%; margin-top: 2px;"></div>
<form id="form" name="form" class="form-horizontal" method="post"
      action="${ctx}/emergency/save.shtml">
    <input type="hidden" class="form-control"
           value="${emergency.id}" name="emergencyFormMap.id" id="id">
    <section class="panel panel-default">
        <div class="panel-body">
            <div class="form-group">
                <label class="col-sm-3 control-label">名称</label>

                <div class="col-sm-9">
                    <input type="text" class="form-control"
                           placeholder="请输入事件名称" value="${emergency.title}"
                           name="emergencyFormMap.title" id="title">
                </div>
            </div>

            <div class="line line-dashed line-lg pull-in"></div>
            <div class="form-group">
                <label class="col-sm-3 control-label">事发地点</label>

                <div class="col-sm-9">
                    <input type="text" class="form-control"
                           placeholder="请输入事发地点" value="${emergency.location}"
                           name="emergencyFormMap.location" id="location">
                </div>
            </div>

            <div class="line line-dashed line-lg pull-in"></div>
            <div class="form-group">
                <label class="col-sm-3 control-label">发生时间</label>

                <div class="col-sm-9">
                    <div class="input-group date form_date" data-date="" data-date-format="yyyy-MM-dd"
                         data-link-field="happenTime" data-link-format="yyyy-mm-dd">
                        <fmt:parseDate value="${emergency.happenTime}" var="happenTime"/>
                        <input class="form-control" size="16" type="text" value="<fmt:formatDate value="${happenTime}" pattern="yyyy-MM-dd"/>" readonly>
                        <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                        <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                    </div>
                    <input type="hidden" id="time_founded" name="emergencyFormMap.happenTime" value="<c:out value="${emergency.happenTime}"/>"/><br/>
                </div>
            </div>

            <div class="line line-dashed line-lg pull-in"></div>
            <div class="form-group">
                <label class="col-sm-3 control-label">相关部门</label>

                <div class="col-sm-9">
                    <input type="text" class="form-control"
                           placeholder="请输入部门" value="${emergency.department}"
                           name="emergencyFormMap.department" id="department">
                </div>
            </div>

            <div class="line line-dashed line-lg pull-in"></div>
            <div class="form-group">
                <label class="col-sm-3 control-label">负责人</label>

                <div class="col-sm-9">
                    <input type="text" class="form-control"
                           placeholder="请输入负责人" value="${emergency.official}"
                           name="emergencyFormMap.country" id="official">
                </div>
            </div>

            <div class="line line-dashed line-lg pull-in"></div>
            <div class="form-group">
                <label class="col-sm-3 control-label">事件经过</label>

                <div class="col-sm-9">
                    <input type="text" class="form-control"
                           placeholder="请输入事件经过" value="${emergency.experience}"
                           name="emergencyFormMap.experience" id="experience">
                </div>
            </div>

            <div class="line line-dashed line-lg pull-in"></div>
            <div class="form-group">
                <label class="col-sm-3 control-label">处置过程</label>

                <div class="col-sm-9">
                    <input type="text" class="form-control"
                           placeholder="请输入处置过程" value="${emergency.handle}"
                           name="emergencyFormMap.handle" id="handle">
                </div>
            </div>

            <div class="line line-dashed line-lg pull-in"></div>
            <div class="form-group">
                <label class="col-sm-3 control-label">周围环境</label>

                <div class="col-sm-9">
                    <input type="text" class="form-control"
                           placeholder="请输入周围环境情况" value="${emergency.environment}"
                           name="emergencyFormMap.environment" id="environment">
                </div>
            </div>

            <div class="line line-dashed line-lg pull-in"></div>
            <div class="form-group">
                <label class="col-sm-3 control-label">备注</label>

                <div class="col-sm-9">
                    <input type="text" class="form-control"
                           placeholder="请输入备注信息" value="${emergency.remarks}"
                           name="emergencyFormMap.remarks" id="remarks">
                </div>
            </div>
        </div>
        <footer class="panel-footer text-right bg-light lter">
            <button type="submit" class="btn btn-success btn-s-xs">保存</button>
        </footer>
    </section>
</form>
<script type="text/javascript">
    onloadurl();
    $('.form_date').datetimepicker({
        language: 'zh-CN',
        weekStart: 1,
        todayBtn: 1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: 2,
        forceParse: 0
    });
</script>
</body>
</html>