package com.pk.models;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SearchInputs {

    private String trainingMode;
    private String courseCategory;
    private String facultyName;
    private LocalDateTime startsOn;
}
