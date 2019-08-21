package life.majiang.community.dto;/*
 *网络传输包dto
 * GitHub需要获取的资料实体类bean
 */

import lombok.Data;
//www.projectLombok.org/features/all 官网使用方法
// 使用@Data自动生成set get方法
@Data
public class GitHubUser {
    private String name;//授权名称
    private long id;
    private String bio;//描述
    //fastJson可以知道了把下划线标识映射到驼峰的属性。网页显示avatar_url可以映射到avatarUrl
    private String avatarUrl;//头像地址

}
