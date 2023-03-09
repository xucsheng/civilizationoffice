package org.jeecg.modules.inspect.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.jeecg.modules.inspect.entity.InspectQuestion;
import org.jeecg.modules.inspect.entity.InspectQuestionAttachment;

import java.util.List;

/**
 * @author: yuyy
 * @Date: 2023/1/31 13:53
 * @Desc 问题详情 + 问题附件信息。
 */
@Data
@ApiModel(value="问题详情DTO", description="问题详情DTO")
public class InspectQuestionDTO extends InspectQuestion {

    private List<InspectQuestionAttachment> inspectQuestionAttachmentList;

}
