package com.awifi.np.biz.timebuysrv.web.core.rules;

import com.awifi.np.biz.timebuysrv.util.StringUtil;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MacShortRule extends Rule {
	public 	String regex = "[0-9A-F][0-9A-F][0-9A-F][0-9A-F][0-9A-F][0-9A-F][0-9A-F][0-9A-F][0-9A-F][0-9A-F][0-9A-F][0-9A-F]";

	public MacShortRule() {
    }
	@Override
	public boolean valid() throws Exception {
		if (StringUtil.isBlank(this.getValue())) {
			return true;
		}


		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(value.toUpperCase());


		if (!matcher.find()) {
			this.setMessage("请输入正确的mac地址");
			return false;
		}
		else {
			return true;
		}
	}

}
