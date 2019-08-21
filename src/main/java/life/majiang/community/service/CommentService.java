package life.majiang.community.service;/*
 *
 */

import life.majiang.community.dto.CommentDTO;
import life.majiang.community.enums.CommentTypeEnum;
import life.majiang.community.enums.NotificationEnum;
import life.majiang.community.enums.NotificationStatusEnum;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import life.majiang.community.mapper.*;
import life.majiang.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @program: community
 * @description:回复校验类
 * @author: Mr.Chen
 * @create: 2019-07-31 16:22
 **/
@Service
public class CommentService {

    @Autowired(required = false)
    private CommentMapper commentMapper;

    @Autowired(required = false)
    private CommentExtMapper commentExtMapper;

    @Autowired(required = false)
    private QuestionMapper questionMapper;

    @Autowired(required = false)
    private QuestionExtMapper questionExtMapper;

    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired(required = false)
    private NotificationMapper notificationMapper;

    @Transactional//事务注解
    public void insert(Comment comment, User commentator) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_AND_COMMENT_NOT_FOUND);
        }

        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        //枚举。区分评论的是问题还是回复评论
        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            //回复评论
            Comment CComment = commentMapper.selectByPrimaryKey(comment.getParentId());

            if (CComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }

            //获取问题
            Question question = questionMapper.selectByPrimaryKey(CComment.getParentId());
            //如果问题为空则反馈页面该问题不存在
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }

//            回复评论进行显示
            commentMapper.insert(comment);

            //增加评论数
            Comment parentComment = new Comment();
            parentComment.setId(comment.getParentId());
            parentComment.setCommentCount(1);
            commentExtMapper.incCommentCount(parentComment);

//           回复评论 创建通知 ctrl+alt+m 快速创建方法
            createNotify(comment, CComment.getCommentator(),
                    commentator.getName(), question.getTitle(),
                    NotificationEnum.REPLY_COMMENT, question.getId());

        } else {
            //获取问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            //如果问题为空则反馈页面该问题不存在
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            //问题不为空则向数据库插入评论且回复数+1
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionExtMapper.incCommentCount(question);

//            回复问题创建通知 ctrl+alt+m 快速创建方法
            createNotify(comment, question.getCreator(),
                    commentator.getName(), question.getTitle(),
                    NotificationEnum.REPLY_QUESTION, question.getId());
        }

    }

    // 回复通知属性
    private void createNotify(Comment comment, Long receiver, String notifierName,
                              String outerTitle, NotificationEnum notificationType,
                              Long outerId) {
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(notificationType.getType());
        notification.setOuterid(outerId);
        notification.setNotifier(comment.getCommentator());
        notification.setStatus(NotificationStatusEnum.UNREAD.getstatus());
        notification.setReceiver(receiver);
        notification.setOuterTitle(outerTitle);
        notification.setNotifierName(notifierName);
        notificationMapper.insert(notification);
    }

    //一级、二级 评论处理
    public List<CommentDTO> listByTargetId(Long id, CommentTypeEnum type) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(type.getType());

        // 评论倒序：  按照 gmt_create：创建时间 的 desc：倒序 排序
        commentExample.setOrderByClause("gmt_create desc");

        List<Comment> commentList = commentMapper.selectByExample(commentExample);
        if (commentList.size() == 0) {
            return new ArrayList<>();
        }
        //java8新特性。Lambda表达式
        //获取去重的评论人
        Set<Long> commentators = commentList.stream().map(comment ->
                comment.getCommentator()).collect(Collectors.toSet());
        //转换为userIds
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(commentators);

        //获取评论人id，并转换为Map
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);

        Map<Long, User> userMap =
                users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));
        //转换comment为commentDTO
        List<CommentDTO> commentDTOS =
                commentList.stream().map(comment -> {
                    CommentDTO commentDTO = new CommentDTO();
                    BeanUtils.copyProperties(comment, commentDTO);
                    commentDTO.setUser(userMap.get(comment.getCommentator()));
                    return commentDTO;
                }).collect(Collectors.toList());
        return commentDTOS;
    }
}
