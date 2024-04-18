package com.ikematsu.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ikematsu.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}
