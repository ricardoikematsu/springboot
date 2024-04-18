package com.ikematsu.services;

import com.ikematsu.data.dto.v1.PersonDTO;
import com.ikematsu.data.dto.v2.PersonDTOV2;
import com.ikematsu.exceptions.ResourceNotFoundException;
import com.ikematsu.mapper.DozerMapper;
import com.ikematsu.mapper.custom.PersonMapper;
import com.ikematsu.model.Person;
import com.ikematsu.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonServices {

    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository repository;

    @Autowired
    PersonMapper mapper;

    public List<PersonDTO> findAll() {

        logger.info("Finding all people!");

        return DozerMapper.parseListObjects(repository.findAll(), PersonDTO.class);
    }

    public PersonDTO findById(Long id) {

        logger.info("Finding one person!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        return DozerMapper.parseObject(entity, PersonDTO.class);
    }

    public PersonDTO create(PersonDTO person) {

        logger.info("Creating one person!");
        var entity = DozerMapper.parseObject(person, Person.class);
        var dto =  DozerMapper.parseObject(repository.save(entity), PersonDTO.class);
        return dto;
    }

    public PersonDTOV2 createV2(PersonDTOV2 person) {

        logger.info("Creating one person with V2!");
        var entity = mapper.convertVoTOEntity(person);
        var dto =  mapper.convertEntityToDto(repository.save(entity));
        return dto;
    }

    public PersonDTO update(PersonDTO person) {

        logger.info("Updating one person!");

        var entity = repository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var vo =  DozerMapper.parseObject(repository.save(entity), PersonDTO.class);
        return vo;
    }

    public void delete(Long id) {

        logger.info("Deleting one person!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        repository.delete(entity);
    }
}