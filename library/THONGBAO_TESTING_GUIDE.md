# 📢 ThongBao Module - Testing Guide

## Prerequisites

- ✅ Application built and running (`mvn spring-boot:run`)
- ✅ Database migration executed
- ✅ Valid JWT token for testing (e.g., from login endpoint)
- ✅ Postman or cURL installed

---

## Environment Setup

```bash
# Set environment variables
BASE_URL=http://localhost:8080
TOKEN=<your-jwt-token-here>
```

---

## Test Scenarios

### ✅ Scenario 1: Create Personal Notification

**Endpoint:** `POST /api/thongbao`

**cURL:**
```bash
curl -X POST http://localhost:8080/api/thongbao \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "tieuDe": "Sách mới được thêm",
    "noiDung": "Sách \"Clean Code\" vừa được thêm vào hệ thống",
    "loaiThongBao": "SUCCESS",
    "nguoiNhan": "admin"
  }'
```

**Expected Response (200 OK):**
```json
{
  "code": 1000,
  "message": "Tạo thông báo thành công",
  "result": {
    "id": 1,
    "tieuDe": "Sách mới được thêm",
    "noiDung": "Sách \"Clean Code\" vừa được thêm vào hệ thống",
    "loaiThongBao": "SUCCESS",
    "daDoc": false,
    "ngayTao": "2026-04-23T15:00:00",
    "nguoiNhan": "admin"
  }
}
```

**Verify:**
- Response code: 1000 (success)
- `daDoc` = false (not read)
- `ngayTao` = current time

---

### ✅ Scenario 2: Create Broadcast Notification

**Endpoint:** `POST /api/thongbao/broadcast`

**cURL:**
```bash
curl -X POST http://localhost:8080/api/thongbao/broadcast \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "tieuDe": "Bảo trì hệ thống",
    "noiDung": "Hệ thống sẽ bảo trì vào 22:00 hôm nay",
    "loaiThongBao": "WARNING"
  }'
```

**Expected Response (200 OK):**
```json
{
  "code": 1000,
  "message": "Tạo thông báo thành công",
  "result": {
    "id": 2,
    "tieuDe": "Bảo trì hệ thống",
    "noiDung": "Hệ thống sẽ bảo trì vào 22:00 hôm nay",
    "loaiThongBao": "WARNING",
    "daDoc": false,
    "ngayTao": "2026-04-23T15:02:00",
    "nguoiNhan": "ALL"
  }
}
```

**Verify:**
- `nguoiNhan` = "ALL" (broadcast)
- Available to all logged-in users

---

### ✅ Scenario 3: Get My Notifications

**Endpoint:** `GET /api/thongbao/me`

**cURL:**
```bash
curl -X GET http://localhost:8080/api/thongbao/me \
  -H "Authorization: Bearer $TOKEN"
```

**Expected Response (200 OK):**
```json
{
  "code": 1000,
  "message": "Lấy danh sách thông báo thành công",
  "result": [
    {
      "id": 2,
      "tieuDe": "Bảo trì hệ thống",
      "noiDung": "Hệ thống sẽ bảo trì vào 22:00 hôm nay",
      "loaiThongBao": "WARNING",
      "daDoc": false,
      "ngayTao": "2026-04-23T15:02:00",
      "nguoiNhan": "ALL"
    },
    {
      "id": 1,
      "tieuDe": "Sách mới được thêm",
      "noiDung": "Sách \"Clean Code\" vừa được thêm vào hệ thống",
      "loaiThongBao": "SUCCESS",
      "daDoc": false,
      "ngayTao": "2026-04-23T15:00:00",
      "nguoiNhan": "admin"
    }
  ]
}
```

**Verify:**
- Sorted by `ngayTao` DESC (newest first)
- Includes broadcast (nguoiNhan = 'ALL')
- Includes personal (nguoiNhan = username)

---

### ✅ Scenario 4: Get Unread Count

**Endpoint:** `GET /api/thongbao/me/unread-count`

**cURL:**
```bash
curl -X GET http://localhost:8080/api/thongbao/me/unread-count \
  -H "Authorization: Bearer $TOKEN"
```

**Expected Response (200 OK):**
```json
{
  "code": 1000,
  "message": "Đếm thông báo chưa đọc thành công",
  "result": {
    "unreadCount": 2
  }
}
```

**Verify:**
- Count = 2 (both notifications are unread)
- Used for notification badge on UI

---

### ✅ Scenario 5: Mark Single Notification as Read

**Endpoint:** `PUT /api/thongbao/{id}/read`

**cURL:**
```bash
curl -X PUT http://localhost:8080/api/thongbao/1/read \
  -H "Authorization: Bearer $TOKEN"
```

**Expected Response (200 OK):**
```json
{
  "code": 1000,
  "message": "Đánh dấu thông báo đã đọc thành công",
  "result": null
}
```

**Verify:**
- Notification ID=1 now has `daDoc = true`
- Get `/api/thongbao/me/unread-count` should return 1

---

### ✅ Scenario 6: Check Updated Unread Count

**Endpoint:** `GET /api/thongbao/me/unread-count`

**cURL:**
```bash
curl -X GET http://localhost:8080/api/thongbao/me/unread-count \
  -H "Authorization: Bearer $TOKEN"
```

**Expected Response (200 OK):**
```json
{
  "code": 1000,
  "message": "Đếm thông báo chưa đọc thành công",
  "result": {
    "unreadCount": 1
  }
}
```

**Verify:**
- Unread count decreased from 2 to 1

---

### ✅ Scenario 7: Mark All Notifications as Read

**Endpoint:** `PUT /api/thongbao/read-all`

**cURL:**
```bash
curl -X PUT http://localhost:8080/api/thongbao/read-all \
  -H "Authorization: Bearer $TOKEN"
```

**Expected Response (200 OK):**
```json
{
  "code": 1000,
  "message": "Đánh dấu tất cả thông báo đã đọc thành công",
  "result": {
    "markedCount": 1
  }
}
```

**Verify:**
- `markedCount` = 1 (only unread notifications are marked)
- Unread count now 0

---

### ✅ Scenario 8: Verify All Marked as Read

**Endpoint:** `GET /api/thongbao/me/unread-count`

**cURL:**
```bash
curl -X GET http://localhost:8080/api/thongbao/me/unread-count \
  -H "Authorization: Bearer $TOKEN"
```

**Expected Response (200 OK):**
```json
{
  "code": 1000,
  "message": "Đếm thông báo chưa đọc thành công",
  "result": {
    "unreadCount": 0
  }
}
```

---

### ✅ Scenario 9: Get Single Notification Detail

**Endpoint:** `GET /api/thongbao/{id}`

**cURL:**
```bash
curl -X GET http://localhost:8080/api/thongbao/1 \
  -H "Authorization: Bearer $TOKEN"
```

**Expected Response (200 OK):**
```json
{
  "code": 1000,
  "message": "Lấy thông báo thành công",
  "result": {
    "id": 1,
    "tieuDe": "Sách mới được thêm",
    "noiDung": "Sách \"Clean Code\" vừa được thêm vào hệ thống",
    "loaiThongBao": "SUCCESS",
    "daDoc": true,
    "ngayTao": "2026-04-23T15:00:00",
    "nguoiNhan": "admin"
  }
}
```

**Verify:**
- `daDoc` = true (marked as read in previous step)

---

### ✅ Scenario 10: Delete Notification

**Endpoint:** `DELETE /api/thongbao/{id}`

**cURL:**
```bash
curl -X DELETE http://localhost:8080/api/thongbao/1 \
  -H "Authorization: Bearer $TOKEN"
```

**Expected Response (200 OK):**
```json
{
  "code": 1000,
  "message": "Xóa thông báo thành công",
  "result": null
}
```

**Verify:**
- Notification ID=1 no longer exists
- Get `/api/thongbao/1` should return 5000 error

---

### ❌ Error Scenario 1: Get Deleted Notification

**Endpoint:** `GET /api/thongbao/1` (after deletion)

**cURL:**
```bash
curl -X GET http://localhost:8080/api/thongbao/1 \
  -H "Authorization: Bearer $TOKEN"
```

**Expected Response (404 / 5000):**
```json
{
  "code": 5000,
  "message": "Không tìm thấy thông báo",
  "result": null
}
```

---

### ❌ Error Scenario 2: Invalid Notification Type

**Endpoint:** `POST /api/thongbao`

**cURL:**
```bash
curl -X POST http://localhost:8080/api/thongbao \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "tieuDe": "Test",
    "noiDung": "Test",
    "loaiThongBao": "INVALID",
    "nguoiNhan": "admin"
  }'
```

**Expected Response (400):**
```json
{
  "code": 5000,
  "message": "Invalid notification type",
  "result": null
}
```

---

### ❌ Error Scenario 3: Missing Token

**Endpoint:** `GET /api/thongbao/me` (no token)

**cURL:**
```bash
curl -X GET http://localhost:8080/api/thongbao/me
```

**Expected Response (401):**
```json
{
  "code": 4010,
  "message": "Unauthorized",
  "result": null
}
```

---

## Postman Collection

Import this JSON into Postman:

```json
{
  "info": {
    "name": "ThongBao API",
    "version": "1.0"
  },
  "item": [
    {
      "name": "Get My Notifications",
      "request": {
        "method": "GET",
        "url": "{{BASE_URL}}/api/thongbao/me",
        "header": [
          {
            "key": "Authorization",
            "value": "Bearer {{TOKEN}}"
          }
        ]
      }
    },
    {
      "name": "Create Notification",
      "request": {
        "method": "POST",
        "url": "{{BASE_URL}}/api/thongbao",
        "header": [
          {
            "key": "Authorization",
            "value": "Bearer {{TOKEN}}"
          },
          {
            "key": "Content-Type",
            "value": "application/json"
          }
        ],
        "body": {
          "mode": "raw",
          "raw": "{\"tieuDe\":\"Sách mới\",\"noiDung\":\"Sách mới được thêm\",\"loaiThongBao\":\"SUCCESS\",\"nguoiNhan\":\"admin\"}"
        }
      }
    },
    {
      "name": "Broadcast Notification",
      "request": {
        "method": "POST",
        "url": "{{BASE_URL}}/api/thongbao/broadcast",
        "header": [
          {
            "key": "Authorization",
            "value": "Bearer {{TOKEN}}"
          },
          {
            "key": "Content-Type",
            "value": "application/json"
          }
        ],
        "body": {
          "mode": "raw",
          "raw": "{\"tieuDe\":\"Bảo trì\",\"noiDung\":\"Hệ thống bảo trì\",\"loaiThongBao\":\"WARNING\"}"
        }
      }
    },
    {
      "name": "Mark as Read",
      "request": {
        "method": "PUT",
        "url": "{{BASE_URL}}/api/thongbao/1/read",
        "header": [
          {
            "key": "Authorization",
            "value": "Bearer {{TOKEN}}"
          }
        ]
      }
    },
    {
      "name": "Mark All as Read",
      "request": {
        "method": "PUT",
        "url": "{{BASE_URL}}/api/thongbao/read-all",
        "header": [
          {
            "key": "Authorization",
            "value": "Bearer {{TOKEN}}"
          }
        ]
      }
    },
    {
      "name": "Get Unread Count",
      "request": {
        "method": "GET",
        "url": "{{BASE_URL}}/api/thongbao/me/unread-count",
        "header": [
          {
            "key": "Authorization",
            "value": "Bearer {{TOKEN}}"
          }
        ]
      }
    },
    {
      "name": "Delete Notification",
      "request": {
        "method": "DELETE",
        "url": "{{BASE_URL}}/api/thongbao/1",
        "header": [
          {
            "key": "Authorization",
            "value": "Bearer {{TOKEN}}"
          }
        ]
      }
    }
  ]
}
```

---

## Test Execution Checklist

- [ ] ✅ Scenario 1: Create personal notification
- [ ] ✅ Scenario 2: Create broadcast notification
- [ ] ✅ Scenario 3: Get all notifications (verify sort & broadcast)
- [ ] ✅ Scenario 4: Get unread count (should be 2)
- [ ] ✅ Scenario 5: Mark single as read
- [ ] ✅ Scenario 6: Verify unread count (should be 1)
- [ ] ✅ Scenario 7: Mark all as read
- [ ] ✅ Scenario 8: Verify unread count (should be 0)
- [ ] ✅ Scenario 9: Get notification detail
- [ ] ✅ Scenario 10: Delete notification
- [ ] ❌ Error 1: Get deleted notification (404)
- [ ] ❌ Error 2: Invalid type
- [ ] ❌ Error 3: Missing token (401)

---

## Performance Testing

```sql
-- Insert 1000 test notifications
INSERT INTO THONGBAO (TieuDe, NoiDung, LoaiThongBao, DaDoc, NgayTao, NguoiNhan)
SELECT 
    'Notification ' + CAST(ROW_NUMBER() OVER (ORDER BY object_id) AS VARCHAR),
    'Test notification content',
    CASE WHEN ROW_NUMBER() OVER (ORDER BY object_id) % 3 = 0 THEN 'INFO'
         WHEN ROW_NUMBER() OVER (ORDER BY object_id) % 3 = 1 THEN 'WARNING'
         ELSE 'SUCCESS' END,
    0,
    DATEADD(MINUTE, -ROW_NUMBER() OVER (ORDER BY object_id), GETDATE()),
    'admin'
FROM sys.objects
LIMIT 1000;

-- Test query performance
SET STATISTICS TIME ON;
SELECT * FROM THONGBAO 
WHERE (NguoiNhan = 'admin' OR NguoiNhan = 'ALL')
ORDER BY NgayTao DESC;
SET STATISTICS TIME OFF;
```

**Expected Performance:**
- Get notifications: < 50ms
- Count unread: < 10ms
- Mark all as read: < 20ms

---

## Troubleshooting

| Issue | Solution |
|-------|----------|
| 401 Unauthorized | Check JWT token expiry and refresh if needed |
| 5000 Notification not found | Verify notification ID exists in database |
| 400 Bad Request | Validate request JSON format and required fields |
| Database connection failed | Check SQL Server connection and THONGBAO table exists |
| Index not working | Verify indexes created with `SELECT * FROM sys.indexes` |

---

**Testing Status**: Ready for Execution
**Last Updated**: April 23, 2026
