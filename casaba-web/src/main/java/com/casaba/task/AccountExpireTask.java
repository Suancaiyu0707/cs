package com.casaba.task;

import com.casaba.agent.core.service.AgentService;
import com.casaba.mer.service.IMerchantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AccountExpireTask {
    private static Logger logger = LoggerFactory.getLogger(AccountExpireTask.class);
    @Autowired
    private AgentService agentService;
    @Autowired
    private IMerchantService merchantService;
    @Scheduled(cron= "0 0 1 * * ?")
    public void expireAgent(){
        long beginTime = System.nanoTime();
        logger.info("begin expire agent========"+beginTime);
        agentService.expireAgents();
        long endTime = System.nanoTime();
        logger.info("end expire agent========"+endTime+"==========costTime={}",endTime-beginTime);
    }
    @Scheduled(cron= "0 0 1 * * ?")
    public void expireMerchants(){
        long beginTime = System.nanoTime();
        logger.info("begin expire merchant========"+beginTime);
        merchantService.exeExpireMer();
        long endTime = System.nanoTime();
        logger.info("end expire merchant========"+endTime+"==========costTime={}",endTime-beginTime);
    }
}
