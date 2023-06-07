package com.mt.cms.dao.publisher;

import com.mt.cms.entity.publisher.Publisher;
import com.mt.common.core.web.base.PageDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "publisherDao")
public interface PublisherDao {

    /**
    * 根据分页参数查询发布者信息集合
    *
    * @param pageDTO 分页条件
    */
    public List<Publisher> findPublishers(PageDTO pageDTO);

    /**
    * 查询全部发布者信息集合
    *
    */
    public List<Publisher> findAllPublishers();


    /**
    * 根据分页参数查询发布者信息集合的数量
    *
    * @param pageDTO 分页条件
    */
    public Long findPublisherTotalCount(PageDTO pageDTO);

    /**
    * 根据ID查询指定的发布者信息
    *
    * @param publisherId Id
    */
    public Publisher findPublisher(@Param("publisherId") Long publisherId);



    /**
    * 新增发布者信息
    *
    * @param publisher 实体对象
    */
    public Long savePublisher(Publisher publisher);

    /**
    * 更新发布者信息
    *
    * @param publisher 实体对象
    */
    public Long updatePublisher(Publisher publisher);

    /**
    * 根据ID删除发布者信息
    *
    * @param publisherId ID
    */
    public Long deletePublisher(@Param("publisherId") Long publisherId);

    Publisher findPublisherByName(String nickname);
}
