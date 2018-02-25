<@l.manage title="后台管理"  import="" tab="">
    <@c.style name="css/user/main.css"></@c.style>

    <@c.style name="compents/layui/css/layui.css"></@c.style>
<div class="layui-layout layui-layout-admin">

    <!-- 左侧导航 -->
    <div class="layui-side layui-bg-black">
        <div class="navBar layui-side-scroll"></div>
    </div>
    <!-- 右侧内容 -->
    <div class="layui-body layui-form">
        <div class="layui-tab marg0" lay-filter="bodyTab">
            <ul class="layui-tab-title top_tab">
                <li class="layui-this" lay-id=""><i class="iconfont icon-computer"></i> <cite>用户管理</cite></li>
            </ul>
            <div class="layui-tab-content clildFrame">
                <div class="layui-tab-item layui-show">
                    <iframe src="../user/toManageMain"></iframe>
                </div>
            </div>
        </div>
    </div>
    <!-- 底部 -->
    <div class="layui-footer footer">
        <p>版权所有 @2017&nbsp;&nbsp;南京市国土资源局&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;技术支持：浙江浙大万维科技有限公司</p>
    </div>
</div>


    <@c.script name="compents/layui/layui.js"></@c.script>
    <@c.script name="js/user/nav.js"></@c.script>
    <@c.script name="js/user/leftNav.js"></@c.script>
    <@c.script name="js/user/index.js"></@c.script>


</@l.manage>