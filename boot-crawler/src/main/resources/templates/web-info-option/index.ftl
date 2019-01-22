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
                    <li class="active">分页登录配置</li>
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
                                            <h3 class="header smaller lighter blue">分页登录配置</h3>
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
                                                        <th>请求类型</th>
                                                        <th>标题内容在同一页面</th>
                                                        <th>分页方式</th>
                                                        <th>是否需要登录</th>
                                                        <th>操作</th>
                                                    </tr>
                                                    <#list webInfoOptionList as infoOption>
                                                    <tr>
                                                        <td>${infoOption.name!}</td>
                                                        <td>${infoOption.id!}</td>
                                                        <td>
                                                            <#if infoOption.requestType??>
                                                            <#if infoOption.requestType == 1>POST
                                                            <#elseif infoOption.requestType == 0>GET
                                                            </#if>
                                                            <#else>未设置
                                                            </#if>
                                                        </td>
                                                        <td>
                                                            <#if infoOption.contentLocationType??>
                                                            <#if infoOption.contentLocationType == 1>不在同一页面
                                                            <#elseif infoOption.contentLocationType == 0>在同一页面
                                                            </#if>
                                                            <#else>未设置
                                                            </#if>
                                                        </td>
                                                        <td>
                                                            <#if infoOption.pagingType??>
                                                            <#if infoOption.pagingType == 1>全页面加载分页
                                                            <#elseif infoOption.pagingType == 0>局部加载分页
                                                            </#if>
                                                            <#else>未设置
                                                            </#if>
                                                        </td>
                                                        <td>
                                                            <#if infoOption.loginRequired??>
                                                            <#if infoOption.loginRequired == 1>需要登录
                                                            <#elseif infoOption.loginRequired == 0>无需登录
                                                            </#if>
                                                            <#else>未设置
                                                            </#if>
                                                        </td>
                                                        <td>
                                                            <div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
                                                                <button class="btn btn-xs btn-danger" onclick="del('${infoOption.id}')">
                                                                    <i class="icon-trash align-top bigger-110"></i>
                                                                    删除
                                                                </button>
                                                                <button class="btn btn-xs btn-success" onclick="edit(
                                                                        '${infoOption.name!}',
                                                                        '${infoOption.id!}',
                                                                        '${infoOption.requestType!}',
                                                                        '${infoOption.contentLocationType!}',
                                                                        '${infoOption.pagingType!}',
                                                                        '${infoOption.loginRequired!}'
                                                                        )">
                                                                    <i class="icon-edit align-top bigger-110"></i>
                                                                    编辑
                                                                </button>
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
        title:"登录分页配置添加：",
        message:'<form id="addWebInfoOption" method="post" class="form-horizontal">' +
        '<div class="form-group">' +
        '	<label class="col-sm-2 control-label">ID</label>' +
        '	<div class="col-sm-10">' +
        '		<input type="text" name="id" class="form-control" placeholder="URL">' +
        '	</div>' +
        '</div>' +
        '<div class="form-group">' +
        '	<label class="col-sm-2 control-label">网站名称</label>' +
        '	<div class="col-sm-10">' +
        '		<input type="text" name="webName" class="form-control" placeholder="网站名称">' +
        '	</div>' +
        '</div>' +

        '<div class="form-group">' +
        '	<label class="col-sm-2 control-label">请求类型</label>' +
        '	<div class="col-sm-10">' +
        '  <label class="inline">' +
        '    <input name="requestType" type="radio" class="ace" value="0">' +
        '    <span class="lbl"> GET请求</span>' +
        '  </label>' +
        '&nbsp; &nbsp;' +
        '  <label class="inline">' +
        '    <input name="requestType" type="radio" class="ace" value="1">' +
        '    <span class="lbl"> POST请求</span>' +
        '  </label>' +
        '	</div>' +
        '</div>' +

        '<div class="form-group">' +
        '	<label class="col-sm-2 control-label">标题内容在同一页面</label>' +
        '	<div class="col-sm-10">' +
        '  <label class="inline">' +
        '    <input name="contentLocationType" type="radio" class="ace" value="0">' +
        '    <span class="lbl"> 在同一页面</span>' +
        '  </label>' +
        '&nbsp; &nbsp;' +
        '  <label class="inline">' +
        '    <input name="contentLocationType" type="radio" class="ace" value="1">' +
        '    <span class="lbl"> 不在同一页面</span>' +
        '  </label>' +
        '	</div>' +
        '</div>' +

        '<div class="form-group">' +
        '	<label class="col-sm-2 control-label">分页类型</label>' +
        '	<div class="col-sm-10">' +
        '  <label class="inline">' +
        '    <input name="pagingType" type="radio" class="ace" value="1">' +
        '    <span class="lbl"> 全局分页</span>' +
        '  </label>' +
        '&nbsp; &nbsp;' +
        '  <label class="inline">' +
        '    <input name="pagingType" type="radio" class="ace" value="0">' +
        '    <span class="lbl"> 局部分页</span>' +
        '  </label>' +
        '	</div>' +
        '</div>' +

        '<div class="form-group">' +
        '	<label class="col-sm-2 control-label">需要登录</label>' +
        '	<div class="col-sm-10">' +
        '  <label class="inline">' +
        '    <input name="loginRequired" type="radio" class="ace" value="1">' +
        '    <span class="lbl"> 是</span>' +
        '  </label>' +
        '&nbsp; &nbsp;' +
        '  <label class="inline">' +
        '    <input name="loginRequired" type="radio" class="ace" value="0">' +
        '    <span class="lbl"> 否</span>' +
        '  </label>' +
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
                    var webUrl = $.trim($("#addWebInfoOption input[name=id]").val());
                    var webName = $.trim($("#addWebInfoOption input[name=webName]").val());
                    var requestType = $.trim($("#addWebInfoOption input[name=requestType]:checked").val());
                    var contentLocationType = $.trim($("#addWebInfoOption input[name=contentLocationType]:checked").val());
                    var pagingType = $.trim($("#addWebInfoOption input[name=pagingType]:checked").val());
                    var loginRequired = $.trim($("#addWebInfoOption input[name=loginRequired]:checked").val());
                    $.ajax({
                        url:"/webInfoOption/save",
                        type:"POST",
                        data:{
                            "webName":webName,
                            "webUrl":webUrl,
                            "requestType":requestType,
                            "contentLocationType":contentLocationType,
                            "pagingType":pagingType,
                            "loginRequired":loginRequired
                        },
                        success:function(data){
                            if(data.code == 1){
                                location.reload();
                                return false;
                            }
                            alert("添加失败：" + data.code);
                        }
                    });
                    return false;
                }
            }
        }
    });
}

function edit(name, url, requestType, contentLocationType, pagingType, loginRequired){
    console.log(requestType, pagingType, loginRequired)
    bootbox.dialog({
        title:"登录分页配置修改：",
        message:'<form id="addWebInfoOption" method="post" class="form-horizontal">' +

        '<div class="form-group">' +
        '	<label class="col-sm-2 control-label">ID</label>' +
        '	<div class="col-sm-10">' +
        '		<input type="text" name="id" class="form-control" placeholder="URL" readonly="true" value="'+ url +'">' +
        '	</div>' +
        '</div>' +

        '<div class="form-group">' +
        '	<label class="col-sm-2 control-label">网站名称</label>' +
        '	<div class="col-sm-10">' +
        '		<input type="text" name="webName" class="form-control" placeholder="网站名称" readonly="true" value="'+ name +'">' +
        '	</div>' +
        '</div>' +

        '<div class="form-group">' +
        '	<label class="col-sm-2 control-label">请求类型</label>' +
        '	<div class="col-sm-10">' +
        '  <label class="inline">' +
        '    <input name="requestType" type="radio" class="ace" value="0"' + ((requestType == 0) ? 'checked' : '') +'>' +
        '    <span class="lbl"> GET请求</span>' +
        '  </label>' +
        '&nbsp; &nbsp;' +
        '  <label class="inline">' +
        '    <input name="requestType" type="radio" class="ace" value="1"' + ((requestType == 1) ? 'checked' : '') +'>' +
        '    <span class="lbl"> POST请求</span>' +
        '  </label>' +
        '	</div>' +
        '</div>' +

        '<div class="form-group">' +
        '	<label class="col-sm-2 control-label">标题内容在同一页面</label>' +
        '	<div class="col-sm-10">' +
        '  <label class="inline">' +
        '    <input name="contentLocationType" type="radio" class="ace" value="0"' + ((contentLocationType == 0) ? 'checked' : '') +'>' +
        '    <span class="lbl"> 在同一页面</span>' +
        '  </label>' +
        '&nbsp; &nbsp;' +
        '  <label class="inline">' +
        '    <input name="contentLocationType" type="radio" class="ace" value="1"' + ((contentLocationType == 1) ? 'checked' : '') +'>' +
        '    <span class="lbl"> 不在同一页面</span>' +
        '  </label>' +
        '	</div>' +
        '</div>' +

        '<div class="form-group">' +
        '	<label class="col-sm-2 control-label">分页类型</label>' +
        '	<div class="col-sm-10">' +
        '  <label class="inline">' +
        '    <input name="pagingType" type="radio" class="ace" value="1"' + ((pagingType == 1) ? 'checked' : '') +'>' +
        '    <span class="lbl"> 全局分页</span>' +
        '  </label>' +
        '&nbsp; &nbsp;' +
        '  <label class="inline">' +
        '    <input name="pagingType" type="radio" class="ace" value="0"' + ((pagingType == 0) ? 'checked' : '') +'>' +
        '    <span class="lbl"> 局部分页</span>' +
        '  </label>' +
        '	</div>' +
        '</div>' +

        '<div class="form-group">' +
        '	<label class="col-sm-2 control-label">需要登录</label>' +
        '	<div class="col-sm-10">' +
        '  <label class="inline">' +
        '    <input name="loginRequired" type="radio" class="ace" value="1"' + ((loginRequired == 1) ? 'checked' : '') + '>' +
        '    <span class="lbl"> 是</span>' +
        '  </label>' +
        '&nbsp; &nbsp;' +
        '  <label class="inline">' +
        '    <input name="loginRequired" type="radio" class="ace" value="0"' + ((loginRequired == 0) ? 'checked' : '') + '>' +
        '    <span class="lbl"> 否</span>' +
        '  </label>' +
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
                    var webUrl = $.trim($("#addWebInfoOption input[name=id]").val());
                    var webName = $.trim($("#addWebInfoOption input[name=webName]").val());
                    var pagingType = $.trim($("#addWebInfoOption input[name=pagingType]:checked").val());
                    var requestType = $.trim($("#addWebInfoOption input[name=requestType]:checked").val());
                    var contentLocationType = $.trim($("#addWebInfoOption input[name=contentLocationType]:checked").val());
                    var loginRequired = $.trim($("#addWebInfoOption input[name=loginRequired]:checked").val());
                    $.ajax({
                        url:"/webInfoOption/update",
                        type:"POST",
                        data:{
                            "name":webName,
                            "id":webUrl,
                            "requestType":requestType,
                            "contentLocationType":contentLocationType,
                            "pagingType":pagingType,
                            "loginRequired":loginRequired},
                        success:function(data){
                            if(data.code == 1){
                                location.reload();
                                return false;
                            }
                            alert("修改失败：" + data.code);
                        }
                    });
                    return false;
                }
            }
        }
    });
}

function del(id){
    bootbox.confirm("你确定要删除么?", function(result) {
        if(result) {
            $.ajax({
                url:"/webInfoOption/delete",
                type:"POST",
                data:{"id":id},
                success:function(data){
                    if (data.code == 1) {
                        bootbox.alert("删除成功");
                        location.reload();
                    } else {
                        alert("删除失败:" + data);
                    }
                }
            });
        }
    });
}
</script>
</body>
</html>
