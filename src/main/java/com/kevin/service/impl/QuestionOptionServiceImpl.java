package com.kevin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kevin.entity.QuestionOption;
import com.kevin.service.QuestionOptionService;
import com.kevin.mapper.QuestionOptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author woolridge
* @description 针对表【option】的数据库操作Service实现
* @createDate 2025-06-24 15:01:10
*/
@Service
public class QuestionOptionServiceImpl extends ServiceImpl<QuestionOptionMapper, QuestionOption>
    implements QuestionOptionService {

    @Autowired
    private QuestionOptionMapper questionOptionMapper;

    @Override
    public void create(QuestionOption questionOption) {
        questionOptionMapper.insert(questionOption);
    }

    @Override
    public void update(QuestionOption option) {
        questionOptionMapper.updateById(option);
    }

    @Override
    public void delete(Long id) {
        questionOptionMapper.deleteById(id);
    }

    @Override
    public List<QuestionOption> listByQuestionId(Long questionId) {
        return questionOptionMapper.selectByQuestionId(questionId);
    }

    @Override
    public QuestionOption getById(Long id) {
        return questionOptionMapper.selectById(id);
    }

}




