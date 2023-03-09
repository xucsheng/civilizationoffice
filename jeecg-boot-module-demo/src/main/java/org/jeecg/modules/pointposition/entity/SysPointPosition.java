package org.jeecg.modules.pointposition.entity;

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
 * @Description: 点位信息表
 * @Author: jeecg-boot
 * @Date:   2023-01-30
 * @Version: V1.0
 */
@Data
@TableName("sys_point_position")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="sys_point_position对象", description="点位信息表")
public class SysPointPosition implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private String sysOrgCode;
	/**点位类型*/
	@Excel(name = "点位类型", width = 15, dicCode = "point_position_type")
	@Dict(dicCode = "point_position_type")
    @ApiModelProperty(value = "点位类型")
    private String pointPositionTypeCode;
	/**点位名称*/
	@Excel(name = "点位名称", width = 15)
    @ApiModelProperty(value = "点位名称")
    private String name;
	/**电信营业厅类型*/
	@Excel(name = "电信营业厅类型", width = 15, dicCode = "telecom_type")
	@Dict(dicCode = "telecom_type")
    @ApiModelProperty(value = "电信营业厅类型")
    private String telecomTypeCode;
	/**医院等级*/
	@Excel(name = "医院等级", width = 15, dicCode = "hospital_level")
	@Dict(dicCode = "hospital_level")
    @ApiModelProperty(value = "医院等级")
    private String hospitalLevelCode;
	/**宾馆等级*/
	@Excel(name = "宾馆等级", width = 15, dicCode = "hotel_level")
	@Dict(dicCode = "hotel_level")
    @ApiModelProperty(value = "宾馆等级")
    private String hotelLevelCode;
	/**所属区域*/
	@Excel(name = "所属区域", width = 15)
    @ApiModelProperty(value = "所属区域")
    private String areaCode;
	/**所属街道*/
	@Excel(name = "所属街道", width = 15)
    @ApiModelProperty(value = "所属街道")
    private String streetCode;
	/**所属社区*/
	@Excel(name = "所属社区", width = 15)
    @ApiModelProperty(value = "所属社区")
    private String communityCode;
	/**文明社区等级*/
	@Excel(name = "文明社区等级", width = 15, dicCode = "community_grade")
	@Dict(dicCode = "community_grade")
    @ApiModelProperty(value = "文明社区等级")
    private String communityGradeCode;

	/**文明村等级*/
	@Excel(name = "文明村等级", width = 15, dicCode = "village_level")
	@Dict(dicCode = "village_level")
	@ApiModelProperty(value = "文明村等级")
	private String villageLevelCode;

	/**社区类别*/
	@Excel(name = "社区类别", width = 15, dicCode = "community_category")
	@Dict(dicCode = "community_category")
    @ApiModelProperty(value = "社区类别")
    private String communityCategoryCode;
	/**文明乡镇等级*/
	@Excel(name = "文明乡镇等级", width = 15, dicCode = "township_level")
	@Dict(dicCode = "township_level")
    @ApiModelProperty(value = "文明乡镇等级")
    private String townshipLevelCode;
	/**乡镇（街道办事处）政府地址）*/
	@Excel(name = "乡镇（街道办事处）政府地址）", width = 15)
    @ApiModelProperty(value = "乡镇（街道办事处）政府地址）")
    private String addressMain;
	/**详细地址*/
	@Excel(name = "详细地址", width = 15)
    @ApiModelProperty(value = "详细地址")
    private String address;
	/**宾馆内饭店/餐厅名称*/
	@Excel(name = "宾馆内饭店/餐厅名称", width = 15)
    @ApiModelProperty(value = "宾馆内饭店/餐厅名称")
    private String restaurantName;
	/**主次干道*/
	@Excel(name = "主次干道", width = 15, dicCode = "road_type")
	@Dict(dicCode = "road_type")
    @ApiModelProperty(value = "主次干道")
    private String roadTypeCode;
	/**居民户数*/
	@Excel(name = "居民户数", width = 15)
    @ApiModelProperty(value = "居民户数")
    private String residentCount;
	/**广场面积（平方米）/文挡墙体面积*/
	@Excel(name = "广场面积（平方米）/文挡墙体面积", width = 15)
    @ApiModelProperty(value = "广场面积（平方米）/文挡墙体面积")
    private String squareArea;
	/**联系人*/
	@Excel(name = "联系人", width = 15)
    @ApiModelProperty(value = "联系人")
    private String contacts;
	/**联系人电话*/
	@Excel(name = "联系人电话", width = 15)
    @ApiModelProperty(value = "联系人电话")
    private String contactPhone;
	/**距离（公里）*/
	@Excel(name = "距离（公里）", width = 15)
    @ApiModelProperty(value = "距离（公里）")
    private String distance;
	/**周边300米范围是否有居民校区*/
	@Excel(name = "周边300米范围是否有居民校区", width = 15, dicCode = "logic")
	@Dict(dicCode = "logic")
    @ApiModelProperty(value = "周边300米范围是否有居民校区")
    private String campusFlag;
	/**点位图片*/
	@Excel(name = "点位图片", width = 15)
    @ApiModelProperty(value = "点位图片")
    private String picture;
	/**经度 */
	@Excel(name = "经度 ", width = 15)
    @ApiModelProperty(value = "经度 ")
    private Double pointLongitude;
	/**纬度*/
	@Excel(name = "纬度", width = 15)
    @ApiModelProperty(value = "纬度")
    private Double pointLatitude;

	/**delFlag*/
	@Excel(name = "delFlag", width = 15)
	@ApiModelProperty(value = "delFlag")
	private Integer delFlag;
}
