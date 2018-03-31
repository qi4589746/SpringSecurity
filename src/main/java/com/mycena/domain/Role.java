package com.mycena.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by jihung on 3/31/18.
 */
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    public String role;

    public Role(String role) {
        this.role = role;
    }

    public Role(long id, String role) {
        this.id = id;
        this.role = role;
    }
}
