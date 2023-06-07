package com.mt.cms.service.villager;

import com.mt.cms.entity.villager.Villager;
import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;

public interface VillagerService {
    /**
     * 根据分页参数查询村民信息集合
     *
     * @param pageDTO 分页条件
     */
    public PageResultDTO findVillagers(PageDTO pageDTO);

    /**
     * 新增村民信息
     *
     * @param villager 实体对象
     */
    public Villager saveVillager(Villager villager);

    /**
     * 更新村民信息
     *
     * @param villager 实体对象
     */
    public Villager updateVillager(Villager villager);

    /**
     * 根据ID删除村民信息
     *
     * @param villagerId ID
     */
    public void deleteVillager(Long villagerId);
}
