package com.cuize.activity.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cuize.activity.dao.domain.ActivityUserctl;
import com.cuize.activity.dao.domain.ActivityUserctlExample;
import com.cuize.activity.dao.domain.Award;
import com.cuize.activity.dao.mapper.ActivityUserctlMapper;
import com.cuize.activity.dao.mapper.AwardMapper;
import com.cuize.activity.service.dto.ValidateDrawInDto;
import com.cuize.activity.service.dto.ValidateDrawOutDto;

@Service
@Transactional(rollbackFor=Exception.class)
public class ValidateDrawService {
	private static final Logger _LOG = LoggerFactory.getLogger(ValidateDrawService.class);
	
	@Autowired 
	private AwardMapper awardMapper;
	@Autowired 
	private ActivityUserctlMapper userctlMapper;

	public synchronized ValidateDrawOutDto validateDraw(ValidateDrawInDto indto) {
		String openid = indto.getOpenid();
		String activityCode = indto.getActivityCode();
		ValidateDrawOutDto result=new ValidateDrawOutDto();
		
		//查看用户是否已经中奖
		ActivityUserctlExample ctlExample = new ActivityUserctlExample();
		ctlExample.createCriteria().andOpenidEqualTo(openid);
		List<ActivityUserctl> rewardLst = userctlMapper.selectByExample(ctlExample);
		//如果已经中奖，状态设置为1 并返回
		if(rewardLst!=null && rewardLst.size()!=0){
			result.setStatus(1);
			_LOG.info("*******用户openid【"+openid+"】 已经存在中奖记录*******");
		}else{
			//如果当前时间没有活动 返回2
			String today = new SimpleDateFormat("YYYY-MM-dd").format(new Date());
			List<Award> awdLst = awardMapper.selectByActivityCodeAndDate(activityCode, today);
			if(awdLst==null || awdLst.size()<=0){
				result.setStatus(2);//没有可参加的活动
				_LOG.info("*******activity code【"+activityCode+"】在【"+today+"】没有活动*******");
			}else{
				//如果当前时间有活动，返回奖品名称和奖品链接
				result.setStatus(0);
				result.setAwardList(awdLst);
			}
		}
		
		return result;
	}
	
}
