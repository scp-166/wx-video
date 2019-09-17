package com.nekosighed.service;

import com.nekosighed.pojo.model.UsersReport;

public interface UsersReportService {
    /**
     * 添加一条举报信息
     *
     * @param usersReport
     */
    void addSingleReport(UsersReport usersReport);
}
