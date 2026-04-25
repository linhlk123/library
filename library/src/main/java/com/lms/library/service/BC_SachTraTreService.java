package com.lms.library.service;

import com.lms.library.dto.request.BC_SachTraTreCreationRequest;
import com.lms.library.dto.request.BC_SachTraTreUpdateRequest;
import com.lms.library.dto.response.BC_SachTraTreResponse;
import com.lms.library.entity.BC_SachTraTre;
import com.lms.library.entity.BC_SachTraTreId;
import com.lms.library.entity.CuonSach;
import com.lms.library.mapper.BC_SachTraTreMapper;
import com.lms.library.repository.BC_SachTraTreRepository;
import com.lms.library.repository.CuonSachRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BC_SachTraTreService {

    BC_SachTraTreRepository bcSachTraTreRepository;
    CuonSachRepository cuonSachRepository;
    BC_SachTraTreMapper bcSachTraTreMapper;

    @Transactional
    public BC_SachTraTreResponse create(BC_SachTraTreCreationRequest request) {
        BC_SachTraTreId id = new BC_SachTraTreId(request.getNgay(), request.getMaCuonSach());

        if (bcSachTraTreRepository.existsById(id)) {
            throw new RuntimeException("Record already exists for this date and book");
        }

        CuonSach cuonSach = cuonSachRepository.findById(request.getMaCuonSach())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        BC_SachTraTre bcSachTraTre = BC_SachTraTre.builder()
                .ngay(request.getNgay())
                .cuonSach(cuonSach)
                .ngayMuon(request.getNgayMuon())
                .soNgayTraTre(request.getSoNgayTraTre())
                .build();

        return bcSachTraTreMapper.toResponse(bcSachTraTreRepository.save(bcSachTraTre));
    }

    @Transactional(readOnly = true)
    public List<BC_SachTraTreResponse> getAll() {
        return bcSachTraTreRepository.findAll()
                .stream()
                .map(bcSachTraTreMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<BC_SachTraTreResponse> getByNgay(java.time.LocalDate ngay) {
        return bcSachTraTreRepository.findByNgay(ngay)
                .stream()
                .map(bcSachTraTreMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<BC_SachTraTreResponse> getByCuonSach(Integer maCuonSach) {
        if (!cuonSachRepository.existsById(maCuonSach)) {
            throw new RuntimeException("Book not found");
        }

        return bcSachTraTreRepository.findByCuonSach_MaCuonSach(maCuonSach)
                .stream()
                .map(bcSachTraTreMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public BC_SachTraTreResponse getById(java.time.LocalDate ngay, Integer maCuonSach) {
        BC_SachTraTreId id = new BC_SachTraTreId(ngay, maCuonSach);

        BC_SachTraTre bcSachTraTre = bcSachTraTreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        return bcSachTraTreMapper.toResponse(bcSachTraTre);
    }

    @Transactional
    public BC_SachTraTreResponse update(java.time.LocalDate ngay, Integer maCuonSach,
            BC_SachTraTreUpdateRequest request) {
        BC_SachTraTreId id = new BC_SachTraTreId(ngay, maCuonSach);

        BC_SachTraTre bcSachTraTre = bcSachTraTreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        if (request.getNgayMuon() != null) {
            bcSachTraTre.setNgayMuon(request.getNgayMuon());
        }

        if (request.getSoNgayTraTre() != null) {
            bcSachTraTre.setSoNgayTraTre(request.getSoNgayTraTre());
        }

        return bcSachTraTreMapper.toResponse(bcSachTraTreRepository.save(bcSachTraTre));
    }

    @Transactional
    public void delete(java.time.LocalDate ngay, Integer maCuonSach) {
        BC_SachTraTreId id = new BC_SachTraTreId(ngay, maCuonSach);

        if (!bcSachTraTreRepository.existsById(id)) {
            throw new RuntimeException("Record not found");
        }

        bcSachTraTreRepository.deleteById(id);
    }
}