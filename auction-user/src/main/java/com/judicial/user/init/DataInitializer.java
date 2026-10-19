package com.judicial.user.init;

import com.judicial.user.entity.Account;
import com.judicial.user.entity.SysUser;
import com.judicial.user.mapper.AccountMapper;
import com.judicial.user.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final AccountMapper accountMapper;
    private final SysUserMapper sysUserMapper;

    @Override
    public void run(String... args) {
        for (long uid = 1; uid <= 3; uid++) {
            Account a = new Account();
            a.setUserId(uid);
            a.setBalance(new BigDecimal("10000"));
            a.setFrozen(BigDecimal.ZERO);
            a.setVersion(0);
            accountMapper.insert(a);
        }
        createUser("bidder1","123456","BIDDER");
        createUser("bidder2","123456","BIDDER");
        createUser("admin","123456","ADMIN");
        createUser("finance","123456","FINANCE");
    }

    private void createUser(String u, String p, String r) {
        try {
            SysUser su = new SysUser();
            su.setUsername(u); su.setPassword(p); su.setRole(r);
            sysUserMapper.insert(su);
        } catch (Exception ignored) {}
    }
}
