package com.example.booking.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "master_id")
    private Master master;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private ServiceEntity service;

    @ManyToOne
    @JoinColumn(name = "salon_id")
    private Salon salon;

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
    private Payment payment;

    public Booking() {
    }

    public Booking(LocalDateTime dateTime, String status) {
        this.dateTime = dateTime;
        this.status = status;
    }

    // ===== BUSINESS METHODS =====

    public void updateStatus(String status) {
        this.status = status;
    }

    public void assignMaster(Master master) {
        this.master = master;
    }

    public boolean canBeCancelled() {

        if ("COMPLETED".equals(status)) {
            return false;
        }

        return dateTime.isAfter(LocalDateTime.now());
    }

    public void cancel() {

        if (!canBeCancelled()) {
            throw new IllegalStateException(
                    "Booking cannot be cancelled"
            );
        }

        this.status = "CANCELLED";
    }

    // ===== GETTERS / SETTERS =====

    public Long getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getStatus() {
        return status;
    }

    public Client getClient() {
        return client;
    }

    public Master getMaster() {
        return master;
    }

    public ServiceEntity getService() {
        return service;
    }

    public Salon getSalon() {
        return salon;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setMaster(Master master) {
        this.master = master;
    }

    public void setService(ServiceEntity service) {
        this.service = service;
    }

    public void setSalon(Salon salon) {
        this.salon = salon;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}