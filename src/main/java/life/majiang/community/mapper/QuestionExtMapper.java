package life.majiang.community.mapper;

import life.majiang.community.dto.QuestionQueryDTO;
import life.majiang.community.model.Question;

import java.util.List;

public interface QuestionExtMapper {

    int incView(Question record);

    //根据QuestionExtMapper.xml中设置的sql语句进行评论数+1处理
    int incCommentCount(Question record);

    //    相关问题栏
    List<Question> selectRelated(Question question);

    Integer countBySearch(QuestionQueryDTO questionQueryDTO);


    List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);
}