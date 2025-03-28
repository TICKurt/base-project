

# 鉴权

## 基于注解的权限认证框架使用手册

### 认证授权框架概述

该框架提供了基于注解的权限认证功能，可以在任何 Spring Boot 项目中轻松集成和使用。主要特性包括：
+ 基于注解的权限控制：通过简单的注解即可实现登录认证、权限验证和角色验证
+ 灵活的配置选项：支持通过配置文件调整各种行为
+ 多种令牌存储方式：支持 Redis、JWT 和数据库三种方式存储令牌
+ URL 白名单：可以设置不需要认证的 URL 路径
+ 多租户支持：内置多租户功能，可以根据需要开启
+ 第三方登录集成：支持微信、QQ、微博等第三方登录
### 具体使用
####  集成步骤
1. 添加依赖
   在需要使用认证功能的模块的 pom.xml 中添加以下依赖：
```  
<dependency>  
    <groupId>com.example</groupId>    
    <artifactId>base-auth</artifactId>    
    <version>${project.version}</version>
</dependency>  
```  
2. 配置文件  
   在 application.yml 或 application.properties 中添加认证配置：

```  
# 认证授权配置  
auth:  
# 是否启用认证  
enabled: true  
# URL白名单  
url-whitelist:  
- /auth/login  
- /auth/register  
- /auth/captcha  
- /auth/refresh-token  
- /swagger-ui/  
- /swagger-resources  
- /v3/api-docs  
- /webjars  
- /doc.html  
  
# 登录相关配置  
login:  
# 是否开启验证码  
captcha-enabled: true  
# 密码最大错误次数  
max-retry-count: 5  
# 锁定时间（分钟）  
lock-time: 30  
  
# Token配置  
token:  
# 令牌自定义标识  
header: Authorization  
# 令牌前缀  
prefix: Bearer  
# 令牌密钥  
secret: your-secret-key  
# 令牌有效期（默认30分钟）  
expire-time: 30  
# token存储方式（1：Redis，2：JWT，3：数据库）  
store-type: 1  
```  

#### 注解使用
框架提供了四个主要注解来实现权限控制：

#####  @RequiresLogin
标记在方法或类上，表示需要登录才能访问：

```java  
@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/info")
    @RequiresLogin
    public ResponseResult<UserInfo> getUserInfo() {
        // 获取并返回用户信息
        return ResponseResult.success(userInfo);
    }
}
```  

##### @RequiresPermission
标记在方法或类上，表示需要特定权限才能访问：

```java  
@RestController
@RequestMapping("/api/user")
public class UserController {

    // 需要单个权限
    @GetMapping("/list")
    @RequiresPermission("system:user:list")
    public ResponseResult<List<UserInfo>> listUsers() {
        // 获取并返回用户列表
        return ResponseResult.success(userList);
    }
    
    // 需要同时具有多个权限（AND逻辑）
    @PostMapping("/update")
    @RequiresPermission(value = {"system:user:edit", "system:user:view"}, logical = Logical.AND)
    public ResponseResult<Void> updateUser(@RequestBody UserInfo user) {
        // 更新用户信息
        return ResponseResult.success();
    }
    
    // 需要具有多个权限中的任意一个（OR逻辑）
    @DeleteMapping("/{id}")
    @RequiresPermission(value = {"system:user:delete", "system:admin"}, logical = Logical.OR)
    public ResponseResult<Void> deleteUser(@PathVariable Long id) {
        // 删除用户
        return ResponseResult.success();
    }
}
```  

##### @RequiresRole
标记在方法或类上，表示需要特定角色才能访问：

```java  
  
@RestController
@RequestMapping("/api/system")
public class SystemController {

    // 需要单个角色
    @GetMapping("/config")
    @RequiresRole("admin")
    public ResponseResult<SystemConfig> getConfig() {
        // 获取系统配置
        return ResponseResult.success(config);
    }
    
    // 需要同时具有多个角色（AND逻辑）
    @PostMapping("/config")
    @RequiresRole(value = {"admin", "system"}, logical = Logical.AND)
    public ResponseResult<Void> updateConfig(@RequestBody SystemConfig config) {
        // 更新系统配置
        return ResponseResult.success();
    }
    
    // 需要具有多个角色中的任意一个（OR逻辑）
    @PostMapping("/restart")
    @RequiresRole(value = {"admin", "ops"}, logical = Logical.OR)
    public ResponseResult<Void> restartSystem() {
        // 重启系统
        return ResponseResult.success();
    }
}
```  

##### @AuthIgnore
标记在方法或类上，表示忽略认证和授权检查：

```java  
  
@RestController
@RequestMapping("/api/public")
public class PublicController {

    @GetMapping("/info")
    @AuthIgnore
    public ResponseResult<Object> getPublicInfo() {
        // 返回公开信息
        return ResponseResult.success(publicInfo);
    }
}
  
```  

#### 在代码中使用AuthService
框架提供了AuthService接口，可以在代码中直接使用它进行认证和授权相关的操作：

```java  
 @Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AuthService authService;
    
    @Override
    public UserInfo getCurrentUserInfo() {
        // 获取当前登录用户
        LoginUserVO loginUser = authService.getLoginUser();
        if (loginUser == null) {
            throw new NotAuthenticatedException("未登录");
        }
        
        // 处理业务逻辑
        return userInfo;
    }
    
    @Override
    public boolean checkPermission(String permission) {
        // 检查当前用户是否有特定权限
        return authService.hasPermission(permission);
    }
    
    @Override
    public boolean checkRole(String role) {
        // 检查当前用户是否有特定角色
        return authService.hasRole(role);
    }
}
```  

### 认证框架配置说明
#### 主要配置项

| 配置项                        | 说明                             | 默认值                        |
| -------------------------- | ------------------------------ | -------------------------- |
| auth.enabled               | 是否启用认证                         | true                       |
| auth.url-whitelist         | URL白名单，不进行认证和授权检查的URL路径        | [/auth/login, …]           |
| auth.login.captcha-enabled | 是否开启验证码                        | true                       |
| auth.login.max-retry-count | 密码最大错误次数                       | 5                          |
| auth.login.lock-time       | 锁定时间（分钟）                       | 30                         |
| auth.token.header          | 令牌自定义标识                        | Authorization              |
| auth.token.prefix          | 令牌前缀                           | Bearer                     |
| auth.token.secret          | 令牌密钥                           | abcdefghijklmnopqrstuvwxyz |
| auth.token.expire-time     | 令牌有效期（分钟）                      | 30                         |
| auth.token.store-type      | token存储方式（1：Redis，2：JWT，3：数据库） | 1                          |

#### 多租户配置

```  
auth:  
  tenant:    # 是否开启多租户  
    enabled: false    # 租户ID参数名，从请求中获取  
    param-name: tenantId    # 租户ID请求头名称  
    header-name: X-Tenant-Id    # 默认租户ID  
    default-tenant-id: "1"```  
  ```
#### 第三方登录配置
```
auth:
  social:
    # 是否开启第三方登录
    enabled: false
    # 是否自动注册
    auto-register: true
    # 微信登录配置
    wechat:
      enabled: false
      app-id: your-wechat-app-id
      app-secret: your-wechat-app-secret
      redirect-uri: https://your-domain.com/auth/wechat/callback
```


### 异常处理
框架定义了两类核心异常：
+ NotAuthenticatedException：未认证异常，当用户未登录或登录已过期时抛出
+ NotAuthorizedException：未授权异常，当用户没有权限访问资源时抛出

框架会自动捕获这些异常并返回相应的HTTP状态码和错误消息：
+ NotAuthenticatedException：返回401（Unauthorized）状态码
+ NotAuthorizedException：返回403（Forbidden）状态码
### 扩展和自定义

#### 实现自定义TokenService
如果预设的三种Token存储方式不满足需求，可以自定义实现TokenService接口：
```java  
@Service
public class CustomTokenServiceImpl implements TokenService {
    // 实现接口方法
}
```  
然后在配置类中注册：
```java  
@Configuration
public class CustomConfig {

    @Bean
    @Primary
    public TokenService tokenService() {
        return new CustomTokenServiceImpl();
    }
}
```  

#### 自定义认证规则
可以通过继承和重写AuthInterceptor或AuthAspect来实现自定义的认证规则：
```java  
@Component
public class CustomAuthInterceptor extends AuthInterceptor {
    // 重写方法自定义认证规则
} 
```  

### 完整示例

```java  
@RestController
@RequestMapping("/api/example")
@RequiresLogin // 类级别注解，表示该控制器下的所有方法都需要登录
public class ExampleController {

    private final AuthService authService;
    
    public ExampleController(AuthService authService) {
        this.authService = authService;
    }
    
    @GetMapping("/public")
    @AuthIgnore // 忽略认证，可以覆盖类级别的RequiresLogin注解
    public ResponseResult<String> publicMethod() {
        return ResponseResult.success("这是一个公开接口");
    }
    
    @GetMapping("/admin")
    @RequiresRole("admin") // 需要admin角色
    public ResponseResult<String> adminMethod() {
        return ResponseResult.success("这是一个管理员接口");
    }
    
    @PostMapping("/resource")
    @RequiresPermission(value = {"resource:create", "resource:edit"}, logical = Logical.OR)
    public ResponseResult<String> createResource() {
        // 在代码中检查权限
        if (authService.hasPermission("resource:special")) {
            // 特殊处理
        }
        
        return ResponseResult.success("创建资源成功");
    }
}
```  
### 注意事项

1. 从确保spring.factories文件正确配置，以启用自动配置

2. 如果使用Redis存储令牌，确保项目中已经配置了Redis

3. 如果使用JWT，确保设置了安全的密钥

4. 在主应用的配置文件中正确配置auth相关属性

5. 注意URL白名单的配置，确保公共资源可以正常访问

6. AOP和拦截器都会对注解进行处理，它们是互补的两种方式

## 总结

以上就是基于注解的权限认证框架的完整实现和使用手册。该框架提供了灵活、可配置的认证授权功能，可以简单地通过注解来控制API的访问权限。根据需要，你可以轻松地扩展和自定义框架的行为。

## 核心组件

1. 注解：定义了权限控制的声明式方法
2. 服务接口：提供认证和授权的核心功能
3. 拦截器：拦截HTTP请求并进行权限验证
4. AOP切面：以切面方式处理注解
5. 配置：支持灵活的配置选项
6. 异常处理：统一处理认证授权异常

通过使用这个框架，可以快速地为应用程序添加安全性，而不必编写大量重复的认证和授权代码。