package life.majiang.community.dto;/*
 *
 */

import life.majiang.community.model.User;
import lombok.Data;

/**
 * @program: community
 * @description:返回页面的评论属性
 * @author: Mr.Chen
 * @create: 2019-08-02 09:25
 **/
@Data
public class CommentDTO {

    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer likeCount;
    private Integer commentCount;//评论数
    private String content;
    private User user;


}
