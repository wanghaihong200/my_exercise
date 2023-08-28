package cn.itcast.account.service.impl;

import cn.itcast.account.entity.AccountFreeze;
import cn.itcast.account.mapper.AccountFreezeMapper;
import cn.itcast.account.mapper.AccountMapper;
import cn.itcast.account.service.AccountTCCService;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description:
 * @author: 王海虹
 * @create: 2023-08-28 13:57
 */
@Slf4j
@Service
public class AccountTCCServiceImpl implements AccountTCCService {
    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private AccountFreezeMapper accountFreezeMapper;

    @Override
    @Transactional
    public void deduct(String userId, int money) {
        // 获取全局事务ID
        String xid = RootContext.getXID();
        // 扣减账户余额
        accountMapper.deduct(userId, money);
        // 记录 冻结账户金额，事务状态为TRYING
        AccountFreeze accountFreeze = AccountFreeze.builder().
                userId(userId).
                freezeMoney(money).
                xid(xid).
                state(AccountFreeze.State.TRY).
                build();
        accountFreezeMapper.insert(accountFreeze);
    }

    @Override
    @Transactional
    public Boolean confirm(BusinessActionContext businessActionContext) {
        // 1. 获取全局事务ID
        String xid = businessActionContext.getXid();
        // 2.根据全局事务ID删除冻结账户金额记录
        int count = accountFreezeMapper.deleteById(xid);
        return count == 1;
    }

    @Override
    @Transactional
    public Boolean cancel(BusinessActionContext businessActionContext) {
        // 查询冻结账户金额记录
        String xid = businessActionContext.getXid();
        AccountFreeze accountFreeze = accountFreezeMapper.selectById(xid);

        // 恢复可用余额
        accountMapper.refund(accountFreeze.getUserId(), accountFreeze.getFreezeMoney());
        // 将冻结金额清零，事务状态改为CANCEL
        accountFreeze.setFreezeMoney(0);
        accountFreeze.setState(AccountFreeze.State.CANCEL);
        int count = accountFreezeMapper.updateById(accountFreeze);

        return count == 1;
    }
}
