# "Thẻ Độc Giả Của Tôi" Feature Implementation

## Overview
Implemented "My Reader Card" feature for the USER subsystem that allows authenticated users to view their reader card information directly from the database.

---

## Part 1: Backend Implementation (Spring Boot)

### 1. New Response DTO - `MyReaderCardResponse.java`
**Location:** `src/main/java/com/lms/library/dto/response/MyReaderCardResponse.java`

```java
{
    String maDocGia;              // Username (from NGUOIDUNG.tenDangNhap)
    String tenDocGia;             // Full name (from NGUOIDUNG.hoTen)
    String tenLoaiDocGia;         // Card type name (from LOAIDOCGIA.tenLoaiDocGia)
    LocalDate ngayLapThe;         // Card issuance date
    LocalDate ngayHetHan;         // Card expiration date
    BigDecimal tongNo;            // Outstanding fine amount
    Boolean cardValid;            // Is card still valid? (ngayHetHan >= today)
    String cardStatus;            // Status: VALID, EXPIRED, PENDING_FEES
}
```

---

### 2. Repository - `DocGiaRepository.java`
**Already Implemented** - Uses existing `findByTenDangNhap()` method:

```java
@Query("SELECT d FROM DocGia d WHERE d.nguoiDung.tenDangNhap = :tenDangNhap")
Optional<DocGia> findByTenDangNhap(@Param("tenDangNhap") String tenDangNhap);
```

This query finds DocGia by the user's username since:
- `maDocGia` in DOCGIA table = `tenDangNhap` in NGUOIDUNG table (1-1 relationship via @MapsId)

---

### 3. Service Method - `DocGiaService.java`
**New Method:** `getMyReaderCard()`

**Implementation Details:**

1. **Get Current User**
   ```java
   String tenDangNhap = SecurityContextHolder.getContext().getAuthentication().getName();
   ```
   - Extracts username from JWT token via Spring Security

2. **Query Database**
   ```java
   DocGia docGia = docGiaRepository.findByTenDangNhap(tenDangNhap);
   ```
   - No mock data - direct database access

3. **Determine Card Status**
   - `VALID`: Card not expired, no outstanding fines
   - `EXPIRED`: Card expiration date is in the past
   - `PENDING_FEES`: Card valid but reader has outstanding fines

4. **Return Populated Response**
   - maDocGia: from DocGia.maDocGia
   - tenDocGia: from DocGia.nguoiDung.hoTen
   - tenLoaiDocGia: from DocGia.loaiDocGia.tenLoaiDocGia
   - ngayLapThe, ngayHetHan, tongNo: directly from DocGia
   - cardValid: computed boolean (ngayHetHan >= today)
   - cardStatus: computed enum-like string

---

### 4. Controller Endpoint - `DocGiaController.java`
**New Endpoint:** `GET /api/docgia/my-card`

```java
@GetMapping("/my-card")
public ApiResponse<MyReaderCardResponse> getMyReaderCard() {
    return ApiResponse.<MyReaderCardResponse>builder()
            .code(1000)
            .message("Lấy thông tin thẻ độc giả thành công")
            .result(docGiaService.getMyReaderCard())
            .build();
}
```

**Key Points:**
- Placed before `@GetMapping("/{maDocGia}")` to match correct route pattern
- Uses `/my-card` path to distinguish from viewing other users' cards
- Automatically injects `DocGiaService`
- Returns standardized `ApiResponse` wrapper

---

## Data Flow Diagram

```
User (Authenticated via JWT)
        ↓
SecurityContextHolder
        ↓
Extract tenDangNhap from JWT Token
        ↓
DocGiaRepository.findByTenDangNhap(tenDangNhap)
        ↓
Query: SELECT d FROM DocGia d WHERE d.nguoiDung.tenDangNhap = :tenDangNhap
        ↓
Database (DOCGIA → join NGUOIDUNG, LOAIDOCGIA)
        ↓
Build MyReaderCardResponse with:
  - Basic Info (maDocGia, tenDocGia, tenLoaiDocGia)
  - Dates (ngayLapThe, ngayHetHan)
  - Finance (tongNo)
  - Computed Fields (cardValid, cardStatus)
        ↓
Return ApiResponse<MyReaderCardResponse>
```

---

## API Usage Example

### Request
```bash
GET /api/docgia/my-card
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

### Success Response (200 OK)
```json
{
    "code": 1000,
    "message": "Lấy thông tin thẻ độc giả thành công",
    "result": {
        "maDocGia": "john_doe",
        "tenDocGia": "Nguyễn Văn A",
        "tenLoaiDocGia": "Thẻ Bạc",
        "ngayLapThe": "2024-01-15",
        "ngayHetHan": "2025-01-15",
        "tongNo": 15000,
        "cardValid": true,
        "cardStatus": "PENDING_FEES"
    }
}
```

### Error Response - No Reader Card
```json
{
    "code": 1000,
    "message": "Tài khoản của bạn chưa được liên kết với Thẻ Độc Giả. Vui lòng liên hệ nhân viên thư viện.",
    "result": null
}
```

### Error Response - Not Authenticated
```json
{
    "code": 401,
    "message": "Unauthorized",
    "result": null
}
```

---

## Card Status Logic

| Condition | Status | Description |
|-----------|--------|-------------|
| ngayHetHan < today | `EXPIRED` | Card is no longer valid |
| ngayHetHan >= today AND tongNo > 0 | `PENDING_FEES` | Card is valid but reader has unpaid fines |
| ngayHetHan >= today AND tongNo <= 0 | `VALID` | Card is in good standing |

---

## Database Relations

```
NGUOIDUNG (NGUOIDUNG table)
├── tenDangNhap (PK) ──┐
├── hoTen               │
├── email               │
├── diaChi              │
└── tenVaiTro           │
                        │
                        ├─→ DOCGIA (DOCGIA table)
                             ├── MaDocGia (PK) = tenDangNhap (1-1 via @MapsId)
                             ├── NgayLapThe
                             ├── NgayHetHan
                             ├── TongNo
                             └── MaLoaiDocGia (FK)
                                      │
                                      └─→ LOAIDOCGIA
                                           ├── MaLoaiDocGia (PK)
                                           └── TenLoaiDocGia
```

---

## Files Modified/Created

| File | Type | Changes |
|------|------|---------|
| `DocGiaService.java` | Modified | Added `getMyReaderCard()` method; Added import for `SecurityContextHolder` and `MyReaderCardResponse` |
| `DocGiaController.java` | Modified | Added `getMyReaderCard()` endpoint; Added import for `MyReaderCardResponse` |
| `MyReaderCardResponse.java` | **NEW** | Created new response DTO |
| `DocGiaRepository.java` | No change | Already has `findByTenDangNhap()` |

---

## Security Considerations

✅ **Authentication Required**: Endpoint requires valid JWT token
✅ **User Isolation**: Users can only view their own card (via SecurityContext)
✅ **No Database Injection**: Uses parameterized queries
✅ **Proper Error Handling**: Custom exceptions with meaningful messages

---

## Integration Points

1. **JWT Authentication**: Works with existing Spring Security OAuth2 configuration
2. **Entity Relations**: Leverages existing @OneToOne and @ManyToOne mappings
3. **Database Access**: Uses Spring Data JPA repository pattern
4. **Response Format**: Follows existing ApiResponse wrapper pattern

---

## Testing Checklist

- [ ] Test with valid authenticated user who has reader card
- [ ] Test with valid authenticated user who has NO reader card
- [ ] Test without authentication token (should be 401)
- [ ] Test response data accuracy (dates, names, fine amounts)
- [ ] Test card status computation:
  - [ ] VALID status (not expired, no fines)
  - [ ] EXPIRED status (past expiration date)
  - [ ] PENDING_FEES status (valid but has fines)
- [ ] Test with readers who have:
  - [ ] Recent card issuance
  - [ ] Expired card
  - [ ] Outstanding fines
  - [ ] Paid all fines
