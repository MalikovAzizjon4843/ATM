package uz.oyatjon.services.admin;

import uz.oyatjon.configs.Session;
import uz.oyatjon.dao.auth.AuthUserDao;
import uz.oyatjon.dao.db.FRWAuthUser;
import uz.oyatjon.enums.auth.Role;
import uz.oyatjon.enums.auth.UserStatus;
import uz.oyatjon.enums.http.HttpStatus;
import uz.oyatjon.exceptions.APIException;
import uz.oyatjon.mapper.AuthUserMapper;
import uz.oyatjon.models.auth.AuthUser;
import uz.oyatjon.response.ResponseEntity;
import uz.oyatjon.services.BaseAbstractService;
import uz.oyatjon.services.IBaseCrudService;
import uz.jl.utils.Color;
import uz.jl.utils.Print;

import java.util.List;
import java.util.Objects;

import static uz.jl.utils.Color.RED;
import static uz.jl.utils.Print.println;

/**
 * @author Azizjon   Data : 16.12.2021 :  Time : 19:51
 */


public class AdminService extends BaseAbstractService<AuthUser, AuthUserDao, AuthUserMapper>
        implements IBaseCrudService<AuthUser> {


    private static AdminService service;

    public static AdminService getInstance(AuthUserDao repository, AuthUserMapper mapper) {
        if (Objects.isNull(service)) {
            service = new AdminService(repository, mapper);
        }
        return service;
    }

    public ResponseEntity<String> createHR(String userName, String password) {
        if (!repository.finByUserNameBoolean1(userName)) {
            AuthUser authUser = new AuthUser();
            authUser.setUsername(userName);
            authUser.setPassword(password);
            authUser.setStatus(UserStatus.ACTIVE);
            authUser.setRole(Role.HR);
            authUser.setId(Session.getInstance().getUser().getId());
            return create(authUser);
        }
        return new ResponseEntity<>("User Not Found Exception", HttpStatus.HTTP_404);

    }

    @Override
    public ResponseEntity<String> create(AuthUser authUser) {
        getAll().add(authUser);
        FRWAuthUser.getInstance().writeAll(getAll());
        return new ResponseEntity<>("Successfully done", HttpStatus.HTTP_200);
    }


    public ResponseEntity<String> deleteHR(String userName) {
        AuthUser user = null;
        try {
            user = repository.findByUserNameHR(userName);
        } catch (APIException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.getStatusByCode(e.getCode()));
        }
        if (user.getDeleted() == 1 || user.getRole().equals(Role.HR)) {
            return new ResponseEntity<>("Already done", HttpStatus.HTTP_406);
        }
        user.setDeleted(1);
        return delete(user);
    }

    public ResponseEntity<String> blockHR(String userName) {
        AuthUser user=null;
        try {
            user = repository.findByUserNameHR(userName);
        } catch (APIException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.getStatusByCode(e.getCode()));
        }
        if (user.getDeleted() == 1 ){
            return new ResponseEntity<>("Already done", HttpStatus.HTTP_406);
        }
        user.setStatus(UserStatus.BLOCKED);
        FRWAuthUser.getInstance().writeAll(user);
        return new ResponseEntity<>("Successfully done", HttpStatus.HTTP_200);


    }

    @Override
    public ResponseEntity<String> delete(AuthUser authUser) {
        FRWAuthUser.getInstance().writeAll(authUser);
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

    public AdminService(AuthUserDao repository, AuthUserMapper mapper) {
        super(repository, mapper);
    }


    public ResponseEntity<String> list() {

        boolean a = false;
        for (AuthUser authUser : FRWAuthUser.getInstance().getAll()) {
            if (authUser.getDeleted() == 0) {
                a = true;
                if (authUser.getStatus().equals(UserStatus.ACTIVE)) {
                    Print.println(Color.RED, authUser.getUsername());
                } else {
                    Print.println(Color.PURPLE, authUser.getUsername());
                }
            }
        }
        if (a) {
            return new ResponseEntity<>("Successfully done", HttpStatus.HTTP_200);
        }
        return new ResponseEntity<>("HR not found", HttpStatus.HTTP_404);
    }

    public ResponseEntity<String> unBlocklist() {
        int a= 0 ;
        boolean b= false;
        for (AuthUser authUser : service.getAll()) {
            if (authUser.getRole().equals(Role.HR) && authUser.getStatus().equals(UserStatus.ACTIVE)){
                b=true;
                println(RED,++a + ". " + authUser.getUsername());
            }
        }
        if (!b){
            return new ResponseEntity<>("Already done", HttpStatus.HTTP_406);
        }

        return new ResponseEntity<>("Successfully done", HttpStatus.HTTP_200);
    }

    public ResponseEntity<String> unBlock(String userName) {

        AuthUser user=null;
        try {
            user = repository.findByUserNameHR(userName);
        } catch (APIException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.getStatusByCode(e.getCode()));
        }
        if (user.getDeleted() == 1 ){
            return new ResponseEntity<>("Already done", HttpStatus.HTTP_406);
        }
        user.setStatus(UserStatus.ACTIVE);
        FRWAuthUser.getInstance().writeAll(user);
        return new ResponseEntity<>("Successfully done", HttpStatus.HTTP_200);

    }

    public ResponseEntity<String> blockHRList() {

        int a=0 ;
        boolean b= false;
        for (AuthUser authUser : service.getAll()) {
            if (authUser.getRole().equals(Role.HR) && authUser.getStatus().equals(UserStatus.BLOCKED)){
                b=true;
                println(RED,++a + ". " + authUser.getUsername());
            }
        }
        if (!b){
            return new ResponseEntity<>("Already done", HttpStatus.HTTP_406);
        }
        return new ResponseEntity<>("Successfully done", HttpStatus.HTTP_200);
    }
}

