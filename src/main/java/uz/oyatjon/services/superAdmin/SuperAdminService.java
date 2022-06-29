package uz.oyatjon.services.superAdmin;

import uz.oyatjon.dao.auth.AuthUserDao;
import uz.oyatjon.dao.db.FRWAuthUser;
import uz.oyatjon.enums.auth.Role;
import uz.oyatjon.enums.auth.UserStatus;
import uz.oyatjon.enums.http.HttpStatus;
import uz.oyatjon.exceptions.APIException;
import uz.oyatjon.exceptions.APIRuntimeException;
import uz.oyatjon.mapper.AuthUserMapper;
import uz.oyatjon.models.auth.AuthUser;
import uz.oyatjon.models.branch.Branch;
import uz.oyatjon.response.ResponseEntity;
import uz.oyatjon.services.BaseAbstractService;
import uz.oyatjon.services.IBaseCrudService;
import uz.jl.utils.Color;
import uz.jl.utils.Print;

import java.util.List;
import java.util.Objects;


public class SuperAdminService
        extends BaseAbstractService<AuthUser, AuthUserDao, AuthUserMapper>
        implements IBaseCrudService<AuthUser> {

    private static SuperAdminService service;

    public static SuperAdminService getInstance(AuthUserDao repository, AuthUserMapper mapper) {
        if (Objects.isNull(service)) {
            service = new SuperAdminService(repository, mapper);
        }
        return service;
    }

    public SuperAdminService(AuthUserDao repository, AuthUserMapper mapper) {
        super(repository, mapper);
    }

    public ResponseEntity<String> create(String userName, String password, Branch branch) {
        if (!repository.finByUserNameBoolean(userName)) {
            AuthUser user = new AuthUser();
            user.setId(branch.getId());
            user.setUsername(userName);
            user.setPassword(password);
            user.setStatus(UserStatus.ACTIVE);
            user.setRole(Role.ADMIN);
            user.setDeleted(0);
            return create(user);
        }
        return new ResponseEntity<>("User Not Found Exception", HttpStatus.HTTP_404);
    }

    @Override
    public ResponseEntity<String> create(AuthUser authUser) {
        if (repository.finByUserNameBoolean(authUser.getUsername())) {
            return new ResponseEntity<>("Already exists", HttpStatus.HTTP_406);
        }

        FRWAuthUser.getInstance().getAll().add(authUser);
        FRWAuthUser.getInstance().writeAll(FRWAuthUser.getInstance().getAll());
        return new ResponseEntity<>("Successfully done", HttpStatus.HTTP_200);
    }

    public ResponseEntity<String> delete(String userName) {
        AuthUser authUser;
        try {
            authUser = repository.findByUserName(userName);
        } catch (APIException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.getStatusByCode(e.getCode()));
        }
        return delete(authUser);
    }

    @Override
    public ResponseEntity<String> delete(AuthUser authUser) {
        if (authUser.getDeleted() == 1 && authUser.getRole().equals(Role.SUPER_ADMIN)) {
            return new ResponseEntity<>("Already done", HttpStatus.HTTP_406);
        }

        authUser.setDeleted(1);
        FRWAuthUser.getInstance().writeAll(getAll());
        return new ResponseEntity<>("Successfully done", HttpStatus.HTTP_200);
    }

    public void list() {
        for (AuthUser authUser : FRWAuthUser.getInstance().getAll()) {
            if (authUser.getDeleted() == 0) {
                if (authUser.getStatus().equals(UserStatus.ACTIVE)) {
                    Print.println(Color.RED, authUser.getUsername());
                } else {
                    Print.println(Color.PURPLE, authUser.getUsername());
                }
            }
        }
    }

    @Override
    public AuthUser get(String id) {
        for (AuthUser authUser : FRWAuthUser.getInstance().getAll()) {
            if (id.equalsIgnoreCase(authUser.getId())) {
                return authUser;
            }
        }
        throw new APIRuntimeException("Admin not found", HttpStatus.HTTP_404.getCode());
    }

    @Override
    public List<AuthUser> getAll() {
        return FRWAuthUser.getInstance().getAll();
    }

    public ResponseEntity<String> block(String userName) {
        try {
            AuthUser authUser = repository.findByUserName(userName);
            if (authUser.getDeleted() == 1) {
                throw new APIException("Admin Not Found", HttpStatus.HTTP_404);
            }
            if (authUser.getStatus().equals(UserStatus.BLOCKED)) {
                return new ResponseEntity<>("Already done", HttpStatus.HTTP_406);
            }
            if (authUser.getStatus().equals(UserStatus.ACTIVE)) {
                authUser.setStatus(UserStatus.BLOCKED);
            }
            FRWAuthUser.getInstance().writeAll(getAll());
        } catch (APIException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.getStatusByCode(e.getCode()));
        }
        return new ResponseEntity<>("Successfully done", HttpStatus.HTTP_200);
    }

    public ResponseEntity<String> unblock(String userName) {
        try {
            AuthUser authUser = repository.findByUserName(userName);
            if (authUser.getDeleted() ==1 ) {
                throw new APIException("Admin Not Found", HttpStatus.HTTP_404);
            }
            if (authUser.getStatus().equals(UserStatus.ACTIVE)) {
                return new ResponseEntity<>("Already done", HttpStatus.HTTP_406);
            }
            if (authUser.getStatus().equals(UserStatus.BLOCKED)) {
                authUser.setStatus(UserStatus.ACTIVE);
            }
            FRWAuthUser.getInstance().writeAll(getAll());
        } catch (APIException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.getStatusByCode(e.getCode()));
        }
        return new ResponseEntity<>("Successfully done", HttpStatus.HTTP_200);
    }

    @Override
    public ResponseEntity<String> update(String id, AuthUser authUser) {
        return null;
    }

    public void blockList() {
        for (AuthUser authUser : FRWAuthUser.getInstance().getAll()) {
            if (authUser.getDeleted() == 0 && authUser.getStatus().equals(UserStatus.BLOCKED))
                Print.println(Color.RED, authUser.getUsername());
        }
    }

    public Integer blockCount() {
        Integer count = 0;
        for (AuthUser authUser : FRWAuthUser.getInstance().getAll()) {
            if (authUser.getDeleted() == 0 && authUser.getStatus().equals(UserStatus.BLOCKED))
                count++;
        }
        return count;
    }


}