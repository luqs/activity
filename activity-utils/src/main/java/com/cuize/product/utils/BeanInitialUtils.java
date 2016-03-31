package com.cuize.product.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import javax.xml.rpc.ServiceException;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import com.cuize.product.meta.RequireField;
import com.google.common.base.Defaults;
import com.google.common.primitives.Primitives;

public class BeanInitialUtils {

	public static final <T> T initWithDefalutValue(Class<T> clazz) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		T inst = BeanUtils.instantiate(clazz);
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			Class<?> type = field.getType();
			String fieldName = field.getName();
			if (StringUtils.equalsIgnoreCase(fieldName, "serialVersionUID")) {
				continue;
			}
			if (Primitives.isWrapperType(type) || type.isPrimitive()) {
				PropertyUtils.setProperty(inst, fieldName, Defaults.defaultValue(Primitives.unwrap(type)));
			}

		}
		return inst;
	}
	
	public static final <T> boolean checkRequire(T t) throws Exception{
		Field[] fields = t.getClass().getDeclaredFields();
		for (Field field : fields) {
			RequireField r = field.getAnnotation(RequireField.class);
			if(r!=null){
				field.setAccessible(true);
				Object fieldValue = field.get(t);
				if(fieldValue==null||(fieldValue instanceof String && StringUtils.isEmpty((String)fieldValue))){
					throw new ServiceException(field.getName()+" 不能为空");
				}
			}
		}
		return true;
	}

}
