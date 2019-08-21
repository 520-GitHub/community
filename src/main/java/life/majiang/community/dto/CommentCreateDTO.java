package life.majiang.community.dto;/*
 *
 */

import lombok.Data;

/**
 * @program: community
 * @description:评论回复用到的属性
 * @author: Mr.Chen
 * @create: 2019-07-31 10:27
 **/
@Data
public class CommentCreateDTO {
    private Long parentId;//父类ID
    private String content;//回复内容
    private Integer type;//1代表问题2代表回复

}
