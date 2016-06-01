package com.cuize.activity.web.task;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cuize.activity.web.util.oauth.OAuthTokenRefresh;
import com.cuize.activity.web.util.oauth.OAuthTokenUtil;

/**
 * 获取微信AccessToken任务
 * @author JackieLan
 *
 */
@Component
public class AccessTokenTask {

	private static final Logger LOG = LoggerFactory.getLogger(AccessTokenTask.class);
	
	/**
     * 每一小时更新一次access_token
     */
	@Scheduled(cron = "0 */60 * * * ?")
	@PostConstruct
    public void refresh(){
		LOG.info("AccessTokenTask.refresh:start");
		OAuthTokenRefresh.refreshOAuthToken();
		LOG.info("AccessTokenTask.refresh:access_token={}", OAuthTokenUtil.access_token);
		LOG.info("AccessTokenTask.refresh:end");
	}
}
