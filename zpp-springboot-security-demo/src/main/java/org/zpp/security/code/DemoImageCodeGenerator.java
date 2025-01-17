package org.zpp.security.code;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.zpp.security.core.properties.SecurityProperties;
import org.zpp.security.core.validate.code.ValidateCodeGenerator;
import org.zpp.security.core.validate.code.image.ImageCode;

/**
 * 线条式验证码
 *
 * 覆盖默认的验证码生成器
 */
@Slf4j
@Component("imageValidateCodeGenerator")
public class DemoImageCodeGenerator implements ValidateCodeGenerator {

	@Autowired
	private SecurityProperties securityProperties;

	@Override
	public ImageCode generate(ServletWebRequest request) {
		int width = ServletRequestUtils.getIntParameter(request.getRequest(), "width",
				securityProperties.getCode().getImage().getWidth());
		int height = ServletRequestUtils.getIntParameter(request.getRequest(), "height",
				securityProperties.getCode().getImage().getHeight());

		//定义图形验证码的长、宽、验证码字符数、干扰元素个数
		LineCaptcha captcha = CaptchaUtil.createLineCaptcha(width,height,
				securityProperties.getCode().getImage().getLength(), 20);

		log.info("[覆盖默认的imageCode] - [{}]",captcha.getCode());
		ImageCode imageCode = new ImageCode(captcha.getImage(),captcha.getCode(),
				securityProperties.getCode().getImage().getExpireIn());
		return imageCode;
	}

}
