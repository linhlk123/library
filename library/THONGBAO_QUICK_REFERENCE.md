# 📢 ThongBao (Notification) - Quick Reference

## tl;dr

```bash
# Get my notifications
GET /api/thongbao/me

# Count unread
GET /api/thongbao/me/unread-count

# Mark 1 as read
PUT /api/thongbao/{id}/read

# Mark all as read
PUT /api/thongbao/read-all

# Create (admin)
POST /api/thongbao
{ "tieuDe": "...", "noiDung": "...", "loaiThongBao": "INFO", "nguoiNhan": "admin" }

# Broadcast
POST /api/thongbao/broadcast
{ "tieuDe": "...", "noiDung": "...", "loaiThongBao": "WARNING" }

# Delete
DELETE /api/thongbao/{id}
```

---

## API Endpoints

| Method | Endpoint | Purpose |
|--------|----------|---------|
| GET | `/api/thongbao/me` | 📋 My notifications |
| GET | `/api/thongbao/me/unread-count` | 🔢 Unread count |
| GET | `/api/thongbao/{id}` | 📄 Detail |
| POST | `/api/thongbao` | ✍️ Create |
| POST | `/api/thongbao/broadcast` | 📢 Broadcast |
| PUT | `/api/thongbao/{id}/read` | ✅ Mark 1 as read |
| PUT | `/api/thongbao/read-all` | ✅ Mark all as read |
| DELETE | `/api/thongbao/{id}` | 🗑️ Delete |

---

## Data Model

```
ThongBao {
  id: Long,
  tieuDe: String,
  noiDung: String,
  loaiThongBao: INFO | WARNING | SUCCESS,
  daDoc: Boolean (default: false),
  ngayTao: LocalDateTime,
  nguoiNhan: String (username or 'ALL')
}
```

---

## Key Features

✅ **Get my notifications**
- Includes personal + broadcast
- Sorted: newest first
- All (read + unread)

✅ **Mark as read**
- Single or all
- Idempotent (safe to call multiple times)

✅ **Unread count**
- Used for badge on UI

✅ **Broadcast**
- Send to all users (`nguoiNhan = 'ALL'`)

---

## Test Examples

### Get notifications
```bash
curl -X GET http://localhost:8080/api/thongbao/me \
  -H "Authorization: Bearer <token>"
```

### Mark all as read
```bash
curl -X PUT http://localhost:8080/api/thongbao/read-all \
  -H "Authorization: Bearer <token>"
```

### Create for user
```bash
curl -X POST http://localhost:8080/api/thongbao \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "tieuDe": "Sách mới",
    "noiDung": "Sách 'Clean Code' vừa được thêm",
    "loaiThongBao": "SUCCESS",
    "nguoiNhan": "admin"
  }'
```

### Broadcast
```bash
curl -X POST http://localhost:8080/api/thongbao/broadcast \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "tieuDe": "Bảo trì hệ thống",
    "noiDung": "Hệ thống bảo trì lúc 22:00",
    "loaiThongBao": "WARNING"
  }'
```

---

## Database Schema

```sql
CREATE TABLE THONGBAO (
    Id BIGINT PRIMARY KEY IDENTITY(1,1),
    TieuDe NVARCHAR(255) NOT NULL,
    NoiDung NVARCHAR(MAX),
    LoaiThongBao VARCHAR(50) NOT NULL,
    DaDoc BIT DEFAULT 0,
    NgayTao DATETIME DEFAULT GETDATE(),
    NguoiNhan NVARCHAR(255) NOT NULL
);

CREATE INDEX idx_nguoinhan_ngaytao ON THONGBAO(nguoi_nhan, ngay_tao DESC);
CREATE INDEX idx_nguoinhan_dadoc ON THONGBAO(nguoi_nhan, da_doc);
```

---

## Files Created

- `entity/ThongBao.java`
- `enums/LoaiThongBao.java`
- `repository/ThongBaoRepository.java`
- `service/ThongBaoService.java`
- `controller/ThongBaoController.java`
- `mapper/ThongBaoMapper.java`
- `dto/request/ThongBaoCreationRequest.java`
- `dto/response/ThongBaoResponse.java`

---

## Next Steps

1. ✅ Create database table
2. ✅ Run application
3. ✅ Test API endpoints
4. ✅ Integrate into UI (notification badge, dropdown)
5. ⏳ Setup scheduled deletion (old notifications)

---

**Status**: ✅ Production Ready | **Version**: 1.0 | **Date**: April 23, 2026
