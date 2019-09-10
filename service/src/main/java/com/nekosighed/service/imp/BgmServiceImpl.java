package com.nekosighed.service.imp;

import com.nekosighed.mapper.mapper.BgmMapper;
import com.nekosighed.pojo.model.Bgm;
import com.nekosighed.service.BgmService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Bgm> queryBgmList() {
        return bgmMapper.selectAll();
    }

    /**
     * 根据 id 获得 bgm
     *
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public <T> Bgm queryBgmById(T bgmId) {
        if (String.class == bgmId.getClass()) {
            return bgmMapper.selectByPrimaryKey((String) bgmId);
        } else {
            return null;
        }
    }

}
