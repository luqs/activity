package com.cuize.test.activity.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cuize.activity.dao.mapper.ActivityMapper;

/**
 * @author Ralph
 * The Service class for the ko_insurance_order database table.
 *
 */
public class ShopMapperTest extends DaoBaseAppTest{
//	private static final Logger logger = LoggerFactory.getLogger(InsuranceOrderMapperTest.class);

	@Autowired
	private ActivityMapper shopMapper;
	/*
	@Test
	public void selectByExample(){
		try{
			Map<Object, Object> params = new HashMap<Object, Object>();
			KoInsuranceOrder ret = this.koInsuranceOrderMapper.selectByPrimaryKey("150526000001");
			System.out.println("\n@@ DEBUG: " + ret.toString() + "\n");
		}catch (NullPointerException e) {
			System.out.println("\n@@ DEBUG: " + "NO records found" + "\n");
		}
	}*/
	
	@Test
	public void selectDescOrderByExample(){
		
		System.out.println(shopMapper.selectByPrimaryKey(3));
	}
	
	/*
	@Test
	public void insert(){
		KoInsuranceOrder record = new KoInsuranceOrder();
		
		record.setInsureOrderId("150526000002");
		Calendar calendar = Calendar.getInstance();
		calendar.set(2015, 11, 22, 21, 8, 53);
		record.setInsureApplyDate( calendar.getTime() );
		record.setInsureName("_NAME_");
		record.setInsureSex("0");
		record.setInsureCertType("1");
		record.setInsureCertNum("431502198805185421");
		record.setInsurePhone("15658068859");
		record.setInsureEmail("123456@126.com");
		record.setInsureProductNo("U2001");
		record.setInsureBankCode("1008");
		record.setInsureBankCount("54215525424512266");
		calendar.set(2015, 11, 22, 21, 14, 33);
		record.setInsureCreatTime( calendar.getTime() );
		
		Integer ret = this.koInsuranceOrderMapper.insert(record);
		System.out.println("\n@@ DEBUG: " + ret.toString() + "\n");	
	}*/
}
