package com.kevin.service;

import com.kevin.entity.Question;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author woolridge
* @description 针对表【question】的数据库操作Service
* @createDate 2025-06-24 15:01:57
*/
public interface QuestionService extends IService<Question> {

    String create(Question question);
    void update(Question question);
    void delete(Long id);
    List<Question> listByQuestionnaireId(Long questionnaireId);
    /**
     * 根据题目ID查询题型
     */
    String getTypeById(Long questionId);
}
