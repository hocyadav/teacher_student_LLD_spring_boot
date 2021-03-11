package io.hari.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.hari.demo.constant.UserType;
import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Hariom Yadav
 * @create 11-03-2021
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@DiscriminatorValue(value = "type_teacher")
@Entity
public class AdminUser extends User{
    private Integer yearOfExp;

    @ManyToMany//we can assign same test to multiple teachers
    private List<Test> testList = new ArrayList<>();//for admin, all test, for student only registerd test id, this field is not in student


    @Builder
    public AdminUser(final String name, final UserType userType,
                     final Integer yearOfExp, final List<Test> testList) {
        super(name, userType);
        this.yearOfExp = yearOfExp;
        this.testList = testList;
    }

    @JsonProperty
    public List<Test> getAllTestList() {
        return testList;//above one is not fetch eager so here every time it will call JPA, we can see in log
    }
}
