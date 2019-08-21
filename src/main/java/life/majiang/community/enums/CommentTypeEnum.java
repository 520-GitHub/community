package life.majiang.community.enums;/*
 *
 */

/**
 * @program: community
 * @description:
 * @author: Mr.Chen
 * @create: 2019-07-31 13:49
 **/
public enum CommentTypeEnum {
    QUESTION(1),//问题
    COMMENT(2);//评论
    private Integer type;

    public static boolean isExist(Integer type) {
        for (CommentTypeEnum commentTypeEnum : CommentTypeEnum.values()) {
            if (commentTypeEnum.getType() == type) {
                return true;
            }
        }
        return false;
    }

    public Integer getType() {
        return type;
    }

    CommentTypeEnum(Integer type) {
        this.type = type;
    }

}
