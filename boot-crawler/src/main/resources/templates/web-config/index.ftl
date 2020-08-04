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
                    <li class="active">分页配置</li>
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
                                            <h3 class="header smaller lighter blue">分页配置</h3>
                                            <div class="table-header"></div>
                                            <div class="row">
                                                <form id="myform" action="" method="post">
                                                    <input type="hidden" name="pageNumber" value="${pageNumber!}">
                                                </form>
                                            </div>

                                            <div class="space-6"></div>
                                            <div class="table-responsive">
                                                <div class="alert alert-block alert-success visible-md visible-lg hidden-sm hidden-xs action-buttons">
                                                    <a href="javascript:add();">+添加配置</a>
                                                </div>
                                                <table id="sample-table-2" class="table table-striped table-bordered table-hover">
                                                    <tr>
                                                        <th>名称</th>
                                                        <th width="10%">URL</th>
                                                        <th width="10%">分页请求具体链接</th>
                                                        <th>分页方式</th>
                                                        <th>内容样式选择器</th>
                                                        <th>内容详情样式选择器</th>
                                                        <th>分页样式选择器</th>
                                                        <th>分页分隔符</th>
                                                        <th>总页数</th>
                                                        <th>操作</th>
                                                    </tr>
                                                    <#list webConfigList as webConfig>
                                                    <tr>
                                                        <td>${webConfig.name!}</td>
                                                        <td>${webConfig.id!}</td>
                                                        <td>${webConfig.pagingUrl!}</td>
                                                        <td>${webConfig.pageMethod!}</td>
                                                        <td>${webConfig.pageContentSelector!}</td>
                                                        <td>${webConfig.contentDetailSelector!}</td>
                                                        <td>${webConfig.pagingSelector!}</td>
                                                        <td>${webConfig.pageSeparator!}</td>
                                                        <td>${webConfig.totalPageNum!}</td>
                                                        <td>
                                                            <div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
                                                                <button class="btn btn-xs btn-primary" onclick="del('${webConfig.id}')">
                                                                    <i class="icon-barcode align-top bigger-110"></i>
                                                                    重置
                                                                </button>
                                                                <button class="btn btn-xs btn-success"
                                                                        onclick="edit('${webConfig.name!}',
                                                                                '${webConfig.id!}',
                                                                                '${webConfig.pagingUrl!}',
                                                                                '${webConfig.pageMethod!}',
                                                                                '${webConfig.pageContentSelector!}',
                                                                                '${webConfig.contentDetailSelector!}',
                                                                                '${webConfig.pagingSelector!}',
                                                                                '${webConfig.pageSeparator!}',
                                                                                '${webConfig.totalPageNum!}'
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
            title:"网页配置添加：",
            message:'<form id="editConfig" method="post" class="form-horizontal">' +
            '<div class="form-group">' +
            '	<label class="col-sm-2 control-label">URL</label>' +
            '	<div class="col-sm-10">' +
            '		<input type="text" name="url" class="form-control" placeholder="WEBINFO-URL">' +
            '	</div>' +
            '</div>' +
            '<div class="form-group">' +
            '	<label class="col-sm-2 control-label">分页请求具体链接</label>' +
            '	<div class="col-sm-10">' +
            '		<input type="text" name="pagingUrl" class="form-control" placeholder="pagingUrl">' +
            '	</div>' +
            '</div>' +
            '<div class="form-group">' +
            '	<label class="col-sm-2 control-label">名称</label>' +
            '	<div class="col-sm-10">' +
            '		<input type="text" name="name" class="form-control" placeholder="Name">' +
            '	</div>' +
            '</div>' +
            '<div class="form-group">' +
            '	<label class="col-sm-2 control-label">分页方式</label>' +
            '	<div class="col-sm-10">' +
            '		<input type="text" name="pageMethod" class="form-control" placeholder="URI：prefix:suffix">' +
            '	</div>' +
            '</div>' +
            '<div class="form-group">' +
            '	<label class="col-sm-2 control-label">内容截取样式</label>' +
            '	<div class="col-sm-10">' +
            '		<input type="text" name="pageContentSelector" class="form-control" placeholder="Content_CSS_Selector">' +
            '	</div>' +
            '</div>' +

            '<div class="form-group">' +
            '	<label class="col-sm-2 control-label">内容详情样式选择器</label>' +
            '	<div class="col-sm-10">' +
            '		<input type="text" name="contentDetailSelector" class="form-control" placeholder="Content_Detail_CSS_Selector">' +
            '	</div>' +
            '</div>' +

            '<div class="form-group">' +
            '	<label class="col-sm-2 control-label">分页截取样式</label>' +
            '	<div class="col-sm-10">' +
            '		<input type="text" name="pagingSelector" class="form-control" placeholder="Pagging_CSS_Selector">' +
            '	</div>' +
            '</div>' +
            '<div class="form-group">' +
            '	<label class="col-sm-2 control-label">分页分隔符</label>' +
            '	<div class="col-sm-10">' +
            '		<input type="text" name="pageSeparator" class="form-control" placeholder="Paging_Separator">' +
            '	</div>' +
            '</div>' +
            '<div class="form-group">' +
            '	<label class="col-sm-2 control-label">总页数</label>' +
            '	<div class="col-sm-10">' +
            '		<input type="text" name="totalPageNum" class="form-control" placeholder="totalPageNum">' +
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
                        var url = $.trim($("#editConfig input[name=url]").val());
                        var name = $.trim($("#editConfig input[name=name]").val());
                        var pagingUrl = $.trim($("#editConfig input[name=pagingUrl]").val());
                        var pageMethod = $.trim($("#editConfig input[name=pageMethod]").val());
                        var pageContentSelector = $.trim($("#editConfig input[name=pageContentSelector]").val());
                        var contentDetailSelector = $.trim($("#editConfig input[name=contentDetailSelector]").val());
                        var pagingSelector = $.trim($("#editConfig input[name=pagingSelector]").val());
                        var pageSeparator = $.trim($("#editConfig input[name=pageSeparator]").val());
                        var totalPageNum = $.trim($("#editConfig input[name=totalPageNum]").val());
                        $.ajax({
                            url:"/webConfig/save",
                            type:"POST",
                            data:{
                                "webInfoId":url,
                                "pagingUrl":pagingUrl,
                                "name":name,
                                "pageMethod":pageMethod,
                                "pageContentSelector":pageContentSelector,
                                "contentDetailSelector":contentDetailSelector,
                                "pagingSelector":pagingSelector,
                                "pageSeparator":pageSeparator,
                                "totalPageNum":totalPageNum
                            },
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

    function edit(name, url, pagingUrl, pageMethod, pageContentSelector, contentDetailSelector, pagingSelector, pageSeparator, totalPageNum){
        bootbox.dialog({
            title:"网页信息编辑：",
            message:'<form id="editConfig" method="post" class="form-horizontal">' +
            '<div class="form-group">' +
            '	<label class="col-sm-2 control-label">名称</label>' +
            '	<div class="col-sm-10">' +
            '		<input type="text" name="name" class="form-control" placeholder="网站名称" value="'+ name +'" readonly="true">' +
            '	</div>' +
            '</div>' +
            '<div class="form-group">' +
            '	<label class="col-sm-2 control-label">URL</label>' +
            '	<div class="col-sm-10">' +
            '		<input type="text" name="url" class="form-control" placeholder="WEBINFO-URL" value="'+ url +'" readonly="true">' +
            '	</div>' +
            '</div>' +
            '<div class="form-group">' +
            '	<label class="col-sm-2 control-label">分页请求具体链接</label>' +
            '	<div class="col-sm-10">' +
            '		<input type="text" name="pagingUrl" class="form-control" placeholder="pagingUrl" value="'+ pagingUrl +'">' +
            '	</div>' +
            '</div>' +
            '<div class="form-group">' +
            '	<label class="col-sm-2 control-label">分页方式</label>' +
            '	<div class="col-sm-10">' +
            '		<input type="text" name="pageMethod" class="form-control" placeholder="http://www.***.com" value="'+ pageMethod +'">' +
            '	</div>' +
            '</div>' +
            '<div class="form-group">' +
            '	<label class="col-sm-2 control-label">内容截取样式</label>' +
            '	<div class="col-sm-10">' +
            '		<input type="text" name="pageContentSelector" class="form-control" placeholder="CSS样式" value="'+ pageContentSelector +'">' +
            '	</div>' +
            '</div>' +
            '<div class="form-group">' +
            '	<label class="col-sm-2 control-label">内容详情截取样式</label>' +
            '	<div class="col-sm-10">' +
            '		<input type="text" name="contentDetailSelector" class="form-control" placeholder="CSS样式" value="'+ contentDetailSelector +'">' +
            '	</div>' +
            '</div>' +
            '<div class="form-group">' +
            '	<label class="col-sm-2 control-label">分页截取样式</label>' +
            '	<div class="col-sm-10">' +
            '		<input type="text" name="pagingSelector" class="form-control" placeholder="CSS样式" value="'+ pagingSelector +'">' +
            '	</div>' +
            '</div>' +
            '<div class="form-group">' +
            '	<label class="col-sm-2 control-label">分页分隔符</label>' +
            '	<div class="col-sm-10">' +
            '		<input type="text" name="pageSeparator" class="form-control" placeholder="一般为空格" value="'+ pageSeparator +'">' +
            '	</div>' +
            '</div>' +
            '<div class="form-group">' +
            '	<label class="col-sm-2 control-label">总页数</label>' +
            '	<div class="col-sm-10">' +
            '		<input type="text" name="totalPageNum" class="form-control" placeholder="总页数" value="'+ totalPageNum +'">' +
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
                        var url = $.trim($("#editConfig input[name=url]").val());
                        var pagingUrl = $.trim($("#editConfig input[name=pagingUrl]").val());
                        var pageMethod = $.trim($("#editConfig input[name=pageMethod]").val());
                        var pageContentSelector = $.trim($("#editConfig input[name=pageContentSelector]").val());
                        var contentDetailSelector = $.trim($("#editConfig input[name=contentDetailSelector]").val());
                        var pagingSelector = $.trim($("#editConfig input[name=pagingSelector]").val());
                        var pageSeparator = $.trim($("#editConfig input[name=pageSeparator]").val());
                        var totalPageNum = $.trim($("#editConfig input[name=totalPageNum]").val());
                        $.ajax({
                            url:"/webConfig/update",
                            type:"POST",
                            data:{
                                "id":url,
                                "pagingUrl":pagingUrl,
                                "pageMethod":pageMethod,
                                "pageContentSelector":pageContentSelector,
                                "contentDetailSelector":contentDetailSelector,
                                "pagingSelector":pagingSelector,
                                "pageSeparator":pageSeparator,
                                "totalPageNum":totalPageNum
                            },
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

    function del(id){
        bootbox.confirm("你确定要重置么?", function(result) {
            if(result) {
                $.ajax({
                    url:"/webConfig/reset",
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