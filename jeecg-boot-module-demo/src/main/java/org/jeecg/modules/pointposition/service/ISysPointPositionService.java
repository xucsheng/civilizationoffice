package org.jeecg.modules.pointposition.service;

import org.jeecg.modules.pointposition.entity.SysPointPosition;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 点位信息表
 * @Author: jeecg-boot
 * @Date:   2023-01-30
 * @Version: V1.0
 */
public interface ISysPointPositionService extends IService<SysPointPosition> {
  void deleteLogic(List<String> ids);
}
