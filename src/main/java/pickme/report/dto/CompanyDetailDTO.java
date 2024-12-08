package pickme.report.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "DTO for CompanyDetail")
public class CompanyDetailDTO {

    @Schema(description = "Company Name", example = "Day Company")
    @NotBlank(message = "Company name is required")
    private String companyName;

    @Schema(description = "Company Features", example = "수평적 구조")
    private String companyFeatures;

    @Schema(description = "Ideal Talent", example = "도전을 즐기는 인재")
    private String companyIdealTalent;

    @Schema(description = "Company News", example = "100억 투자 유치 성공")
    private String companyNews;

}
