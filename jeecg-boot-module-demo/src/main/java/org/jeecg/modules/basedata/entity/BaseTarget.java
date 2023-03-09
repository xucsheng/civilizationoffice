package org.jeecg.modules.basedata.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.io.JsonStringEncoder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 基础数据管理-指标库
 * @Author: jeecg-boot
 * @Date:   2023-01-29
 * @Version: V1.0
 */
@Data
@TableName(value = "base_target", autoResultMap = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="base_target对象", description="基础数据管理-指标库")
public class BaseTarget implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键id")
    private String id;

	/**指标类型*/
	@Excel(name = "指标类型", width = 15)
    @ApiModelProperty(value = "指标类型")
    private String targetType;

	/**指标名称*/
	@Excel(name = "指标名称", width = 15)
    @ApiModelProperty(value = "指标名称")
    private String targetName;

	/**考查方式 1输入数量 2两项选择 3三项选择*/
	@Excel(name = "考查方式 1输入数量 2两项选择 3三项选择", width = 15)
    @ApiModelProperty(value = "考查方式 1输入数量 2两项选择 3三项选择")
    private Integer inspectType;

	/**考察单位*/
	@Excel(name = "考察单位", width = 15)
    @ApiModelProperty(value = "考察单位")
    private String inspectUnit;

	/**考察标准*/
	@Excel(name = "考察标准", width = 15)
    @ApiModelProperty(value = "考察标准")
    private String inspectStandard;

	/**考察问题*/
	@Excel(name = "考察问题", width = 15)
    @ApiModelProperty(value = "考察问题")
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private InspectQuestions inspectQuestions;

	/**排序*/
	@Excel(name = "排序", width = 15)
    @ApiModelProperty(value = "排序")
    private Integer sort;

    /**使用状态*/
    @Excel(name = "使用状态 0启用 1禁用", width = 15)
    @ApiModelProperty(value = "使用状态 0启用 1禁用")
    private Integer useState;

	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;

	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

	/**修改人*/
    @ApiModelProperty(value = "修改人")
    private String updateBy;

	/**修改时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "逻辑删除 0未删除 1已删除")
    private Integer delFlag;
}
