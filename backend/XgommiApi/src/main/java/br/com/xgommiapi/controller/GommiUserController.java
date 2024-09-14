package br.com.xgommiapi.controller;

import br.com.xgommiapi.domain.entity.GommiUser;
import br.com.xgommiapi.dto.GommiUserDTO;
import br.com.xgommiapi.exception.GommiUserNotFoundException;
import br.com.xgommiapi.exception.GommiUserNotUniqueException;
import br.com.xgommiapi.exception.GommiUserNullAtributeException;
import br.com.xgommiapi.service.GommiUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/xgommi/gommiuser")
public class GommiUserController {
    @Autowired
    private GommiUserService gommiUserService;

    public GommiUserController(GommiUserService gommiUserService) {
        this.gommiUserService = gommiUserService;
    }

    @PostMapping
    public ResponseEntity<GommiUser> post(@RequestBody GommiUserDTO gommiUserDTO) throws GommiUserNotUniqueException, GommiUserNullAtributeException {
        return new ResponseEntity<>(gommiUserService.createGommiUser(gommiUserDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<GommiUser>> getAll() {
        return new ResponseEntity<>(gommiUserService.getAllGommiUsers(), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<GommiUser> get(@PathVariable Long id) throws GommiUserNotFoundException {
        return new ResponseEntity<>(gommiUserService.getGommiUserById(id), HttpStatus.OK);
    }

    @GetMapping("/login/{login}")
    public ResponseEntity<GommiUser> getByLogin(@PathVariable String login) throws GommiUserNotFoundException {
        return new ResponseEntity<>(gommiUserService.getGommiUserByLogin(login), HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<GommiUser> getByEmail(@PathVariable String email) throws GommiUserNotFoundException {
        return new ResponseEntity<>(gommiUserService.getGommiUserByEmail(email), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<GommiUser> update(@RequestBody GommiUser gommiUser) throws GommiUserNotFoundException, GommiUserNotUniqueException, GommiUserNullAtributeException {
        return new ResponseEntity<>(gommiUserService.updateUser(gommiUser), HttpStatus.OK);
    }
}
