package com.example.inventory_management.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.inventory_management.DTO.ReportsDTO;
import com.example.inventory_management.Service.ExportReportService;
import com.example.inventory_management.Service.ReportService;

@RestController
@RequestMapping("/api/export")
public class ExportReportController {

	@Autowired
	private ReportService RT_service;

	@Autowired
	private ExportReportService ER_service;

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/download/{period}")
	private ResponseEntity<byte[]> download(@PathVariable Integer period) throws IOException {
		System.out.println("from export controller");
		List<ReportsDTO> reports = null;
		if (period == 1) {
			reports = RT_service.getYear();
		}
		if (period == 2) {
			reports = RT_service.getMonth();
		}
		if (period == 3) {
			reports = RT_service.get();
		}
		byte[] excel = ER_service.generateExcelReport(reports);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reports.xlsx")
				.contentType(
						MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
				.body(excel);
	}

}
