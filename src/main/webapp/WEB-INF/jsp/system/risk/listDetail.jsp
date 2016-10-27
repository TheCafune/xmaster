<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/common/common.jspf"%>

<%@include file="/common/common.jspf"%>
<script src="${ctx}/notebook/notebook_files/echarts.min.js"></script>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/system/risk/listDetail.js"></script>
<div class="m-b-md">
	<input id="groupId" name="groupId" value = "${groupId}" type = "hidden">
	<input id="standard_num" name="standard_num" value = "${standard_num}" type = "hidden">
	<input id="unstandard_num" name="unstandard_num" value = "${unstandard_num}" type = "hidden">
	<form class="form-inline" role="form" id="searchForm" name="searchForm">
		<div class="form-group">
			<label class="control-label"> <span
				class="h4 font-thin v-middle">名称:</span></label> <input
				class="input-medium ui-autocomplete-input" id="name"
				name="riskFormMap.name">
		</div>
		<a href="javascript:void(0)" class="btn btn-default" id="search">查询</a>
		<%--<a href="javascript:void(0)" class="btn btn-warning" id="callback_test">测试表格回调函数</a>--%>
		<a href="javascript:grid.exportData('/user/export.shtml')"
			class="btn btn-info" id="search">导出excel</a>
	</form>
</div>
<header class="panel-heading">
	<div class="doc-buttons">
		<c:forEach items="${res}" var="key">
            ${key.description}
        </c:forEach>
	</div>
</header>
<div class="table-responsive">
	<div id="paging" class="pagclass"></div>
</div>

<div id="callback_div" class="table-responsive" style="display: none;">
	<div id="paging_callback" class="pagclass"></div>
</div>

<div id="chart" style="width: 600px;height:400px;margin:auto;"></div>
