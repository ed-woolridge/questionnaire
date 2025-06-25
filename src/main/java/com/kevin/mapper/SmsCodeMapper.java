package com.kevin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kevin.entity.SmsCode;
import org.apache.ibatis.annotations.Mapper;

/**
 * 短信验证码Mapper接口
 */
@Mapper
public interface SmsCodeMapper extends BaseMapper<SmsCode> {
} 