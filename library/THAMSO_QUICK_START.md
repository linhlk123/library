# ⚡ ThamSo Module - Quick Start Guide

## 🚀 Bắt Đầu Nhanh Chóng (5 Phút)

### Bước 1️⃣: Xác nhận Module Đã Cài Đặt ✓

Kiểm tra các file này tồn tại:
```
✓ src/main/java/com/lms/library/config/ThamSoDataSeeder.java
✓ src/main/java/com/lms/library/service/ThamSoService.java
✓ src/main/java/com/lms/library/controller/ThamSoController.java
✓ src/main/java/com/lms/library/exception/ErrorCode.java (cập nhật)
```

### Bước 2️⃣: Khởi Chạy Ứng Dụng

```bash
mvn spring-boot:run
```

**Nếu thành công, bạn sẽ thấy log:**
```
=============== Starting ThamSoDataSeeder ===============
Initializing ThamSo (Business Rules Parameters)...
✓ Successfully initialized 8 ThamSo records
  - TuoiToiThieu: 18
  - TuoiToiDa: 55
  - ThoiHanThe: 6 months
  - ...
=============== ThamSoDataSeeder completed ===============
```

### Bước 3️⃣: Test API

**Lấy tất cả tham số:**
```bash
curl -X GET http://localhost:8080/api/thamso
```

**Response:**
```json
{
  "code": 1000,
  "message": "Lấy danh sách tham số thành công",
  "result": [
    {"tenThamSo": "TuoiToiThieu", "giaTri": "18"},
    {"tenThamSo": "TuoiToiDa", "giaTri": "55"},
    ...
  ]
}
```

### Bước 4️⃣: Sử Dụng Trong Service Khác

```java
@Service
@RequiredArgsConstructor
public class MyService {
    private final ThamSoService thamSoService;
    
    public void validateAge(Integer age) {
        Integer minAge = thamSoService.getTuoiToiThieu();  // 18
        Integer maxAge = thamSoService.getTuoiToiDa();     // 55
        
        if (age < minAge || age > maxAge) {
            throw new IllegalArgumentException("Tuổi không hợp lệ");
        }
    }
}
```

---

## 📞 Câu Hỏi Thường Gặp

### Q1: Dữ liệu khởi tạo ở đâu?
**A:** File `ThamSoDataSeeder.java` - tự động chạy khi app startup

### Q2: Có thể chỉnh sửa tham số không?
**A:** Có! Dùng API:
```bash
curl -X PUT http://localhost:8080/api/thamso/TuoiToiThieu \
  -H "Content-Type: application/json" \
  -d '{"giaTri": "21"}'
```

### Q3: Cách lấy giá trị tham số từ service?
**A:** Dùng getter methods:
```java
Integer age = thamSoService.getTuoiToiThieu();
Boolean apDung = thamSoService.isApDungQDKiemTraSoTienThu();
```

### Q4: Nếu tham số không tồn tại?
**A:** Sẽ throw `AppException` với error code 6003 (NOT_FOUND)

### Q5: Tất cả 8 tham số mặc định là gì?
**A:** Xem bảng dưới:

| Tên | Giá trị | Ý nghĩa |
|-----|--------|--------|
| TuoiToiThieu | 18 | Min age |
| TuoiToiDa | 55 | Max age |
| ThoiHanThe | 6 | Card validity (months) |
| KhoangCachNamXB | 8 | Max book age (years) |
| SoNgayMuonToiDa | 4 | Max borrow days |
| SoSachMuonToiDa | 5 | Max books per borrow |
| TienPhatToiDa | 1000 | Max fine (VND/day) |
| ApDungQDKiemTraSoTienThu | 1 | Enable validation |

---

## 🔍 Debugging Tips

### Problem: Dữ liệu không được khởi tạo
**Solution:**
1. Kiểm tra bảng THAMSO có tồn tại không
2. Check log có lỗi không
3. Thử xóa dữ liệu cũ rồi restart

### Problem: "Parameter not found" error
**Solution:**
- Tham số chưa tồn tại trong DB
- Dùng GET /api/thamso để xem danh sách
- Tạo tham số mới nếu cần

### Problem: NumberFormatException khi gọi getter
**Solution:**
- Giá trị tham số không phải số
- Check DB xem giá trị có đúng định dạng không
- Ví dụ: ThoiHanThe phải là "6" chứ không phải "6 months"

---

## 📚 Tài Liệu Đầy Đủ

| Document | Nội dung |
|----------|---------|
| THAMSO_MODULE_README.md | Tài liệu chi tiết (API, examples, config) |
| THAMSO_CHANGES_SUMMARY.md | Tóm tắt các thay đổi |
| THAMSO_FILE_REFERENCE.md | Danh sách file chi tiết |
| ExampleServiceUsage.java | 8 ví dụ code thực tế |
| THAMSO_API_POSTMAN.json | Postman collection (8 requests) |
| test-thamso-api.sh | Bash script test (curl commands) |

---

## ⚙️ 5 Phút Setup Checklists

### Lần Đầu Setup
```
□ Download/pull code
□ Khởi chạy app: mvn spring-boot:run
□ Kiểm tra log có "Successfully initialized 8 ThamSo records"
□ Test GET /api/thamso → OK?
□ ✓ Done!
```

### Sử Dụng Trong Service
```
□ Inject ThamSoService vào service của bạn
□ Gọi getter method: thamSoService.getTuoiToiThieu()
□ Xử lý exception: try-catch hoặc let GlobalExceptionHandler xử lý
□ ✓ Done!
```

### Testing API
```
□ Mở Postman hoặc Terminal
□ Import THAMSO_API_POSTMAN.json (nếu dùng Postman)
□ Hoặc chạy test-thamso-api.sh
□ Test GET, PUT, POST, DELETE endpoints
□ ✓ Done!
```

---

## 🎯 Tiếp Theo

1. **Đọc Tài Liệu:**
   - THAMSO_MODULE_README.md → Chi tiết hơn

2. **Xem Ví Dụ:**
   - ExampleServiceUsage.java → 8 use cases thực tế

3. **Test API:**
   - Import Postman collection hoặc dùng bash script

4. **Tích Hợp:**
   - Thêm ThamSoService vào service khác
   - Dùng getter methods để lấy tham số

5. **Custom Tham Số:**
   - Có thể tạo thêm tham số mới bằng POST /api/thamso
   - Hoặc cập nhật tham số bằng PUT /api/thamso/{tenThamSo}

---

## 💡 Best Practices

✓ **Luôn dùng getter methods** thay vì query trực tiếp
```java
// ✓ GOOD
Integer age = thamSoService.getTuoiToiThieu();

// ✗ BAD
ThamSo ts = thamSoRepository.findById("TuoiToiThieu").get();
Integer age = Integer.parseInt(ts.getGiaTri());
```

✓ **Xử lý exception** khi gọi getter
```java
try {
    Integer age = thamSoService.getTuoiToiThieu();
} catch (AppException e) {
    log.error("Tham số không tồn tại", e);
}
```

✓ **Cache tham số** nếu sử dụng thường xuyên
```java
private Integer cachedMinAge = null;

public Integer getMinAge() {
    if (cachedMinAge == null) {
        cachedMinAge = thamSoService.getTuoiToiThieu();
    }
    return cachedMinAge;
}
```

✓ **Validate tham số** khi cập nhật
```java
if (newValue < 0 || newValue > 1000) {
    throw new IllegalArgumentException("Giá trị tham số không hợp lệ");
}
thamSoService.updateThamSo("ThoiHanThe", request);
```

---

## 🆘 Liên Hệ Support

Nếu gặp vấn đề:

1. Kiểm tra file `THAMSO_MODULE_README.md`
2. Xem ví dụ trong `ExampleServiceUsage.java`
3. Check log application
4. Verify database có 8 records
5. Test với Postman collection

---

**⏱️ Estimated Time:** 5 minutes
**Difficulty:** ⭐☆☆☆☆ (Very Easy)
**Status:** ✅ Ready to Use
