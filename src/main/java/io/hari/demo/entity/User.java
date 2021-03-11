package io.hari.demo.entity;

import io.hari.demo.constant.UserType;
import lombok.*;

import javax.persistence.*;

/**
 * @Author Hariom Yadav
 * @create 11-03-2021
 */
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {}, callSuper = true)
@AllArgsConstructor
//@Builder//1. remove builder since we are adding inside child class
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)//default is single table, so if we remove also it will work
//@Inheritance(strategy = InheritanceType.JOINED)//default is single table, create other table : working
@DiscriminatorColumn(name = "type_column")
public class User extends BaseEntity{
    private String name;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    //other common metadata
}
