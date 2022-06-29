package uz.oyatjon.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;



@Getter
@Setter
@ToString
public class Auditable implements BaseEntity {
    private String id;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;
    private int deleted;

    public Auditable() {
        this.createdAt = new Date();
    }

    @Builder(builderMethodName = "parentBuilder", buildMethodName = "parentBuild")
    public Auditable(Date createdAt, String createdBy, Date updatedAt, String updatedBy, int deleted) {
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.deleted = deleted;
    }
    public Auditable(String id,String createdBy){
        this.id=id;
        this.createdBy=createdBy;
        this.createdAt=new Date();
    }
}
