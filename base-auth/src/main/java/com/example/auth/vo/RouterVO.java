package com.example.auth.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * 路由视图对象
 *
 * @author 作者
 * @date 创建时间
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RouterVO {

    /**
     * 路由名称
     */
    private String name;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 重定向地址
     */
    private String redirect;

    /**
     * 是否隐藏路由
     */
    private Boolean hidden;

    /**
     * 是否总是显示
     */
    private Boolean alwaysShow;

    /**
     * 路由元数据
     */
    private MetaVO meta;

    /**
     * 子路由
     */
    private List<RouterVO> children;

    /**
     * 路由元数据内部类
     */
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class MetaVO {
        /**
         * 标题
         */
        private String title;

        /**
         * 图标
         */
        private String icon;

        /**
         * 是否缓存（0-不缓存，1-缓存）
         */
        private Boolean keepAlive;

        /**
         * 权限标识
         */
        private String permission;
    }
} 