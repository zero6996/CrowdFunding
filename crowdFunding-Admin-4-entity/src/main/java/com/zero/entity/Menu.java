package com.zero.entity;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    /**
     * 对应数据库表主键
     */
    private Integer id;
    /**
     * 对应父节点id(如果pid为null，则说明当前节点是根节点)
     */
    private Integer pid;
    /**
     * 节点名称
     */
    private String name;
    /**
     * 节点对应的URL地址
     */
    private String url;
    /**
     * 节点图标
     */
    private String icon;

    /**
     * 当前节点的子节点集合，设置默认值是为了避免组装节点时空指针异常
     */
    private List<Menu> children = new ArrayList<>();
    /**
     * 控制节点展开还是折叠，true表示让整个树形菜单默认展开
     */
    private Boolean open = true;

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", pid=" + pid +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", icon='" + icon + '\'' +
                ", children=" + children +
                ", open=" + open +
                '}';
    }

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }
}