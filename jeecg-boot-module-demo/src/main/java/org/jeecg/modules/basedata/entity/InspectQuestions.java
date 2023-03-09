package org.jeecg.modules.basedata.entity;

import lombok.Data;

import java.util.List;

/**
 * @Description: 考察问题序列化对象
 * @author: lhtao
 * @date: 2023年01月29日 15:40
 */
@Data
public class InspectQuestions {
    private List<String> questions;
}
