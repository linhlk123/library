# Load and update Postman collection with realistic request bodies
$path = 'c:/Users/TGDD/Downloads/library/library/postman-collection.json'
$json = Get-Content -Raw $path | ConvertFrom-Json

# Define sample data for each entity type
$sampleData = @{
    'LoaiDocGia' = '{"tenLoaiDocGia": "Student"}'
    'DocGia' = '{"maDocGia": "DG001", "maLoaiDocGia": 1, "hoTen": "John Doe", "ngaySinh": "1990-01-15", "diaChi": "123 Main St", "email": "john@example.com", "matKhau": "password123", "ngayLapThe": "2024-01-01", "ngayHetHan": "2025-01-01", "tongNo": 0}'
    'ThamSo' = '{"tenThamSo": "MAX_LOAN_DAYS", "giaTri": "30"}'
    'TheLoai' = '{"tenTheLoai": "Fiction"}'
    'TacGia' = '{"tenTacGia": "John Smith", "ngaySinh": "1950-10-25"}'
    'DauSach' = '{"tenDauSach": "Sample Book"}'
    'Sach' = '{"maDauSach": 1, "maTheLoai": 1, "nhaXuatBan": "Publisher Inc", "namXuatBan": 2020, "soLuong": 10, "giaTien": 150000}'
    'CuonSach' = '{"maSach": 1, "tinhTrang": "Good"}'
    'CT_TacGia' = '{"maDauSach": 1, "maTacGia": 1}'
    'PhieuMuonTra' = '{"maCuonSach": 1, "maDocGia": "DG001", "ngayMuon": "2024-04-19", "ngayPhaiTra": "2024-05-19", "soNgayMuon": 30, "tienPhat": 0, "maNhanVien": "staff"}'
    'PhieuThuTienPhat' = '{"maDocGia": "DG001", "ngayThu": "2024-04-19", "soTienThu": 50000, "conLai": 100000}'
    'BaoCaoTinhHinhMuonSach' = '{"thang": 4, "nam": 2024, "tongSoLuotMuon": 50}'
    'CTBC_THMS' = '{"maBCTHMS": 1, "maTheLoai": 1, "soLuotMuon": 25, "tiLe": 50}'
    'BC_SachTraTre' = '{"ngay": "2024-04-19", "maCuonSach": 1, "ngayMuon": "2024-03-19", "soNgayTraTre": 31}'
}

function Walk($items) {
    foreach($it in $items) {
        if($it.item) {
            Walk $it.item
        }
        if($it.request -and $it.request.body -and $it.request.body.mode -eq 'raw' -and $it.request.body.raw -eq '{}') {
            # Determine entity type from request name with more precise matching
            $name = $it.name
            $matched = $false
            
            # Try exact name first (highest priority)
            foreach($key in @('LoaiDocGia', 'BaoCaoTinhHinhMuonSach', 'BC_SachTraTre', 'CTBC_THMS', 'CT_TacGia', 'PhieuMuonTra', 'PhieuThuTienPhat', 'CuonSach', 'DauSach', 'Sach', 'DocGia', 'ThamSo', 'TheLoai', 'TacGia')) {
                if($name -like "*$key*" -or $name -contains $key) {
                    $it.request.body.raw = $sampleData[$key]
                    $matched = $true
                    break
                }
            }
        }
    }
}

Walk $json.item

# Save updated collection
$json | ConvertTo-Json -Depth 100 | Out-File -Encoding UTF8 $path
Write-Host "Postman collection updated with sample request bodies"
