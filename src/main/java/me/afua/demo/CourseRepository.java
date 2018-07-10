package me.afua.demo;

import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course,Long>{
    Iterable <Course> findAllByTitleContainingIgnoreCase(String search);
    Iterable <Course> findAllByCreditsBetween(int min, int max);

}
