package com.changyue.blogserver.crawler.callback;


import com.changyue.blogserver.crawler.model.CrawlerProxy;

import java.util.List;

/**
 * IP池更新回调
 */
public interface ProxyProviderCallBack {
    public List<CrawlerProxy> getProxyList();
}
