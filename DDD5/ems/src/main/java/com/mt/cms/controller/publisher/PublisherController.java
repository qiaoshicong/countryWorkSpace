

package com.mt.cms.controller.publisher;

import com.mt.cms.entity.publisher.Publisher;
import com.mt.cms.service.publisher.PublisherService;
import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Api(tags = "发布者信息")
@RestController
@RequestMapping("/api/cms/publisher/Publisher")
@CrossOrigin(allowCredentials = "true")
public class PublisherController {
    private static Logger logger = LoggerFactory.getLogger(PublisherController.class);


    @Autowired
    private PublisherService publisherService;

    /**
     * 根据分页参数查询发布者信息集合
     *
     * @param pageDTO 分页条件
     */

    @PostMapping("/findPublishers")
    public PageResultDTO findPublishers(@RequestBody PageDTO pageDTO) {
        return this.publisherService.findPublishers(pageDTO);
    }

    /**
     * 根据ID查询指定的发布者信息
     *
     * @param publisherId Id
     */
    @PostMapping("/findPublisher")
    public Publisher findPublisher(@RequestParam Long publisherId) {
        return this.publisherService.findPublisher(publisherId);
    }


    /**
     * 新增保存发布者信息
     *
     * @param publisher 实体对象
     */
    @PostMapping("/savePublisher")
    public Publisher savePublisher(@RequestBody Publisher publisher) {
        return this.publisherService.savePublisher(publisher);
    }

    /**
     * 修改保存发布者信息
     *
     * @param publisher 实体对象
     */

    @PostMapping("/updatePublisher")
    public Publisher updatePublisher(@RequestBody Publisher publisher) {
        return this.publisherService.updatePublisher(publisher);
    }

    /**
     * 根据ID删除发布者信息
     *
     * @param publisherId ID
     */

    @PostMapping("/deletePublisher")
    public void deletePublisher(@RequestParam Long publisherId) {
        this.publisherService.deletePublisher(publisherId);
    }


}

