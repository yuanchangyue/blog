package com.changyue.blogserver.crawler.callback;

/**
 * @author : 袁阊越
 * @description : 延时回调接口 为了判断下载页面是否成功，因为csdn的cookie登录验证是通过js实现的 需要通过Selenium下载页面后等待一会检测cookie是否注入成功
 * @date : 2020/3/24
 */
public interface DelayedCallBack {
    /**
     * 延时调用方法
     *
     * @param time 等待时间
     * @return 回调对象
     */
    Object callBack(long time);

    /**
     * 判断是否存在
     */
    boolean isExist();

    /**
     * 获取每次睡眠时间
     */
    long sleepTime();

    /**
     * 超时时间
     */
    long timeOut();
}
