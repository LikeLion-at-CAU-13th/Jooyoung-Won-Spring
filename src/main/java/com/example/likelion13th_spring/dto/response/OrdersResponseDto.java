package com.example.likelion13th_spring.dto.response;

import com.example.likelion13th_spring.domain.Mapping.ProductOrders;
import com.example.likelion13th_spring.domain.Orders;
import com.example.likelion13th_spring.domain.ShippingAddress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
@Builder
@AllArgsConstructor
public class OrdersResponseDto {
    private Long id;
    private String deliverStatus;
    private Long couponId;
    private List<ProductOrdersResponseDto> productOrders;
    private ShippingAddressResponseDto shippingAddress;

    public static OrdersResponseDto fromEntity(Orders orders) {
        return OrdersResponseDto.builder()
            .id(orders.getId())
            .deliverStatus(orders.getDeliverStatus().toString())
            .couponId(orders.getCoupon() != null ? orders.getCoupon().getId() : null)
            .productOrders(
                orders.getProductOrders().stream()
                    // map: 스트림의 각 요소를 다른 타입으로 변환, 각 요소에 적용할 함수 전달
                    .map(ProductOrdersResponseDto::fromEntity)
                    .toList()  // 스트림을 다시 리스트로 모음
            )
            .shippingAddress(
                orders.getShippingAddress() != null
                    ? ShippingAddressResponseDto.fromEntity(orders.getShippingAddress())
                    : null
            )
            .build();
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ProductOrdersResponseDto {
        private Long productId;
        private Integer quantity;

        public static ProductOrdersResponseDto fromEntity(ProductOrders productOrders) {
            return ProductOrdersResponseDto.builder()
                .productId(productOrders.getProduct().getId())
                .quantity(productOrders.getQuantity())
                .build();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ShippingAddressResponseDto {
        private String recipient;
        private String phoneNumber;
        private String streetAddress;
        private String addressDetail;
        private String postalCode;

        public static ShippingAddressResponseDto fromEntity(ShippingAddress shippingAddress) {
            return ShippingAddressResponseDto.builder()
                .recipient(shippingAddress.getRecipient())
                .phoneNumber(shippingAddress.getPhoneNumber())
                .streetAddress(shippingAddress.getStreetAddress())
                .addressDetail(shippingAddress.getAddressDetail())
                .postalCode(shippingAddress.getPostalCode())
                .build();
        }
    }
}
