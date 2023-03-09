package org.jeecg.modules.inspect.vo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.jeecg.modules.basedata.entity.InspectQuestions;
import org.jeecg.modules.inspect.entity.InspectQuestion;

import java.util.List;

/**
 * @author: yuyy
 * @Date: 2023/1/31 15:20
 * @Desc 考察问题详情
 */
@Data
public class InspectQuestionVO extends InspectQuestion {
    private String inspectTargetName; // 检查指标名
    private InspectQuestions inspectQuestions; // 问题-指标 下的具体检测问题。 lu 字段存值为：JSON类型。
    private List<String> questionAttachmentUrls; // 问题图片附件URL集合
    private String questionAddress;
    private String longitude; // 经度
    private String latitude; // 纬度
}
