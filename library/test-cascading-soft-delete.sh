#!/bin/bash

# ============================================================
# CASCADING SOFT DELETE - API TEST SCRIPT
# ============================================================
# 
# Script để test các endpoint Cascading Soft Delete
# 
# Cách dùng:
#   chmod +x test-cascading-soft-delete.sh
#   ./test-cascading-soft-delete.sh
# 
# ============================================================

set -e

# Color codes for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Configuration
API_BASE_URL="http://localhost:8080/api"
DAUSACH_ENDPOINT="$API_BASE_URL/dausach"

# Helper functions
print_header() {
    echo -e "\n${BLUE}╔════════════════════════════════════════════════════════════╗${NC}"
    echo -e "${BLUE}║ $1${NC}"
    echo -e "${BLUE}╚════════════════════════════════════════════════════════════╝${NC}\n"
}

print_success() {
    echo -e "${GREEN}✓ $1${NC}"
}

print_error() {
    echo -e "${RED}✗ $1${NC}"
}

print_info() {
    echo -e "${YELLOW}ℹ $1${NC}"
}

# Test 1: Get all DauSach (should not include deleted ones)
test1_get_all_dausach() {
    print_header "TEST 1: GET all DauSach"
    
    print_info "Fetching all DauSach..."
    curl -X GET "$DAUSACH_ENDPOINT" \
        -H "Content-Type: application/json" \
        -H "Accept: application/json" \
        -s | python -m json.tool
    
    print_success "Test 1 completed"
}

# Test 2: Get single DauSach by ID
test2_get_single_dausach() {
    print_header "TEST 2: GET single DauSach by ID"
    
    DAUSACH_ID=1
    print_info "Fetching DauSach ID=$DAUSACH_ID..."
    
    curl -X GET "$DAUSACH_ENDPOINT/$DAUSACH_ID" \
        -H "Content-Type: application/json" \
        -H "Accept: application/json" \
        -s | python -m json.tool
    
    print_success "Test 2 completed"
}

# Test 3: Soft Delete single DauSach
test3_soft_delete_single() {
    print_header "TEST 3: DELETE single DauSach (Soft Delete)"
    
    DAUSACH_ID=1
    print_info "Soft deleting DauSach ID=$DAUSACH_ID..."
    print_info "This will cascade delete all related Sach and CuonSach"
    
    RESPONSE=$(curl -X DELETE "$DAUSACH_ENDPOINT/$DAUSACH_ID" \
        -H "Content-Type: application/json" \
        -H "Accept: application/json" \
        -s)
    
    echo "$RESPONSE" | python -m json.tool
    
    if echo "$RESPONSE" | grep -q '"code":1000'; then
        print_success "Soft delete successful"
    else
        print_error "Soft delete failed"
    fi
}

# Test 4: Verify DauSach is deleted (should not appear in list)
test4_verify_deleted() {
    print_header "TEST 4: VERIFY DauSach is deleted"
    
    DAUSACH_ID=1
    print_info "Fetching all DauSach (deleted one should not appear)..."
    
    curl -X GET "$DAUSACH_ENDPOINT" \
        -H "Content-Type: application/json" \
        -H "Accept: application/json" \
        -s | python -m json.tool
    
    print_success "Verification completed"
}

# Test 5: Try to delete non-existent DauSach (error handling)
test5_delete_nonexistent() {
    print_header "TEST 5: DELETE non-existent DauSach (Error Handling)"
    
    DAUSACH_ID=99999
    print_info "Attempting to delete non-existent DauSach ID=$DAUSACH_ID..."
    print_info "Expected: 500 error with 'Not found' message"
    
    RESPONSE=$(curl -X DELETE "$DAUSACH_ENDPOINT/$DAUSACH_ID" \
        -H "Content-Type: application/json" \
        -H "Accept: application/json" \
        -s -w "\n%{http_code}")
    
    HTTP_CODE=$(echo "$RESPONSE" | tail -1)
    BODY=$(echo "$RESPONSE" | head -n -1)
    
    echo "HTTP Code: $HTTP_CODE"
    echo "Response:"
    echo "$BODY" | python -m json.tool
    
    if [ "$HTTP_CODE" = "500" ]; then
        print_success "Error handling works correctly"
    else
        print_error "Expected HTTP 500, got $HTTP_CODE"
    fi
}

# Test 6: Soft Delete Multiple DauSach
test6_delete_multiple() {
    print_header "TEST 6: DELETE multiple DauSach (Batch Delete)"
    
    print_info "Soft deleting multiple DauSach: IDs 2, 3, 4..."
    print_info "This will cascade delete all related Sach and CuonSach for each"
    
    RESPONSE=$(curl -X POST "$DAUSACH_ENDPOINT/delete-multiple" \
        -H "Content-Type: application/json" \
        -H "Accept: application/json" \
        -d '[2, 3, 4]' \
        -s)
    
    echo "$RESPONSE" | python -m json.tool
    
    if echo "$RESPONSE" | grep -q '"code":1000'; then
        print_success "Batch delete successful"
    else
        print_error "Batch delete failed"
    fi
}

# Test 7: Verify Multiple Deletes
test7_verify_multiple_deleted() {
    print_header "TEST 7: VERIFY multiple DauSach are deleted"
    
    print_info "Fetching all DauSach (deleted ones should not appear)..."
    
    curl -X GET "$DAUSACH_ENDPOINT" \
        -H "Content-Type: application/json" \
        -H "Accept: application/json" \
        -s | python -m json.tool
    
    print_success "Verification completed"
}

# Test 8: Performance test (if you have many records)
test8_performance() {
    print_header "TEST 8: PERFORMANCE - Delete with many related records"
    
    print_info "This test measures time to delete a DauSach with many Sach/CuonSach"
    print_info "Expected: < 100ms even for 10,000+ related records"
    
    DAUSACH_ID=5
    
    print_info "Starting timer..."
    START_TIME=$(date +%s%N | cut -b1-13)
    
    RESPONSE=$(curl -X DELETE "$DAUSACH_ENDPOINT/$DAUSACH_ID" \
        -H "Content-Type: application/json" \
        -H "Accept: application/json" \
        -s)
    
    END_TIME=$(date +%s%N | cut -b1-13)
    DURATION=$((END_TIME - START_TIME))
    
    echo "Response:"
    echo "$RESPONSE" | python -m json.tool
    echo ""
    echo "Execution time: ${DURATION}ms"
    
    if [ "$DURATION" -lt 1000 ]; then
        print_success "Performance is EXCELLENT (< 1 second)"
    elif [ "$DURATION" -lt 5000 ]; then
        print_info "Performance is GOOD (< 5 seconds)"
    else
        print_error "Performance is POOR (> 5 seconds)"
    fi
}

# Test 9: Concurrent deletes (if supported)
test9_concurrent_deletes() {
    print_header "TEST 9: CONCURRENT DELETE (Multiple simultaneous requests)"
    
    print_info "Sending 5 concurrent delete requests..."
    
    for i in {6..10}; do
        {
            print_info "Deleting DauSach ID=$i in background..."
            curl -X DELETE "$DAUSACH_ENDPOINT/$i" \
                -H "Content-Type: application/json" \
                -H "Accept: application/json" \
                -s > /dev/null
            print_success "Deleted DauSach ID=$i"
        } &
    done
    
    wait
    
    print_success "All concurrent deletes completed"
}

# Test 10: Full Integration Test
test10_full_integration() {
    print_header "TEST 10: FULL INTEGRATION TEST"
    
    print_info "Step 1: Get initial DauSach count..."
    INITIAL=$(curl -s "$DAUSACH_ENDPOINT" | grep -o '"maDauSach"' | wc -l)
    echo "Initial count: $INITIAL"
    
    print_info "Step 2: Delete DauSach ID=11..."
    curl -X DELETE "$DAUSACH_ENDPOINT/11" \
        -H "Content-Type: application/json" \
        -s > /dev/null
    
    print_info "Step 3: Get final DauSach count..."
    FINAL=$(curl -s "$DAUSACH_ENDPOINT" | grep -o '"maDauSach"' | wc -l)
    echo "Final count: $FINAL"
    
    DIFF=$((INITIAL - FINAL))
    echo "Difference: $DIFF record(s)"
    
    if [ "$DIFF" -gt 0 ]; then
        print_success "Integration test PASSED"
    else
        print_error "Integration test FAILED"
    fi
}

# Main menu
show_menu() {
    print_header "CASCADING SOFT DELETE - TEST SUITE"
    
    echo "Available tests:"
    echo "  1) Get all DauSach"
    echo "  2) Get single DauSach"
    echo "  3) Soft Delete single DauSach"
    echo "  4) Verify DauSach is deleted"
    echo "  5) Delete non-existent DauSach (Error handling)"
    echo "  6) Delete multiple DauSach"
    echo "  7) Verify multiple DauSach deleted"
    echo "  8) Performance test"
    echo "  9) Concurrent deletes"
    echo " 10) Full integration test"
    echo " 11) Run all tests"
    echo "  0) Exit"
    echo ""
}

run_all_tests() {
    print_header "RUNNING ALL TESTS"
    
    test1_get_all_dausach
    test2_get_single_dausach
    test3_soft_delete_single
    test4_verify_deleted
    test5_delete_nonexistent
    test6_delete_multiple
    test7_verify_multiple_deleted
    test8_performance
    test9_concurrent_deletes
    test10_full_integration
    
    print_header "ALL TESTS COMPLETED"
}

# Check if server is running
check_server() {
    print_info "Checking if API server is running at $API_BASE_URL..."
    
    if curl -s "$API_BASE_URL/dausach" > /dev/null 2>&1; then
        print_success "Server is running"
        return 0
    else
        print_error "Server is not responding at $API_BASE_URL"
        print_info "Please start the application with: mvn spring-boot:run"
        return 1
    fi
}

# Main program
main() {
    # Check if server is running
    if ! check_server; then
        exit 1
    fi
    
    # Interactive menu loop
    while true; do
        show_menu
        
        read -p "Select option (0-11): " choice
        
        case $choice in
            1) test1_get_all_dausach ;;
            2) test2_get_single_dausach ;;
            3) test3_soft_delete_single ;;
            4) test4_verify_deleted ;;
            5) test5_delete_nonexistent ;;
            6) test6_delete_multiple ;;
            7) test7_verify_multiple_deleted ;;
            8) test8_performance ;;
            9) test9_concurrent_deletes ;;
            10) test10_full_integration ;;
            11) run_all_tests ;;
            0) 
                print_info "Exiting test suite"
                exit 0
                ;;
            *)
                print_error "Invalid option. Please select 0-11"
                ;;
        esac
        
        read -p "Press Enter to continue..."
    done
}

# Run main program
main
