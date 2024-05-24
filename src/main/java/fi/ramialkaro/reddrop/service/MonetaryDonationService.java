package fi.ramialkaro.reddrop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.ramialkaro.reddrop.model.MonetaryDonation;
import fi.ramialkaro.reddrop.repository.MonetaryDonationRepository;

@Service
public class MonetaryDonationService {
    @Autowired
    private MonetaryDonationRepository monetaryDonationRepository;

    public List<MonetaryDonation> getAllMonetaryDonations() {
        return monetaryDonationRepository.findAll();
    }

    public Optional<MonetaryDonation> getMonetaryDonationById(Long id) {
        return monetaryDonationRepository.findById(id);
    }

    public MonetaryDonation createMonetaryDonation(MonetaryDonation monetaryDonation) {
        return monetaryDonationRepository.save(monetaryDonation);
    }

    public void deleteMonetaryDonation(Long id) {
        monetaryDonationRepository.deleteById(id);
    }
}