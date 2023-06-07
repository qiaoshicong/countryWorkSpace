package com.mt.cms.dao.villager;

import com.mt.cms.entity.villager.Villager;
import com.mt.common.core.web.base.PageDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "villagerDao")
public interface VillagerDao {

    /**
     * 根据分页参数查询村民信息集合
     *
     * @param pageDTO 分页条件
     */
    public List<Villager> findVillagers(PageDTO pageDTO);


    /**
     * 根据分页参数查询村民信息集合的数量
     *
     * @param pageDTO 分页条件
     */
    public Long findVillagerTotalCount(PageDTO pageDTO);

    /**
     * 新增村民信息
     *
     * @param villager 实体对象
     */
    public Long saveVillager(Villager villager);

    /**
     * 更新村民信息
     *
     * @param villager 实体对象
     */
    public Long updateVillager(Villager villager);

    /**
     * 根据ID删除村民信息
     *
     * @param villagerId ID
     */
    public Long deleteVillager(@Param("villagerId") Long villagerId);
}
