package com.simon.repository;

import com.simon.domain.Course;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by simon on 2017/3/12.
 */
public interface CourseRepository extends MongoRepository<Course, String> {
}
