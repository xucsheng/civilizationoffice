package org.jeecg.modules.inspect.service;

import org.jeecg.modules.inspect.dto.InspectQuestionDTO;
import org.jeecg.modules.inspect.entity.InspectQuestion;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.inspect.vo.InspectQuestionVO;

/**
 * @Description: inspect_question
 * @Author: jeecg-boot
 * @Date:   2023-01-31
 * @Version: V1.0
 */
public interface IInspectQuestionService extends IService<InspectQuestion> {

    /**
     * 问题上传
     * @param inspectQuestionDTO
     */
    void saveInspectQuestion(InspectQuestionDTO inspectQuestionDTO);

    /**
     * 根据问题ID，获取问题详情。
     * @param id
     * @return
     */
    InspectQuestionVO findInspectQuestionById(String id);

}
