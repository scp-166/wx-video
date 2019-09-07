package com.nekosighed.api.notifier;


import com.nekosighed.common.utils.JsonResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 捕获全局异常的通知类
 */
@ControllerAdvice
public class ExceptionNotifier {

    @ExceptionHandler(Exception.class)
    public JsonResult exceptionCatcher(Exception e){
        e.printStackTrace();
        return JsonResult.error();
    }


}
