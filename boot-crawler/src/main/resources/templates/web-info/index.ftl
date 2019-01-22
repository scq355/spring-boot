<#import "../common/common-css.ftl" as css/>
<#import "../common/common-js.ftl" as js/>
<!DOCTYPE html>
<html lang="en">
<head>
<@css.description/>
<@css.ace/>
<@css.datePicker/>
</head>
<body>
<#include "../include/top-menu.ftl"/>
<div class="main-container" id="main-container">
    <script type="text/javascript">
        try {
            ace.settings.check('main-container', 'fixed')
        } catch (e) {}
    </script>

    <div class="main-container-inner">
        <a class="menu-toggler" id="menu-toggler" href="#">
            <span class="menu-text"></span>
        </a>

        <div class="sidebar" id="sidebar">
        <#include "../include/left-menu.ftl"/>
        </div>
        <div class="main-content">
            <div class="breadcrumbs" id="breadcrumbs">
                <script type="text/javascript">
                    try {
                        ace.settings.check('breadcrumbs', 'fixed')
                    } catch (e) {}
                </script>
                <ul class="breadcrumb">
                    <li>
                        <i class="icon-home home-icon"></i>
                        <a href="">主页</a>
                    </li>
                    <li class="active">网址配置</li>
                </ul>
            </div>

            <div class="page-content">
                <div class="page-content">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="row">
                                <div class="col-xs-12">
                                    <div class="row">
                                        <div class="col-xs-12">
                                            <h3 class="header smaller lighter blue">网址配置</h3>
                                            <div class="table-header"></div>
                                            <div class="row">
                                                <form id="myform" action="" method="post">
                                                    <input type="hidden" name="pageNumber" value="${pageNumber!}">
                                                </form>
                                            </div>

                                            <div class="space-6"></div>
                                            <div class="table-responsive">
                                                <div class="alert alert-block alert-success visible-md visible-lg hidden-sm hidden-xs action-buttons">
                                                    <a href="javascript:add();" style="text-align: right!important;">+添加配置</a>
                                                </div>
                                                <table id="sample-table-2" class="table table-striped table-bordered table-hover">
                                                    <tr>
                                                        <th>名称</th>
                                                        <th>URL</th>
                                                        <th>显示状态</th>
                                                        <th>匹配关键字</th>
                                                        <th>抓取进度</th>
                                                        <th>信息类型</th>
                                                        <th>更新时间</th>
                                                        <th>操作</th>
                                                    </tr>
                                                    <#list webInfoList as webInfo>
                                                    <tr>
                                                        <td>${webInfo.name!}</td>
                                                        <td>${webInfo.id!}</td>
                                                        <td><#if webInfo.isShow == 1>显示<#else>隐藏</#if></td>
                                                        <td>${webInfo.params!}</td>
                                                        <td>
                                                            <#if webInfo.processStatus??>
                                                                <#if webInfo.processStatus == 0><span class="label">初始可修改</span>
                                                                <#elseif webInfo.processStatus == 1><span class="label label-success">修改冻结</span>
                                                                <#elseif webInfo.processStatus == 2><span class="label label-warning">分页配置完成</span>
                                                                <#elseif webInfo.processStatus == 3><span class="label label-danger">登录配置完成</span>
                                                                <#elseif webInfo.processStatus == 4><span class="label label-info">可爬取状态-爬取等待</span>
                                                                <#elseif webInfo.processStatus == 5><span class="label label-purple">爬取中...</span>
                                                                <#elseif webInfo.processStatus == 6><span class="label label-success">爬取成功-结束</span>
                                                                <#elseif webInfo.processStatus == 7><span class="label label-grey">爬取失败-结束</span>
                                                                </#if>
                                                            </#if>
                                                        </td>
                                                        <td>
                                                            <#if webInfo.infoType == 1>
                                                                舆情监控
                                                            <#elseif webInfo.infoType == 2>
                                                                债权转让
                                                            </#if>
                                                        </td>
                                                        <td>${webInfo.updateAt?string("yyyy-MM-dd HH:mm:ss")!}</td>
                                                        <td>
                                                            <div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
                                                                <button class="btn btn-xs btn-purple" onclick="lock('${webInfo.id}')">
                                                                    <i class="icon-lock align-top bigger-110"></i>
                                                                    <#if webInfo.processStatus == 0>修改锁定<#else>已锁定</#if>
                                                                </button>
                                                                <#if webInfo.processStatus == 0>
                                                                    <button class="btn btn-xs btn-success" onclick="edit('${webInfo.id}', '${webInfo.name}', '${webInfo.url}', '${webInfo.params!}')">
                                                                        <i class="icon-edit align-top bigger-110"></i>
                                                                        编辑
                                                                    </button>
                                                                </#if>
                                                                <button class="btn btn-xs btn-grey" onclick="reset('${webInfo.id}')">
                                                                    <i class="icon-ban-circle align-top bigger-110"></i>
                                                                    隐藏
                                                                </button>
                                                                <#if webInfo.processStatus == 0 || webInfo.processStatus == 1>
                                                                    <button class="btn btn-xs btn-danger" onclick="del('${webInfo.id}')">
                                                                        <i class="icon-trash align-top bigger-110"></i>
                                                                        删除
                                                                    </button>
                                                                </#if>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    </#list>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    <#include "../include/right-menu.ftl"/>
    </div>
    <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
        <i class="icon-double-angle-up icon-only bigger-110"></i>
    </a>
</div>
<@js.ace/>
<@js.dataTables/>
<@js.datePicker/>
<script src="/static/js/ace/bootbox.min.js"></script>
<script>
function add(){
    bootbox.dialog({
        title:"网页信息编辑：",
        message:'<form id="addWebInfo" method="post" class="form-horizontal">' +
        '<div class="form-group">' +
        '	<label class="col-sm-2 control-label">网站名称</label>' +
        '	<div class="col-sm-10">' +
        '		<input type="text" name="webName" class="form-control" placeholder="网站名称">' +
        '	</div>' +
        '</div>' +
        '<div class="form-group">' +
        '	<label class="col-sm-2 control-label">网站地址</label>' +
        '	<div class="col-sm-10">' +
        '		<input type="text" name="webUrl" class="form-control" placeholder="http://www.***.com">' +
        '	</div>' +
        '</div>' +
        '<div class="form-group">' +
        '	<label class="col-sm-2 control-label">关键字</label>' +
        '	<div class="col-sm-10">' +
        '		<input type="text" name="params" class="form-control" placeholder="不同的关键字请用‘,’隔开">' +
        '	</div>' +
        '</div>' +
        '</form>',
        locale:"zh_CN",
        onEscape:true,
        buttons:{
            OK: {
                label: "保存",
                className: "btn-primary",
                callback: function () {
                    var webName = $.trim($("#addWebInfo input[name=webName]").val());
                    var webUrl = $.trim($("#addWebInfo input[name=webUrl]").val());
                    var params = $.trim($("#addWebInfo input[name=params]").val());
                    $.ajax({
                        url:"/webInfo/save",
                        type:"POST",
                        data:{"webName":webName,"webUrl":webUrl,"params":params},
                        success:function(data){
                            if(data.code == 1){
                                location.reload();
                                return false;
                            }
                            alert("添加失败：" + data.msg);
                        }
                    });
                    return false;
                }
            }
        }
    });
}

function edit(id, webName, webUrl, params){
    bootbox.dialog({
        title:"网页信息编辑：",
        message:'<form id="addWebInfo" method="post" class="form-horizontal">' +
        '<div class="form-group">' +
        '	<label class="col-sm-2 control-label">网站名称</label>' +
        '	<div class="col-sm-10">' +
        '		<input type="text" name="webName" class="form-control" placeholder="网站名称" value="'+ webName +'">' +
        '	</div>' +
        '</div>' +
        '<div class="form-group">' +
        '	<label class="col-sm-2 control-label">网站地址</label>' +
        '	<div class="col-sm-10">' +
        '		<input type="text" name="webUrl" class="form-control" placeholder="http://www.***.com" value="'+ webUrl +'" readonly="true">' +
        '	</div>' +
        '</div>' +
        '<div class="form-group">' +
        '	<label class="col-sm-2 control-label">关键字</label>' +
        '	<div class="col-sm-10">' +
        '		<input type="text" name="params" class="form-control" placeholder="不同的关键字请用‘，’隔开" value="'+ params +'">' +
        '	</div>' +
        '</div>' +
        '</form>',
        locale:"zh_CN",
        onEscape:true,
        buttons:{
            OK: {
                label: "更新",
                className: "btn-primary",
                callback: function () {
                    var webName = $.trim($("#addWebInfo input[name=webName]").val());
                    var params = $.trim($("#addWebInfo input[name=params]").val());
                    $.ajax({
                        url:"/webInfo/update",
                        type:"POST",
                        data:{
                            "id": id,
                            "webName":webName,
                            "params":params
                        },
                        success:function(data){
                            if(data.code == 1){
                                location.reload();
                                return false;
                            }
                            alert("修改失败：" + data.msg);
                        }
                    });
                    return false;
                }
            }
        }
    });
}

function reset(id){
    bootbox.confirm("你确定要“删除”么?", function(result) {
        if(result) {
            $.ajax({
                url:"/webInfo/reset",
                type:"POST",
                data:{"isShow":0,"webInfoId":id},
                success:function(data){
                    if (data.code == 1) {
                        bootbox.alert("“删除”成功");
                        location.reload();
                    } else {
                        alert("“删除”失败:" + data.msg);
                    }
                }
            });
        }
    });
}

function lock(id){
    bootbox.confirm("锁定之后不可再返回修改，您确定要锁定么?", function(result) {
        if(result) {
            $.ajax({
                url:"/webInfo/lockStatus",
                type:"POST",
                data:{"id":id},
                success:function(data){
                    if (data.code == 1) {
                        bootbox.alert("锁定成功");
                        location.reload();
                    } else {
                        alert("锁定失败:" + data.msg);
                    }
                }
            });
        }
    });
}

function del(id){
    bootbox.confirm("你确定要删除么?", function(result) {
        if(result) {
            $.ajax({
                url:"/webInfo/delete",
                type:"POST",
                data:{"isShow":0,"webInfoId":id},
                success:function(data){
                    if (data.code == 1) {
                        bootbox.alert("删除成功");
                        location.reload();
                    } else {
                        alert("删除失败:" + data.msg);
                    }
                }
            });
        }
    });
}
</script>
</body>
</html>
