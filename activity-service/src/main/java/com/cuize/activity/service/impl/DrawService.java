package com.cuize.activity.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cuize.activity.dao.domain.ActivityAward;
import com.cuize.activity.dao.domain.ActivityAwardExample;
import com.cuize.activity.dao.domain.ActivityUserctl;
import com.cuize.activity.dao.domain.ActivityUserctlExample;
import com.cuize.activity.dao.mapper.ActivityAwardMapper;
import com.cuize.activity.dao.mapper.ActivityUserctlMapper;
import com.cuize.activity.service.dto.DrawInDto;
import com.cuize.activity.service.dto.DrawOutDto;

@Service
@Transactional(rollbackFor=Exception.class)
public class DrawService {
	private static final Logger _LOG = LoggerFactory.getLogger(DrawService.class);
	
	private static ConcurrentHashMap<String,List<ActivityAward>> todayWard = new ConcurrentHashMap<String,List<ActivityAward>>();
	
	@Autowired 
	private ActivityAwardMapper awardMapper;
	@Autowired 
	private ActivityUserctlMapper userctlMapper;

	public synchronized DrawOutDto activityDraw(DrawInDto indto) {
		String today = new SimpleDateFormat("YYYY-MM-dd").format(new Date());
		String openid = indto.getOpenid();
		String activityCode = indto.getActivityCode();
		DrawOutDto result=new DrawOutDto();
		
		
		//查看用户是否已经中奖
		ActivityUserctlExample ctlExample = new ActivityUserctlExample();
		ctlExample.createCriteria().andOpenidEqualTo(openid);
		List<ActivityUserctl>  rewardLst = userctlMapper.selectByExample(ctlExample);
		//如果已经中奖，状态设置为1 并返回
		if(rewardLst!=null && rewardLst.size()!=0){
			_LOG.info("*******用户openid【"+openid+"】 已中奖*******");
			result.setStatus(1);
			return result;
		}
		
		String key=activityCode+"-"+today;
		//将当日剩余数量大于的的奖品初始化到list中
		if(todayWard.get(key)==null ||todayWard.get(key).size()<=0){
			ActivityAwardExample example1 = new ActivityAwardExample();
			example1.createCriteria()
				.andActivityDateEqualTo(today)
				.andActivityCodeEqualTo(activityCode)
				.andRemainCountGreaterThan(0);
			List<ActivityAward> aaList= awardMapper.selectByExample(example1) ;
			todayWard.put(key, aaList);
			_LOG.info("*******key 【"+key+"】初始化长度*******"+(aaList==null?0:aaList.size()));
		}
		
		List<ActivityAward> awardList = todayWard.get(key);
		if(awardList==null||awardList.size()<=0){
			result.setStatus(2);
			return result;
		}
		//5清源山 11九华山  8马仁奇峰   10九子岩  66祈福带
		int rom = new Random().nextInt(100);
		_LOG.info("*******rom*******"+rom);
		int min=0;int max=0;
		//遍历当前活动所有奖品的概率（百分数）
		for(int i=0;i<awardList.size();i++){
			ActivityAward a = awardList.get(i);
			min=max;
			max=max+a.getProbability().intValue();
			_LOG.info("*******min**max*****"+min+"---"+max);
			//如果随机数落在第i个区间，就中i奖
			if(min<=rom&&rom<max){
				result.setAwardCode(a.getAwardCode());
				if(a.getRemainCount()<=0){
					result.setCount(0);
				}else{
					//记录用户已经中奖
					ActivityUserctl ctl = new ActivityUserctl();
					ctl.setOpenid(openid);
					userctlMapper.insert(ctl);
					
					//将数据库剩余数量减一
					a.setRemainCount(a.getRemainCount()-1);
					awardMapper.updateByPrimaryKey(a);
					//维护list里面的对应元素：剩余数量减一、删除当日剩余数量为0 的奖项
					todayWard.get(key).set(i, a);
					result.setCount(1);
				}
				break;
			}
		}
		result.setStatus(0);
		_LOG.info("*******result*******"+result.getAwardCode());
		return result;
	}
}
