package pickme.report.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "DTO for IndustryDetail")
public class IndustryDetailDTO {

    @Schema(description = "Industry Type", example = "E-commerce")
    @NotBlank(message = "Industry type is required")
    private String industryType;

    @Schema(description = "Industry Features")
    private String industryFeatures;

    @Schema(description = "Industry News")
    private String industryNews;

}
