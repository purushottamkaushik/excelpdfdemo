package com.pk.repo;

import com.pk.entity.CourseDetails;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CourseRepository extends JpaRepository<CourseDetails, Integer> {

    @Query(value = "SELECT distinct(courseCategory) from CourseDetails ")
    Set<String> getUniqueCourseCategories();

    @Query(value = "SELECT distinct(trainingMode) from CourseDetails ")
    public Set<String> getUniqueTrainingModes();

    @Query(value = "SELECT distinct(facultyName) from CourseDetails ")
    public Set<String> getUniqueFacultyNames();
}
