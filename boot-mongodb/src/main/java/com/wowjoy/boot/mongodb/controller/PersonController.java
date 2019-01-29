package com.wowjoy.boot.mongodb.controller;

import com.wowjoy.boot.mongodb.domain.Person;
import com.wowjoy.boot.mongodb.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author scq
 */
@RestController
public class PersonController {
    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping(value = "/list")
    public List<Person> list() {
        return personService.list();
    }

    @GetMapping(value = "/persons")
    public List<Person> persons() {
        return personService.persons();
    }

    @GetMapping(value = "/insert")
    public void insertPerson() {
        personService.insert();
    }

    @GetMapping(value = "/findAndModify")
    public void findAndModify() {
        personService.findAndUpdate();
    }

    @GetMapping(value = "/findAndReplace")
    public void findAndReplace() {
        personService.findAndReplace();
    }

    @GetMapping(value = "/remove")
    public void remove() {
        personService.remove();
    }

    @GetMapping(value = "/version")
    public void version() {
        personService.version();
    }

    @GetMapping(value = "/basicQuery")
    public void basicQuery() {
        personService.basicQuery();
    }
}
