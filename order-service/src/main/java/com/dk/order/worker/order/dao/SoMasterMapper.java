package com.dk.order.worker.order.dao;

import com.dk.order.worker.order.entity.SoMaster;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * 订单表
 * @author scott lewis
 * @date 2019/05/27
 */
@Mapper
@Repository
public interface SoMasterMapper {

    /**
     * [新增]
     * @author scott lewis
     * @date 2019/05/27
     **/
    int insert(SoMaster soMaster);

    /**
     * [刪除]
     * @author scott lewis
     * @date 2019/05/27
     **/
    int delete(@Param("sysno") Long sysno);

    int updateSoStatusToCreateSuccess(@Param("sysno") Long sysno);

    int updateSoStatusToCreateFail(@Param("sysno") Long sysno);

    /**
     * [查詢] 根據主鍵 id 查詢
     * @author scott lewis
     * @date 2019/05/27
     **/
    SoMaster load(@Param("sysno") Long sysno);

    /**
     * [查詢] 分頁查詢
     * @author scott lewis
     * @date 2019/05/27
     **/
    List<SoMaster> pageList(@Param("offset") int offset,
                            @Param("pagesize") int pagesize);

    /**
     * [查詢] 分頁查詢 count
     * @author scott lewis
     * @date 2019/05/27
     **/
    int pageListCount(@Param("offset") int offset,
                      @Param("pagesize") int pagesize);

}
