package pickme.report.mapper;

import org.mapstruct.Mapper;
import pickme.report.dto.CompanyDetailDTO;
import pickme.report.dto.CompanyIndustryReportResponseDTO;
import pickme.report.dto.IndustryDetailDTO;
import pickme.report.model.Report;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ReportMapper {

    // CompanyIndustryReport를 CompanyIndustryReportResponseDTO로 매핑
    default CompanyIndustryReportResponseDTO toCompanyIndustryReportResponseDTO(Report.CompanyIndustryReport report) {
        if (report == null) {
            return null;
        }
        CompanyIndustryReportResponseDTO dto = new CompanyIndustryReportResponseDTO();
        dto.setCategory(report.getCategory());
        dto.setCreatedAt(report.getCreatedAt());
        dto.setUpdatedAt(report.getUpdatedAt());
        dto.setCompanyDetail(toCompanyDetailDTO(report.getCompanyDetail()));
        dto.setIndustryDetail(toIndustryDetailDTO(report.getIndustryDetail()));
        return dto;
    }

    // 개별 매핑 메서드들
    default CompanyDetailDTO toCompanyDetailDTO(Report.CompanyDetail detail) {
        if (detail == null) {
            return null;
        }
        CompanyDetailDTO dto = new CompanyDetailDTO();
        dto.setCompanyName(detail.getCompanyName());
        dto.setCompanyFeatures(detail.getCompanyFeatures());
        dto.setCompanyIdealTalent(detail.getCompanyIdealTalent());
        dto.setCompanyNews(detail.getCompanyNews());
        return dto;
    }

    default Report.CompanyDetail toCompanyDetail(CompanyDetailDTO dto) {
        if (dto == null) {
            return null;
        }
        Report.CompanyDetail detail = new Report.CompanyDetail();
        detail.setCompanyName(dto.getCompanyName());
        detail.setCompanyFeatures(dto.getCompanyFeatures());
        detail.setCompanyIdealTalent(dto.getCompanyIdealTalent());
        detail.setCompanyNews(dto.getCompanyNews());
        return detail;
    }

    default IndustryDetailDTO toIndustryDetailDTO(Report.IndustryDetail detail) {
        if (detail == null) {
            return null;
        }
        IndustryDetailDTO dto = new IndustryDetailDTO();
        dto.setIndustryType(detail.getIndustryType());
        dto.setIndustryFeatures(detail.getIndustryFeatures());
        dto.setIndustryNews(detail.getIndustryNews());
        return dto;
    }

    default Report.IndustryDetail toIndustryDetail(IndustryDetailDTO dto) {
        if (dto == null) {
            return null;
        }
        Report.IndustryDetail detail = new Report.IndustryDetail();
        detail.setIndustryType(dto.getIndustryType());
        detail.setIndustryFeatures(dto.getIndustryFeatures());
        detail.setIndustryNews(dto.getIndustryNews());
        return detail;
    }

}
