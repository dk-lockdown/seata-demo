package com.dk.product.worker.product.dao;

import com.dk.product.worker.product.entity.Inventory;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商品库存
 * @author scott lewis
 * @date 2019/05/27
 */
@Mapper
@Repository
public interface InventoryMapper {

    /**
     * [新增]
     * @author scott lewis
     * @date 2019/05/27
     **/
    int insert(Inventory inventory);

    /**
     * [刪除]
     * @author scott lewis
     * @date 2019/05/27
     **/
    int delete(@Param("id") int id);

    int allocateInventory(@Param("productSysNo") Long productSysNo,@Param("qty") int qty);

    int cancelAllocateInventory(@Param("productSysNo") Long productSysNo,@Param("qty") int qty);

    /**
     * [查詢] 根據主鍵 id 查詢
     * @author scott lewis
     * @date 2019/05/27
     **/
    Inventory load(@Param("id") int id);

    /**
     * [查詢] 分頁查詢
     * @author scott lewis
     * @date 2019/05/27
     **/
    List<Inventory> pageList(@Param("offset") int offset,
                             @Param("pagesize") int pagesize);

    /**
     * [查詢] 分頁查詢 count
     * @author scott lewis
     * @date 2019/05/27
     **/
    int pageListCount(@Param("offset") int offset,
                      @Param("pagesize") int pagesize);

}
