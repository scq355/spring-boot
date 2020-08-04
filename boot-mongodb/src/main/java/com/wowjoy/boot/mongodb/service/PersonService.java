package com.wowjoy.boot.mongodb.service;

import com.alibaba.fastjson.JSON;
import com.wowjoy.boot.mongodb.domain.Person;
import com.wowjoy.boot.mongodb.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.FindAndReplaceOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

/**
 * @author scq
 */
@Slf4j
@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final MongoOperations mongoOperations;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public PersonService(PersonRepository personRepository,
                         MongoOperations mongoOperations,
                         MongoTemplate mongoTemplate) {
        this.personRepository = personRepository;
        this.mongoOperations = mongoOperations;
        this.mongoTemplate = mongoTemplate;
    }

    public List<Person> list() {
        return personRepository.findAll();
    }

    public List<Person> persons () {
        return mongoOperations.findAll(Person.class);
    }

    public void insert() {
        Person person = new Person("Joe", 34);
        mongoOperations.insert(person);

        mongoOperations.updateFirst(query(where("name").is("Joe")), update("age", 35), Person.class);
        Person one = mongoOperations.findOne(query(where("name").is("Joe")), Person.class);
        assert one != null;
        System.out.println(one.toString());

        mongoOperations.remove(one);
        List<Person> personList = mongoOperations.findAll(Person.class);
        System.out.println(Collections.singletonList(personList).toString());

        mongoOperations.dropCollection(Person.class);
    }

    public void findAndUpdate() {
        mongoTemplate.insert(new Person("Tom", 21));
        mongoTemplate.insert(new Person("Dick", 22));
        mongoTemplate.insert(new Person("Harry", 23));

        Query query = new Query(Criteria.where("name").is("Harry"));
        Update update = new Update().inc("age", 1);
        Person person = mongoTemplate.findAndModify(query, update, Person.class);

        Person one = mongoTemplate.findOne(query, Person.class);

        Person modify = mongoTemplate.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), Person.class);

        assert person != null;
        System.out.println(person.toString());
        assert one != null;
        System.out.println(one.toString());
        assert modify != null;
        System.out.println(modify.toString());

        query = new Query(Criteria.where("name").is("Harry"));
        person = mongoTemplate.findAndModify(query, update, new FindAndModifyOptions().returnNew(true).upsert(true), Person.class);
        assert person != null;
        System.out.println(person.toString());
    }

    public void findAndReplace() {
        Optional<Person> result = mongoTemplate.update(Person.class)
                .matching(query(where("name").is("scq")))
                .replaceWith(new Person("How", 12))
                .withOptions(FindAndReplaceOptions.options().upsert())
                .as(Person.class)
                .findAndReplace();
        System.out.println(JSON.toJSONString(result));
    }

    public void remove() {
        Person person = new Person("Tom", 21);
        mongoTemplate.remove(person, "pers");

        mongoTemplate.remove(query(where("name").is("scq")), "pers");
        mongoTemplate.remove(new Query().limit(2), "pers");

        mongoTemplate.findAllAndRemove(query(where("name").is("scq")), "pers");
        mongoTemplate.findAllAndRemove(new Query().limit(3), "pers");
    }

    /**
     * 乐观锁定需要设置WriteConcern为ACKNOWLEDGED。否则OptimisticLockingFailureException可以默默吞噬。
     */
    public void version() {
        // 最初插入文档version设置为0
        Person daenerys = mongoTemplate.insert(new Person("Daenerys", 24));
        // 加载刚刚插入的文档version还在0
        Person temp = mongoTemplate.findOne(query(where("id").is(daenerys.getId())), Person.class);

        daenerys.setAge(33);
        // 使用更新文档version = 0设置age和碰撞version到1
        mongoTemplate.save(daenerys);
        // 尝试更新以前加载的文档version=0 操作失败OptimisticLockingFailureException 因为当前version是1
        assert temp != null;
        mongoTemplate.save(temp);
    }

    /**
     * 使用 Query和Criteria表达查询，Query和Criteria类遵循流畅API样式，同时具有易于理解的代码，
     * 可以链在一起的多个方法标准和查询。为了提高可读性，静态导入可以避免使用'new'关键字来创建Query和Criteria实例。
     * 还可以使用从普通JSON字符串BasicQuery创建Query实例
     */
    public void basicQuery() {
        BasicQuery query = new BasicQuery("{ age : { $lt : 20 }}");
        List<Person> personList = mongoTemplate.find(query, Person.class);
        System.out.println(JSON.toJSONString(personList));
    }




}
