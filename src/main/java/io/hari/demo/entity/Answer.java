package io.hari.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

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
public class Answer extends BaseEntity{
    private String answer;

    //other metadata

    @JsonProperty
    public Long getAnsId() {
        return super.getId();
    }
}

