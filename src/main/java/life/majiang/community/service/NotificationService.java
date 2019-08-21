package life.majiang.community.service;

import life.majiang.community.dto.NotificationDTO;
import life.majiang.community.dto.PaginationDTO;
import life.majiang.community.enums.NotificationEnum;
import life.majiang.community.enums.NotificationStatusEnum;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import life.majiang.community.mapper.NotificationMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Notification;
import life.majiang.community.model.NotificationExample;
import life.majiang.community.model.User;
import life.majiang.community.model.UserExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired(required = false)
    private NotificationMapper notificationMapper;

    @Autowired(required = false)
    private UserMapper userMapper;

    public PaginationDTO list(Long id, Integer page, Integer size) {

        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();

        Integer totalPage;

        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(id);
        Integer totalCount = (int) notificationMapper.countByExample(notificationExample);

        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        if (page < 1) {
            page = 1;
        }

        if (page > totalPage) {
            page = totalPage;
        }

        paginationDTO.setPagination(totalPage, page);

        Integer offset = size * (page - 1);
        NotificationExample example = new NotificationExample();
        example.createCriteria()
                .andReceiverEqualTo(id);

        // 评论倒序：  按照 gmt_create：创建时间 的 desc：倒序 排序
        example.setOrderByClause("gmt_create desc");

        List<Notification> notifications =
                notificationMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));

        Set<Long> disUserId = notifications.stream().map(notify -> notify.getNotifier()).collect(Collectors.toSet());
        List<Long> UserIds = new ArrayList<>(disUserId);
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(UserIds);
        List<User> users = userMapper.selectByExample(userExample);
        users.stream().collect(Collectors.toMap(u -> u.getId(), u -> u));

        List<NotificationDTO> notificationList = new ArrayList<>();
        paginationDTO.setData(notificationList);

        if (notifications.size() == 0) {
            return paginationDTO;
        }

        for (Notification notification : notifications) {

            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setTypeName(NotificationEnum.nameOfType(notification.getType()));
            notificationList.add(notificationDTO);
        }

        return paginationDTO;
    }

    public Long unreadCount(Long id) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(id)
                .andStatusEqualTo(NotificationStatusEnum.UNREAD.getstatus());
        return notificationMapper.countByExample(notificationExample);
    }

    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification == null) {
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!notification.getReceiver().equals(user.getId())) {
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }

        notification.setStatus(NotificationStatusEnum.READ.getstatus());
        notificationMapper.updateByPrimaryKey(notification);


        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        notificationDTO.setTypeName(NotificationEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }
}
