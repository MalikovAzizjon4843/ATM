package uz.oyatjon.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;



@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthUserMapper {
    private static AuthUserMapper mapper;

    public static AuthUserMapper getInstance() {
        if (Objects.isNull(mapper)) {
            mapper = new AuthUserMapper();
        }
        return mapper;
    }
}
