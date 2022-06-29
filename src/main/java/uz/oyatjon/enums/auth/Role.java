package uz.oyatjon.enums.auth;


public enum Role {
    SUPER_ADMIN,
    ADMIN,
    EMPLOYEE,
    HR,
    CLIENT,
    ANONYMOUS;

    public boolean in(Role... roles) {
        for (Role role : roles) {
            if (role.equals(this)) return true;
        }
        return false;
    }

    public boolean notIn(Role... roles) {
        return !in(roles);
    }
}
