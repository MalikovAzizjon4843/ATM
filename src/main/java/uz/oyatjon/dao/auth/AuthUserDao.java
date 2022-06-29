package uz.oyatjon.dao.auth;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import uz.oyatjon.dao.atm.BaseDao;
import uz.oyatjon.dao.db.FRWAuthUser;
import uz.oyatjon.enums.auth.Role;
import uz.oyatjon.enums.http.HttpStatus;
import uz.oyatjon.exceptions.APIException;
import uz.oyatjon.models.atm.ATMEntity;
import uz.oyatjon.models.auth.AuthUser;

import java.util.List;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthUserDao extends BaseDao<AuthUser> {
    FRWAuthUser frwAuthUser = FRWAuthUser.getInstance();

    private static AuthUserDao dao;

    public static AuthUserDao getInstance() {
        if (Objects.isNull(dao))
            dao = new AuthUserDao();
        return dao;
    }

    public boolean finByUserNameBoolean(String name) {
        for (AuthUser authUser : frwAuthUser.getAll()) {
            if (name.equalsIgnoreCase(authUser.getUsername())) {
                return true;
            }
        }
        return false;
    }


    public AuthUser findByUserName(String username) throws APIException {
        for (AuthUser user : frwAuthUser.getAll()) {
            if (Objects.nonNull(user) && user.getUsername().equals(username)) return user;
        }
        return null;
    }

    public boolean finByUserNameBoolean1(String userName) {
        for (AuthUser authUser : frwAuthUser.getAll()) {
            if (userName.equalsIgnoreCase(authUser.getUsername()) && authUser.getRole().equals(Role.HR)) {
                return true;
            }
        }
        return false;
    }
    public AuthUser findByUserNameHR(String userName) throws APIException {
        for (AuthUser user : frwAuthUser.getAll()) {
            if (user.getUsername().equals(userName) && user.getRole().equals(Role.HR)) return user;
        }
        throw new APIException("User Not Found Exception", HttpStatus.HTTP_404);
    }

    public boolean finByHRNameBoolean1(String userName) {
        for (AuthUser authUser : frwAuthUser.getAll()) {
            if (userName.equalsIgnoreCase(authUser.getUsername()) && authUser.getRole().equals(Role.EMPLOYEE)) {
                return true;
            }
        }
        return false;
    }

    public AuthUserDao(String path) {
        super(path);
    }

    @Override
    public List<ATMEntity> getALL() {
        return null;
    }

    @Override
    public void writerALL(List<ATMEntity> dataList) {

    }
}
