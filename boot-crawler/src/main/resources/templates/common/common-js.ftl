<#macro ace>
<!-- basic scripts -->

<!--[if !IE]> -->

<script src="/static/js/common/jquery-2.0.3.min.js"></script>

<!-- <![endif]-->

<!--[if IE]>
<script src="/static/js/common/jquery-2.0.3.min.js"></script>
<![endif]-->

<!--[if !IE]> -->

<script type="text/javascript">
    window.jQuery || document.write("<script src='/static/js/ace/jquery-2.0.3.min.js'>"+"<"+"script>");
</script>

<!-- <![endif]-->

<!--[if IE]>
<script type="text/javascript">
 window.jQuery || document.write("<script src='assets/js/jquery-1.10.2.min.js'>"+"<"+"script>");
</script>
<![endif]-->

<script type="text/javascript">
    if("ontouchend" in document) document.write("<script src='/static/js/ace/jquery.mobile.custom.min.js'>"+"<"+"script>");
</script>
<script src="/static/js/ace/bootstrap.min.js"></script>
<script src="/static/js/ace/typeahead-bs2.min.js"></script>

<!-- page specific plugin scripts -->

<!--[if lte IE 8]>
<script src="/static/js/ace/excanvas.min.js"></script>
<![endif]-->

<script src="/static/js/ace/jquery-ui-1.10.3.custom.min.js"></script>
<script src="/static/js/ace/jquery.ui.touch-punch.min.js"></script>
<script src="/static/js/ace/jquery.slimscroll.min.js"></script>
<script src="/static/js/ace/jquery.easy-pie-chart.min.js"></script>
<script src="/static/js/ace/jquery.sparkline.min.js"></script>
<script src="/static/js/ace/flot/jquery.flot.min.js"></script>
<script src="/static/js/ace/flot/jquery.flot.pie.min.js"></script>
<script src="/static/js/ace/flot/jquery.flot.resize.min.js"></script>

<!-- ace scripts -->

<script src="/static/js/ace/ace-elements.min.js"></script>
<script src="/static/js/ace/ace.min.js"></script>
</#macro>

<#macro datePicker>
<script src="/static/js/ace/date-time/bootstrap-datepicker.min.js"></script>
<script src="/static/js/ace/date-time/bootstrap-timepicker.min.js"></script>
<script src="/static/js/ace/date-time/moment.min.js"></script>
<script src="/static/js/ace/date-time/daterangepicker.min.js"></script>
<script src="/static/js/ace/date-time/locales/bootstrap-datepicker.zh-CN.js"></script>
<script>
    $(function(){
        $.fn.datepicker.defaults = {
            language: 'zh-CN'
        };
    });
</script>
</#macro>

<#macro dataTables>
<script src="/static/js/ace/jquery.dataTables.min.js"></script>
<script src="/static/js/ace/jquery.dataTables.bootstrap.js"></script>
</#macro>