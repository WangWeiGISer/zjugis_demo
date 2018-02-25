package com.zjugis.demo.utils;/**
 * Created by Administrator on 2017/11/5.
 */

import com.github.pagehelper.Page;

import java.util.List;

/**
 * @WangWei
 * @create 2017-11-05 0:46
 **/
public class BeanUtil {
    public static <T> PagedResult<T> toPagedResult(List<T> datas) {
        PagedResult<T> result = new PagedResult<T>();
        if (datas instanceof Page) {
            Page page = (Page) datas;
            result.setPageNo(page.getPageNum());
            result.setPageSize(page.getPageSize());
            result.setDataList(page.getResult());
            result.setTotal(page.getTotal());
            result.setPages(page.getPages());
        }
        else {
            result.setPageNo(1);
            result.setPageSize(datas.size());
            result.setDataList(datas);
            result.setTotal(datas.size());
        }

        return result;
    }
}
