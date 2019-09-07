package com.nekosighed.common.utils;

import com.nekosighed.common.comonenum.BusinessErrorEnum;
import com.nekosighed.common.comonenum.NormalErrorEnum;
import com.nekosighed.common.comonenum.SimpleEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 自定义响应数据结构
 * 这个类是提供给门户，ios，安卓，微信商城用的
 * 门户接受此类数据后需要使用本类的方法转换成对于的数据类型格式（类，或者list）
 * 其他自行处理
 * 200：表示成功
 * 500：表示错误，错误信息在msg字段中
 * 501：bean验证错误，不管多少个错误都以map形式返回
 * 502：拦截器拦截到用户token出错
 * 555：异常抛出信息
 */

@Getter
@Setter
public class JsonResult extends ResponseEntity {

    /**
     * 响应业务状态
     */
    private Integer status;

    /**
     * 响应消息
     */
    private String msg;

    /**
     * 响应数据
     */
    private Object data;

    /**
     * 整合数据
     *
     * @param status  业务状态
     * @param msg 响应消息
     * @param data 响应数据
     * @return
     */
    private static Map<String, Object> fillData(int status, String msg, Object data) {
        return new HashMap<String, Object>(3) {
            {
                put("status", status);
                put("msg", msg);
                put("data", data);
            }
        };
    }

    /**
     * 成功响应
     *
     * @return JsonResult
     */
    public static JsonResult success() {
        return success("", "");
    }

    public static JsonResult success(String msg) {
        return success(msg, "");
    }

    public static JsonResult success(String msg, Object data) {
        return new JsonResult(fillData(SimpleEnum.RESPONSE_SUCCESS.getNum(), msg, data), HttpStatus.OK);
    }

    /**
     * 失败响应
     *
     * @return JsonResult
     */
    public static JsonResult error() {
        return error(NormalErrorEnum.UNKNOWN_ERROR.getMsg(), "");
    }

    public static JsonResult error(String msg) {
        return error(msg, "");
    }

    public static JsonResult error(String msg, Object data) {
        return new JsonResult(fillData(SimpleEnum.RESPONSE_ERROR.getNum(), msg, data), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 具体错误响应
     *
     * @param msg
     * @param data
     * @return
     */
    public static JsonResult detailResponse(String msg, Object data){
        return detailResponse(BusinessErrorEnum.INTERNAL_BUSINESS_ERROR, msg, data);
    }

    public static JsonResult detailResponse(BusinessErrorEnum errorEnum){
        return detailResponse(errorEnum, errorEnum.getMsg(), "");
    }

    public static JsonResult detailResponse(BusinessErrorEnum errorEnum, String msg, Object data){
        return new JsonResult(fillData(errorEnum.getNum(), msg, data), HttpStatus.OK);
    }


    /**
     * 继承的方法
     *
     * @param status
     */

    public JsonResult(HttpStatus status) {
        super(status);
    }

    public JsonResult(Object body, HttpStatus status) {
        super(body, status);
    }

    public JsonResult(MultiValueMap headers, HttpStatus status) {
        super(headers, status);
    }

    public JsonResult(Object body, MultiValueMap headers, HttpStatus status) {
        super(body, headers, status);
    }
}
