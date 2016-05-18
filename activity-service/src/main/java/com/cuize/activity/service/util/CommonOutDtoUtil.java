package com.cuize.activity.service.util;

import com.cuize.activity.service.dto.common.CommonOutDto;

public class CommonOutDtoUtil {

	public static void setResult(CommonOutDto commonOutDto, boolean success, String resultCode, String resultMsg){
		commonOutDto.setSuccess(success);
		commonOutDto.setResultCode(resultCode);
		commonOutDto.setResultMsg(resultMsg);
	}
}
