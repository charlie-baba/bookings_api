package com.glofox.api.services.impl;

import com.glofox.api.entity.StudioClass;
import com.glofox.api.enums.ResponseCode;
import com.glofox.api.pojo.request.ClassRequest;
import com.glofox.api.pojo.response.BaseResponse;
import com.glofox.api.repositories.ClassesRepository;
import com.glofox.api.services.ClassService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Charles on 16/05/2021
 */
@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    private ClassesRepository repository;

    @Override
    public List<StudioClass> getAllClasses(int page, int size) {
        long skip = page == 1 ? 0 : (long) page *size;
        List<StudioClass> classes = repository.findAllClasses(skip, size);
        return classes;
    }

    @Override
    public List<StudioClass> getAllActiveClasses(int page, int size) {
        long skip = page == 1 ? 0 : (long) page *size;
        List<StudioClass> classes = repository.findAllActiveClasses(skip, size);
        return classes;
    }

    @Override
    public BaseResponse saveClass(ClassRequest classRequest) {
        if (repository.findValidClassByName(classRequest.getName()) != null) {
            return new BaseResponse(ResponseCode.Bad_Request.getCode(),
                    "An active class with this name already exist.");
        }

        StudioClass studioClass = new StudioClass();
        BeanUtils.copyProperties(classRequest, studioClass);
        repository.save(studioClass);
        return new BaseResponse(ResponseCode.Success);
    }

    @Override
    public BaseResponse updateClass(Long id, ClassRequest classRequest) {
        StudioClass existingClass = repository.findById(id);
        if (existingClass == null) {
            return new BaseResponse(ResponseCode.Not_Found.getCode(),
                    "A class with this id does not exist.");
        }

        StudioClass classByName = repository.findValidClassByName(classRequest.getName());
        if (classByName != null && !existingClass.getId().equals(classByName.getId())){
            return new BaseResponse(ResponseCode.Bad_Request.getCode(),
                    "A class with this name already exist.");
        }

        BeanUtils.copyProperties(classRequest, existingClass);
        repository.update(id, existingClass);
        return new BaseResponse(ResponseCode.Success);
    }

    @Override
    public BaseResponse deleteClass(Long id) {
        if (repository.findById(id) == null) {
            return new BaseResponse(ResponseCode.Not_Found.getCode(),
                    "Class with this id does not exist.");
        }

        repository.delete(id);
        return new BaseResponse(ResponseCode.Success);
    }
}
