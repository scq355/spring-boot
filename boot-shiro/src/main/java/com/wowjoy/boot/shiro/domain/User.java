package com.wowjoy.boot.shiro.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author scq
 * @date 2020-08-04 12:57:00
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class User {
    private Integer id;
    private String name;
    private String password;
    private String perms;
}
