package com.pk.resource;

import com.pk.models.SearchInputs;
import com.pk.models.SearchOutputs;
import com.pk.service.ICourseService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/reporting/api/")
public class CourseController {

    private ICourseService service;
    private HttpServletResponse response;

    public CourseController(ICourseService service) {
        this.service = service;
    }

    @Operation(description = "get all course categories")
    @GetMapping("/courses")
    public ResponseEntity<?> fetchCourseCategories() {
        try {
            Set<String> courses = service.showAllCourseCategories();
            return new ResponseEntity<>(courses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(description = "get all faculties")
    @GetMapping("/faculties")
    public ResponseEntity<?> fetchFaculties() {
        try {
            Set<String> faculties = service.showAllFaculties();
            return new ResponseEntity<>(faculties, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(description = "get all Training modes")
    @GetMapping("/modes")
    public ResponseEntity<?> fetchTrainingModes() {
        try {
            Set<String> modes = service.showAllTrainingModes();
            return new ResponseEntity<>(modes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(description = "fetch course by filter")
    @GetMapping("/filter")
    public ResponseEntity<?> fetchCoursesByFilters(@RequestBody SearchInputs inputs) {
        try {
            List<SearchOutputs> results = service.showCoursesByFilters(inputs);
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(description = "generate PDF report  course ")
    @GetMapping("/pdf")
    public ResponseEntity<?> generatePdf(HttpServletResponse response) {
        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=courses.pdf");
            service.generatePdfReport(response);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(description = "generate PDF report  course by filter")
    @GetMapping("/pdf/filter")
    public ResponseEntity<?> generatePdfWithFilter(
            @RequestBody SearchInputs searchInputs, HttpServletResponse response) {
        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=courses-filter.pdf");
            service.generatePdfReport(searchInputs, response);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(description = "generate Excel report  course ")
    @GetMapping("/excel")
    public ResponseEntity<?> generateExcel(HttpServletResponse response) {
        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=courses.xlsx");
            service.generateExcelReport(response);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(description = "generate PDF report  course by filter")
    @GetMapping("/excel/filter")
    public ResponseEntity<?> generateExcelWithFilter(
            @RequestBody SearchInputs searchInputs, HttpServletResponse response) {
        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=courses-filter.xlsx");
            service.generateExcelReport(searchInputs, response);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
