package com.givehopeweb.repositories;

import com.givehopeweb.models.UserRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by David on 2/21/17.
 */

@Repository
public interface Roles extends CrudRepository <UserRole, Integer> {

    @Query("select ur.role from UserRole ur, User u where u.username=?1 and ur.userId = u.id")
    List<String> ofUserWith (String username);
}