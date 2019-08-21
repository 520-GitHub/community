package life.majiang.community.dto;/*
 *网络传输包dto
 * 登录授权需要用到的数据资料实体类bean
 */

import lombok.Data;
//www.projectLombok.org/features/all 官网使用方法
// 使用@Data自动生成set get方法
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
