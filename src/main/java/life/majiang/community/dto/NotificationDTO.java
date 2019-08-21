package life.majiang.community.dto;

import life.majiang.community.model.User;
import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private Long outerid;
    private Integer status;

    private String notifierName;
    private User notifier;

    private String outerTitle;
    private String typeName;
    private Integer type;
}
