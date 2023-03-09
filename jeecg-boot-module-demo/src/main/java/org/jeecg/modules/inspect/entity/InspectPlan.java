package org.jeecg.modules.inspect.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 考察管理-考察计划管理
 * @Author: jeecg-boot
 * @Date:   2023-01-30
 * @Version: V1.0
 */
@Data
@TableName("inspect_plan")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="inspect_plan对象", description="考察管理-考察计划管理")
public class InspectPlan implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键id")
    private java.lang.String id;
	/**计划名称*/
	@Excel(name = "计划名称", width = 15)
    @ApiModelProperty(value = "计划名称")
    private java.lang.String planName;
	/**考察开始时间*/
	@Excel(name = "考察开始时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "考察开始时间")
    private java.util.Date inspectBeginTime;
    /**考察结束时间*/
    @Excel(name = "考察开始时间", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "考察结束时间")
    private java.util.Date inspectEndTime;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;
	/**逻辑删除 0未删除 1已删除*/
	@Excel(name = "逻辑删除 0未删除 1已删除", width = 15)
    @ApiModelProperty(value = "逻辑删除 0未删除 1已删除")
    private java.lang.Integer delFlag;
	/**修改人*/
    @ApiModelProperty(value = "修改人")
    private java.lang.String updateBy;
	/**考察方案id*/
	@Excel(name = "考察方案id", width = 15)
    @ApiModelProperty(value = "考察方案id")
    private java.lang.String projectId;
    /**逻辑删除 0未删除 1已删除*/
    @Excel(name = "计划状态 0未进行 1进行中", width = 15)
    @ApiModelProperty(value = "计划状态 0未进行 1进行中")
    private java.lang.Integer planState;
}
