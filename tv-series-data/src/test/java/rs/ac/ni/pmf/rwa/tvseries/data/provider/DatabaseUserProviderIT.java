package rs.ac.ni.pmf.rwa.tvseries.data.provider;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.ac.ni.pmf.rwa.tvseries.core.model.User;
import rs.ac.ni.pmf.rwa.tvseries.core.model.UserAccess;
import rs.ac.ni.pmf.rwa.tvseries.data.dao.UserDao;
import rs.ac.ni.pmf.rwa.tvseries.data.entity.UserEntity;
import rs.ac.ni.pmf.rwa.tvseries.data.mapper.UserEntityMapper;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DatabaseUserProviderIT {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private DatabaseUserProvider databaseUserProvider;

    @Test
    public void shouldGetByUsername(){
        UserEntity entity=mock(UserEntity.class);
        String username="username";

        when(userDao.findByUsername(username)).thenReturn(Optional.of(entity));

        Optional<User>  returned=databaseUserProvider.getUserByUsername(username);
        assertThat(returned.isPresent()).isTrue();
        assertThat(returned.get()).isEqualTo(UserEntityMapper.fromEntity(entity));


        when(userDao.findByUsername(username)).thenReturn(Optional.empty());

        Optional<User>  returned2=databaseUserProvider.getUserByUsername(username);
        assertThat(returned2.isPresent()).isFalse();

    }

    @Test
    public void shouldGetAllUsers(){
        List<UserEntity> entities = mock(List.class);

        when(userDao.findAll()).thenReturn(entities);

        List<User> returned=databaseUserProvider.getAllUsers();
        List<User> expected=entities.stream()
                .map(UserEntityMapper::fromEntity)
                .toList();
        assertThat(returned).isEqualTo(expected);

    }

    @Test
    public void shouldSaveUsers( ){
        User user=mock(User.class);
        when(user.getUserAccess()).thenReturn(mock(UserAccess.class));
        databaseUserProvider.saveUser(user);
        verify(userDao).save(UserEntityMapper.toEntity(user));
    }

    @Test
    public void shouldRemoveTvSeries( ){
        String username="username";
        databaseUserProvider.removeUser(username);
        verify(userDao).deleteByUsername(username);
    }

    @Test
    public void shouldUpdateUsers( ){
        User user=mock(User.class);
        String username="username";

        UserEntity toBeUpdated=mock(UserEntity.class);
        when(userDao.findByUsername(username)).thenReturn(Optional.of(toBeUpdated));

        databaseUserProvider.updateUser(user,username);
        verify(userDao).save(toBeUpdated);
    }

    @Test
    public void shouldManageUsersAccess( ){
        String username="username";
        UserAccess userAccess=mock(UserAccess.class);

        UserEntity toBeUpdated=mock(UserEntity.class);
        when(userDao.findByUsername(username)).thenReturn(Optional.of(toBeUpdated));

        databaseUserProvider.manageUsersAccess(username,userAccess);
        verify(userDao).save(toBeUpdated);
    }


    @Test
    public void shouldGetUsersAccess(){
        UserEntity entity=mock(UserEntity.class);
        String username="username";

        when(userDao.findByUsername(username)).thenReturn(Optional.of(entity));

        Optional<UserAccess>  returned=databaseUserProvider.getUsersAccess(username);
        assertThat(returned.isPresent()).isTrue();
        assertThat(returned.get()).isEqualTo(UserEntityMapper.accessFromEntity(entity));


        when(userDao.findByUsername(username)).thenReturn(Optional.empty());

        Optional<UserAccess>  returned2=databaseUserProvider.getUsersAccess(username);
        assertThat(returned2.isPresent()).isFalse();

    }


}
