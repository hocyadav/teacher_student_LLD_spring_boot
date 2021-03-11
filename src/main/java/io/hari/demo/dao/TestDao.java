package io.hari.demo.dao;

import io.hari.demo.entity.Test;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Hariom Yadav
 * @create 11-03-2021
 */
@Repository
public interface TestDao extends BaseDao<Test>{

    List<Test> findAllByQuestions_Id(Long questionId);//for bidirectinal
}
