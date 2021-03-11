package io.hari.demo.entity;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Hariom Yadav
 * @create 11-03-2021
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class MapQuestionAns {
    private Map<Long, Long> questionAnswer = new HashMap<>();
}
