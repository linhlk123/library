package com.lms.library.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lms.library.dto.request.PhieuMuonTraCreationRequest;
import com.lms.library.dto.request.PhieuMuonTraRequestRequest;
import com.lms.library.dto.request.PhieuMuonTraUpdateRequest;
import com.lms.library.dto.response.PhieuMuonTraResponse;
import com.lms.library.dto.response.PhieuMuonTraResponseDTO;
import com.lms.library.entity.CT_TacGia;
import com.lms.library.entity.CuonSach;
import com.lms.library.entity.DauSach;
import com.lms.library.entity.DocGia;
import com.lms.library.entity.NguoiDung;
import com.lms.library.entity.PhieuMuonTra;
import com.lms.library.entity.Sach;
import com.lms.library.enums.TrangThaiPhieu;
import com.lms.library.repository.CT_TacGiaRepository;
import com.lms.library.repository.CuonSachRepository;
import com.lms.library.repository.DocGiaRepository;
import com.lms.library.repository.NguoiDungRepository;
import com.lms.library.repository.PhieuMuonTraRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PhieuMuonTraService {

	PhieuMuonTraRepository phieuMuonTraRepository;
	CT_TacGiaRepository ctTacGiaRepository;
	CuonSachRepository cuonSachRepository;
	DocGiaRepository docGiaRepository;
	NguoiDungRepository nguoiDungRepository;
	DocGiaService docGiaService;

	@Transactional
	public PhieuMuonTraResponse createBorrowRequest(PhieuMuonTraRequestRequest request) {
		DocGia docGia = docGiaRepository.findByTenDangNhap(request.getTenDangNhap())
				.orElseThrow(
						() -> new RuntimeException("Tài khoản của bạn chưa được liên kết với Thẻ Độc Giả hợp lệ."));

		CuonSach cuonSach = cuonSachRepository.findById(request.getMaCuonSach())
				.orElseThrow(() -> new RuntimeException("Cuốn sách không tồn tại"));

		LocalDate today = LocalDate.now();
		if (docGia.getNgayHetHan() == null || docGia.getNgayHetHan().isBefore(today)) {
			throw new RuntimeException("Thẻ độc giả đã hết hạn, không thể mượn sách");
		}

		if (docGia.getTongNo() != null && docGia.getTongNo().compareTo(BigDecimal.ZERO) > 0) {
			throw new RuntimeException("Bạn còn nợ phạt. Vui lòng thanh toán trước khi mượn sách mới.");
		}

		boolean hasOverdueBooks = phieuMuonTraRepository.existsByDocGiaAndNgayTraIsNullAndNgayPhaiTraBefore(docGia,
				today);
		if (hasOverdueBooks) {
			throw new RuntimeException("Bạn có sách mượn quá hạn, không thể mượn thêm sách");
		}

		long currentBorrowedBooks = phieuMuonTraRepository.countByDocGiaAndNgayTraIsNull(docGia);
		if (currentBorrowedBooks >= 5) {
			throw new RuntimeException("Bạn đã mượn số lượng sách tối đa (5 quyển). " +
					"Vui lòng trả lại ít nhất 1 cuốn sách trước khi mượn thêm.");
		}

		PhieuMuonTra phieuMuonTra = PhieuMuonTra.builder()
				.cuonSach(cuonSach)
				.docGia(docGia)
				.ngayMuon(null)
				.ngayPhaiTra(null)
				.ngayTra(null)
				.soNgayMuon(null)
				.tienPhat(BigDecimal.ZERO)
				.nhanVien(null)
				.trangThai(TrangThaiPhieu.PENDING)
				.build();

		phieuMuonTraRepository.save(phieuMuonTra);

		return toResponse(phieuMuonTra);
	}

	@Transactional
	public PhieuMuonTraResponse create(PhieuMuonTraCreationRequest request) {
		CuonSach cuonSach = cuonSachRepository.findById(request.getMaCuonSach())
				.orElseThrow(() -> new RuntimeException("Cuốn sách không tồn tại"));

		DocGia docGia = docGiaRepository.findById(request.getMaDocGia())
				.orElseThrow(() -> new RuntimeException("Độc giả không tồn tại"));

		NguoiDung nhanVien = nguoiDungRepository.findById(request.getMaNhanVien())
				.orElseThrow(() -> new RuntimeException("Nhân viên không tồn tại"));

		validateRequest(request);

		Integer soNgayMuon = tinhSoNgayMuon(request.getNgayMuon(), request.getNgayTra(), request.getSoNgayMuon());
		BigDecimal tienPhat = request.getTienPhat() != null ? request.getTienPhat() : BigDecimal.ZERO;

		PhieuMuonTra phieuMuonTra = PhieuMuonTra.builder()
				.cuonSach(cuonSach)
				.docGia(docGia)
				.ngayMuon(request.getNgayMuon())
				.ngayPhaiTra(request.getNgayPhaiTra())
				.ngayTra(request.getNgayTra())
				.soNgayMuon(soNgayMuon)
				.tienPhat(tienPhat)
				.nhanVien(nhanVien)
				.trangThai(resolveTrangThai(request.getTrangThai(), TrangThaiPhieu.PENDING))
				.build();

		phieuMuonTraRepository.save(phieuMuonTra);

		if (tienPhat.compareTo(BigDecimal.ZERO) > 0) {
			docGiaService.updateTongNoDocGia(docGia.getMaDocGia());
		}

		return toResponse(phieuMuonTra);
	}

	public List<PhieuMuonTraResponseDTO> getAll() {
		List<PhieuMuonTra> phieuMuonTraList = phieuMuonTraRepository.findAllWithBookInfo();
		return mapToResponseDTOList(phieuMuonTraList);
	}

	public List<PhieuMuonTraResponseDTO> getByMaDocGia(String maDocGia) {
		List<PhieuMuonTra> phieuMuonTraList = phieuMuonTraRepository.findByMaDocGiaWithBookInfo(maDocGia);
		return mapToResponseDTOList(phieuMuonTraList);
	}

	public PhieuMuonTraResponse getById(Integer soPhieu) {
		PhieuMuonTra phieuMuonTra = phieuMuonTraRepository.findById(soPhieu)
				.orElseThrow(() -> new RuntimeException("Phiếu mượn trả không tồn tại"));

		return toResponse(phieuMuonTra);
	}

	@Transactional
	public PhieuMuonTraResponse update(Integer soPhieu, PhieuMuonTraUpdateRequest request) {
		PhieuMuonTra phieuMuonTra = phieuMuonTraRepository.findById(soPhieu)
				.orElseThrow(() -> new RuntimeException("Phiếu mượn trả không tồn tại"));

		CuonSach cuonSach = cuonSachRepository.findById(request.getMaCuonSach())
				.orElseThrow(() -> new RuntimeException("Sách không tồn tại"));

		DocGia docGia = docGiaRepository.findById(request.getMaDocGia())
				.orElseThrow(() -> new RuntimeException("Độc giả không tồn tại"));

		NguoiDung nhanVien = nguoiDungRepository.findById(request.getMaNhanVien())
				.orElseThrow(() -> new RuntimeException("Nhân viên không tồn tại"));

		validateRequest(request);

		LocalDate ngayTra = LocalDate.now();
		phieuMuonTra.setNgayTra(ngayTra);

		BigDecimal tienPhat = BigDecimal.ZERO;
		if (phieuMuonTra.getNgayPhaiTra() != null && ngayTra.isAfter(phieuMuonTra.getNgayPhaiTra())) {
			long soNgayTreHan = ChronoUnit.DAYS.between(phieuMuonTra.getNgayPhaiTra(), ngayTra);
			tienPhat = BigDecimal.valueOf(soNgayTreHan * 1000);
		}

		phieuMuonTra.setTienPhat(tienPhat);
		phieuMuonTra.setCuonSach(cuonSach);
		phieuMuonTra.setDocGia(docGia);
		phieuMuonTra.setNhanVien(nhanVien);
		phieuMuonTra.setTrangThai(resolveTrangThai(request.getTrangThai(), phieuMuonTra.getTrangThai()));
		if (request.getSoNgayMuon() != null) {
			phieuMuonTra.setSoNgayMuon(request.getSoNgayMuon());
		}

		phieuMuonTraRepository.save(phieuMuonTra);

		cuonSach.setTinhTrang("Sẵn sàng");
		cuonSachRepository.save(cuonSach);

		if (tienPhat.compareTo(BigDecimal.ZERO) > 0) {
			docGiaService.updateTongNoDocGia(docGia.getMaDocGia());
		}

		return toResponse(phieuMuonTra);
	}

	public void delete(Integer soPhieu) {
		PhieuMuonTra phieuMuonTra = phieuMuonTraRepository.findById(soPhieu)
				.orElseThrow(() -> new RuntimeException("Borrowing voucher does not exist"));

		phieuMuonTraRepository.delete(phieuMuonTra);
	}

	private List<PhieuMuonTraResponseDTO> mapToResponseDTOList(List<PhieuMuonTra> phieuMuonTraList) {
		List<Integer> maDauSachIds = phieuMuonTraList.stream()
				.map(PhieuMuonTra::getCuonSach)
				.filter(Objects::nonNull)
				.map(CuonSach::getSach)
				.filter(Objects::nonNull)
				.map(Sach::getDauSach)
				.filter(Objects::nonNull)
				.map(DauSach::getMaDauSach)
				.filter(Objects::nonNull)
				.distinct()
				.toList();

		Map<Integer, String> tacGiaByDauSachId = buildTacGiaMap(maDauSachIds);

		return phieuMuonTraList.stream()
				.map(phieuMuonTra -> toResponseDTO(phieuMuonTra, tacGiaByDauSachId))
				.toList();
	}

	private Map<Integer, String> buildTacGiaMap(List<Integer> maDauSachIds) {
		if (maDauSachIds == null || maDauSachIds.isEmpty()) {
			return Collections.emptyMap();
		}

		List<CT_TacGia> ctTacGias = ctTacGiaRepository.findAllByDauSachIds(maDauSachIds);

		return ctTacGias.stream()
				.filter(ct -> ct.getDauSach() != null && ct.getDauSach().getMaDauSach() != null)
				.collect(Collectors.groupingBy(
						ct -> ct.getDauSach().getMaDauSach(),
						Collectors.mapping(
								ct -> ct.getTacGia() != null ? ct.getTacGia().getTenTacGia() : null,
								Collectors.collectingAndThen(
										Collectors.toList(),
										names -> names.stream()
												.filter(Objects::nonNull)
												.distinct()
												.collect(Collectors.joining(", "))))));
	}

	private PhieuMuonTraResponseDTO toResponseDTO(PhieuMuonTra entity, Map<Integer, String> tacGiaByDauSachId) {
		CuonSach cuonSach = entity.getCuonSach();
		Sach sach = cuonSach != null ? cuonSach.getSach() : null;
		DauSach dauSach = sach != null ? sach.getDauSach() : null;

		Integer maDauSach = dauSach != null ? dauSach.getMaDauSach() : null;
		String tacGia = maDauSach != null ? tacGiaByDauSachId.get(maDauSach) : null;
		if (tacGia != null && tacGia.isBlank()) {
			tacGia = null;
		}

		return PhieuMuonTraResponseDTO.builder()
				.soPhieu(entity.getSoPhieu())
				.ngayMuon(entity.getNgayMuon())
				.ngayPhaiTra(entity.getNgayPhaiTra())
				.ngayTra(entity.getNgayTra())
				.tienPhat(entity.getTienPhat())
				.trangThai(entity.getTrangThai() != null ? entity.getTrangThai().name() : null)
				.maDocGia(entity.getDocGia() != null ? entity.getDocGia().getMaDocGia() : null)
				.maCuonSach(cuonSach != null ? cuonSach.getMaCuonSach() : null)
				.tenDauSach(dauSach != null ? dauSach.getTenDauSach() : null)
				.anhBiaUrl(dauSach != null ? dauSach.getAnhBiaUrl() : null)
				.tacGia(tacGia)
				.nhaXuatBan(sach != null ? sach.getNhaXuatBan() : null)
				.build();
	}

	private void validateRequest(PhieuMuonTraCreationRequest request) {
		if (request.getNgayMuon() == null) {
			throw new RuntimeException("Borrowing date cannot be empty");
		}

		if (request.getNgayPhaiTra() != null && request.getNgayPhaiTra().isBefore(request.getNgayMuon())) {
			throw new RuntimeException("Return due date cannot be before borrowing date");
		}

		if (request.getNgayTra() != null && request.getNgayTra().isBefore(request.getNgayMuon())) {
			throw new RuntimeException("Return date cannot be before borrowing date");
		}

		if (request.getTienPhat() != null && request.getTienPhat().compareTo(BigDecimal.ZERO) < 0) {
			throw new RuntimeException("Fine amount cannot be negative");
		}
	}

	private void validateRequest(PhieuMuonTraUpdateRequest request) {
		if (request.getNgayMuon() == null) {
			throw new RuntimeException("Borrowing date cannot be empty");
		}

		if (request.getNgayPhaiTra() != null && request.getNgayPhaiTra().isBefore(request.getNgayMuon())) {
			throw new RuntimeException("Return due date cannot be before borrowing date");
		}

		if (request.getNgayTra() != null && request.getNgayTra().isBefore(request.getNgayMuon())) {
			throw new RuntimeException("Return date cannot be before borrowing date");
		}

		if (request.getTienPhat() != null && request.getTienPhat().compareTo(BigDecimal.ZERO) < 0) {
			throw new RuntimeException("Fine amount cannot be negative");
		}
	}

	private Integer tinhSoNgayMuon(LocalDate ngayMuon, LocalDate ngayTra, Integer soNgayMuonInput) {
		if (ngayMuon != null && ngayTra != null) {
			return (int) ChronoUnit.DAYS.between(ngayMuon, ngayTra);
		}
		return soNgayMuonInput != null ? soNgayMuonInput : 0;
	}

	private TrangThaiPhieu resolveTrangThai(String rawStatus, TrangThaiPhieu defaultValue) {
		if (rawStatus == null || rawStatus.isBlank()) {
			return defaultValue;
		}
		try {
			return TrangThaiPhieu.valueOf(rawStatus.trim().toUpperCase());
		} catch (IllegalArgumentException ex) {
			throw new RuntimeException("Trạng thái phiếu không hợp lệ: " + rawStatus);
		}
	}

	private PhieuMuonTraResponse toResponse(PhieuMuonTra entity) {
		return PhieuMuonTraResponse.builder()
				.soPhieu(entity.getSoPhieu())
				.maCuonSach(entity.getCuonSach() != null ? entity.getCuonSach().getMaCuonSach() : null)
				.maDocGia(entity.getDocGia() != null ? entity.getDocGia().getMaDocGia() : null)
				.tenDocGia(entity.getDocGia() != null && entity.getDocGia().getNguoiDung() != null
						? entity.getDocGia().getNguoiDung().getHoTen()
						: null)
				.ngayMuon(entity.getNgayMuon())
				.ngayPhaiTra(entity.getNgayPhaiTra())
				.ngayTra(entity.getNgayTra())
				.soNgayMuon(entity.getSoNgayMuon())
				.tienPhat(entity.getTienPhat())
				.maNhanVien(entity.getNhanVien() != null ? entity.getNhanVien().getTenDangNhap() : null)
				.tenNhanVien(entity.getNhanVien() != null ? entity.getNhanVien().getHoTen() : null)
				.trangThai(entity.getTrangThai() != null ? entity.getTrangThai().getCode() : null)
				.build();
	}

	public long countPending() {
        return phieuMuonTraRepository.countByTrangThai(TrangThaiPhieu.PENDING); 
    }
}
