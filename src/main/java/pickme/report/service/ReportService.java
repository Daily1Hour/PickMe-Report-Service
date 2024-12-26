package pickme.report.service;

import pickme.report.dto.CompanyIndustryReportCreateDTO;
import pickme.report.dto.CompanyIndustryReportResponseDTO;
import pickme.report.dto.CompanyIndustryReportSidebarDTO;

import java.util.List;

public interface ReportService {

    String createReport(String userId, CompanyIndustryReportCreateDTO reportCreateDTO);

    CompanyIndustryReportResponseDTO getReport(String userId, String reportId);

    CompanyIndustryReportResponseDTO updateReport(String userId, String reportId, CompanyIndustryReportCreateDTO reportUpdateDTO);

    boolean deleteReport(String userId, String reportId);

    List<CompanyIndustryReportSidebarDTO> getSidebarData(String userId);

}
