package uz.oyatjon.models.branch;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import uz.oyatjon.enums.branch.BranchStatus;
import uz.oyatjon.models.Auditable;

import java.util.Date;

@Getter
@Setter
public class Branch extends Auditable {
    private String name;
    private String bankId;
    private BranchStatus status;

    public Branch() {
        this.status = BranchStatus.ACTIVE;
    }

    @Builder(builderMethodName = "childBuilder", buildMethodName = "childBuild")
    public Branch(Date createdAt, String createdBy, Date updatedAt, String updatedBy, int deleted, String name, String bankId, BranchStatus status) {
        super(createdAt, createdBy, updatedAt, updatedBy, deleted);
        this.name = name;
        this.bankId = bankId;
        this.status = status;
    }
}
