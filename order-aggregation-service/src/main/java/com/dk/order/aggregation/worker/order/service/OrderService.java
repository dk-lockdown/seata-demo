package com.dk.order.aggregation.worker.order.service;

import com.alibaba.fastjson.JSON;
import com.dk.foundation.engine.baseentity.StandResponse;
import com.dk.order.aggregation.worker.order.action.AllocateInventoryTccAction;
import com.dk.order.aggregation.worker.order.action.CreateOrderTccAction;
import com.dk.order.aggregation.worker.order.remote.order.OrderSvc;
import com.dk.order.aggregation.worker.order.remote.order.entity.SoItem;
import com.dk.order.aggregation.worker.order.remote.order.entity.SoMaster;
import com.dk.order.aggregation.worker.order.remote.product.ProductSvc;
import com.dk.order.aggregation.worker.order.remote.product.req.AllocateInventoryReq;
import com.dk.order.aggregation.worker.order.req.CreateOrderReq;
import com.dk.foundation.common.SnowflakeIdGenerator;
import com.dk.foundation.engine.exception.BusinessException;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    CreateOrderTccAction createOrderTccAction;

    @Autowired
    AllocateInventoryTccAction allocateInventoryTccAction;

    @Resource
    OrderSvc orderSvc;

    @Resource
    ProductSvc productSvc;

    public List<Long> createSo(CreateOrderReq req) throws BusinessException {
        if(req.shoppingProducts==null||req.shoppingProducts.size()==0) {
            throw new BusinessException("购买的商品不能为空");
        }


        return null;
    }

    @GlobalTransactional
    public List<Long> createSo(boolean atMode) throws BusinessException {

        List<Long> soSysNos = new ArrayList<>();
        List<SoMaster> soMasters = new ArrayList<>();
        SoMaster soMaster = new SoMaster();
        Long sysno = SnowflakeIdGenerator.getInstance().nextId();
        soMaster.setSysNo(sysno);
        soMaster.setAppid(sysno.toString());
        soMaster.setBuyerUserSysNo(1L);
        soMaster.setSellerCompanyCode("SC001");
        soMaster.setReceiveDivisionSysNo(110105L);
        soMaster.setReceiveAddress("朝阳区长安街001号");
        soMaster.setReceiveZip("000001");
        soMaster.setReceiveContact("斯密达");
        soMaster.setReceiveContactPhone("18728828296");
        soMaster.setStockSysNo(1L);
        soMaster.setPaymentType(1);
        soMaster.setAppid("dk-order");

        /**
         *
         * todo 从req里面构造订单商品明细
         * 这里仅测试，就写死了
         */

        SoItem soItem = new SoItem();
        soItem.setSoSysNo(sysno);
        soItem.setProductSysNo(1L);
        soItem.setProductName("刺力王");
        soItem.setCostPrice(new BigDecimal(200));
        soItem.setOriginalPrice(new BigDecimal(232));
        soItem.setDealPrice(new BigDecimal(215));
        soItem.setQuantity(2);
        List<SoItem> soItems = new ArrayList<>();
        soItems.add(soItem);

        soMaster.setSoAmt(soItem.getDealPrice().multiply(new BigDecimal(soItem.getQuantity())));
        soMaster.setSoItems(soItems);
        soMasters.add(soMaster);
        soSysNos.add(sysno);


        List<AllocateInventoryReq> reqs = new ArrayList<>();
        AllocateInventoryReq allocateInventoryReq = new AllocateInventoryReq();
        allocateInventoryReq.setProductSysNo(1L);
        allocateInventoryReq.setQty(soItem.getQuantity());
        reqs.add(allocateInventoryReq);

        if(atMode) {
            StandResponse result1 = orderSvc.insert(soMasters);
            if (result1.getCode() != 200) {
                throw new BusinessException(result1.getMsg());
            }

            StandResponse result2 = productSvc.allocateInventory(reqs);
            if (result2.getCode() != 200) {
                throw new BusinessException(result2.getMsg());
            }
        }
        else {
            boolean result1 = createOrderTccAction.prepare(null, soMasters, JSON.toJSONString(soSysNos));
            boolean result2 = allocateInventoryTccAction.prepare(null, JSON.toJSONString(reqs));
            if (result1 && result2) {
                return soSysNos;
            }
        }
        return soSysNos;
    }
}
