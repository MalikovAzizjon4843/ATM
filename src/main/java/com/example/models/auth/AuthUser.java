package com.example.models.auth;

import com.example.configs.AppConfig;
import com.example.enums.auth.Role;
import com.example.enums.auth.UserStatus;
import com.example.models.Auditable;
import com.example.models.card.Cards;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import com.example.models.personal.Passport;
import com.example.models.settings.Language;

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
