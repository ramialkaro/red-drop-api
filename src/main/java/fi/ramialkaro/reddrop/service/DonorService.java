package fi.ramialkaro.reddrop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.ramialkaro.reddrop.model.Donor;
import fi.ramialkaro.reddrop.model.Organization;
import fi.ramialkaro.reddrop.model.User;
import fi.ramialkaro.reddrop.repository.DonorRepository;
import fi.ramialkaro.reddrop.repository.OrganizationRepository;
import fi.ramialkaro.reddrop.repository.UserRepository;

@Service
public class DonorService {

    @Autowired
    private DonorRepository donorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    public List<Donor> getAllDonors() {
        return donorRepository.findAll();
    }

    public Optional<Donor> getDonorById(Long id) {
        return donorRepository.findById(id);
    }

    public Donor addDonor(Donor donor) {
        User user = userRepository.findById(donor.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Organization organization = organizationRepository.findById(donor.getOrganization().getId())
                .orElseThrow(() -> new RuntimeException("Organization not found"));

        donor.setUser(user);
        donor.setOrganization(organization);

        return donorRepository.save(donor);
    }

    public void deleteDonor(Long id) {
        donorRepository.deleteById(id);
    }
}