package com.kevin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kevin.common.Result;
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
    public Result create(Questionnaire questionnaire) {
        int result = questionnaireMapper.insert(questionnaire);
        if (result > 0) {
            return Result.success("问卷创建成功！");
        } else {
            return Result.error("问卷创建成功！");
        }
    }

    @Override
    public Result update(Questionnaire questionnaire) {
        int i = questionnaireMapper.updateById(questionnaire);
        if (i > 0) {
            return Result.success("问卷更新成功！");
        } else {
            return Result.error("问卷更新失败！");
        }
    }

    @Override
    public Result delete(Long id) {
        int i = questionnaireMapper.deleteById(id);
        if (i > 0) {
            return Result.success("问卷删除成功！");
        } else {
            return Result.error("问卷删除失败！");
        }
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




