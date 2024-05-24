package fi.ramialkaro.reddrop.service;

import org.springframework.stereotype.Service;

import fi.ramialkaro.reddrop.model.Organization;
import fi.ramialkaro.reddrop.repository.OrganizationRepository;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    public List<Organization> getAllOrganizations() {
        return organizationRepository.findAll();
    }

    public Optional<Organization> getOrganizationById(Long id) {
        return organizationRepository.findById(id);
    }

    public Organization createOrganization(Organization organization) {
        return organizationRepository.save(organization);
    }

    public void deleteOrganization(Long id) {
        organizationRepository.deleteById(id);
    }
}
