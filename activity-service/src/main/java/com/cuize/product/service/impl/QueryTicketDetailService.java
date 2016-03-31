package com.cuize.product.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cuize.product.dao.domain.ShopProduct;
import com.cuize.product.dao.mapper.ShopProductMapper;
import com.cuize.product.service.dto.QueryTicketDetailInDto;
import com.cuize.product.service.dto.QueryTicketDetailOutDto;
import com.cuize.product.utils.BeanInitialUtils;

/**
 * 产品库存入库接口
 * 
 * @author luqingsong
 *
 */
@Service
@Transactional(rollbackFor=Exception.class)
public class QueryTicketDetailService{
	private static final Logger _LOG = LoggerFactory.getLogger(QueryTicketDetailService.class);
	
	@Autowired 
	private ShopProductMapper shopProductMapper;
	
	/**
	 * 产品库存入库接口
	 * @author luqingsong
	 */
	public QueryTicketDetailOutDto queryTicketDetail(QueryTicketDetailInDto inDto) throws Exception{
		BeanInitialUtils.checkRequire(inDto);
		Integer ticketId = inDto.getTicketId();
		
		ShopProduct ticket= shopProductMapper.selectByPrimaryKey(ticketId);
		_LOG.info("*******no tiket fund by id【"+ticketId+"】");
		QueryTicketDetailOutDto outDto = new QueryTicketDetailOutDto();
		outDto.setData(ticket);
		return outDto;
	}

}
