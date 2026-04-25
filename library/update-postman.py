import json
import re

# Load collection
with open('postman-collection.json', 'r', encoding='utf-8-sig') as f:
    collection = json.load(f)

# Sample data for each entity
sample_data = {
    'LoaiDocGia': '{"tenLoaiDocGia": "Student"}',
    'DocGia': '{"maDocGia": "DG001", "maLoaiDocGia": 1, "hoTen": "John Doe", "ngaySinh": "1990-01-15", "diaChi": "123 Main St", "email": "john@example.com", "matKhau": "password123", "ngayLapThe": "2024-01-01", "ngayHetHan": "2025-01-01", "tongNo": 0}',
    'ThamSo': '{"tenThamSo": "MAX_LOAN_DAYS", "giaTri": "30"}',
    'TheLoai': '{"tenTheLoai": "Fiction"}',
    'TacGia': '{"tenTacGia": "John Smith", "ngaySinh": "1950-10-25"}',
    'DauSach': '{"tenDauSach": "Sample Book"}',
    'Sach': '{"maDauSach": 1, "maTheLoai": 1, "nhaXuatBan": "Publisher Inc", "namXuatBan": 2020, "soLuong": 10, "giaTien": 150000}',
    'CuonSach': '{"maSach": 1, "tinhTrang": "Good"}',
    'CT_TacGia': '{"maDauSach": 1, "maTacGia": 1}',
    'PhieuMuonTra': '{"maCuonSach": 1, "maDocGia": "DG001", "ngayMuon": "2024-04-19", "ngayPhaiTra": "2024-05-19", "soNgayMuon": 30, "tienPhat": 0, "maNhanVien": "staff"}',
    'PhieuThuTienPhat': '{"maDocGia": "DG001", "ngayThu": "2024-04-19", "soTienThu": 50000, "conLai": 100000}',
    'BaoCaoTinhHinhMuonSach': '{"thang": 4, "nam": 2024, "tongSoLuotMuon": 50}',
    'CTBC_THMS': '{"maBCTHMS": 1, "maTheLoai": 1, "soLuotMuon": 25, "tiLe": 50}',
    'BC_SachTraTre': '{"ngay": "2024-04-19", "maCuonSach": 1, "ngayMuon": "2024-03-19", "soNgayTraTre": 31}',
}

def find_and_update(items):
    for item in items:
        # Process nested items
        if 'item' in item:
            find_and_update(item['item'])
        
        # Update empty request bodies
        if 'request' in item and item['request'].get('body', {}).get('raw') == '{}':
            name = item.get('name', '')
            
            # Find best match - try exact entity name first, then partial
            for entity_key in sample_data:
                if entity_key in name:
                    item['request']['body']['raw'] = sample_data[entity_key]
                    break

# Process all items
find_and_update(collection.get('item', []))

# Save updated collection
with open('postman-collection.json', 'w', encoding='utf-8') as f:
    json.dump(collection, f, indent=2, ensure_ascii=False)

print("Postman collection updated with sample request bodies")
