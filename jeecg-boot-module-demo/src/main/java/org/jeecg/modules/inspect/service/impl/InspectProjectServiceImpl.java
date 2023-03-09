package org.jeecg.modules.inspect.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.modules.inspect.entity.InspectProject;
import org.jeecg.modules.inspect.entity.InspectProjectTarget;
import org.jeecg.modules.inspect.mapper.InspectProjectMapper;
import org.jeecg.modules.inspect.mapper.InspectProjectTargetMapper;
import org.jeecg.modules.inspect.service.IInspectProjectService;
import org.jeecg.modules.inspect.service.IInspectProjectTargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description: 考察管理-方案管理
 * @Author: jeecg-boot
 * @Date:   2023-01-30
 * @Version: V1.0
 */
@Service
public class InspectProjectServiceImpl extends ServiceImpl<InspectProjectMapper, InspectProject> implements IInspectProjectService {

    @Autowired
    private IInspectProjectTargetService projectTargetService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void copy(String id) {
        //保存方案主体
        InspectProject project = getById(id);
        project.setId(null).setCreateTime(null).setCreateBy(null).setUpdateBy(null).setUpdateTime(null).setProjectName(project.getProjectName() + "_副本");
        save(project);
        //保存方案指标
        List<InspectProjectTarget> targets = projectTargetService.list(new LambdaQueryWrapper<InspectProjectTarget>().eq(InspectProjectTarget::getProjectId, id).eq(InspectProjectTarget::getDelFlag, CommonConstant.DEL_FLAG_0));
        targets.forEach(t -> {
            t.setId(null).setCreateBy(null).setCreateTime(null).setUpdateBy(null).setUpdateTime(null).setProjectId(project.getId());
        });
        projectTargetService.saveBatch(targets);
    }
}
