$(function () {
    init();
    // 定位表格查询框
    $(".search").css({
        'position': 'fixed',
        'right': '10px',
        'top': '0',
        'z-index': '1000',
        'width': '240px'
    });
    $(".search input").attr("placeholder", "搜索");
    $(".search input").css({
        'padding-right': '23px'
    });
    initBtnEvent();
});

function initBtnEvent() {
    // 新增
    $("#btn_add").bind('click', function () {
        // 先清空
        $("#coupon_id").val("");
        $("#cp_name").val("");
        $("#cp_amount").val("");
        $('#myModal1').modal({
            backdrop: 'static',
            keyboard: false
        });
    });
    // 删除
    $("#btn_del").bind('click', function () {
        var selections = getIdSelections();
        layer.confirm("您确定要删除所选信息吗?", {
            skin: 'layui-layer-molv',
            btn: ['确定', '取消']
        }, function () {
            var url = getRootPath() + "adminCoupon/deleteByIds";
            $.post(url, {
				ids: selections + ""
            }, function (data) {
                if (data.status == "1") {
                    layer.msg(data.message, {
                        time: 2000
                    }, function () {
                        $('#roleInfo').bootstrapTable('refresh');
                    });
                } else {
                    layer.msg(data.message, {
                        time: 2000
                    });
                }
            }, "json");
        }, function () {
            return;
        });
    });
}

function init() {
    // 初始化Table
    oTable = new TableInit();
    oTable.Init();
};

var TableInit = function () {
    var oTableInit = new Object();
    oTableInit.Init = function () {
        $('#roleInfo').bootstrapTable({
            url: getRootPath() + "adminCoupon/queryByPage", // 请求后台的URL（*）
            toolbar: '#toolbar', // 工具按钮用哪个容器
            striped: true,
            search: true,
            searchOnEnterKey: true,
            showColumns: true, // 是否显示所有的列
            showRefresh: true, // 是否显示刷新按钮
            showToggle: true, // 是否显示详细视图和列表视图的切换按钮
            sortable: false,
            sortOrder: "asc",
            sidePagination: "server", // 分页方式：client客户端分页，server服务端分页（*）
            cache: false,
            clickToSelect: false,
            queryParams: oTableInit.queryParams,
            showExport: "true",
            minimumCountColumns: 2, // 最少允许的列数
            buttonsAlign: "left",
            buttonsClass: 'white',
            showPaginationSwitch: false,
            pagination: true,
            pageNumber: 1, // 初始化加载第一页，默认第一页
            pageSize: 10, // 每页的记录行数（*）
            pageList: '[10, 25, 50, 100,ALL]', // 可供选择的每页的行数（*）
            columns: [{
                field: 'state',
                checkbox: true,
            }, {
                field: 'id',
                visible: false,
                title: 'id',
            }, {
                field: 'cp_name',
                title: '优惠券名称',
            }, {
                align: 'center',
                field: 'cp_amount',
                title: '优惠券金额',
                formatter: function (value) {
                    var html = "";
                    if (value <= 50) {
                        html = "<span class='badge bg-green'  style='padding:5px 10px;align:center;width:50px;'>"
                            + value + "</span>";
                    } else {
                        html = "<span class='badge bg-orange'  style='padding:5px 10px;align:center;width:50px;'>"
                            + value + "</span>";
                    }
                    return html;
                }
            }, {
                field: 'creator_name',
                title: '创建人',
            }, {
                align: 'center',
                field: 'cp_start_time',
                title: '开始时间',
                formatter: function (value) {
                    var html = json2TimeStamp(value);
                    return html;
                }
            }, {
                align: 'center',
                field: 'cp_end_time',
                title: '结束时间',
                formatter: function (value) {
                    var html = json2TimeStamp(value);
                    return html;
                }
            }, {
                align: 'center',
                field: 'cp_publish_status',
                title: '发布状态',
                formatter: function (value) {
                    var html = "";
                    if ("已发布" == value) {
                        html = "<span class='badge bg-green'  style='padding:5px 10px;align:center;width:50px;'>"
                            + value + "</span>";
                    } else {
                        html = "<span class='badge bg-orange'  style='padding:5px 10px;align:center;width:50px;'>"
                            + value + "</span>";
                    }
                    return html;
                }
            }, {
                field: 'cp_update_time',
                title: '发布时间',
                formatter: function (value) {
                    var html = json2TimeStamp(value);
                    return html;
                }
            }, {
                field: 'id',
                title: '操作',
                align: 'center',
                events: operateEvents,
                formatter: operateFormatter
            }],
            onCheck: function (row, e) {
                tableCheckEvents();
            },
            onUncheck: function (row, e) {
                tableCheckEvents();
            },
            onCheckAll: function (rows) {
                $("#btn_del").attr("disabled", false);
            },
            onUncheckAll: function (rows) {
                $("#btn_del").attr("disabled", true);
            },
            onLoadSuccess: function (rows) {
                $("#btn_del").attr("disabled", true);
            }
        });
    };

    function operateFormatter(value, row, index) {
        var html = '<a id="a_check">查看 <span style="color:#CCC">|</span> </a>'
            + '<a id="a_edit">修改 <span style="color:#CCC">|</span> </a>'
            + '<a id="a_publish">发布<span style="color:#CCC">|</span> </a>'
            + '<a id="a_repeal">撤销</a>';
        return html;
    }

    // 操作列的事件
    window.operateEvents = {
        'click #a_check': function (e, value, row, index) {
            editOrCheck(row, 1);
        },
        'click #a_edit': function (e, value, row, index) {
            editOrCheck(row, 2);
        },
        'click #a_publish': function (e, value, row, index) {
            publish(row, 3);
        },
        'click #a_repeal': function (e, value, row, index) {
            repeal(row, 4);
        }
    };

    oTableInit.queryParams = function (params) {
        var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            limit: params.limit, // 页面大小
            offset: params.offset, // 页码
            search: params.search
            // 表格搜索框的值
        };
        return temp;
    };

    return oTableInit;

};

//毫秒转时间YYYY/MM/DD hh:mm
function json2LocalTimeStamp(milliseconds) {
    if (milliseconds == 0 || milliseconds === "") {
        return "";
    }
    var datetime = new Date();
    datetime.setTime(milliseconds);
    var year = datetime.getFullYear();
    //月份重0开始，所以要加1，当小于10月时，为了显示2位的月份，所以补0
    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
    var hour = datetime.getHours() < 10 ? "0" + datetime.getHours() : datetime.getHours();
    var minute = datetime.getMinutes() < 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
    return year + "-" + month + "-" + date + " " + hour + ":" + minute;
}

// 查看和编辑
function editOrCheck(obj, type) {
    console.log(JSON.stringify(obj));
    if (type == 1) {
        $('#btEmpAdd').hide();
        $("#myModalTitle").html("查看");
        $("#cp_name").attr("disabled", true);
        $("#cp_amount").attr("disabled", true);
        $("#cp_start_time").attr("disabled", true);
        $("#cp_end_time").attr("disabled", true);
    } else {
        $('#btEmpAdd').show();
        $("#myModalTitle").html("编辑");
        $("#cp_name").attr("disabled", false);
        $("#cp_amount").attr("disabled", false);
        $("#cp_start_time").attr("disabled", false);
        $("#cp_end_time").attr("disabled", false);
    }

    var cp_start_time = json2LocalTimeStamp(obj.cp_start_time);
    var cp_end_time = json2LocalTimeStamp(obj.cp_end_time);

    console.log(cp_start_time);
    console.log(cp_end_time);

    $("#coupon_id").val(obj.id);
    $("#cp_name").val(obj.cp_name);
    $("#cp_amount").val(obj.cp_amount);
    $("#cp_start_time").val(cp_start_time);
    $("#cp_end_time").val(cp_end_time);

    $('#myModal1').modal({
        backdrop: 'static',
        keyboard: false
    });
}

/**
 * 发布信息接口
 * @param obj
 * @param type
 */
function publish(obj, type) {
    var isPublish = obj.cp_is_pubish;
    if (isPublish == 1) {
        layer.msg("已发布，不能再发布了", {
            time: 2000
        });
        return;
    }
    var para = {
        id: obj.id
    };
    layer.confirm("您确定要发布该优惠券吗?", {
        skin: 'layui-layer-molv',
        btn: ['确定', '取消']
    }, function () {
        var url = getRootPath() + "adminCoupon/publish";
        $.post(url, para, function (data) {
            if (data.status == "1") {
                layer.msg(data.message, {
                    time: 2000
                }, function () {
                    $('#roleInfo').bootstrapTable('refresh');
                });
            } else {
                layer.msg(data.message, {
                    time: 2000
                });
            }
        }, "json");
    }, function () {
        return;
    });
}

/**
 * 发布信息接口
 * @param obj
 * @param type
 */
function repeal(obj, type) {
    var isPublish = obj.cp_is_pubish;
    if (isPublish == 2) {
        layer.msg("已发布，不能再发布了", {
            time: 2000
        });
        return;
    }
    var para = {
        id: obj.id
    };
    layer.confirm("您确定要撤销该优惠券吗?", {
        skin: 'layui-layer-molv',
        btn: ['确定', '取消']
    }, function () {
        var url = getRootPath() + "adminCoupon/repeal";
        $.post(url, para, function (data) {
            if (data.status == "1") {
                layer.msg(data.message, {
                    time: 2000
                }, function () {
                    $('#roleInfo').bootstrapTable('refresh');
                });
            } else {
                layer.msg(data.message, {
                    time: 2000
                });
            }
        }, "json");
    }, function () {
        return;
    });
}


// 表格选择事件
function tableCheckEvents() {
    var r = $('#roleInfo').bootstrapTable('getSelections');
    if (r.length == 0) {
        $("#btn_del").attr("disabled", true);
    }
    if (r.length == 1) {
        $("#btn_del").attr("disabled", false);
    }
}

/**
 * 选择框
 *
 * @returns
 */
function getIdSelections() {
    return $.map($('#roleInfo').bootstrapTable('getSelections'),
        function (row) {
            return row.id;
        });
}

/**
 * 保存操作
 */
function openSaveButton() {
    var cp_name = $("#cp_name").val();

    if (null == cp_name || "" == cp_name) {
        layer.msg("请输入优惠券名称", {
            time: 2000
        });
        return;
    }

    var cp_amount = $("#cp_amount").val();

    if (null == cp_amount || "" == cp_amount) {
        layer.msg("请输入优惠券金额", {
            time: 2000
        });
        return;
    }

    var cp_start_time = $("#cp_start_time").val();

    if (null == cp_start_time || "" == cp_start_time) {
        layer.msg("请输入优惠券的开始时间", {
            time: 2000
        });
        return;
    }

    var cp_end_time = $("#cp_end_time").val();

    if (null == cp_end_time || "" == cp_end_time) {
        layer.msg("请输入优惠券的结束时间", {
            time: 2000
        });
        return;
    }

    var startDate1 = new Date(cp_start_time);
    var endDate2 = new Date(cp_end_time);
    if (startDate1.getTime() > endDate2.getTime()) {//转换成毫秒进行比较
        layer.msg("结束时间不能小于开始时间", {
            time: 2000
        });
        return;
    }

    var addJson = {
        id: $("#coupon_id").val(),
        creator_id: $("#user_id").val(),
        creator_name: $("#user_id").attr("name"),
        cp_name: cp_name,
        cp_amount: cp_amount,
        cp_start_time: startDate1.getTime(),
        cp_end_time: endDate2.getTime()
    };

    $.ajax({
        type: "post",
        url: getRootPath() + "adminCoupon/saveOrUpdate",
        data: addJson,
        dataType: "json",//返回的数据格式为json
        async: true,
        success: function (data) {
            if (data.status == "1") {
                layer.msg(data.message, {
                    time: 2000
                }, function () {
                    $('#roleInfo').bootstrapTable('refresh');
                    $('#myModal1').modal('hide');
                });
            } else {
                layer.msg(data.message, {
                    time: 2000
                });
            }
        }
    });
}