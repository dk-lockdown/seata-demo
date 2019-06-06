package com.dk.product.worker.product.service;

import com.alibaba.fastjson.JSON;
import com.dk.foundation.engine.baseentity.TccRequest;
import com.dk.foundation.engine.exception.BusinessException;
import com.dk.product.worker.product.dao.InventoryMapper;
import com.dk.product.worker.product.dao.ProductMapper;
import com.dk.product.worker.product.entity.Product;
import com.dk.product.worker.product.req.AllocateInventoryReq;
import com.dk.product.worker.transaction.dao.TransactionMapper;
import com.dk.product.worker.transaction.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductMapper productMapper;

    @Autowired
    InventoryMapper inventoryMapper;

    @Autowired
    TransactionMapper transactionMapper;

    public Product load(Long sysno) {
        return productMapper.load(sysno);
    }

    @Transactional(rollbackFor = Throwable.class)
    public void allocateInventory(TccRequest<List<AllocateInventoryReq>> reqs) throws BusinessException {
        /**
         * 防止服务悬挂
         */
        Transaction existTransaction = transactionMapper.load(reqs.getXid(),reqs.getBranchId());
        if(existTransaction!=null) {
            throw new BusinessException("该事务已经提交");
        }

        Transaction transaction = new Transaction();
        transaction.setXid(reqs.getXid());
        transaction.setBranchId(reqs.getBranchId());
        transaction.setArgsJson(JSON.toJSONString(reqs.getData()));
        transaction.setState(1);
        transactionMapper.insert(transaction);

        for (AllocateInventoryReq req : reqs.getData()) {
            int result = inventoryMapper.allocateInventory(req.getProductSysNo(),req.getQty());
            if(result<=0) {
                throw new BusinessException("为订单分配库存失败");
            }
        }
    }

    public void commitAllocateInventory(TccRequest<List<AllocateInventoryReq>> reqs) {
        transactionMapper.updateBranchTransactionToCommitted(reqs.getXid(),reqs.getBranchId());
    }

    @Transactional(rollbackFor = Throwable.class)
    public void cancelAllocateInventory(TccRequest<List<AllocateInventoryReq>> reqs) throws BusinessException {
        /**
         * 允许空回滚
         */
        Transaction existTransaction = transactionMapper.load(reqs.getXid(),reqs.getBranchId());
        if(existTransaction==null) {
            Transaction transaction = new Transaction();
            transaction.setXid(reqs.getXid());
            transaction.setBranchId(reqs.getBranchId());
            transaction.setArgsJson(JSON.toJSONString(reqs.getData()));
            /**
             * 已经回滚
             */
            transaction.setState(3);
            transactionMapper.insert(transaction);
            return;
        }
        transactionMapper.updateBranchTransactionToRollbacked(reqs.getXid(),reqs.getBranchId());

        /**
         * 按道理来将，只要一阶段成功，那么回滚时，订单分配的数量一定是够回滚的。
         */
        for (AllocateInventoryReq req : reqs.getData()) {
            inventoryMapper.cancelAllocateInventory(req.getProductSysNo(),req.getQty());
        }
    }

    @Transactional(rollbackFor = Throwable.class)
    public void allocateInventory(List<AllocateInventoryReq> reqs) throws BusinessException {
        for (AllocateInventoryReq req : reqs) {
            int result = inventoryMapper.allocateInventory(req.getProductSysNo(),req.getQty());
            if(result<=0) {
                throw new BusinessException("为订单分配库存失败");
            }
        }
    }
}
