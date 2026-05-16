package com.example.booking.mediator.interfaces;

public interface IAdminService {

    void blockUser(Long userId);

    void unblockUser(Long userId);

    void resolveDispute(Long bookingId);
}