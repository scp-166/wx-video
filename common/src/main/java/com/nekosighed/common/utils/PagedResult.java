package com.nekosighed.common.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 分页后的结果
 *
 * @author lyl
 */
@ToString
@Getter
@Setter
public class PagedResult {
    /**
     * 当前页
     */
    private Integer currentPage;
    /**
     * 期望分页后每页的数量
     */
    private Integer expectPageSize;
    /**
     * 实际分页后每页的数量
     */
    private Integer actualPageSize;
    /**
     * 每分页后每页的内容
     */
    private List<?> rows;

    /**
     * 总记录数，即原查询后的总量
     */
    private Long totalRecord;

    /**
     * 一共能分几页
     */
    private Integer totalPages;
}
