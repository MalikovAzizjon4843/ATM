package uz.oyatjon.utils;

import org.apache.commons.lang3.RandomStringUtils;
import uz.jl.utils.Color;
import uz.jl.utils.Print;

import java.util.Scanner;


public class BaseUtils {
    public static Scanner doubleSCr = new Scanner(System.in);
    public static String genId() {
        return System.nanoTime() + RandomStringUtils.random(20, true, true);
    }
    public static double getKoordinata(){
        try{
            Print.print(Color.WHITE, "Enter your koordinata: ");
            return doubleSCr.nextDouble();
        }catch (Exception e){
            Print.println(Color.RED,"Please, Enter number: ");
        }
        return getKoordinata();
    }

}
