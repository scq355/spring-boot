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
                    <li class="active">账号凭证</li>
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
                                            <h3 class="header smaller lighter blue">账号凭证</h3>
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
                                                        <th>用户名</th>
                                                        <th>密码</th>
                                                        <th>凭证</th>
                                                        <th>操作</th>
                                                    </tr>
                                                    <#list webTokenList as webToken>
                                                    <tr>
                                                        <td>${webToken.name!}</td>
                                                        <td>${webToken.id!}</td>
                                                        <td>${webToken.userName!}</td>
                                                        <td>${webToken.password!}</td>
                                                        <td>
                                                        <textarea style="width: 400px!important; resize: none;" readonly="readonly">
                                                            ${webToken.certificate!}
                                                        </textarea>
                                                        </td>
                                                        <td>
                                                            <div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
                                                                <button class="btn btn-xs btn-primary" onclick="reset('${webToken.id}')">
                                                                    <i class="icon-barcode align-top bigger-110"></i>
                                                                    重置
                                                                </button>
                                                                <button class="btn btn-xs btn-success"
                                                                        onclick="edit('${webToken.name!}',
                                                                                '${webToken.id!}',
                                                                                '${webToken.userName!}',
                                                                                '${webToken.password!}',
                                                                                '${webToken.certificate!}')">
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
    $(function(){
        $('.date-picker').datepicker({autoclose:true}).next().on(ace.click_event, function(){
            $(this).prev().focus();
        });

        $('#spinner1').ace_spinner({
            value: 0,
            min: 0,
            max: 200,
            step: 10,
            btn_up_class: 'btn-info',
            btn_down_class:'btn-info'
        }).on('change', function(){
            //alert(this.value)
        });
    });

    function add(){
        bootbox.dialog({
            title:"网页信息编辑：",
            message:'<form id="addWebToken" method="post" class="form-horizontal">' +
            '<div class="form-group">' +
            '	<label class="col-sm-2 control-label">ID</label>' +
            '	<div class="col-sm-10">' +
            '		<input type="text" name="webUrl" class="form-control" placeholder="URL">' +
            '	</div>' +
            '</div>' +
            '<div class="form-group">' +
            '	<label class="col-sm-2 control-label">网站名称</label>' +
            '	<div class="col-sm-10">' +
            '		<input type="text" name="webName" class="form-control" placeholder="网站名称">' +
            '	</div>' +
            '</div>' +
            '<div class="form-group">' +
            '	<label class="col-sm-2 control-label">用户名</label>' +
            '	<div class="col-sm-10">' +
            '		<input type="text" name="userName" class="form-control" placeholder="用户名">' +
            '	</div>' +
            '</div>' +
            '<div class="form-group">' +
            '	<label class="col-sm-2 control-label">密码</label>' +
            '	<div class="col-sm-10">' +
            '		<input type="text" name="password" class="form-control" placeholder="******">' +
            '	</div>' +
            '</div>' +
            '<div class="form-group">' +
            '	<label class="col-sm-2 control-label">凭证</label>' +
            '	<div class="col-sm-10">' +
            '		<input type="text" name="certificate" class="form-control" placeholder="token">' +
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
                        var webUrl = $.trim($("#addWebToken input[name=webUrl]").val());
                        var webName = $.trim($("#addWebToken input[name=webName]").val());
                        var userName = $.trim($("#addWebToken input[name=userName]").val());
                        var password = $.trim($("#addWebToken input[name=password]").val());
                        var certificate = $.trim($("#addWebToken input[name=certificate]").val());
                        $.ajax({
                            url:"/webToken/save",
                            type:"POST",
                            data:{"webName":webName, "webUrl":webUrl,"userName":userName, "password":password, "certificate":certificate},
                            success:function(data){
                                if(data.code == 1){
                                    location.reload();
                                    return false;
                                }
                                alert("失败：" + data.code);
                            }
                        });
                        return false;
                    }
                }
            }
        });
    }

    function edit(name, id, userName, password, certificate){
        bootbox.dialog({
            title:"网页信息编辑：",
            message:'<form id="editWebToken" method="post" class="form-horizontal">' +
            '<div class="form-group">' +
            '	<label class="col-sm-2 control-label">网站名称</label>' +
            '	<div class="col-sm-10">' +
            '		<input type="text" name="webName" class="form-control" value="'+ name +'" readonly="true">' +
            '	</div>' +
            '</div>' +
            '<div class="form-group">' +
            '	<label class="col-sm-2 control-label">URL</label>' +
            '	<div class="col-sm-10">' +
            '		<input type="text" name="url" class="form-control" value="'+ id +'" readonly="true">' +
            '	</div>' +
            '</div>' +
            '<div class="form-group">' +
            '	<label class="col-sm-2 control-label">用户名</label>' +
            '	<div class="col-sm-10">' +
            '		<input type="text" name="userName" class="form-control" value="'+ userName +'">' +
            '	</div>' +
            '</div>' +
            '<div class="form-group">' +
            '	<label class="col-sm-2 control-label">密码</label>' +
            '	<div class="col-sm-10">' +
            '		<input type="text" name="password" class="form-control" value="'+ password +'">' +
            '	</div>' +
            '</div>' +
            '<div class="form-group">' +
            '	<label class="col-sm-2 control-label">凭证</label>' +
            '	<div class="col-sm-10">' +
            '		<input type="text" name="certificate" class="form-control" value="'+ certificate +'">' +
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
                        var webName = $.trim($("#editWebToken input[name=webName]").val());
                        var url = $.trim($("#editWebToken input[name=url]").val());
                        var userName = $.trim($("#editWebToken input[name=userName]").val());
                        var password = $.trim($("#editWebToken input[name=password]").val());
                        var certificate = $.trim($("#editWebToken input[name=certificate]").val());
                        $.ajax({
                            url:"/webToken/update",
                            type:"POST",
                            data:{
                                "webName":webName,
                                "id":url,
                                "userName":userName,
                                "password":password,
                                "certificate":certificate
                            },
                            success:function(data){
                                if(data.code == 1){
                                    location.reload();
                                    return false;
                                }
                                alert("更新失败：" + data.code);
                            }
                        });
                        return false;
                    }
                }
            }
        });
    }

    function reset(id){
        bootbox.confirm("你确定要重置么?", function(result) {
            if(result) {
                $.ajax({
                    url:"/webToken/reset",
                    type:"POST",
                    data:{"id":id},
                    success:function(data){
                        if (data.code == 1) {
                            bootbox.alert("重置成功");
                            location.reload();
                        } else {
                            alert("重置失败:" + data);
                        }
                    }
                });
            }
        });
    }
</script>
</body>
</html>
