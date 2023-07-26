package br.com.decoder.ead.authuser.controllers;

import br.com.decoder.ead.authuser.dto.InstructorDto;
import br.com.decoder.ead.authuser.enums.UserType;
import br.com.decoder.ead.authuser.models.UserModel;
import br.com.decoder.ead.authuser.services.UserService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/instructors")
public class InstructorController {

    @Autowired
    UserService userService;

    private static final String USER_NOT_FOUND = "User not found";

    @PostMapping("/subscription")
    public ResponseEntity<Object> saveSubscriptionInstructor(@RequestBody @Valid InstructorDto instructorDto){

        Optional<UserModel> userModelOptional = userService.findById(instructorDto.getUserId());
        if(!userModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(USER_NOT_FOUND);
        } else {
            var userModel = userModelOptional.get();
            userModel.setUserType(UserType.INSTRUCTOR);
            userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
            userService.save(userModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(userModel);
        }
    }

}
