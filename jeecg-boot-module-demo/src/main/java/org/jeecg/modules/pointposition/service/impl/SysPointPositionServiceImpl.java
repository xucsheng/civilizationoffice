package org.jeecg.modules.pointposition.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.jeecg.modules.pointposition.entity.SysPointPosition;
import org.jeecg.modules.pointposition.mapper.SysPointPositionMapper;
import org.jeecg.modules.pointposition.service.ISysPointPositionService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: 点位信息表
 * @Author: jeecg-boot
 * @Date:   2023-01-30
 * @Version: V1.0
 */
@Service
public class SysPointPositionServiceImpl extends ServiceImpl<SysPointPositionMapper, SysPointPosition> implements ISysPointPositionService {

    @Override
    public void deleteLogic(List<String> ids) {
        LambdaUpdateWrapper<SysPointPosition> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.in(SysPointPosition::getId,ids);
        lambdaUpdateWrapper.set(SysPointPosition::getDelFlag,1);
        this.update(lambdaUpdateWrapper);
    }
}
