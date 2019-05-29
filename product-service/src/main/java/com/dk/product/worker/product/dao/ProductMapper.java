package com.dk.product.worker.product.dao;

import com.dk.product.worker.product.entity.Product;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * 商品sku
 * @author scott lewis
 * @date 2019/05/27
 */
@Mapper
@Repository
public interface ProductMapper {

    /**
     * [新增]
     * @author scott lewis
     * @date 2019/05/27
     **/
    int insert(Product product);

    /**
     * [刪除]
     * @author scott lewis
     * @date 2019/05/27
     **/
    int delete(@Param("sysno") Long sysno);

    /**
     * [查詢] 根據主鍵 sysno 查詢
     * @author scott lewis
     * @date 2019/05/27
     **/
    Product load(@Param("sysno") Long sysno);

    /**
     * [查詢] 分頁查詢
     * @author scott lewis
     * @date 2019/05/27
     **/
    List<Product> pageList(@Param("offset") int offset,
                           @Param("pagesize") int pagesize);

    /**
     * [查詢] 分頁查詢 count
     * @author scott lewis
     * @date 2019/05/27
     **/
    int pageListCount(@Param("offset") int offset,
                      @Param("pagesize") int pagesize);

}
