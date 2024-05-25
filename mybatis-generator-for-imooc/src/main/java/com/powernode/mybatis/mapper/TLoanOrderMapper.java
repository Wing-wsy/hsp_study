package com.powernode.mybatis.mapper;

import com.powernode.mybatis.pojo.TLoanOrder;
import java.util.List;

public interface TLoanOrderMapper {
    int deleteByPrimaryKey(Long loanId);

    int insert(TLoanOrder record);

    TLoanOrder selectByPrimaryKey(Long loanId);

    List<TLoanOrder> selectAll();

    int updateByPrimaryKey(TLoanOrder record);
}