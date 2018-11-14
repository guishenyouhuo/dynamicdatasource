package com.guigui.main.models;

public class DatasourceConfig {
    private String beanId;

    private String url;

    private String userName;

    private String password;

    private String maxactive;

    private String type;

    private String dsKey;

    public String getBeanId() {
        return beanId;
    }

    public void setBeanId(String beanId) {
        this.beanId = beanId == null ? null : beanId.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getMaxactive() {
        return maxactive;
    }

    public void setMaxactive(String maxactive) {
        this.maxactive = maxactive == null ? null : maxactive.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getDsKey() {
        return dsKey;
    }

    public void setDsKey(String dsKey) {
        this.dsKey = dsKey == null ? null : dsKey.trim();
    }
}
