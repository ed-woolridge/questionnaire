package com.kevin.service;

import com.kevin.common.Result;
import com.kevin.entity.Questionnaire;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
* @author woolridge
* @description 针对表【questionnaire】的数据库操作Service
* @createDate 2025-06-24 15:02:38
*/
public interface QuestionnaireService extends IService<Questionnaire> {

    Result create(Questionnaire questionnaire);
    Result update(Questionnaire questionnaire);
    Result delete(Long id);
    List<Questionnaire> list();
    Questionnaire getById(Long id);

}
