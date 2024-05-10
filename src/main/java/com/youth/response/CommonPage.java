package com.youth.response;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import lombok.Data;
import com.youth.request.CommonConstant;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 公共分页对象
 */
@Data
public class CommonPage<T> {
    private Integer page = CommonConstant.DEFAULT_PAGE;
    private Integer limit = CommonConstant.DEFAULT_LIMIT;
    private Integer totalPage = 0;
    private Long total = 0L;
    private List<T> list = new ArrayList<>();

    /**
     * 将IPage分页后的list转为分页信息
     */
    public static <T> CommonPage<T> restPage(List<T> list) {
        CommonPage<T> result = new CommonPage<T>();
        PageInfo<T> pageInfo = new PageInfo<T>(list);
        result.setTotalPage(pageInfo.getPages());
        result.setPage(pageInfo.getPageNum());
        result.setLimit(pageInfo.getPageSize());
        result.setTotal(pageInfo.getTotal());
        result.setList(pageInfo.getList());
        return result;
    }

    /**
     * 将PageHelper分页后的list转为分页信息
     */
    public static <T> CommonPage<T> restPage(IPage<T> page) {
        CommonPage<T> result = new CommonPage<T>();
        result.setTotalPage((int) page.getPages());
        result.setPage((int) page.getCurrent());
        result.setLimit((int) page.getSize());
        result.setTotal(page.getTotal());
        result.setList(page.getRecords());
        return result;
    }

    /**
     * 将PageHelper分页后的 PageInfo 转为分页信息
     *
     * @return
     */
    public static <T> CommonPage<T> restPage(PageInfo<T> pageInfo) {
        CommonPage<T> result = new CommonPage<T>();
        result.setTotalPage(pageInfo.getPages());
        result.setPage(pageInfo.getPageNum());
        result.setLimit(pageInfo.getPageSize());
        result.setTotal(pageInfo.getTotal());
        result.setList(pageInfo.getList());
        return result;
    }

    /**
     * 对象A复制对象B的分页信息 // 多次数据查询导致分页数据异常解决办法
     */
    public static <T> PageInfo<T> copyPageInfo(Page originPageInfo, List<T> list) {
        PageInfo<T> pageInfo = new PageInfo<>(list);
        BeanUtils.copyProperties(originPageInfo, pageInfo, "list");
        return pageInfo;
    }

    /**
     * 对象A复制对象B的分页信息 // 多次数据查询导致分页数据异常解决办法
     */
    public static <T> PageInfo<T> copyPageInfo(PageInfo<?> originPageInfo, List<T> list) {
        PageInfo<T> pageInfo = new PageInfo<>(list);
        pageInfo.setPages(originPageInfo.getPages());
        pageInfo.setPageNum(originPageInfo.getPageNum());
        pageInfo.setPageSize(originPageInfo.getPageSize());
        pageInfo.setTotal(originPageInfo.getTotal());
        pageInfo.setList(list);
        return pageInfo;
    }
}
