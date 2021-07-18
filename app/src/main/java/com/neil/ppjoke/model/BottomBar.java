package com.neil.ppjoke.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * TO-DO
 * @author neil.zhong
 * @date 7/18/21 3:09 PM
 */
public class BottomBar {

    @JSONField(name = "activeColor")
    private String activeColor;
    @JSONField(name = "inActiveColor")
    private String inActiveColor;
    @JSONField(name = "selectTab")
    private Integer selectTab;
    @JSONField(name = "tabs")
    private List<Tabs> tabs;

    public String getActiveColor() {
        return activeColor;
    }

    public void setActiveColor(String activeColor) {
        this.activeColor = activeColor;
    }

    public String getInActiveColor() {
        return inActiveColor;
    }

    public void setInActiveColor(String inActiveColor) {
        this.inActiveColor = inActiveColor;
    }

    public Integer getSelectTab() {
        return selectTab;
    }

    public void setSelectTab(Integer selectTab) {
        this.selectTab = selectTab;
    }

    public List<Tabs> getTabs() {
        return tabs;
    }

    public void setTabs(List<Tabs> tabs) {
        this.tabs = tabs;
    }

    public static class Tabs {
        @JSONField(name = "size")
        private Integer size;
        @JSONField(name = "enable")
        private Boolean enable;
        @JSONField(name = "index")
        private Integer index;
        @JSONField(name = "pageUrl")
        private String pageUrl;
        @JSONField(name = "title")
        private String title;
        @JSONField(name = "tintColor")
        private String tintColor;

        public Integer getSize() {
            return size;
        }

        public void setSize(Integer size) {
            this.size = size;
        }

        public Boolean getEnable() {
            return enable;
        }

        public void setEnable(Boolean enable) {
            this.enable = enable;
        }

        public Integer getIndex() {
            return index;
        }

        public void setIndex(Integer index) {
            this.index = index;
        }

        public String getPageUrl() {
            return pageUrl;
        }

        public void setPageUrl(String pageUrl) {
            this.pageUrl = pageUrl;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTintColor() {
            return tintColor;
        }

        public void setTintColor(String tintColor) {
            this.tintColor = tintColor;
        }
    }
}
