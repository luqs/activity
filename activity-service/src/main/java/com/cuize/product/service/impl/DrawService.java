package com.cuize.product.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cuize.product.dao.domain.ShopActivityDraw;
import com.cuize.product.dao.domain.ShopActivityDrawExample;
import com.cuize.product.dao.domain.ShopActivityRewardctl;
import com.cuize.product.dao.domain.ShopActivityRewardctlExample;
import com.cuize.product.dao.mapper.ShopActivityDrawMapper;
import com.cuize.product.dao.mapper.ShopActivityRewardctlMapper;
import com.cuize.product.service.dto.DrawInDto;
import com.cuize.product.service.dto.DrawOutDto;

@Service
@Transactional(rollbackFor=Exception.class)
public class DrawService {
	private static final Logger _LOG = LoggerFactory.getLogger(DrawService.class);
	private static String dateA="";
	private static String dateB="";
	
	private static List<ShopActivityDraw> todayALst = new ArrayList<ShopActivityDraw>();
	private static List<ShopActivityDraw> todayBLst = new ArrayList<ShopActivityDraw>();
	
	@Autowired 
	private ShopActivityDrawMapper activityDrawMapper;
	@Autowired 
	private ShopActivityRewardctlMapper activityRewardctlMapper;

	public synchronized DrawOutDto activityDraw(DrawInDto indto) {
		String today = new SimpleDateFormat("YYYY-MM-dd").format(new Date());
		String openid = indto.getOpenid();
		Integer id = indto.getId();
		DrawOutDto result=new DrawOutDto();
		
		
		//查看用户是否已经中奖
		ShopActivityRewardctlExample ctlExample = new ShopActivityRewardctlExample();
		ctlExample.createCriteria().andOpenidEqualTo(openid);
		List<ShopActivityRewardctl> rewardLst = activityRewardctlMapper.selectByExample(ctlExample);
		//如果已经中奖，状态设置为1 并返回
		if(rewardLst!=null && rewardLst.size()!=0){
			_LOG.info("*******用户openid【"+openid+"】 已经存在中奖记录*******");
			result.setStatus(1);
			return result;
		}
		
		//将当日剩余数量大于的的奖品初始化到list中
		if(!dateA.equals(today)||todayALst.size()<=0){
			ShopActivityDrawExample example1 = new ShopActivityDrawExample();
			example1.createCriteria()
				.andActivityDateEqualTo(today)
				.andActivityIdEqualTo(id)
				.andSurplusCountGreaterThan(0);
			todayALst= activityDrawMapper.selectByExample(example1) ;
			dateA=today;
			_LOG.info("*******today  ALst初始化*******"+todayALst.size());
		}
		if(!dateB.equals(today)||todayBLst.size()<=0){
			ShopActivityDrawExample example2 = new ShopActivityDrawExample();
			example2.createCriteria()
				.andActivityDateEqualTo(today)
				.andActivityIdEqualTo(id)
				.andSurplusCountGreaterThan(0);
			todayBLst = activityDrawMapper.selectByExample(example2) ;
			dateB = today;
			_LOG.info("*******today  BLst初始化*******"+todayBLst.size());
		}		
		
		if(1==id){
			//0-1清源山  2-3九华山  4-5马仁奇峰   6-7九子岩 8-100祈福带
			//按照list的size 随机抽取奖项
			int rom = new Random().nextInt(100);
			String name = "";
			if(rom<=1 && rom>=0){
				name = "清源山";
			}else if(rom<=3 && rom>=2){
				name = "九华大佛";
			}else if(rom<=5 && rom>=4){
				name = "马仁奇峰";
			}else if(rom<=7 && rom>=6){
				name = "九子岩";
			}else if(rom<=100 && rom>=8){
				name = "祈福带";
			}
			ShopActivityDraw finalAward = null;
			int index = -1;
			for(int i = 0;i<todayALst.size();i++){
				ShopActivityDraw award =todayALst.get(i);
				if(award.getAwardName().indexOf(name)>=0){
					finalAward=award;
					index=i;
					break;
				}
			}
			if(finalAward==null){
				for(int i = 0;i<todayALst.size();i++){
					ShopActivityDraw award =todayALst.get(i);
					if(award.getAwardName().indexOf("祈福带")>=0){
						finalAward=award;
						index =i;
						break;
					}
				}
			}
			
			if(finalAward==null){
				return null;
			}
			
			result.setName(finalAward.getAwardName());
			
			//记录用户已经中奖
			ShopActivityRewardctl ctl = new ShopActivityRewardctl();
			ctl.setOpenid(openid);
			activityRewardctlMapper.insert(ctl);
			
			//将数据库剩余数量减一
//			ShopActivityDrawExample example = new ShopActivityDrawExample();
//			example.createCriteria().andIdEqualTo(finalAward.getId());
//			finalAward.setVersion(finalAward.getVersion()+1);
			finalAward.setSurplusCount(finalAward.getSurplusCount()-1);
//			activityDrawMapper.updateByExample(finalAward, example);
			activityDrawMapper.updateByPrimaryKey(finalAward);
			//维护list里面的对应元素：剩余数量减一、删除当日剩余数量为0 的奖项
			todayALst.set(index, finalAward);
			if(finalAward.getSurplusCount()<=0){
				todayALst.remove(index);
			}
			
			result.setName(finalAward.getAwardName());

		}else if(2==id){
			
			//0-1清源山  2-3九华山  4-5马仁奇峰   6-7九子岩 8-100祈福带
			//按照list的size 随机抽取奖项
			int rom = new Random().nextInt(100);
			String name = "";
			if(rom<=1 && rom>=0){
				name = "清源山";
			}else if(rom<=3 && rom>=2){
				name = "九华大佛";
			}else if(rom<=5 && rom>=4){
				name = "马仁奇峰";
			}else if(rom<=7 && rom>=6){
				name = "九子岩";
			}else if(rom<=12 && rom>=8){
				name = "佛珠";
			}
			else if(rom<=12 && rom>=8){
				name = "佛珠";
			}else if(rom<=100 && rom>=13){
				name = "祈福带";
			}
			ShopActivityDraw finalAward = null;
			
			int index = -1;
			for(int i = 0;i<todayBLst.size();i++){
				ShopActivityDraw award =todayBLst.get(i);
				if(award.getAwardName().indexOf(name)>=0){
					finalAward=award;
					index=i;
					break;
				}
			}
			if(finalAward==null){
				for(int i = 0;i<todayBLst.size();i++){
					ShopActivityDraw award =todayBLst.get(i);
					if(award.getAwardName().indexOf("祈福带")>=0){
						finalAward=award;
						index =i;
						break;
					}
				}
			}
			
			if(finalAward==null){
				return null;
			}
			
			result.setName(finalAward.getAwardName());
			
			ShopActivityRewardctl ctl = new ShopActivityRewardctl();
			ctl.setOpenid(openid);
			activityRewardctlMapper.insert(ctl);
			
//			ShopActivityDrawExample example = new ShopActivityDrawExample();
//			example.createCriteria().andVersionEqualTo(finalAward.getVersion()).andIdEqualTo(finalAward.getId());
//			finalAward.setVersion(finalAward.getVersion()+1);
			finalAward.setSurplusCount(finalAward.getSurplusCount()-1);
//			activityDrawMapper.updateByExample(finalAward, example);
			activityDrawMapper.updateByPrimaryKey(finalAward);
			todayBLst.set(index, finalAward);
			if(finalAward.getSurplusCount()<=0){
				todayBLst.remove(index);
			}
			
			result.setName(finalAward.getAwardName());
		}
		result.setStatus(0);
		
		return result;
	}
}
