package com.awifi.np.biz.timebuysrv.web.log;

import com.awifi.np.biz.common.base.model.Page;

public class ResultDTO {
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private Object data;
    private String msg;
    private Page page;

    public Object getOther() {
        return other;
    }

    public void setOther(Object other) {
        this.other = other;
    }

    private Object other;

    public ResultDTO(String code, Object data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public ResultDTO(String code, Object data, String msg, Page page) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.page = page;
    }

    public ResultDTO() {
        // TODO Auto-generated constructor stub
    }

    public ResultDTO(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public void clone(ResultDTO result) {
        this.code = result.code;
        this.data = result.data;
        this.msg = result.msg;
        this.page = result.page;
    }

    /**
     * 说明:判断是否正确
     * 
     * @return
     * @return boolean
     * @author dozen.zhang
     * @date 2015年12月14日上午11:44:59
     */
    public boolean isRight() {
        return this.code.equals("0");
    }
}
