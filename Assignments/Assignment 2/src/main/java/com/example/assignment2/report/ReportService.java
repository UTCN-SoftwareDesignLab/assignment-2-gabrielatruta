package com.example.assignment2.report;

import java.io.ByteArrayOutputStream;

public interface ReportService {

    ByteArrayOutputStream export();
    ReportType getType();

}
