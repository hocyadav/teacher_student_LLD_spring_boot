package io.hari.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
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
@Table(name = "tests")
public class Test extends BaseEntity{
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Question> questions = new HashSet<>();

    //other metadata

    @JsonProperty
    public Integer getMaxTestScore() {
        return questions.stream().map(Question::getScore).reduce(0, (a , b) -> a + b);
    }

    @JsonProperty
    public Set<Question> getAllQuestions() {
        return  questions;
    }

}
