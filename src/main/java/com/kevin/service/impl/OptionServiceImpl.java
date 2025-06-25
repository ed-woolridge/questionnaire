package com.kevin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kevin.entity.Option;
import com.kevin.service.OptionService;
import com.kevin.mapper.OptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author woolridge
* @description 针对表【option】的数据库操作Service实现
* @createDate 2025-06-24 15:01:10
*/
@Service
public class OptionServiceImpl extends ServiceImpl<OptionMapper, Option>
    implements OptionService{

    @Autowired
    private OptionMapper optionMapper;

    @Override
    public void create(Option option) {
        optionMapper.insert(option);
    }

    @Override
    public void update(Option option) {
        optionMapper.updateById(option);
    }

    @Override
    public void delete(Long id) {
        optionMapper.deleteById(id);
    }

    @Override
    public List<Option> listByQuestionId(Long questionId) {
        return optionMapper.selectList(new QueryWrapper<Option>().eq("question_id", questionId));
    }

}




