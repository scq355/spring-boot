<script type="text/javascript">
    try {
        ace.settings.check('sidebar', 'fixed')
    } catch (e) {}
</script>
<script src="/static/js/ace/jquery-1.10.2.min.js"></script>

<div class="sidebar-shortcuts" id="sidebar-shortcuts">
    <div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
        <button class="btn btn-success">
            <i class="icon-signal"></i>
        </button>

        <button class="btn btn-info">
            <i class="icon-pencil"></i>
        </button>

        <button class="btn btn-warning">
            <i class="icon-group"></i>
        </button>

        <button class="btn btn-danger">
            <i class="icon-cogs"></i>
        </button>
    </div>

    <div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
        <span class="btn btn-success"></span>

        <span class="btn btn-info"></span>

        <span class="btn btn-warning"></span>

        <span class="btn btn-danger"></span>
    </div>
</div>

<ul class="nav nav-list">
    <li class="crawler">
        <a href="/crawler/list">
            <i class="icon-comments"></i>
            <span class="menu-text"> 用户评论 </span>
        </a>
    </li>

    <li id="base_group_finance" class="">
        <a href="#" class="dropdown-toggle">
            <i class="icon-money"></i>
            <span class="menu-text"> 债权转让 </span>

            <b class="arrow icon-angle-down"></b>
        </a>

        <ul class="submenu" id="submenu_base_finance">
            <li class="financeStatic">
                <a href="/financeStatic/list">
                    <i class="icon-bar-chart"></i>
                    <span class="menu-text"> 统计概览 </span>
                </a>
            </li>
            <li class="financeCreditor">
                <a href="/financeCreditor/list">
                    <i class="icon-list-alt"></i>
                    <span class="menu-text"> 数据详情 </span>
                </a>
            </li>
        </ul>
    </li>

    <li id="base_group" class="">
        <a href="#" class="dropdown-toggle">
            <i class="icon-desktop"></i>
            <span class="menu-text"> 基础配置 </span>

            <b class="arrow icon-angle-down"></b>
        </a>

        <ul class="submenu" id="submenu_base">
            <li class="webInfo">
                <a href="/webInfo/list">
                    <i class="icon-globe"></i>
                    <span class="menu-text"> 网址配置 </span>
                </a>
            </li>
            <li class="webInfoOption">
                <a href="/webInfoOption/list">
                    <i class="icon-exchange"></i>
                    <span class="menu-text"> 页面配置 </span>
                </a>
            </li>
            <li class="webConfig">
                <a href="/webConfig/list">
                    <i class="icon-book"></i>
                    <span class="menu-text"> 分页配置 </span>
                </a>
            </li>
            <li class="webToken">
                <a href="/webToken/list">
                    <i class="icon-key"></i>
                    <span class="menu-text"> 凭证配置 </span>
                </a>
            </li>
        </ul>
    </li>

</ul>

<div class="sidebar-collapse" id="sidebar-collapse">
    <i class="icon-double-angle-left" data-icon1="icon-double-angle-left" data-icon2="icon-double-angle-right"></i>
</div>

<script type="text/javascript">
    $(function(){
        var url = document.URL;
        var urlArr = url.split('/');
        if ("webInfo" === urlArr[3] || "webInfoOption" === urlArr[3]
                || 'webConfig' === urlArr[3] || 'webToken' === urlArr[3]) {
            $('#base_group').addClass('active open');
            document.getElementById('submenu_base').style.display="block";
        } else if ("financeCreditor" === urlArr[3] || "financeStatic" === urlArr[3]) {
            $('#base_group_finance').addClass('active open');
            document.getElementById('submenu_base_finance').style.display="block";
        }
        $('.' + urlArr[3]).addClass('active');
    })
</script>

<script type="text/javascript">
    try {
        ace.settings.check('sidebar', 'collapsed')
    } catch (e) {}
</script>