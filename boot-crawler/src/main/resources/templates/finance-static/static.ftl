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
                    <li class="active">债权转让</li>
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
                                            <h3 class="header smaller lighter blue">债权转让数据统计</h3>
                                            <div class="table-header"></div>
                                            <div class="row">
                                                <form id="myform" action="/financeStatic/export" method="GET">
                                                    <input type="hidden" name="pageNumber" value="${pageNumber!}">
                                                    <div class="col-sm-3 form-group">
                                                        <button class="btn btn-sm btn-info" type="submit">
                                                            <i class="icon-print bigger-110"></i>
                                                            导出
                                                        </button>
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
                                                        <th>公司名称</th>
                                                        <th>转让标的期限</th>
                                                        <th>当前统计债权转让金额<#if nowDate??>/${nowDate?string('yyyy-MM-dd hh:mm:ss')!}</#if></th>
                                                        <th>上次统计债权转让金额<#if previousDate??>/${previousDate?string('yyyy-MM-dd hh:mm:ss')!}</#if></th>
                                                        <th>浮动金额</th>
                                                    </tr>
                                                    <#list financeStaticList as finance>
                                                        <tr>
                                                            <td>${finance.webName!}</td>
                                                            <td>${finance.deadline!} 天</td>
                                                            <td align="right">
                                                            <#if finance.totalTransfer??>
                                                                ${finance.totalTransfer?string(',###.00')}
                                                            <#else>
                                                                0.00
                                                            </#if>
                                                            </td>
                                                            <td align="right">
                                                            <#if finance.totalTransferPrevious??>
                                                                ${finance.totalTransferPrevious?string(',###.00')}
                                                            <#else>
                                                                0.00
                                                            </#if>
                                                            </td>
                                                            <td align="right">
                                                            <#if finance.totalTransferPrevious?? && finance.totalTransfer??>
                                                                ${(finance.totalTransferPrevious * 2 - finance.totalTransfer)?string}
                                                            </#if>
                                                            </td>
                                                        </tr>
                                                    </#list>
                                                </table>
                                            </div>
                                            <div style="float: right">
                                                <ul class="pagination">
                                                <li class=<#if pageNumber == 1>"disabled"<#else>"active"</#if>>
                                                        <#assign num = pageNumber - 1>
                                                        <a href=<#if pageNumber == 1>"#"<#else>"/financeStatic/list?pageNumber=${num!}"</#if> style="cursor: pointer">
                                                    <i class="icon-double-angle-left">&nbsp;上一页</i>
                                                    </a>
                                                    </li>
                                                        <li class=<#if pageNumber == pageNum>"disabled"<#else>"active"</#if>>
                                                        <#assign num = pageNumber + 1>
                                                        <a href=<#if pageNumber == pageNum>"#"<#else>"/financeStatic/list?pageNumber=${num!}"</#if> style="cursor: pointer">
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

    /* 导出 */
    $('.btn-info').click(function () {
        window.location.href = "/static";
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
