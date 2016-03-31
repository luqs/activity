package com.cuize.activity.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cuize.activity.dao.domain.Activity;
import com.cuize.activity.dao.domain.ActivityAward;
import com.cuize.activity.dao.domain.ActivityAwardExample;
import com.cuize.activity.dao.domain.ActivityExample;
import com.cuize.activity.dao.domain.ActivityUserctl;
import com.cuize.activity.dao.domain.ActivityUserctlExample;
import com.cuize.activity.dao.mapper.ActivityAwardMapper;
import com.cuize.activity.dao.mapper.ActivityMapper;
import com.cuize.activity.dao.mapper.ActivityUserctlMapper;
import com.cuize.activity.service.dto.ValidateDrawInDto;
import com.cuize.activity.service.dto.ValidateDrawOutDto;

@Service
@Transactional(rollbackFor=Exception.class)
public class ValidateDrawService {
	private static final Logger _LOG = LoggerFactory.getLogger(ValidateDrawService.class);
	
	@Autowired 
	private ActivityMapper activityMapper;
	@Autowired 
	private ActivityAwardMapper awardMapper;
	@Autowired 
	private ActivityUserctlMapper userctlMapper;

	public synchronized ValidateDrawOutDto validateDraw(ValidateDrawInDto indto) {
		String openid = indto.getOpenid();
		Integer shopId = indto.getShopId();
		ValidateDrawOutDto result=new ValidateDrawOutDto();
		
		String today = new SimpleDateFormat("YYYY-MM-dd").format(new Date());
		
		ActivityExample activityExample = new ActivityExample();
		activityExample.createCriteria().andShopIdEqualTo(shopId);
		List<Activity> acLst = activityMapper.selectByExample(activityExample);
		
		if(acLst==null||acLst.size()<=0){
			//没有可参加的活动
			result.setStatus(2);
			_LOG.info("*******shop Id【"+shopId+"】没有活动*******");
		}else{
			List<Integer> idLst = new ArrayList<Integer>();
			for(Activity ac : acLst){
				idLst.add(ac.getId());
			}
			ActivityAwardExample awardExample = new ActivityAwardExample();
			awardExample.createCriteria().andActivityIdIn(idLst).andActivityDateEqualTo(today);
			List<ActivityAward> awardLst = awardMapper.selectByExample(awardExample);
			
			if(awardLst==null||awardLst.size()<=0){
				//当前店铺今天没有活动
				result.setStatus(2);//没有可参加的活动
				_LOG.info("*******shop Id【"+shopId+"】在【"+today+"】没有活动*******");
			}else{
				//查看用户是否已经中奖
				ActivityUserctlExample ctlExample = new ActivityUserctlExample();
				ctlExample.createCriteria().andOpenidEqualTo(openid);
				List<ActivityUserctl> rewardLst = userctlMapper.selectByExample(ctlExample);
				//如果已经中奖，状态设置为1 并返回
				if(rewardLst!=null && rewardLst.size()!=0){
					result.setStatus(1);
					_LOG.info("*******用户openid【"+openid+"】 已经存在中奖记录*******");
				}else{
					result.setStatus(0);
					result.setActivityId(awardLst.get(0).getId());
				}
			}
		}
		
		return result;
	}
	
}
