package com.myproject.movie.services.impls;

import com.myproject.movie.models.dtos.commons.ScreeningSeatDto;
import com.myproject.movie.models.entities.Booking;
import com.myproject.movie.models.entities.Screening;
import com.myproject.movie.models.entities.User;
import com.myproject.movie.models.enums.BookingStatus;
import com.myproject.movie.repositories.BookingRepository;
import com.myproject.movie.repositories.ScreeningRepository;
import com.myproject.movie.repositories.UserRepository;
import com.myproject.movie.services.BookingService;
import com.myproject.movie.services.ScreeningService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final ScreeningService screeningService;
    private final BookingRepository bookingRepository;
    private final ScreeningRepository screeningRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public Booking bookSeats(Long screeningId, List<Long> seatIds, Long userId) {
        try {
            screeningService.bookSeats(screeningId, seatIds);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to reserve seats: " + e.getMessage());
        }

        // Fetch screening entity
        Screening screening = screeningRepository.findById(screeningId)
                .orElseThrow(() -> new RuntimeException("Screening not found with ID: " + screeningId));

        // Fetch user entity
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        // Create booking
        Booking booking = new Booking();
        booking.setScreening(screening);
        booking.setUser(user);
        booking.setTotalAmount(calculateTotalAmount(screeningId, seatIds));
        booking.setStatus(BookingStatus.PENDING);

        return bookingRepository.save(booking);
    }

    private Float calculateTotalAmount(Long screeningId, List<Long> seatIds) {
        return (float) screeningService.getSeatsByScreeningId(screeningId).stream()
                .filter(seat -> seatIds.contains(seat.getId()))
                .mapToDouble(ScreeningSeatDto::getPrice)
                .sum();
    }
}
