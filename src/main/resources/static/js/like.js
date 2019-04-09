$(function () {
    var voteUpAnswer = $(".voteUpAnswer");
    voteUpAnswer.click(function () {
        var likeStatus = $(this).attr("liked");
        if (likeStatus > 0) {
            return
        }
        var answerId = $(this).attr("like-id");
        var dislikeStatus = $("#dislike-" + answerId).attr("disliked");
        var that = $(this);
        $.ajax({
            url: "/likeAnswer",
            type: "post",
            data: {answerId: answerId},
            success: function (result) {
                if (result.code == 0) {
                    if (dislikeStatus < 0) {
                        $("#dislike-" + answerId).removeClass("active");
                        $("#dislike-" + answerId).attr("disliked", "0");
                        that.find("span").text(result.msg);
                        that.addClass("active");
                        that.attr("liked", "1");
                    } else {
                        that.find("span").text(result.msg);
                        that.addClass("active");
                        that.attr("liked", "1");

                    }
                } else {
                    alert("出现错误");
                }
            }
        });
    });


    var voteDownAnswer = $(".voteDownAnswer");
    voteDownAnswer.click(function () {
        var dislikeStatus = $(this).attr("disliked");
        if (dislikeStatus < 0) {
            return
        }
        var answerId = $(this).attr("dislike-id");
        var likeStatus = $("#like-" + answerId).attr("liked");
        var that = $(this);
        $.ajax({
            url: "/dislikeAnswer",
            type: "post",
            data: {answerId: answerId},
            success: function (result) {
                if (result.code == 0) {
                    if (likeStatus > 0) {
                        $("#like-" + answerId).removeClass("active");
                        $("#like-" + answerId).find("span").text(result.msg);
                        $("#like-" + answerId).attr("liked", "0");
                        that.addClass("active");
                        that.attr("disliked", "-1");

                    } else {
                        that.addClass("active");
                        that.attr("disliked", "-1");

                    }
                } else {
                    alert("出现错误");
                }
            }
        });
    });


});