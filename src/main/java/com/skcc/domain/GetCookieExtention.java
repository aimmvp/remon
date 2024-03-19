package com.skcc.domain;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.SilentCssErrorHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.html.parser.HTMLParserListener;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptEngine;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@Slf4j
public class GetCookieExtention  extends WebClient{
//    static String loginUrl = "https://partnersso.sktelecom.com/swing/skt/login.html";

    public String getSessionVal(String loginUrl) throws Exception{
        String rtnVal = "";
        setCssErrorHandler(new SilentCssErrorHandler());
        log.error("@@@@@[{}]", loginUrl);
        try ( final WebClient webClient = new WebClient()) {
            webClient.getOptions().setThrowExceptionOnScriptError(false);
//
//
//            webClient.setHTMLParserListener(HTMLParserListener.LOG_REPORTER);
//            webClient.setJavaScriptEngine(new JavaScriptEngine(webClient));
//            webClient.getOptions().setJavaScriptEnabled(true);
//            webClient.getCookieManager().setCookiesEnabled(true);
//            webClient.getOptions().setThrowExceptionOnScriptError(true);
//            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
//            webClient.setAjaxController(new NicelyResynchronizingAjaxController());
//            webClient.getCache().setMaxSize(0);
//            webClient.getOptions().setRedirectEnabled(true);
            final HtmlPage page = webClient.getPage(loginUrl);
            List<FrameWindow> frames = page.getFrames();
            HtmlPage formpage = null;
            for (FrameWindow frame : frames) {
//                log.error(frame.getFrameElement().getId());
                if ("loginFormFrame".equals(frame.getFrameElement().getId())) {
                    formpage = (HtmlPage) frame.getEnclosedPage();
                }
            }

            HtmlForm form = formpage.getFormByName("sso");
            HtmlTextInput idInput = form.getInputByName("USER");
            HtmlPasswordInput pwInput = (HtmlPasswordInput) form.getInputByName("PASSWORD");

            idInput.type("UX533");
            pwInput.type("q1w2e3r4((");
            HtmlAnchor submitButton =  form.getFirstByXPath("//a[@class='submit']");
            submitButton.click();
            rtnVal = webClient.getCookieManager().getCookie("SSOSESSION").getValue();
            log.error("[{}]", rtnVal);

        } catch (FailingHttpStatusCodeException | IOException e) {
            return "ERROR";
        }
        return rtnVal;
    }
}
