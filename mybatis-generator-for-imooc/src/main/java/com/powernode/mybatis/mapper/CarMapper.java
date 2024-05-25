package com.powernode.mybatis.mapper;

import com.powernode.mybatis.pojo.Car;
import java.util.List;

public interface CarMapper {
    int deleteByPrimaryKey(Long loanId);

    int insert(Car record);

    Car selectByPrimaryKey(Long loanId);

    List<Car> selectAll();

    int updateByPrimaryKey(Car record);
}