package com.cwa.crudspringboot.repository;

import com.cwa.crudspringboot.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
