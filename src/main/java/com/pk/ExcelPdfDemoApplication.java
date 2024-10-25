package com.pk;

import com.github.javafaker.Faker;
import com.pk.entity.CourseDetails;
import com.pk.service.ICourseService;
import java.time.LocalDateTime;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExcelPdfDemoApplication implements CommandLineRunner {

    @Autowired
    private ICourseService courseService;

    public static void main(String[] args) {
        SpringApplication.run(ExcelPdfDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        int numberOfRecords = 100;
        Faker faker = new Faker();
        for (int i = 0; i < numberOfRecords; i++) {
            CourseDetails course = new CourseDetails();
            course.setCourseName(faker.educator().course());
            course.setCourseCategory(faker.educator().campus());
            course.setFacultyName(faker.name().fullName());
            course.setCourseStatus(faker.bool().bool() ? "Active" : "Inactive");
            course.setTimestamp(LocalDateTime.now());
            course.setLocation(faker.address().city());
            course.setCourseFee(new Random().nextInt(10)); // Fees between 1.0 and 500.0
            course.setTrainingMode(faker.options().option("Online", "Offline", "Hybrid"));
            course.setAdminContact(faker.phoneNumber().phoneNumber());
            course.setCreatedDate(LocalDateTime.now());
            course.setCreatedBy(faker.name().username());
            course.setLastUpdatedBy(faker.name().username());

            // Save the entity to the database
            courseService.saveCourse(course);
        }
    }
}
