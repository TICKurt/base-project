# 工作流模块

本模块基于 Flowable 6.8.0 实现，提供工作流引擎功能。

## 快速开始

### 1. 添加依赖

在主项目的 `pom.xml` 中添加依赖：

```xml
<dependency>
    <groupId>com.example</groupId>
    <artifactId>base-workflow</artifactId>
    <version>${project.version}</version>
</dependency>
```

### 2. 配置数据库

确保在 `application.yml` 中配置了正确的数据库连接信息：

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/base_project?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=GMT%2B8
    username: your_username
    password: your_password
```

### 3. 初始化数据库

首次启动时，Flowable 会自动创建必要的数据库表。

### 4. 流程定义文件

流程定义文件（.bpmn20.xml）需要放在 `resources/processes` 目录下。

示例流程文件结构：
```
src/main/resources/
  └── processes/
      └── leave-process.bpmn20.xml
```

### 5. 自定义配置

在 `application.yml` 中可以自定义 Flowable 的配置：

```yaml
flowable:
  # 关闭定时任务JOB
  async-executor-activate: false
  # 数据库策略
  database-schema-update: true
  # 历史记录配置级别
  history-level: full
  # 自动部署验证设置
  check-process-definitions: true
  # 字体配置
  font:
    activity-font-name: 宋体
    label-font-name: 宋体
    annotation-font-name: 宋体
    activity-font-size: 12
    label-font-size: 12
    annotation-font-size: 12
```

## 主要功能

1. 流程定义管理
   - 流程部署
   - 流程设计
   - 流程版本管理

2. 流程实例管理
   - 启动流程
   - 查询流程状态
   - 终止流程

3. 任务管理
   - 待办任务
   - 已办任务
   - 任务委派
   - 任务转办

4. 历史数据
   - 流程历史
   - 任务历史
   - 变量历史

## 注意事项

1. 数据库表
   - Flowable 会自动创建所需的表
   - 表前缀说明：
     - ACT_RE_*: 流程定义和资源
     - ACT_RU_*: 运行时数据
     - ACT_HI_*: 历史数据
     - ACT_ID_*: 身份数据

2. 中文支持
   - 已配置中文字体支持
   - 流程图中的中文显示正常

3. 性能优化
   - 已禁用定时任务，如需启用请修改配置
   - 历史数据级别设置为 FULL，可根据需要调整

## 待实现功能

1. 流程设计器
   - [ ] 集成在线设计器
   - [ ] 自定义组件
   - [ ] 表单设计

2. 流程管理
   - [ ] 流程分类
   - [ ] 流程权限
   - [ ] 流程监控

3. 任务管理
   - [ ] 批量审批
   - [ ] 任务提醒
   - [ ] 任务超时处理

4. 业务集成
   - [ ] 业务表单关联
   - [ ] 消息通知
   - [ ] 数据统计 