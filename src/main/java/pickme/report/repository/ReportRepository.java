package pickme.report.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pickme.report.model.Report;

@Repository
public interface ReportRepository extends MongoRepository<Report, String> {
}
