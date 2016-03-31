package com.cuize.product.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cuize.product.dao.domain.Shop;
import com.cuize.product.dao.domain.ShopExample;
import com.cuize.product.dao.domain.ShopProduct;
import com.cuize.product.dao.domain.ShopProductExample;
import com.cuize.product.dao.mapper.ShopMapper;
import com.cuize.product.dao.mapper.ShopProductMapper;
import com.cuize.product.service.dto.QueryTicketListInDto;
import com.cuize.product.service.dto.QueryTicketListOutDto;
import com.cuize.product.utils.BeanInitialUtils;

/**
 * 产品库存入库接口
 * 
 * @author luqingsong
 *
 */
@Service
@Transactional(rollbackFor=Exception.class)
public class QueryTicketListService{
	private static final Logger _LOG = LoggerFactory.getLogger(QueryTicketListService.class);
	
	@Autowired 
	private ShopProductMapper shopProductMapper;
	@Autowired 
	private ShopMapper shopMapper;;
	
	/**
	 * 产品库存入库接口
	 * @author luqingsong
	 */
	public List<QueryTicketListOutDto> queryTicketList(QueryTicketListInDto inDto) throws Exception{
		BeanInitialUtils.checkRequire(inDto);
		
		ShopExample shopExample = new ShopExample();
		shopExample.createCriteria()
			.andParentIdEqualTo(inDto.getScenicId());
		shopExample.or().andIdEqualTo(inDto.getScenicId());
		List<Shop> parkLst = shopMapper.selectByExample(shopExample);
		_LOG.info("*******fund park List "+parkLst+" by parent scenicId【"+inDto.getScenicId()+"】");
		
		
		
		List<QueryTicketListOutDto> outdtoLst = new ArrayList<QueryTicketListOutDto>();
		for(Shop park : parkLst){
			_LOG.info("*******fund park "+park+" by scenicId【"+inDto.getScenicId()+"】");
			ShopProductExample example = new ShopProductExample();
			example.createCriteria()
				.andShopIdEqualTo(park.getId())
				.andDayTicketTypeEqualTo(inDto.getDateType());
			List<ShopProduct> ticketLst = shopProductMapper.selectByExample(example);
			_LOG.info("*******fund ticket List "+ticketLst+" by scenicId【"+inDto.getScenicId()+"】");
			
			for(ShopProduct ticket:ticketLst){
				QueryTicketListOutDto outDto = new QueryTicketListOutDto();
				BeanUtils.copyProperties(ticket, outDto);
				outDto.setParkName(park.getName());
				outDto.setParkAbstract(park.getParkAbstract());
				outdtoLst.add(outDto);
			}
		}
		Collections.sort(outdtoLst, new Comparator<QueryTicketListOutDto>() {

			@Override
			public int compare(QueryTicketListOutDto o1,
					QueryTicketListOutDto o2) {
				return o1.getSortNum()-o2.getSortNum();
			}
		});
		return outdtoLst;
	}

}
