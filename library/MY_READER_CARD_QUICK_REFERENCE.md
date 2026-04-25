# "Thẻ Độc Giả Của Tôi" - Quick Reference Guide

## 🚀 Quick Start

### API Endpoint
```
GET /api/docgia/my-card
```

### How to Test with Postman

1. **Get JWT Token**
   - Send POST to `/api/v1/auth/token` with username and password
   - Copy the `token` value

2. **Call My Card Endpoint**
   ```
   GET http://localhost:8080/api/docgia/my-card
   Header: Authorization: Bearer <YOUR_JWT_TOKEN>
   ```

3. **Expected Response**
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
       "tongNo": 15000.00,
       "cardValid": true,
       "cardStatus": "VALID"
     }
   }
   ```

---

## 📁 Files Changed

### Created
- ✨ `src/main/java/com/lms/library/dto/response/MyReaderCardResponse.java`

### Modified
- 📝 `src/main/java/com/lms/library/service/DocGiaService.java`
  - Added `getMyReaderCard()` method
  - Added import: `SecurityContextHolder`
  - Added import: `MyReaderCardResponse`

- 📝 `src/main/java/com/lms/library/controller/DocGiaController.java`
  - Added `@GetMapping("/my-card")` endpoint
  - Added import: `MyReaderCardResponse`

---

## 🔑 Key Methods

### DocGiaService.getMyReaderCard()
```java
public MyReaderCardResponse getMyReaderCard()
```
- **Purpose**: Get current user's reader card info
- **Returns**: `MyReaderCardResponse` with all card details
- **Throws**: RuntimeException if user has no reader card
- **Data Source**: Direct database query

### DocGiaController.getMyReaderCard()
```java
@GetMapping("/my-card")
public ApiResponse<MyReaderCardResponse> getMyReaderCard()
```
- **Purpose**: HTTP endpoint to get my reader card
- **Method**: GET
- **Path**: `/api/docgia/my-card`
- **Auth**: Requires JWT token
- **Response**: ApiResponse wrapper with MyReaderCardResponse

---

## 📊 Response Field Meanings

| Field | Source | Description |
|-------|--------|-------------|
| `maDocGia` | DocGia.maDocGia | Username (reader ID) |
| `tenDocGia` | NguoiDung.hoTen | Reader's full name |
| `tenLoaiDocGia` | LoaiDocGia.tenLoaiDocGia | Card type (e.g., "Thẻ Bạc") |
| `ngayLapThe` | DocGia.ngayLapThe | Date card was issued |
| `ngayHetHan` | DocGia.ngayHetHan | Card expiration date |
| `tongNo` | DocGia.tongNo | Outstanding fine amount |
| `cardValid` | Computed | Is card valid? (expires >= today) |
| `cardStatus` | Computed | Status: VALID / EXPIRED / PENDING_FEES |

---

## 🎯 Card Status Rules

```
IF ngayHetHan < today
  → cardStatus = "EXPIRED"
  → cardValid = false

ELSE IF tongNo > 0
  → cardStatus = "PENDING_FEES"
  → cardValid = true

ELSE
  → cardStatus = "VALID"
  → cardValid = true
```

---

## 🔐 Authentication Flow

```
1. User sends JWT token in Authorization header
   ↓
2. Spring Security validates JWT
   ↓
3. SecurityContextHolder.getContext().getAuthentication().getName()
   extracts username from token
   ↓
4. DocGiaRepository.findByTenDangNhap(username)
   queries database
   ↓
5. MyReaderCardResponse is built and returned
```

---

## ⚠️ Error Handling

### 401 Unauthorized
- **Cause**: No JWT token or invalid token
- **Solution**: Login first, get token from `/api/v1/auth/token`

### Exception: "Tài khoản của bạn chưa được liên kết..."
- **Cause**: User account exists but has no reader card
- **Solution**: User must go to library to register for reader card

### 500 Internal Server Error
- **Cause**: Database error or unexpected issue
- **Check**: Server logs for detailed error

---

## 🧪 Test Cases

### Test Case 1: Valid User with Valid Card
```
Input: Valid JWT token for user with active reader card
Expected: 200 OK with card details, cardStatus = "VALID"
```

### Test Case 2: Valid User with Expired Card
```
Input: Valid JWT token for user with expired reader card
Expected: 200 OK with card details, cardStatus = "EXPIRED", cardValid = false
```

### Test Case 3: Valid User with Unpaid Fines
```
Input: Valid JWT token for user with active card but tongNo > 0
Expected: 200 OK with card details, cardStatus = "PENDING_FEES", cardValid = true
```

### Test Case 4: Valid User, No Reader Card
```
Input: Valid JWT token for user without reader card
Expected: RuntimeException → Error message in response
```

### Test Case 5: No Authentication
```
Input: No JWT token (or invalid token)
Expected: 401 Unauthorized
```

---

## 📝 Code Examples

### Using curl
```bash
# Get token
TOKEN=$(curl -X POST http://localhost:8080/api/v1/auth/token \
  -H "Content-Type: application/json" \
  -d '{"tenDangNhap":"john_doe","matKhau":"password"}' \
  | jq -r '.result.token')

# Get card info
curl -X GET http://localhost:8080/api/docgia/my-card \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  | jq
```

### Using JavaScript/Fetch
```javascript
const token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...";

const response = await fetch('http://localhost:8080/api/docgia/my-card', {
    method: 'GET',
    headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
    }
});

const data = await response.json();
console.log(data.result);
```

### Using Java RestTemplate
```java
RestTemplate restTemplate = new RestTemplate();
HttpHeaders headers = new HttpHeaders();
headers.setBearerAuth(token);

HttpEntity<Void> entity = new HttpEntity<>(headers);
ResponseEntity<ApiResponse<MyReaderCardResponse>> response = 
    restTemplate.exchange(
        "http://localhost:8080/api/docgia/my-card",
        HttpMethod.GET,
        entity,
        new ParameterizedTypeReference<ApiResponse<MyReaderCardResponse>>() {}
    );

MyReaderCardResponse card = response.getBody().getResult();
```

---

## 📚 Related Files & Concepts

### Related Repositories
- `DocGiaRepository` - Access DocGia data
- `NguoiDungRepository` - Access user data (not directly called)
- `LoaiDocGiaRepository` - Access card types (not directly called)

### Related Entities
- `DocGia` - Reader entity (main data source)
- `NguoiDung` - User entity (joined via OneToOne)
- `LoaiDocGia` - Card type entity (joined via ManyToOne)

### Related DTOs
- `MyReaderCardResponse` - Response DTO (NEW)
- `DocGiaResponse` - Full DocGia response (existing)
- `ApiResponse<T>` - Generic wrapper (existing)

### Related Controllers
- `DocGiaController` - CRUD + my-card endpoint

### Related Services
- `DocGiaService` - Business logic + my-card logic

---

## 🎓 Learning Points

1. **SecurityContextHolder**: How to get current authenticated user in Spring
2. **@MapsId**: Understanding how one-to-one relationships work with shared primary keys
3. **Custom Queries**: Using @Query to write JPQL queries
4. **Computed Fields**: Adding business logic to DTOs
5. **REST Best Practices**: Using `/my-` prefix for current user resources

---

## ✅ Deployment Checklist

- [ ] Code compiles without errors
- [ ] All tests pass (unit + integration)
- [ ] Tested with multiple user accounts
- [ ] Tested with expired cards
- [ ] Tested with cards having fines
- [ ] Tested without authentication token
- [ ] Tested with invalid tokens
- [ ] Postman collection updated
- [ ] API documentation updated
- [ ] Frontend ready to consume endpoint

---

**Need help?** Check the implementation verification document or server logs!
