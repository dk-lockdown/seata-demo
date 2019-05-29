package com.dk.order.worker.order.dao;

import com.dk.order.worker.order.entity.SoItem;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * 订单明细表
 * @author scott lewis
 * @date 2019/05/27
 */
@Mapper
@Repository
public interface SoItemMapper {

    /**
     * [新增]
     * @author scott lewis
     * @date 2019/05/27
     **/
    int insert(SoItem soItem);

    /**
     * [刪除]
     * @author scott lewis
     * @date 2019/05/27
     **/
    int delete(@Param("sysno") Long sysno);

    /**
     * [查詢] 根據主鍵 id 查詢
     * @author scott lewis
     * @date 2019/05/27
     **/
    SoItem load(@Param("sysno") Long sysno);

    /**
     * [查詢] 分頁查詢
     * @author scott lewis
     * @date 2019/05/27
     **/
    List<SoItem> pageList(@Param("offset") int offset,
                          @Param("pagesize") int pagesize);

    /**
     * [查詢] 分頁查詢 count
     * @author scott lewis
     * @date 2019/05/27
     **/
    int pageListCount(@Param("offset") int offset,
                      @Param("pagesize") int pagesize);

}
