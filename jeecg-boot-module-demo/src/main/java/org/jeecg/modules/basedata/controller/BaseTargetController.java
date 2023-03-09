package org.jeecg.modules.basedata.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.basedata.entity.BaseTarget;
import org.jeecg.modules.basedata.service.IBaseTargetService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 基础数据管理-指标库
 * @Author: jeecg-boot
 * @Date:   2023-01-29
 * @Version: V1.0
 */
@Api(tags="基础数据管理-指标库")
@RestController
@RequestMapping("/basedata/baseTarget")
@Slf4j
public class BaseTargetController extends JeecgController<BaseTarget, IBaseTargetService> {
	@Autowired
	private IBaseTargetService baseTargetService;

	/**
	 * 分页列表查询
	 *
	 * @param baseTarget
	 * @param pageNo
	 * @param pageSize
	 * @param
	 * @return
	 */
	@AutoLog(value = "基础数据管理-指标库-分页列表查询")
	@ApiOperation(value="基础数据管理-指标库-分页列表查询", notes="基础数据管理-指标库-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(BaseTarget baseTarget,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   @RequestParam(name="projectId", required = false) String projectId,
								   HttpServletRequest req) {
		QueryWrapper<BaseTarget> queryWrapper = QueryGenerator.initQueryWrapper(baseTarget, req.getParameterMap());
		queryWrapper.notInSql(StrUtil.isNotBlank(projectId), "id", "select target_id from inspect_project_target where del_flag = 0 and project_id = " + projectId);
		Page<BaseTarget> page = new Page<BaseTarget>(pageNo, pageSize);
		IPage<BaseTarget> pageList = baseTargetService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param baseTarget
	 * @return
	 */
	@AutoLog(value = "基础数据管理-指标库-添加")
	@ApiOperation(value="基础数据管理-指标库-添加", notes="基础数据管理-指标库-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody BaseTarget baseTarget) {
		baseTarget.setUseState(CommonConstant.STATUS_NORMAL);
		baseTargetService.save(baseTarget);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param baseTarget
	 * @return
	 */
	@AutoLog(value = "基础数据管理-指标库-编辑")
	@ApiOperation(value="基础数据管理-指标库-编辑", notes="基础数据管理-指标库-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody BaseTarget baseTarget) {
		baseTargetService.updateById(baseTarget);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "基础数据管理-指标库-通过id删除")
	@ApiOperation(value="基础数据管理-指标库-通过id删除", notes="基础数据管理-指标库-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) Long id) {
		baseTargetService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "基础数据管理-指标库-批量删除")
	@ApiOperation(value="基础数据管理-指标库-批量删除", notes="基础数据管理-指标库-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.baseTargetService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "基础数据管理-指标库-通过id查询")
	@ApiOperation(value="基础数据管理-指标库-通过id查询", notes="基础数据管理-指标库-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BaseTarget baseTarget = baseTargetService.getById(id);
		if(baseTarget==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(baseTarget);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param baseTarget
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BaseTarget baseTarget) {
        return super.exportXls(request, baseTarget, BaseTarget.class, "基础数据管理-指标库");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, BaseTarget.class);
    }

	 /**
	  * 启用/禁用
	  *
	  * @param id
	  * @param useState
	  * @return
	  */
	 @AutoLog(value = "基础数据管理-指标库-启用/禁用")
	 @ApiOperation(value="基础数据管理-指标库-启用/禁用", notes="基础数据管理-指标库-启用/禁用")
	 @RequestMapping(value = "/useState/{id}/{useState}", method = RequestMethod.PUT)
	 public Result<?> useState(@PathVariable("id") String id, @PathVariable("useState") Integer useState) {
		 if (CommonConstant.STATUS_NORMAL == useState || CommonConstant.STATUS_DISABLE == useState) {
			 if (baseTargetService.update(new BaseTarget(), new LambdaUpdateWrapper<BaseTarget>().set(BaseTarget::getUseState, useState).eq(BaseTarget::getId, id))) {
				 return Result.OK("操作成功");
			 } else {
				 return Result.error("操作失败");
			 }
		 } else {
			 return Result.error("状态未知");
		 }
	 }
}
