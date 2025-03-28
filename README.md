# Base Project 基础框架项目

## 项目介绍

本项目是一个基于主流Java技术栈的基础框架项目，采用DDD领域驱动设计思想和模块化架构，旨在提供一个可扩展的开发平台。项目已增强支持企业级组织结构管理、中国特色工作流和设备管理功能。

## 技术栈

- 核心框架：Spring Boot 2.7.x
- ORM框架：MyBatis Plus 3.5.x
- 数据库：MySQL 8.0（支持多数据源配置）
- 缓存：Redis
- 消息队列：RocketMQ
- 工作流引擎：Flowable 6.7.x
- 权限认证：Spring Security + JWT
- 接口文档：apifox
- 项目构建：Maven 3.8+
- 开发环境：JDK 1.8
- 微信开发：WxJava（微信Java开发工具包）

## 项目架构

项目采用DDD领域驱动设计，使用Maven多模块管理，在保持代码模块化组织的同时实现统一打包部署：

### 模块结构
- base-project：父工程，统一管理依赖版本和构建配置
- base-boot：应用启动模块，整合所有其他模块
- base-core：核心基础设施，包含通用工具、异常处理等
- base-common：公共模块，存放共享组件、工具类等
- base-auth：认证与授权模块，实现用户管理、权限控制
- base-system：系统管理模块，提供组织、菜单、角色等管理
- base-mq：消息队列模块，处理异步消息和事务消息
- base-workflow：工作流模块，集成Flowable引擎
- base-weixin：微信小程序模块，集成WxJava
- base-log：日志服务，记录系统操作日志和异常日志
- base-api：外部API模块，提供对外服务接口
- base-business：业务模块（可按需增加）
- base-device：设备管理模块（新增）

### 模块内部分层
每个业务模块都遵循以下分层架构：
- interfaces：接口层，包含controller和API定义
- application：应用层，处理业务流程编排
- domain：领域层，核心业务逻辑
- infrastructure：基础设施层，包含dao、外部服务等

### 领域模型
- entity：领域实体
- vo：视图对象
- dto：数据传输对象
- repository：仓储接口

## 项目特性

### 架构设计
- 基于DDD的模块化架构设计
- 统一的依赖管理和构建配置
- 模块间低耦合，通过Spring上下文和事件机制通信
- 集成MyBatis Plus，实现代码生成
- 支持多数据源配置，实现动态数据源切换
- 集成RocketMQ，支持异步消息和事务消息处理
- 集成Flowable工作流引擎，支持流程设计和任务管理

### 安全特性
- 基于JWT的认证授权
- 密码加密存储与复杂度策略
- XSS防护和SQL注入防护
- 敏感数据加密
- 接口权限控制

### 运维监控
- 集成Spring Boot Actuator，实现应用监控
- 数据库监控和慢SQL分析
- 服务健康检查
- 系统告警通知

### 性能优化
- 多级缓存策略（本地缓存+Redis）
- 数据库读写分离
- 索引优化和SQL调优
- 大数据量分页优化
- 接口性能监控

### 微信小程序功能
- 用户管理：授权登录、用户信息维护
- 消息推送：模板消息、订阅消息
- 小程序功能：二维码生成、数据统计
- 微信支付：支付功能集成
- 安全管理：数据加密、签名验证

### 增强的组织结构管理
- 支持更复杂的企业级组织架构
- 多维度的职位和职级体系
- 支持组织间多种关系类型
- 组织扩展信息（统一社会信用代码、法人代表等）
- 区域和地址体系
- 支持用户多职位、主副职位设置

### 中国特色工作流
- 支持或签、会签、依次审批等多种审批模式
- 灵活的流程干预机制（加签、转办、委托等）
- 自定义表单与流程定义关联
- 完善的流程催办和提醒机制
- 任务超时自动处理策略
- 流程统计和分析功能
- 流程模板功能

### 设备管理功能
- 基础设备生命周期管理
- 设备分类管理
- 设备状态监控
- 设备告警功能
- 车辆管理与设备绑定
- 灵活的功能模块化配置，可选开启：
  - 设备维修管理
  - 设备巡检管理
  - 设备附件管理
  - 车辆维保管理
- 设备二维码支持

### 开发规范
- 统一异常处理，规范错误码
- 统一API响应格式
- 集成Springdoc OpenAPI，自动生成接口文档
- 完善的日志记录
- 代码质量检查

## 快速开始

### 环境要求

- JDK 1.8
- Maven 3.8+
- MySQL 8.0+
- Redis 6.0+

### 获取代码

```bash
git clone https://github.com/TICKurt/base-project.git
cd base-project
```

### 编译构建

```bash
mvn clean package -Dmaven.test.skip=true
```

### 初始化数据库

```bash
# 创建数据库
mysql -u root -p < docs/sql/init-database.sql

# 初始化表结构和基础数据
mysql -u root -p base-project < docs/sql/auth_system.sql

# 初始化增强表结构（可选）
mysql -u root -p base-project < docs/sql/init-enhanced-schema.sql

# 如需开启更多设备管理功能，请修改配置文件并执行相应的SQL
```

### 启动应用

```bash
java -jar base-boot/target/base-boot.jar --spring.profiles.active=dev
```

访问 http://localhost:8080 即可看到系统登录界面

## 模块说明

| 模块名称 | 描述 |
|---------|------|
| base-boot | 应用启动模块，整合所有其他模块 |
| base-core | 核心基础设施，包含通用工具、异常处理等 |
| base-common | 公共模块，存放共享组件、工具类等 |
| base-auth | 认证与授权模块，实现用户管理、权限控制 |
| base-system | 系统管理模块，提供组织、菜单、角色等管理 |
| base-mq | 消息队列模块，处理异步消息和事务消息 |
| base-workflow | 工作流模块，集成Flowable引擎 |
| base-weixin | 微信小程序模块，集成WxJava |
| base-log | 日志服务，记录系统操作日志和异常日志 |
| base-api | 外部API模块，提供对外服务接口 |
| base-business | 业务模块（可按需增加） |
| base-device | 设备管理模块，提供设备全生命周期管理 |

## 开发工具

- **构建工具**: Maven
- **CI/CD**: Jenkins
- **容器化**: Docker
- **版本控制**: Git

## 开发指南

详细的开发文档请参考 [开发指南](docs/development-guide.md)

### 代码规范

本项目采用[阿里巴巴Java开发手册](https://github.com/alibaba/p3c)作为开发规范

### 提交规范

提交信息格式：`类型(模块): 描述`

类型包括：
- feat: 新功能
- fix: 修复bug
- docs: 文档变更
- style: 代码格式调整
- refactor: 重构
- perf: 性能优化
- test: 测试相关
- chore: 构建过程或辅助工具变动

示例：`feat(auth): 添加手机号登录功能`

## 贡献

1. Fork 本仓库
2. 创建你的特性分支 (`git checkout -b feature/amazing-feature`)
3. 提交你的改动 (`git commit -m 'feat: add some amazing feature'`)
4. 推送到分支 (`git push origin feature/amazing-feature`)
5. 创建一个 Pull Request

## 许可证

Distributed under the MIT License. See `LICENSE` for more information.

## 联系我们

- 项目仓库: [https://github.com/TICKurt/base-project](https://github.com/TICKurt/base-project)
- 问题反馈: [GitHub Issues](https://github.com/TICKurt/base-project/issues)