package com.itianyi.bean;

/**
 * 类名：TreeElement.java
 * 类描述：树形结构节点类
 * 创建时间：2012-11-23 16:32
 */
public class TreeElement {
    private String id = null;// 当前节点id
    private String title = null;// 当前节点文字
    private boolean hasParent = false;// 当前节点是否有父节点
    private boolean hasChild = false;// 当前节点是否有子节点
    private boolean childShow = false;// 如果子节点，字节当当前是否已显示
    private String parentId = null;// 父节点id
    private int level = -1;// 当前界面层级
    private boolean fold = false;// 是否处于展开状态

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isHasParent() {
        return hasParent;
    }

    public void setHasParent(boolean hasParent) {
        this.hasParent = hasParent;
    }

    public boolean isHasChild() {
        return hasChild;
    }

    public void setHasChild(boolean hasChild) {
        this.hasChild = hasChild;
    }

    public boolean isChildShow() {
        return childShow;
    }

    public void setChildShow(boolean childShow) {
        this.childShow = childShow;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isFold() {
        return fold;
    }

    public void setFold(boolean fold) {
        this.fold = fold;
    }
}
