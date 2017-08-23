package com.awifi.np.biz.common.ms.util;

public class TimeTag {

	public static String getTimeFromInt(Integer time) {
		if (time == null) {
			return null;
		}
		int hour = time / 3600;
		int min = (time - hour * 3600) / 60;
		StringBuilder sy = new StringBuilder();
		if (hour < 10) {
			sy.append("0").append(hour);
		} else {
			sy.append(hour);
		}
		sy.append(":");
		if (min < 10) {
			sy.append("0").append(min);
		} else {
			sy.append(min);
		}
		return sy.toString();
	}

	public static String getTimeFromStr(String time) {
		try {
			String[] arry = time.split(":");
			if (arry.length != 2) {
				return null;
			}
			Integer yyTime = Integer.valueOf(arry[0]) * 3600 + Integer.valueOf(arry[1]) * 60;
			return yyTime.toString();
		} catch (Exception e) {
			////System.out.println("营业时间转换出错了");
			return null;
		}
	}

	public static void main(String[] args) {
		////System.out.println(getTimeFromInt(3660));
	}

}
