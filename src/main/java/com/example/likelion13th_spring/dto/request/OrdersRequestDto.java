package com.example.likelion13th_spring.dto.request;

import com.example.likelion13th_spring.domain.Mapping.ProductOrders;
import com.example.likelion13th_spring.domain.Orders;
import com.example.likelion13th_spring.domain.Product;
import com.example.likelion13th_spring.domain.ShippingAddress;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import java.util.List;
import java.util.Map;

@Getter
public class OrdersRequestDto {

    private Long memberId;
    private Long couponId;
    private ShippingAddressRequestDto shippingAddress;
    private List<ProductOrdersRequestDto> productOrders;

    @Getter
    public static class ProductOrdersRequestDto {
        private Long productId;
        private Integer quantity;

        public ProductOrders toEntity(Orders orders, Product product) {
            return ProductOrders.builder()
                .orders(orders)
                .product(product)
                .quantity(this.quantity)
                .build();
        }
    }

    @Getter
    public static class ShippingAddressRequestDto {

        private String recipient;
        private String phoneNumber;
        private String streetAddress;
        private String addressDetail;
        private String postalCode;

        public ShippingAddress toEntity(Orders orders) {
            return ShippingAddress.builder()
                .orders(orders)
                .recipient(this.getRecipient())
                .phoneNumber(this.getPhoneNumber())
                .streetAddress(this.getStreetAddress())
                .addressDetail(this.getAddressDetail())
                .postalCode(this.getPostalCode())
                .build();
        }

    }
}
