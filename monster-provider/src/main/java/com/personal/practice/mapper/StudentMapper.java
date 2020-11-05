package com.personal.practice.mapper;

import com.personal.practice.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Scope;

import java.util.List;

@Mapper
@Scope("prototype")
public interface StudentMapper  {

    boolean insert(@Param("student") Student student);

    List<Student> listStudent();
}
