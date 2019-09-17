package com.nekosighed.service.imp;

import com.nekosighed.mapper.mapper.UsersMapper;
import com.nekosighed.mapper.mapper.UsersReportMapper;
import com.nekosighed.pojo.model.UsersReport;
import com.nekosighed.service.UsersReportService;

import javax.annotation.Resource;

public class UsersReportServiceImpl implements UsersReportService {
    @Resource
    UsersReportMapper usersReportMapper;

    /**
     * 添加一条举报信息
     *
     * @param usersReport
     */
    @Override
    public void addSingleReport(UsersReport usersReport) {
        usersReportMapper.insert(usersReport);
    }
}
