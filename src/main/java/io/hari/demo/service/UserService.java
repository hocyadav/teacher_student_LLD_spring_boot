package io.hari.demo.service;

import io.hari.demo.dao.TestDao;
import io.hari.demo.entity.BaseEntity;
import io.hari.demo.entity.Test;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @Author Hariom Yadav
 * @create 11-03-2021
 */
@Service
@Slf4j
public class UserService {

    @Autowired
    TestDao testDao;

    public Integer calculateMarks(@NonNull final Long testId, @NonNull final Map<Long, Long> studentQueAnsMapping) {
        log.info("calculating final score for testId: {}, and for questions ans mapping: {} ", testId, studentQueAnsMapping);
        //todo imp atomic variable
        AtomicReference<Integer> finalScore = new AtomicReference<>(0);//for stream convert normal result int to atomic
        final Optional<Test> testOptional = testDao.findById(testId);
        testOptional.ifPresent(test -> {
            test.getQuestions().forEach(question -> {
                final Long fetchedQue = question.getId();
                final Long fetchedCorrectAns = question.getCorrectAnswerId();
                if (studentQueAnsMapping.containsKey(fetchedQue)) {
                    if (studentQueAnsMapping.get(fetchedQue).equals(fetchedCorrectAns)) {
                        finalScore.updateAndGet(v -> v + question.getScore());//atomic get old value and then update with score value
                    }
                }
            });
        });
        log.info("testId: {}, final score: {}", testId, finalScore);
        return finalScore.get();
    }

    public Map<Long, Long> removeUnwantedQuestionAnsMappingByStudent(@NonNull final Map<Long, Long> requestMap, @NonNull final Long testId) {
        log.info("removing unwanted from request map: {} and testId: {}", requestMap, testId);
        final Map<Long, Long> resultMapLongLongMap = new HashMap<>();
        final Optional<Test> optionalTest = testDao.findById(testId);
        final List<Long> questionIds = optionalTest.map(Test::getQuestions).orElseGet(() -> new HashSet<>())
                .stream()
                .map(BaseEntity::getId)
                .collect(Collectors.toList());

        requestMap.forEach((k, v) -> {
            if (questionIds.contains(k)) {
                final Long value = requestMap.get(k);
                resultMapLongLongMap.put(k, value);
            }
        });
        log.info("final clean request {}", resultMapLongLongMap);
        return resultMapLongLongMap;
    }
}
