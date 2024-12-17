package pickme.report.service;

import pickme.report.dto.CompanyIndustryReportCreateDTO;
import pickme.report.dto.CompanyIndustryReportResponseDTO;

import java.util.Date;
import java.util.List;

public interface ReportService {

    String createReport(String userId, CompanyIndustryReportCreateDTO reportCreateDTO);

    CompanyIndustryReportResponseDTO getReport(String userId, String reportId, int page, int size);

    CompanyIndustryReportResponseDTO updateReport(String userId, String reportId, CompanyIndustryReportCreateDTO reportUpdateDTO);

    boolean deleteReport(String userId, String reportId);

    List<CompanyIndustryReportResponseDTO> getReportList(String userId, String category, int page, int size);

}
