package com.example.likelion13th_spring.Controller;

import com.example.likelion13th_spring.dto.request.*;
import com.example.likelion13th_spring.dto.response.OrdersResponseDto;
import com.example.likelion13th_spring.dto.response.ProductResponseDto;
import com.example.likelion13th_spring.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrdersController {

    private final OrdersService ordersService;

    // 주문 생성
    @PostMapping
    public ResponseEntity<OrdersResponseDto> createOrders(@RequestBody OrdersRequestDto dto) {
        return ResponseEntity.ok(ordersService.createOrders(dto));
    }

    // 사용자 별 주문 조회
    @GetMapping
    public ResponseEntity<List<OrdersResponseDto>> getOrdersByMemberId(@RequestBody OrdersGetRequestDto dto) {
        return ResponseEntity.ok(ordersService.getOrdersByMemberId(dto));
    }

    // 개별 주문 조회
    @GetMapping("/{id}")
    public ResponseEntity<OrdersResponseDto> getOrdersById(@PathVariable Long id) {
        return ResponseEntity.ok(ordersService.getOrdersById(id));
    }

    // 특정 주문 수정
    @PutMapping("/{id}")
    public ResponseEntity<OrdersResponseDto> updateOrders(@PathVariable Long id,
                                                            @RequestBody OrdersRequestDto.ShippingAddressRequestDto dto) {
        return ResponseEntity.ok(ordersService.updateOrders(id, dto));
    }

    // 특정 주문 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrders(@PathVariable Long id) {
        ordersService.deleteOrders(id);
        return ResponseEntity.ok("주문이 성공적으로 삭제되었습니다.");
    }
}