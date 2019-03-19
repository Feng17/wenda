$(function () {
    $("#registerButton").click(function () {
        $.ajax({
            url: "/register",
            type: "post",
            data: {username: $("#registerUsername").val(),email:$("#registerEmail").val(), password: $("#registerPassword").val()},
            success: function (result) {
                if (result.msg == "ok") {
                    alert("激活邮件已发送，验证后就登录了")
                    $("#myModal").modal("hide");
                    $("#registerUsername").val("");
                    $("#registerEmail").val("");
                    $("#registerPassword").val("");
                    $("#registerError").text("");
                } else {
                    $("#registerError").text(result.msg);

                }
            }
        });
    });
    $("#loginButton").click(function () {
        $.ajax({
            url: "/signin",
            type: "post",
            data: {username: $("#loginUsername").val(), password: $("#loginPassword").val()},
            success: function (result) {
                if (result.msg) {
                    $("#loginError").text(result.msg);
                } else {
                    window.location.href = "/index";
                }
            }
        });
    });

    $("#modalClose").click(function () {
        $("#registerUsername").val("");
        $("#registerEmail").val("");
        $("#registerPassword").val("");
        $("#registerError").text("");
    });

});