package org.jeecg.modules.pointposition.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.pointposition.entity.SysPointPosition;
import org.jeecg.modules.pointposition.service.ISysPointPositionService;

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
 * @Description: 点位信息表
 * @Author: jeecg-boot
 * @Date:   2023-01-30
 * @Version: V1.0
 */
@Api(tags="点位信息表")
@RestController
@RequestMapping("/pointposition/sysPointPosition")
@Slf4j
public class SysPointPositionController extends JeecgController<SysPointPosition, ISysPointPositionService> {
	@Autowired
	private ISysPointPositionService sysPointPositionService;

	/**
	 * 分页列表查询
	 *
	 * @param sysPointPosition
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "点位信息表-分页列表查询")
	@ApiOperation(value="点位信息表-分页列表查询", notes="点位信息表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(SysPointPosition sysPointPosition,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<SysPointPosition> queryWrapper = QueryGenerator.initQueryWrapper(sysPointPosition, req.getParameterMap());
		Page<SysPointPosition> page = new Page<SysPointPosition>(pageNo, pageSize);
		IPage<SysPointPosition> pageList = sysPointPositionService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param sysPointPosition
	 * @return
	 */
	@AutoLog(value = "点位信息表-添加")
	@ApiOperation(value="点位信息表-添加", notes="点位信息表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody SysPointPosition sysPointPosition) {
		sysPointPositionService.save(sysPointPosition);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param sysPointPosition
	 * @return
	 */
	@AutoLog(value = "点位信息表-编辑")
	@ApiOperation(value="点位信息表-编辑", notes="点位信息表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody SysPointPosition sysPointPosition) {
		sysPointPositionService.updateById(sysPointPosition);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "点位信息表-通过id删除")
	@ApiOperation(value="点位信息表-通过id删除", notes="点位信息表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		this.sysPointPositionService.deleteLogic(Arrays.asList(id));
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "点位信息表-批量删除")
	@ApiOperation(value="点位信息表-批量删除", notes="点位信息表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.sysPointPositionService.deleteLogic(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "点位信息表-通过id查询")
	@ApiOperation(value="点位信息表-通过id查询", notes="点位信息表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		SysPointPosition sysPointPosition = sysPointPositionService.getById(id);
		if(sysPointPosition==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(sysPointPosition);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param sysPointPosition
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, SysPointPosition sysPointPosition) {
        return super.exportXls(request, sysPointPosition, SysPointPosition.class, "点位信息表");
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
        return super.importExcel(request, response, SysPointPosition.class);
    }

}
