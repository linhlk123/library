package com.lms.library.service;

import com.lms.library.dto.request.ThamSoCreationRequest;
import com.lms.library.dto.request.ThamSoUpdateRequest;
import com.lms.library.dto.response.ThamSoResponse;
import com.lms.library.entity.ThamSo;
import com.lms.library.exception.AppException;
import com.lms.library.exception.ErrorCode;
import com.lms.library.mapper.ThamSoMapper;
import com.lms.library.repository.ThamSoRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ThamSoService {

    ThamSoRepository thamSoRepository;
    ThamSoMapper thamSoMapper;

    @Transactional
    public ThamSoResponse createThamSo(ThamSoCreationRequest request) {
        if (thamSoRepository.existsById(request.getTenThamSo())) {
            throw new AppException(ErrorCode.THAM_SO_EXISTED);
        }

        ThamSo thamSo = ThamSo.builder()
                .tenThamSo(request.getTenThamSo())
                .giaTri(request.getGiaTri())
                .build();

        return thamSoMapper.toThamSoResponse(thamSoRepository.save(thamSo));
    }

    @Transactional(readOnly = true)
    public List<ThamSoResponse> getAllThamSo() {
        return thamSoRepository.findAll()
                .stream()
                .map(thamSoMapper::toThamSoResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ThamSoResponse getThamSoById(String tenThamSo) {
        ThamSo thamSo = thamSoRepository.findById(tenThamSo)
                .orElseThrow(() -> new AppException(ErrorCode.THAM_SO_NOT_FOUND));

        return thamSoMapper.toThamSoResponse(thamSo);
    }

    @Transactional
    public ThamSoResponse updateThamSo(String tenThamSo, ThamSoUpdateRequest request) {
        ThamSo thamSo = thamSoRepository.findById(tenThamSo)
                .orElseThrow(() -> new AppException(ErrorCode.THAM_SO_NOT_FOUND));

        thamSo.setGiaTri(request.getGiaTri());

        return thamSoMapper.toThamSoResponse(thamSoRepository.save(thamSo));
    }

    @Transactional
    public void deleteThamSo(String tenThamSo) {
        if (!thamSoRepository.existsById(tenThamSo)) {
            throw new AppException(ErrorCode.THAM_SO_NOT_FOUND);
        }

        thamSoRepository.deleteById(tenThamSo);
    }

    /**
     * ==================== Getter Methods cho Nghiệp vụ ====================
     * Các method này dùng để lấy giá trị tham số từ database
     * và convert sang kiểu dữ liệu thích hợp
     */

    @Transactional(readOnly = true)
    public Integer getTuoiToiThieu() {
        ThamSo thamSo = thamSoRepository.findById("TuoiToiThieu")
                .orElseThrow(() -> new AppException(ErrorCode.THAM_SO_NOT_FOUND));
        return Integer.parseInt(thamSo.getGiaTri());
    }

    @Transactional(readOnly = true)
    public Integer getTuoiToiDa() {
        ThamSo thamSo = thamSoRepository.findById("TuoiToiDa")
                .orElseThrow(() -> new AppException(ErrorCode.THAM_SO_NOT_FOUND));
        return Integer.parseInt(thamSo.getGiaTri());
    }

    @Transactional(readOnly = true)
    public Integer getThoiHanThe() {
        ThamSo thamSo = thamSoRepository.findById("ThoiHanThe")
                .orElseThrow(() -> new AppException(ErrorCode.THAM_SO_NOT_FOUND));
        return Integer.parseInt(thamSo.getGiaTri());
    }

    @Transactional(readOnly = true)
    public Integer getKhoangCachNamXB() {
        ThamSo thamSo = thamSoRepository.findById("KhoangCachNamXB")
                .orElseThrow(() -> new AppException(ErrorCode.THAM_SO_NOT_FOUND));
        return Integer.parseInt(thamSo.getGiaTri());
    }

    @Transactional(readOnly = true)
    public Integer getSoNgayMuonToiDa() {
        ThamSo thamSo = thamSoRepository.findById("SoNgayMuonToiDa")
                .orElseThrow(() -> new AppException(ErrorCode.THAM_SO_NOT_FOUND));
        return Integer.parseInt(thamSo.getGiaTri());
    }

    @Transactional(readOnly = true)
    public Integer getSoSachMuonToiDa() {
        ThamSo thamSo = thamSoRepository.findById("SoSachMuonToiDa")
                .orElseThrow(() -> new AppException(ErrorCode.THAM_SO_NOT_FOUND));
        return Integer.parseInt(thamSo.getGiaTri());
    }

    @Transactional(readOnly = true)
    public Long getTienPhatToiDa() {
        ThamSo thamSo = thamSoRepository.findById("TienPhatToiDa")
                .orElseThrow(() -> new AppException(ErrorCode.THAM_SO_NOT_FOUND));
        return Long.parseLong(thamSo.getGiaTri());
    }

    @Transactional(readOnly = true)
    public Boolean isApDungQDKiemTraSoTienThu() {
        ThamSo thamSo = thamSoRepository.findById("ApDungQDKiemTraSoTienThu")
                .orElseThrow(() -> new AppException(ErrorCode.THAM_SO_NOT_FOUND));
        return thamSo.getGiaTri().equals("1");
    }
}