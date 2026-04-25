package com.lms.library.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lms.library.dto.request.DauSachCreationRequest;
import com.lms.library.dto.request.DauSachUpdateRequest;
import com.lms.library.dto.response.DauSachResponse;
import com.lms.library.dto.response.TacGiaResponse;
import com.lms.library.entity.CT_TacGia;
import com.lms.library.entity.DauSach;
import com.lms.library.entity.TacGia;
import com.lms.library.entity.TheLoai;
import com.lms.library.mapper.DauSachMapper;
import com.lms.library.repository.CT_TacGiaRepository;
import com.lms.library.repository.CuonSachRepository;
import com.lms.library.repository.DauSachRepository;
import com.lms.library.repository.SachRepository;
import com.lms.library.repository.TacGiaRepository;
import com.lms.library.repository.TheLoaiRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DauSachService {

    DauSachRepository dauSachRepository;
    TheLoaiRepository theLoaiRepository;
    DauSachMapper dauSachMapper;
    CT_TacGiaRepository CT_TacGiaRepository;
    TacGiaRepository tacGiaRepository;
    SachRepository sachRepository;
    CuonSachRepository cuonSachRepository;

    public DauSachResponse createDauSach(DauSachCreationRequest request) {
        TheLoai theLoai = theLoaiRepository.findById(request.getMaTheLoai())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thể loại"));

        DauSach dauSach = DauSach.builder()
                .tenDauSach(request.getTenDauSach())
                .theLoai(theLoai)
                .anhBiaUrl(request.getAnhBiaUrl())
                .isDeleted(false)
                .build();

        return dauSachMapper.toDauSachResponse(dauSachRepository.save(dauSach));
    }

    public List<DauSachResponse> getAllDauSach() {
        List<DauSach> dauSachList = dauSachRepository.findAll().stream()
                .filter(d -> d.getIsDeleted() == null || !d.getIsDeleted())
                .collect(Collectors.toList());

        return dauSachList.stream().map(dauSach -> {
            // Bước A: Map các thông tin cơ bản (Tên sách, thể loại, ảnh bìa)
            DauSachResponse response = dauSachMapper.toDauSachResponse(dauSach);

            // Bước B: Tìm danh sách mã tác giả nối với đầu sách này
            // (Lưu ý: Tùy tên hàm trong CTTacGiaRepository của bạn, có thể là
            // findByMaDauSach hoặc findByDauSach_MaDauSach)
            List<CT_TacGia> ctList = CT_TacGiaRepository.findByDauSach_MaDauSach(dauSach.getMaDauSach());

            // Bước C: Biến danh sách mã thành danh sách TacGiaResponse
            List<TacGiaResponse> tacGiaResponses = ctList.stream()
                    .map(ct -> {
                        // Tìm tác giả gốc trong DB. (Nếu entity CTTacGia của bạn đã có sẵn hàm
                        // getTacGia() thì càng tốt, bỏ qua dòng này)
                        TacGia tg = tacGiaRepository.findByMaTacGia(ct.getTacGia().getMaTacGia())
                                .orElse(null);
                        if (tg == null)
                            return null;

                        return TacGiaResponse.builder()
                                .maTacGia(tg.getMaTacGia())
                                .tenTacGia(tg.getTenTacGia())
                                .build();
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            // Bước D: Gắn mảng tác giả vào Response
            response.setTacGiaList(tacGiaResponses);

            return response;
        }).collect(Collectors.toList());
    }

    public DauSachResponse getDauSachById(Integer maDauSach) {
        DauSach dauSach = dauSachRepository.findById(maDauSach)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đầu sách"));

        if (dauSach.getIsDeleted() != null && dauSach.getIsDeleted()) {
            throw new RuntimeException("Đầu sách này đã bị xóa");
        }

        return dauSachMapper.toDauSachResponse(dauSach);
    }

    public List<DauSachResponse> getDauSachByTheLoai(Integer maTheLoai) {
        if (!theLoaiRepository.existsById(maTheLoai)) {
            throw new RuntimeException("Không tìm thấy thể loại");
        }

        return dauSachRepository.findByTheLoai_MaTheLoai(maTheLoai)
                .stream()
                .filter(d -> d.getIsDeleted() == null || !d.getIsDeleted())
                .map(dauSachMapper::toDauSachResponse)
                .toList();
    }

    public DauSachResponse updateDauSach(Integer maDauSach, DauSachUpdateRequest request) {
        DauSach dauSach = dauSachRepository.findById(maDauSach)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đầu sách"));

        if (dauSach.getIsDeleted() != null && dauSach.getIsDeleted()) {
            throw new RuntimeException("Không thể cập nhật đầu sách đã bị xóa");
        }

        if (request.getTenDauSach() != null) {
            dauSach.setTenDauSach(request.getTenDauSach());
        }

        if (request.getMaTheLoai() != null) {
            TheLoai theLoai = theLoaiRepository.findById(request.getMaTheLoai())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy thể loại"));
            dauSach.setTheLoai(theLoai);
        }

        return dauSachMapper.toDauSachResponse(dauSachRepository.save(dauSach));
    }

    /**
     * Soft Delete một DauSach - Xóa mềm dây chuyền
     * 
     * LOGIC CASCADING SOFT DELETE:
     * 1. Xóa mềm tất cả CuonSach liên quan (cháu)
     * 2. Xóa mềm tất cả Sach liên quan (con)
     * 3. Xóa mềm DauSach (cha)
     * 
     * Sử dụng JPQL Update Queries để tối ưu hiệu năng
     * (Không fetch dữ liệu lên RAM)
     * 
     * @param maDauSach: Mã đầu sách
     * @throws RuntimeException: Nếu DauSach không tồn tại
     */
    @Transactional
    public void softDeleteDauSach(Integer maDauSach) {
        DauSach dauSach = dauSachRepository.findById(maDauSach)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đầu sách"));

        if (dauSach.getIsDeleted() != null && dauSach.getIsDeleted()) {
            throw new RuntimeException("Đầu sách này đã bị xóa trước đó");
        }

        try {
            // BƯỚC 1: Xóa mềm tất cả CuonSach (cháu) liên quan
            // Sử dụng subquery tìm tất cả Sach của DauSach, rồi xóa mềm CuonSach
            int cuonSachDeleted = cuonSachRepository.softDeleteAllByMaDauSach(maDauSach);

            // BƯỚC 2: Xóa mềm tất cả Sach (con) liên quan
            int sachDeleted = sachRepository.softDeleteAllByMaDauSach(maDauSach);

            // BƯỚC 3: Xóa mềm DauSach (cha)
            dauSach.setIsDeleted(true);
            dauSachRepository.save(dauSach);

            // Log thông tin
            log.info("✓ Soft Delete DauSach ID={}: {} CuonSach, {} Sach",
                    maDauSach, cuonSachDeleted, sachDeleted);

        } catch (Exception e) {
            log.error("✗ Lỗi khi Soft Delete DauSach ID={}: {}", maDauSach, e.getMessage());
            // @Transactional sẽ tự động rollback
            throw new RuntimeException("Lỗi khi xóa đầu sách " + maDauSach + ": " + e.getMessage(), e);
        }
    }

    /**
     * Soft Delete một DauSach - Phiên bản cũ (giữ lại để tương thích)
     * 
     * @deprecated Sử dụng {@link #softDeleteDauSach(Integer)} thay vào
     */
    @Deprecated
    @Transactional
    public void deleteDauSach(Integer maDauSach) {
        softDeleteDauSach(maDauSach);
    }

    /**
     * Soft Delete nhiều DauSach cùng lúc - Xóa mềm dây chuyền
     * 
     * LOGIC CASCADING SOFT DELETE cho mỗi DauSach:
     * 1. Xóa mềm tất cả CuonSach liên quan (cháu)
     * 2. Xóa mềm tất cả Sach liên quan (con)
     * 3. Xóa mềm DauSach (cha)
     * 
     * Sử dụng JPQL Update Queries để tối ưu hiệu năng
     * 
     * @param ids: Danh sách mã đầu sách cần xóa mềm
     * @return Thông báo kết quả xóa
     */
    @Transactional
    public String deleteMultiple(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return "Không có đầu sách nào để xóa";
        }

        int deletedCount = 0;
        int totalCuonSachDeleted = 0;
        int totalSachDeleted = 0;

        for (Integer maDauSach : ids) {
            DauSach dauSach = dauSachRepository.findById(maDauSach).orElse(null);
            if (dauSach == null) {
                continue;
            }

            try {
                // BƯỚC 1: Xóa mềm tất cả CuonSach (cháu) liên quan
                int cuonSachDeleted = cuonSachRepository.softDeleteAllByMaDauSach(maDauSach);
                totalCuonSachDeleted += cuonSachDeleted;

                // BƯỚC 2: Xóa mềm tất cả Sach (con) liên quan
                int sachDeleted = sachRepository.softDeleteAllByMaDauSach(maDauSach);
                totalSachDeleted += sachDeleted;

                // BƯỚC 3: Xóa mềm DauSach (cha)
                dauSach.setIsDeleted(true);
                dauSachRepository.save(dauSach);
                deletedCount++;

                log.info("✓ Soft Delete DauSach ID={}: {} CuonSach, {} Sach",
                        maDauSach, cuonSachDeleted, sachDeleted);

            } catch (Exception e) {
                log.error("✗ Lỗi khi Soft Delete DauSach ID={}: {}", maDauSach, e.getMessage());
                // @Transactional sẽ tự động rollback nếu có lỗi
                throw new RuntimeException("Lỗi khi xóa đầu sách " + maDauSach + ": " + e.getMessage(), e);
            }
        }

        String result = String.format("Xóa thành công %d đầu sách (%d Sach, %d CuonSach)",
                deletedCount, totalSachDeleted, totalCuonSachDeleted);
        log.info("✓ {}", result);
        return result;
    }
}