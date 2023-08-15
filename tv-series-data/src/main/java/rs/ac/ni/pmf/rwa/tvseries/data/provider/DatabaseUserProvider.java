package rs.ac.ni.pmf.rwa.tvseries.data.provider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rs.ac.ni.pmf.rwa.tvseries.core.model.TvSeries;
import rs.ac.ni.pmf.rwa.tvseries.core.model.User;
import rs.ac.ni.pmf.rwa.tvseries.core.model.UserAccess;
import rs.ac.ni.pmf.rwa.tvseries.core.provider.UserProvider;
import rs.ac.ni.pmf.rwa.tvseries.data.dao.TvSeriesDao;
import rs.ac.ni.pmf.rwa.tvseries.data.dao.UserDao;
import rs.ac.ni.pmf.rwa.tvseries.data.entity.TvSeriesEntity;
import rs.ac.ni.pmf.rwa.tvseries.data.entity.UserEntity;
import rs.ac.ni.pmf.rwa.tvseries.data.mapper.TvSeriesEntityMapper;
import rs.ac.ni.pmf.rwa.tvseries.data.mapper.UserEntityMapper;
import rs.ac.ni.pmf.rwa.tvseries.exception.UnknownUserException;
import rs.ac.ni.pmf.rwa.tvseries.shared.Roles;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class DatabaseUserProvider implements UserProvider {

    private final UserDao userDao;

    @Override
    public Optional<User> getUserByUsername(String username) {
        Optional<UserEntity> optionalUserEntity=userDao.findByUsername(username);

        if(optionalUserEntity.isEmpty()){
            log.info("User with username[{}] is not founded",username);
            return Optional.empty();
        }
        UserEntity userEntity=optionalUserEntity.get();


        log.info("Returned User with username [{}]",username);
        return Optional.ofNullable(UserEntityMapper.fromEntity(userEntity));
    }

    @Override
    public List<User> getAllUsers() {
        log.info("Returned list of all Users");
        return userDao.findAll().stream()
                .map(UserEntityMapper::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void saveUser(User user) {
        UserEntity entity=UserEntityMapper.toEntity(user);
        log.info("Saved User");
        userDao.save(entity);
    }

    @Override
    public void removeUser(String username) {

        log.info("Deleted User with username[{}]",username);
        userDao.deleteByUsername(username);
    }

    @Override
    public void updateUser(User user,String username) {
        UserEntity existing=userDao.findByUsername(username).orElseThrow(()->new UnknownUserException(username));
        existing.setUsername(user.getUsername());
        existing.setPassword(user.getPassword());
        log.info("Updated User");
        userDao.save(existing);
    }

    @Override
    public void manageUsersAccess(String username, UserAccess userAccess) {
        UserEntity user=userDao.findByUsername(username).orElseThrow(()->new UnknownUserException(username));
        user.setRole(userAccess.getRole());
        user.setAccountNonExpired(userAccess.isAccountNonExpired());
        user.setAccountNonLocked(userAccess.isAccountNonLocked());
        user.setCredentialsNonExpired(userAccess.isCredentialsNonExpired());
        user.setEnabled(userAccess.isEnabled());
        userDao.save(user);
    }

    @Override
    public Optional<UserAccess> getUsersAccess(String username) {
        Optional<UserEntity> optionalUserEntity=userDao.findByUsername(username);

        if(optionalUserEntity.isEmpty()){
            log.info("User with username[{}] is not founded",username);
            return Optional.empty();
        }
        UserEntity userEntity=optionalUserEntity.get();

        return Optional.of(UserEntityMapper.accessFromEntity(userEntity));
    }


}
