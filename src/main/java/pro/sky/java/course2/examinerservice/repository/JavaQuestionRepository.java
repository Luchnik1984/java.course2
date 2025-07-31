package pro.sky.java.course2.examinerservice.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("javaQuestionRepository")
public class JavaQuestionRepository extends AbstractQuestionRepository {
}
