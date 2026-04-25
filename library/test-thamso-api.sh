#!/usr/bin/env bash

# ThamSo (Business Rules Parameters) API Testing Guide
# Module Quản lý Tham Số Hệ thống Thư viện

API_BASE_URL="http://localhost:8080/api/thamso"

echo "========================================="
echo "1. GET ALL PARAMETERS"
echo "========================================="
curl -X GET "$API_BASE_URL" \
  -H "Content-Type: application/json"

echo -e "\n\n========================================="
echo "2. GET SINGLE PARAMETER (TuoiToiThieu)"
echo "========================================="
curl -X GET "$API_BASE_URL/TuoiToiThieu" \
  -H "Content-Type: application/json"

echo -e "\n\n========================================="
echo "3. UPDATE PARAMETER (TuoiToiThieu)"
echo "========================================="
curl -X PUT "$API_BASE_URL/TuoiToiThieu" \
  -H "Content-Type: application/json" \
  -d '{
    "giaTri": "20"
  }'

echo -e "\n\n========================================="
echo "4. CREATE NEW PARAMETER"
echo "========================================="
curl -X POST "$API_BASE_URL" \
  -H "Content-Type: application/json" \
  -d '{
    "tenThamSo": "SoPhutMuonToiDa",
    "giaTri": "60"
  }'

echo -e "\n\n========================================="
echo "5. DELETE PARAMETER"
echo "========================================="
curl -X DELETE "$API_BASE_URL/SoPhutMuonToiDa" \
  -H "Content-Type: application/json"

echo -e "\n========== TEST COMPLETED =========="
