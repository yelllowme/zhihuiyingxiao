package com.yunshangkeji.zhihuiyingxiao.a_part_common.model;

/**
 * Created by yellow on 2016/11/17.
 * APP版本Model
 */

public class AppVersionModel {

    public static AppVersionModel appVersionModel = new AppVersionModel();

    /**
     * APP是否必须更新代号
     * 0：不是必须更新
     * 1：必须更新
     */

    public static final int APP_UPDATE_NECESSARY_NO = 0;

    public static final int APP_UPDATE_NECESSARY_YES = 1;

    //年版本号
    private int yearVersion;

    //月版本号
    private int monthVersion;

    //周版本号
    private int weekVersion;

    //更新是否是必须的
    private int isNecessary;

    //APP更新地址
    private String downloadUrl;

    //版本信息
    private String versionInfo;

    public int getYearVersion() {
        return yearVersion;
    }

    public void setYearVersion(int yearVersion) {
        this.yearVersion = yearVersion;
    }

    public int getMonthVersion() {
        return monthVersion;
    }

    public void setMonthVersion(int monthVersion) {
        this.monthVersion = monthVersion;
    }

    public int getWeekVersion() {
        return weekVersion;
    }

    public void setWeekVersion(int weekVersion) {
        this.weekVersion = weekVersion;
    }

    public int getIsNecessary() {
        return isNecessary;
    }

    public void setIsNecessary(int isNecessary) {
        this.isNecessary = isNecessary;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getVersionInfo() {
        return versionInfo;
    }

    public void setVersionInfo(String versionInfo) {
        this.versionInfo = versionInfo;
    }
}
