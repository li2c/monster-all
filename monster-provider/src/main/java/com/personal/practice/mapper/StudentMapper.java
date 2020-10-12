package com.personal.practice.mapper;

import com.personal.practice.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Transactional;

@Mapper
@Scope("prototype")
public interface StudentMapper  {

    boolean insert(@Param("student") Student student);
}
