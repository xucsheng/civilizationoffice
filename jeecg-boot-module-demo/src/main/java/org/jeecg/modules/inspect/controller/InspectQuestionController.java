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
	 * ??????????????????
	 *
	 * @param inspectQuestion
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@ApiOperation(value="????????????-??????????????????", notes="????????????-??????????????????")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(InspectQuestion inspectQuestion,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		// TODO ?????? ?????? ???????????? ???????????????????????? ?????? inspectQuestionRecord?????????
		// TODO ???????????????????????????????????????
		QueryWrapper<InspectQuestion> queryWrapper = QueryGenerator.initQueryWrapper(inspectQuestion, req.getParameterMap());
		Page<InspectQuestion> page = new Page<InspectQuestion>(pageNo, pageSize);
		IPage<InspectQuestion> pageList = inspectQuestionService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   ??????
	 * @param inspectQuestionDTO
	 * @return
	 */
	@AutoLog(value = "????????????")
	@ApiOperation(value="????????????-??????", notes="????????????-??????")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody InspectQuestionDTO inspectQuestionDTO) {
		inspectQuestionService.saveInspectQuestion(inspectQuestionDTO);
		return Result.OK("???????????????");
	}
	
	/**
	 *  ??????
	 *
	 * @param inspectQuestion
	 * @return
	 */
	@AutoLog(value = "inspect_question-??????")
	@ApiOperation(value="inspect_question-??????", notes="inspect_question-??????")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody InspectQuestion inspectQuestion) {
		inspectQuestionService.updateById(inspectQuestion);
		return Result.OK("????????????!");
	}
	
	/**
	 *   ??????id??????
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "inspect_question-??????id??????")
	@ApiOperation(value="inspect_question-??????id??????", notes="inspect_question-??????id??????")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		inspectQuestionService.removeById(id);
		return Result.OK("????????????!");
	}
	
	/**
	 *  ????????????
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "inspect_question-????????????")
	@ApiOperation(value="inspect_question-????????????", notes="inspect_question-????????????")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.inspectQuestionService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("??????????????????!");
	}
	
	/**
	 * ??????id??????
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "??????????????????-??????id??????")
	@ApiOperation(value="??????????????????-??????id??????", notes="??????????????????-??????id??????")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		return Result.OK(inspectQuestionService.findInspectQuestionById(id));
	}

    /**
    * ??????excel
    *
    * @param request
    * @param inspectQuestion
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, InspectQuestion inspectQuestion) {
        return super.exportXls(request, inspectQuestion, InspectQuestion.class, "inspect_question");
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
        return super.importExcel(request, response, InspectQuestion.class);
    }

}
