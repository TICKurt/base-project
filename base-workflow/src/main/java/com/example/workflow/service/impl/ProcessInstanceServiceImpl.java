package com.example.workflow.service.impl;

import com.example.workflow.model.dto.ProcessInstanceDTO;
import com.example.workflow.model.vo.ProcessInstanceVO;
import com.example.workflow.service.ProcessInstanceService;
import com.example.workflow.utils.FlowableUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.runtime.ProcessInstanceQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 流程实例服务实现类
 *
 * @author Author
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessInstanceServiceImpl implements ProcessInstanceService {

    private final RuntimeService runtimeService;
    private final TaskService taskService;
    private final HistoryService historyService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String startProcessInstance(String processDefinitionId, String businessKey, Map<String, Object> variables) {
        if (StringUtils.isBlank(processDefinitionId)) {
            throw new IllegalArgumentException("流程定义ID不能为空");
        }
        
        try {
            log.info("开始启动流程实例，流程定义ID：{}，业务标识：{}", processDefinitionId, businessKey);
            
            ProcessInstance processInstance;
            
            if (variables != null) {
                processInstance = runtimeService.startProcessInstanceById(processDefinitionId, businessKey, variables);
            } else {
                processInstance = runtimeService.startProcessInstanceById(processDefinitionId, businessKey, new HashMap<>());
            }
            
            log.info("流程实例启动成功，流程实例ID：{}", processInstance.getId());
            return processInstance.getId();
        } catch (Exception e) {
            log.error("启动流程实例失败，流程定义ID：{}，业务标识：{}", processDefinitionId, businessKey, e);
            throw new RuntimeException("启动流程实例失败：" + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String startProcessInstanceByKey(String processDefinitionKey, String businessKey, Map<String, Object> variables) {
        if (StringUtils.isBlank(processDefinitionKey)) {
            throw new IllegalArgumentException("流程定义标识不能为空");
        }
        
        try {
            log.info("开始启动流程实例，流程定义标识：{}，业务标识：{}", processDefinitionKey, businessKey);
            
            ProcessInstance processInstance;
            
            if (variables != null) {
                processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey, variables);
            } else {
                processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey, new HashMap<>());
            }
            
            log.info("流程实例启动成功，流程实例ID：{}", processInstance.getId());
            return processInstance.getId();
        } catch (Exception e) {
            log.error("启动流程实例失败，流程定义标识：{}，业务标识：{}", processDefinitionKey, businessKey, e);
            throw new RuntimeException("启动流程实例失败：" + e.getMessage(), e);
        }
    }

    @Override
    public List<ProcessInstanceVO> listProcessInstances(String processDefinitionKey, String businessKey, String startUserId, Boolean active, Boolean suspended) {
        try {
            log.info("查询流程实例列表，流程定义标识：{}，业务标识：{}，发起人ID：{}", processDefinitionKey, businessKey, startUserId);
            
            // 创建查询对象
            ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery();

            // 设置查询条件
            if (StringUtils.isNotBlank(processDefinitionKey)) {
                query.processDefinitionKey(processDefinitionKey);
            }
            if (StringUtils.isNotBlank(businessKey)) {
                query.processInstanceBusinessKey(businessKey);
            }
            if (StringUtils.isNotBlank(startUserId)) {
                query.startedBy(startUserId);
            }
            if (Boolean.TRUE.equals(active)) {
                query.active();
            }
            if (Boolean.TRUE.equals(suspended)) {
                query.suspended();
            }

            // 执行查询
            List<ProcessInstance> processInstances = query
                    .orderByProcessInstanceId()
                    .desc()
                    .list();
            
            log.info("查询到流程实例数量：{}", processInstances.size());

            // 转换为VO
            return processInstances.stream()
                    .map(this::convertToProcessInstanceVO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("查询流程实例列表失败", e);
            throw new RuntimeException("查询流程实例列表失败：" + e.getMessage(), e);
        }
    }

    @Override
    public ProcessInstanceVO getProcessInstanceById(String processInstanceId) {
        if (StringUtils.isBlank(processInstanceId)) {
            throw new IllegalArgumentException("流程实例ID不能为空");
        }
        
        try {
            log.info("获取流程实例详情，流程实例ID：{}", processInstanceId);
            
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(processInstanceId)
                    .singleResult();

            if (processInstance == null) {
                log.warn("未找到流程实例，流程实例ID：{}", processInstanceId);
                return null;
            }

            return convertToProcessInstanceVO(processInstance);
        } catch (Exception e) {
            log.error("获取流程实例详情失败，流程实例ID：{}", processInstanceId, e);
            throw new RuntimeException("获取流程实例详情失败：" + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void suspendProcessInstance(String processInstanceId) {
        if (StringUtils.isBlank(processInstanceId)) {
            throw new IllegalArgumentException("流程实例ID不能为空");
        }
        
        try {
            log.info("开始挂起流程实例，流程实例ID：{}", processInstanceId);
            
            // 检查流程实例是否存在
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(processInstanceId)
                    .singleResult();
            
            if (processInstance == null) {
                throw new RuntimeException("流程实例不存在：" + processInstanceId);
            }
            
            // 检查流程实例是否已挂起
            if (processInstance.isSuspended()) {
                log.info("流程实例已经挂起，无需操作，流程实例ID：{}", processInstanceId);
                return;
            }
            
            runtimeService.suspendProcessInstanceById(processInstanceId);
            log.info("流程实例挂起成功，流程实例ID：{}", processInstanceId);
        } catch (Exception e) {
            log.error("挂起流程实例失败，流程实例ID：{}", processInstanceId, e);
            throw new RuntimeException("挂起流程实例失败：" + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void activateProcessInstance(String processInstanceId) {
        if (StringUtils.isBlank(processInstanceId)) {
            throw new IllegalArgumentException("流程实例ID不能为空");
        }
        
        try {
            log.info("开始激活流程实例，流程实例ID：{}", processInstanceId);
            
            // 检查流程实例是否存在
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(processInstanceId)
                    .singleResult();
            
            if (processInstance == null) {
                throw new RuntimeException("流程实例不存在：" + processInstanceId);
            }
            
            // 检查流程实例是否已激活
            if (!processInstance.isSuspended()) {
                log.info("流程实例已经激活，无需操作，流程实例ID：{}", processInstanceId);
                return;
            }
            
            runtimeService.activateProcessInstanceById(processInstanceId);
            log.info("流程实例激活成功，流程实例ID：{}", processInstanceId);
        } catch (Exception e) {
            log.error("激活流程实例失败，流程实例ID：{}", processInstanceId, e);
            throw new RuntimeException("激活流程实例失败：" + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteProcessInstance(String processInstanceId, String deleteReason) {
        if (StringUtils.isBlank(processInstanceId)) {
            throw new IllegalArgumentException("流程实例ID不能为空");
        }
        
        try {
            log.info("开始删除流程实例，流程实例ID：{}，删除原因：{}", processInstanceId, deleteReason);
            
            // 检查流程实例是否存在
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(processInstanceId)
                    .singleResult();
            
            if (processInstance == null) {
                log.warn("流程实例不存在，可能已经被删除，流程实例ID：{}", processInstanceId);
                return;
            }
            
            runtimeService.deleteProcessInstance(processInstanceId, deleteReason);
            log.info("流程实例删除成功，流程实例ID：{}", processInstanceId);
        } catch (Exception e) {
            log.error("删除流程实例失败，流程实例ID：{}", processInstanceId, e);
            throw new RuntimeException("删除流程实例失败：" + e.getMessage(), e);
        }
    }

    @Override
    public Map<String, Object> getProcessInstanceVariables(String processInstanceId) {
        if (StringUtils.isBlank(processInstanceId)) {
            throw new IllegalArgumentException("流程实例ID不能为空");
        }
        
        try {
            log.info("获取流程变量，流程实例ID：{}", processInstanceId);
            
            // 检查流程实例是否存在
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(processInstanceId)
                    .singleResult();
            
            if (processInstance == null) {
                throw new RuntimeException("流程实例不存在：" + processInstanceId);
            }
            
            Map<String, Object> variables = runtimeService.getVariables(processInstanceId);
            log.info("获取流程变量成功，流程实例ID：{}，变量数量：{}", processInstanceId, variables.size());
            return variables;
        } catch (Exception e) {
            log.error("获取流程变量失败，流程实例ID：{}", processInstanceId, e);
            throw new RuntimeException("获取流程变量失败：" + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setProcessInstanceVariables(String processInstanceId, Map<String, Object> variables) {
        if (StringUtils.isBlank(processInstanceId)) {
            throw new IllegalArgumentException("流程实例ID不能为空");
        }
        
        if (variables == null || variables.isEmpty()) {
            throw new IllegalArgumentException("流程变量不能为空");
        }
        
        try {
            log.info("设置流程变量，流程实例ID：{}，变量数量：{}", processInstanceId, variables.size());
            
            // 检查流程实例是否存在
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(processInstanceId)
                    .singleResult();
            
            if (processInstance == null) {
                throw new RuntimeException("流程实例不存在：" + processInstanceId);
            }
            
            runtimeService.setVariables(processInstanceId, variables);
            log.info("设置流程变量成功，流程实例ID：{}", processInstanceId);
        } catch (Exception e) {
            log.error("设置流程变量失败，流程实例ID：{}", processInstanceId, e);
            throw new RuntimeException("设置流程变量失败：" + e.getMessage(), e);
        }
    }

    @Override
    public List<ProcessInstanceVO> listProcessInstancesByPage(String processDefinitionKey, String businessKey, String startUserId, Boolean active, Boolean suspended, int pageNum, int pageSize) {
        if (pageNum <= 0) {
            throw new IllegalArgumentException("页码必须大于0");
        }
        if (pageSize <= 0) {
            throw new IllegalArgumentException("每页数量必须大于0");
        }
        
        try {
            log.info("分页查询流程实例列表，流程定义标识：{}，业务标识：{}，发起人ID：{}，页码：{}，每页数量：{}", 
                    processDefinitionKey, businessKey, startUserId, pageNum, pageSize);
            
            // 创建查询对象
            ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery();

            // 设置查询条件
            if (StringUtils.isNotBlank(processDefinitionKey)) {
                query.processDefinitionKey(processDefinitionKey);
            }
            if (StringUtils.isNotBlank(businessKey)) {
                query.processInstanceBusinessKey(businessKey);
            }
            if (StringUtils.isNotBlank(startUserId)) {
                query.startedBy(startUserId);
            }
            if (Boolean.TRUE.equals(active)) {
                query.active();
            }
            if (Boolean.TRUE.equals(suspended)) {
                query.suspended();
            }

            // 计算起始位置
            int startIndex = (pageNum - 1) * pageSize;
            
            // 执行分页查询
            List<ProcessInstance> processInstances = query
                    .orderByProcessInstanceId()
                    .desc()
                    .listPage(startIndex, pageSize);
            
            log.info("分页查询流程实例成功，共查询到{}条数据", processInstances.size());

            // 转换为VO
            return processInstances.stream()
                    .map(this::convertToProcessInstanceVO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("分页查询流程实例失败", e);
            throw new RuntimeException("分页查询流程实例失败：" + e.getMessage(), e);
        }
    }

    @Override
    public long countProcessInstances(String processDefinitionKey, String businessKey, String startUserId, Boolean active, Boolean suspended) {
        try {
            log.info("统计流程实例数量，流程定义标识：{}，业务标识：{}，发起人ID：{}", 
                    processDefinitionKey, businessKey, startUserId);
            
            // 创建查询对象
            ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery();

            // 设置查询条件
            if (StringUtils.isNotBlank(processDefinitionKey)) {
                query.processDefinitionKey(processDefinitionKey);
            }
            if (StringUtils.isNotBlank(businessKey)) {
                query.processInstanceBusinessKey(businessKey);
            }
            if (StringUtils.isNotBlank(startUserId)) {
                query.startedBy(startUserId);
            }
            if (Boolean.TRUE.equals(active)) {
                query.active();
            }
            if (Boolean.TRUE.equals(suspended)) {
                query.suspended();
            }

            // 执行统计查询
            long count = query.count();
            
            log.info("统计流程实例数量成功，共{}条数据", count);
            return count;
        } catch (Exception e) {
            log.error("统计流程实例数量失败", e);
            throw new RuntimeException("统计流程实例数量失败：" + e.getMessage(), e);
        }
    }

    @Override
    public List<ProcessInstanceVO> getProcessInstancesByBusinessKey(String businessKey) {
        if (StringUtils.isBlank(businessKey)) {
            throw new IllegalArgumentException("业务标识不能为空");
        }
        
        try {
            log.info("根据业务标识查询流程实例，业务标识：{}", businessKey);
            
            // 执行查询
            List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery()
                    .processInstanceBusinessKey(businessKey)
                    .orderByProcessInstanceId()
                    .desc()
                    .list();
            
            log.info("根据业务标识查询流程实例成功，共查询到{}条数据", processInstances.size());

            // 转换为VO
            return processInstances.stream()
                    .map(this::convertToProcessInstanceVO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("根据业务标识查询流程实例失败，业务标识：{}", businessKey, e);
            throw new RuntimeException("根据业务标识查询流程实例失败：" + e.getMessage(), e);
        }
    }

    /**
     * 将流程实例转换为VO
     */
    private ProcessInstanceVO convertToProcessInstanceVO(ProcessInstance processInstance) {
        ProcessInstanceVO vo = new ProcessInstanceVO();
        
        vo.setId(processInstance.getId());
        vo.setProcessDefinitionId(processInstance.getProcessDefinitionId());
        vo.setProcessDefinitionKey(processInstance.getProcessDefinitionKey());
        vo.setProcessDefinitionName(processInstance.getProcessDefinitionName());
        vo.setProcessDefinitionVersion(processInstance.getProcessDefinitionVersion());
        vo.setDeploymentId(processInstance.getDeploymentId());
        vo.setBusinessKey(processInstance.getBusinessKey());
        vo.setName(processInstance.getName());
        vo.setDescription(processInstance.getDescription());
        vo.setTenantId(processInstance.getTenantId());
        vo.setSuspended(processInstance.isSuspended());
        vo.setEnded(false); // 运行中的流程实例
        
        // 获取父流程实例ID和根流程实例ID
        vo.setParentId(processInstance.getParentId());
        vo.setRootProcessInstanceId(processInstance.getRootProcessInstanceId());
        
        // 获取当前活动ID
        if (processInstance.getActivityId() != null) {
            vo.setActivityId(processInstance.getActivityId());
        }
        
        // 获取流程变量
        try {
            Map<String, Object> variables = runtimeService.getVariables(processInstance.getId());
            vo.setProcessVariables(variables);
        } catch (Exception e) {
            log.warn("获取流程变量失败，流程实例ID：{}", processInstance.getId(), e);
        }
        
        // 获取流程发起人和发起时间
        try {
            org.flowable.engine.history.HistoricProcessInstance historicProcessInstance = 
                    historyService.createHistoricProcessInstanceQuery()
                            .processInstanceId(processInstance.getId())
                            .singleResult();
            
            if (historicProcessInstance != null) {
                vo.setStartUserId(historicProcessInstance.getStartUserId());
                vo.setStartTime(historicProcessInstance.getStartTime());
                vo.setEndTime(historicProcessInstance.getEndTime());
                vo.setDurationInMillis(historicProcessInstance.getDurationInMillis());
                vo.setDeleteReason(historicProcessInstance.getDeleteReason());
                
                // 设置开始活动ID
                if (historicProcessInstance.getStartActivityId() != null) {
                    vo.setActivityId(historicProcessInstance.getStartActivityId());
                }
                
                // 判断流程是否已结束
                if (historicProcessInstance.getEndTime() != null) {
                    vo.setEnded(true);
                }
            }
        } catch (Exception e) {
            log.warn("获取流程历史信息失败，流程实例ID：{}", processInstance.getId(), e);
        }
        
        return vo;
    }
} 