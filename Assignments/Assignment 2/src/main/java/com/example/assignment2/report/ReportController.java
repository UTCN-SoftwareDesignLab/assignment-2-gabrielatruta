package com.example.assignment2.report;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.assignment2.URLMapping.API_PATH;
import static com.example.assignment2.URLMapping.EXPORT_REPORT;

@RestController
@RequestMapping(API_PATH)
@RequiredArgsConstructor
public class ReportController {

    private final ReportServiceFactory reportServiceFactory;

    @GetMapping(EXPORT_REPORT)
    public String export(@PathVariable ReportType type) {
        System.out.println("Export " + type);
        return reportServiceFactory.getReportService(type).export();
    }
}
