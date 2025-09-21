package com.example.likelion13th_spring.service;

import com.example.likelion13th_spring.domain.*;
import com.example.likelion13th_spring.domain.Mapping.ProductOrders;
import com.example.likelion13th_spring.dto.request.*;
import com.example.likelion13th_spring.dto.response.OrdersResponseDto;
import com.example.likelion13th_spring.dto.response.ProductResponseDto;
import com.example.likelion13th_spring.enums.DeliverStatus;
import com.example.likelion13th_spring.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;
    private final ProductRepository productRepository;
    private final OrdersRepository ordersRepository;
    private final ShippingAddressRepository shippingAddressRepository;

    @Transactional
    public OrdersResponseDto createOrders(OrdersRequestDto dto) {
        Member member = memberRepository.findById(dto.getMemberId())
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Coupon coupon;

        if (dto.getCouponId() == null)
            coupon = null;
        else
            coupon = couponRepository.findById(dto.getCouponId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰입니다."));

        Orders orders = Orders.builder()
            .deliverStatus(DeliverStatus.PREPARATION)
            .coupon(coupon)
            .productOrders(new ArrayList<>())
            .buyer(member)
            .build();

        ShippingAddress shippingAddress = dto.getShippingAddress().toEntity(orders);

        orders.addShippingAddress(shippingAddress);

        for (OrdersRequestDto.ProductOrdersRequestDto poDto : dto.getProductOrders()) {
            Product product = productRepository.findById(poDto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

            ProductOrders productOrders = poDto.toEntity(orders, product);

            orders.getProductOrders().add(productOrders);
            product.getProductOrders().add(productOrders);
        }

        ordersRepository.save(orders);
        shippingAddressRepository.save(shippingAddress);

        return OrdersResponseDto.fromEntity(orders);
    }

    public List<OrdersResponseDto> getOrdersByMemberId(OrdersGetRequestDto dto) {
        Member member = memberRepository.findById(dto.getMemberId())
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        return ordersRepository.findAllByBuyerId(member.getId()).stream()
            .map(OrdersResponseDto::fromEntity)
            .toList();
    }

    public OrdersResponseDto getOrdersById(Long id) {
        Orders orders = ordersRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("주문이 존재하지 않습니다."));
        return OrdersResponseDto.fromEntity(orders);
    }

    @Transactional
    public OrdersResponseDto updateOrders(Long id, OrdersRequestDto.ShippingAddressRequestDto dto) {
        Orders orders = ordersRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("주문이 존재하지 않습니다."));

        if (!orders.getDeliverStatus().equals(DeliverStatus.PREPARATION)) {
            throw new IllegalArgumentException("배송 준비 중에만 수정할 수 있습니다.");
        }

        orders.getShippingAddress().update(dto.getRecipient(), dto.getPhoneNumber(), dto.getStreetAddress(), dto.getAddressDetail(), dto.getPostalCode());

        return OrdersResponseDto.fromEntity(orders);
    }

    @Transactional
    public void deleteOrders(Long id) {
        Orders orders = ordersRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("주문이 존재하지 않습니다."));

        if (!orders.getDeliverStatus().equals(DeliverStatus.COMPLETED)) {
            throw new IllegalArgumentException("배송이 완료된 경우에만 삭제할 수 있습니다.");
        }

        ordersRepository.delete(orders);
    }

}