-- 用户表
CREATE TABLE `sys_user`
(
    `id`                      varchar(36)  NOT NULL COMMENT '主键ID（UUID）',
    `username`                varchar(50)  NOT NULL COMMENT '用户名',
    `password`                varchar(100) NOT NULL COMMENT '密码（加密存储）',
    `nickname`                varchar(50)           DEFAULT NULL COMMENT '昵称',
    `real_name`               varchar(50)           DEFAULT NULL COMMENT '真实姓名',
    `avatar`                  varchar(255)          DEFAULT NULL COMMENT '头像URL',
    `email`                   varchar(100)          DEFAULT NULL COMMENT '邮箱',
    `mobile`                  varchar(20)           DEFAULT NULL COMMENT '手机号',
    `gender`                  tinyint(1)            DEFAULT 0 COMMENT '性别（0-未知，1-男，2-女）',
    `birth_date`              date                  DEFAULT NULL COMMENT '出生日期',
    `status`                  tinyint(1)            DEFAULT 1 COMMENT '状态（0-禁用，1-正常，2-锁定）',
    `org_id`                  varchar(36)           DEFAULT NULL COMMENT '所属组织ID',
    `position`                varchar(50)           DEFAULT NULL COMMENT '职位',
    `work_no`                 varchar(30)           DEFAULT NULL COMMENT '工号',
    `user_type`               tinyint(1)            DEFAULT 2 COMMENT '用户类型（0-超级管理员，1-管理员，2-普通用户）',
    `last_login_ip`           varchar(50)           DEFAULT NULL COMMENT '最后登录IP',
    `last_login_time`         datetime              DEFAULT NULL COMMENT '最后登录时间',
    `login_count`             int(11)               DEFAULT 0 COMMENT '登录次数',
    `pwd_reset_time`          datetime              DEFAULT NULL COMMENT '密码重置时间',
    `pwd_expire_time`         datetime              DEFAULT NULL COMMENT '密码过期时间',
    `account_non_expired`     tinyint(1)            DEFAULT 1 COMMENT '账号是否未过期（1-未过期，0-已过期）',
    `account_non_locked`      tinyint(1)            DEFAULT 1 COMMENT '账号是否未锁定（1-未锁定，0-已锁定）',
    `credentials_non_expired` tinyint(1)            DEFAULT 1 COMMENT '密码是否未过期（1-未过期，0-已过期）',
    `create_by`               varchar(36)           DEFAULT NULL COMMENT '创建人',
    `create_time`             datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`               varchar(36)           DEFAULT NULL COMMENT '更新人',
    `update_time`             datetime              DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`              tinyint(1)            DEFAULT 0 COMMENT '是否删除（0-否，1-是）',
    `tenant_id`               varchar(36)           DEFAULT NULL COMMENT '租户ID',
    `remark`                  varchar(255)          DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`),
    UNIQUE KEY `uk_mobile` (`mobile`),
    UNIQUE KEY `uk_email` (`email`),
    KEY `idx_org_id` (`org_id`),
    KEY `idx_status` (`status`),
    KEY `idx_create_time` (`create_time`),
    KEY `idx_tenant_id` (`tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统用户表';

-- 组织机构表
CREATE TABLE `sys_organization`
(
    `id`              varchar(36)  NOT NULL COMMENT '主键ID（UUID）',
    `parent_id`       varchar(36)           DEFAULT NULL COMMENT '父级组织ID',
    `name`            varchar(100) NOT NULL COMMENT '组织名称',
    `code`            varchar(50)           DEFAULT NULL COMMENT '组织编码',
    `org_type`        tinyint(1)            DEFAULT 1 COMMENT '组织类型（1-公司，2-部门，3-团队，4-小组）',
    `level`           int(11)               DEFAULT 1 COMMENT '组织层级',
    `path`            varchar(255)          DEFAULT NULL COMMENT '组织路径（存储所有父级ID，格式如：/根ID/父ID/）',
    `sort`            int(11)               DEFAULT 0 COMMENT '排序号',
    `leader_id`       varchar(36)           DEFAULT NULL COMMENT '负责人ID',
    `leader_name`     varchar(50)           DEFAULT NULL COMMENT '负责人姓名',
    `contact_phone`   varchar(20)           DEFAULT NULL COMMENT '联系电话',
    `contact_address` varchar(255)          DEFAULT NULL COMMENT '联系地址',
    `status`          tinyint(1)            DEFAULT 1 COMMENT '状态（0-禁用，1-正常）',
    `create_by`       varchar(36)           DEFAULT NULL COMMENT '创建人',
    `create_time`     datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`       varchar(36)           DEFAULT NULL COMMENT '更新人',
    `update_time`     datetime              DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`      tinyint(1)            DEFAULT 0 COMMENT '是否删除（0-否，1-是）',
    `tenant_id`       varchar(36)           DEFAULT NULL COMMENT '租户ID',
    `remark`          varchar(255)          DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_code` (`code`),
    KEY `idx_parent_id` (`parent_id`),
    KEY `idx_sort` (`sort`),
    KEY `idx_tenant_id` (`tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='组织机构表';

-- 角色表
CREATE TABLE `sys_role`
(
    `id`          varchar(36) NOT NULL COMMENT '主键ID（UUID）',
    `name`        varchar(50) NOT NULL COMMENT '角色名称',
    `code`        varchar(50) NOT NULL COMMENT '角色编码',
    `role_type`   tinyint(1)           DEFAULT 1 COMMENT '角色类型（1-系统角色，2-业务角色）',
    `data_scope`  tinyint(1)           DEFAULT 1 COMMENT '数据权限范围（1-全部数据，2-本部门及以下，3-本部门，4-仅本人，5-自定义）',
    `status`      tinyint(1)           DEFAULT 1 COMMENT '状态（0-禁用，1-正常）',
    `sort`        int(11)              DEFAULT 0 COMMENT '排序号',
    `is_built_in` tinyint(1)           DEFAULT 0 COMMENT '是否内置（0-否，1-是，内置角色不可删除）',
    `create_by`   varchar(36)          DEFAULT NULL COMMENT '创建人',
    `create_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`   varchar(36)          DEFAULT NULL COMMENT '更新人',
    `update_time` datetime             DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`  tinyint(1)           DEFAULT 0 COMMENT '是否删除（0-否，1-是）',
    `tenant_id`   varchar(36)          DEFAULT NULL COMMENT '租户ID',
    `remark`      varchar(255)         DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_code` (`code`, `tenant_id`),
    KEY `idx_status` (`status`),
    KEY `idx_tenant_id` (`tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色表';

-- 用户角色关联表
CREATE TABLE `sys_user_role`
(
    `id`          varchar(36) NOT NULL COMMENT '主键ID（UUID）',
    `user_id`     varchar(36) NOT NULL COMMENT '用户ID',
    `role_id`     varchar(36) NOT NULL COMMENT '角色ID',
    `create_by`   varchar(36)          DEFAULT NULL COMMENT '创建人',
    `create_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `tenant_id`   varchar(36)          DEFAULT NULL COMMENT '租户ID',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_role` (`user_id`, `role_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_role_id` (`role_id`),
    KEY `idx_tenant_id` (`tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户角色关联表';

-- 菜单表
CREATE TABLE `sys_menu`
(
    `id`          varchar(36) NOT NULL COMMENT '主键ID（UUID）',
    `parent_id`   varchar(36)          DEFAULT NULL COMMENT '父级菜单ID',
    `name`        varchar(50) NOT NULL COMMENT '菜单名称',
    `path`        varchar(100)         DEFAULT NULL COMMENT '路由路径（浏览器地址栏URL）',
    `component`   varchar(100)         DEFAULT NULL COMMENT '组件路径（前端组件相对路径）',
    `perms`       varchar(100)         DEFAULT NULL COMMENT '权限标识（如：system:user:list）',
    `icon`        varchar(100)         DEFAULT NULL COMMENT '菜单图标',
    `menu_type`   tinyint(1)           DEFAULT 0 COMMENT '菜单类型（0-目录，1-菜单，2-按钮/权限）',
    `visible`     tinyint(1)           DEFAULT 1 COMMENT '是否可见（0-隐藏，1-显示）',
    `status`      tinyint(1)           DEFAULT 1 COMMENT '状态（0-禁用，1-正常）',
    `keep_alive`  tinyint(1)           DEFAULT 0 COMMENT '是否缓存（0-不缓存，1-缓存）',
    `always_show` tinyint(1)           DEFAULT 0 COMMENT '是否总是显示（适用于一级菜单）',
    `target`      varchar(20)          DEFAULT '_self' COMMENT '打开方式（_self-当前页面，_blank-新窗口）',
    `level`       int(11)              DEFAULT 1 COMMENT '菜单层级',
    `tree_path`   varchar(255)         DEFAULT NULL COMMENT '菜单路径（格式如：/根ID/父ID/）',
    `sort`        int(11)              DEFAULT 0 COMMENT '排序号',
    `create_by`   varchar(36)          DEFAULT NULL COMMENT '创建人',
    `create_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`   varchar(36)          DEFAULT NULL COMMENT '更新人',
    `update_time` datetime             DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`  tinyint(1)           DEFAULT 0 COMMENT '是否删除（0-否，1-是）',
    `tenant_id`   varchar(36)          DEFAULT NULL COMMENT '租户ID',
    `remark`      varchar(255)         DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    KEY `idx_parent_id` (`parent_id`),
    KEY `idx_sort` (`sort`),
    KEY `idx_tenant_id` (`tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='菜单表';

-- 角色菜单关联表
CREATE TABLE `sys_role_menu`
(
    `id`          varchar(36) NOT NULL COMMENT '主键ID（UUID）',
    `role_id`     varchar(36) NOT NULL COMMENT '角色ID',
    `menu_id`     varchar(36) NOT NULL COMMENT '菜单ID',
    `create_by`   varchar(36)          DEFAULT NULL COMMENT '创建人',
    `create_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `tenant_id`   varchar(36)          DEFAULT NULL COMMENT '租户ID',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_role_menu` (`role_id`, `menu_id`),
    KEY `idx_role_id` (`role_id`),
    KEY `idx_menu_id` (`menu_id`),
    KEY `idx_tenant_id` (`tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色菜单关联表';

-- 角色组织关联表（用于数据权限控制）
CREATE TABLE `sys_role_org`
(
    `id`          varchar(36) NOT NULL COMMENT '主键ID（UUID）',
    `role_id`     varchar(36) NOT NULL COMMENT '角色ID',
    `org_id`      varchar(36) NOT NULL COMMENT '组织ID',
    `create_by`   varchar(36)          DEFAULT NULL COMMENT '创建人',
    `create_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `tenant_id`   varchar(36)          DEFAULT NULL COMMENT '租户ID',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_role_org` (`role_id`, `org_id`),
    KEY `idx_role_id` (`role_id`),
    KEY `idx_org_id` (`org_id`),
    KEY `idx_tenant_id` (`tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色组织关联表';


-- 字典类型表
CREATE TABLE `sys_dict_type`
(
    `id`          varchar(36)  NOT NULL COMMENT '主键ID（UUID）',
    `name`        varchar(100) NOT NULL COMMENT '字典名称',
    `code`        varchar(100) NOT NULL COMMENT '字典编码',
    `status`      tinyint(1)            DEFAULT 1 COMMENT '状态（0-禁用，1-正常）',
    `create_by`   varchar(36)           DEFAULT NULL COMMENT '创建人',
    `create_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`   varchar(36)           DEFAULT NULL COMMENT '更新人',
    `update_time` datetime              DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`  tinyint(1)            DEFAULT 0 COMMENT '是否删除（0-否，1-是）',
    `tenant_id`   varchar(36)           DEFAULT NULL COMMENT '租户ID',
    `remark`      varchar(255)          DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_code` (`code`, `tenant_id`),
    KEY `idx_tenant_id` (`tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='字典类型表';

-- 字典数据表
CREATE TABLE `sys_dict_item`
(
    `id`             varchar(36)  NOT NULL COMMENT '主键ID（UUID）',
    `dict_type_id`   varchar(36)  NOT NULL COMMENT '字典类型ID',
    `dict_type_code` varchar(100) NOT NULL COMMENT '字典类型编码',
    `label`          varchar(100) NOT NULL COMMENT '字典标签',
    `value`          varchar(100) NOT NULL COMMENT '字典值',
    `css_class`      varchar(100)          DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
    `list_class`     varchar(100)          DEFAULT NULL COMMENT '表格回显样式',
    `is_default`     tinyint(1)            DEFAULT 0 COMMENT '是否默认（0-否，1-是）',
    `status`         tinyint(1)            DEFAULT 1 COMMENT '状态（0-禁用，1-正常）',
    `sort`           int(11)               DEFAULT 0 COMMENT '排序号',
    `create_by`      varchar(36)           DEFAULT NULL COMMENT '创建人',
    `create_time`    datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`      varchar(36)           DEFAULT NULL COMMENT '更新人',
    `update_time`    datetime              DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`     tinyint(1)            DEFAULT 0 COMMENT '是否删除（0-否，1-是）',
    `tenant_id`      varchar(36)           DEFAULT NULL COMMENT '租户ID',
    `remark`         varchar(255)          DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_dict_type_value` (`dict_type_id`, `value`, `tenant_id`),
    KEY `idx_dict_type_id` (`dict_type_id`),
    KEY `idx_sort` (`sort`),
    KEY `idx_tenant_id` (`tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='字典数据表';

-- 系统配置表
CREATE TABLE `sys_config`
(
    `id`           varchar(36)  NOT NULL COMMENT '主键ID（UUID）',
    `config_name`  varchar(100) NOT NULL COMMENT '配置名称',
    `config_key`   varchar(100) NOT NULL COMMENT '配置键名',
    `config_value` varchar(500) NOT NULL COMMENT '配置键值',
    `config_type`  tinyint(1)            DEFAULT 0 COMMENT '配置类型（0-系统配置，1-业务配置）',
    `is_builtin`   tinyint(1)            DEFAULT 0 COMMENT '是否内置（0-否，1-是）',
    `status`       tinyint(1)            DEFAULT 1 COMMENT '状态（0-禁用，1-正常）',
    `create_by`    varchar(36)           DEFAULT NULL COMMENT '创建人',
    `create_time`  datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`    varchar(36)           DEFAULT NULL COMMENT '更新人',
    `update_time`  datetime              DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`   tinyint(1)            DEFAULT 0 COMMENT '是否删除（0-否，1-是）',
    `tenant_id`    varchar(36)           DEFAULT NULL COMMENT '租户ID',
    `remark`       varchar(255)          DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_config_key` (`config_key`, `tenant_id`),
    KEY `idx_config_type` (`config_type`),
    KEY `idx_tenant_id` (`tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统配置表';


-- 职位表（岗位）
CREATE TABLE `sys_position`
(
    `id`          varchar(36)  NOT NULL COMMENT '主键ID',
    `name`        varchar(100) NOT NULL COMMENT '职位名称',
    `code`        varchar(50)  NOT NULL COMMENT '职位编码',
    `rank`        int(11)               DEFAULT 0 COMMENT '职级(数字越大级别越高)',
    `org_id`      varchar(36)           DEFAULT NULL COMMENT '所属组织ID',
    `description` varchar(255)          DEFAULT NULL COMMENT '职位描述',
    `status`      tinyint(1)            DEFAULT 1 COMMENT '状态(0禁用,1正常)',
    `create_by`   varchar(36)           DEFAULT NULL COMMENT '创建人',
    `create_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`   varchar(36)           DEFAULT NULL COMMENT '更新人',
    `update_time` datetime              DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`  tinyint(1)            DEFAULT 0 COMMENT '是否删除(0否,1是)',
    `tenant_id`   varchar(36)           DEFAULT NULL COMMENT '租户ID',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_code` (`code`, `tenant_id`),
    KEY `idx_org_id` (`org_id`),
    KEY `idx_tenant_id` (`tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='职位表';

-- 用户职位关联表
CREATE TABLE `sys_user_position`
(
    `id`          varchar(36) NOT NULL COMMENT '主键ID',
    `user_id`     varchar(36) NOT NULL COMMENT '用户ID',
    `position_id` varchar(36) NOT NULL COMMENT '职位ID',
    `org_id`      varchar(36)          DEFAULT NULL COMMENT '所属组织ID',
    `is_primary`  tinyint(1)           DEFAULT 0 COMMENT '是否主职位(0否,1是)',
    `create_by`   varchar(36)          DEFAULT NULL COMMENT '创建人',
    `create_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`   varchar(36)          DEFAULT NULL COMMENT '更新人',
    `update_time` datetime             DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `tenant_id`   varchar(36)          DEFAULT NULL COMMENT '租户ID',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_position` (`user_id`, `position_id`, `org_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_position_id` (`position_id`),
    KEY `idx_org_id` (`org_id`),
    KEY `idx_tenant_id` (`tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户职位关联表';

-- 区域表（省市区县）
CREATE TABLE `sys_region`
(
    `id`          varchar(36)  NOT NULL COMMENT '主键ID',
    `parent_id`   varchar(36)           DEFAULT NULL COMMENT '父级区域ID',
    `name`        varchar(100) NOT NULL COMMENT '区域名称',
    `code`        varchar(20)  NOT NULL COMMENT '区域编码(行政区划码)',
    `level`       tinyint(1)            DEFAULT 0 COMMENT '区域级别(0国家,1省,2市,3区县,4乡镇/街道)',
    `sort`        int(11)               DEFAULT 0 COMMENT '排序号',
    `status`      tinyint(1)            DEFAULT 1 COMMENT '状态(0禁用,1正常)',
    `create_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime              DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_code` (`code`),
    KEY `idx_parent_id` (`parent_id`),
    KEY `idx_level` (`level`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='区域表';

-- 组织扩展表（增加更多企业级属性）
CREATE TABLE `sys_organization_ext`
(
    `id`                   varchar(36) NOT NULL COMMENT '主键ID（与组织表ID相同）',
    `credit_code`          varchar(50)          DEFAULT NULL COMMENT '统一社会信用代码',
    `legal_person`         varchar(50)          DEFAULT NULL COMMENT '法人代表',
    `business_license`     varchar(255)         DEFAULT NULL COMMENT '营业执照URL',
    `registered_capital`   decimal(20, 2)       DEFAULT NULL COMMENT '注册资本',
    `founded_time`         date                 DEFAULT NULL COMMENT '成立时间',
    `business_scope`       text                 DEFAULT NULL COMMENT '经营范围',
    `region_id`            varchar(36)          DEFAULT NULL COMMENT '所属区域ID',
    `detailed_address`     varchar(255)         DEFAULT NULL COMMENT '详细地址',
    `email`                varchar(100)         DEFAULT NULL COMMENT '邮箱',
    `fax`                  varchar(30)          DEFAULT NULL COMMENT '传真',
    `website`              varchar(255)         DEFAULT NULL COMMENT '网站',
    `logo`                 varchar(255)         DEFAULT NULL COMMENT 'Logo URL',
    `parent_relation_type` tinyint(1)           DEFAULT 1 COMMENT '与上级关系类型(1直属,2控股,3参股,4合作,5其他)',
    `org_category`         varchar(50)          DEFAULT NULL COMMENT '组织类别(如生产部门、管理部门、营销部门等)',
    `create_by`            varchar(36)          DEFAULT NULL COMMENT '创建人',
    `create_time`          datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`            varchar(36)          DEFAULT NULL COMMENT '更新人',
    `update_time`          datetime             DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `tenant_id`            varchar(36)          DEFAULT NULL COMMENT '租户ID',
    PRIMARY KEY (`id`),
    KEY `idx_region_id` (`region_id`),
    KEY `idx_tenant_id` (`tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='组织扩展表';

-- 职级体系表
CREATE TABLE `sys_rank`
(
    `id`          varchar(36) NOT NULL COMMENT '主键ID',
    `name`        varchar(50) NOT NULL COMMENT '职级名称',
    `code`        varchar(20) NOT NULL COMMENT '职级编码',
    `level`       int(11)     NOT NULL COMMENT '职级等级(数字越大级别越高)',
    `description` varchar(255)         DEFAULT NULL COMMENT '职级描述',
    `status`      tinyint(1)           DEFAULT 1 COMMENT '状态(0禁用,1正常)',
    `create_by`   varchar(36)          DEFAULT NULL COMMENT '创建人',
    `create_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`   varchar(36)          DEFAULT NULL COMMENT '更新人',
    `update_time` datetime             DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`  tinyint(1)           DEFAULT 0 COMMENT '是否删除(0否,1是)',
    `tenant_id`   varchar(36)          DEFAULT NULL COMMENT '租户ID',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_code` (`code`, `tenant_id`),
    KEY `idx_level` (`level`),
    KEY `idx_tenant_id` (`tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='职级体系表';

-- 用户职级关联表
CREATE TABLE `sys_user_rank`
(
    `id`             varchar(36) NOT NULL COMMENT '主键ID',
    `user_id`        varchar(36) NOT NULL COMMENT '用户ID',
    `rank_id`        varchar(36) NOT NULL COMMENT '职级ID',
    `effective_date` date        NOT NULL COMMENT '生效日期',
    `expiry_date`    date                 DEFAULT NULL COMMENT '失效日期',
    `create_by`      varchar(36)          DEFAULT NULL COMMENT '创建人',
    `create_time`    datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`      varchar(36)          DEFAULT NULL COMMENT '更新人',
    `update_time`    datetime             DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `tenant_id`      varchar(36)          DEFAULT NULL COMMENT '租户ID',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_rank_id` (`rank_id`),
    KEY `idx_tenant_id` (`tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户职级关联表';

-- 组织关系表(记录组织间的多种关系，不局限于上下级)
CREATE TABLE `sys_org_relation`
(
    `id`            varchar(36) NOT NULL COMMENT '主键ID',
    `org_id`        varchar(36) NOT NULL COMMENT '组织ID',
    `target_org_id` varchar(36) NOT NULL COMMENT '目标组织ID',
    `relation_type` tinyint(1)  NOT NULL COMMENT '关系类型(1上下级,2同级,3业务协作,4管理关系,5其他)',
    `description`   varchar(255)         DEFAULT NULL COMMENT '关系描述',
    `create_by`     varchar(36)          DEFAULT NULL COMMENT '创建人',
    `create_time`   datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`     varchar(36)          DEFAULT NULL COMMENT '更新人',
    `update_time`   datetime             DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `tenant_id`     varchar(36)          DEFAULT NULL COMMENT '租户ID',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_org_relation` (`org_id`, `target_org_id`, `relation_type`),
    KEY `idx_org_id` (`org_id`),
    KEY `idx_target_org_id` (`target_org_id`),
    KEY `idx_tenant_id` (`tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='组织关系表';


-- 租户表
CREATE TABLE `sys_tenant`
(
    `id`             varchar(36)  NOT NULL COMMENT '租户ID',
    `name`           varchar(100) NOT NULL COMMENT '租户名称',
    `code`           varchar(50)  NOT NULL COMMENT '租户编码',
    `domain`         varchar(255)          DEFAULT NULL COMMENT '域名',
    `contact_user`   varchar(50)           DEFAULT NULL COMMENT '联系人',
    `contact_phone`  varchar(20)           DEFAULT NULL COMMENT '联系电话',
    `contact_email`  varchar(100)          DEFAULT NULL COMMENT '联系邮箱',
    `expire_time`    datetime              DEFAULT NULL COMMENT '过期时间',
    `license_key`    varchar(255)          DEFAULT NULL COMMENT '授权码',
    `tenant_status`  tinyint(1)            DEFAULT 1 COMMENT '状态(0-禁用,1-正常,2-过期)',
    `data_source_id` varchar(36)           DEFAULT NULL COMMENT '数据源ID',
    `create_time`    datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    datetime              DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark`         varchar(255)          DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_code` (`code`),
    KEY `idx_tenant_status` (`tenant_status`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='租户表';

-- 用户组表
CREATE TABLE `sys_user_group`
(
    `id`          varchar(36)  NOT NULL COMMENT '主键ID',
    `name`        varchar(100) NOT NULL COMMENT '用户组名称',
    `code`        varchar(50)  NOT NULL COMMENT '用户组编码',
    `group_type`  tinyint(1)            DEFAULT 1 COMMENT '用户组类型(1-普通组,2-项目组,3-专项组)',
    `owner_id`    varchar(36)           DEFAULT NULL COMMENT '负责人ID',
    `org_id`      varchar(36)           DEFAULT NULL COMMENT '所属组织ID',
    `status`      tinyint(1)            DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    `expire_time` datetime              DEFAULT NULL COMMENT '过期时间',
    `create_by`   varchar(36)           DEFAULT NULL COMMENT '创建人',
    `create_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`   varchar(36)           DEFAULT NULL COMMENT '更新人',
    `update_time` datetime              DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`  tinyint(1)            DEFAULT 0 COMMENT '是否删除(0否,1是)',
    `tenant_id`   varchar(36)           DEFAULT NULL COMMENT '租户ID',
    `remark`      varchar(255)          DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_code` (`code`, `tenant_id`),
    KEY `idx_owner_id` (`owner_id`),
    KEY `idx_org_id` (`org_id`),
    KEY `idx_tenant_id` (`tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户组表';

-- 用户组成员表
CREATE TABLE `sys_user_group_user`
(
    `id`          varchar(36) NOT NULL COMMENT '主键ID',
    `group_id`    varchar(36) NOT NULL COMMENT '用户组ID',
    `user_id`     varchar(36) NOT NULL COMMENT '用户ID',
    `join_time`   datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '加入时间',
    `expire_time` datetime             DEFAULT NULL COMMENT '过期时间',
    `user_role`   tinyint(1)           DEFAULT 0 COMMENT '在组中的角色(0-普通成员,1-管理员,2-负责人)',
    `create_by`   varchar(36)          DEFAULT NULL COMMENT '创建人',
    `create_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`   varchar(36)          DEFAULT NULL COMMENT '更新人',
    `update_time` datetime             DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `tenant_id`   varchar(36)          DEFAULT NULL COMMENT '租户ID',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_group_user` (`group_id`, `user_id`),
    KEY `idx_group_id` (`group_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_tenant_id` (`tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户组成员表';

-- 操作日志表
CREATE TABLE `sys_oper_log`
(
    `id`             varchar(36)  NOT NULL COMMENT '日志ID',
    `title`          varchar(100) NOT NULL COMMENT '操作模块',
    `business_type`  tinyint(1)    DEFAULT 0 COMMENT '业务类型(0-其它,1-新增,2-修改,3-删除,4-授权,5-导出,6-导入,7-强退,8-生成代码,9-清空数据)',
    `method`         varchar(255)  DEFAULT NULL COMMENT '请求方法',
    `request_method` varchar(10)   DEFAULT NULL COMMENT '请求方式(GET,POST,PUT,DELETE等)',
    `operator_type`  tinyint(1)    DEFAULT 0 COMMENT '操作类别(0-其它,1-后台用户,2-手机端用户)',
    `oper_name`      varchar(50)   DEFAULT NULL COMMENT '操作人员',
    `dept_name`      varchar(50)   DEFAULT NULL COMMENT '部门名称',
    `oper_url`       varchar(255)  DEFAULT NULL COMMENT '请求URL',
    `oper_ip`        varchar(128)  DEFAULT NULL COMMENT '主机地址',
    `oper_location`  varchar(255)  DEFAULT NULL COMMENT '操作地点',
    `oper_param`     varchar(2000) DEFAULT NULL COMMENT '请求参数',
    `json_result`    varchar(2000) DEFAULT NULL COMMENT '返回参数',
    `status`         tinyint(1)    DEFAULT 0 COMMENT '操作状态(0-正常,1-异常)',
    `error_msg`      varchar(2000) DEFAULT NULL COMMENT '错误消息',
    `oper_time`      datetime      DEFAULT NULL COMMENT '操作时间',
    `cost_time`      bigint(20)    DEFAULT 0 COMMENT '消耗时间(毫秒)',
    `tenant_id`      varchar(36)   DEFAULT NULL COMMENT '租户ID',
    PRIMARY KEY (`id`),
    KEY `idx_business_type` (`business_type`),
    KEY `idx_oper_time` (`oper_time`),
    KEY `idx_tenant_id` (`tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='操作日志表';
