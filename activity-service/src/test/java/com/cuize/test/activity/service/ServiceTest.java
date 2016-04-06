/**
 * 
 */
package com.cuize.test.activity.service;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cuize.activity.service.dto.DrawInDto;
import com.cuize.activity.service.impl.DrawService;

/**
 * @author xyz(Auto-generated)
 * The Service class for the ko_product_stock database table.
 *
 */
public class ServiceTest extends BaseServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(ServiceTest.class);

	@Autowired
	private DrawService service;
	
	@Test
	public void countByParams() throws Exception {
		DrawInDto inDto = new DrawInDto();
		inDto.setOpenid("");
		inDto.setActivityCode("");
		
		logger.info("******"+service.activityDraw(inDto));
	}

}
