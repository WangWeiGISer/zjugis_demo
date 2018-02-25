 <@c.html title="南京市长江河道信息管理系统" import="jquery,layui,fontawesome,toastr">
    <@c.style name="css/login/login.css"></@c.style>
	 <@c.style name="compents/icheck/square/blue.css"></@c.style>
	 <@c.script name="compents/icheck/icheck.js"></@c.script>
    <@c.script name="js/login/login.js"></@c.script>

 <div class="title" style="height: 80px;width: 100%">
     <div id="layerControlBtn" style="float: left;padding-top: 28px;padding-left: 29px;">
         <a>
             <img src="<@c.rootPath/>/static/images/ico_loginlogo.png">
         </a>
     </div>
     <div style="color: white;font-size: 32px;padding-left: 20px;padding-top: 30px;font-weight: 600;font-family: FZLTDHK-GBK1-0;float: left">长江南京段堤防划界确权管理信息系统</div>
 </div>
 <div class="content" id="content" style="text-align: center">
     <div class="login-content" style="height: 455px;width: 421px;margin-left: 57%;padding-top:4%">
         <div class="login-title" style="color: white;font-size: 44px;font-weight:100;font-family: FZLTCXHJW-GB1-0;">
             <a>
                 <img src="<@c.rootPath/>/static/images/pic_welcome.png">
             </a>
         </div>
         <div class="login-ic" style="height: 108px;margin-top: 41px;z-index: 200;position: relative">
             <a>
                 <img src="<@c.rootPath/>/static/images/avatar.png" style="border: 5px solid #fff;border-radius: 100px;background: #fff">
             </a>
         </div>
         <div class="login-input" style="color: white;width: 421px;height: 245px;background: white;margin-top: -56px;border-radius: 10px;padding-top: 75px">
             <div style="height: 48px;width: 338px;border: solid 2px #eeeeee;  border-radius: 10px;margin-left: 42px">
                 <div style="width: 58px;height: 33px;border-right:solid 2px #eeeeee;padding-top: 15px;float: left">
                     <a> <img src="<@c.rootPath/>/static/images/ico_loginuser.png"></a>
                 </div>
                 <input id="userName"  style="float: left;height: 45px;width: 210px;border: 0;text-align: center;" name="loginName" required lay-verify="required" placeholder="账号" autocomplete="off">
             </div>
             <div style="height: 48px;width: 338px;border: solid 2px #eeeeee;  border-radius: 10px;margin-left: 42px;margin-top: 12px">
                 <div style="width: 58px;height: 33px;border-right:solid 2px #eeeeee;padding-top: 15px;float: left">
                     <a> <img src="<@c.rootPath/>/static/images/ico_loginpassword.png"></a>
                 </div>
                 <input id="password" type="password" style="float: left;height: 45px;width: 210px;border: 0;text-align: center;" name="password" required lay-verify="required" placeholder="密码" autocomplete="off">
             </div>
             <#--<div style="height: 20px;width: 380px;margin-top: 14px;">
                 <div style="float: left;width: 20px;margin-left: 42px">
                     <input type="radio" name="iCheck">
                 </div>
                 <div style="font-size: 15px;color: #666666;text-align: left;float: left;margin-left: 5px">记住密码</div>
                 <div style="font-size: 15px;color: #0084ff;float: right;">忘记密码</div>
             </div>-->
             <div style="width:  340px;height:48px;margin-left: 42px;">
                 <button id="loginSubmit" style="width:  340px;height:48px;border-radius: 10px;background-color: #0084ff;margin-top: 22px;color: white;font-size: 19px;">登 录</button>
             </div>
         </div>
     </div>
 </div>
 <div class="foot" style="height: 16px;color: white; font-size: 13px;bottom: 31px;text-align:center">版权所有© 2017 浙江浙大万维科技有限公司</div>
<script>
    document.getElementById("content").style.height = $(document).height()- 126+ "px";
</script>
</@c.html>