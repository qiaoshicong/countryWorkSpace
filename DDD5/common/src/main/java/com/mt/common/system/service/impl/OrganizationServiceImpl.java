package com.mt.common.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mt.common.core.web.PageParam;
import com.mt.common.system.entity.Organization;
import com.mt.common.system.mapper.OrganizationMapper;
import com.mt.common.system.service.OrganizationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 组织机构服务实现类
 * Created by wangfan on 2020-03-14 11:29:04
 */
@Service
public class OrganizationServiceImpl extends ServiceImpl<OrganizationMapper, Organization> implements OrganizationService {

    @Override
    public List<Organization> listPage(PageParam<Organization> page) {
        return baseMapper.listPage(page);
    }

    @Override
    public List<Organization> listAll(Map<String, Object> page) {
        return baseMapper.listAll(page);
    }

}
