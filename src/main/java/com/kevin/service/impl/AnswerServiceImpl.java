package com.kevin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kevin.entity.Answer;
import com.kevin.service.AnswerService;
import com.kevin.mapper.AnswerMapper;
import org.springframework.stereotype.Service;

/**
* @author woolridge
* @description 针对表【answer】的数据库操作Service实现
* @createDate 2025-06-24 14:54:52
*/
@Service
public class AnswerServiceImpl extends ServiceImpl<AnswerMapper, Answer>
    implements AnswerService{

}




