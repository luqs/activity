package com.cuize.activity.dao.mapper;

import com.cuize.activity.dao.domain.Award;
import com.cuize.activity.dao.domain.AwardExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AwardMapper {
    /**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table award
	 * @mbggenerated  Wed Apr 06 11:15:31 CST 2016
	 */
	int countByExample(AwardExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table award
	 * @mbggenerated  Wed Apr 06 11:15:31 CST 2016
	 */
	int deleteByExample(AwardExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table award
	 * @mbggenerated  Wed Apr 06 11:15:31 CST 2016
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table award
	 * @mbggenerated  Wed Apr 06 11:15:31 CST 2016
	 */
	int insert(Award record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table award
	 * @mbggenerated  Wed Apr 06 11:15:31 CST 2016
	 */
	int insertSelective(Award record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table award
	 * @mbggenerated  Wed Apr 06 11:15:31 CST 2016
	 */
	List<Award> selectByExample(AwardExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table award
	 * @mbggenerated  Wed Apr 06 11:15:31 CST 2016
	 */
	Award selectByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table award
	 * @mbggenerated  Wed Apr 06 11:15:31 CST 2016
	 */
	int updateByExampleSelective(@Param("record") Award record,
			@Param("example") AwardExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table award
	 * @mbggenerated  Wed Apr 06 11:15:31 CST 2016
	 */
	int updateByExample(@Param("record") Award record,
			@Param("example") AwardExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table award
	 * @mbggenerated  Wed Apr 06 11:15:31 CST 2016
	 */
	int updateByPrimaryKeySelective(Award record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table award
	 * @mbggenerated  Wed Apr 06 11:15:31 CST 2016
	 */
	int updateByPrimaryKey(Award record);

	//=============add by luqs
    List<Award> selectByActivityCodeAndDate(@Param("activityCode") String activityCode, @Param("activityDate") String activityDate);
}