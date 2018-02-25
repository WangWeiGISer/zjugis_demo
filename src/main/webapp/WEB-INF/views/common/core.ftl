<!-- root path -->
<#macro rootPath>${springMacroRequestContext.getContextPath()}</#macro>

<!-- add script -->
<#macro script name>
<script src="<@rootPath/>/static/${name}" type="text/javascript"></script>
</#macro>
<#macro mscript name main>
<script src="<@rootPath/>/static/${name}" data-main="${main}" type="text/javascript"></script>
</#macro>

<!-- add style -->
<#macro style name>
<link href="<@rootPath/>/static/${name}" type="text/css" media="screen" rel="stylesheet"/>
</#macro>

<!-- add media style -->
<#macro mstyle name media>
<link href="<@rootPath/>/static/${name}" type="text/css" media="${media}" rel="stylesheet"/>
</#macro>

<#macro icon name sizes>
<link href="<@rootPath/>/static/${name}" type="image/png" rel="icon" sizes="${sizes}"/>
</#macro>



<!-- base html -->
<#macro html title="" import="" theme="">
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width">
    <title>${title}</title>
    <link rel="shortcut icon" type="image/x-icon" href="<@rootPath/>/static/favicon.ico">
    <#if import??>
        <#list import?split(",") as lib>
            <#if lib?trim != "">
                <#switch lib>
                    <#case "echart">
                        <@script name="compents/echart/echarts.min.js"></@script>
                        <@script name="compents/echart/ecStat.js"></@script>
                        <#break/>
                    <#case "jquery">
                        <@script name="js/jquery/jquery-2.2.4.min.js"></@script>
                        <#break />
                    <#case "bootstrap">
                        <@script name="compents/bootstrap/js/bootstrap.min.js"></@script>
                        <@style name="compents/bootstrap/css/bootstrap.min.css"></@style>
                        <#break/>
                    <#case "iconfont">
                        <@style name="compents/iconfont/iconfont.css"></@style>
                        <#break/>
                    <#case "layout">
                        <@style name="css/theme/layout.css"></@style>
                        <#break/>
                    <#case "fontawesome">
                        <@style name="compents/font-awesome/css/font-awesome.css"></@style>
                        <#break/>
                    <#case "vue">
                        <@script name="compents/vue/vue.js"></@script>
                        <#break/>
                    <#case "layui">
                        <@style name="compents/layui/css/layui.css"></@style>
                        <@script name="compents/layui/layui.all.js"></@script>
                        <#break/>
                    <#case "formValidation">
                        <@style name="compents/formValidation/formValidation.css"></@style>
                        <@script name="compents/formValidation/formValidation.js"></@script>
                        <@script name="compents/formValidation/bootstrap.js"></@script>
                        <#break/>
                    <#case "easyui">
                        <@style name="compents/easyui/themes/metro/easyui.css"></@style>
                        <@script name="compents/easyui/jquery.easyui.min.js"></@script>
                        <@script name="compents/easyui/locale/easyui-lang-zh_CN.js"></@script>
                        <#break/>
                    <#case "ztree">
                        <@style name="compents/ztree/metroStyle.css"></@style>
                        <@script name="compents/ztree/jquery.ztree.all.min.js"></@script>
                        <#break/>
                    <#case "toastr">
                        <@style name="compents/toastr/toastr.min.css"></@style>
                        <@script name="compents/toastr/toastr.min.js"></@script>
                        <#break/>
                    <#case "select2">
                        <@style name="compents/ztree/metroStyle.css"></@style>
                        <@script name="compents/ztree/jquery.ztree.all.min.js"></@script>
                        <#break/>
                    <#default>
                        <#if lib?contains("js")>
                            <@script name="js/theme/${lib}"></@script>
                        </#if>
                        <#if lib?contains("css")>
                            <@style name="css/theme/${lib}"></@style>
                        </#if>
                        <#break />
                </#switch>
            </#if>
        </#list>
    </#if>
</head>
<body style="height: 100%">
    <#nested />
</body>

</#macro>

<#macro admin title="" import="" theme="">
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width">
    <title>${title}</title>
    <link rel="shortcut icon" href="<@rootPath/>/static/favicon.ico">
    <#if import??>
        <#list import?split(",") as lib>
            <#if lib?trim != "">
                <#switch lib>
                    <#case "jquery">
                        <@script name="js/jquery/jquery-2.2.4.min.js"></@script>
                        <#break />
                    <#case "md5">
                        <@script name="compents/md5/jQuery.md5.js"></@script>
                        <#break />
                    <#case "wow">
                        <@script name="compents/wow/wow.min.js"></@script>
                        <@style name="compents/wow/animate.css"></@style>
                        <#break/>
                    <#case "bootstrap">
                        <@script name="compents/bootstrap/js/bootstrap.min.js"></@script>
                        <@style name="compents/bootstrap/css/bootstrap.min.css"></@style>
                        <#break/>
                    <#case "awesome">
                        <@style name="compents/awesome/font-awesome.min.css"></@style>
                        <#break/>
                    <#case "layui">
                        <@style name="compents/layui/css/layui.css"></@style>
                        <@script name="compents/layui/layui.all.js"></@script>
                        <#break/>
                    <#case "metisMenu">
                        <@script name="compents/metisMenu/jquery.metisMenu.js"></@script>
                        <#break/>
                    <#case "vue">
                        <@script name="compents/vue/vue.js"></@script>
                        <#break/>
                    <#case "jsTree">
                        <@style name="compents/jsTree/style.min.css"></@style>
                        <@script name="compents/jsTree/jstree.min.js"></@script>
                        <#break/>
                    <#case "ztree">
                        <@style name="compents/ztree/metroStyle.css"></@style>
                        <@script name="compents/ztree/jquery.ztree.all.min.js"></@script>
                        <#break/>
                    <#case "slimscroll">
                        <@script name="compents/slimscroll/jquery.slimscroll.min.js"></@script>
                        <#break/>
                    <#case "pace">
                        <@script name="compents/pace/pace.min.js"></@script>
                        <#break/>
                    <#case "iconfont">
                        <@style name="compents/iconfont_admin/iconfont.css"></@style>
                        <#break/>
                    <#case "select2">
                        <@style name="compents/select2/css/select2.min.css"></@style>
                        <@script name="compents/select2/select2.full.min.js"></@script>
                        <@script name="compents/select2/i18n/zh-CN.js"></@script>
                        <#break/>
                    <#case "echart">
                        <@script name="compents/echart/echarts.min.js"></@script>
                        <@script name="compents/echart/macarons.js"></@script>
                        <#break/>
                    <#default>
                        <#if theme??>
                            <#if lib?contains("js")>
                                <@script name="js/theme/${theme}/${lib}"></@script>
                            </#if>
                            <#if lib?contains("css")>
                                <@style name="css/theme/${theme}/${lib}"></@style>
                            </#if>
                        </#if>
                        <#break />
                </#switch>
            </#if>
        </#list>
    </#if>
</head>
<body class="fixed-nav fixed-sidebar full-height-layout gray-bg" style="overflow:hidden">
    <#nested />
</body>
</html>
</#macro>
