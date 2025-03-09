package com.myproject.movie.mappers;

import com.myproject.movie.models.dtos.responses.PaymentResponseDto;
import com.myproject.movie.models.entities.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    @Mapping(source = "booking.id", target = "bookingId")
    PaymentResponseDto toPaymentResponseDto(Payment payment);
}
