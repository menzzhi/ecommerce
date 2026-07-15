package com.example.ecommerce1.dto;

import java.util.List;

public record UserResponse(
          String nome,
          String email,
          List<AddressResponses> address
) {
}
