package fi.ramialkaro.reddrop.model;

import java.time.LocalDateTime;
import java.util.Set;

import fi.ramialkaro.reddrop.model.enums.OrganizationType;
import fi.ramialkaro.reddrop.model.enums.ServiceType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Entity
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String address;

    private String contactNumber;
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrganizationType type; // e.g., hospital, clinic, health center

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ServiceType servicesProvided; // e.g., blood donation, emergency services, etc.

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "organization")
    private Set<Donor> donors;

    @OneToMany(mappedBy = "organization")
    private Set<Receiver> receivers;

    @OneToMany(mappedBy = "organization")
    private Set<Donation> donations;

    @OneToMany(mappedBy = "organization")
    private Set<MonetaryDonation> monetaryDonations;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public OrganizationType getType() {
        return type;
    }

    public void setType(OrganizationType type) {
        this.type = type;
    }

    public ServiceType getServicesProvided() {
        return servicesProvided;
    }

    public void setServicesProvided(ServiceType servicesProvided) {
        this.servicesProvided = servicesProvided;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<Donor> getDonors() {
        return donors;
    }

    public void setDonors(Set<Donor> donors) {
        this.donors = donors;
    }

    public Set<Receiver> getReceivers() {
        return receivers;
    }

    public void setReceivers(Set<Receiver> receivers) {
        this.receivers = receivers;
    }

    public Set<Donation> getDonations() {
        return donations;
    }

    public void setDonations(Set<Donation> donations) {
        this.donations = donations;
    }

    public Set<MonetaryDonation> getMonetaryDonations() {
        return monetaryDonations;
    }

    public void setMonetaryDonations(Set<MonetaryDonation> monetaryDonations) {
        this.monetaryDonations = monetaryDonations;
    }

    @Override
    public String toString() {
        return "Organization [id=" + id + ", name=" + name + ", address=" + address + ", contactNumber=" + contactNumber
                + ", email=" + email + ", type=" + type + ", servicesProvided=" + servicesProvided + ", createdAt="
                + createdAt + ", updatedAt=" + updatedAt + ", donors=" + donors + ", receivers=" + receivers
                + ", donations=" + donations + ", monetaryDonations=" + monetaryDonations + "]";
    }

}
