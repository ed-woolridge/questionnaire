package com.kevin.service;

import com.kevin.entity.Questionnaire;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author woolridge
* @description 针对表【questionnaire】的数据库操作Service
* @createDate 2025-06-24 15:02:38
*/
public interface QuestionnaireService extends IService<Questionnaire> {

    void create(Questionnaire questionnaire);
    void update(Questionnaire questionnaire);
    void delete(Long id);
    List<Questionnaire> list();
    Questionnaire getById(Long id);

}
