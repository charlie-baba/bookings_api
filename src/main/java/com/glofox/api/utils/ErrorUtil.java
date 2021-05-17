package com.glofox.api.utils;

import com.glofox.api.enums.ResponseCode;
import com.glofox.api.pojo.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.Errors;

/**
 * @author Charles on 16/05/2021
 */
public class ErrorUtil {

    public static String getResponseMessage(Errors errors) {
        if (errors == null || CollectionUtils.isEmpty(errors.getAllErrors()))
            return null;

        StringBuilder sb = new StringBuilder();
        errors.getAllErrors().forEach((error) -> sb.append(error.getDefaultMessage()).append(", "));
        return sb.toString();
    }

    public static ResponseEntity<BaseResponse> getBaseResponseResponseEntity(Errors errors) {
        BaseResponse response;
        if(errors.hasErrors()) {
            response = new BaseResponse(ResponseCode.Bad_Request);
            response.setResponseMessage(getResponseMessage(errors));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}
