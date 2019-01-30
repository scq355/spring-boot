package com.wowjoy.boot.mongodb.service;

import com.wowjoy.boot.mongodb.domain.Student;
import com.wowjoy.boot.mongodb.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.domain.ExampleMatcher.StringMatcher.ENDING;

/**
 * @author scq
 */
@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public StudentService(StudentRepository studentRepository, MongoTemplate mongoTemplate) {
        this.studentRepository = studentRepository;
        this.mongoTemplate = mongoTemplate;
    }


    public void queryByExample() {
        Student student = new Student();
        student.setFirstname("Dave");

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("lastname")
                .withIncludeNullValues()
                .withStringMatcher(ENDING);

        Example<Student> example = Example.of(student, matcher);

        matcher = ExampleMatcher.matching()
                .withMatcher("firstname", ExampleMatcher.GenericPropertyMatcher::endsWith)
                .withMatcher("firstname", ExampleMatcher.GenericPropertyMatcher::startsWith);
    }

    public List<Student> findStudent(Student student) {
        return studentRepository.findAll(Example.of(student));
    }


}
