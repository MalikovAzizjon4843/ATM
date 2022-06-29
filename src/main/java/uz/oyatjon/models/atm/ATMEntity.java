package uz.oyatjon.models.atm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.oyatjon.enums.atm.ATMStatus;
import uz.oyatjon.enums.atm.ATMType;
import uz.oyatjon.models.Auditable;

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
