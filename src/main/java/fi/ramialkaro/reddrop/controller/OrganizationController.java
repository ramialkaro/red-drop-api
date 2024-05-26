package fi.ramialkaro.reddrop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import fi.ramialkaro.reddrop.model.Organization;
import fi.ramialkaro.reddrop.service.OrganizationService;

import java.util.List;

@RestController
@RequestMapping("/organizations")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @GetMapping
    public List<Organization> getAllOrganizations() {
        return organizationService.getAllOrganizations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Organization> getOrganizationById(@PathVariable Long id) {
        return organizationService.getOrganizationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Organization createOrganization(@RequestBody Organization organization) {
        return organizationService.createOrganization(organization);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Organization> updateOrganization(@PathVariable Long id,
            @RequestBody Organization organizationDetails) {
        return organizationService.getOrganizationById(id).map(organization -> {
            organization.setName(organizationDetails.getName());
            organization.setAddress(organizationDetails.getAddress());
            organization.setContactNumber(organizationDetails.getContactNumber());
            organization.setEmail(organizationDetails.getEmail());
            organization.setType(organizationDetails.getType());
            organization.setServicesProvided(organizationDetails.getServicesProvided());
            organizationService.createOrganization(organization);
            return ResponseEntity.ok(organization);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganization(@PathVariable Long id) {
        organizationService.deleteOrganization(id);
        return ResponseEntity.noContent().build();
    }
}
