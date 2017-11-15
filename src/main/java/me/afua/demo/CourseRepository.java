package me.afua.demo;

import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course,Long>{
    Iterable <Course> findAllByTitleContainingIgnoreCase(String search);
}
