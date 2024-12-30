package pickme.report.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@Schema(description = "DTO for CompanyIndustryReport sidebar data")
public class CompanyIndustryReportSidebarDTO {

    @Schema(description = "Report ID")
    private String reportId;

    @Schema(description = "Category")
    private String category;

    @Schema(description = "Creation time")
    private Date createdAt;

    @Schema(description = "Last update time")
    private Date updatedAt;

    @Schema(description = "회사 이름", example = "Day Company")
    private String companyName;

    @Schema(description = "산업 타입", example = "E-commerce")
    private String industryType;

}
