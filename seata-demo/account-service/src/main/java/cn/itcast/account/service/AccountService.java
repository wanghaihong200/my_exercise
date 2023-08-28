package cn.itcast.account.service;

import io.seata.rm.tcc.api.BusinessActionContext;


public interface AccountService {
    /**
     * 从用户账户中扣款
     */
    void deduct(String userId, int money);


}