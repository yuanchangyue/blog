package com.changyue.blogserver.utils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

/**
 * @author : 袁阊越
 * @description : 分页工具
 * @date : 2020-03-11 17:22
 */
public class PageInfoUtil {
    /**
     * 将PageInfo对象泛型中的Po对象转化为Vo对象
     *
     * @param pageInfoPo PageInfo<Po>对象</>
     * @param <P>        Po类型
     * @param <V>        Vo类型
     * @return Vo类型的分页
     */
    public static <P, V> PageInfo<V> PageInfo2PageInfoDTO(PageInfo<P> pageInfoPo) {
        // 创建Page对象，实际上是一个ArrayList类型的集合
        Page<V> page = new Page<>(pageInfoPo.getPageNum(), pageInfoPo.getPageSize());
        page.setTotal(pageInfoPo.getTotal());
        return new PageInfo<>(page);
    }
}
