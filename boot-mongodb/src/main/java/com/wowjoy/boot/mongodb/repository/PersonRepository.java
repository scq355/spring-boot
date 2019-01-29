package com.wowjoy.boot.mongodb.repository;

import com.wowjoy.boot.mongodb.domain.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author scq
 */
public interface PersonRepository extends MongoRepository<Person, String> {
}
