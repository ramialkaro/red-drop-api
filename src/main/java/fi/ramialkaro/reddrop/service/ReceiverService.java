package fi.ramialkaro.reddrop.service;

import fi.ramialkaro.reddrop.model.Organization;
import fi.ramialkaro.reddrop.model.Receiver;
import fi.ramialkaro.reddrop.model.User;
import fi.ramialkaro.reddrop.repository.OrganizationRepository;
import fi.ramialkaro.reddrop.repository.ReceiverRepository;
import fi.ramialkaro.reddrop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReceiverService {

    @Autowired
    private ReceiverRepository receiverRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    public List<Receiver> getAllReceivers() {
        return receiverRepository.findAll();
    }

    public Optional<Receiver> getReceiverById(Long id) {
        return receiverRepository.findById(id);
    }

    public Receiver createReceiver(Receiver receiver) {
        User user = userRepository.findById(receiver.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Organization organization = organizationRepository.findById(receiver.getOrganization().getId())
                .orElseThrow(() -> new RuntimeException("Organization not found"));

        receiver.setUser(user);
        receiver.setOrganization(organization);

        return receiverRepository.save(receiver);
    }

    public void deleteReceiver(Long id) {
        receiverRepository.deleteById(id);
    }
}
