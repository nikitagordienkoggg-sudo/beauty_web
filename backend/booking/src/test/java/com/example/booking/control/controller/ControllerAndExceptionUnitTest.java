package com.example.booking.control.controller;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.booking.control.GlobalExceptionHandler;
import com.example.booking.entity.Master;
import com.example.booking.entity.Schedule;
import com.example.booking.entity.ServiceEntity;
import com.example.booking.entity.TimeSlot;
import com.example.booking.mediator.interfaces.IAdminService;
import com.example.booking.mediator.interfaces.IScheduleService;
import com.example.booking.mediator.interfaces.IServiceCatalogService;
import com.example.booking.mediator.interfaces.IStaffManagementService;

import jakarta.persistence.EntityNotFoundException;

class ControllerAndExceptionUnitTest {

    @Test
    void adminControllerDelegates() {
        IAdminService service = mock(IAdminService.class);
        AdminController controller = new AdminController(service);

        controller.resolve(7L);

        verify(service).resolveDispute(7L);
    }

    @Test
    void serviceScheduleAndStaffControllersDelegate() {
        IServiceCatalogService catalog = mock(IServiceCatalogService.class);
        ServiceController serviceController = new ServiceController(catalog);
        ServiceEntity s = new ServiceEntity();
        when(catalog.searchServices("nails")).thenReturn(List.of(s));
        when(catalog.createService(s)).thenReturn(s);
        when(catalog.updateService(1L, s)).thenReturn(s);

        assertEquals(1, serviceController.search("nails").size());
        assertEquals(s, serviceController.create(s));
        assertEquals(s, serviceController.update(1L, s));
        serviceController.delete(1L);
        verify(catalog).deleteService(1L);

        IScheduleService scheduleService = mock(IScheduleService.class);
        ScheduleController scheduleController = new ScheduleController(scheduleService);
        Schedule schedule = new Schedule();
        when(scheduleService.createSchedule(1L, java.time.LocalDate.of(2026, 5, 16))).thenReturn(schedule);
        when(scheduleService.getScheduleByMaster(1L)).thenReturn(List.of(schedule));
        when(scheduleService.getAvailableSlots(2L)).thenReturn(List.of(new TimeSlot()));

        assertEquals(schedule, scheduleController.create(1L, "2026-05-16"));
        assertEquals(1, scheduleController.get(1L).size());
        assertEquals(1, scheduleController.slots(2L).size());

        IStaffManagementService staffService = mock(IStaffManagementService.class);
        StaffController staffController = new StaffController(staffService);
        Master master = new Master();
        when(staffService.createMaster(master)).thenReturn(master);
        when(staffService.updateMaster(1L, master)).thenReturn(master);

        assertEquals(master, staffController.create(master));
        assertEquals(master, staffController.update(1L, master));
        staffController.complete(5L);
        verify(staffService).confirmServiceCompletion(5L);
    }

    @Test
    void globalExceptionHandlerReturnsExpectedResponses() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        ResponseEntity<String> notFound = handler.handleNotFound(new EntityNotFoundException("missing"));
        assertEquals(HttpStatus.NOT_FOUND, notFound.getStatusCode());
        assertEquals("missing", notFound.getBody());

        ResponseEntity<String> illegalState = handler.handleIllegalState(new IllegalStateException("bad state"));
        assertEquals(HttpStatus.BAD_REQUEST, illegalState.getStatusCode());
        assertEquals("bad state", illegalState.getBody());

        ResponseEntity<String> validation = handler.handleValidation();
        assertEquals(HttpStatus.BAD_REQUEST, validation.getStatusCode());
        assertEquals("Validation error", validation.getBody());
    }
}
