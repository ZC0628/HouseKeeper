package com.zc.contacts.base.utils;

import java.lang.annotation.RetentionPolicy;
import java.math.BigDecimal;

/**
 * 		字节转换MB
 */
public final class ByteToMbUtil {
	/**
	 * 字节转换成MB，并且保留两位小数
	 */
	public static double byteTomb(long number){
		double n = number/1024.0/1024;
		BigDecimal bd = new BigDecimal(n);
		//四舍五入保留两位小数
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		return bd.doubleValue();
	}
	
}
