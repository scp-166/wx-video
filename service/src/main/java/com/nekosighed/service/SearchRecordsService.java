package com.nekosighed.service;

import java.util.List;

public interface SearchRecordsService {
    /**
     * 添加一次搜索记录
     *
     * @param videoDesc
     */
    void addHotWord(String videoDesc);

    /**
     * 查询 热词
     *
     * @return
     */
    List<String> getHotWords();
}
