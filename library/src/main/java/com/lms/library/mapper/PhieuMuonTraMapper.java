package com.lms.library.mapper;

import com.lms.library.dto.request.PhieuMuonTraCreationRequest;
import com.lms.library.dto.request.PhieuMuonTraUpdateRequest;
import com.lms.library.dto.response.PhieuMuonTraResponse;
import com.lms.library.entity.PhieuMuonTra;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PhieuMuonTraMapper {

    PhieuMuonTra toPhieuMuonTra(PhieuMuonTraCreationRequest request);

    void updatePhieuMuonTra(@MappingTarget PhieuMuonTra phieuMuonTra,
            PhieuMuonTraUpdateRequest request);

    PhieuMuonTraResponse toPhieuMuonTraResponse(PhieuMuonTra phieuMuonTra);
}