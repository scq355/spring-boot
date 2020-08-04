package com.wowjoy.boot.cache.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Employee implements Serializable {
    private static final long serialVersionUID = -6106619304083809427L;
    private Integer id;
    private String lastName;
    private String email;
    private Integer gender;
    private Integer dId;
}
