package com.kevin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kevin.entity.Questionnaire;
import com.kevin.service.QuestionnaireService;
import com.kevin.mapper.QuestionnaireMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author woolridge
* @description 针对表【questionnaire】的数据库操作Service实现
* @createDate 2025-06-24 15:02:38
*/
@Service
public class QuestionnaireServiceImpl extends ServiceImpl<QuestionnaireMapper, Questionnaire> implements QuestionnaireService {

    @Autowired
    private QuestionnaireMapper questionnaireMapper;

    @Override
    public void create(Questionnaire questionnaire) {
        questionnaireMapper.insert(questionnaire);
    }

    @Override
    public void update(Questionnaire questionnaire) {
        questionnaireMapper.updateById(questionnaire);
    }

    @Override
    public void delete(Long id) {
        questionnaireMapper.deleteById(id);
    }

    @Override
    public List<Questionnaire> list() {
        return questionnaireMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public Questionnaire getById(Long id) {
        return questionnaireMapper.selectById(id);
    }
}




