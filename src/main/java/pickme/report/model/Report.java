package pickme.report.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "reports")
@Data
@Schema(description = "기업 및 산업 분석 데이터 모델")
@CompoundIndexes(
        // reports 배열 안의 각 요소에 들어있는 reportId에 대해 인덱스를 생성
        @CompoundIndex(name = "reportId_index", def = "{'reports.reportId': 1}")
)
public class Report {

    @Id
    private String userId;

    private List<CompanyIndustryReport> reports;

    @Data
    public static class CompanyIndustryReport {
        private String reportId;
        private String category;
        private Date createdAt;
        private Date updatedAt;
        private CompanyDetail companyDetail;
        private IndustryDetail industryDetail;
    }

    @Data
    public static class CompanyDetail {
        private String companyName;
        private String companyFeatures;
        private String companyIdealTalent;
        private String companyNews;
    }

    @Data
    public static class IndustryDetail {
        private String industryType;
        private String industryFeatures;
        private String industryNews;
    }

}
