package life.majiang.community.dto;/*
 *
 */

import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import lombok.Data;

import javax.naming.ldap.Rdn;
import java.util.List;

/**
 * @program: community
 * @description:
 * @author: Mr.Chen
 * @create: 2019-07-31 13:09
 **/
@Data
public class ResultDTO<T> {
    private Integer code;//响应
    private String message;//提示
    //范型。返会类型List、User都有可能
    private T data;

    public static ResultDTO errorOf(Integer code, String message) {

        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizeErrorCode errorCode) {
        return errorOf(errorCode.getCode(), errorCode.getMessage());
    }

    public static ResultDTO errorOf(CustomizeException ex) {
        return errorOf(ex.getCode(), ex.getMessage());
    }

    public static ResultDTO okOf() {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        return resultDTO;
    }

    public static<T> ResultDTO okOf(T t) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");

        resultDTO.setData(t);
        return resultDTO;
    }


}
