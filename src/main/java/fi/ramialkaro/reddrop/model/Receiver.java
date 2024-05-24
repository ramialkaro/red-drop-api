package fi.ramialkaro.reddrop.model;

import fi.ramialkaro.reddrop.model.enums.BloodType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Receiver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BloodType bloodType;

    @ManyToOne
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @Column(nullable = false)
    private boolean hasInfectionOrDisease;

    @Column
    private String diseaseDescription;

    @Column(nullable = false)
    private boolean isSmoker;

    @Column(nullable = false)
    private boolean consumesAlcohol;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public boolean isHasInfectionOrDisease() {
        return hasInfectionOrDisease;
    }

    public void setHasInfectionOrDisease(boolean hasInfectionOrDisease) {
        this.hasInfectionOrDisease = hasInfectionOrDisease;
    }

    public String getDiseaseDescription() {
        return diseaseDescription;
    }

    public void setDiseaseDescription(String diseaseDescription) {
        this.diseaseDescription = diseaseDescription;
    }

    public boolean isSmoker() {
        return isSmoker;
    }

    public void setSmoker(boolean isSmoker) {
        this.isSmoker = isSmoker;
    }

    public boolean isConsumesAlcohol() {
        return consumesAlcohol;
    }

    public void setConsumesAlcohol(boolean consumesAlcohol) {
        this.consumesAlcohol = consumesAlcohol;
    }

    @Override
    public String toString() {
        return "Receiver [id=" + id + ", user=" + user + ", bloodType=" + bloodType + ", organization=" + organization
                + ", hasInfectionOrDisease=" + hasInfectionOrDisease + ", diseaseDescription=" + diseaseDescription
                + ", isSmoker=" + isSmoker + ", consumesAlcohol=" + consumesAlcohol + "]";
    }
}
