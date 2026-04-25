# 📦 Danh sách File Module Quản lý Tham Số (ThamSo)

## 🎯 Core Files (Bắt buộc)

### Entity Layer
```
src/main/java/com/lms/library/entity/
└── ThamSo.java
    - @Entity @Table(name = "THAMSO")
    - @Id: tenThamSo (String, 100)
    - Column: giaTri (String, 100)
    - Lombok: @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
```

### Repository Layer
```
src/main/java/com/lms/library/repository/
└── ThamSoRepository.java
    - Extends JpaRepository<ThamSo, String>
    - Cung cấp: findById, save, delete, existsById, count
```

### Service Layer
```
src/main/java/com/lms/library/service/
└── ThamSoService.java ⭐ [UPDATED]
    - CRUD Operations: createThamSo, getAllThamSo, getThamSoById, updateThamSo, deleteThamSo
    - Getter Methods: getTuoiToiThieu, getTuoiToiDa, getThoiHanThe, getKhoangCachNamXB,
                      getSoNgayMuonToiDa, getSoSachMuonToiDa, getTienPhatToiDa,
                      isApDungQDKiemTraSoTienThu
    - All methods marked with @Transactional
    - Exception handling with AppException + ErrorCode
```

### Controller Layer
```
src/main/java/com/lms/library/controller/
└── ThamSoController.java
    - GET /api/thamso
    - GET /api/thamso/{tenThamSo}
    - POST /api/thamso
    - PUT /api/thamso/{tenThamSo}
    - DELETE /api/thamso/{tenThamSo}
    - All methods return ApiResponse<T>
```

### DTO Layer
```
src/main/java/com/lms/library/dto/
├── request/
│   ├── ThamSoCreationRequest.java
│   │   - tenThamSo: String
│   │   - giaTri: String
│   └── ThamSoUpdateRequest.java
│       - giaTri: String
└── response/
    └── ThamSoResponse.java
        - tenThamSo: String
        - giaTri: String
```

### Mapper Layer
```
src/main/java/com/lms/library/mapper/
└── ThamSoMapper.java
    - toThamSoResponse(ThamSo) → ThamSoResponse
```

### Exception Layer
```
src/main/java/com/lms/library/exception/
├── ErrorCode.java ⭐ [UPDATED]
│   - THAM_SO_EXISTED(6002, ...)
│   - THAM_SO_NOT_FOUND(6003, ...)
├── AppException.java
│   - Custom exception với ErrorCode
└── GlobalExceptionHandler.java
    - Centralized exception handling
```

## 🔧 Configuration Files (New)

### Data Seeding
```
src/main/java/com/lms/library/config/
└── ThamSoDataSeeder.java ⭐ [NEW]
    - Implements CommandLineRunner
    - Runs on application startup
    - Initializes 8 default ThamSo records if table is empty
    - Logging with @Slf4j
```

## 📚 Documentation Files

### README & Documentation
```
Project Root (library/)
├── THAMSO_MODULE_README.md ⭐ [NEW]
│   - Comprehensive module documentation
│   - API endpoints with examples
│   - Getter methods reference
│   - Testing guide
│   - Exception handling
│   - Technology stack
│
├── THAMSO_CHANGES_SUMMARY.md ⭐ [NEW]
│   - Summary of all changes
│   - Requirements completion checklist
│   - Detailed file structure
│   - Usage examples
│
├── THAMSO_API_POSTMAN.json ⭐ [NEW]
│   - Postman collection
│   - 8 pre-configured requests for testing
│   - Example with variable base_url
│
└── test-thamso-api.sh ⭐ [NEW]
    - Bash script for API testing
    - curl commands for all endpoints
```

## 💡 Example Files

### Usage Examples
```
src/main/java/com/lms/library/service/example/
└── ExampleServiceUsage.java ⭐ [NEW]
    - 8 practical examples
    - How to use getter methods in other services
    - Example 1: Age validation
    - Example 2: Borrow days validation
    - Example 3: Book quantity validation
    - Example 4: Publication year validation
    - Example 5: Late fee calculation
    - Example 6: Payment processing
    - Example 7: Library card expiration
    - Example 8: Complex borrowing rules validation
```

## 📊 Data Model

### Database Table
```sql
CREATE TABLE THAMSO (
    TenThamSo VARCHAR(100) PRIMARY KEY,
    GiaTri VARCHAR(100) NOT NULL
);
```

### Default Records (8 records)
```
1. TenThamSo: "TuoiToiThieu"               | GiaTri: "18"
2. TenThamSo: "TuoiToiDa"                  | GiaTri: "55"
3. TenThamSo: "ThoiHanThe"                 | GiaTri: "6"
4. TenThamSo: "KhoangCachNamXB"            | GiaTri: "8"
5. TenThamSo: "SoNgayMuonToiDa"            | GiaTri: "4"
6. TenThamSo: "SoSachMuonToiDa"            | GiaTri: "5"
7. TenThamSo: "TienPhatToiDa"              | GiaTri: "1000"
8. TenThamSo: "ApDungQDKiemTraSoTienThu"  | GiaTri: "1"
```

## 🔗 Dependencies

### Maven Dependencies (Already included)
- Spring Boot Data JPA
- Lombok
- Jakarta Persistence API
- SLF4J Logging

### Internal Dependencies
- com.lms.library.exception.AppException
- com.lms.library.exception.ErrorCode
- com.lms.library.dto.request.ApiResponse
- com.lms.library.config.ThamSoDataSeeder

## 🚀 API Endpoints Summary

| Method | Endpoint | Status Code | Response Type |
|--------|----------|-------------|---------------|
| GET | /api/thamso | 200 | ApiResponse<List<ThamSoResponse>> |
| GET | /api/thamso/{tenThamSo} | 200/404 | ApiResponse<ThamSoResponse> |
| POST | /api/thamso | 200/400 | ApiResponse<ThamSoResponse> |
| PUT | /api/thamso/{tenThamSo} | 200/404 | ApiResponse<ThamSoResponse> |
| DELETE | /api/thamso/{tenThamSo} | 200/404 | ApiResponse<String> |

## 📋 Getter Methods Reference

```java
// Integer type getters
Integer getTuoiToiThieu()          // Min age
Integer getTuoiToiDa()             // Max age
Integer getThoiHanThe()            // Card validity (months)
Integer getKhoangCachNamXB()       // Max book age (years)
Integer getSoNgayMuonToiDa()       // Max borrow days
Integer getSoSachMuonToiDa()       // Max books per borrow

// Long type getter
Long getTienPhatToiDa()            // Max fine (VND/day)

// Boolean type getter
Boolean isApDungQDKiemTraSoTienThu() // Enable payment validation
```

## 🔐 Exception Codes

| Code | HTTP Status | Message |
|------|------------|---------|
| 6002 | 400 | THAM_SO_EXISTED - Parameter already exists |
| 6003 | 404 | THAM_SO_NOT_FOUND - Parameter not found |

## 📈 Development Flow

```
1. Application Startup
   ↓
2. ThamSoDataSeeder.run() executes
   ↓
3. Check if THAMSO table has data
   ├─ If NO: Insert 8 default records
   └─ If YES: Skip seeding
   ↓
4. Application Ready
   ↓
5. ThamSoService available for other services
   ↓
6. Getter methods can be called: thamSoService.getTuoiToiThieu()
```

## ✅ Quality Checklist

- ✓ Clean Code (Lombok, @FieldDefaults, Java naming conventions)
- ✓ SOLID Principles (Single responsibility, Dependency injection)
- ✓ Transaction Management (@Transactional on all service methods)
- ✓ Exception Handling (AppException + ErrorCode)
- ✓ Logging (SLF4J with @Slf4j)
- ✓ API Response Standardization (ApiResponse<T>)
- ✓ Read-only Optimization (@Transactional(readOnly=true))
- ✓ Documentation (Comprehensive comments + external docs)
- ✓ Type Safety (Automatic String → Integer/Long/Boolean conversion)
- ✓ Data Seeding (Auto-initialization on startup)

## 🎓 Learning Resources

1. **How to use Getter Methods:**
   - See: `ExampleServiceUsage.java`
   - Includes 8 practical examples

2. **How to Test API:**
   - Use: `test-thamso-api.sh`
   - Or: Import `THAMSO_API_POSTMAN.json` into Postman

3. **Complete Documentation:**
   - Read: `THAMSO_MODULE_README.md`
   - Summary: `THAMSO_CHANGES_SUMMARY.md`

## 📝 File Statistics

- **Core Files:** 8 (Entity, Repository, Service, Controller, DTOs, Mapper)
- **Configuration Files:** 1 (ThamSoDataSeeder)
- **Exception Files:** 2 (ErrorCode updated, AppException)
- **Example Files:** 1 (ExampleServiceUsage)
- **Documentation:** 4 (README, Summary, Postman, this file)
- **Test Scripts:** 1 (bash script)

**Total New/Updated Files:** 17

---

**Last Updated:** April 22, 2026
**Module Status:** ✅ Production Ready
