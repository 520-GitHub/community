/**
 * 问题评论
 */
function post() {

    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    // 调用封装评论
    commentTarget(questionId, content, 1);

}

/**
 * 二级评论
 */
function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-" + commentId).val();
    //     // 调用封装评论
    commentTarget(commentId, content, 2);
}

/**
 * 封装评论
 */
function commentTarget(targetId, content, type) {
//客户端校验回复不能为空
    if (!content || content.trim().length == 0) {
        alert("回复不能为空呦！！！");
        return;
    }

    var ajax = $.ajax({

        type: 'POST',
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            // 如果回复成功关掉回复框
            if (response.code == 200) {
                //回复成功刷新页面
                window.location.reload();
            } else {
                if (response.code == 2003) {//如果未登录则返回弹框询问是否登录
                    var isAccepted = confirm(response.message);
                    // 如果返回true跳转到登录页面进行登录
                    if (isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=7137fe0ce10b9e42685d&redirect_uri=http://localhost:8888/callback&scope=user&state=1");
                        // 向index.html页面传递key和value。
                        window.localStorage.setItem("closable", "1");
                    }
                } else {
                    alert(response.message)
                }
            }
        },
        dataType: "json"
    });
}


/**
 * 展开二级评论
 */

function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $('#comment-' + id);

    // 获取二级评论展开状态
    var collapse = e.getAttribute("data-collapse");
    if (collapse) {
        // 折叠二级评论
        comments.removeClass("in");
        //移除二级评论展开状态
        e.removeAttribute("data-collapse", "in");
        //折叠时，移除高亮
        e.classList.remove("active");
    } else {
        var subCommentContainer = $("#comment-" + id);

        // 获取子元素 最后一个元素是回复框
        if (subCommentContainer.children().length != 1) {
            // 展开二级评论
            comments.addClass("in");
            //标记二级评论展开状态
            e.setAttribute("data-collapse", "in");
            //展开时，添加高亮
            e.classList.add("active");
        } else {

            // 获取回复评论的内容的请求后再展开二级评论
            var json = $.getJSON("/comment/" + id, function (data) {

                // 遍历元素reverse()倒序
                $.each(data.data.reverse(), function (index, comment) {

                    //头像
                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-thumbnail",
                        "src": comment.user.avatarUrl
                    }));

                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.name
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "menu"
                    })).append($("<span/>", {
                        "class": "pull-right",
                        "html": moment(comment.gmtCreate).format('YYYY-MM-DD,h:mm:ss')
                    }));


                    //commentElement的子元素
                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments",
                    }).append(mediaElement);
                    // 拼接
                    subCommentContainer.prepend(commentElement);
                });
                // 展开二级评论
                comments.addClass("in");
                //标记二级评论展开状态
                e.setAttribute("data-collapse", "in");
                //展开 添加高亮
                e.classList.add("active");
            });
        }
    }
}

/**
 * 提问标签添加
 * @param value
 */
function selectTay(e) {
    var value = e.getAttribute("data-tag");

    var previous = $("#tag").val();
// 如果标签不存添加
    if (previous.indexOf(value) == -1) {
        if (previous) {
            $("#tag").val(previous + "," + value);
        } else {
            $("#tag").val(value);
        }
    }

}

function showSelectTag() {
        $("#select-tag").show();
}
