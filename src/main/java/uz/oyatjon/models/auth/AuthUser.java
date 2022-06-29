package uz.oyatjon.models.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import uz.oyatjon.configs.AppConfig;
import uz.oyatjon.enums.auth.Role;
import uz.oyatjon.enums.auth.UserStatus;
import uz.oyatjon.models.Auditable;
import uz.oyatjon.models.card.Cards;
import uz.oyatjon.models.personal.Passport;
import uz.oyatjon.models.settings.Language;

import java.util.ArrayList;
import java.util.Date;


@Getter
@Setter
public class AuthUser extends Auditable {
    private String username;
    private String password;
    private String bankId;
    private String branchID;
    private Role role;
    private UserStatus status;
    private String phoneNumber;
    private Language language;
    private Passport passport;
    private ArrayList<Cards> cards = new ArrayList<>(1);

    public AuthUser() {
        super();
        this.role = Role.ANONYMOUS;
        this.language = AppConfig.language;
    }

    @Builder(builderMethodName = "childBuilder", buildMethodName = "childBuild")
    public AuthUser(Date createdAt, String createdBy, Date updatedAt, String updatedBy, int deleted, String username, String password, String bankId, Role role, UserStatus status, String phoneNumber, Language language) {
        super(createdAt, createdBy, updatedAt, updatedBy, deleted);
        this.username = username;
        this.password = password;
        this.bankId = bankId;
        this.role = role;
        this.status = status;
        this.phoneNumber = phoneNumber;
        this.language = language;
    }
}
