package org.jeecg.modules.inspect.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.jeecg.modules.basedata.entity.InspectQuestions;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 考察管理-方案指标管理
 * @Author: jeecg-boot
 * @Date:   2023-01-30
 * @Version: V1.0
 */
@Data
@TableName(value = "inspect_project_target", autoResultMap = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="inspect_project_target对象", description="考察管理-方案指标管理")
public class InspectProjectTarget implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键id")
    private String id;

	/**方案id*/
	@Excel(name = "方案id", width = 15)
    @ApiModelProperty(value = "方案id")
    private String projectId;

    /**点位类型*/
    @Excel(name = "点位类型", width = 15)
    @ApiModelProperty(value = "点位类型")
    private String pointType;

	/**指标id*/
	@Excel(name = "指标id", width = 15)
    @ApiModelProperty(value = "指标id")
    private String targetId;

	/**针对方案特例化的考察标准*/
	@Excel(name = "针对方案特例化的考察标准", width = 15)
    @ApiModelProperty(value = "针对方案特例化的考察标准")
    private String inspectStandardProj;

	/**针对方案特例化的考察问题*/
	@Excel(name = "针对方案特例化的考察问题", width = 15)
    @ApiModelProperty(value = "针对方案特例化的考察问题")
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private InspectQuestions inspectQuestionsProj;

	/**逻辑删除 0未删除 1已删除*/
	@Excel(name = "逻辑删除 0未删除 1已删除", width = 15)
    @ApiModelProperty(value = "逻辑删除 0未删除 1已删除")
    private Integer delFlag;

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
}
