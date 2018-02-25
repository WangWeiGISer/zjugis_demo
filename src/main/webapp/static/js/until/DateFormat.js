/**
 * Created by wangwei on 2017/11/8.
 */
//返回 01-12 的月份值
function getMonth(date){
    var month = "";
    month = date.getMonth() + 1; //getMonth()得到的月份是0-11
    if(month<10){
        month = "0" + month;
    }
    return month;
}


//返回01-30的日期
function getDay(date){
    var day = "";
    day = date.getDate();
    if(day<10){
        day = "0" + day;
    }
    return day;
}


//返回小时
function getHours(date){
    var hours = "";
    hours = date.getHours();
    if(hours<10){
        hours = "0" + hours;
    }
    return hours;
}
//返回分
function getMinutes(date){
    var minute = "";
    minute = date.getMinutes();
    if(minute<10){
        minute = "0" + minute;
    }
    return minute;
}
//返回秒
function getSeconds(date){
    var second = "";
    second = date.getSeconds();
    if(second<10){
        second = "0" + second;
    }
    return second;
}

/**
 * 返回yyyy-MM-dd格式日期（一）
 * @param longTypeDate
 * @returns {string}
 */
function dateFormat_1(longTypeDate){
    var dateType = "";
    var date = new Date();
    date.setTime(longTypeDate);
    dateType += date.getFullYear();   //年
    dateType += "-" + getMonth(date); //月
    dateType += "-" + getDay(date);   //日
    return dateType;
}
/**
 * 返回yyyy-MM-dd格式日期（二）：
 * @param longTypeDate
 * @returns {string}
 */
function dateFormat_2(longTypeDate){
    var dateType = "";
    var date = new Date();
    date.setTime(longTypeDate);
    dateType = date.getFullYear()+"-"+getMonth(date)+"-"+getDay(date);//yyyy-MM-dd格式日期
    return dateType;
}
/**
 * 返回yyyy-MM-dd 00:00:00格式日期（二）
 * @param longTypeDate
 * @returns {string}
 */
function datetimeFormat_2(longTypeDate){
    var datetimeType = "";
    var date = new Date();
    date.setTime(longTypeDate);
    datetimeType = date.getFullYear()+"-"+getMonth(date)+"-"+getDay(date)+"&nbsp;"+getHours(date)+":"+getMinutes(date)+":"+getSeconds(date);//yyyy-MM-dd 00:00:00格式日期
    return datetimeType;
}

/**
 * 返回yyyy-MM-dd 00:00格式日期（3）
 * @param longTypeDate
 * @returns {string}
 */
function datetimeFormat_3(longTypeDate){
    var datetimeType = "";
    var date = new Date();
    date.setTime(longTypeDate);
    datetimeType = date.getFullYear()+"-"+getMonth(date)+"-"+getDay(date)+"&nbsp;"+getHours(date)+":"+getMinutes(date);//yyyy-MM-dd 00:00格式日期
    return datetimeType;
}

/**
 * 返回yyyy-MM-dd 00:00:00格式日期（一）
 * @param longTypeDate
 * @returns {string}
 */
function datetimeFormat_1(longTypeDate){
    var datetimeType = "";
    var date = new Date();
    date.setTime(longTypeDate);
    datetimeType+= date.getFullYear();   //年
    datetimeType+= "-" + getMonth(date); //月
    datetimeType += "-" + getDay(date);   //日
    datetimeType+= "&nbsp;&nbsp;" + getHours(date);   //时
    datetimeType+= ":" + getMinutes(date);      //分
    datetimeType+= ":" + getSeconds(date);      //分
    return datetimeType;
}

