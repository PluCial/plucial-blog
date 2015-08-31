package com.appspot.plucial.controller;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public abstract class BaseController extends Controller {

    @Override
    protected Navigation run() throws Exception {

        setState();
        setThisURI();

        return execute();
    }

    /**
     * リクエストのなりすまし防止用の状態トークンを作成します。
     * 後の検証に備えてセッションで保存します。
     */
    public void setState() {
        String state = new BigInteger(130, new SecureRandom()).toString(32);
        sessionScope("state", state);
        requestScope("state", state);
    }

    /**
     * デバイスがスマートフォンであるか判定
     * @param request
     * @return
     */
    public boolean isSmartPhone() {

        String userAgent = request.getHeader("User-Agent").toLowerCase();

        if(userAgent != null && (userAgent.indexOf("iphone") > 0 || userAgent.indexOf("android") > 0)) {
            return true;
        }

        return false;
    }

    /**
     * 自分自身URLを設定
     */
    public void setThisURI() {
        String requestURL = request.getRequestURL().toString();
        String requestURI = request.getRequestURI();
        String baseURL = requestURL.replace(requestURI, "") + "/";

        requestScope("thisPageUrl", baseURL);
    }

    /**
     * リクエスト処理
     * @return
     * @throws Exception
     */
    protected abstract Navigation execute() throws Exception;

}
