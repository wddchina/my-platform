package com.wdd.myplatform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 菜单管理
 * </p>
 *
 * @author wdd
 * @since 2021-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysMenu对象", description="菜单管理")
public class SysMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "menu_id", type = IdType.ASSIGN_ID)
    private Long menuId;

    @ApiModelProperty(value = "上级ID，一级菜单为0")
    private Long pid;

    @ApiModelProperty(value = "菜单URL")
    private String url;

    @ApiModelProperty(value = "授权(多个用逗号分隔，如：sys:user:list,sys:user:save)")
    private String permissions;

    @ApiModelProperty(value = "类型   0：菜单   1：按钮")
    private Integer type;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "是否为敏感菜单")
    private String isImportant;

    @ApiModelProperty(value = "菜单分类")
    private String menuClass;

    @ApiModelProperty(value = "客户端")
    private String client;

    @ApiModelProperty(value = "是否显示")
    private Integer showFlag;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建者")
    private String creator;

    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    @ApiModelProperty(value = "更新者")
    private String updater;

    @ApiModelProperty(value = "更新时间")
    private Date updateDate;


    public static final String MENU_ID = "menu_id";

    public static final String PID = "pid";

    public static final String URL = "url";

    public static final String PERMISSIONS = "permissions";

    public static final String TYPE = "type";

    public static final String ICON = "icon";

    public static final String SORT = "sort";

    public static final String IS_IMPORTANT = "is_important";

    public static final String MENU_CLASS = "menu_class";

    public static final String CLIENT = "client";

    public static final String SHOW_FLAG = "show_flag";

    public static final String REMARK = "remark";

    public static final String CREATOR = "creator";

    public static final String CREATE_DATE = "create_date";

    public static final String UPDATER = "updater";

    public static final String UPDATE_DATE = "update_date";

}
