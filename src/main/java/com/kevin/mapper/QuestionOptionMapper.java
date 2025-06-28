package com.kevin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kevin.entity.QuestionOption;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface QuestionOptionMapper extends BaseMapper<QuestionOption> {
    @Select("SELECT * FROM `question_option` WHERE question_id = #{questionId}")
    List<QuestionOption> selectByQuestionId(Long questionId);
}
