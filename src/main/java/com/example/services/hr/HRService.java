package com.example.services.hr;

import com.example.configs.Session;
import com.example.dao.auth.AuthUserDao;
import com.example.dao.db.FRWAuthUser;
import com.example.enums.auth.Role;
import com.example.enums.auth.UserStatus;
import com.example.enums.http.HttpStatus;
import com.example.exceptions.APIException;
import com.example.mapper.AuthUserMapper;
import com.example.models.auth.AuthUser;
import com.example.response.ResponseEntity;
import com.example.services.BaseAbstractService;
import com.example.services.IBaseCrudService;
import uz.jl.utils.Color;
import uz.jl.utils.Print;

import java.util.List;
import java.util.Objects;

public class HRService  extends BaseAbstractService<AuthUser, AuthUserDao, AuthUserMapper>
        implements IBaseCrudService<AuthUser> {

    private static HRService service;

    public static HRService getInstance(AuthUserDao repository, AuthUserMapper mapper) {
        if (Objects.isNull(service)) {
            service = new HRService(repository, mapper);
        }
        return service;
    }


    public HRService(AuthUserDao repository, AuthUserMapper mapper) {
        super(repository, mapper);
    }

    public ResponseEntity<String> create(String userName, String password) {
        if (!repository.finByHRNameBoolean1(userName)) {
            AuthUser authUser = new AuthUser();
            authUser.setUsername(userName);
            authUser.setPassword(password);
            authUser.setStatus(UserStatus.ACTIVE);
            authUser.setRole(Role.EMPLOYEE);
            authUser.setId(Session.getInstance().getUser().getId());
            return create(authUser);
        }
        return new ResponseEntity<>("User Not Found Exception", HttpStatus.HTTP_404);

    }

    @Override
    public ResponseEntity<String> create(AuthUser authUser) {
        FRWAuthUser.getInstance().writeAll(authUser);
        return new ResponseEntity<>("Successfully done", HttpStatus.HTTP_200);
    }

    @Override
    public ResponseEntity<String> delete(AuthUser authUser) {
        if (authUser.getDeleted() == 1 ){
            return new ResponseEntity<>("Already delete", HttpStatus.HTTP_406);
        }
        authUser.setDeleted(1);
        FRWAuthUser.getInstance().writeAll(getAll());
        return new ResponseEntity<>("Successfully done", HttpStatus.HTTP_200);

    }

    @Override
    public AuthUser get(String id) {
        return null;
    }

    @Override
    public List<AuthUser> getAll() {
      return FRWAuthUser.getInstance().getAll();

    }

    @Override
    public ResponseEntity<String> update(String id, AuthUser authUser) {
        return null;
    }

    public ResponseEntity<String> listHR() {
        boolean a = false;
        for (AuthUser authUser : FRWAuthUser.getInstance().getAll()) {
            if (authUser.getDeleted() == 0 && authUser.getRole().equals(Role.HR)) {
                     a=true;
                if (authUser.getStatus().equals(UserStatus.ACTIVE)) {
                    Print.println(Color.RED, authUser.getUsername());
                } else {
                    Print.println(Color.PURPLE, authUser.getUsername());
                }
            }
        }
            if (!a){
                return new ResponseEntity<>("User Not Found Exception", HttpStatus.HTTP_404);
            }
        return new ResponseEntity<>("Successfully done", HttpStatus.HTTP_200);
    }

    public ResponseEntity<String> deleteHR(String userName) {
        AuthUser authUser;
        try {
            authUser = repository.findByUserNameHR(userName);
        } catch (APIException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.getStatusByCode(e.getCode()));
        }
        return delete(authUser);
    }
}

