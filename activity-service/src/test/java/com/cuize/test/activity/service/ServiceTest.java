/**
 * 
 */
package com.cuize.test.activity.service;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cuize.activity.service.dto.ValidateDrawInDto;
import com.cuize.activity.service.impl.ValidateDrawService;

/**
 * @author xyz(Auto-generated)
 * The Service class for the ko_product_stock database table.
 *
 */
public class ServiceTest extends BaseServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(ServiceTest.class);

	@Autowired
	private ValidateDrawService queryTicketListService;
	
	@Test
	public void countByParams() throws Exception {
		ValidateDrawInDto inDto = new ValidateDrawInDto();
		inDto.setOpenid("");
		
		logger.info("******"+queryTicketListService.validateDraw(inDto));
	}

}
