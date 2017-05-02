package com.like26th.likeproject.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by Administrator on 2017/3/14.
 */
@Entity
public class User {
    @Id
    private String id;
    @Generated
    private String name;

    @Generated(hash = 1037321026)
    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
