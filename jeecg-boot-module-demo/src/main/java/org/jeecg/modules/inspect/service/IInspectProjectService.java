package org.jeecg.modules.inspect.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.inspect.entity.InspectProject;

/**
 * @Description: 考察管理-方案管理
 * @Author: jeecg-boot
 * @Date:   2023-01-30
 * @Version: V1.0
 */
public interface IInspectProjectService extends IService<InspectProject> {

    /**
     * 方案拷贝
     * @param id
     */
    void copy(String id);
}
