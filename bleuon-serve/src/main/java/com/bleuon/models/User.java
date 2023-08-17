package com.bleuon.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Integer id;
    private String username;
    private String password;
    private String phone;
    private String email;
    private String profession;
    private String company;
    private String position;
    private String avatar;
    private String signature;
    private String sex;
    private Timestamp registerDate;

}
