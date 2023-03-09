package org.jeecg.modules.inspect.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.constant.InspectConstant;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.modules.inspect.dto.InspectQuestionDTO;
import org.jeecg.modules.inspect.entity.InspectQuestion;
import org.jeecg.modules.inspect.entity.InspectQuestionAttachment;
import org.jeecg.modules.inspect.entity.InspectQuestionRecord;
import org.jeecg.modules.inspect.mapper.InspectQuestionMapper;
import org.jeecg.modules.inspect.service.IInspectQuestionAttachmentService;
import org.jeecg.modules.inspect.service.IInspectQuestionRecordService;
import org.jeecg.modules.inspect.service.IInspectQuestionService;
import org.jeecg.modules.inspect.vo.InspectQuestionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @Description: inspect_question
 * @Author: jeecg-boot
 * @Date:   2023-01-31
 * @Version: V1.0
 */
@Service
public class InspectQuestionServiceImpl extends ServiceImpl<InspectQuestionMapper, InspectQuestion> implements IInspectQuestionService {

    @Autowired
    private IInspectQuestionRecordService iInspectQuestionRecordService;

    @Autowired
    private IInspectQuestionAttachmentService inspectQuestionAttachmentService;

    @Transactional
    @Override
    public void saveInspectQuestion(InspectQuestionDTO inspectQuestionDTO) {
        String questionId = null; // 考察问题存表雪花ID
        String questionCode = "Q" + String.valueOf(RandomUtil.randomChar()).toUpperCase() + DateUtil.format(new Date(), "yyyyMMddHHmmss"); // 考察问题编码
        Integer checkStatus = inspectQuestionDTO.getCheckStatus();
        List<InspectQuestionAttachment> inspectQuestionAttachmentList = inspectQuestionDTO.getInspectQuestionAttachmentList();
        if(InspectConstant.INSPECT_QUESTION_CHECK_STATUS_CONFORM_NO == checkStatus) {
            if(CollectionUtils.isEmpty(inspectQuestionAttachmentList)) {
                throw new JeecgBootException("不符合，需上传拍照附件！");
            }
            InspectQuestion inspectQuestion = BeanUtil.copyProperties(inspectQuestionDTO, InspectQuestion.class);
            this.save(inspectQuestion);
            questionId = inspectQuestion.getId();
        }
        InspectQuestionRecord inspectQuestionRecord = BeanUtil.copyProperties(inspectQuestionDTO, InspectQuestionRecord.class);
        if(questionId != null) {
            inspectQuestionRecord.setId(questionId);
        }
        iInspectQuestionRecordService.save(inspectQuestionRecord);
        // 保存问题拍照附件。
        questionId = inspectQuestionRecord.getId();
        if(inspectQuestionAttachmentList != null && inspectQuestionAttachmentList.size() > 0) {
            for (InspectQuestionAttachment inspectQuestionAttachment : inspectQuestionAttachmentList) {
                if(InspectConstant.INSPECT_QUESTION_CHECK_STATUS_CONFORM_NO == checkStatus && StringUtils.isBlank(inspectQuestionAttachment.getAttachmentUrl())) {
                    throw new JeecgBootException("不符合，需上传拍照附件！");
                }
                inspectQuestionAttachment.setQuestionId(questionId);
                inspectQuestionAttachment.setQuestionCode(questionCode);
                inspectQuestionAttachment.setInspectPlanId(inspectQuestionRecord.getInspectPlanId());
                inspectQuestionAttachment.setInspectProjectId(inspectQuestionRecord.getInspectProjectId());
                inspectQuestionAttachment.setInspectTargetId(inspectQuestionRecord.getInspectTargetId());
            }
            inspectQuestionAttachmentService.saveBatch(inspectQuestionAttachmentList);
        }
    }

    @Override
    public InspectQuestionVO findInspectQuestionById(String id) {
        InspectQuestion one = this.getById(id);
        if(one == null) {
            throw new JeecgBootException("未找到问题信息");
        }
        InspectQuestionVO inspectQuestionVO = BeanUtil.copyProperties(one, InspectQuestionVO.class);
        // TODO 指标查询……
        return inspectQuestionVO;
    }
}
