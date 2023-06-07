package com.mt.cms.service.villager.impl;

import com.mt.cms.dao.villager.VillagerDao;
import com.mt.cms.entity.villager.Villager;
import com.mt.cms.service.villager.VillagerService;
import com.mt.common.core.exception.BusinessException;
import com.mt.common.core.web.BaseService;
import com.mt.common.core.web.base.BaseEntity;
import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class VillagerServiceBean extends BaseService implements VillagerService {

    private static Logger logger = LogManager.getLogger();

    @Autowired
    private VillagerDao villagerDao;

    @Resource
    private RedisTemplate<String, List<Villager>> redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 根据分页参数查询村民信息集合
     *
     * @param pageDTO 分页条件
     */
    @Override
    public PageResultDTO findVillagers(PageDTO pageDTO) {
        pageDTO.setStartIndex((pageDTO.getCurrentPage() - 1) * pageDTO.getPageSize());
        this.validateFindVillagers(pageDTO);
        List<Villager> villagerDTOS = this.villagerDao.findVillagers(pageDTO);
        Long totalCount = this.villagerDao.findVillagerTotalCount(pageDTO);

        PageResultDTO pageResultDTO = new PageResultDTO();
        pageResultDTO.setTotalCount(totalCount);
        pageResultDTO.setDatas(villagerDTOS);

        return pageResultDTO;
    }

    /**
     * 新增村民信息
     *
     * @param villager 实体对象
     */
    @Override
    public Villager saveVillager(Villager villager) {
        this.validateSaveVillager(villager);
        this.setSavePulicColumns(villager);
        Long rows = this.villagerDao.saveVillager(villager);
        if (rows != 1) {
            String error = "新增保存村民信息出错，数据库应该返回1,但返回了 " + rows;
            throw new BusinessException(error);
        }
        return villager;
    }

    /**
     * 更新村民信息
     *
     * @param villager 实体对象
     */
    @Override
    public Villager updateVillager(Villager villager) {
        this.validateUpdateVillager(villager);
        Long rows = this.villagerDao.updateVillager(villager);
        if (rows != 1) {
            String error = "修改保存村民信息出错，数据库应该返回1,但返回了 " + rows + ",数据可能被删除";
            throw new BusinessException(error);
        }
        return villager;
    }

    /**
     * 根据ID删除村民信息
     *
     * @param villagerId ID
     */
    @Override
    public void deleteVillager(Long villagerId) {
        this.validateDeleteVillager(villagerId);

        Map<Class<? extends BaseEntity>, EntityUsage> entityUsageMap = this.checkForeignEntity(Villager.class, villagerId);
        if (entityUsageMap != null && entityUsageMap.size() > 0) {
            StringBuilder errors = new StringBuilder();
            errors.append("计划删除的数据正在被以下数引用\n");
            for (EntityUsage entityUsage : entityUsageMap.values()) {
                errors.append("\t").append(entityUsage.getEntityLabel()).append("\n");
                for (Map.Entry<Long, String> entry : entityUsage.getUsageIdNames().entrySet()) {
                    errors.append("\t\t").append(entry.getKey()).append("\t").append(entry.getValue()).append("\n");
                }
            }
            errors.append("，不能删除，请检查处理后再删除");
            throw new BusinessException(errors.toString());
        }
        Long rows = this.villagerDao.deleteVillager(villagerId);
        if (rows != 1) {
            String error = "删除村民信息出错，数据可能已经被删除";
            throw new BusinessException(error);
        }
    }

    private void validateFindVillagers(PageDTO pageDTO) {
    }


    private void validateSaveVillager(Villager villager) {
        //不为空判断
        if (villager.getEid() != null || villager.getCreatorId() != null || villager.getCreateDatetime() != null) {
            throw new BusinessException("非法请求");
        }
    }

    private void validateUpdateVillager(Villager villager) {
        //不为空判断
        if (villager.getEid() == null) {
            throw new BusinessException("唯一标识不能为空");
        }
        //是否存在判断
        if (this.villagerDao.findVillagerTotalCount(PageDTO.create(Villager.FIELD_ID, villager.getEid())) == 0) {
            throw new BusinessException("修改的村民信息 " + villager.getName() + " 不存在，修改失败，请重试或联系管理员");
        }
    }

    private void validateDeleteVillager(Long villagerId) {
    }

    @Override
    public boolean canDownloadAttachment(String formName, Long id) {
        return true;
    }
}
