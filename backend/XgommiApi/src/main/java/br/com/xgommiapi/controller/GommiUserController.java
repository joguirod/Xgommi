package br.com.xgommiapi.controller;

import br.com.xgommiapi.domain.entity.GommiUser;
import br.com.xgommiapi.dto.*;
import br.com.xgommiapi.exception.*;
import br.com.xgommiapi.service.GommiUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/xgommi/gommiuser")
public class  GommiUserController {
    @Autowired
    private GommiUserService gommiUserService;

    public GommiUserController(GommiUserService gommiUserService) {
        this.gommiUserService = gommiUserService;
    }

    @PostMapping
    public ResponseEntity<GommiUserSimpleResponseDTO> post(@RequestBody GommiUserRequestDTO gommiUserRequestDTO) throws GommiUserNotUniqueException, GommiUserNullAtributeException {
        return new ResponseEntity<>(gommiUserService.createGommiUser(gommiUserRequestDTO), HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<GommiUserResponseDTO> login(@RequestBody GommiUserLoginRequestDTO gommiUserLoginRequestDTO) throws GommiUserNotFoundException, GommiUserAuthenticationFailedException {
        return new ResponseEntity<>(gommiUserService.loginResponse(gommiUserLoginRequestDTO), HttpStatus.OK);}

    @PostMapping("/follow")
    public ResponseEntity<FollowerRelationResponseDTO> followGommiUser(@RequestBody FollowerRelationRequestDTO followerRelationRequestDTO) throws SelfFollowException, GommiUserNotFoundException, GommiUserNullAtributeException, FollowerRelationAlreadyExistsException {
        return new ResponseEntity<>(gommiUserService.createFollowerRelationResponse(followerRelationRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<GommiUserSimpleResponseDTO>> getAll() {
        return new ResponseEntity<>(gommiUserService.getAllGommiUsersSimpleResponseDTO(), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<GommiUserResponseDTO> get(@PathVariable Long id) throws GommiUserNotFoundException {
        return new ResponseEntity<>(gommiUserService.getGommiUserResponseDTOById(id), HttpStatus.OK);
    }

    @GetMapping("/login/{login}")
    public ResponseEntity<GommiUserResponseDTO> getByLogin(@PathVariable String login) throws GommiUserNotFoundException {
        return new ResponseEntity<>(gommiUserService.getGommiUserResponseDTOByLogin(login), HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<GommiUserResponseDTO> getByEmail(@PathVariable String email) throws GommiUserNotFoundException {
        return new ResponseEntity<>(gommiUserService.getGommiUserResponseDTOByEmail(email), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<GommiUserResponseDTO> update(@RequestBody GommiUserSimpleRequestDTO gommiUser) throws GommiUserNotFoundException, GommiUserNotUniqueException, GommiUserNullAtributeException {
        return new ResponseEntity<>(gommiUserService.updateUser(gommiUser), HttpStatus.OK);
    }
}
