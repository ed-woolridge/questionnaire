package com.kevin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kevin.entity.Question;
import com.kevin.service.QuestionService;
import com.kevin.mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author woolridge
* @description 针对表【question】的数据库操作Service实现
* @createDate 2025-06-24 15:01:57
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService{

    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public String create(Question question) {
        int result = questionMapper.insert(question);

        if (result > 0){
            return "success";
        }else {
            return "fail";
        }
    }

    @Override
    public void update(Question question) {
        questionMapper.updateById(question);
    }

    @Override
    public void delete(Long id) {
        questionMapper.deleteById(id);
    }

    @Override
    public List<Question> listByQuestionnaireId(Long questionnaireId) {
        return questionMapper.selectList(new QueryWrapper<Question>().eq("questionnaire_id", questionnaireId));
    }

    @Override
    public String getTypeById(Long questionId) {
        Question question = questionMapper.selectById(questionId);
        return question != null ? question.getType() : null;
    }
}




