package com.pk.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "course_dtl")
public class CourseDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "course_id")
    private Integer id;

    @Column(name = "course_name", length = 40)
    private String courseName;

    @Column(name = "course_category")
    private String courseCategory;

    @Column(name = "faculty_name")
    private String facultyName;

    @Column(name = "is_active")
    private String courseStatus;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name = "location")
    private String location;

    @DecimalMin("0.1")
    @Positive @Column(name = "course_fees")
    private Float courseFee;

    @Column(name = "mode")
    private String trainingMode;

    @Column(name = "admin_contact", nullable = false)
    private String adminContact;

    @CreationTimestamp
    @Column(insertable = true, updatable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(updatable = true, insertable = false)
    private LocalDateTime updatedDate;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String lastUpdatedBy;
}
