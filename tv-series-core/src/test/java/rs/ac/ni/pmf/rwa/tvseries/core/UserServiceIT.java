package rs.ac.ni.pmf.rwa.tvseries.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.ac.ni.pmf.rwa.tvseries.core.model.User;
import rs.ac.ni.pmf.rwa.tvseries.core.model.UserAccess;
import rs.ac.ni.pmf.rwa.tvseries.core.provider.UserProvider;
import rs.ac.ni.pmf.rwa.tvseries.core.service.UserService;
import rs.ac.ni.pmf.rwa.tvseries.exception.DuplicateUserException;
import rs.ac.ni.pmf.rwa.tvseries.exception.UnknownUserException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceIT {

    @Mock
    private UserProvider userProvider;

    @InjectMocks
    private UserService userService;

    @Test
    public void shouldGetUserByUsername( ){
        final User expectedUser = mock(User.class);
        when(userProvider.getUserByUsername("username"))
                .thenReturn(Optional.of(expectedUser));

        final User actualUser = userService.getUserByUsername("username");

        assertThat(actualUser).isEqualTo(expectedUser);

    }


    @Test
    public void shouldThrowWhenGetUserByUsername( ){
        when(userProvider.getUserByUsername("username")).thenReturn(Optional.empty());

        assertThatThrownBy(() ->userService.getUserByUsername("username") )
                .hasMessage("Unknown User with username 'username'")
                .isInstanceOf(UnknownUserException.class);

    }


    @Test
    public void shouldGetAllUsers( ){
        final List<User> expectedUsers = mock(List.class);
        when(userProvider.getAllUsers())
                .thenReturn(expectedUsers);

        final List<User> actualUsers = userService.getAllUsers();

        assertThat(actualUsers).isEqualTo(expectedUsers);

    }

    @Test
    public void shouldCreateUser(){
        final String username="username";

        final User user=mock(User.class);
        when(user.getUsername()).thenReturn(username);
        when(userProvider.getUserByUsername(username)).thenReturn(Optional.empty());

        userService.createUser(user);

        verify(userProvider).saveUser(user);

    }

    @Test
    public void shouldThrowWhenCreateUser() {


        final String username = "username";

        final User user = mock(User.class);
        when(user.getUsername()).thenReturn(username);
        when(userProvider.getUserByUsername(username)).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> userService.createUser(user))
                .isInstanceOf(DuplicateUserException.class)
                .hasMessage("Username '"+username+"' already taken!");
    }

    @Test
    public void shouldUpdateUser(){
        final String username="username";
        final User user=mock(User.class);
        when(user.getUsername()).thenReturn(username);
        when(userProvider.getUserByUsername(username)).thenReturn(Optional.of(user));

        userService.update(user,username);
        verify(userProvider).updateUser(user,username);

        /*When different Username is passed*/

        String username2="username";
        final  User user2 =mock(User.class);
        when(user2.getUsername()).thenReturn("anotherUsername");
        when(userProvider.getUserByUsername(username)).thenReturn(Optional.of(user));

        when(userProvider.getUserByUsername("anotherUsername")).thenReturn(Optional.empty());
        userService.update(user2,username2);
        verify(userProvider).updateUser(user2,username2);


    }

    @Test
    public void shouldThrowWhenUpdateUser(){
        String username="username";
        final User user=mock(User.class);

        when(userProvider.getUserByUsername(username)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.update(user,username))
                .isInstanceOf(UnknownUserException.class)
                .hasMessage("Unknown User with username 'username'");


        String username2="username";
        final  User user2 =mock(User.class);
        when(user2.getUsername()).thenReturn("anotherUsername");
        when(userProvider.getUserByUsername(username)).thenReturn(Optional.of(user));

        when(userProvider.getUserByUsername("anotherUsername")).thenReturn(Optional.of(user2));

        assertThatThrownBy(() -> userService.update(user2,username2))
                .isInstanceOf(DuplicateUserException.class)
                .hasMessage("Username 'anotherUsername' already taken!");




    }

    @Test
    public void shouldDeleteUser(){
        final String username="username";
        final User user=mock(User.class);
        when(userProvider.getUserByUsername(username)).thenReturn(Optional.of(user));

        userService.delete(username);
        verify(userProvider).removeUser(username);
    }


    @Test
    public void shouldThrowWhenDeleteUser(){
        final String username="username";
        when(userProvider.getUserByUsername(username)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.delete(username))
                .isInstanceOf(UnknownUserException.class);
    }

    @Test
    public void shouldUpdateUserAccess(){
        final String username="username";
        final User user=mock(User.class);
        final UserAccess userAccess=mock(UserAccess.class);
        when(userProvider.getUserByUsername(username)).thenReturn(Optional.of(user));

        userService.manageUsersAccess(username,userAccess);
        verify(userProvider).manageUsersAccess(username,userAccess);

    }

    @Test
    public void shouldThrowWhenUpdateUserAccess(){
        final String username="username";
        final UserAccess userAccess=mock(UserAccess.class);
        when(userProvider.getUserByUsername(username)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.manageUsersAccess(username,userAccess))
                .isInstanceOf(UnknownUserException.class)
                .hasMessage("Unknown User with username 'username'");

    }

    @Test
    public void shouldGetUserAccessByUsername( ){
        String username="username";
        final UserAccess expectedUserAccess = mock(UserAccess.class);

        final User user=mock(User.class);
        when(userProvider.getUserByUsername(username)).thenReturn(Optional.of(user));

        when(userProvider.getUsersAccess("username"))
                .thenReturn(Optional.of(expectedUserAccess));

        final UserAccess actualUserAccess = userService.getUsersAccess("username");

        assertThat(actualUserAccess).isEqualTo(expectedUserAccess);

    }

    @Test
    public void shouldThrowWhenGetUserAccessByUsername(){
        final String username="username";
        when(userProvider.getUserByUsername(username)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUsersAccess(username))
                .isInstanceOf(UnknownUserException.class)
                .hasMessage("Unknown User with username 'username'");

    }






}
