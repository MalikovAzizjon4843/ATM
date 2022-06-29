package com.example.models.personal;

import com.example.enums.extras.Gender;
import com.example.models.Auditable;
import lombok.*;

import java.util.Date;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Passport extends Auditable {
    private String serial;
    private String number;
    private Gender gender;
    private String firstName;
    private String lastName;
    private String fatherName;
    private String ownerId;

    @Builder(builderMethodName = "childBuilder")
    public Passport(String id, Date createdAt, String createdBy, Date updatedAt, String updatedBy, int deleted, String serial, String number, Gender gender, String firstName, String lastName, String fatherName, String ownerId) {
        super(createdAt, createdBy, updatedAt, updatedBy, deleted);
        this.serial = serial;
        this.number = number;
        this.gender = gender;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fatherName = fatherName;
        this.ownerId = ownerId;
    }
}
