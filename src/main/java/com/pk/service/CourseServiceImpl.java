package com.pk.service;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.pk.entity.CourseDetails;
import com.pk.models.SearchInputs;
import com.pk.models.SearchOutputs;
import com.pk.repo.CourseRepository;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.naming.directory.SearchResult;

@Service
@Slf4j
@Transactional
public class CourseServiceImpl implements ICourseService {

    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Set<String> showAllCourseCategories() {
        return courseRepository.getUniqueCourseCategories();
    }

    @Override
    public Set<String> showAllTrainingModes() {
        return courseRepository.getUniqueTrainingModes();
    }

    @Override
    public Set<String> showAllFaculties() {
        return courseRepository.getUniqueFacultyNames();
    }

    @Override
    public List<SearchOutputs> showCoursesByFilters(SearchInputs searchInputs) {

        CourseDetails courseDetails = new CourseDetails();
        if (StringUtils.hasText(searchInputs.getCourseCategory())) {
            courseDetails.setCourseCategory(searchInputs.getCourseCategory());
        }
        if (StringUtils.hasText(searchInputs.getFacultyName())) {
            courseDetails.setFacultyName(searchInputs.getFacultyName());
        }
        if (StringUtils.hasText(searchInputs.getTrainingMode())) {
            courseDetails.setTrainingMode(searchInputs.getTrainingMode());
        }

        if (Objects.nonNull(searchInputs.getStartsOn())) {
            searchInputs.setStartsOn(searchInputs.getStartsOn());
        }

        Example<CourseDetails> searchInputsExample = Example.of(courseDetails);
        List<CourseDetails> courseDetailsList = courseRepository.findAll(searchInputsExample);

        List<SearchOutputs> results = new ArrayList<>();
        for (CourseDetails courseDetail : courseDetailsList) {
            SearchOutputs searchOutputs = new SearchOutputs();
            BeanUtils.copyProperties(courseDetail, searchOutputs);
            results.add(searchOutputs);
        }

        return results;
    }

    @Override
    public void generatePdfReport(SearchInputs searchInputs, HttpServletResponse httpServletResponse) {
        List<SearchOutputs> searchOutputs = showCoursesByFilters(searchInputs);
    }

    @Override
    public void generateExcelReport(SearchInputs searchInputs, HttpServletResponse httpServletResponse)
            throws Exception {
        List<SearchOutputs> searchOutputs = showCoursesByFilters(searchInputs);
        generateExcelReportFromData(searchOutputs,httpServletResponse);

    }

    private void generateExcelReportFromData(List<SearchOutputs> searchOutputs, HttpServletResponse httpServletResponse) throws IOException {
        if (Objects.nonNull(searchOutputs) && !searchOutputs.isEmpty()) {

            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet();

            HSSFRow header = sheet.createRow(0);
            header.getCell(0).setCellValue("id");
            header.getCell(1).setCellValue("name");
            header.getCell(2).setCellValue("category");
            header.getCell(3).setCellValue("faculty");
            header.getCell(4).setCellValue("status");
            header.getCell(5).setCellValue("location");
            header.getCell(6).setCellValue("fee");
            header.getCell(7).setCellValue("Admin contact");

            int i = 1;
            for (SearchOutputs searchOutput : searchOutputs) {
                HSSFRow currentRow = sheet.createRow(i);
                currentRow.getCell(0).setCellValue(searchOutput.getId());
                currentRow.getCell(1).setCellValue(searchOutput.getCourseName());
                currentRow.getCell(2).setCellValue(searchOutput.getCourseCategory());
                currentRow.getCell(3).setCellValue(searchOutput.getFacultyName());
                currentRow.getCell(4).setCellValue(searchOutput.getCourseStatus());
                currentRow.getCell(5).setCellValue(searchOutput.getLocation());
                currentRow.getCell(6).setCellValue(searchOutput.getCourseFee());
                currentRow.getCell(7).setCellValue(searchOutput.getAdminContact());
                i++;
            }

            // get response output stream
            ServletOutputStream outputStream = httpServletResponse.getOutputStream();
            workbook.write(outputStream);
            outputStream.close();
            workbook.close();
        }

    }

    @Override
    public void generatePdfReport(HttpServletResponse httpServletResponse) throws Exception {
        List<SearchOutputs> searchOutputs = new ArrayList<>();
        generatePdfReportFromData(searchOutputs,httpServletResponse);

    }

    private void generatePdfReportFromData(List<SearchOutputs> searchOutputs,
                                           HttpServletResponse httpServletResponse) throws IOException {


        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document,httpServletResponse.getOutputStream());
        document.open();

        // FOnt for Paragrapgh
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setSize(30);
        font.setColor(Color.BLUE);

        // Paragraph
        Paragraph paragraph = new Paragraph("Search Results", font);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(paragraph);


        // FOnt for Table
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(70.0f);
        table.setSpacingBefore(2.0f);
        table.setSpacingAfter(2.0f);

        PdfPCell cell = new PdfPCell();
         cell.setBackgroundColor(Color.BLUE);
         cell.setPadding(5f);

       Font cellFont = FontFactory.getFont(FontFactory.TIMES_ROMAN);
       cell.setBackgroundColor(Color.GREEN);


       cell.setPhrase(new Phrase("ID", cellFont));
       table.addCell(cell);
       cell.setPhrase(new Phrase("courseName", cellFont));
       table.addCell(cell);
       cell.setPhrase(new Phrase("faculty", cellFont));
       table.addCell(cell);


        for (SearchOutputs output : searchOutputs) {
            table.addCell(String.valueOf(output.getId()));
            table.addCell(String.valueOf(output.getCourseName()));
            table.addCell(String.valueOf(output.getFacultyName()));
        }

        document.add(table);
        document.close();
    }

    @Override
    public void generateExcelReport(HttpServletResponse httpServletResponse) throws Exception {

        List<SearchOutputs> searchOutputs = new ArrayList<>();
        for (CourseDetails courseDetails : courseRepository.findAll()) {
            SearchOutputs outputs = new SearchOutputs();
            BeanUtils.copyProperties(courseDetails, outputs);
            searchOutputs.add(outputs);
        }
        if (Objects.nonNull(searchOutputs) && !searchOutputs.isEmpty()) {
            generateExcelReportFromData(searchOutputs, httpServletResponse);
        }
    }
}
