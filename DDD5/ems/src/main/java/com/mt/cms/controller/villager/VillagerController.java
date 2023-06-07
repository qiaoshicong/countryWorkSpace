package com.mt.cms.controller.villager;

import com.mt.cms.dto.villager.VillagerEditDto;
import com.mt.cms.entity.villager.Villager;
import com.mt.cms.service.villager.VillagerService;
import com.mt.common.core.annotation.ApiPageParam;
import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@Api(tags = "村民信息")
@RestController
@RequestMapping("/api/cms/villager/Villager")
@CrossOrigin(allowCredentials = "true")
public class VillagerController {
    private static Logger logger = LoggerFactory.getLogger(VillagerController.class);
    @Autowired
    private VillagerService villagerService;

    /**
     * 根据分页参数查询村民信息集合
     *
     * @param pageDTO 分页条件
     */
    @PreAuthorize("hasAuthority('cms:villager:Villager:view')")
    @ApiOperation("分页查询村民信息")
    @ApiPageParam
    @PostMapping("/findVillagers")
    public PageResultDTO findVillagers(@RequestBody PageDTO pageDTO) {
        return this.villagerService.findVillagers(pageDTO);
    }


    /**
     * 创建新的村民信息
     */
    @PreAuthorize("hasAuthority('cms:villager:Villager:add')")
    @ApiOperation("创建新的村民信息")
    @PostMapping("/createVillager")
    public VillagerEditDto createVillager() {
        VillagerEditDto villagerEditDto = new VillagerEditDto();
        villagerEditDto.setVillager(new Villager());

        this.prepareVillagerEditDto(villagerEditDto);
        return villagerEditDto;
    }

    private void prepareVillagerEditDto(VillagerEditDto villagerEditDto) {
    }

    /**
     * 新增保存村民信息
     *
     * @param villager 实体对象
     */
    @PreAuthorize("hasAuthority('cms:villager:Villager:add')")
    @ApiOperation("新增保存村民信息")
    @PostMapping("/saveVillager")
    public Villager saveVillager(@RequestBody Villager villager) {
        return this.villagerService.saveVillager(villager);
    }

    /**
     * 修改保存村民信息
     *
     * @param villager 实体对象
     */
    @PreAuthorize("hasAuthority('cms:villager:Villager:update')")
    @ApiOperation("修改保存村民信息")
    @PostMapping("/updateVillager")
    public Villager updateVillager(@RequestBody Villager villager) {
        return this.villagerService.updateVillager(villager);
    }

    /**
     * 根据ID删除村民信息
     *
     * @param villagerId ID
     */
    @PreAuthorize("hasAuthority('cms:villager:Villager:remove')")
    @ApiOperation("根据ID删除村民信息")
    @PostMapping("/deleteVillager")
    public void deleteVillager(@RequestParam Long villagerId) {
        this.villagerService.deleteVillager(villagerId);
    }
}

