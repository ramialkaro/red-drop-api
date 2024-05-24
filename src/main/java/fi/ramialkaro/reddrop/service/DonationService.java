package fi.ramialkaro.reddrop.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.ramialkaro.reddrop.model.Donation;
import fi.ramialkaro.reddrop.model.Donor;
import fi.ramialkaro.reddrop.model.Receiver;
import fi.ramialkaro.reddrop.repository.DonationRepository;
import fi.ramialkaro.reddrop.repository.DonorRepository;
import fi.ramialkaro.reddrop.repository.ReceiverRepository;
import fi.ramialkaro.reddrop.util.BloodTypeCompatibility;

@Service
public class DonationService {
    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private DonorRepository donorRepository;

    @Autowired
    private ReceiverRepository receiverRepository;

    public List<Donation> getAllDonations() {
        return donationRepository.findAll();
    }

    public Optional<Donation> getDonationById(Long id) {
        return donationRepository.findById(id);
    }

    public Donation addDonation(Donation donation) {
        Optional<Donor> donorOptional = donorRepository.findById(donation.getDonor().getId());

        if (!donorOptional.isPresent()) {
            throw new IllegalArgumentException("Donor does not exist");
        }

        Donor donor = donorOptional.get();

        if (donation.getReceiver() != null) {
            Optional<Receiver> receiverOptional = receiverRepository.findById(donation.getReceiver().getId());
            if (!receiverOptional.isPresent()) {
                throw new IllegalArgumentException("Receiver does not exist");
            }

            Receiver receiver = receiverOptional.get();

            if (!BloodTypeCompatibility.isCompatible(donor.getBloodType(), receiver.getBloodType())) {
                throw new IllegalArgumentException("Incompatible blood types: " + donor.getBloodType()
                        + " cannot donate to " + receiver.getBloodType());
            }

            if (donor.isHasInfectionOrDisease() || receiver.isHasInfectionOrDisease()) {
                throw new IllegalArgumentException(
                        "Donation cannot proceed due to infection or disease. Details: Donor: "
                                + donor.getDiseaseDescription() + ", Receiver: " + receiver.getDiseaseDescription());
            }

            if (donor.isSmoker() || receiver.isSmoker()) {
                throw new IllegalArgumentException("Donation cannot proceed due to smoking habits");
            }

            if (donor.isConsumesAlcohol() || receiver.isConsumesAlcohol()) {
                throw new IllegalArgumentException("Donation cannot proceed due to alcohol consumption");
            }

            donation.setReceiver(receiver);
        }

        donation.setDonor(donor);
        return donationRepository.save(donation);
    }

    public void deleteDonation(Long id) {
        donationRepository.deleteById(id);
    }

    public List<Map<String, Object>> getDonationsGroupedByDonorId() {
        return donationRepository.findDonationsGroupedByDonorId();
    }
}