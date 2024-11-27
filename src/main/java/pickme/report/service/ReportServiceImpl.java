package pickme.report.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pickme.report.dto.CompanyIndustryReportCreateDTO;
import pickme.report.dto.CompanyIndustryReportResponseDTO;
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
    public CompanyIndustryReportResponseDTO createReport(String userId, CompanyIndustryReportCreateDTO reportCreateDTO) {
        Report report = reportRepository.findById(userId).orElseGet(() -> {
            Report newReport = new Report();
            newReport.setUserId(userId);
            newReport.setReports(new ArrayList<>());
            return newReport;
        });

        String category = reportCreateDTO.getCategory();

        // 새로운 CompanyIndustryReport 생성
        Report.CompanyIndustryReport companyIndustryReport = new Report.CompanyIndustryReport();
        companyIndustryReport.setCategory(category);
        Date now = new Date();
        companyIndustryReport.setCreatedAt(now);
        companyIndustryReport.setUpdatedAt(now);

        // Category에 따라 Details 설정
        if (category.equalsIgnoreCase("company") || category.equalsIgnoreCase("all")) {
            companyIndustryReport.setCompanyDetails(reportMapper.toCompanyDetailList(reportCreateDTO.getCompanyDetails()));
        }

        if (category.equalsIgnoreCase("industry") || category.equalsIgnoreCase("all")) {
            companyIndustryReport.setIndustryDetails(reportMapper.toIndustryDetailList(reportCreateDTO.getIndustryDetails()));
        }

        // Report에 추가
        report.getReports().add(companyIndustryReport);

        // 저장
        reportRepository.save(report);

        return reportMapper.toCompanyIndustryReportResponseDTO(companyIndustryReport);
    }

    @Override
    public CompanyIndustryReportResponseDTO getReport(String userId, String category, Date createdAt, int page, int size) {
        Optional<Report> optionalReport = reportRepository.findById(userId);
        if (optionalReport.isPresent()) {
            Report.CompanyIndustryReport report = findReport(optionalReport.get(), category, createdAt);
            if (report != null) {
                CompanyIndustryReportResponseDTO responseDTO = reportMapper.toCompanyIndustryReportResponseDTO(report);
                // Details에 페이징 적용
                if (category.equalsIgnoreCase("company") || category.equalsIgnoreCase("all")) {
                    responseDTO.setCompanyDetails(paginateList(responseDTO.getCompanyDetails(), page, size));
                }
                if (category.equalsIgnoreCase("industry") || category.equalsIgnoreCase("all")) {
                    responseDTO.setIndustryDetails(paginateList(responseDTO.getIndustryDetails(), page, size));
                }
                return responseDTO;
            }
        }
        return null;
    }

    @Override
    public CompanyIndustryReportResponseDTO updateReport(String userId, String category, Date createdAt, CompanyIndustryReportCreateDTO reportUpdateDTO) {
        Optional<Report> optionalReport = reportRepository.findById(userId);
        if (optionalReport.isPresent()) {
            Report report = optionalReport.get();
            Report.CompanyIndustryReport companyIndustryReport = findReport(report, category, createdAt);
            if (companyIndustryReport != null) {
                // Category에 따라 Details 업데이트
                if (category.equalsIgnoreCase("company") || category.equalsIgnoreCase("all")) {
                    companyIndustryReport.setCompanyDetails(reportMapper.toCompanyDetailList(reportUpdateDTO.getCompanyDetails()));
                }

                if (category.equalsIgnoreCase("industry") || category.equalsIgnoreCase("all")) {
                    companyIndustryReport.setIndustryDetails(reportMapper.toIndustryDetailList(reportUpdateDTO.getIndustryDetails()));
                }

                companyIndustryReport.setUpdatedAt(new Date());
                reportRepository.save(report);
                return reportMapper.toCompanyIndustryReportResponseDTO(companyIndustryReport);
            }
        }
        return null;
    }

    @Override
    public boolean deleteReport(String userId, String category, Date createdAt) {
        Optional<Report> optionalReport = reportRepository.findById(userId);
        if (optionalReport.isPresent()) {
            Report report = optionalReport.get();
            Report.CompanyIndustryReport companyIndustryReport = findReport(report, category, createdAt);
            if (companyIndustryReport != null) {
                report.getReports().remove(companyIndustryReport);
                reportRepository.save(report);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<CompanyIndustryReportResponseDTO> getReportList(String userId, String category, int page, int size) {
        Optional<Report> optionalReport = reportRepository.findById(userId);
        if (optionalReport.isPresent()) {
            List<Report.CompanyIndustryReport> reports = optionalReport.get().getReports().stream()
                    .filter(r -> r.getCategory().equalsIgnoreCase(category))
                    .collect(Collectors.toList());

            List<Report.CompanyIndustryReport> paginatedReports = paginateList(reports, page, size);

            return paginatedReports.stream()
                    .map(reportMapper::toCompanyIndustryReportResponseDTO)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    // 헬퍼 메서드
    private Report.CompanyIndustryReport findReport(Report report, String category, Date createdAt) {
        return report.getReports().stream()
                .filter(r -> r.getCategory().equalsIgnoreCase(category) && r.getCreatedAt().equals(createdAt))
                .findFirst()
                .orElse(null);
    }

    private <T> List<T> paginateList(List<T> list, int page, int size) {
        int start = page * size;
        int end = Math.min(start + size, list.size());
        if (start >= list.size()) {
            return Collections.emptyList();
        }
        return list.subList(start, end);
    }

}
