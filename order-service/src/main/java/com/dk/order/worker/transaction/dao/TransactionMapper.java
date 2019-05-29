package com.dk.order.worker.transaction.dao;

import com.dk.order.worker.transaction.entity.Transaction;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 事务记录表
 * @author scott lewis
 * @date 2019/05/28
 */
@Mapper
@Repository
public interface TransactionMapper {

    /**
     * [新增]
     * @author scott lewis
     * @date 2019/05/28
     **/
    int insert(Transaction transaction);

    void updateBranchTransactionToCommitted(@Param("xid") String xid,@Param("branchId") Long branchId);

    void updateBranchTransactionToRollbacked(@Param("xid") String xid,@Param("branchId") Long branchId);


    /**
     * [查詢] 根據主鍵 id 查詢
     * @author scott lewis
     * @date 2019/05/28
     **/
    Transaction load(@Param("xid") String xid,@Param("branchId") Long branchId);


}
