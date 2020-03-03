var $$ = Dom7;

var App = new Framework7();

$$("#submit").on("click", function(){
    var user = JSON.stringify(App.form.convertToData("#form_data"));
    var userStr = "userStr=" + user;
    var url = '/signin'
    console.log(userStr)
    App.request.post(url, userStr, function(data){
        console.log(data);
        var datajson = JSON.parse(data);
        if(datajson.success == true){
            // document.cookie = "sessionID=" + datajson.sessionID;
            if(datajson.userKind == "1"){
                window.location.href="/home"
            }else{
                window.location.href="/storeadmin/home"
            }
        }else{
            App.dialog.alert(data.msg);
        }
    })
})

// Login Screen-Modal DOM events
$$('.login-screen').on('loginscreen:open', function (e) {
    console.log('Login screen open')
});
$$('.login-screen').on('loginscreen:opened', function (e) {
    console.log('Login screen opened')
});
$$('.login-screen').on('loginscreen:close', function (e) {
    console.log('Login screen close')
});
$$('.login-screen').on('loginscreen:closed', function (e) {
    console.log('Login screen closed')
});