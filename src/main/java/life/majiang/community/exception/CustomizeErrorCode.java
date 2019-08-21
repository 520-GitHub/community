package life.majiang.community.exception;/*
 *
 */

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND(2001, "该问题已沉没大海，看看别的问题吧！！！"),
    QUESTION_AND_COMMENT_NOT_FOUND(2002, "未选中任何问题或评论！！！"),
    NO_LOGIN(2003, "当前操作需要登录，请登录后重试"),
    SYSTEM_ERROR(2004, "服务器过热啦！！！稍后再试试吧~~~"),
    TYPE_PARAM_WRONG(2005, "评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006, "该回复已沉没大海，看看别的别论吧！！！"),
    COMMENT_IS_EMPTY(2007, "回复不能为空呦！！！"),
    READ_NOTIFICATION_FAIL(2008, "非法操作！！！"),
    NOTIFICATION_NOT_FOUND(2008, "消息不翼而飞了！！！"),
    FILE_UPLOAD_FAIL(2009, "图片上传失败"),
    ;

    private String message;
    private Integer Code;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return Code;
    }

    CustomizeErrorCode(Integer code, String message) {
        this.Code = code;
        this.message = message;
    }


}