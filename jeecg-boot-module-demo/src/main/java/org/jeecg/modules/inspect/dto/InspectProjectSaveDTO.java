package org.jeecg.modules.inspect.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.modules.inspect.entity.InspectProjectTarget;

import java.util.List;

/**
 * @Description: 方案保存DTO
 * @author: lhtao
 * @date: 2023年01月30日 13:47
 */
@ApiModel(value="InspectProjectSaveDTO对象", description="考察方案保存对象")
@Data
public class InspectProjectSaveDTO {

    @ApiModelProperty(value = "方案id")
    private String id;

    @ApiModelProperty(value = "指标集合")
    private List<InspectProjectTarget> targets;
}
