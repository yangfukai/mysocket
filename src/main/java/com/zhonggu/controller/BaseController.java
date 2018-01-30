package com.zhonggu.controller;

import org.apache.log4j.Logger;

/**
 * @author yangkai on 2018/1/24.
 */
public abstract class BaseController implements BaseInterface{

    public final Logger LOG = Logger.getLogger(getClassName());

    private String getClassName() {
        return this.getClass().getSimpleName();
    }
}
