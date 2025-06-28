package com.kevin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kevin.entity.QuestionOption;

import java.util.List;

/**
* @author woolridge
* @description 针对表【option】的数据库操作Service
* @createDate 2025-06-24 15:01:10
*/
public interface QuestionOptionService extends IService<QuestionOption> {

    void create(QuestionOption questionOption);
    void update(QuestionOption questionOption);
    void delete(Long id);
    List<QuestionOption> listByQuestionId(Long questionId);
    QuestionOption getById(Long id);

}
