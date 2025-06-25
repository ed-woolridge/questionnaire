package com.kevin.service;

import com.kevin.entity.Option;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author woolridge
* @description 针对表【option】的数据库操作Service
* @createDate 2025-06-24 15:01:10
*/
public interface OptionService extends IService<Option> {

    void create(Option option);
    void update(Option option);
    void delete(Long id);
    List<Option> listByQuestionId(Long questionId);

}
