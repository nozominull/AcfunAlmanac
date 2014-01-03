package com.nozomi.almanac.util;

public class LunarUtil {

	public static String GetLunarDay(int solarYear, int solarMonth, int solarDay) {
		// solarYear = solarYear<1900?(1900+solarYear):solarYear;
		if (solarYear < 1921 || solarYear > 2020) {
			return "";
		} else {
			solarMonth = (solarMonth > 0) ? (solarMonth - 1) : 11;
			e2c(solarYear, solarMonth, solarDay);
			return e2c(solarYear, solarMonth, solarDay);
		}
	}

	private static int GetBit(int m, int n) {
		return (m >> n) & 1;
	}

	private static String e2c(int solarYear, int solarMonth, int solarDay) {
		int[] CalendarData = { 0xA4B, 0x5164B, 0x6A5, 0x6D4, 0x415B5, 0x2B6,
				0x957, 0x2092F, 0x497, 0x60C96, 0xD4A, 0xEA5, 0x50DA9, 0x5AD,
				0x2B6, 0x3126E, 0x92E, 0x7192D, 0xC95, 0xD4A, 0x61B4A, 0xB55,
				0x56A, 0x4155B, 0x25D, 0x92D, 0x2192B, 0xA95, 0x71695, 0x6CA,
				0xB55, 0x50AB5, 0x4DA, 0xA5B, 0x30A57, 0x52B, 0x8152A, 0xE95,
				0x6AA, 0x615AA, 0xAB5, 0x4B6, 0x414AE, 0xA57, 0x526, 0x31D26,
				0xD95, 0x70B55, 0x56A, 0x96D, 0x5095D, 0x4AD, 0xA4D, 0x41A4D,
				0xD25, 0x81AA5, 0xB54, 0xB6A, 0x612DA, 0x95B, 0x49B, 0x41497,
				0xA4B, 0xA164B, 0x6A5, 0x6D4, 0x615B4, 0xAB6, 0x957, 0x5092F,
				0x497, 0x64B, 0x30D4A, 0xEA5, 0x80D65, 0x5AC, 0xAB6, 0x5126D,
				0x92E, 0xC96, 0x41A95, 0xD4A, 0xDA5, 0x20B55, 0x56A, 0x7155B,
				0x25D, 0x92D, 0x5192B, 0xA95, 0xB4A, 0x416AA, 0xAD5, 0x90AB5,
				0x4BA, 0xA5B, 0x60A57, 0x52B, 0xA93, 0x40E95 };

		int[] madd = { 0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334 };
		boolean isEnd = false;
		int tmp = solarYear;
		if (tmp < 1900) {
			tmp += 1900;
		}

		int total = (tmp - 1921) * 365 + (int) Math.floor((tmp - 1921) / 4)
				+ madd[solarMonth] + solarDay - 38;
		if (solarYear % 4 == 0 && solarMonth > 1) {
			total++;
		}
		int m = 0;
		int k = 0;
		int n = 0;
		for (m = 0; m < 255; m++) {
			k = (CalendarData[m] < 0xfff) ? 11 : 12;
			for (n = k; n >= 0; n--) {
				if (total <= 29 + GetBit(CalendarData[m], n)) {
					isEnd = true;
					break;
				}

				total = total - 29 - GetBit(CalendarData[m], n);
			}
			if (isEnd) {
				break;
			}
		}

		int cYear = 1921 + m;
		int cMonth = k - n + 1;
		int cDay = total;
		if (k == 12) {
			if (cMonth == Math.floor(CalendarData[m] / 0x10000) + 1) {
				cMonth = 1 - cMonth;
			}
			if (cMonth > Math.floor(CalendarData[m] / 0x10000) + 1) {
				cMonth--;
			}
		}
		return GetcDateString(cYear, cMonth, cDay);
	}

	private static String GetcDateString(int cYear, int cMonth, int cDay) {
		String tgString = "甲乙丙丁戊己庚辛壬癸";
		String dzString = "子丑寅卯辰巳午未申酉戌亥";
		String sx = "鼠牛虎兔龙蛇马羊猴鸡狗猪";
		String monString = "正二三四五六七八九十冬腊";
		String numString = "一二三四五六七八九十";

		String tmp = "";
		tmp += tgString.charAt((cYear - 4) % 10);
		tmp += dzString.charAt((cYear - 4) % 12);
		tmp += "";
		tmp += sx.charAt((cYear - 4) % 12);
		tmp += "年 ";
		if (cMonth < 1) {
			tmp += "闰";
			tmp += monString.charAt(-cMonth - 1);
		} else {
			tmp += monString.charAt(cMonth - 1);
		}
		;
		tmp += "月";
		tmp += (cDay < 11) ? "初" : ((cDay < 20) ? "十" : ((cDay < 30) ? "廿"
				: "三十"));
		if (cDay % 10 != 0 || cDay == 10) {
			tmp += numString.charAt((cDay - 1) % 10);
		}
		return tmp;
	};

}
