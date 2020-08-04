package com.wowjoy.boot.mongodb.repository;

import com.wowjoy.boot.mongodb.domain.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author scq
 */
public interface StudentRepository extends MongoRepository<Student, String> {
}
