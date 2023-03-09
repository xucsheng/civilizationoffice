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
import org.jeecg.modules.inspect.dto.InspectQuestionDTO;
import org.jeecg.modules.inspect.entity.InspectQuestion;
import org.jeecg.modules.inspect.service.IInspectQuestionService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.inspect.vo.InspectQuestionVO;
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
 * @Description: inspect_question
 * @Author: jeecg-boot
 * @Date:   2023-01-31
 * @Version: V1.0
 */
@Api(tags="inspect_question")
@RestController
@RequestMapping("/inspect/inspectQuestion")
@Slf4j
public class InspectQuestionController extends JeecgController<InspectQuestion, IInspectQuestionService> {

	@Autowired
	private IInspectQuestionService inspectQuestionService;
	
	/**
	 * 分页列表查询
	 *
	 * @param inspectQuestion
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@ApiOperation(value="考察问题-分页列表查询", notes="考察问题-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(InspectQuestion inspectQuestion,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		// TODO 根据 点位 条件查询 所有的问题列表？ 还是 inspectQuestionRecord记录表
		// TODO 需要提供考察指标相关信息。
		QueryWrapper<InspectQuestion> queryWrapper = QueryGenerator.initQueryWrapper(inspectQuestion, req.getParameterMap());
		Page<InspectQuestion> page = new Page<InspectQuestion>(pageNo, pageSize);
		IPage<InspectQuestion> pageList = inspectQuestionService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 * @param inspectQuestionDTO
	 * @return
	 */
	@AutoLog(value = "上传问题")
	@ApiOperation(value="问题上传-添加", notes="问题上传-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody InspectQuestionDTO inspectQuestionDTO) {
		inspectQuestionService.saveInspectQuestion(inspectQuestionDTO);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param inspectQuestion
	 * @return
	 */
	@AutoLog(value = "inspect_question-编辑")
	@ApiOperation(value="inspect_question-编辑", notes="inspect_question-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody InspectQuestion inspectQuestion) {
		inspectQuestionService.updateById(inspectQuestion);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "inspect_question-通过id删除")
	@ApiOperation(value="inspect_question-通过id删除", notes="inspect_question-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		inspectQuestionService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "inspect_question-批量删除")
	@ApiOperation(value="inspect_question-批量删除", notes="inspect_question-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.inspectQuestionService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "考察问题详情-通过id查询")
	@ApiOperation(value="考察问题详情-通过id查询", notes="考察问题详情-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		return Result.OK(inspectQuestionService.findInspectQuestionById(id));
	}

    /**
    * 导出excel
    *
    * @param request
    * @param inspectQuestion
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, InspectQuestion inspectQuestion) {
        return super.exportXls(request, inspectQuestion, InspectQuestion.class, "inspect_question");
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
        return super.importExcel(request, response, InspectQuestion.class);
    }

}
