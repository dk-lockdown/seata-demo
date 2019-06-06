package com.dk.order.worker.order.service;

import com.alibaba.fastjson.JSON;
import com.dk.foundation.common.SnowflakeIdGenerator;
import com.dk.foundation.engine.baseentity.TccRequest;
import com.dk.foundation.engine.exception.BusinessException;
import com.dk.order.worker.order.dao.SoItemMapper;
import com.dk.order.worker.order.dao.SoMasterMapper;
import com.dk.order.worker.order.entity.SoItem;
import com.dk.order.worker.order.entity.SoMaster;
import com.dk.order.worker.transaction.dao.TransactionMapper;
import com.dk.order.worker.transaction.entity.Transaction;
import io.seata.spring.annotation.GlobalLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SoService {
    @Autowired
    SoMasterMapper soMasterMapper;

    @Autowired
    SoItemMapper soItemMapper;

    @Autowired
    TransactionMapper transactionMapper;

    @Transactional(rollbackFor = Throwable.class)
    public List<Long> createSo(TccRequest<List<SoMaster>> soMasters) throws BusinessException {
        /**
         * 防止服务悬挂
         */
        Transaction existTransaction = transactionMapper.load(soMasters.getXid(),soMasters.getBranchId());
        if(existTransaction!=null) {
            throw new BusinessException("该事务已经提交");
        }

        Transaction transaction = new Transaction();
        transaction.setXid(soMasters.getXid());
        transaction.setBranchId(soMasters.getBranchId());
        transaction.setArgsJson(JSON.toJSONString(soMasters.getData()));
        transaction.setState(1);
        transactionMapper.insert(transaction);


        List<Long> results = new ArrayList<>();
        if (soMasters.getData()!=null&&soMasters.getData().size()>0) {
            for (SoMaster soMaster : soMasters.getData()) {
                if (soMaster.getSoItems()!=null&&soMaster.getSoItems().size()>0) {
                    if (soMaster.getSysNo()==null) {
                        Long sysno = SnowflakeIdGenerator.getInstance().nextId();
                        soMaster.setSysNo(sysno);
                    }
                    soMaster.setStatus(0);
                    soMasterMapper.insert(soMaster);
                    for (SoItem soItem : soMaster.getSoItems()) {
                        soItemMapper.insert(soItem);
                    }
                    results.add(soMaster.getSysNo());
                }
            }
        }
        return results;
    }

    @Transactional(rollbackFor = Throwable.class)
    public void updateSoStatusToCreateSuccess(TccRequest<List<Long>> sysnos){
        transactionMapper.updateBranchTransactionToCommitted(sysnos.getXid(),sysnos.getBranchId());
        if(sysnos.getData()!=null&&sysnos.getData().size()>0) {
            for (Long sysno : sysnos.getData()) {
                soMasterMapper.updateSoStatusToCreateSuccess(sysno);
            }
        }
    }

    @Transactional(rollbackFor = Throwable.class)
    public void updateSoStatusToCreateFail(TccRequest<List<Long>> sysnos){
        /**
         * 允许空回滚
         */
        Transaction existTransaction = transactionMapper.load(sysnos.getXid(),sysnos.getBranchId());
        if(existTransaction==null) {
            Transaction transaction = new Transaction();
            transaction.setXid(sysnos.getXid());
            transaction.setBranchId(sysnos.getBranchId());
            transaction.setArgsJson(JSON.toJSONString(sysnos.getData()));
            /**
             * 已经回滚
             */
            transaction.setState(3);
            transactionMapper.insert(transaction);
            return;
        }

        transactionMapper.updateBranchTransactionToRollbacked(sysnos.getXid(),sysnos.getBranchId());
        if(sysnos.getData()!=null&&sysnos.getData().size()>0) {
            for (Long sysno : sysnos.getData()) {
                soMasterMapper.updateSoStatusToCreateFail(sysno);
            }
        }
    }

    public List<Long> createSo(List<SoMaster> soMasters) throws BusinessException {
        List<Long> results = new ArrayList<>();
        if (soMasters.size()>0) {
            for (SoMaster soMaster : soMasters) {
                if (soMaster.getSoItems()!=null&&soMaster.getSoItems().size()>0) {
                    if (soMaster.getSysNo()==null) {
                        Long sysno = SnowflakeIdGenerator.getInstance().nextId();
                        soMaster.setSysNo(sysno);
                    }
                    soMaster.setStatus(10);
                    soMasterMapper.insert(soMaster);
                    for (SoItem soItem : soMaster.getSoItems()) {
                        soItemMapper.insert(soItem);
                    }
                    results.add(soMaster.getSysNo());
                }
            }
        }
        return results;
    }
}
