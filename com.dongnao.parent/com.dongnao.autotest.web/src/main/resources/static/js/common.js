var DateTime = {
	min: "0001-01-01 00:00:00",
	max: "9999-12-31 23:59:59",
	sqlServer: {
		min: "1753-01-01 00:00:00",
		min_N: "/Date(-6847833600000)/",
	    min_T: "1753-01-01T00:00:00"
	},
	mysql: {
		unixTime_min: "1970-01-01 08:00:00"
	}
}

Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1,                 // 月份
        "d+": this.getDate(),                    // 日
        "H+": this.getHours(),                   // 小时
        "m+": this.getMinutes(),                 // 分
        "s+": this.getSeconds(),                 // 秒
        "q+": Math.floor((this.getMonth() + 3) / 3), // 季度
        "S": this.getMilliseconds()             // 毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

/**
 * ******************************************** datagrid *******************************************
 */
function onBeforeLoad(param, pageSize) {
	pageSize = isNaN(pageSize) ? 20 : pageSize;
	param.pageIndex = isNaN(param.page) ? 1 : param.page;
	param.pageSize = isNaN(param.rows) ? pageSize : param.rows;

	// 处理排序
	var sorts = [];
	if (!param.sort || !param.order)
		return;
	var propertys = param.sort.split(",");
	var orders = param.order.split(",");
	for (var i = 0; i < propertys.length; i++) {
		if (orders[i].toLowerCase() == "asc")
			sorts.push("+" + propertys[i]);
		else
			sorts.push("-" + propertys[i]);
	}
	param.sorts = sorts.join(",");
}
function formatDateTime(val, row) {
	if (val == undefined || val == DateTime.sqlServer.min || val == DateTime.sqlServer.min_N || val == DateTime.mysql.unixTime_min) {
		return "";
	} else {
		var valInt = val;
		if (isNaN(val)) {
			if (val.toString().length == 22)
				valInt = parseInt(val.substr(6, 13));
			else
				return val;
		}
		var date = new Date(valInt);
		return date.format("yyyy-MM-dd HH:mm:ss");
	}
}
function formatCheckbox(val, row) {
	if (val) {
		return "<input type='checkbox' checked disabled />";
	} else {
		return "<input type='checkbox' disabled />";
	}
}

/**
 * ********************* datagrid tool按钮 ****************************************
 */
function btnHelp_Click(sender, e) {
	window.open('tencent://Message/?websiteName=qzone.qq.com&Menu=yes&Uin=84579883', '');
}

function btnRefresh_Click(sender, dataGrid) {
	dataGrid.datagrid("reload");
}

/**
 * ********************** 提示框 ***************************************
 */
function showLoading() {
	// / <summary>显示加载条</summary>
	$.messager.progress();
}

function hideLoading() {
	// / <summary>关闭加载条</summary>
	$.messager.progress('close');
}

function showInfo(message) {
	// / <summary>显示成功提示信息</summary>
	showNewMessage(message, "alert");
}

function showSuccess(message) {
	// / <summary>显示成功提示信息</summary>
	showNewMessage(message, "alert-success");
}

function showWarning(message) {
	// / <summary>显示成功提示信息</summary>
	showNewMessage(message, "alert-warning");
}

function showError(message) {
	// / <summary>显示成功提示信息</summary>
	showNewMessage(message, "alert-error");
}

function showNewMessage(message, alertCls) {
	// / <summary>显示提示信息</summary>
	var mesId = "mes_" + Math.floor(Math.random() * 100000000 + 1);
	var html = "<div class=\"alert-close\" onclick=\"$('#" + mesId
			+ "').panel('close');\">&times;</div>"
			+ "<div class=\"alert-icons\"></div>" + message;
	$.messager.show({
		id: mesId,
		msg: html,
		width: 800,
		height: 40,
		showType: 'slide',
		bodyCls: alertCls,
		style: {
			right: '',
			top: document.body.scrollTop + document.documentElement.scrollTop,
			bottom: ''
		}
	});
}

function showMessage(message, title) {
	// / <summary>显示提示信息</summary>
	if (!title)
		title = "温馨提示";
	$.messager.show({
		title: title,
		msg: message,
		timeout: 5000,
		showType: 'fade'
	});
}

function dialogMessage(message, title) {
	// / <summary>显示提示框</summary>
	// / <param>提示信息</param>
	if (!title)
		title = "温馨提示";
	$.messager.alert(title, message, "info");
}

function dialogWarning(message, title) {
	// / <summary>显示警告提示框</summary>
	// / <param>提示信息</param>
	if (!title)
		title = "温馨提示";
	$.messager.alert(title, message, "warning");
}

function dialogError(message, title) {
	// / <summary>显示错误提示框</summary>
	// / <param>提示信息</param>
	if (!title)
		title = "温馨提示";
	$.messager.alert(title, message, "error");
}