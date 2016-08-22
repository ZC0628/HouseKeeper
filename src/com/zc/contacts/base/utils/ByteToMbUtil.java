package com.zc.contacts.base.utils;

import java.lang.annotation.RetentionPolicy;
import java.math.BigDecimal;

/**
 * 		�ֽ�ת��MB
 */
public final class ByteToMbUtil {
	/**
	 * �ֽ�ת����MB�����ұ�����λС��
	 */
	public static double byteTomb(long number){
		double n = number/1024.0/1024;
		BigDecimal bd = new BigDecimal(n);
		//�������뱣����λС��
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		return bd.doubleValue();
	}
	
}
