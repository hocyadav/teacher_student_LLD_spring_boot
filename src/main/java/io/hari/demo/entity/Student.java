package io.hari.demo.entity;

import io.hari.demo.constant.UserType;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Hariom Yadav
 * @create 11-03-2021
 */
@Getter
@Setter
@ToString
@NoArgsConstructor//4.
@Entity
@DiscriminatorValue(value = "type_student")
public class Student extends User{

    @ManyToMany//same test can be assign to different students
    private List<Test> registerTest = new ArrayList<>();

    @Builder//3.
    public Student(final String name, final UserType userType, final List<Test> registerTest) {//2. create constructor will child + parent field
        super(name, userType);
        this.registerTest = registerTest;
    }
}
