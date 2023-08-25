package rs.ac.ni.pmf.rwa.tvseries.rest.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.ni.pmf.rwa.tvseries.core.model.User;
import rs.ac.ni.pmf.rwa.tvseries.core.service.UserService;
import rs.ac.ni.pmf.rwa.tvseries.rest.dto.user.UserAccessDTO;
import rs.ac.ni.pmf.rwa.tvseries.rest.dto.user.UserDTO;
import rs.ac.ni.pmf.rwa.tvseries.rest.mapper.user.UserAccessMapper;
import rs.ac.ni.pmf.rwa.tvseries.rest.mapper.user.UserMapper;

import java.util.List;
import java.util.stream.Collectors;

@SecurityRequirement(name = "default")
@RestController
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    private final UserMapper userMapper;
    private final UserAccessMapper userAccessMapper;
    @GetMapping("/users")
    public List<UserDTO> getAllUsers()
    {
        return userService.getAllUsers().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @PreAuthorize("#username == authentication.name || hasAuthority('ADMIN')")
    @GetMapping("/users/{username}")
    public UserDTO getUserByUsername(@PathVariable(name = "username") final String username)
    {
        final User user = userService.getUserByUsername(username);
        return userMapper.toDto(user);
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody final UserDTO userDTO)
    {

        userService.createUser(userMapper.fromDto(userDTO));
    }
    @PreAuthorize("#username == authentication.name || hasAuthority('ADMIN')")
    @PutMapping("/users/{username}")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateUser(@RequestBody final UserDTO userDTO,@PathVariable(value = "username") String username)
    {
        userService.update(userMapper.fromDto(userDTO), username);
    }
    @PreAuthorize("#username == authentication.name || hasAuthority('ADMIN')")
    @DeleteMapping("/users/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable(value = "username") String username)
    {
        userService.delete(username);
    }


    @PutMapping("/users/{username}/manage-access")
    @ResponseStatus(HttpStatus.CREATED)
    public void manageUsersAccess(@RequestBody UserAccessDTO userAccessDTO, @PathVariable(value = "username") String username)
    {
        userService.manageUsersAccess(username,userAccessMapper.fromDto(userAccessDTO) );
    }

    @GetMapping("/users/{username}/show-access")
    @ResponseStatus(HttpStatus.OK)
    public UserAccessDTO showUsersAccess( @PathVariable(value = "username") String username)
    {
        return userAccessMapper.toDto(userService.getUsersAccess(username)) ;
    }



}
