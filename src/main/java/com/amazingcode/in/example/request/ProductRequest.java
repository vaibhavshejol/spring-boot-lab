package com.amazingcode.in.example.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private String productName;
    private LocalDate manufactureDate;
    private LocalDate expiryDate;
    private Double price;
    private Long categoryId;
}
