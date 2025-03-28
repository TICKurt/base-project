package com.example.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 认证相关配置属性类
 * 对应application.yml中的auth配置
 * 
 * @author example
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "auth")
public class AuthProperties {

    /**
     * 是否启用认证
     */
    private Boolean enabled = true;

    /**
     * URL白名单
     * 这些URL不进行认证和授权检查
     */
    private String[] urlWhitelist = {"/auth/login", "/auth/register", "/auth/captcha", "/auth/refresh-token"};

    /**
     * 登录相关配置
     */
    private LoginConfig login = new LoginConfig();

    /**
     * Token配置
     */
    private TokenConfig token = new TokenConfig();

    /**
     * 注册相关配置
     */
    private RegisterConfig register = new RegisterConfig();
    
    /**
     * 第三方登录配置
     */
    private SocialConfig social = new SocialConfig();
    
    /**
     * 多租户配置
     */
    private TenantConfig tenant = new TenantConfig();

    /**
     * 登录配置类
     */
    @Data
    public static class LoginConfig {
        /**
         * 是否开启验证码
         */
        private Boolean captchaEnabled = true;

        /**
         * 密码最大错误次数
         */
        private Integer maxRetryCount = 5;

        /**
         * 锁定时间（分钟）
         */
        private Integer lockTime = 30;
        
        /**
         * 是否允许多终端同时登录
         */
        private Boolean multipleLoginEnabled = true;
        
        /**
         * 是否记录登录日志
         */
        private Boolean loginLogEnabled = true;
        
        /**
         * 默认登录类型（1：用户名密码，2：手机号验证码，3：邮箱验证码，4：第三方登录）
         */
        private Integer defaultLoginType = 1;
    }

    /**
     * Token配置类
     */
    @Data
    public static class TokenConfig {
        /**
         * 令牌自定义标识
         */
        private String header = "Authorization";

        /**
         * 令牌前缀
         */
        private String prefix = "Bearer";

        /**
         * 令牌密钥
         */
        private String secret = "abcdefghijklmnopqrstuvwxyz";

        /**
         * 令牌有效期（默认30分钟）
         */
        private Integer expireTime = 30;
        
        /**
         * 记住我token有效期（默认7天）
         */
        private Integer rememberMeExpireTime = 7 * 24 * 60;
        
        /**
         * 刷新token有效期（默认7天）
         */
        private Integer refreshTokenExpireTime = 7 * 24 * 60;
        
        /**
         * token存储方式（1：Redis，2：JWT，3：数据库）
         */
        private Integer storeType = 1;
    }

    /**
     * 注册配置类
     */
    @Data
    public static class RegisterConfig {
        /**
         * 是否开启注册功能
         */
        private Boolean enabled = true;

        /**
         * 是否需要验证码
         */
        private Boolean captchaEnabled = true;

        /**
         * 默认注册类型（1：用户名密码，2：手机号验证码，3：邮箱验证码，4：第三方注册）
         */
        private Integer defaultRegisterType = 1;
        
        /**
         * 注册用户默认状态（0：禁用，1：正常）
         */
        private Integer defaultStatus = 1;
        
        /**
         * 是否需要邮箱验证
         */
        private Boolean emailVerificationEnabled = false;
        
        /**
         * 是否需要手机验证
         */
        private Boolean mobileVerificationEnabled = false;
    }
    
    /**
     * 第三方登录配置类
     */
    @Data
    public static class SocialConfig {
        /**
         * 是否开启第三方登录
         */
        private Boolean enabled = false;
        
        /**
         * 微信登录配置
         */
        private WechatConfig wechat = new WechatConfig();
        
        /**
         * QQ登录配置
         */
        private QQConfig qq = new QQConfig();
        
        /**
         * 微博登录配置
         */
        private WeiboConfig weibo = new WeiboConfig();
        
        /**
         * 是否自动注册
         * 第三方账号首次登录时是否自动创建用户
         */
        private Boolean autoRegister = true;
        
        /**
         * 第三方登录用户默认状态（0：禁用，1：正常）
         */
        private Integer defaultStatus = 1;
        
        /**
         * 微信登录配置类
         */
        @Data
        public static class WechatConfig {
            /**
             * 是否开启微信登录
             */
            private Boolean enabled = false;
            
            /**
             * 应用ID
             */
            private String appId;
            
            /**
             * 应用密钥
             */
            private String appSecret;
            
            /**
             * 回调地址
             */
            private String redirectUri;
        }
        
        /**
         * QQ登录配置类
         */
        @Data
        public static class QQConfig {
            /**
             * 是否开启QQ登录
             */
            private Boolean enabled = false;
            
            /**
             * 应用ID
             */
            private String appId;
            
            /**
             * 应用密钥
             */
            private String appSecret;
            
            /**
             * 回调地址
             */
            private String redirectUri;
        }
        
        /**
         * 微博登录配置类
         */
        @Data
        public static class WeiboConfig {
            /**
             * 是否开启微博登录
             */
            private Boolean enabled = false;
            
            /**
             * 应用ID
             */
            private String appId;
            
            /**
             * 应用密钥
             */
            private String appSecret;
            
            /**
             * 回调地址
             */
            private String redirectUri;
        }
    }
    
    /**
     * 多租户配置类
     */
    @Data
    public static class TenantConfig {
        /**
         * 是否开启多租户
         */
        private Boolean enabled = false;
        
        /**
         * 租户ID参数名，从请求中获取
         */
        private String paramName = "tenantId";
        
        /**
         * 租户ID请求头名称
         */
        private String headerName = "X-Tenant-Id";
        
        /**
         * 默认租户ID
         */
        private String defaultTenantId = "1";
        
        /**
         * 排除的租户ID
         * 这些租户ID不会被租户过滤器处理
         */
        private String[] excludeTenantIds = {"1"};
        
        /**
         * 排除的表名
         * 这些表不会进行多租户处理
         */
        private String[] excludeTables = {
            "sys_tenant", "sys_dict_type", "sys_dict_item", "sys_config"
        };
    }
} 