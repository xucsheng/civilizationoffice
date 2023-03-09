package org.jeecg.modules.inspect.controller;

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
import org.jeecg.modules.inspect.entity.InspectQuestionRecord;
import org.jeecg.modules.inspect.service.IInspectQuestionRecordService;

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
 * @Description: inspect_question_record
 * @Author: jeecg-boot
 * @Date:   2023-01-31
 * @Version: V1.0
 */
@Api(tags="inspect_question_record")
@RestController
@RequestMapping("/inspect/inspectQuestionRecord")
@Slf4j
public class InspectQuestionRecordController extends JeecgController<InspectQuestionRecord, IInspectQuestionRecordService> {
	@Autowired
	private IInspectQuestionRecordService inspectQuestionRecordService;
	
	/**
	 * 分页列表查询
	 *
	 * @param inspectQuestionRecord
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "inspect_question_record-分页列表查询")
	@ApiOperation(value="inspect_question_record-分页列表查询", notes="inspect_question_record-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(InspectQuestionRecord inspectQuestionRecord,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<InspectQuestionRecord> queryWrapper = QueryGenerator.initQueryWrapper(inspectQuestionRecord, req.getParameterMap());
		Page<InspectQuestionRecord> page = new Page<InspectQuestionRecord>(pageNo, pageSize);
		IPage<InspectQuestionRecord> pageList = inspectQuestionRecordService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param inspectQuestionRecord
	 * @return
	 */
	@AutoLog(value = "inspect_question_record-添加")
	@ApiOperation(value="inspect_question_record-添加", notes="inspect_question_record-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody InspectQuestionRecord inspectQuestionRecord) {
		inspectQuestionRecordService.save(inspectQuestionRecord);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param inspectQuestionRecord
	 * @return
	 */
	@AutoLog(value = "inspect_question_record-编辑")
	@ApiOperation(value="inspect_question_record-编辑", notes="inspect_question_record-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody InspectQuestionRecord inspectQuestionRecord) {
		inspectQuestionRecordService.updateById(inspectQuestionRecord);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "inspect_question_record-通过id删除")
	@ApiOperation(value="inspect_question_record-通过id删除", notes="inspect_question_record-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		inspectQuestionRecordService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "inspect_question_record-批量删除")
	@ApiOperation(value="inspect_question_record-批量删除", notes="inspect_question_record-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.inspectQuestionRecordService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "inspect_question_record-通过id查询")
	@ApiOperation(value="inspect_question_record-通过id查询", notes="inspect_question_record-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		InspectQuestionRecord inspectQuestionRecord = inspectQuestionRecordService.getById(id);
		if(inspectQuestionRecord==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(inspectQuestionRecord);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param inspectQuestionRecord
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, InspectQuestionRecord inspectQuestionRecord) {
        return super.exportXls(request, inspectQuestionRecord, InspectQuestionRecord.class, "inspect_question_record");
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
        return super.importExcel(request, response, InspectQuestionRecord.class);
    }

}
