package pickme.report.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "DTO for creating a new CompanyIndustryReport")
public class CompanyIndustryReportCreateDTO {

    @Schema(description = "Category", example = "All")
    @NotBlank(message = "Category is required")
    private String category;

    @Schema(description = "회사 상세 정보 리스트")
    @Valid
    private List<CompanyDetailDTO> companyDetails;

    @Schema(description = "산업 상세 정보 리스트")
    @Valid
    private List<IndustryDetailDTO> industryDetails;

}
