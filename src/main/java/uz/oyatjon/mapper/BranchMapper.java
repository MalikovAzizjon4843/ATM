package uz.oyatjon.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BranchMapper {
    private static BranchMapper mapper;

    public static BranchMapper getInstance() {
        if (Objects.isNull(mapper)) {
            mapper = new BranchMapper();
        }
        return mapper;
    }
}
