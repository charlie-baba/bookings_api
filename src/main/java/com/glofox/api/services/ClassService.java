package com.glofox.api.services;

import com.glofox.api.entity.StudioClass;
import com.glofox.api.pojo.request.ClassRequest;
import com.glofox.api.pojo.response.BaseResponse;

import java.util.List;

/**
 * @author Charles on 16/05/2021
 */
public interface ClassService {

    List<StudioClass> getAllClasses(int page, int size);

    List<StudioClass> getAllActiveClasses(int page, int size);

    BaseResponse saveClass(ClassRequest classRequest);

    BaseResponse updateClass(Long id, ClassRequest classRequest);

    BaseResponse deleteClass(Long id);
}
