package pickme.report.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@Schema(description = "DTO for CompanyIndustryReport responses")
public class CompanyIndustryReportResponseDTO {

    private String category;
    private Date createdAt;
    private Date updatedAt;
    private CompanyDetailDTO companyDetail;
    private IndustryDetailDTO industryDetail;

}
