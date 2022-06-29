package uz.oyatjon.services.auth;

import uz.oyatjon.configs.Session;
import uz.oyatjon.dao.auth.AuthUserDao;
import uz.oyatjon.enums.http.HttpStatus;
import uz.oyatjon.exceptions.APIException;
import uz.oyatjon.mapper.AuthUserMapper;
import uz.oyatjon.models.auth.AuthUser;
import uz.oyatjon.response.ResponseEntity;
import uz.oyatjon.services.BaseAbstractService;
import uz.oyatjon.services.IBaseCrudService;

import java.util.List;
import java.util.Objects;

public class AuthService
        extends BaseAbstractService<AuthUser, AuthUserDao, AuthUserMapper>
        implements IBaseCrudService<AuthUser> {

    private static AuthService service;

    public static AuthService getInstance(AuthUserDao repository, AuthUserMapper mapper) {
        if (Objects.isNull(service)) {
            service = new AuthService(repository, mapper);
        }
        return service;
    }

    protected AuthService(AuthUserDao repository, AuthUserMapper mapper) {
        super(repository, mapper);
    }

    public ResponseEntity<String> login(String username, String password) {
        try {
            AuthUser user = repository.findByUserName(username);
            if (Objects.isNull(user) || !user.getPassword().equals(password))
                return new ResponseEntity<>("Bad Credentials", HttpStatus.HTTP_400);
            Session.getInstance().setUser(user);
            return new ResponseEntity<>("success", HttpStatus.HTTP_200);
        } catch (APIException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.getStatusByCode(e.getCode()));
        }
    }

    @Override
    public ResponseEntity<String> create(AuthUser authUser) {
        return null;
    }

    @Override
    public ResponseEntity<String> delete(AuthUser authUser) {
        return null;
    }

    @Override
    public AuthUser get(String id) {
        return null;
    }

    @Override
    public List<AuthUser> getAll() {
        return null;
    }

    @Override
    public ResponseEntity<String> update(String id, AuthUser authUser) {
        return null;
    }
}
