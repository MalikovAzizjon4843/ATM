package uz.oyatjon.enums.atm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import uz.jl.utils.Color;
import uz.jl.utils.Input;
import uz.jl.utils.Print;

import static uz.jl.utils.Color.BLUE;
import static uz.jl.utils.Print.print;


@Getter
@AllArgsConstructor
public enum ATMType {
    UZCARD("Uzcard"),
    HUMO("Humo"),
    VISA("Visa"),
    VISA_UZCARD("Visa Uzcard"),
    VISA_HUMO("Visa Humo");
    private String description;
    public static void show(){
        for (ATMType value : ATMType.values()) {
            if(!value.equals(VISA_HUMO))
                print(BLUE,value+" or ");
            else
                print(BLUE,value);
        }
    }
    public static ATMType getByValue(String type){
        for (ATMType value : values()) {
            if(value.name().equals(type))
                return value;
        }
        return null;
    }
    public static void showCards(){
        for (ATMType value : values()) {
            Print.println(Color.BLACK,value.getDescription());
        }
    }
    public static ATMType getAtmType() {ATMType.showCards();
        String cardDes = Input.getStr("Card type: ");
        for (ATMType value : values()) {
            if (value.getDescription().equalsIgnoreCase(cardDes)) {
                return value;
            }
        }
        return getAtmType();
    }
}
