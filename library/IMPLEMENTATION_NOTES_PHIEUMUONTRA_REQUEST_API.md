# PhieuMuonTra API - New Implementation Summary

## Changes Made

### 1. **Entity Update - PhieuMuonTra.java**
- **Added trangThai field**: New field with `@Enumerated(EnumType.STRING)` mapping to `TrangThaiPhieu` enum
- **Made ngayMuon nullable**: Changed `@Column(name = "NgayMuon", nullable = false)` → `@Column(name = "NgayMuon")`
- **Made maNhanVien nullable**: Changed `nullable = false` → `nullable = true` on ManyToOne relationship
- **Import**: Added import for `TrangThaiPhieu` enum

### 2. **New Enum - TrangThaiPhieu.java**
Created new enum file at: `src/main/java/com/lms/library/enums/TrangThaiPhieu.java`

Status values:
- `PENDING` - Chờ xác nhận (Awaiting confirmation)
- `ACTIVE` - Đang mượn (Currently borrowed)
- `REJECTED` - Từ chối (Rejected)
- `RETURNED` - Đã trả (Returned)

Each status has code and description properties.

### 3. **Repository Enhancement - DocGiaRepository.java**
Added new method:
```java
@Query("SELECT d FROM DocGia d WHERE d.nguoiDung.tenDangNhap = :tenDangNhap")
Optional<DocGia> findByTenDangNhap(@Param("tenDangNhap") String tenDangNhap);
```
This allows finding DocGia by user's username (tenDangNhap).

### 4. **New Request DTO - PhieuMuonTraRequestRequest.java**
Created new DTO at: `src/main/java/com/lms/library/dto/request/PhieuMuonTraRequestRequest.java`

Payload structure:
```java
{
    "tenDangNhap": "user_username",
    "maCuonSach": 123
}
```

### 5. **Service Implementation - PhieuMuonTraService.java**
Added new method: `createBorrowRequest(PhieuMuonTraRequestRequest request)`

**Logic Flow:**
1. **Find DocGia by tenDangNhap** - Throws RuntimeException if not found
   - Error message: "Tài khoản của bạn chưa được liên kết với Thẻ Độc Giả hợp lệ."

2. **Validate Borrowing Rules:**
   - ✅ Check card is valid (ngayHetHan not expired)
   - ✅ Check user has no fine debt (tongNo <= 0)
   - ✅ Check user has no overdue books
   - ✅ Check user hasn't reached limit of 5 books

3. **Create Request:**
   - Creates new PhieuMuonTra with:
     - `ngayMuon = null` (not yet delivered)
     - `maNhanVien = null` (no staff assigned)
     - `trangThai = PENDING` (awaiting confirmation)
     - `tienPhat = 0`

**Updated imports:**
- Added `PhieuMuonTraRequestRequest`
- Added `TrangThaiPhieu`

### 6. **Controller Addition - PhieuMuonTraController.java**
Added new endpoint:
```java
@PostMapping("/request")
public ApiResponse<PhieuMuonTraResponse> createBorrowRequest(@RequestBody PhieuMuonTraRequestRequest request)
```

**Endpoint URL:** `POST /api/phieumuontra/request`

**Response on success:**
- Code: 1000
- Message: "Tạo yêu cầu mượn sách thành công. Vui lòng chờ nhân viên xác nhận."
- Result: PhieuMuonTraResponse with trangThai = "PENDING"

### 7. **Response DTO Update - PhieuMuonTraResponse.java**
Added new field:
```java
String trangThai;  // PENDING, ACTIVE, REJECTED, RETURNED
```

### 8. **Service Mapper Update - PhieuMuonTraService.java**
Updated `toResponse()` method to map trangThai:
```java
.trangThai(entity.getTrangThai() != null ? entity.getTrangThai().getCode() : null)
```

## API Usage Example

**Request:**
```bash
POST /api/phieumuontra/request
Content-Type: application/json

{
    "tenDangNhap": "john_doe",
    "maCuonSach": 42
}
```

**Success Response (200 OK):**
```json
{
    "code": 1000,
    "message": "Tạo yêu cầu mượn sách thành công. Vui lòng chờ nhân viên xác nhận.",
    "result": {
        "soPhieu": 101,
        "maCuonSach": 42,
        "maDocGia": "john_doe",
        "tenDocGia": "Nguyễn Văn A",
        "ngayMuon": null,
        "ngayPhaiTra": null,
        "ngayTra": null,
        "soNgayMuon": null,
        "tienPhat": 0,
        "maNhanVien": null,
        "tenNhanVien": null,
        "trangThai": "PENDING"
    }
}
```

**Error Response (400/500):**
```json
{
    "code": 1000,
    "message": "Tài khoản của bạn chưa được liên kết với Thẻ Độc Giả hợp lệ.",
    "result": null
}
```

## Database Schema Changes Required

If using Flyway/Liquibase migrations, add to your SQL migration:

```sql
ALTER TABLE PHIEUMUONTRA 
    ADD COLUMN TrangThai VARCHAR(20),
    MODIFY NgayMuon DATE NULL,
    MODIFY MaNhanVien VARCHAR(50) NULL;
```

## Files Modified

1. ✅ `src/main/java/com/lms/library/entity/PhieuMuonTra.java`
2. ✅ `src/main/java/com/lms/library/enums/TrangThaiPhieu.java` (NEW)
3. ✅ `src/main/java/com/lms/library/repository/DocGiaRepository.java`
4. ✅ `src/main/java/com/lms/library/dto/request/PhieuMuonTraRequestRequest.java` (NEW)
5. ✅ `src/main/java/com/lms/library/service/PhieuMuonTraService.java`
6. ✅ `src/main/java/com/lms/library/controller/PhieuMuonTraController.java`
7. ✅ `src/main/java/com/lms/library/dto/response/PhieuMuonTraResponse.java`

## Validation Rules Summary

| Rule | Condition | Error Message |
|------|-----------|---|
| Valid DocGia | User has linked reader card | "Tài khoản của bạn chưa được liên kết với Thẻ Độc Giả hợp lệ." |
| Card Valid | ngayHetHan not expired | "Thẻ độc giả đã hết hạn, không thể mượn sách" |
| No Fine Debt | tongNo <= 0 | "Bạn còn nợ phạt. Vui lòng thanh toán trước khi mượn sách mới." |
| No Overdue Books | No unreturned books past due date | "Bạn có sách mượn quá hạn, không thể mượn thêm sách" |
| Borrow Limit | Current borrowed books < 5 | "Bạn đã mượn số lượng sách tối đa (5 quyển)..." |

## Next Steps (Optional)

1. Update staff endpoint to approve/reject requests (update trangThai from PENDING to ACTIVE/REJECTED)
2. Add staff endpoint to fulfill approved requests (set ngayMuon, ngayPhaiTra, maNhanVien)
3. Add migration script to update database schema
4. Test with Postman collection
