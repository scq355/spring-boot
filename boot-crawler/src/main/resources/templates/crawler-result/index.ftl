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
                    <li class="active">舆情监控</li>
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
                                            <h3 class="header smaller lighter blue">舆情监控</h3>
                                            <div class="table-header"></div>
                                            <div class="row">
                                                <form id="myform" action="" method="post">
                                                    <input type="hidden" name="pageNumber" value="${pageNumber!}">
                                                    <div class="col-xs-12 col-sm-3">
                                                        <div class="input-group">
                                                            <input type="text" name="name" value="${name!}" class="form-control search-query" placeholder="名称" />
                                                            <span class="input-group-btn">
                                                                <button type="button" class="btn btn-purple btn-sm btn-search">
                                                                  查询
                                                                  <i class="icon-search icon-on-right bigger-110"></i>
                                                                </button>
                                                            </span>
                                                        </div>
                                                    </div>
                                                </form>
                                            </div>
                                            <div class="space-6"></div>
                                            <div class="table-responsive">
                                                <div class="alert alert-block alert-success">
                                                    <i class="icon-bar-chart"></i> 当前记录：<strong class="red">${count!}条</strong>
                                                </div>
                                                <table id="sample-table-2" class="table table-striped table-bordered table-hover">
                                                    <tr>
                                                        <th>名称</th>
                                                        <th>URL</th>
                                                        <th>匹配到的关键字</th>
                                                        <th>处理状态</th>
                                                        <th>更新时间</th>
                                                        <th>操作</th>
                                                    </tr>
                                                    <#list crawlerResultList as result>
                                                    <tr>
                                                        <td>${result.name!}</td>
                                                        <td class="hidden-sm action-buttons"><a href="${result.id!}" target="_blank">${result.id!}</a></td>
                                                        <td>${result.matchedParams!}</td>
                                                        <td>
                                                            <#if result.status == 1><span class="label label-danger">待处理</span>
                                                            <#elseif result.status == 2><span class="label label-yellow">已经处理</span>
                                                            </#if>
                                                        </td>
                                                        <td>${result.updateAt?string("yyyy-MM-dd HH:mm:ss")!}</td>
                                                        <td>
                                                            <div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
                                                                <button class="btn btn-xs btn-success" onclick="setDisable('${result.id}')">
                                                                    <i class="icon-refresh align-top bigger-110"></i>
                                                                    处理
                                                                </button>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    </#list>
                                                </table>
                                            </div>
                                            <div style="float: right">
                                                <ul class="pagination">
                                                    <li class=<#if pageNumber == 1>"disabled"<#else>"active"</#if>>
                                                        <#assign num = pageNumber - 1>
                                                        <a href=<#if pageNumber == 1>"#"<#else>"/crawler/list?pageNumber=${num!}"</#if> style="cursor: pointer">
                                                            <i class="icon-double-angle-left">&nbsp;上一页</i>
                                                        </a>
                                                    </li>
                                                    <li class=<#if pageNumber == pageNum>"disabled"<#else>"active"</#if>>
                                                        <#assign num = pageNumber + 1>
                                                        <a href=<#if pageNumber == pageNum>"#"<#else>"/crawler/list?pageNumber=${num!}"</#if> style="cursor: pointer">
                                                            下一页&nbsp;<i class="icon-double-angle-right"></i>
                                                        </a>
                                                    </li>
                                                </ul>
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
/* 查询 */
$('.btn-search').click(function() {
    $('#myform').submit();
});


function setDisable(id){
    bootbox.confirm("确定本条数据已经处理了么?", function(result) {
        if(result) {
            $.ajax({
                url:"/crawler/update",
                type:"POST",
                data:{"id":id,"status":2},
                success:function(data){
                    if (data.context == 1) {
                        bootbox.alert("处理成功");
                        location.reload();
                    } else {
                        alert("处理失败:" + data.msg);
                    }
                }
            });
        }
    });
}
</script>
</body>
</html>
