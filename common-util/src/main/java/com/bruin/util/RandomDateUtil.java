package com.bruin.util;

import java.time.LocalDate;
import java.util.Random;

public class RandomDateUtil {

	private static int EVERY_MONTH_START_DAY = 1;

	private static final Random random = new Random();

	public static void main(String[] args) {

		for (int i = 0; i < 20; i++) {

			System.out.println(radomInt(2, true));
		}


	}

	public static String randomDate(int year, int month, int num, boolean isRemoveWeek) {
		LocalDate targetMonth = LocalDate.of(year, month, EVERY_MONTH_START_DAY);

		int days = targetMonth.lengthOfMonth();
		for (int i = 0; i < num; i++) {
			int i1 = radomIntBound(days, true);
			if (isRemoveWeek) {
				int value = targetMonth.plusDays(i1).getDayOfWeek().getValue();
				if (value==6 || value==7){
					i--;
					continue;
				}
			}

		}

		return "";
	}

	private static int radomIntBound(int bound, boolean isRemoveZero) {
		return radomInt(bound + 1, isRemoveZero);
	}

	private static int radomInt(int bound, boolean isRemoveZero) {
		int result;
		while ((result = random.nextInt(bound)) == 0 && isRemoveZero) {
		}
		return result;
	}

}
