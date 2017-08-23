package com.awifi.np.biz.common.exception;

/**
 * @Description: Portal接口异常
 * @Title: PortalException.java
 * @Package com.awifi.toe.base.exception
 * @author 许小满
 * @date 2016年1月27日 上午11:42:38
 * @version V1.0
 */
public class PortalException extends Exception {

    /** 序列号 */
    private static final long serialVersionUID = 8662044174443879224L;

    public PortalException() {
        super();
    }

    public PortalException(Throwable cause) {
        super(cause);
    }

    public PortalException(String message) {
        super(message);
    }

    public PortalException(String message, Throwable e) {
        super(message, e);
    }

}
