package com.example.booking.mediator.service;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.booking.entity.Booking;
import com.example.booking.entity.Client;
import com.example.booking.entity.Master;
import com.example.booking.entity.ServiceEntity;
import com.example.booking.entity.TimeSlot;
import com.example.booking.foundation.repository.IBookingRepository;
import com.example.booking.foundation.repository.IClientRepository;
import com.example.booking.foundation.repository.IMasterRepository;
import com.example.booking.foundation.repository.IServiceEntityRepository;
import com.example.booking.foundation.repository.ITimeSlotRepository;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock IBookingRepository bookingRepository;
    @Mock IClientRepository clientRepository;
    @Mock IServiceEntityRepository serviceEntityRepository;
    @Mock IMasterRepository masterRepository;
    @Mock ITimeSlotRepository timeSlotRepository;

    @InjectMocks BookingServiceImpl service;

   @Test
    void shouldCreateBooking() {
        // 1. Подготовка данных
        Client client = new Client();
        Master master = new Master();
        ServiceEntity serviceEntity = new ServiceEntity();
        
        TimeSlot slot = new TimeSlot();
        slot.setAvailable(true); // КРИТИЧНО: делаем слот доступным

        // 2. Настройка моков
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(serviceEntityRepository.findById(1L)).thenReturn(Optional.of(serviceEntity));
        when(masterRepository.findById(1L)).thenReturn(Optional.of(master));
        when(timeSlotRepository.findById(1L)).thenReturn(Optional.of(slot));
        
        // Настраиваем возврат объекта при сохранении, чтобы assertNotNull сработал
        when(bookingRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // 3. Выполнение
        Booking booking = service.createBooking(1L, 1L, 1L, 1L);

        // 4. Проверки
        assertNotNull(booking);
        assertEquals("CREATED", booking.getStatus());
        verify(bookingRepository, times(1)).save(any());
        verify(timeSlotRepository, times(1)).save(slot); // Проверяем, что слот сохранился
    }

    @Test
    void shouldCancelBooking() {

        Booking booking = new Booking();
        booking.updateStatus("CREATED");
        booking.setDateTime(java.time.LocalDateTime.now().plusDays(1)); // Устанавливаем дату в будущем, чтобы не было ограничения по времени

        when(bookingRepository.findById(1L))
                .thenReturn(java.util.Optional.of(booking));

        service.cancelBooking(1L);

        assertEquals("CANCELLED", booking.getStatus());
    }

    @Test
    void shouldNotCancelCompletedBooking() {

        Booking booking = new Booking();
        booking.updateStatus("COMPLETED");
        booking.setDateTime(java.time.LocalDateTime.now().plusDays(1)); // Устанавливаем дату в будущем, чтобы не было ограничения по времени

        when(bookingRepository.findById(1L))
                .thenReturn(java.util.Optional.of(booking));

        assertThrows(RuntimeException.class,
                () -> service.cancelBooking(1L));
    }

    @Test
    void shouldCancelBooking_success() {

    Booking booking = new Booking();
    booking.updateStatus("CREATED");
    booking.setDateTime(LocalDateTime.now().plusDays(1));

    when(bookingRepository.findById(1L))
            .thenReturn(Optional.of(booking));

    service.cancelBooking(1L);

    assertEquals("CANCELLED", booking.getStatus());
    }


    @Test
    void shouldFailCancel_whenCompleted() {

        Booking booking = new Booking();
        booking.updateStatus("COMPLETED");
        booking.setDateTime(LocalDateTime.now());

        when(bookingRepository.findById(1L))
                .thenReturn(Optional.of(booking));

        assertThrows(RuntimeException.class,
                () -> service.cancelBooking(1L));
    }

}