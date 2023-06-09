package com.mt.common.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mt.common.core.web.PageParam;
import com.mt.common.system.entity.DictionaryData;

import java.util.List;
import java.util.Map;

/**
 * 字典项服务类
 * Created by wangfan on 2020-03-14 11:29:04
 */
public interface DictionaryDataService extends IService<DictionaryData> {

    /**
     * 关联分页查询
     */
    List<DictionaryData> listPage(PageParam<DictionaryData> page);

    /**
     * 关联查询所有
     */
    List<DictionaryData> listAll(Map<String, Object> page);

    /**
     * 根据字典代码查询字典项
     */
    List<DictionaryData> listByDictCode(String dictCode);

    /**
     * 根据字典代码和字典项名称查询字典项
     */
    DictionaryData listByDictCodeAndName(String dictCode, String name);

}
