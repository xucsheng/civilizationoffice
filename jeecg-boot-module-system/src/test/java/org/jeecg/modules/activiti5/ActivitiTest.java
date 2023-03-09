package org.jeecg.modules.activiti5;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

/**
 * @author: yuyy
 * @Date: 2022/10/14 17:16
 * @Desc 工作流测试。
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SuppressWarnings({"FieldCanBeLocal", "SpringJavaAutowiredMembersInspection"})
public class ActivitiTest {

//    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Test
    public void test01() {
        System.out.println("===== test === test ====");
    }

    @Test
    public void depolyProcess() {
        // 部署流程
        Deployment deployment = repositoryService.createDeployment().name("PowerAssessmentForm")
                .addClasspathResource("PowerAssessmentForm.bpmn20.xml")
                .addClasspathResource("PowerAssessmentForm.bpmn20.png")
                .deploy();
        System.out.println("部署流程成功，部署ID：" + deployment.getId());
    }

    @Test
    public void depolyProcess01() {
        // 部署流程
        Deployment deployment = repositoryService.createDeployment().name("Paf")
                .addClasspathResource("Paf.bpmn20.xml")
                .addClasspathResource("PowerAssessmentForm.bpmn20.png")
                .deploy();
        System.out.println("部署流程成功，部署ID：" + deployment.getId());
    }

    @Test
    public void startProcess() {
        // 启动流程
//        RuntimeService runtimeService = this.processEngine.getRuntimeService();
        // act_re_procdef 表 ID_ 值 ，eg: PowerAssessmentForm:1:10005
        String processDefinitionId = "PowerAssessmentForm:1:10005"; // 流程定义ID
        String processDefinitionKey = "PowerAssessmentForm"; // 流程定义Key
//        runtimeService.startProcessInstanceById(processDefinitionId);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey);
        processInstance.getId();
        processInstance.getActivityId();
        System.out.println("processInstanceId ->" + processInstance.getId());
        System.out.println("ActivityId ->" + processInstance.getActivityId());
        System.out.println("流程启动成功！");

    }

    @Test
    public void startProcessByVar() {
        // 启动流程
//        String processDefinitionKey = "PowerAssessmentForm"; // 流程定义Key
        String processDefinitionKey = "Paf"; // 流程定义Key
        HashMap<String, Object> variables = new HashMap<>();
        variables.put("usernames", "yuyy,yu,qianxiaoqi,zhuj");
        runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);
        System.out.println("流程启动成功！");


    }

    @Test
    public void findGroupUser() {
        // 查看组成员列表
//        String taskId = "15005";
        String taskId = "20005";
        List<IdentityLink> identityLinks = taskService.getIdentityLinksForTask(taskId);
        for (IdentityLink obj: identityLinks) {
            System.out.println("userId:" + obj.getUserId());
            System.out.println("taskId:" + obj.getTaskId());
            System.out.println("groupId:" + obj.getGroupId());
            System.out.println("type:" + obj.getType());
            System.out.println("流程定义ID:" + obj.getProcessDefinitionId());
            System.out.println("流程实例ID:" + obj.getProcessInstanceId());
            System.out.println("#########################");
        }

    }

    @Test
    public void addGroupUser() {
        // 添加组成员
        String taskId = "20005";
        taskService.addCandidateUser(taskId, "xiaoyu");
        System.out.println("添加用户组成员 成功！");

    }

    @Test
    public void deleteGroupUser() {
        // 删除组成员
        String taskId = "20005";
        taskService.deleteCandidateUser(taskId, "xiaoyu");
        System.out.println("删除用户组成员 成功！");
        // 删除成功后，只会删除 表：act_hi_identitylink 中的 候选者数据！
    }

    @Test
    public void findGroupTask() {
        // 查询组任务 （执行任务拾取后，分组任务则查不到了！！！）
        String candidateUser = "yuyy";
//        String candidateUser = "yu";
//        String candidateUser = "qianxiaoqi";
//        String candidateUser = "zhuj";
        List<Task> taskList = taskService.createTaskQuery().taskCandidateUser(candidateUser).list();
        if(null != taskList && taskList.size() > 0) {
            for (Task task : taskList) {
                System.out.println("任务ID：" + task.getId());
                System.out.println("流程实例ID：" + task.getProcessInstanceId());
                System.out.println("执行实例ID：" + task.getExecutionId());
                System.out.println("流程定义ID：" + task.getProcessDefinitionId());
                System.out.println("任务名称：" + task.getName());
                System.out.println("任务办理人：" + task.getAssignee());
                System.out.println("###################################");
            }

        }
    }

    @Test
    public void claim() {
        // 任务拾取 （将组任务 变成 个人任务。）
        String taskId = "20005";
        taskService.claim(taskId, "yuyy");
        System.out.println("任务拾取成功！");
    }

    @Test
    public void claimBack() {
        // 任务回退
        String taskId = "20005";
        taskService.claim(taskId, null);
        System.out.println("任务回退成功！");
    }

    @Test
    public void findPersonTask() {
        // 查询个人任务 （查看当前用户 待办任务）
        String assignee = "yuyy";
//        String assignee = "qianxiaoqi";
        // 查询：待办理任务总数
        long count = taskService.createTaskQuery().taskAssignee(assignee).count();
        // 分页查询：
//        List<Task> list = taskService.createTaskQuery().taskAssignee(assignee).listPage(1, 10);
        List<Task> list = taskService.createTaskQuery().taskAssignee(assignee).list();
        if(null != list && list.size() > 0) {
            for (Task task : list) {
                System.out.println("任务ID：" + task.getId());
                System.out.println("流程实例ID：" + task.getProcessInstanceId());
                System.out.println("执行实例ID：" + task.getExecutionId());
                System.out.println("流程定义ID：" + task.getProcessDefinitionId());
                System.out.println("任务名称：" + task.getName());
                System.out.println("任务办理人：" + task.getAssignee());
                System.out.println("###################################");
            }

        }
    }

    @Test
    public void handleTask() {
        // 办理任务
        String taskId = "20005";
        // 根据任务ID，查询任务实例。
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

        /**
         * 第一步：根据任务ID，查询这个评估单任务详情
         */
        // 从任务里面取出流程实例ID
        String processInstanceId = task.getProcessInstanceId();
        // 根据流程实例ID，查询流程实例
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processDefinitionId(processInstanceId).singleResult();
        // 取出 business_key
        String businessKey = processInstance.getBusinessKey(); // eg： LeaveBill:9
        System.out.println("businessKey ->" + businessKey);
        String billId = businessKey.split(":")[1]; // 请假单业务ID，用于查询详情。
        // 请假单业务ID 查询 …… TODO

        /**
         * 第二步：根据TaskId，查询流程图连线信息
         * 查询 form 按钮信息 （提交 OR 放弃） 【流程图连线信息】
         */
        // 根据流程定义ID，查询流程定义的XML
        String processDefinitionId = task.getProcessDefinitionId(); // 流程定义ID
//        ProcessDefinition processDefinition = repositoryService.getProcessDefinition(processDefinitionId);
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity)repositoryService.getProcessDefinition(processDefinitionId);
        // 根据流程实例ID，获取流程实例 ProcessInstance processInstance，获取流程实例中的活动节点ID。
        String activityId = processInstance.getActivityId();
        // 根据活动节点ID，获取程定义的XML和当前活动节点相关数据。
        ActivityImpl activityImpl = processDefinition.findActivity(activityId);

        List<String> names = new ArrayList<>();
        // 从 activityImpl获取连线信息。
        List<PvmTransition> outgoingTransitions = activityImpl.getOutgoingTransitions();
        if(null != outgoingTransitions && outgoingTransitions.size() > 0 ) {
            // PvmTransition 连线对象
            for (PvmTransition pvmTransition : outgoingTransitions) {
                String name = pvmTransition.getProperty("name").toString();
                names.add(name);
            }

        }

        /**
         * 第三步：查询批注信息
         */
        // 根据流程实例ID，获取
        List<Comment> comments = taskService.getProcessInstanceComments(processInstanceId);
        if(null != null && comments.size() > 0) {
            for (Comment comment : comments) {
                String userId = comment.getUserId();
                Date time = comment.getTime();
                String fullMessage = comment.getFullMessage();
                String type = comment.getType();
            }
        }

        /**
         * 第四步： 完成任务。
         */
//        String taskId = ""; // 前端传值：任务ID
        String outcome = "提交"; // 前端传值：连接名称
        String businessID = ""; // 前端传值：业务流程ID，eg:请假单ID or 评估单ID。
        String pizhuInfo = ""; // 前端传值： 批注信息

        // 添加批注信息。 需要设置 批注人信息。
        Authentication.setAuthenticatedUserId("yuyy"); // ThreadLocal 添加线程变量 设置用户人信息。
        taskService.addComment(taskId, processInstanceId, "[" + outcome + "]" + pizhuInfo);

        Map<String, Object> variables = new HashMap<>();
        variables.put("outcome", outcome); // 确认流程图 下一步往哪走。
        taskService.complete(taskId, variables); // 完成任务。
        // 查询当前用户 的 下一级 审批对象。TODO  通过任务监听器实现 TaskListenerImpl implements TaskListener 接口 设置下级办理任务人。

        // 判断任务是否结束。(根据流程实例ID查询。)
        ProcessInstance processInstanceResult = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId).singleResult();
        if(null==processInstanceResult) {
            System.out.println("流程已结束！");
//            LeaveBill leaveBill = new LeaveBill();
//            leaveBill.setId("123213");
            if(outcome=="放弃") {
                // 评估单ID 或者 请假单ID 设置业务状态。
//                leaveBill.setState(2);
            } else {
                // 审批完成
//                leaveBill.setState(1);
            }
//            this.billMapper.update(); // 业务更新
        } else {
            System.out.println("流程已流转到下一个办理去了！");

        }
    }

    @Test
    public void queryProcessByTaskId() {
        // 根据任务ID查询 流程进度图。
        // 取出流程定义ID
        // 办理任务
        String taskId = "20005";
        // 根据任务ID，查询任务实例。
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        // 根据流程定义ID，查询流程定义的XML
        String processDefinitionId = task.getProcessDefinitionId(); // 流程定义ID
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(processDefinitionId)
                .singleResult();
        String deploymentId = processDefinition.getDeploymentId(); // 流程部署ID
        // 将 deploymentId返回给前端获取 流程图 流 展示。

        // 根据任务节点坐标
        Map<String, Object> coordinate = new HashMap<>();
        String processInstanceId = task.getProcessInstanceId();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processDefinitionId(processInstanceId).singleResult();
        // 根据流程实例ID，获取流程实例 ProcessInstance processInstance，获取流程实例中的活动节点ID。
        String activityId = processInstance.getActivityId();
        // 根据活动节点ID，获取程定义的XML和当前活动节点相关数据。
        ProcessDefinitionEntity processDefinition1 = (ProcessDefinitionEntity)repositoryService.getProcessDefinition(processDefinitionId);
        ActivityImpl activityImpl = processDefinition1.findActivity(activityId);

      // 从activityImpl取出坐标信息
        coordinate.put("x", activityImpl.getX());
        coordinate.put("y", activityImpl.getY());
        coordinate.put("width", activityImpl.getWidth());
        coordinate.put("height", activityImpl.getHeight());

        // 将坐标信息 coordinate 值 返回给前端，控制流程图样式大小。

    }

    @Test
    public void queryShenPiJingdu() {
        // 1、查看审批进度
        // 根据请假单ID，查询审批批注信息及请假单详情信息。
        // 组装Business_Key
//        String simpleName = LeaveBill.class.getSimpleName();
//        String businessKey =  simpleName + ":" + id;
        String businessKey = "LeaveBill:123";
        Task task = taskService.createTaskQuery().processInstanceBusinessKey(businessKey).singleResult();
        task.getId(); // 取出任务ID

        // 历史流程实例
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceBusinessKey(businessKey)
                .singleResult();
        // 使用taskService + 流程实例ID，查询批注信息。
//        List<ActCommentEntity> rtnList = new ArrayList<>();
        List<Comment> comments = taskService.getProcessInstanceComments(historicProcessInstance.getId());
        if(null != comments && comments.size() > 0) {
            for (Comment comment : comments) {
//                ActCommentEntity entity = new  ActCommentEntity();
//                BeanUtil.copyProperties(comment, entity);
//                rtnList.add(entity);
            }
            // 将 rtnList 返回出去。
        }


        // 2、查看我的审批记录
        String assignee = "yuyy";
        long count = historyService.createHistoricTaskInstanceQuery().taskAssignee(assignee).count();
        List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery().taskAssignee(assignee).listPage(1, 10);

    }

    @Test
    public void findTaskIdByProcIntsId() {
        Task task1 = taskService.createTaskQuery().processInstanceId("20001").singleResult();
        Task task2 = taskService.createTaskQuery().taskCandidateUser("yuyy").processInstanceId("20001")
                .taskDefinitionKey("sid-b1914f1a-e11d-4a72-8c71-a8e72e1a33b2")
                .singleResult();

        System.out.println("1任务ID：" + task1.getId());
        System.out.println("2任务ID：" + task2.getId());
    }

}
