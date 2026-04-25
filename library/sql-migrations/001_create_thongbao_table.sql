-- ThongBao (Notification) Database Migration
-- SQL Server T-SQL
-- Created: April 23, 2026

-- ============================================================================
-- 1. Create THONGBAO table
-- ============================================================================

IF OBJECT_ID('dbo.THONGBAO', 'U') IS NULL
BEGIN
    CREATE TABLE dbo.THONGBAO (
        Id BIGINT PRIMARY KEY IDENTITY(1,1),
        TieuDe NVARCHAR(255) NOT NULL,
        NoiDung NVARCHAR(MAX),
        LoaiThongBao VARCHAR(50) NOT NULL,      -- INFO, WARNING, SUCCESS
        DaDoc BIT DEFAULT 0,                     -- 0 = chưa đọc, 1 = đã đọc
        NgayTao DATETIME DEFAULT GETDATE(),      -- Tạo lúc
        NguoiNhan NVARCHAR(255) NOT NULL,       -- Username hoặc 'ALL' (broadcast)
        CONSTRAINT CK_LoaiThongBao CHECK (LoaiThongBao IN ('INFO', 'WARNING', 'SUCCESS')),
        CONSTRAINT CK_DaDoc CHECK (DaDoc IN (0, 1))
    );
    
    PRINT 'Table THONGBAO created successfully';
END
ELSE
BEGIN
    PRINT 'Table THONGBAO already exists';
END;

-- ============================================================================
-- 2. Create Indexes for Performance
-- ============================================================================

-- Index 1: For findByNguoiNhanOrderByNgayTaoDesc query
-- Used when: Getting all notifications for a user (newest first)
-- Columns: (NguoiNhan, NgayTao DESC) - Composite index
IF NOT EXISTS (SELECT name FROM sys.indexes WHERE name = 'idx_nguoinhan_ngaytao')
BEGIN
    CREATE INDEX idx_nguoinhan_ngaytao 
    ON dbo.THONGBAO(NguoiNhan ASC, NgayTao DESC);
    PRINT 'Index idx_nguoinhan_ngaytao created';
END
ELSE
BEGIN
    PRINT 'Index idx_nguoinhan_ngaytao already exists';
END;

-- Index 2: For countUnreadByNguoiNhan query
-- Used when: Counting unread notifications (WHERE DaDoc = 0)
-- Columns: (NguoiNhan, DaDoc) - Composite index
IF NOT EXISTS (SELECT name FROM sys.indexes WHERE name = 'idx_nguoinhan_dadoc')
BEGIN
    CREATE INDEX idx_nguoinhan_dadoc 
    ON dbo.THONGBAO(NguoiNhan ASC, DaDoc ASC);
    PRINT 'Index idx_nguoinhan_dadoc created';
END
ELSE
BEGIN
    PRINT 'Index idx_nguoinhan_dadoc already exists';
END;

-- Index 3: For markAllAsReadByNguoiNhan query (UPDATE)
-- Used when: Marking all notifications as read for a user
-- Note: Same as idx_nguoinhan_dadoc, so no additional index needed
PRINT 'Index strategy: idx_nguoinhan_dadoc covers UPDATE query';

-- ============================================================================
-- 3. Test Data (Optional - Comment out in production)
-- ============================================================================

-- PRINT 'Inserting test data...';
-- INSERT INTO dbo.THONGBAO (TieuDe, NoiDung, LoaiThongBao, DaDoc, NgayTao, NguoiNhan)
-- VALUES 
--     ('Thông báo 1', 'Nội dung thông báo 1', 'INFO', 0, GETDATE(), 'admin'),
--     ('Cảnh báo 1', 'Nội dung cảnh báo 1', 'WARNING', 0, DATEADD(HOUR, -1, GETDATE()), 'admin'),
--     ('Thành công 1', 'Nội dung thành công 1', 'SUCCESS', 1, DATEADD(HOUR, -2, GETDATE()), 'admin'),
--     ('Thông báo broadcast', 'Thông báo cho tất cả users', 'INFO', 0, GETDATE(), 'ALL');

-- ============================================================================
-- 4. Verification
-- ============================================================================

PRINT '';
PRINT '=== Verification ===';
PRINT '';

-- Check table exists
PRINT 'Table THONGBAO:';
SELECT TABLE_SCHEMA, TABLE_NAME, TABLE_TYPE 
FROM INFORMATION_SCHEMA.TABLES 
WHERE TABLE_NAME = 'THONGBAO';

-- Check indexes
PRINT '';
PRINT 'Indexes on THONGBAO:';
SELECT name, type_desc 
FROM sys.indexes 
WHERE object_id = OBJECT_ID('dbo.THONGBAO') AND name IS NOT NULL;

-- Check row count
PRINT '';
PRINT 'Row count:';
SELECT COUNT(*) as [Total Notifications] FROM dbo.THONGBAO;

PRINT '';
PRINT 'Migration completed successfully!';

-- ============================================================================
-- 5. Sample Queries for Testing
-- ============================================================================

/* 
-- Get all notifications for user 'admin' (newest first)
SELECT * FROM THONGBAO 
WHERE NguoiNhan = 'admin' OR NguoiNhan = 'ALL'
ORDER BY NgayTao DESC;

-- Count unread for user 'admin'
SELECT COUNT(*) as UnreadCount 
FROM THONGBAO 
WHERE (NguoiNhan = 'admin' OR NguoiNhan = 'ALL') AND DaDoc = 0;

-- Mark all as read for user 'admin'
UPDATE THONGBAO 
SET DaDoc = 1 
WHERE (NguoiNhan = 'admin' OR NguoiNhan = 'ALL') AND DaDoc = 0;

-- Delete old notifications (older than 30 days)
DELETE FROM THONGBAO 
WHERE NgayTao < DATEADD(DAY, -30, GETDATE());

*/

-- ============================================================================
-- 6. Cleanup (If needed - Comment out unless necessary)
-- ============================================================================

/*
-- Drop indexes
IF EXISTS (SELECT name FROM sys.indexes WHERE name = 'idx_nguoinhan_ngaytao')
    DROP INDEX idx_nguoinhan_ngaytao ON dbo.THONGBAO;

IF EXISTS (SELECT name FROM sys.indexes WHERE name = 'idx_nguoinhan_dadoc')
    DROP INDEX idx_nguoinhan_dadoc ON dbo.THONGBAO;

-- Drop table
IF OBJECT_ID('dbo.THONGBAO', 'U') IS NOT NULL
    DROP TABLE dbo.THONGBAO;
*/
