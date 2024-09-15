package br.com.xgommiapi.service;

import br.com.xgommiapi.domain.entity.FollowerRelation;
import br.com.xgommiapi.domain.entity.GommiUser;
import br.com.xgommiapi.domain.repository.FollowerRelationRepository;
import br.com.xgommiapi.domain.repository.GommiUserRepository;
import br.com.xgommiapi.dto.*;
import br.com.xgommiapi.exception.*;
import br.com.xgommiapi.utils.GommiUserUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GommiUserService {
    private final GommiUserRepository gommiUserRepository;
    private final FollowerRelationRepository followerRelationRepository;

    public GommiUserService(GommiUserRepository gommiUserRepository, FollowerRelationRepository followerRelationRepository) {
        this.gommiUserRepository = gommiUserRepository;
        this.followerRelationRepository = followerRelationRepository;
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

    public GommiUserResponseDTO getGommiUserResponseDTOById(Long id) throws GommiUserNotFoundException {
        GommiUser gommiUser = getGommiUserById(id);
        return GommiUserUtils.convertToResponseDTO(gommiUser);
    }

    public GommiUserResponseDTO getGommiUserResponseDTOByLogin(String login) throws GommiUserNotFoundException {
        GommiUser gommiUser = getGommiUserByLogin(login);
        return GommiUserUtils.convertToResponseDTO(gommiUser);
    }

    public GommiUserResponseDTO getGommiUserResponseDTOByEmail(String email) throws GommiUserNotFoundException {
        GommiUser gommiUser = getGommiUserByEmail(email);
        return GommiUserUtils.convertToResponseDTO(gommiUser);
    }

    public List<GommiUserSimpleResponseDTO> getAllGommiUsersSimpleResponseDTO() {
        List<GommiUser> usersFound = getAllGommiUsers();
        return GommiUserUtils.convertToSimpleResponseDTOList(usersFound);
    }

    public GommiUserSimpleResponseDTO createGommiUser(GommiUserRequestDTO gommiUserRequestDTO) throws GommiUserNotUniqueException, GommiUserNullAtributeException {
        GommiUser gommiUser = new GommiUser(gommiUserRequestDTO);
        validateUser(gommiUser);
        gommiUser.setRegistrationDate(LocalDateTime.now());
        gommiUserRepository.save(gommiUser);
        return GommiUserUtils.convertToSimpleResponseDTO(gommiUser);
    }

    public void validateUser(GommiUser gommiUser) throws GommiUserNotUniqueException, GommiUserNullAtributeException {
        validateGommiUserUniqueAtributes(gommiUser);
        validateGommiUserNotNullAtributes(gommiUser);
    }

    public GommiUser updateUser(GommiUser gommiUserUpdated) throws GommiUserNotUniqueException, GommiUserNullAtributeException, GommiUserNotFoundException {
        if (!gommiUserRepository.existsById(gommiUserUpdated.getIdGommiUser())) {
            throw new GommiUserNotFoundException("User with id " + gommiUserUpdated.getIdGommiUser() + " not found");
        }
        validateGommiUserUniqueAtributes(gommiUserUpdated);
        validateGommiUserNotNullAtributes(gommiUserUpdated);

        return gommiUserRepository.save(gommiUserUpdated);
    }

    public FollowerRelation followGommiUser(Long idFollower, Long idFollowed) throws GommiUserNotFoundException, GommiUserNullAtributeException, SelfFollowException, FollowerRelationAlreadyExistsException {
        validateFollowerRelation(idFollower, idFollowed);

        GommiUser follower = getGommiUserById(idFollower);
        GommiUser followed = getGommiUserById(idFollowed);

        FollowerRelation followerRelation = new FollowerRelation();
        followerRelation.setFollower(follower);
        followerRelation.setFollowed(followed);

        return followerRelationRepository.save(followerRelation);
    }

    public FollowerRelationResponseDTO createFollowerRelationResponse(FollowerRelationRequestDTO followRequest) throws SelfFollowException, GommiUserNotFoundException, GommiUserNullAtributeException, FollowerRelationAlreadyExistsException {
        FollowerRelation followerRelation = followGommiUser(followRequest.idFollower(), followRequest.idFollowed());
        return GommiUserUtils.convertToFollowerRelationResponseDTO(followerRelation);
    }

    public void deleteGommiUser(Long id) throws GommiUserNotFoundException {
        GommiUser gommiUser = getGommiUserById(id);
        gommiUserRepository.delete(gommiUser);
    }

    public void validateGommiUserUniqueAtributes(GommiUser gommiUser) throws GommiUserNotUniqueException {
        if(gommiUserRepository.existsByLogin(gommiUser.getLogin())) {
            throw new GommiUserNotUniqueException("An user with the login " + gommiUser.getLogin() + " already exists");
        }

        if(gommiUserRepository.existsByEmail(gommiUser.getEmail())) {
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

    public void validateFollowerRelation(Long idFollower, Long idFollowed) throws GommiUserNullAtributeException, SelfFollowException, FollowerRelationAlreadyExistsException {
        if (idFollower == null || idFollowed == null) {
            throw new GommiUserNullAtributeException("The id sent cannot be null");
        }

        if (idFollower.equals(idFollowed)) {
            throw new SelfFollowException("GommiUser cannot follow itself");
        }

        if(followerRelationRepository.existsByFollowerIdAndFollowedId(idFollower, idFollowed)) {
            throw new FollowerRelationAlreadyExistsException("The GommiUser with the id " + idFollower + " already " +
                    "follows the GommiUser with the id " + idFollowed);
        }
    }
}
