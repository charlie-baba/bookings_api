package com.glofox.api.repositories;

import com.glofox.api.entity.StudioClass;
import com.glofox.api.utils.DateUtil;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Charles on 16/05/2021
 */
@Repository
public class ClassesRepository {

    private static final HashMap<Long, StudioClass> classes = new HashMap<>();
    private static long lastId = 0;

    public StudioClass findById(Long id){
        return classes.get(id);
    }

    public List<StudioClass> findAllClasses(long skip, long size){
        return classes.values().stream()
                .skip(skip)
                .limit(size)
                .collect(Collectors.toList());
    }

    public List<StudioClass> findAllActiveClasses(long skip, long size){
        return classes.values().stream()
                .filter(n -> DateUtil.isStillValid(n.getStartDate(), n.getEndDate()))
                .skip(skip)
                .limit(size)
                .collect(Collectors.toList());
    }

    public StudioClass findValidClassByName(String className) {
        return classes.values().stream()
                .filter(n -> n.getName().equals(className) && DateUtil.isStillValid(n.getStartDate(), n.getEndDate()))
                .findFirst().orElse(null);
    }

    public synchronized void save(StudioClass studioClass){
        studioClass.setId(++lastId);
        classes.put(lastId, studioClass);
    }

    public void update(Long id, StudioClass studioClass){
        classes.put(id, studioClass);
    }

    public void delete(Long id){
        classes.remove(id);
    }
}
