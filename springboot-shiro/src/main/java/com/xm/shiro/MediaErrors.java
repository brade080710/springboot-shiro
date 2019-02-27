package com.xm.shiro;

import java.util.Locale;

import org.springframework.context.support.ResourceBundleMessageSource;

import com.xm.shiro.utils.BaseError;


public class MediaErrors {
	private static ResourceBundleMessageSource ms;

	public static String getMessage(String code, Object...args) {
		if(ms==null) {
			ms = new ResourceBundleMessageSource();
			ms.setDefaultEncoding("UTF-8");
			ms.setBasenames("media-errors");
		}
		return ms.getMessage(code, args, Locale.SIMPLIFIED_CHINESE);
	}
	
    public static class MediaError extends BaseError {

		private static final long serialVersionUID = 1L;

		public MediaError(String msg){
			super(msg);
		}

    }

    public static MediaError UploadFileSizeTooLarge(Integer size) {
        return new MediaError(getMessage("UploadFileSizeTooLarge",size));
    }
    public static MediaError CreateDirFailed() {
        return new MediaError(getMessage("CreateDirFailed"));
    }
	public static MediaError InvalidPrefix() {
        return new MediaError(getMessage("InvalidPrefix"));
	}
}