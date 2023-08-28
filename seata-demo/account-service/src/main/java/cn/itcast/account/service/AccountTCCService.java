package cn.itcast.account.service;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * @description: Seata的TCC模式接口
 * @author: 王海虹
 * @create: 2023-08-28 11:47
 */
@LocalTCC
public interface AccountTCCService {
    /**
     * 从用户账户中扣款
     */
    @TwoPhaseBusinessAction(name="deduct", commitMethod = "confirm", rollbackMethod = "cancel")
    void deduct(@BusinessActionContextParameter(paramName = "userId") String userId,
                @BusinessActionContextParameter(paramName = "money") int money);

    Boolean confirm(BusinessActionContext businessActionContext);

    Boolean cancel(BusinessActionContext businessActionContext);
}
