<#-- admin -->
<#macro admin title="" tab="" import="" >
    <@c.admin title="${title!}" import="jquery,bootstrap,awesome,metisMenu,iconfont,slimscroll,pace,animate.css,style.css,framework.js,${import}" theme="default">
    <div id="wrapper">
        <!--左侧导航开始-->
        <nav class="navbar-default navbar-static-side" role="navigation">
            <div class="nav-close"><i class="fa fa-times-circle"></i>
            </div>
            <div class="sidebar-collapse">
                <ul class="nav" id="side-menu">
                    <li>
                        <a class="logo-element navbar-minimalize" href="#"><i class="fa fa-list" aria-hidden="true"></i>
                        </a>
                    </li>
                    <li <#if tab=="area"||tab="user"||tab="organization">class="active"</#if>>
                        <a href="#">
                            <i class="fa fa-university"></i>
                            <span class="nav-label">系统管理</span>
                            <span class="fa arrow"></span>
                        </a>
                        <ul class="nav nav-second-level collapse in">
                            <li <#if tab == "organization">class="active"</#if>>
                                <a class="J_menuItem" href="<@c.rootPath/>/admin/organization/list"><i class="iconfont icon-zuzhijigou" aria-hidden="true"></i>机构管理</a>
                            </li>
                            <li <#if tab == "role">class="active"</#if>>
                                <a class="J_menuItem" href="<@c.rootPath/>/admin/role/list"><i class="iconfont icon-jiaose" aria-hidden="true"></i>角色管理</a>
                            </li>
                            <li <#if tab == "user">class="active"</#if>>
                                <a class="J_menuItem" href="<@c.rootPath/>/admin/user/list" data-index="0"><i class="iconfont icon-iconfontcolor19"
                                ></i>用户管理</a>
                            </li>
                            <li <#if tab == "area">class="active"</#if>>
                                <a class="J_menuItem" href="<@c.rootPath/>/admin/area/list"><i class="iconfont icon-quyu" aria-hidden="true"></i>行政区划管理</a>
                            </li>
                            <li <#if tab == "power">class="active"</#if>>
                                <a class="J_menuItem" href="<@c.rootPath/>/admin/power/list"><i class="iconfont icon-quanxian" aria-hidden="true"></i>权限管理</a>
                            </li>
                            <li <#if tab == "apk">class="active"</#if>>
                                <a class="J_menuItem" href="<@c.rootPath/>/admin/apkversion/list"><i class="iconfont icon-iconfontweibiaoti1015" aria-hidden="true"></i>apk版本管理</a>
                            </li>
                        </ul>
                    </li>
                    <#--<li <#if tab=="power"||tab="role"||tab="dict"||tab="service"||tab="job"||tab="apis"||tab="apk">class="active"</#if>>
                        <a href="#">
                            <i class="iconfont icon-shezhi" aria-hidden="true"></i>
                            <span class="nav-label">系统设置</span>
                            <span class="fa arrow"></span>
                        </a>
                        <ul class="nav nav-second-level">
                            <li <#if tab == "dict">class="active"</#if>>
                                <a class="J_menuItem" href="<@c.rootPath/>/admin/dict/list"><i class="iconfont icon-zidian" aria-hidden="true"></i>字典管理</a>
                            </li>
                            <li <#if tab == "service">class="active"</#if>>
                                <a class="J_menuItem" href="<@c.rootPath/>/admin/datasource/list"><i class="iconfont icon-c22_hide" aria-hidden="true"></i>服务管理</a>
                            </li>
                            <li <#if tab == "job">class="active"</#if>>
                                <a class="J_menuItem" href="<@c.rootPath/>/admin/job/list"><i class="iconfont icon-renwu-copy" aria-hidden="true"></i>任务管理</a>
                            </li>
                        <li <#if tab == "apis">class="active"</#if>>
                        <a class="J_menuItem" href="<@c.rootPath/>/admin/api/list"><i class="iconfont icon-iconfontweibiaoti1015" aria-hidden="true"></i>API管理</a>
                        </li>
                        </ul>
                    </li>-->

                <#--<li <#if tab=="sqlMonitor"||tab="userMonitor"||tab="logMonitor"||tab="systemMonitor">class="active"</#if>>-->
                <#--<a href="#"><i class="iconfont icon-iconfontpczhenjijiance-copy"></i> <span class="nav-label">系统监控 </span>-->
                <#--<span class="fa arrow"></span></a>-->
                <#--<ul class="nav nav-second-level">-->
                <#--<li <#if tab == "userMonitor">class="active"</#if>><a class="J_menuItem" href="<@c.rootPath/>/admin/monitor/user">-->
                <#--<i class="iconfont icon-yonghujiankong-copy" aria-hidden="true"></i>用户监控</a>-->
                <#--</li>-->
                <#--<li <#if tab == "logMonitor">class="active"</#if>><a class="J_menuItem" href="<@c.rootPath/>/admin/monitor/log"><i class="iconfont icon-rizhi" aria-hidden="true"></i>日志监控</a>-->
                <#--</li>-->
                <#--<#if env.getPro('system.monitor.enable') == "true">-->
                <#--<li <#if tab == "systemMonitor">class="active"</#if>><a class="J_menuItem" href="<@c.rootPath/>/admin/monitor/system"><i class="iconfont icon-fuwuduanjiankong" aria-hidden="true"></i>系统监控</a>-->
                <#--</li>-->
                <#--</#if>-->
                <#--<li <#if tab == "sqlMonitor">class="active"</#if>><a class="J_menuItem" href="<@c.rootPath/>/admin/monitor/sql"><i class="iconfont icon-sql" aria-hidden="true"></i>SQL监控</a>-->
                <#--</li>-->
                <#--</ul>-->
                <#--</li>-->

                <#--<li <#if tab=="sourceConfig"||tab="functionConfig">class="active"</#if>>-->
                <#--<a href="#"><i class="iconfont icon-yewuguanli"></i> <span class="nav-label">业务设置 </span>-->
                <#--<span class="fa arrow"></span></a>-->
                <#--<ul class="nav nav-second-level">-->
                <#--<li <#if tab == "sourceConfig">class="active"</#if>><a class="J_menuItem" href="<@c.rootPath/>/admin/config/source"><i class="iconfont icon-shujuyuanguanli"></i>巡查数据源设置</a>-->
                <#--</li>-->
                <#--<li <#if tab == "functionConfig">class="active"</#if>><a class="J_menuItem" href="#"><i class="iconfont icon-gongneng"></i>巡查功能配置</a>-->
                <#--</li>-->
                <#--</ul>-->
                <#--</li>-->
                </ul>
            </div>
        </nav>
        <!--左侧导航结束-->
        <!--右侧部分开始-->
        <div id="page-wrapper" class="gray-bg dashbard-1">
            <div class="row border-bottom">
                <nav class="navbar navbar-fixed-top" role="navigation" style="margin-bottom: 0">
                    <div style="width: auto!important;" class="navbar-header">
                        <a style="display: none" class="navbar-minimalize minimalize-styl-2 btn btn-primary "
                           href="#"><i class="fa fa-bars"></i> </a>
                        <a href="#" class="top-logo">
                            <i style="color: #fff;font-size: 22px" class="fa fa-users"
                               aria-hidden="true"></i>
                        </a>

                    </div>
                    <ul class="nav navbar-top-links navbar-left">
                        <li class="dropdown hidden-xs">
                            <a href="<@c.rootPath/>/admin/dashboard"
                               style="padding-left: 25px;padding-right: 25px;font-size: 16px;color: #fff"
                               class="right-sidebar-toggle" aria-expanded="false">
                                管理控制台
                            </a>
                        </li>
                    </ul>
                    <ul class="nav navbar-top-links navbar-right">
                        <li class="dropdown hidden-xs">
                            <a href="<@c.rootPath/>/user/torivermap" style="font-size: 16px;" class="right-sidebar-toggle" aria-expanded="false">
                                <i style="color: #fff" class="fa fa-reply"></i> 返回首页
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
            <div class="row J_mainContent" id="content-main">
                <#nested />
            </div>
            <div class="footer">
                <div class="pull-right">
                    &copy;浙大万维科技有限公司 All Right Reserved.&nbsp&nbsp&nbsp<a target="_blank"
                                                                          href="http://www.zjugis.com/">关于我们</a></a>
                </div>
            </div>
        </div>
    </div>
    </@c.admin>
</#macro>

<!-- jsTree 无限扩展树结构 宏 -->
<#macro areaTree children parent>
    <#if children?? && children?size gt 0>
    <ul>
        <#list children as child>
            <li style="font-size: 16px; color: #0a6aa1" data-jstree='${child.toString()}'>
            ${child.name!}
                    <@areaTree children=child.children parent=child/>
            </li>
        </#list>
    </ul>
    </#if>
</#macro>



<#macro single title=""tab="${tab}" import="" footer =true>
    <@c.html title="${title}" import="jquery,bootstrap,fontawesome,${import},iconfont,layout.css">
        <#nested />

    </@c.html>
</#macro>

