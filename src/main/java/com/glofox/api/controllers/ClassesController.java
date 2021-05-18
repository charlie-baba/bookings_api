package com.glofox.api.controllers;

import com.glofox.api.entity.StudioClass;
import com.glofox.api.enums.ResponseCode;
import com.glofox.api.pojo.request.ClassRequest;
import com.glofox.api.pojo.response.BaseResponse;
import com.glofox.api.services.ClassService;
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
@RequestMapping("/api/classes")
public class ClassesController {

    @Autowired
    private ClassService classService;

    @Value("${page.size}")
    private int pageSize;

    @GetMapping("/getAll/{page}")
    public List<StudioClass> getAllClasses(@PathVariable("page") int page){
        return classService.getAllClasses(page, pageSize);
    }

    @GetMapping("/getActive/{page}")
    public List<StudioClass> getActiveClasses(@PathVariable("page") int page){
        return classService.getAllActiveClasses(page, pageSize);
    }

    @PostMapping
    public ResponseEntity<BaseResponse> saveClass(@Valid @RequestBody ClassRequest request, Errors errors) {
        ResponseEntity<BaseResponse> respEntity = ErrorUtil.getBaseResponseResponseEntity(errors);
        if (respEntity != null) return respEntity;
        BaseResponse response;

        try {
            request.setName(StringUtils.capitalize(request.getName().trim()));
            response = classService.saveClass(request);
            respEntity = new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response = new BaseResponse(ResponseCode.Internal_Server_Error.getCode(), e.getMessage());
            respEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return respEntity;
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> updateClass(@Valid @RequestBody ClassRequest request,
                                                   @PathVariable("id") Long id,
                                                    Errors errors) {
        ResponseEntity<BaseResponse> respEntity = ErrorUtil.getBaseResponseResponseEntity(errors);
        if (respEntity != null) return respEntity;

        try {
            request.setName(StringUtils.capitalize(request.getName().trim()));
            BaseResponse response = classService.updateClass(id, request);
            respEntity = new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            BaseResponse response = new BaseResponse(ResponseCode.Internal_Server_Error.getCode(), e.getMessage());
            respEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return respEntity;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> deleteClass(@PathVariable("id") Long id){
        ResponseEntity<BaseResponse> respEntity;
        try {
            BaseResponse response = classService.deleteClass(id);
            respEntity = new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            BaseResponse response = new BaseResponse(ResponseCode.Internal_Server_Error.getCode(), e.getMessage());
            respEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return respEntity;
    }
}
