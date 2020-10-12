package com.personal.practice.mapper.db;

import com.personal.practice.entity.Student;
import com.personal.practice.mapper.StudentMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class DbProvider {
    @Resource
    private StudentMapper studentMapper;


}
