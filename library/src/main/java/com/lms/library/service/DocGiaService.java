package com.lms.library.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lms.library.dto.request.DocGiaCreationRequest;
import com.lms.library.dto.request.DocGiaUpdateRequest;
import com.lms.library.dto.response.DocGiaResponse;
import com.lms.library.dto.response.MyReaderCardResponse;
import com.lms.library.entity.DocGia;
import com.lms.library.entity.LoaiDocGia;
import com.lms.library.entity.NguoiDung;
import com.lms.library.mapper.DocGiaMapper;
import com.lms.library.repository.DocGiaRepository;
import com.lms.library.repository.LoaiDocGiaRepository;
import com.lms.library.repository.NguoiDungRepository;
import com.lms.library.repository.PhieuMuonTraRepository;
import com.lms.library.repository.PhieuThuTienPhatRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DocGiaService {

    DocGiaRepository docGiaRepository;
    LoaiDocGiaRepository loaiDocGiaRepository;
    NguoiDungRepository nguoiDungRepository;
    PhieuMuonTraRepository phieuMuonTraRepository;
    PhieuThuTienPhatRepository phieuThuTienPhatRepository;
    DocGiaMapper docGiaMapper;

    public DocGiaResponse createDocGia(DocGiaCreationRequest request) {

        NguoiDung existingNguoiDung = nguoiDungRepository.findByTenDangNhap(request.getMaDocGia())
                .orElseThrow(
                        () -> new RuntimeException("Account does not exist. Please register first!"));

        if (docGiaRepository.existsById(request.getMaDocGia())) {
            throw new RuntimeException("This account has already been issued a library card!");
        }

        // STEP 2: FIND CARD TYPE
        LoaiDocGia loaiDocGia = loaiDocGiaRepository.findById(request.getMaLoaiDocGia())
                .orElseThrow(() -> new RuntimeException("Card type not found"));

        // BƯỚC 3: TẠO THẺ ĐỘC GIẢ VÀ "MÓC NỐI"
        // Note: DO NOT set maDocGia - @MapsId will automatically derive it from
        // nguoiDung
        DocGia docGia = DocGia.builder()
                .nguoiDung(existingNguoiDung) // Set nguoiDung - primary key will auto-populate from this
                .loaiDocGia(loaiDocGia)
                .ngayLapThe(request.getNgayLapThe() != null ? request.getNgayLapThe() : LocalDate.now())
                .ngayHetHan(request.getNgayHetHan() != null ? request.getNgayHetHan() : LocalDate.now().plusYears(1))
                .tongNo(BigDecimal.ZERO)
                .tenVaiTro(existingNguoiDung.getVaiTro() != null ? existingNguoiDung.getVaiTro().getTenVaiTro() : null)
                .build();

        // Lưu xuống DB
        docGia = docGiaRepository.save(docGia);

        return docGiaMapper.toDocGiaResponse(docGia);
    }

    public List<DocGiaResponse> getAllDocGia() {
        return docGiaRepository.findAll()
                .stream()
                .map(docGiaMapper::toDocGiaResponse)
                .toList();
    }

    public DocGiaResponse getDocGiaById(String maDocGia) {
        DocGia docGia = docGiaRepository.findById(maDocGia)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy độc giả"));

        return docGiaMapper.toDocGiaResponse(docGia);
    }

    public DocGiaResponse updateDocGia(String maDocGia, DocGiaUpdateRequest request) {
        // Find existing DocGia
        DocGia docGia = docGiaRepository.findById(maDocGia)
                .orElseThrow(() -> new RuntimeException("Reader not found"));

        // Validate and update card type
        LoaiDocGia loaiDocGia = loaiDocGiaRepository.findById(request.getMaLoaiDocGia())
                .orElseThrow(() -> new RuntimeException("Card type not found"));

        docGia.setLoaiDocGia(loaiDocGia);
        docGia.setNgayLapThe(request.getNgayLapThe());
        docGia.setNgayHetHan(request.getNgayHetHan());

        return docGiaMapper.toDocGiaResponse(docGiaRepository.save(docGia));
    }

    public void deleteDocGia(String maDocGia) {
        if (!docGiaRepository.existsById(maDocGia)) {
            throw new RuntimeException("Reader not found");
        }
        docGiaRepository.deleteById(maDocGia);
    }

    /**
     * API GET /api/docgia/my-card
     * Lấy thông tin thẻ độc giả của người dùng hiện tại (đang đăng nhập)
     * 
     * @return MyReaderCardResponse chứa: maDocGia, tenDocGia, tenLoaiDocGia,
     *         ngayLapThe, ngayHetHan, tongNo, cardValid, cardStatus
     */
    public MyReaderCardResponse getMyReaderCard() {
        // Lấy username của người dùng hiện tại từ SecurityContext
        String tenDangNhap = SecurityContextHolder.getContext().getAuthentication().getName();

        if (tenDangNhap == null || tenDangNhap.isEmpty()) {
            throw new RuntimeException("Không thể xác định người dùng hiện tại");
        }

        // Tìm DocGia bằng tenDangNhap
        DocGia docGia = docGiaRepository.findByTenDangNhap(tenDangNhap)
                .orElseThrow(() -> new RuntimeException(
                        "Tài khoản của bạn chưa được liên kết với Thẻ Độc Giả. Vui lòng liên hệ nhân viên thư viện."));

        // Lấy thông tin người dùng
        NguoiDung nguoiDung = docGia.getNguoiDung();
        String tenDocGia = nguoiDung != null ? nguoiDung.getHoTen() : "N/A";

        // Lấy tên loại thẻ
        LoaiDocGia loaiDocGia = docGia.getLoaiDocGia();
        String tenLoaiDocGia = loaiDocGia != null ? loaiDocGia.getTenLoaiDocGia() : "N/A";

        // Xác định trạng thái thẻ
        LocalDate today = LocalDate.now();
        Boolean cardValid = docGia.getNgayHetHan() != null && !docGia.getNgayHetHan().isBefore(today);

        String cardStatus;
        if (!cardValid) {
            cardStatus = "EXPIRED"; // Thẻ đã hết hạn
        } else if (docGia.getTongNo() != null && docGia.getTongNo().compareTo(BigDecimal.ZERO) > 0) {
            cardStatus = "PENDING_FEES"; // Còn nợ phạt
        } else {
            cardStatus = "VALID"; // Thẻ hợp lệ
        }

        // Build response
        return MyReaderCardResponse.builder()
                .maDocGia(docGia.getMaDocGia())
                .tenDocGia(tenDocGia)
                .tenLoaiDocGia(tenLoaiDocGia)
                .ngayLapThe(docGia.getNgayLapThe())
                .ngayHetHan(docGia.getNgayHetHan())
                .tongNo(docGia.getTongNo() != null ? docGia.getTongNo() : BigDecimal.ZERO)
                .cardValid(cardValid)
                .cardStatus(cardStatus)
                .build();
    }

    /**
     * Cập nhật tổng nợ của một độc giả dựa trên tổng tiền phạt và tiền thu
     * Logic: tongNo = tongTienPhat - tongTienThu
     * Đảm bảo @Transactional để tránh dữ liệu bất đồng bộ
     *
     * @param maDocGia: Mã độc giả cần cập nhật
     */
    @Transactional
    public void updateTongNoDocGia(String maDocGia) {
        // Lấy độc giả từ database
        DocGia docGia = docGiaRepository.findById(maDocGia)
                .orElseThrow(() -> new RuntimeException("Độc giả không tồn tại"));

        // Tính tổng tiền phạt từ PhieuMuonTra
        BigDecimal tongTienPhat = phieuMuonTraRepository.tinhTongTienPhatByDocGia(maDocGia);

        // Tính tổng tiền thu từ PhieuThuTienPhat
        BigDecimal tongTienThu = phieuThuTienPhatRepository.tinhTongTienThuByDocGia(maDocGia);

        // Tính tổng nợ: nợ = tổng phạt - tổng thu
        BigDecimal tongNo = tongTienPhat.subtract(tongTienThu);

        // Nợ không được âm, nếu âm thì set về 0
        if (tongNo.compareTo(BigDecimal.ZERO) < 0) {
            tongNo = BigDecimal.ZERO;
        }

        // Cập nhật vào entity
        docGia.setTongNo(tongNo);

        // Lưu vào database
        docGiaRepository.save(docGia);
    }
}