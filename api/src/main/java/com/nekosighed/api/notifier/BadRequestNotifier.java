package com.nekosighed.api.notifier;

import com.nekosighed.common.comonenum.NormalErrorEnum;
import com.nekosighed.common.utils.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

@ControllerAdvice
public class BadRequestNotifier {
    private static final Logger logger = LoggerFactory.getLogger(BadRequestNotifier.class);

    /**
     * RequestParam 注释了 表单类，验证失败会抛出该异常
     * @param exception
     * @return
     */
    @ExceptionHandler(BindException.class)
    public JsonResult bindException(BindException exception){
        logger.warn("表单验证失败");
        return getJsonResult(exception.getBindingResult());
    }

    /**
     * RequestParam 注释的 单参数验证失败会进入该异常
     *  query param
     * @param exception
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public JsonResult constraintViolationException(ConstraintViolationException exception){
        logger.warn("单表单参数验证失败");
        return JsonResult.error("参数违反约束： " + exception.getLocalizedMessage());
    }


    /**
     * RequestBody 验证失败
     * 注意 controller 的参数需要配置 @Valid + @RequestBody
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public JsonResult jsonValidationException(MethodArgumentNotValidException exception) {
        logger.warn("requestBody 验证失败");
        return getJsonResult(exception.getBindingResult());
    }

    /**
     * 访问类型错误
     * 例如 controller 显式设置 consumer为 MediaType.APPLICATION_JSON_UTF8_VALUE
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public JsonResult httpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException exception) {
        logger.info("接口要求的方法是: {}", exception.getSupportedMediaTypes().toString());
        return JsonResult.error(NormalErrorEnum.HTTP_MEDIA_TYPE_NOT_SUPPORT.getMsg());
    }

    /**
     * RequestParam 未设置 required=false,缺失参数会抛出该异常
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public JsonResult missingRequestParameterException(MissingServletRequestParameterException exception) {
        logger.info(exception.getLocalizedMessage());
        logger.info(exception.getMessage());
        logger.info(exception.getParameterName());
        logger.info(exception.getParameterType());
        return JsonResult.error("缺少参数: " + exception.getParameterName() + ", 要求类型为: " + exception.getParameterType());
    }

    /**
     * 出现解析错误时候操作
     * 比如 RequestBody 且没有设置  require=false 参数解析时出现错误
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public JsonResult httpMessageNotReadableException(HttpMessageNotReadableException exception) {
        if (Optional.ofNullable(exception.getMessage()).isPresent()) {

            return exception.getMessage().startsWith("Required request body is missing") ?
                    JsonResult.error(NormalErrorEnum.JSON_PARSING_ERROR.getMsg()) :
                    JsonResult.error(NormalErrorEnum.OTHER_PARSING_ERROR.getMsg());
        }
        return JsonResult.error();
    }


    /**
     * 获取校验的错误内容
     *
     * @param result
     * @return
     */
    private JsonResult getJsonResult(BindingResult result) {
        if (result.hasErrors()) {
            StringBuffer errorBuffer = new StringBuffer();
            List<ObjectError> errors = result.getAllErrors();
            errors.forEach((item) -> {
                String[] strings = item.getCodes();  // 参数类的各种具体成员表示方法
                if (Optional.ofNullable(strings).isPresent()) {
                    // item.getCode(): 校验失败的成员名
                    if (Optional.ofNullable(item.getDefaultMessage()).isPresent() && Optional.ofNullable(item.getCode()).isPresent()) {
                        errorBuffer.append(strings[1].substring(item.getCode().length() + 1))  // strings[1] 默认打印是 注解名: 出错属性
                                .append(": ")
                                .append(item.getDefaultMessage())
                                .append(" | ");
                    }
                }
            });
            return JsonResult.error(errorBuffer.toString());
        } else {
            return JsonResult.error(NormalErrorEnum.UNKNOWN_ERROR.getMsg());
        }
    }
}
