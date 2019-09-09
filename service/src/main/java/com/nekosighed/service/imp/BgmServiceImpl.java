package com.nekosighed.service.imp;

import com.nekosighed.mapper.mapper.BgmMapper;
import com.nekosighed.pojo.model.Bgm;
import com.nekosighed.service.BgmService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BgmServiceImpl implements BgmService {
    @Resource
    private BgmMapper bgmMapper;

    /**
     * 获得 bgm 列表
     *
     * @return
     */
    @Override
    public List<Bgm> queryBgmList() {
        return bgmMapper.selectAll();
    }
}
