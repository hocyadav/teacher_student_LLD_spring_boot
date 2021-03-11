package io.hari.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author Hariom Yadav
 * @create 11-03-2021
 */
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {}, callSuper = true)
@AllArgsConstructor
@Builder
@Entity
public class Question extends BaseEntity{
    private String question;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Answer> answerSet = new HashSet<>();

    private Long correctAnswerId;
    private Integer score;

    //not required since we created new table user response
//    private Long submittedAnswerId;//this is for student only , we can seperate above class as parent class

    //other metadata

    @JsonProperty
    public Set<Answer> getAllAnsSets() {
        return answerSet;
    }

    @JsonProperty
    public Long getQueId() {
        return super.getId();
    }
}
