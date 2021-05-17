package com.glofox.api.controllers;

import com.glofox.api.entity.Booking;
import com.glofox.api.enums.ResponseCode;
import com.glofox.api.pojo.request.BookingRequest;
import com.glofox.api.pojo.response.BaseResponse;
import com.glofox.api.services.BookingService;
import com.glofox.api.utils.ErrorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Charles on 16/05/2021
 */
@RestController
@RequestMapping("/api/bookings")
public class BookingsController {

    @Autowired
    private BookingService bookingService;

    @Value("${page.size}")
    private int pageSize;

    @GetMapping("/getAll/{page}")
    public List<Booking> getAllBookings(@PathVariable("page") int page){
        return bookingService.getAllBookings(page, pageSize);
    }

    @PostMapping
    public ResponseEntity<BaseResponse> saveBooking(@Valid @RequestBody BookingRequest request,
                                                    Errors errors) {
        ResponseEntity<BaseResponse> respEntity = ErrorUtil.getBaseResponseResponseEntity(errors);
        if (respEntity != null) return respEntity;
        BaseResponse response;

        try {
            request.setClassName(StringUtils.capitalize(request.getClassName().trim()));
            response = bookingService.saveBooking(request);
            respEntity = new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response = new BaseResponse(ResponseCode.Internal_Server_Error.getCode(), e.getMessage());
            respEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return respEntity;
    }

}
