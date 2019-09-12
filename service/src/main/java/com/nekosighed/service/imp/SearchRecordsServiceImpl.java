package com.nekosighed.service.imp;

import com.nekosighed.common.utils.UuidUtils;
import com.nekosighed.mapper.mapper.SearchRecordsMapper;
import com.nekosighed.pojo.model.SearchRecords;
import com.nekosighed.service.SearchRecordsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SearchRecordsServiceImpl implements SearchRecordsService {

    @Resource
    private SearchRecordsMapper searchRecordsMapper;

    /**
     * 添加一次搜索记录
     *
     * @param videoDesc
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void addHotWord(String videoDesc) {
        searchRecordsMapper.insert(new SearchRecords(UuidUtils.createUUID(), videoDesc));
    }

    /**
     * 查询 热词
     *
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<String> getHotWords() {
        return searchRecordsMapper.getHotWords();
    }

}
