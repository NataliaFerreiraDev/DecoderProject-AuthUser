package br.com.decoder.ead.authuser.controllers;

import br.com.decoder.ead.authuser.dto.UserDto;
import br.com.decoder.ead.authuser.models.UserModel;
import br.com.decoder.ead.authuser.services.UserService;
import br.com.decoder.ead.authuser.specifications.SpecificationTemplate;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/users")
public class UserController {

    private static final String USER_NOT_FOUND = "User not found";

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserModel>> getAllUsers(SpecificationTemplate.UserSpec spec,
                                                       @PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC)
                                                       Pageable pageable,
                                                       @RequestParam(required = false) UUID courseId) {

        Page<UserModel> userModelPage = null;
        if(courseId != null){
            userModelPage = userService.findAll(SpecificationTemplate.userCourseId(courseId).and(spec), pageable);
        } else {
            userModelPage = userService.findAll(spec, pageable);
        }

        if(!userModelPage.isEmpty()){
            for (UserModel user: userModelPage.toList()) {
                user.add(linkTo(methodOn(UserController.class).getOneUser(user.getUserId())).withSelfRel());
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(userModelPage);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getOneUser(@PathVariable(value = "userId") UUID userId) {
        Optional<UserModel> userModelOptional = userService.findById(userId);

        return userModelOptional.<ResponseEntity<Object>>map(
                        userModel -> ResponseEntity.status(HttpStatus.OK).body(userModel))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(USER_NOT_FOUND));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "userId") UUID userId) {
        Optional<UserModel> userModelOptional = userService.findById(userId);

        if (userModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(USER_NOT_FOUND);
        } else {
            userService.delete(userModelOptional.get());
            return ResponseEntity.status(HttpStatus.OK).body("User deleted success");
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "userId") UUID userId,
                                             @RequestBody @Validated(UserDto.UserView.UserPut.class)
                                             @JsonView(UserDto.UserView.UserPut.class) UserDto userDto) {
        Optional<UserModel> userModelOptional = userService.findById(userId);

        if (userModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(USER_NOT_FOUND);
        } else {
            var userModel = userModelOptional.get();
            userModel.setFullName(userDto.getFullName());
            userModel.setPhoneNumber(userDto.getPhoneNumber());
            userModel.setCpf(userDto.getCpf());
            userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
            userService.save(userModel);

            return ResponseEntity.status(HttpStatus.OK).body(userModel);
        }
    }

    @PutMapping("/{userId}/password")
    public ResponseEntity<Object> updatePassword(@PathVariable(value = "userId") UUID userId,
                                                 @RequestBody @Validated(UserDto.UserView.PasswordPut.class)
                                                 @JsonView(UserDto.UserView.PasswordPut.class) UserDto userDto) {
        Optional<UserModel> userModelOptional = userService.findById(userId);

        if (userModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(USER_NOT_FOUND);
        }
        if (!userModelOptional.get().getPassword().equals(userDto.getOldPassword())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Mismatched old password! ");
        } else {
            var userModel = userModelOptional.get();
            userModel.setPassword(userDto.getPassword());
            userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
            userService.save(userModel);

            return ResponseEntity.status(HttpStatus.OK).body("Password updated successfuly.");
        }
    }

    @PutMapping("/{userId}/image")
    public ResponseEntity<Object> updateImage(@PathVariable(value = "userId") UUID userId,
                                              @RequestBody @Validated(UserDto.UserView.ImagePut.class)
                                              @JsonView(UserDto.UserView.ImagePut.class) UserDto userDto) {
        Optional<UserModel> userModelOptional = userService.findById(userId);

        if (userModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(USER_NOT_FOUND);
        } else {
            var userModel = userModelOptional.get();
            userModel.setImageUrl(userDto.getImageUrl());
            userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
            userService.save(userModel);

            return ResponseEntity.status(HttpStatus.OK).body(userModel);
        }
    }

}
