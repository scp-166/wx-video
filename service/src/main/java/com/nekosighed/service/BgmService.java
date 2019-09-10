package com.nekosighed.service;

import com.nekosighed.pojo.model.Bgm;

import java.util.List;

/**
 * bgm 表相关擦欧总
 *
 * @author lyl
 */
public interface BgmService {
    /**
     * 获取 Bgm 列表
     *
     * @return
     */
    List<Bgm> queryBgmList();

    /**
     * 根据 id 查询 Bgm
     *
     * @param bgmId bgmId
     * @return
     */
    <T> Bgm queryBgmById(T bgmId);
}
