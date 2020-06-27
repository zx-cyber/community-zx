package life.majiang.community.service;

import life.majiang.community.DataTransferObject.PaginationDTO;
import life.majiang.community.DataTransferObject.QuestionDTO;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zx on 2020/4/19 20:24
 */
@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;

    //page是前端点击的第几页
    public PaginationDTO queryQuestions(Integer page, Integer size) {
        Integer offset = size * (page - 1);
        //位limit服务
        List<Question> questions = questionMapper.queryQuestions(offset, size);
        PaginationDTO pagination = new PaginationDTO();

        Integer totalCount = userMapper.count();

        List<QuestionDTO> questionDTOS = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.queryById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        pagination.setQuestions(questionDTOS);
        pagination.setPagination(totalCount,page,size);
        return pagination;
    }
}
