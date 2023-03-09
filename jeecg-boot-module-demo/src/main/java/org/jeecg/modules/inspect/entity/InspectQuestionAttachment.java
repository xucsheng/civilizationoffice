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
 * @Description: inspect_question_attachment
 * @Author: jeecg-boot
 * @Date:   2023-01-31
 * @Version: V1.0
 */
@Data
@TableName("inspect_question_attachment")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="inspect_question_attachment对象", description="inspect_question_attachment")
public class InspectQuestionAttachment implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键雪花ID*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键雪花ID")
    private String id;
	/**问题ID*/
	@Excel(name = "问题ID", width = 15)
    @ApiModelProperty(value = "问题ID")
    private String questionId;
	/**问题编码*/
	@Excel(name = "问题编码", width = 15)
    @ApiModelProperty(value = "问题编码")
    private String questionCode;
	/**考察计划ID*/
	@Excel(name = "考察计划ID", width = 15)
    @ApiModelProperty(value = "考察计划ID")
    private String inspectPlanId;
	/**考察方案ID*/
	@Excel(name = "考察方案ID", width = 15)
    @ApiModelProperty(value = "考察方案ID")
    private String inspectProjectId;
	/**考察指标ID*/
	@Excel(name = "考察指标ID", width = 15)
    @ApiModelProperty(value = "考察指标ID")
    private String inspectTargetId;
	/**图片附件URL*/
	@Excel(name = "图片附件URL", width = 15)
    @ApiModelProperty(value = "图片附件URL")
    private String attachmentUrl;
	/**0:正常；1：删除；*/
	@Excel(name = "0:正常；1：删除；", width = 15)
    @ApiModelProperty(value = "0:正常；1：删除；")
    private Integer delFlag;
}
