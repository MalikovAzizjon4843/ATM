package com.example.models.atm;

import com.example.enums.atm.ATMStatus;
import com.example.enums.atm.ATMType;
import com.example.models.Auditable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ATMEntity extends Auditable {
    private String bankId;
    private String branchID;
    private ATMType type;
    private String name;
    private ATMStatus status;
    private double latitude;
    private double longitude;
    private ArrayList<Cassette> cassettes = new ArrayList<>(4);


    public String getATM() {
        return "ATM: " +
                " type=" + type +
                ", name='" + name  +
                ", status=" + status +
                ", latitude=" + latitude +
                ", longitude=" + longitude;
    }


}
