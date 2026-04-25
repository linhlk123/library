# Module Quản lý Tham Số (ThamSo - Business Rules Parameters)

## 📋 Giới thiệu

Module **Quản lý Tham Số** quản lý các quy định nghiệp vụ của hệ thống Thư viện. Cung cấp các API để đọc và cập nhật các tham số định hình hành vi của hệ thống.

## 📊 Cấu trúc Dữ liệu

**Bảng THAMSO:**
```sql
CREATE TABLE THAMSO (
    TenThamSo VARCHAR(100) PRIMARY KEY,
    GiaTri VARCHAR(100) NOT NULL
);
```

| Tham số | Giá trị | Ý nghĩa |
|--------|--------|--------|
| `TuoiToiThieu` | 18 | Tuổi tối thiểu của độc giả |
| `TuoiToiDa` | 55 | Tuổi tối đa của độc giả |
| `ThoiHanThe` | 6 | Thời hạn thẻ độc giả (tháng) |
| `KhoangCachNamXB` | 8 | Khoảng cách năm xuất bản - sách quá cũ không được mượn (năm) |
| `SoNgayMuonToiDa` | 4 | Số ngày mượn tối đa (ngày) |
| `SoSachMuonToiDa` | 5 | Số sách mượn tối đa trong một lần mượn |
| `TienPhatToiDa` | 1000 | Tiền phạt tối đa (VNĐ/ngày) |
| `ApDungQDKiemTraSoTienThu` | 1 | Áp dụng Quy Định Kiểm Tra Số Tiền Thu (1=Có, 0=Không) |

## 🚀 API Endpoints

### 1. Lấy tất cả tham số
```
GET /api/thamso
```

**Response (200 OK):**
```json
{
  "code": 1000,
  "message": "Lấy danh sách tham số thành công",
  "result": [
    {
      "tenThamSo": "TuoiToiThieu",
      "giaTri": "18"
    },
    {
      "tenThamSo": "TuoiToiDa",
      "giaTri": "55"
    }
  ]
}
```

### 2. Lấy một tham số cụ thể
```
GET /api/thamso/{tenThamSo}
```

**Example:**
```
GET /api/thamso/TuoiToiThieu
```

**Response (200 OK):**
```json
{
  "code": 1000,
  "message": "Lấy thông tin tham số thành công",
  "result": {
    "tenThamSo": "TuoiToiThieu",
    "giaTri": "18"
  }
}
```

### 3. Cập nhật giá trị tham số
```
PUT /api/thamso/{tenThamSo}
Content-Type: application/json

{
  "giaTri": "20"
}
```

**Example:**
```
PUT /api/thamso/TuoiToiThieu
```

**Response (200 OK):**
```json
{
  "code": 1000,
  "message": "Cập nhật tham số thành công",
  "result": {
    "tenThamSo": "TuoiToiThieu",
    "giaTri": "20"
  }
}
```

### 4. Tạo tham số mới
```
POST /api/thamso
Content-Type: application/json

{
  "tenThamSo": "TenThamSoMoi",
  "giaTri": "GiaTriMoi"
}
```

**Response (200 OK):**
```json
{
  "code": 1000,
  "message": "Tạo tham số thành công",
  "result": {
    "tenThamSo": "TenThamSoMoi",
    "giaTri": "GiaTriMoi"
  }
}
```

### 5. Xóa tham số
```
DELETE /api/thamso/{tenThamSo}
```

**Response (200 OK):**
```json
{
  "code": 1000,
  "message": "Xóa tham số thành công",
  "result": "Xóa tham số thành công"
}
```

## 🔧 Service Methods - Getter Functions

ThamSoService cung cấp các getter methods để các service khác dễ dàng truy cập tham số:

```java
// Trả về Integer
public Integer getTuoiToiThieu()
public Integer getTuoiToiDa()
public Integer getThoiHanThe()
public Integer getKhoangCachNamXB()
public Integer getSoNgayMuonToiDa()
public Integer getSoSachMuonToiDa()

// Trả về Long
public Long getTienPhatToiDa()

// Trả về Boolean
public Boolean isApDungQDKiemTraSoTienThu()
```

### Cách sử dụng trong các Service khác:

```java
@Service
@RequiredArgsConstructor
public class PhieuMuonTraService {
    private final ThamSoService thamSoService;
    
    public void validateMuonSach(PhieuMuonTraCreationRequest request) {
        Integer soNgayToiDa = thamSoService.getSoNgayMuonToiDa();
        Integer soSachToiDa = thamSoService.getSoSachMuonToiDa();
        
        if (request.getSoNgay() > soNgayToiDa) {
            throw new AppException(ErrorCode.INVALID_FORMAT);
        }
        
        if (request.getCuonSachList().size() > soSachToiDa) {
            throw new AppException(ErrorCode.INVALID_FORMAT);
        }
    }
}
```

## 🌱 Data Seeding

### Tự động khởi tạo dữ liệu

Khi ứng dụng khởi chạy, class `ThamSoDataSeeder` (triển khai `CommandLineRunner`) sẽ:

1. **Kiểm tra** xem bảng THAMSO có dữ liệu chưa
2. **Nếu chưa có**, tự động chèn 8 bản ghi mặc định
3. **Nếu đã có**, bỏ qua (không chèn trùng)

**Log output:**
```
=============== Starting ThamSoDataSeeder ===============
Initializing ThamSo (Business Rules Parameters)...
✓ Successfully initialized 8 ThamSo records
  - TuoiToiThieu: 18
  - TuoiToiDa: 55
  - ThoiHanThe: 6 months
  - KhoangCachNamXB: 8 years
  - SoNgayMuonToiDa: 4 days
  - SoSachMuonToiDa: 5 books
  - TienPhatToiDa: 1000 VND/day
  - ApDungQDKiemTraSoTienThu: 1 (enabled)
=============== ThamSoDataSeeder completed ===============
```

## 📦 Cấu trúc File

```
src/main/java/com/lms/library/
├── entity/
│   └── ThamSo.java                      # Entity class
├── repository/
│   └── ThamSoRepository.java            # JPA Repository
├── service/
│   └── ThamSoService.java               # Business logic + Getter methods
├── controller/
│   └── ThamSoController.java            # REST API endpoints
├── dto/
│   ├── request/
│   │   ├── ThamSoCreationRequest.java
│   │   └── ThamSoUpdateRequest.java
│   └── response/
│       └── ThamSoResponse.java
├── mapper/
│   └── ThamSoMapper.java                # DTO Mapper
├── exception/
│   ├── ErrorCode.java                   # Error codes enum
│   ├── AppException.java                # Custom exception
│   └── GlobalExceptionHandler.java      # Exception handler
└── config/
    └── ThamSoDataSeeder.java            # Data seeding on startup
```

## ⚙️ Công nghệ sử dụng

- **Spring Boot 3.x** - Framework
- **Spring Data JPA** - ORM
- **Lombok** - Code generation
- **Transaction Management** - @Transactional
- **Exception Handling** - AppException + ErrorCode
- **Logging** - SLF4J

## 🛡️ Exception Handling

Module sử dụng exception handling với error codes:

| Error Code | HTTP Status | Message |
|-----------|------------|---------|
| 1000 | 200 OK | Operation successful |
| 6002 | 400 Bad Request | THAM_SO_EXISTED |
| 6003 | 404 Not Found | THAM_SO_NOT_FOUND |

**Error Response:**
```json
{
  "code": 6003,
  "message": "Parameter not found",
  "timestamp": "2026-04-22T10:30:00",
  "path": "/api/thamso/ThamSoKhongTonTai"
}
```

## 🧪 Testing

### Test với cURL

```bash
# Lấy tất cả tham số
curl -X GET http://localhost:8080/api/thamso

# Lấy một tham số
curl -X GET http://localhost:8080/api/thamso/TuoiToiThieu

# Cập nhật tham số
curl -X PUT http://localhost:8080/api/thamso/TuoiToiThieu \
  -H "Content-Type: application/json" \
  -d '{"giaTri": "21"}'

# Tạo tham số mới
curl -X POST http://localhost:8080/api/thamso \
  -H "Content-Type: application/json" \
  -d '{"tenThamSo": "SoPhutMuonToiDa", "giaTri": "60"}'

# Xóa tham số
curl -X DELETE http://localhost:8080/api/thamso/SoPhutMuonToiDa
```

### Test với Postman

Import postman-collection.json và chạy các request trong folder ThamSo.

## 📝 Notes

- Tất cả methods trong ThamSoService được đánh dấu `@Transactional`
- Read-only operations dùng `@Transactional(readOnly = true)`
- Getter methods tự động convert String sang Integer/Long/Boolean
- Nếu tham số không tồn tại, throw `AppException(ErrorCode.THAM_SO_NOT_FOUND)`
- Code tuân thủ Clean Code + SOLID principles

## 👨‍💻 Developers

- **Architecture:** Service Layer Pattern + DTO + Mapper
- **Code Style:** Lombok + @FieldDefaults
- **Exception Handling:** Custom AppException
- **Logging:** SLF4J with @Slf4j

---

**Last Updated:** April 22, 2026
