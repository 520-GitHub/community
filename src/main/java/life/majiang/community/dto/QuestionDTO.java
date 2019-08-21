package life.majiang.community.dto;/*
 *
 */

import life.majiang.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private Long id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creator;//发布人id
    private Integer viewCount;//浏览
    private Integer commentCount;//回复
    private Integer likeCount;//收藏
    private User user;


}
