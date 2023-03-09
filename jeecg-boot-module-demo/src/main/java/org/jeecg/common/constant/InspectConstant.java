package org.jeecg.common.constant;

/**
 * @author: yuyy
 * @Date: 2023/1/31 13:13
 * @Desc
 */
public interface InspectConstant {

    /**
     * 考察问题，检查结果
     */
    Integer INSPECT_QUESTION_CHECK_STATUS_INIT  = 0;
    Integer INSPECT_QUESTION_CHECK_STATUS_CONFORM_ALL  = 1; // 符合
    Integer INSPECT_QUESTION_CHECK_STATUS_CONFORM_NO  = 2; // 不符合
    Integer INSPECT_QUESTION_CHECK_STATUS_CONFORM_DEPART  = 3; // 基本符合
}
