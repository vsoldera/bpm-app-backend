package com.app.springrolejwt.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Post implements Serializable {

    private String uuid;
    private String monitoredName;
}
