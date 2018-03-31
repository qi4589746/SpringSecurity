package com.mycena.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by jihung on 3/31/18.
 */
@Entity
public class UserRole {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    public long userId;

    public long roleId;


    public UserRole(long id, long userId, long roleId) {
        this.id = id;
        this.userId = userId;
        this.roleId = roleId;
    }

    public UserRole(long userId, long roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }
}
