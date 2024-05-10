package com.youth.response;

import lombok.Data;

import java.util.List;

/**
 * 通用的分页实体对象
 */
@Data
public class EduPage<T> {

    //当前第几页
    private Integer pageIndex;

    //每一页显示的数量
    private Integer pageSize;

    //总页数
    private Integer totalSize;

    //总记录数
    private Integer totalCount;

    //分页的数据集
    private List<T> data;
}
