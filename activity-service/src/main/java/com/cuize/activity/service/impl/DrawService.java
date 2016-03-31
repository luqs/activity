package com.cuize.activity.service.impl;

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
	private static String dateA="";
	private static String dateB="";
	private static String dateC="";
	
	private static List<ActivityAward> todayALst = new ArrayList<ActivityAward>();
	private static List<ActivityAward> todayBLst = new ArrayList<ActivityAward>();
	private static List<ActivityAward> todayCLst = new ArrayList<ActivityAward>();
	
	@Autowired 
	private ActivityAwardMapper awardMapper;
	@Autowired 
	private ActivityUserctlMapper userctlMapper;

	public synchronized DrawOutDto activityDraw(DrawInDto indto) {
		String today = new SimpleDateFormat("YYYY-MM-dd").format(new Date());
		String openid = indto.getOpenid();
		Integer id = indto.getId();
		DrawOutDto result=new DrawOutDto();
		
		
		//查看用户是否已经中奖
		ActivityUserctlExample ctlExample = new ActivityUserctlExample();
		ctlExample.createCriteria().andOpenidEqualTo(openid);
		List<ActivityUserctl>  rewardLst = userctlMapper.selectByExample(ctlExample);
		//如果已经中奖，状态设置为1 并返回
		if(rewardLst!=null && rewardLst.size()!=0){
			_LOG.info("*******用户openid【"+openid+"】 已经存在中奖记录*******");
			result.setStatus(1);
			return result;
		}
		
		//将当日剩余数量大于的的奖品初始化到list中
		if(!dateA.equals(today)||todayALst.size()<=0){
			ActivityAwardExample example1 = new ActivityAwardExample();
			example1.createCriteria()
				.andActivityDateEqualTo(today)
				.andActivityIdEqualTo(id)
				.andRemainCountGreaterThan(0);
			todayALst= awardMapper.selectByExample(example1) ;
			dateA=today;
			_LOG.info("*******today  ALst初始化*******"+todayALst.size());
		}
		if(!dateB.equals(today)||todayBLst.size()<=0){
			ActivityAwardExample example2 = new ActivityAwardExample();
			example2.createCriteria()
				.andActivityDateEqualTo(today)
				.andActivityIdEqualTo(id)
				.andRemainCountGreaterThan(0);
			todayBLst = awardMapper.selectByExample(example2) ;
			dateB = today;
			_LOG.info("*******today  BLst初始化*******"+todayBLst.size());
		}	
		if(!dateC.equals(today)||todayCLst.size()<=0){
			ActivityAwardExample example3 = new ActivityAwardExample();
			example3.createCriteria()
				.andActivityDateEqualTo(today)
				.andActivityIdEqualTo(id)
				.andRemainCountGreaterThan(0);
			todayCLst = awardMapper.selectByExample(example3) ;
			dateC = today;
			_LOG.info("*******today  CLst初始化*******"+todayCLst.size());
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
			ActivityAward finalAward = null;
			int index = -1;
			for(int i = 0;i<todayALst.size();i++){
				ActivityAward award =todayALst.get(i);
				if(award.getAwardName().indexOf(name)>=0){
					finalAward=award;
					index=i;
					break;
				}
			}
			if(finalAward==null){
				for(int i = 0;i<todayALst.size();i++){
					ActivityAward award =todayALst.get(i);
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
			ActivityUserctl ctl = new ActivityUserctl();
			ctl.setOpenid(openid);
			userctlMapper.insert(ctl);
			
			//将数据库剩余数量减一
			finalAward.setRemainCount(finalAward.getRemainCount()-1);
			awardMapper.updateByPrimaryKey(finalAward);
			//维护list里面的对应元素：剩余数量减一、删除当日剩余数量为0 的奖项
			todayALst.set(index, finalAward);
			if(finalAward.getRemainCount()<=0){
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
			ActivityAward finalAward = null;
			
			int index = -1;
			for(int i = 0;i<todayBLst.size();i++){
				ActivityAward award =todayBLst.get(i);
				if(award.getAwardName().indexOf(name)>=0){
					finalAward=award;
					index=i;
					break;
				}
			}
			if(finalAward==null){
				for(int i = 0;i<todayBLst.size();i++){
					ActivityAward award =todayBLst.get(i);
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
			
			ActivityUserctl ctl = new ActivityUserctl();
			ctl.setOpenid(openid);
			userctlMapper.insert(ctl);
			
			finalAward.setRemainCount(finalAward.getRemainCount()-1);
			awardMapper.updateByPrimaryKey(finalAward);
			todayBLst.set(index, finalAward);
			if(finalAward.getRemainCount()<=0){
				todayBLst.remove(index);
			}
			result.setName(finalAward.getAwardName());
		}else if(3==id){
			
			//0-1清源山  2-3九华山  4-5马仁奇峰   6-7九子岩 8-100祈福带
			//按照list的size 随机抽取奖项
			int rom = new Random().nextInt(100);
			String name = "";
			if(rom==1){
				name = "瘦西湖";
			}else if(rom==2){
				name = "大明寺";
			}else if(rom<=17 && rom>=3){
				name = "红包";
			}else{
				name="谢谢参与";
			}
			ActivityAward finalAward = null;
			int index = -1;
			for(int i = 0;i<todayCLst.size();i++){
				ActivityAward award =todayCLst.get(i);
				if(award.getAwardName().indexOf(name)>=0){
					finalAward=award;
					index=i;
					break;
				}
			}
			
			if(finalAward==null){
				result.setStatus(0);
				result.setName("谢谢参与");
				
				ActivityUserctl ctl = new ActivityUserctl();
				ctl.setOpenid(openid);
				userctlMapper.insert(ctl);
				return result;
			}
			result.setName(finalAward.getAwardName());
			
			ActivityUserctl ctl = new ActivityUserctl();
			ctl.setOpenid(openid);
			userctlMapper.insert(ctl);
			
			finalAward.setRemainCount(finalAward.getRemainCount()-1);
			awardMapper.updateByPrimaryKey(finalAward);
			todayCLst.set(index, finalAward);
			if(finalAward.getRemainCount()<=0){
				todayCLst.remove(index);
			}
			result.setName(finalAward.getAwardName());
		}
		result.setStatus(0);
		
		return result;
	}
	
	public static void main(String[] args) {
		System.out.println("name".indexOf(null));
	}
}
