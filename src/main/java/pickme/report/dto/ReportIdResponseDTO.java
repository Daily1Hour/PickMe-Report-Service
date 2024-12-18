package pickme.report.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "DTO for returning reportId")
public class ReportIdResponseDTO {

    private String reportId;

}
