package br.com.xgommiapi.service;

import br.com.xgommiapi.domain.entity.GommiUser;
import br.com.xgommiapi.domain.repository.GommiUserRepository;
import br.com.xgommiapi.dto.GommiUserDTO;
import br.com.xgommiapi.exception.GommiUserNotFoundException;
import br.com.xgommiapi.exception.GommiUserNotUniqueException;
import br.com.xgommiapi.exception.GommiUserNullAtributeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GommiUserService {
    @Autowired
    private GommiUserRepository gommiUserRepository;

    public GommiUserService(GommiUserRepository gommiUserRepository) {
        this.gommiUserRepository = gommiUserRepository;
    }

    public GommiUser getGommiUserById(Long id) throws GommiUserNotFoundException {
        Optional<GommiUser> gommiUser = gommiUserRepository.findById(id);

        if (gommiUser.isEmpty()) {
            throw new GommiUserNotFoundException("User with id " + id + " not found");
        }

        return gommiUser.get();
    }

    public GommiUser getGommiUserByLogin(String login) throws GommiUserNotFoundException {
        GommiUser gommiUser = gommiUserRepository.findByLogin(login);

        if (gommiUser == null) {
            throw new GommiUserNotFoundException("User with login " + login + " not found");
        }

        return gommiUser;
    }

    public GommiUser getGommiUserByEmail(String email) throws GommiUserNotFoundException {
        GommiUser gommiUser = gommiUserRepository.findByEmail(email);

        if (gommiUser == null) {
            throw new GommiUserNotFoundException("User with email " + email + " not found");
        }

        return gommiUser;
    }

    public List<GommiUser> getAllGommiUsers() {
        return gommiUserRepository.findAll();
    }

    public GommiUser createGommiUser(GommiUserDTO gommiUserDTO) throws GommiUserNotUniqueException, GommiUserNullAtributeException {
        GommiUser gommiUser = new GommiUser(gommiUserDTO);
        validateGommiUserUniqueAtributes(gommiUser);
        validateGommiUserNotNullAtributes(gommiUser);
        gommiUser.setRegistrationDate(LocalDateTime.now());
        return gommiUserRepository.save(gommiUser);
    }

    public GommiUser updateUser(GommiUser gommiUserUpdated) throws GommiUserNotUniqueException, GommiUserNullAtributeException, GommiUserNotFoundException {
        if (!gommiUserRepository.existsById(gommiUserUpdated.getIdGommiUser())) {
            throw new GommiUserNotFoundException("User with id " + gommiUserUpdated.getIdGommiUser() + " not found");
        }
        validateGommiUserUniqueAtributes(gommiUserUpdated);
        validateGommiUserNotNullAtributes(gommiUserUpdated);

        return gommiUserRepository.save(gommiUserUpdated);
    }

    public void deleteGommiUser(Long id) throws GommiUserNotFoundException {
        GommiUser gommiUser = getGommiUserById(id);
        gommiUserRepository.delete(gommiUser);
    }


    public void validateGommiUserUniqueAtributes(GommiUser gommiUser) throws GommiUserNotUniqueException {
        if(gommiUserRepository.existsByLogin(gommiUser.getLogin())) {
            throw new GommiUserNotUniqueException("An user with the login " + gommiUser.getLogin() + " already exists");
        }

        if(gommiUserRepository.existsByEmail(gommiUser.getLogin())) {
            throw new GommiUserNotUniqueException("An user with the email " + gommiUser.getEmail() + " already exists");
        }
    }

    public void validateGommiUserNotNullAtributes(GommiUser gommiUser) throws GommiUserNullAtributeException {
        if(gommiUser.getPassword() == null) {
            throw new GommiUserNullAtributeException("Password is required");
        }

        if(gommiUser.getName() == null || gommiUser.getName().trim().isEmpty()) {
            throw new GommiUserNullAtributeException("Name is required");
        }
    }

    public GommiUser save(GommiUser gommiUser) {
        return gommiUserRepository.save(gommiUser);
    }
}
