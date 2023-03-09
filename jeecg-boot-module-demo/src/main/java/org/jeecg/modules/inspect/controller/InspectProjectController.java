package org.jeecg.modules.inspect.controller;

import java.util.*;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.basedata.entity.BaseTarget;
import org.jeecg.modules.basedata.service.IBaseTargetService;
import org.jeecg.modules.inspect.dto.InspectProjectSaveDTO;
import org.jeecg.modules.inspect.entity.InspectPlan;
import org.jeecg.modules.inspect.entity.InspectProject;
import org.jeecg.modules.inspect.entity.InspectProjectTarget;
import org.jeecg.modules.inspect.service.IInspectPlanService;
import org.jeecg.modules.inspect.service.IInspectProjectService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.inspect.service.IInspectProjectTargetService;
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
 * @Description: 考察管理-方案管理
 * @Author: jeecg-boot
 * @Date:   2023-01-30
 * @Version: V1.0
 */
@Api(tags="考察管理-方案管理")
@RestController
@RequestMapping("/inspect/inspectProject")
@Slf4j
public class InspectProjectController extends JeecgController<InspectProject, IInspectProjectService> {
	@Autowired
	private IInspectProjectService inspectProjectService;

	@Autowired
	private IInspectProjectTargetService inspectProjectTargetService;

	@Autowired
	private IInspectPlanService inspectPlanService;

	@Autowired
	private IBaseTargetService baseTargetService;

	/**
	 * 分页列表查询方案
	 *
	 * @param inspectProject
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "考察管理-方案管理-分页列表查询方案")
	@ApiOperation(value="考察管理-方案管理-分页列表查询方案", notes="考察管理-方案管理-分页列表查询方案")
	@GetMapping(value = "/project/list")
	public Result<?> queryProjectPageList(InspectProject inspectProject,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<InspectProject> queryWrapper = QueryGenerator.initQueryWrapper(inspectProject, req.getParameterMap());
		Page<InspectProject> page = new Page<InspectProject>(pageNo, pageSize);
		IPage<InspectProject> pageList = inspectProjectService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	 /**
	  * 分页列表查询指标
	  *
	  * @param inspectProjectTarget
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	 @AutoLog(value = "考察管理-方案管理-分页列表查询指标")
	 @ApiOperation(value="考察管理-方案管理-分页列表查询指标", notes="考察管理-方案管理-分页列表查询指标")
	 @GetMapping(value = "/target/list")
	 public Result<?> queryTargetPageList(InspectProjectTarget inspectProjectTarget,
									@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									HttpServletRequest req) {
		 QueryWrapper<InspectProjectTarget> queryWrapper = QueryGenerator.initQueryWrapper(inspectProjectTarget, req.getParameterMap());
		 Page<InspectProjectTarget> page = new Page<InspectProjectTarget>(pageNo, pageSize);
		 IPage<InspectProjectTarget> pageList = inspectProjectTargetService.page(page, queryWrapper);
		 return Result.OK(pageList);
	 }

	 /**
	  *   保存方案
	  *
	  * @param inspectProject
	  * @return
	  */
	 @AutoLog(value = "考察管理-方案管理-保存方案")
	 @ApiOperation(value="考察管理-方案管理-保存方案", notes="考察管理-方案管理-保存方案")
	 @PostMapping(value = "/project/save")
	 public Result<?> saveProject(@RequestBody InspectProject inspectProject) {
		 //修改的情况下需要判断有没有被考察计划引用
		 if (StrUtil.isNotBlank(inspectProject.getId())) {

		 }
		 inspectProjectService.saveOrUpdate(inspectProject);
		 return Result.OK("保存方案成功！");
	 }

	/**
	 *   保存指标
	 *
	 * @param dto
	 * @return
	 */
	@AutoLog(value = "考察管理-方案管理-保存指标")
	@ApiOperation(value="考察管理-方案管理-保存指标", notes="考察管理-方案管理-保存指标")
	@PostMapping(value = "/target/save")
	public Result<?> saveTarget(@RequestBody InspectProjectSaveDTO dto) {
		checkProjectInUse(dto.getId());
		//从界面设计来讲 保存指标只存在批量新增和单独修改两种
		if (CollectionUtil.isNotEmpty(dto.getTargets())) {
			if (!dto.getTargets().stream().filter(t -> StrUtil.isNotBlank(t.getId())).findAny().map(p -> {
				//对于有id需要修改的数据
				inspectProjectTargetService.updateById(p);
				return p;
			}).isPresent()) {
				//如果不存在需要单独修改的数据，说明是新增。需要自动先补充原指标的标准与问题
				List<BaseTarget> baseTargets = baseTargetService.listByIds(dto.getTargets().stream().filter(t -> StrUtil.isBlank(t.getId())).map(p -> p.getTargetId()).collect(Collectors.toList()));
				dto.getTargets().forEach(x -> {
					x.setProjectId(dto.getId());
					BaseTarget eq = baseTargets.stream().filter(y -> y.getId().equals(x.getTargetId())).findFirst().get();
					x.setInspectQuestionsProj(eq.getInspectQuestions());
					x.setInspectStandardProj(eq.getInspectStandard());
				});
				inspectProjectTargetService.saveBatch(dto.getTargets());
			}
		}
		return Result.OK("保存指标成功！");
	}

	 /**
	  *   删除指标
	  *
	  * @param id
	  * @return
	  */
	 @AutoLog(value = "考察管理-方案管理-删除指标")
	 @ApiOperation(value="考察管理-方案管理-删除指标", notes="考察管理-方案管理-删除指标")
	 @DeleteMapping(value = "/target/delete")
	 public Result<?> deleteTarget(@RequestParam(name = "id", required = true) String id) {
		 InspectProjectTarget target = inspectProjectTargetService.getById(id);
		 checkProjectInUse(target.getProjectId());
		 inspectProjectTargetService.removeById(id);
		 return Result.OK("删除指标成功!");
	 }

	/**
	 *   删除方案
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "考察管理-方案管理-删除方案")
	@ApiOperation(value="考察管理-方案管理-删除方案", notes="考察管理-方案管理-删除方案")
	@DeleteMapping(value = "/project/delete")
	public Result<?> deleteProject(@RequestParam(name="id",required=true) String id) {
		checkProjectInUse(id);
		inspectProjectService.removeById(id);
		return Result.OK("删除方案成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "考察管理-方案管理-批量删除")
	@ApiOperation(value="考察管理-方案管理-批量删除", notes="考察管理-方案管理-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.inspectProjectService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "考察管理-方案管理-通过id查询")
	@ApiOperation(value="考察管理-方案管理-通过id查询", notes="考察管理-方案管理-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		InspectProject inspectProject = inspectProjectService.getById(id);
		if(inspectProject==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(inspectProject);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param inspectProject
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, InspectProject inspectProject) {
        return super.exportXls(request, inspectProject, InspectProject.class, "考察管理-方案管理");
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
        return super.importExcel(request, response, InspectProject.class);
    }

	 /**
	  * 复制
	  *
	  * @param id
	  * @return
	  */
	 @AutoLog(value = "考察管理-方案管理-复制")
	 @ApiOperation(value="考察管理-方案管理-复制", notes="考察管理-方案管理-复制")
	 @PostMapping(value = "/copy")
	 public Result<?> copy(@RequestParam(name="id",required=true) String id) {
		 inspectProjectService.copy(id);
		 return Result.OK("复制成功");
	 }

	 /**
	  * 判断方案是否被未删除的计划引用
	  * @param projectId
	  */
	private void checkProjectInUse(String projectId) {
		//TODO 根据方案id去计划里民安看有没有没已经启动的计划
		if (inspectPlanService.list(new LambdaQueryWrapper<InspectPlan>().eq(InspectPlan::getProjectId, projectId).eq(InspectPlan::getPlanState, CommonConstant.STATUS_1)).size() > 0) {
			throw new RuntimeException("该方案被已启动的考察计划关联，无法删除");
		}
	}
}
