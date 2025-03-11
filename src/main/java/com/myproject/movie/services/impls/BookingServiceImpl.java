package com.myproject.movie.services.impls;

import com.myproject.movie.models.dtos.commons.ScreeningSeatDto;
import com.myproject.movie.models.entities.Booking;
import com.myproject.movie.models.entities.BookingSeat;
import com.myproject.movie.models.entities.Screening;
import com.myproject.movie.models.entities.ScreeningSeat;
import com.myproject.movie.models.entities.User;
import com.myproject.movie.models.enums.BookingStatus;
import com.myproject.movie.repositories.*;
import com.myproject.movie.services.BookingService;
import com.myproject.movie.services.ScreeningService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {
    private final ScreeningService screeningService;
    private final BookingRepository bookingRepository;
    private final BookingSeatRepository bookingSeatRepository;
    private final ScreeningRepository screeningRepository;
    private final ScreeningSeatRepository screeningSeatRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public Booking bookSeats(Long screeningId, List<Long> screeningSeatIds, Long userId) {
        try {
            screeningService.bookSeats(screeningId, screeningSeatIds);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to reserve seats: " + e.getMessage());
        }

        Screening screening = screeningRepository.findById(screeningId)
                .orElseThrow(() -> new RuntimeException("Screening not found with ID: " + screeningId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        Booking booking = new Booking();
        booking.setScreening(screening);
        booking.setUser(user);
        booking.setTotalAmount(calculateTotalAmount(screeningId, screeningSeatIds));
        booking.setStatus(BookingStatus.PENDING);
        Booking savedBooking = bookingRepository.save(booking);

        List<ScreeningSeat> screeningSeats = screeningSeatRepository.findByIdIn(screeningSeatIds);
        if (screeningSeats.size() != screeningSeatIds.size()) {
            throw new IllegalArgumentException("One or more screeningSeatId(s) do not exist: " + screeningSeatIds);
        }

        List<BookingSeat> bookingSeats = screeningSeats.stream()
                .map(screeningSeat -> {
                    BookingSeat bs = new BookingSeat();
                    bs.setBooking(savedBooking);
                    bs.setScreeningSeat(screeningSeat);
                    bs.setPrice(getSeatPrice(screeningId, screeningSeat.getId()));
                    return bs;
                })
                .collect(Collectors.toList());
        bookingSeatRepository.saveAll(bookingSeats);

        log.info("Created bookingId: {} with {} seats", savedBooking.getId(), bookingSeats.size());
        return savedBooking;
    }

    private Float getSeatPrice(Long screeningId, Long screeningSeatId) {
        return (float) screeningService.getSeatsByScreeningId(screeningId).stream()
                .filter(seat -> seat.getId().equals(screeningSeatId))
                .mapToDouble(ScreeningSeatDto::getPrice)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Price not found for screeningSeatId: " + screeningSeatId));
    }

    private Float calculateTotalAmount(Long screeningId, List<Long> screeningSeatIds) {
        return (float) screeningService.getSeatsByScreeningId(screeningId).stream()
                .filter(seat -> screeningSeatIds.contains(seat.getId()))
                .mapToDouble(ScreeningSeatDto::getPrice)
                .sum();
    }
}
