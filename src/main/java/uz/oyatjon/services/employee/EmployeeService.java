package uz.oyatjon.services.employee;

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
import uz.oyatjon.services.IBaseCrudService2;
import uz.jl.utils.Color;
import uz.jl.utils.Print;
import uz.oyatjon.utils.BaseUtils;

import java.util.List;
import java.util.Objects;

import static uz.jl.utils.Color.PURPLE;
import static uz.jl.utils.Color.RED;
import static uz.jl.utils.Input.getStr;

public class EmployeeService extends BaseAbstractService<AuthUser, AuthUserDao, AuthUserMapper>
        implements IBaseCrudService2<AuthUser> {
    private static EmployeeService service;

    public static EmployeeService getInstance(AuthUserDao repository, AuthUserMapper mapper) {
        if (Objects.isNull(service)) {
            service = new EmployeeService(repository, mapper);
        }
        return service;
    }

    public EmployeeService(AuthUserDao repository, AuthUserMapper mapper) {
        super(repository, mapper);
    }


    public static int list() {
        int k = 1;
        List<AuthUser> users = FRWAuthUser.getInstance().getAll();
        for (AuthUser user : users) {
            if (user.getRole().equals(Role.EMPLOYEE)) {
                if (user.getStatus().equals(UserStatus.ACTIVE))
                    Print.println(k++ + ". " + PURPLE, user.getUsername());
                else
                    Print.println(k++ + ". " + RED, user.getUsername());
            }
        }
        if (k == 1) {
            Print.println(Color.RED, "Birorta ham user topilmadi");
        }
        return k;
    }

    public static void block() {
        List<AuthUser> users = FRWAuthUser.getInstance().getAll();
        if (list() == 1) {
            Print.println(RED, "Employee not found !");
            return;
        }
        String username = getStr("Username -> ");
        String name = findByUsername(username);
        if (Objects.nonNull(name)) {
            for (AuthUser user : users) {
                if (user.getUsername().equals(name)) {
                    if (user.getStatus().equals(UserStatus.BLOCKED)) {
                        Print.println(RED, "Bu Oldin bloklanganku 😐");
                        return;
                    }
                    user.setStatus(UserStatus.BLOCKED);
                    FRWAuthUser.getInstance().writeAll(users);
                    Print.println(PURPLE, "Successfully Blocked");
                    return;
                }
            }
        }
        Print.println(RED, "Employee not found !");
    }

    public static void unBlock() {
        List<AuthUser> users = FRWAuthUser.getInstance().getAll();
        if (blockList() == 1) {
            Print.println(RED, "Employee not found !");
            return;
        }
        String username = getStr("Username -> ");
        String name = findByUsername(username);
        if (Objects.nonNull(name)) {
            for (AuthUser user : users) {
                if (user.getUsername().equals(name)) {
                    user.setStatus(UserStatus.ACTIVE);
                    FRWAuthUser.getInstance().writeAll(users);
                    Print.println(PURPLE, "Successfully UnBlocked");
                    return;
                }
            }
        }
        Print.println(RED, "Employee not found !");

    }

    public static int blockList() {
        int k = 1;
        List<AuthUser> users = FRWAuthUser.getInstance().getAll();
        for (AuthUser user : users) {
            if (user.getStatus().equals(UserStatus.BLOCKED)) {
                Print.println(k++ + ". " + RED, user.getUsername());
                k++;
            }
        }
        return k;
    }

    private static String findByUsername(String userName) {
        List<AuthUser> users = FRWAuthUser.getInstance().getAll();
        for (AuthUser user : users) {
            if (user.getUsername().equals(userName)) {
                return userName;
            }
        }
        return null;
    }


    @Override
    public ResponseEntity<String> create(AuthUser authUser) throws APIException {
        AuthUser user = repository.findByUserName(authUser.getUsername());
        if (Objects.nonNull(user)) {
            return new ResponseEntity<>("Already exists", HttpStatus.HTTP_406);
        }
        authUser.setId(BaseUtils.genId());
        authUser.setStatus(UserStatus.ACTIVE);
        authUser.setRole(Role.CLIENT);
        authUser.setCreatedBy(Session.getInstance().getUser().getId());
        authUser.setBranchID(Session.getInstance().getUser().getBranchID());
        authUser.setLanguage(Session.getInstance().getUser().getLanguage());
        FRWAuthUser.getInstance().writeAll(authUser);
        return new ResponseEntity<>("Successfully done", HttpStatus.HTTP_200);
    }

    @Override
    public ResponseEntity<String> delete(String name) {
        List<AuthUser> users = FRWAuthUser.getInstance().getAll();
        if (list() == 1) {
            return new ResponseEntity<>("Employee not found !", HttpStatus.HTTP_404);
        }
        String findName = findByUsername(name);
        if (Objects.nonNull(findName)) {
            for (AuthUser user : users) {
                if (user.getUsername().equals(findName)) {
                    users.remove(user);
                    FRWAuthUser.getInstance().writeAll(users);
                    return new ResponseEntity<>("Successfully Deleted !", HttpStatus.HTTP_404);
                }
            }
        }
        return new ResponseEntity<>("Employee not found !", HttpStatus.HTTP_404);
    }

    @Override
    public ResponseEntity<String> get(String name) {
        return null;
    }

    @Override
    public ResponseEntity<List<AuthUser>> getAll() {
        return null;
    }

    @Override
    public ResponseEntity<String> update(String name) {
        return null;
    }
}
