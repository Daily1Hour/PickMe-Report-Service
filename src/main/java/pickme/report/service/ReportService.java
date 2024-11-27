package pickme.report.service;

import pickme.report.dto.CompanyIndustryReportCreateDTO;
import pickme.report.dto.CompanyIndustryReportResponseDTO;

import java.util.Date;
import java.util.List;

public interface ReportService {

    CompanyIndustryReportResponseDTO createReport(String userId, CompanyIndustryReportCreateDTO reportCreateDTO);

    CompanyIndustryReportResponseDTO getReport(String userId, String category, Date createdAt, int page, int size);

    CompanyIndustryReportResponseDTO updateReport(String userId, String category, Date createdAt, CompanyIndustryReportCreateDTO reportUpdateDTO);

    boolean deleteReport(String userId, String category, Date createdAt);

    List<CompanyIndustryReportResponseDTO> getReportList(String userId, String category, int page, int size);

}
