var webUtils = {};
webUtils.objNullResult = function (obj,type) {
    switch(type)
    {
        case "string":
            if (typeof (obj) ==="undefined"){
                return "";
            }else {
                return obj;
            }
            break;
        case "object":
            if (typeof (obj) ==="undefined"){
                return {};
            }else {
                return obj;
            }
            break;
        case "int":
            if (typeof (obj) ==="undefined"){
                return 0;
            }else {
                return obj;
            }
            break;
        case "array":
            if (typeof (obj) ==="undefined"){
                return [];
            }else {
                return obj;
            }
            break;
        default:
            return obj;
    }
};
webUtils.formaterHMS = function (value) {
    var hour = Math.floor(value/3600);
    var leave2=value%3600;
    var minutes=Math.floor(leave2/60);
    var leave3=leave2%60;
    var seconds=Math.round(leave3);
    return hour+"时"+minutes+"分"+seconds+"秒";
};
webUtils.formaterYMD = function (value) {
    var date = new Date(parseInt(value))
    return date.getFullYear() + "年" + (date.getMonth() + 1) + "月" + date.getDate() + "日";
}
webUtils.IEVersion = function () {
    var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
    var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1; //判断是否IE浏览器
    var isEdge = userAgent.indexOf("Windows NT 6.1; Trident/7.0;") > -1 && !isIE; //判断是否IE的Edge浏览器
    if(isIE)
    {
        var reIE = new RegExp("MSIE (\\d+\\.\\d+);");
        reIE.test(userAgent);
        var fIEVersion = parseFloat(RegExp["$1"]);
        if(fIEVersion < 10)
        { return "-IE10";}
        else
        { return "+IE10"}//IE版本过低
    }
    else if(isEdge)
    {
        return "Edge";
    }
    else
    {
        return "-1";//非IE
    }
}