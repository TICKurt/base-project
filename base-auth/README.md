# 认证与授权模块 (Base Auth)

本模块提供完整的认证授权功能，包括用户管理、角色管理、权限控制、组织机构管理等功能。

## 功能特性

- 基于RBAC (Role-Based Access Control) 权限模型
- 支持多种认证方式 (用户名密码、手机验证码、邮箱验证码、第三方登录)
- 基于JWT的无状态认证
- 支持细粒度的权限控制 (接口级别、方法级别)
- 完整的用户管理和角色管理
- 支持组织机构管理和组织关系
- 多租户支持
- 集成Spring Security
- 基于注解的权限控制
- 支持数据权限

## 使用方法

### 引入依赖

在你的项目的`pom.xml`中添加依赖：

```xml
<dependency>
    <groupId>com.example</groupId>
    <artifactId>base-auth</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

### 配置说明

在`application.yml`中添加认证相关配置：

```yaml
auth:
  # 是否启用认证
  enabled: true
  # URL白名单，不进行认证和授权检查
  url-whitelist:
    - /auth/login
    - /auth/register
    - /auth/captcha
    - /auth/refresh-token
    - /doc.html
    - /swagger-resources
    - /v3/api-docs
  # 登录相关配置
  login:
    # 是否开启验证码
    captcha-enabled: true
    # 密码最大错误次数
    max-retry-count: 5
    # 锁定时间（分钟）
    lock-time: 30
    # 是否允许多终端同时登录
    multiple-login-enabled: true
  # Token配置
  token:
    # 令牌自定义标识
    header: Authorization
    # 令牌前缀
    prefix: Bearer
    # 令牌密钥
    secret: abcdefghijklmnopqrstuvwxyz
    # 令牌有效期（默认30分钟）
    expire-time: 30
    # 刷新令牌有效期（默认7天）
    refresh-token-expire-time: 10080
  # 多租户配置
  tenant:
    # 是否开启多租户
    enabled: true
    # 租户ID传递方式
    # 可选值: param(请求参数), header(请求头), cookie(Cookie)
    type: header
    # 租户ID参数名
    param-name: tenantId
```

### 权限注解使用

1. 标记访问某个接口需要登录

```java
@GetMapping("/user/info")
@RequiresLogin
public ResponseResult<UserVO> getUserInfo() {
    // ...
}
```

2. 标记访问某个接口需要特定权限

```java
@GetMapping("/user/list")
@RequiresPermission("system:user:list")
public ResponseResult<List<UserVO>> getUserList() {
    // ...
}
```

3. 标记访问某个接口需要多个权限 (AND关系)

```java
@PostMapping("/user")
@RequiresPermission(value = {"system:user:list", "system:user:add"}, logical = Logical.AND)
public ResponseResult<String> addUser(@RequestBody UserDTO user) {
    // ...
}
```

4. 标记访问某个接口需要满足多个权限中的任意一个 (OR关系)

```java
@DeleteMapping("/user/{userId}")
@RequiresPermission(value = {"system:user:delete", "system:user:admin"}, logical = Logical.OR)
public ResponseResult<Void> deleteUser(@PathVariable String userId) {
    // ...
}
```

5. 标记访问某个接口需要特定角色

```java
@PutMapping("/system/config")
@RequiresRole("admin")
public ResponseResult<Void> updateConfig(@RequestBody ConfigDTO config) {
    // ...
}
```

6. 忽略权限检查

```java
@GetMapping("/public/data")
@AuthIgnore
public ResponseResult<List<DataVO>> getPublicData() {
    // ...
}
```

### 手动进行权限检查

在业务代码中手动进行权限检查：

```java
@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private AuthService authService;
    
    public void doSomething() {
        // 检查是否已登录
        if (!authService.isAuthenticated()) {
            throw new NotAuthenticatedException("请先登录");
        }
        
        // 检查是否有特定权限
        if (!authService.hasPermission("system:user:edit")) {
            throw new NotAuthorizedException("权限不足");
        }
        
        // 检查是否有特定角色
        if (!authService.hasRole("admin")) {
            throw new NotAuthorizedException("角色不足");
        }
        
        // 获取当前登录用户
        LoginUserVO loginUser = authService.getLoginUser();
        
        // 业务逻辑...
    }
}
```

## 待办事项

以下是模块待实现的功能：

- [ ] 组织机构管理功能实现
- [x] 角色管理功能实现
- [ ] 菜单管理功能实现
- [ ] 字典管理功能实现
- [ ] 配置管理功能实现
- [ ] 职位和职级管理功能实现
- [ ] 租户管理功能实现
- [ ] 数据权限功能实现
- [ ] 用户组管理功能实现
- [ ] 审批规则管理功能实现（配合工作流）
- [ ] 审批代理功能实现（配合工作流）
- [ ] API资源管理功能实现
- [ ] 操作日志管理功能实现
- [ ] 第三方登录集成
- [ ] 登录日志记录功能实现
- [ ] 密码策略功能实现

## 已实现功能

- [x] 用户管理基本功能 (CRUD)
- [x] 角色管理基本功能 (CRUD)
- [x] 基于注解的权限控制
- [x] 登录认证功能
 