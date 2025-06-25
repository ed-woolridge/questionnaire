package com.kevin.mapper;

import com.kevin.entity.Answer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author woolridge
* @description 针对表【answer】的数据库操作Mapper
* @createDate 2025-06-24 14:54:52
* @Entity com.kevin.entity.Answer
*/
@Mapper
public interface AnswerMapper extends BaseMapper<Answer> {

}




