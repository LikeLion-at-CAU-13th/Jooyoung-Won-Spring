package com.example.likelion13th_spring.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShippingAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String recipient;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String streetAddress;

    @Column(nullable = false)
    private String addressDetail;

    @Column(nullable = false)
    private String postalCode;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Orders orders;

    public void update(String recipient, String phoneNumber, String streetAddress, String addressDetail, String postalCode) {
        this.recipient = recipient;
        this.phoneNumber = phoneNumber;
        this.streetAddress = streetAddress;
        this.addressDetail = addressDetail;
        this.postalCode = postalCode;
    }
}
