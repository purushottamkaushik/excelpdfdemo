package com.pk.models;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchOutputs {

    private Integer id;

    private String courseName;

    private String courseCategory;

    private String facultyName;

    private String courseStatus;

    private LocalDateTime timestamp;

    private String location;

    private Integer courseFee;

    private String trainingMode;

    private String adminContact;
}
