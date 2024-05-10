package com.youth.request;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * 分页公共请求对象
 */
@Data
public class PageParamRequest {


    private int page = CommonConstant.DEFAULT_PAGE;


    private int limit = CommonConstant.DEFAULT_LIMIT;

    public IPage getPage() {
        return new Page(page, limit);
    }

}
