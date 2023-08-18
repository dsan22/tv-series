package rs.ac.ni.pmf.rwa.tvseries.data.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import rs.ac.ni.pmf.rwa.tvseries.data.TestConfiguration;
import rs.ac.ni.pmf.rwa.tvseries.data.entity.UserEntity;
import rs.ac.ni.pmf.rwa.tvseries.shared.Roles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ContextConfiguration(classes = TestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserDaoIT {
    @Autowired
    private UserDao userDao;

    private static  final List<UserEntity> DEFAULT_USERS= Arrays.asList(
            UserEntity
                    .builder()
                    .username("username1")
                    .password("password1")
                    .role(Roles.ADMIN)
                    .enabled(true)
                    .credentialsNonExpired(true)
                    .accountNonLocked(true)
                    .accountNonExpired(true)
                    .build(),
            UserEntity
                    .builder()
                    .username("username2")
                    .password("password2")
                    .role(Roles.USER)
                    .enabled(true)
                    .credentialsNonExpired(true)
                    .accountNonLocked(true)
                    .accountNonExpired(true)
                    .build(),
            UserEntity
                    .builder()
                    .username("username3")
                    .password("password3")
                    .role(Roles.GUEST)
                    .enabled(true)
                    .credentialsNonExpired(true)
                    .accountNonLocked(true)
                    .accountNonExpired(true)
                    .build()
    );

    @BeforeEach
    public void initData()
    {
        userDao.saveAll(DEFAULT_USERS);
    }


    @Test
    public void shouldGetAllUsers()
    {
        final List<UserEntity> users = userDao.findAll();
        assertThat(users).hasSize(DEFAULT_USERS.size());
    }

    @Test
    public void shouldSaveTvSeries()
    {
        final UserEntity toSave =UserEntity.builder().build();
        userDao.save(toSave);
        assertThat(userDao.count()).isEqualTo(DEFAULT_USERS.size()+1);
    }

    @Test
    public void shouldFindUserByUsername()
    {

        final Optional<UserEntity> founded =userDao.findByUsername(DEFAULT_USERS.get(0).getUsername());
        assertThat(founded.isPresent()).isTrue();
        assertThat(founded.get().getId()).isEqualTo(DEFAULT_USERS.get(0).getId());

    }

    @Test
    public void shouldDeleteUSerByUsername()
    {
        userDao.deleteByUsername(DEFAULT_USERS.get(0).getUsername());
        assertThat(userDao.count()).isEqualTo(DEFAULT_USERS.size()-1);

    }
}
