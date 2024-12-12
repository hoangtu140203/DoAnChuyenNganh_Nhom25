package com.gr25.thinkpro.service;

public interface ExportService {
    byte[] exportAllTimeReport() throws Exception;
    byte[] exportMonthReport(int month, int year) throws Exception;
    byte[] exportDayReport(String date) throws Exception;
}
