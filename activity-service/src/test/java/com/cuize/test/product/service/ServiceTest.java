/**
 * 
 */
package com.cuize.test.product.service;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cuize.product.service.dto.QueryTicketListInDto;
import com.cuize.product.service.dto.QueryTicketListOutDto;
import com.cuize.product.service.impl.QueryTicketListService;

/**
 * @author xyz(Auto-generated)
 * The Service class for the ko_product_stock database table.
 *
 */
public class ServiceTest extends BaseServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(ServiceTest.class);

	@Autowired
	private QueryTicketListService queryTicketListService;
	
	@Test
	public void countByParams() throws Exception {
		QueryTicketListInDto inDto = new QueryTicketListInDto();
		inDto.setScenicId(1);
		inDto.setDateType(1);
		
		for(QueryTicketListOutDto s:queryTicketListService.queryTicketList(inDto)){
			System.out.println(s.getSortNum());
		}
		logger.info("******"+queryTicketListService.queryTicketList(inDto));
	}

}
