var App = new Framework7();

var $$ = Dom7;

$$('#submit').on('click', function () {
    var user = JSON.stringify(App.form.convertToData('#form_data'));
    var userJSON = JSON.parse(user)
    var regExp = /\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/;
    if (userJSON.userMail == null || userJSON.userMail == "") {
        App.dialog.alert("邮箱不能为空");
    } else if (!regExp.test(userJSON.userMail)) {
        App.dialog.alert("无效邮箱");
    } else if (userJSON.userPasswd != userJSON.userPasswdRe) {
        App.dialog.alert("密码输入不一致");
    } else if(!(/^1[3456789]\d{9}$/.test(userJSON.userPhone))){
        App.dialog.alert("无效手机号");
    } else {
        delete userJSON.userPasswdRe
        user = JSON.stringify(userJSON);
        var userStr = "userStr=" + user;
        var url = '/signup'
        App.request.post(url, userStr, function (data) {
            if(JSON.parse(data).success == true){
                App.dialog.alert("注册成功");
                if(JSON.parse(data).userKind == "1"){
                    window.location.href="/home"
                }else{
                    window.location.href="/storeadmin/home"
                }
            }else{
                App.dialog.alert(JSON.parse(data).msg);
            }
        });
    }
})