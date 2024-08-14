package com.pawan.rest.services.restful_web_services.versioning;

import com.pawan.rest.services.restful_web_services.model.Name;
import com.pawan.rest.services.restful_web_services.model.Person;
import com.pawan.rest.services.restful_web_services.model.PersonV2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersioningPersonController {

    @GetMapping(path = "/v1/person")
    public Person getFirstVersionPerson() {
        return new Person("Pawan Kumar");
    }

    @GetMapping(path = "/v2/person")
    public PersonV2 getSecondVersionPerson() {
        return new PersonV2(new Name("Pawan","Kumar"));
    }

    @GetMapping(path = "/person",params = "version=1")
    public PersonV2 getThirdVersionPerson() {
        return new PersonV2(new Name("Pawan","Kumar"));
    }

    @GetMapping(path = "/person",params = "version=2")
    public PersonV2 getFourthVersionPerson() {
        return new PersonV2(new Name("Pawan","Kumar"));
    }

    @GetMapping(path = "/person",headers = "version=1")
    public PersonV2 getHeaderVersionPerson() {
        return new PersonV2(new Name("KK","Kumar"));
    }
}
