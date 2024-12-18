package pickme.report.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pickme.report.dto.CompanyIndustryReportCreateDTO;
import pickme.report.dto.CompanyIndustryReportResponseDTO;
import pickme.report.dto.CompanyIndustryReportSidebarDTO;
import pickme.report.dto.ReportIdResponseDTO;
import pickme.report.service.JWTService;
import pickme.report.service.ReportService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/report")
@Tag(name = "Report", description = "기업 및 산업 분석 관리 API")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private JWTService jwtService;

    // 1. 새로운 CompanyIndustryReport 생성
    @Operation(summary = "보고서 생성", description = "새로운 기업/산업 분석 보고서를 생성합니다.")
    @PostMapping
    public ResponseEntity<ReportIdResponseDTO> createReport(
            @RequestHeader(value = "Authorization") String token,
            @Valid @RequestBody CompanyIndustryReportCreateDTO reportCreateDTO
    ) throws Exception {
        String userId = jwtService.extractToken(token);
        String reportId = reportService.createReport(userId, reportCreateDTO);
        // reportId만 담은 DTO 반환
        ReportIdResponseDTO response = new ReportIdResponseDTO(reportId);
        return ResponseEntity.status(201).body(response);
    }

    // 2. 특정 CompanyIndustryReport 조회
    @Operation(summary = "보고서 조회", description = "특정 기업/산업 분석 보고서를 조회합니다.")
    @GetMapping
    public ResponseEntity<CompanyIndustryReportResponseDTO> getReport(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam String reportId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) throws Exception {
        String userId = jwtService.extractToken(token);
        CompanyIndustryReportResponseDTO responseDTO = reportService.getReport(userId, reportId, page, size);
        if (responseDTO != null) {
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    // 3. CompanyIndustryReport 업데이트
    @Operation(summary = "보고서 업데이트", description = "기업/산업 분석 보고서를 업데이트합니다.")
    @PutMapping
    public ResponseEntity<CompanyIndustryReportResponseDTO> updateReport(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam String reportId,
            @Valid @RequestBody CompanyIndustryReportCreateDTO reportUpdateDTO
    ) throws Exception {
        String userId = jwtService.extractToken(token);
        CompanyIndustryReportResponseDTO responseDTO = reportService.updateReport(userId, reportId, reportUpdateDTO);
        if (responseDTO != null) {
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    // 4. CompanyIndustryReport 삭제
    @Operation(summary = "보고서 삭제", description = "기업/산업 분석 보고서를 삭제합니다.")
    @DeleteMapping
    public ResponseEntity<Void> deleteReport(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam String reportId
    ) throws Exception {
        String userId = jwtService.extractToken(token);
        boolean deleted = reportService.deleteReport(userId, reportId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    // 5. 사이드바 데이터 조회 (페이징 적용)
    @Operation(summary = "사이드바 데이터 조회", description = "사이드바에 필요한 기업/산업 분석 보고서를 조회합니다.")
    @GetMapping("/sidebar")
    public ResponseEntity<List<CompanyIndustryReportSidebarDTO>> getSidebarData(
            @RequestHeader(value = "Authorization") String token
    ) throws Exception {
        String userId = jwtService.extractToken(token);
        List<CompanyIndustryReportSidebarDTO> sidebarData = reportService.getSidebarData(userId);
        return ResponseEntity.ok(sidebarData);
    }

}
