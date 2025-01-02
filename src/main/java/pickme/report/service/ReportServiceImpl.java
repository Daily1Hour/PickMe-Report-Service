package pickme.report.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pickme.report.dto.CompanyIndustryReportCreateDTO;
import pickme.report.dto.CompanyIndustryReportResponseDTO;
import pickme.report.dto.CompanyIndustryReportSidebarDTO;
import pickme.report.mapper.ReportMapper;
import pickme.report.model.Report;
import pickme.report.repository.ReportRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private ReportMapper reportMapper;

    @Override
    public String createReport(String userId, CompanyIndustryReportCreateDTO reportCreateDTO) {
        Report report = reportRepository.findById(userId).orElseGet(() -> {
            Report newReport = new Report();
            newReport.setUserId(userId);
            newReport.setReports(new ArrayList<>());
            return newReport;
        });

        String category = reportCreateDTO.getCategory();

        // 새로운 CompanyIndustryReport 생성
        Report.CompanyIndustryReport companyIndustryReport = new Report.CompanyIndustryReport();
        String reportId = UUID.randomUUID().toString();
        companyIndustryReport.setReportId(reportId);
        companyIndustryReport.setCategory(category);
        Date now = new Date();
        companyIndustryReport.setCreatedAt(now);
        companyIndustryReport.setUpdatedAt(now);

        // Category에 따라 Detail 설정
        if (category.equalsIgnoreCase("company") || category.equalsIgnoreCase("all")) {
            companyIndustryReport.setCompanyDetail(reportMapper.toCompanyDetail(reportCreateDTO.getCompanyDetail()));
        }

        if (category.equalsIgnoreCase("industry") || category.equalsIgnoreCase("all")) {
            companyIndustryReport.setIndustryDetail(reportMapper.toIndustryDetail(reportCreateDTO.getIndustryDetail()));
        }

        // Report에 추가
        report.getReports().add(companyIndustryReport);

        // 저장
        reportRepository.save(report);

        return reportId;
    }

    @Override
    public CompanyIndustryReportResponseDTO getReport(String userId, String reportId) {
        Optional<Report> optionalReport = reportRepository.findById(userId);
        if (optionalReport.isPresent()) {
            Report.CompanyIndustryReport foundReport = findReportById(optionalReport.get(), reportId);
            if (foundReport != null) {
                return reportMapper.toCompanyIndustryReportResponseDTO(foundReport);
            }
        }
        return null;
    }

    @Override
    public CompanyIndustryReportResponseDTO updateReport(String userId, String reportId, CompanyIndustryReportCreateDTO reportUpdateDTO) {
        Optional<Report> optionalReport = reportRepository.findById(userId);
        if (optionalReport.isPresent()) {
            Report report = optionalReport.get();
            Report.CompanyIndustryReport companyIndustryReport = findReportById(report, reportId);
            if (companyIndustryReport != null) {
                String category = companyIndustryReport.getCategory();
                // Category에 따라 Details 업데이트
                if (category.equalsIgnoreCase("company") || category.equalsIgnoreCase("all")) {
                    companyIndustryReport.setCompanyDetail(reportMapper.toCompanyDetail(reportUpdateDTO.getCompanyDetail()));
                } else {
                    companyIndustryReport.setCompanyDetail(null);
                }

                if (category.equalsIgnoreCase("industry") || category.equalsIgnoreCase("all")) {
                    companyIndustryReport.setIndustryDetail(reportMapper.toIndustryDetail(reportUpdateDTO.getIndustryDetail()));
                } else {
                    companyIndustryReport.setIndustryDetail(null);
                }

                companyIndustryReport.setUpdatedAt(new Date());
                reportRepository.save(report);
                return reportMapper.toCompanyIndustryReportResponseDTO(companyIndustryReport);
            }
        }
        return null;
    }

    @Override
    public boolean deleteReport(String userId, String reportId) {
        Optional<Report> optionalReport = reportRepository.findById(userId);
        if (optionalReport.isPresent()) {
            Report report = optionalReport.get();
            Report.CompanyIndustryReport companyIndustryReport = findReportById(report, reportId);
            if (companyIndustryReport != null) {
                report.getReports().remove(companyIndustryReport);
                reportRepository.save(report);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<CompanyIndustryReportSidebarDTO> getSidebarData(String userId) {
        Optional<Report> optionalReport = reportRepository.findById(userId);
        if (optionalReport.isPresent()) {
            return optionalReport.get().getReports().stream()
                    .map(r -> {
                        CompanyIndustryReportSidebarDTO dto = new CompanyIndustryReportSidebarDTO();
                        dto.setReportId(r.getReportId());
                        dto.setCategory(r.getCategory());
                        dto.setCreatedAt(r.getCreatedAt());
                        dto.setUpdatedAt(r.getUpdatedAt());

                        // companyDetail과 industryDetail 중 존재하는 것에 따라 추가 정보 세팅
                        if (r.getCompanyDetail() != null && r.getIndustryDetail() == null) {
                            // companyDetail만 있는 경우
                            dto.setTitle(r.getCompanyDetail().getCompanyName());
                        } else if (r.getIndustryDetail() != null && r.getCompanyDetail() == null) {
                            // industryDetail만 있는 경우
                            dto.setTitle(r.getIndustryDetail().getIndustryFeatures());
                        }

                        return dto;
                    })
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }


    // 헬퍼 메서드
    private Report.CompanyIndustryReport findReportById(Report report, String reportId) {
        return report.getReports().stream()
                .filter(r -> r.getReportId().equals(reportId))
                .findFirst()
                .orElse(null);
    }

}
