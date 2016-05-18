package com.cuize.activity.service.dto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GlobalConfig {
	@Value("${app.name:product}")
	private String appName;

	@Value("${env}")
	private String env;

	@Value("${timestamp}")
	private String buildTimestamp;

	@Value("${batch.succ.mobiles}")
	private String getBatchSuccMobiles;

	@Value("${batch.fail.mobiles}")
	private String getBatchFailMobiles;

	@Value("${ds.env}")
	private String dsEnv;

	@Value("${wx.pay.mchid}")
	private String mchid;
	
	@Value("${wx.pay.mchname}")
	private String mchname;
	
	@Value("${wx.pay.apikey}")
	private String apikey;
	
	@Value("${wx.pay.appid}")
	private String appid;
	
	@Value("${wx.pay.appsecret}")
	private String appsecret;

	@Value("${wx.api.hbpreorder.url}")
	private String hbpreorderUrl;
	
	@Value("${wx.api.lottery.addlotteryinfo.url}")
	private String addlotteryinfoUrl;
	
	@Value("${wx.api.lottery.setprizebucket.url}")
	private String setprizebucketUrl;
	
	@Value("${wx.api.lottery.setlotteryswitch.url}")
	private String setlotteryswitchUrl;
	
	@Value("${wx.pay.cer.path}")
	private String cerFilePath;
	
	/**
	 * OAuth授权redirect_uri域名
	 * 嗨摇公众号为piaowu.hiyo.cc
	 */
	@Value("${wx.api.oauth.redirecturi}")
	private String oauthRedirectUri;
	
	public String getAppName() {
		return appName;
	}

	public String getEnv() {
		return env;
	}

	public String getBuildTimestamp() {
		return buildTimestamp;
	}

	public String getGetBatchSuccMobiles() {
		return getBatchSuccMobiles;
	}

	public String getGetBatchFailMobiles() {
		return getBatchFailMobiles;
	}

	public String getDsEnv() {
		return dsEnv;
	}

	public String getMchid() {
		return mchid;
	}

	public void setMchid(String mchid) {
		this.mchid = mchid;
	}

	public String getMchname() {
		return mchname;
	}

	public void setMchname(String mchname) {
		this.mchname = mchname;
	}

	public String getApikey() {
		return apikey;
	}

	public void setApikey(String apikey) {
		this.apikey = apikey;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getAppsecret() {
		return appsecret;
	}

	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}

	public String getHbpreorderUrl() {
		return hbpreorderUrl;
	}

	public void setHbpreorderUrl(String hbpreorderUrl) {
		this.hbpreorderUrl = hbpreorderUrl;
	}

	public String getAddlotteryinfoUrl() {
		return addlotteryinfoUrl;
	}

	public void setAddlotteryinfoUrl(String addlotteryinfoUrl) {
		this.addlotteryinfoUrl = addlotteryinfoUrl;
	}

	public String getSetprizebucketUrl() {
		return setprizebucketUrl;
	}

	public void setSetprizebucketUrl(String setprizebucketUrl) {
		this.setprizebucketUrl = setprizebucketUrl;
	}

	public String getSetlotteryswitchUrl() {
		return setlotteryswitchUrl;
	}

	public void setSetlotteryswitchUrl(String setlotteryswitchUrl) {
		this.setlotteryswitchUrl = setlotteryswitchUrl;
	}

	public String getCerFilePath() {
		return cerFilePath;
	}

	public void setCerFilePath(String cerFilePath) {
		this.cerFilePath = cerFilePath;
	}

	public String getOauthRedirectUri() {
		return oauthRedirectUri;
	}

	public void setOauthRedirectUri(String oauthRedirectUri) {
		this.oauthRedirectUri = oauthRedirectUri;
	}
	
	
}
