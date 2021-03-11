package io.hari.demo;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import io.hari.demo.config.AppConfig;
import io.hari.demo.constant.UserType;
import io.hari.demo.dao.*;
import io.hari.demo.entity.*;
import io.hari.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Autowired
	AppConfig config;

	@Autowired
	UserCommonDao userCommonDao;
	@Autowired
	TestDao testDao;
	@Autowired
	QueDao queDao;

	@Autowired
	AnsDao ansDao;

	@Autowired
	UserResponseDao userResponseDao;

	@Autowired
	AdminUserDao adminUserDao;

	@Autowired
	UserService userService;

	@Override
	public void run(String... args) throws Exception {
		//todo DONE: create question 1 and 4 different ans, and assign correct ans to question
		final Answer ans1 = Answer.builder().answer("ans1 correct").build();
		final Question question1 = Question.builder().question("que 1").score(10)
				.answerSet(Sets.newHashSet(//cascade ALL
						ans1,
						Answer.builder().answer("ans2 wrong").build(),
						Answer.builder().answer("ans3 wrong").build(),
						Answer.builder().answer("ans4 wrong").build()
						))
				.build();
		queDao.save(question1);
		final Long id = question1.getId();//check jpa has assign PK or not..
		System.out.println("id = " + id);

		final Long id1 = ans1.getId();//check jpa has assign id(PK) or not
		System.out.println("id1 = " + id1);

		//set correct answer id
		final Question question = queDao.findById(id).get();
		question.setCorrectAnswerId(id1);
		queDao.save(question);

		//todo: DONE fetch que obj and then iterate answer : solution fetc eager add so that we can get actual ans obj
		final List<Question> questions = queDao.findAll();
		questions.forEach(q -> {
			final Set<Answer> answerSet = q.getAnswerSet();
			System.err.println("answerSet = " + answerSet);
		});

		//todo: DONE create que 2 , copy and paste
		final Answer ans11 = Answer.builder().answer("ans12 correct").build();
		final Question q2 = Question.builder().question("que 2").score(20)
				.answerSet(Sets.newHashSet(//cascade ALL
						ans11,
						Answer.builder().answer("ans22 wrong").build(),
						Answer.builder().answer("ans32 wrong").build()
				))
				.build();
		queDao.save(q2);
		final Long id2 = q2.getId();//check jpa has assign PK or not..
		System.out.println("id2 = " + id2);

		final Long id12 = ans11.getId();//check jpa has assign id(PK) or not
		System.out.println("id12 = " + id12);

		//set correct answer id
		final Question question2 = queDao.findById(id2).get();
		question2.setCorrectAnswerId(id12);
		queDao.save(question2);

		//todo: DONE create test with q1 and q2
		final Test test1 = Test.builder().name("test1")
				.questions(Sets.newHashSet(question1, question2))
				.build();
		testDao.save(test1);

		//todo : DONE : create another test 2 with only one que
		final Test test2 =
				Test.builder().name("test 2").questions(Sets.newHashSet(question1)).build();//throw error we are assigning same que to multiple test ,
																					// so mark List<Q> as mant to many
		testDao.save(test2);

		//todo :DONE fetch test and iterate over questions
		final List<Test> tests = testDao.findAll();//add fetch type as eager
		tests.forEach(t -> {
			final Set<Question> tQuestions = t.getQuestions();
			System.err.println("tQuestions = " + tQuestions);
		});


		//TODO: done :  create a new teacher and add test to teacher
		final AdminUser jitendra_sir = AdminUser.builder().name("jitendra sir").userType(UserType.teacher).yearOfExp(15)
				.testList(Arrays.asList(test1, test2)).build();
		userCommonDao.save(jitendra_sir);
//		adminUserDao.save(jitendra_sir);//this will also work

		//TODO DONE : create another teacher with same test id
		final AdminUser hariom_sir = AdminUser.builder().name("hariom sir").userType(UserType.teacher).yearOfExp(2)
						.testList(Arrays.asList(test1)).build();//same test as jitendra sir - error make it many to many
		userCommonDao.save(hariom_sir);

		//todo DONE: create a student and start with test 1
		final Student hariom = Student.builder().userType(UserType.student).name("hariom")
				.registerTest(Arrays.asList(test1)).build();
		userCommonDao.save(hariom);

		//todo DONE : create student 2 and assign same test as above test1 : so change 1:M -> M:M
		final Student chandan_yadav =
				Student.builder().name("chandan yadav").userType(UserType.student).registerTest(Arrays.asList(test1)).build();
		userCommonDao.save(chandan_yadav);

		// TODO: DONE : create student1 (hariom) response for a test1
		//todo: create a class obj that store quq + ans as pair -> convert using attribute convertor and save in db

		final ImmutableMap<Long, Long> questionAnswer =
				ImmutableMap.of(1L, ans1.getId(), question2.getId(), ans11.getId());//google map immutable map of
		final UserResponse userResponse = UserResponse.builder()
				.userId(hariom.getId())
				.testId(test1.getId())
				.mapQuestionAns(MapQuestionAns.builder().questionAnswer(questionAnswer).build())
				.build();
		System.err.println("userResponse = " + userResponse);
		userResponseDao.save(userResponse);

		//todo DONE : fetch user response and iterate
		final List<UserResponse> userResponses = userResponseDao.findAll();
		userResponses.forEach(r -> {
			final MapQuestionAns mapQuestionAns = r.getMapQuestionAns();
			System.out.println("mapQuestionAns = " + mapQuestionAns);
			final Map<Long, Long> questionAnswer1 = mapQuestionAns.getQuestionAnswer();
			System.out.println("questionAnswer1 = " + questionAnswer1);
			final Long que1Ans = questionAnswer1.get(1L);
			final Long que2Ans = questionAnswer1.get(2L);
			System.out.println("que1Ans = " + que1Ans);
			System.out.println("que2Ans = " + que2Ans);
		});

		//todo DONE: create new dao method and test bi directional
		final List<Test> allByQuestions_id = testDao.findAllByQuestions_Id(1L);
		System.out.println("allByQuestions_id = " + allByQuestions_id);
		final List<Test> allByQuestions_id1 = testDao.findAllByQuestions_Id(2L);
		System.out.println("allByQuestions_id1 = " + allByQuestions_id1);

		//todo :DONE :  create a method to calculate the total marks
		final Integer finalScores = userService.calculateMarks(1L, questionAnswer);
		System.out.println("finalScores = " + finalScores);

		//todo : done :  remove unwanted que ans mapping for a given test
		final ImmutableMap<Long, Long> oldRequest = ImmutableMap.of(1L, 1L, 2L, 2L, 3L, 2L);
		System.out.println("oldRequest = " + oldRequest);
		final Map<Long, Long> finalCleanRequest = userService.removeUnwantedQuestionAnsMappingByStudent(oldRequest, 2L);
		System.out.println("finalCleanRequest = " + finalCleanRequest);

	}
}
