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
import org.jeecg.common.system.base.entity.JeecgEntity;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 考察问题
 * @Author: jeecg-boot
 * @Date:   2023-01-31
 * @Version: V1.0
 */
@Data
@TableName("inspect_question")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="inspect_question对象", description="inspect_question")
public class InspectQuestion extends JeecgEntity implements Serializable {
    private static final long serialVersionUID = 1L;
	/**问题编号*/
	@Excel(name = "问题编号", width = 15)
    @ApiModelProperty(value = "问题编号")
    private String questionCode;
	/**问题名称*/
	@Excel(name = "问题名称", width = 15)
    @ApiModelProperty(value = "问题名称")
    private String questionName;
	/**问题状态(0：初始化、1：问题上报、2：处置审核、3：处置、4、无需处理、5、结案)*/
	@Excel(name = "问题状态(0：初始化、1：问题上报、2：处置审核、3：处置、4、无需处理、5、结案)", width = 15)
    @ApiModelProperty(value = "问题状态(0：初始化、1：问题上报、2：处置审核、3：处置、4、无需处理、5、结案)")
    private Integer questionStatus;
	/**问题分类*/
	@Excel(name = "问题分类", width = 15)
    @ApiModelProperty(value = "问题分类")
    private Integer questionType;
	/**考察点位ID*/
	@Excel(name = "考察点位ID", width = 15)
    @ApiModelProperty(value = "考察点位ID")
    private String inspectPointId;
	/**考察点位名称*/
	@Excel(name = "考察点位名称", width = 15)
    @ApiModelProperty(value = "考察点位名称")
    private String inspectPointName;
	/**考察计划ID*/
	@Excel(name = "考察计划ID", width = 15)
    @ApiModelProperty(value = "考察计划ID")
    private String inspectPlanId;
	/**考察计划名称*/
	@Excel(name = "考察计划名称", width = 15)
    @ApiModelProperty(value = "考察计划名称")
    private String inspectPlanName;
	/**考察方案ID*/
	@Excel(name = "考察方案ID", width = 15)
    @ApiModelProperty(value = "考察方案ID")
    private String inspectProjectId;
	/**考察方案名称*/
	@Excel(name = "考察方案名称", width = 15)
    @ApiModelProperty(value = "考察方案名称")
    private String inspectProjectName;
	/**考察人员方案ID*/
	@Excel(name = "考察人员方案ID", width = 15)
    @ApiModelProperty(value = "考察人员方案ID")
    private String inspectPersonProjectId;
	/**考察人员方案名称*/
	@Excel(name = "考察人员方案名称", width = 15)
    @ApiModelProperty(value = "考察人员方案名称")
    private String inspectPersonProjectName;
	/**责任主体code*/
	@Excel(name = "责任主体code", width = 15)
    @ApiModelProperty(value = "责任主体code")
    private String subjectToliabilityOrgcode;
	/**考察指标ID*/
	@Excel(name = "考察指标ID", width = 15)
    @ApiModelProperty(value = "考察指标ID")
    private String inspectTargetId;
	/**检查时间*/
	@Excel(name = "检查时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "检查时间")
    private Date checkTime;
	/**检查结果，1：符合；2：不符合；*/
	@Excel(name = "检查结果，1：符合；2：不符合；", width = 15)
    @ApiModelProperty(value = "检查结果，1：符合；2：不符合；")
    private Integer checkStatus;
	/**问题描述*/
	@Excel(name = "问题描述", width = 15)
    @ApiModelProperty(value = "问题描述")
    private String questionDescr;
	/**0:正常；1：删除；*/
	@Excel(name = "0:正常；1：删除；", width = 15)
    @ApiModelProperty(value = "0:正常；1：删除；")
    private Integer delFlag;
}
