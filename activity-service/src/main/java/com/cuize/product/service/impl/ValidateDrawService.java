package com.cuize.product.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cuize.product.dao.domain.ShopActivityRewardctl;
import com.cuize.product.dao.domain.ShopActivityRewardctlExample;
import com.cuize.product.dao.mapper.ShopActivityDrawMapper;
import com.cuize.product.dao.mapper.ShopActivityRewardctlMapper;
import com.cuize.product.service.dto.ValidateDrawInDto;
import com.cuize.product.service.dto.ValidateDrawOutDto;

@Service
@Transactional(rollbackFor=Exception.class)
public class ValidateDrawService {
	private static final Logger _LOG = LoggerFactory.getLogger(ValidateDrawService.class);
	
	@Autowired 
	private ShopActivityDrawMapper activityDrawMapper;
	@Autowired 
	private ShopActivityRewardctlMapper activityRewardctlMapper;

	public synchronized ValidateDrawOutDto validateDraw(ValidateDrawInDto indto) {
		String openid = indto.getOpenid();
		ValidateDrawOutDto result=new ValidateDrawOutDto();
		
		//查看用户是否已经中奖
		ShopActivityRewardctlExample ctlExample = new ShopActivityRewardctlExample();
		ctlExample.createCriteria().andOpenidEqualTo(openid);
		List<ShopActivityRewardctl> rewardLst = activityRewardctlMapper.selectByExample(ctlExample);
		//如果已经中奖，状态设置为1 并返回
		if(rewardLst!=null && rewardLst.size()!=0){
			_LOG.info("*******用户openid【"+openid+"】 已经存在中奖记录*******");
			result.setStatus(1);
		}else{
			result.setStatus(0);
		}
		return result;
	}
	
}
