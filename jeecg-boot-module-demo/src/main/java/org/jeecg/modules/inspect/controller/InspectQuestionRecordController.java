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
	 * ??????????????????
	 *
	 * @param inspectQuestionRecord
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "inspect_question_record-??????????????????")
	@ApiOperation(value="inspect_question_record-??????????????????", notes="inspect_question_record-??????????????????")
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
	 *   ??????
	 *
	 * @param inspectQuestionRecord
	 * @return
	 */
	@AutoLog(value = "inspect_question_record-??????")
	@ApiOperation(value="inspect_question_record-??????", notes="inspect_question_record-??????")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody InspectQuestionRecord inspectQuestionRecord) {
		inspectQuestionRecordService.save(inspectQuestionRecord);
		return Result.OK("???????????????");
	}
	
	/**
	 *  ??????
	 *
	 * @param inspectQuestionRecord
	 * @return
	 */
	@AutoLog(value = "inspect_question_record-??????")
	@ApiOperation(value="inspect_question_record-??????", notes="inspect_question_record-??????")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody InspectQuestionRecord inspectQuestionRecord) {
		inspectQuestionRecordService.updateById(inspectQuestionRecord);
		return Result.OK("????????????!");
	}
	
	/**
	 *   ??????id??????
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "inspect_question_record-??????id??????")
	@ApiOperation(value="inspect_question_record-??????id??????", notes="inspect_question_record-??????id??????")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		inspectQuestionRecordService.removeById(id);
		return Result.OK("????????????!");
	}
	
	/**
	 *  ????????????
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "inspect_question_record-????????????")
	@ApiOperation(value="inspect_question_record-????????????", notes="inspect_question_record-????????????")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.inspectQuestionRecordService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("??????????????????!");
	}
	
	/**
	 * ??????id??????
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "inspect_question_record-??????id??????")
	@ApiOperation(value="inspect_question_record-??????id??????", notes="inspect_question_record-??????id??????")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		InspectQuestionRecord inspectQuestionRecord = inspectQuestionRecordService.getById(id);
		if(inspectQuestionRecord==null) {
			return Result.error("?????????????????????");
		}
		return Result.OK(inspectQuestionRecord);
	}

    /**
    * ??????excel
    *
    * @param request
    * @param inspectQuestionRecord
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, InspectQuestionRecord inspectQuestionRecord) {
        return super.exportXls(request, inspectQuestionRecord, InspectQuestionRecord.class, "inspect_question_record");
    }

    /**
      * ??????excel????????????
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
