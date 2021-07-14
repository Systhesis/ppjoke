package com.neil.ppjoke.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author zhongnan
 * @time 2021/7/14 20:11
 *
 */
public class Destination {

    @JSONField(name = "isFragment")
    private Boolean isFragment;
    @JSONField(name = "asStarter")
    private Boolean asStarter;
    @JSONField(name = "needLogin")
    private Boolean needLogin;
    @JSONField(name = "id")
    private Integer id;
    @JSONField(name = "clazzName")
    private String clazzName;
    @JSONField(name = "pageUrl")
    private String pageUrl;

    public Boolean getIsFragment() {
        return isFragment;
    }

    public void setIsFragment(Boolean isFragment) {
        this.isFragment = isFragment;
    }

    public Boolean getAsStarter() {
        return asStarter;
    }

    public void setAsStarter(Boolean asStarter) {
        this.asStarter = asStarter;
    }

    public Boolean getNeedLogin() {
        return needLogin;
    }

    public void setNeedLogin(Boolean needLogin) {
        this.needLogin = needLogin;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }
}
