/*$(function () {
    $("#submituser").onclick
})*/

$(document).ready(function () {
    $("#submituser").click(function () {
        addUser();
    });
});

function testAlert() {
    alert("已经到了添加界面！")
}

function addUser() {
    var loginId = $("input[name='loginId']").val();
    var loginName = $("input[name='loginName']").val();
    var loginPassword = $("input[name='loginPassword']").val();
    var nickName = $("input[name='nickName']").val();
    var email = $("input[name='email']").val();
    $.ajax({
        cache: true,
        type: "POST",
        url: "add",
        contentType: "application/json;charset=UTF-8",
        dataType: "JSON",
        data: JSON.stringify({
            data: {
                "loginId": loginId,
                "loginName": loginName,
                "loginPassword": loginPassword,
                "nickName": nickName,
                "email": email
            }
        }),
        async: false,
        error: function (request) {
            alert("系统错误");
        },
        success: function (data) {
            if (data.code == 1) {
                alert(data.description);
            } else {
                alert(data.description);
            }
        }
    });
}