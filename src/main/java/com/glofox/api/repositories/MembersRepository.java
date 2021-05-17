package com.glofox.api.repositories;

import com.glofox.api.entity.Member;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

/**
 * @author Charles on 16/05/2021
 */
@Repository
public class MembersRepository {

    private static HashMap<Long, Member> members = new HashMap<>();
    private static long lastId = 0;

    public Member findByEmail(String email) {
        return members.values().stream()
                .filter(n -> n.getEmail().equals(email))
                .findFirst().orElse(null);
    }

    public synchronized void save(Member member){
        member.setId(++lastId);
        members.put(lastId, member);
    }

    public void update(Long id, Member member){
        members.put(id, member);
    }

}
