package com.bruin.util;

import java.math.BigDecimal;
import java.util.Objects;

public class DoubleUtil {

	public static double multiplication(Double d1, Double d2) {
		return multiplication(d1, d2, 2);
	}

	/**
	 *
	 * @param d1 第一个数字
	 * @param d2 第二个数字
	 * @param num 小数点保留位数
	 * @return 返回一个double类型的值
	 */
	public static double multiplication(Double d1, Double d2, int num) {
		if (Objects.isNull(d1) && Objects.isNull(d2)) {
			throw new NullPointerException("相关数字为空!");
		}
		return new BigDecimal(d1.toString()).multiply(new BigDecimal(d2.toString()))
				.setScale(num, BigDecimal.ROUND_HALF_UP).doubleValue();
	}


	public static double divide(Double dividend, Double divisor) {
		return divide(dividend, divisor, 2);
	}

	/**
	 * double 相除
	 * 
	 * @param dividend 被除数
	 * @param divisor 除数
	 * @param num 小数点后面保留位数
	 * @return double 类型的值
	 */
	public static double divide(Double dividend, Double divisor, int num) {
		if (Objects.isNull(divisor) && Objects.isNull(dividend)) {
			throw new NullPointerException("除数或被除数为null");
		}
		return new BigDecimal(dividend.toString()).divide(new BigDecimal(divisor.toString()), BigDecimal.ROUND_HALF_UP)
				.setScale(num, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

}
