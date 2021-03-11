package io.hari.demo.entity;

import io.hari.demo.entity.converter.QueAnsConverter;
import lombok.*;

import javax.persistence.Convert;
import javax.persistence.Entity;

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
public class UserResponse extends BaseEntity {
    private Long userId;//convert to integer for optimization
    private Long testId;

    //make a map of below pair
//    Long questionId;
//    Long submittedAnsId;

    //pair of auq and ans for single test
    @Convert(converter = QueAnsConverter.class)
    private MapQuestionAns mapQuestionAns;

}
