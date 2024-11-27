package pickme.report.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "reports")
@Data
@Schema(description = "기업 분석 데이터 모델")
public class Report {

    @Id
    private String userId;

    private List<CompanyIndustryReport> reports;

    @Data
    public static class CompanyIndustryReport {
        private String category;
        private Date createdAt;
        private Date updatedAt;
        private List<CompanyDetail> companyDetails;
        private List<IndustryDetails> industryDetails;
    }

    @Data
    public static class CompanyDetail {
        private String companyName;
        private String companyFeatures;
        private String companyIdealTalent;
        private String companyNews;
    }

    @Data
    public static class IndustryDetails {
        private String industryType;
        private String industryFeatures;
        private String industryNews;
    }

}
