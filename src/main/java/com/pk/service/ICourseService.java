package com.pk.service;

import com.pk.entity.CourseDetails;
import com.pk.models.SearchInputs;
import com.pk.models.SearchOutputs;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

public interface ICourseService {

    public Set<String> showAllCourseCategories();

    public Set<String> showAllTrainingModes();

    public Set<String> showAllFaculties();

    public List<SearchOutputs> showCoursesByFilters(SearchInputs searchInputs);

    public void generatePdfReport(SearchInputs searchInputs, HttpServletResponse httpServletResponse) throws Exception;

    public void generateExcelReport(SearchInputs searchInputs, HttpServletResponse httpServletResponse)
            throws Exception;

    public void generatePdfReport(HttpServletResponse httpServletResponse) throws Exception;

    public void generateExcelReport(HttpServletResponse httpServletResponse) throws Exception;

    void saveCourse(CourseDetails course);
}
