package uz.oyatjon.services.atm;

import uz.oyatjon.configs.Session;
import uz.oyatjon.dao.atm.ATMDao;
import uz.oyatjon.dao.db.FRWATMEntity;
import uz.oyatjon.enums.atm.ATMStatus;
import uz.oyatjon.enums.atm.ATMType;
import uz.oyatjon.enums.atm.CassetteStatus;
import uz.oyatjon.enums.atm.Status;
import uz.oyatjon.enums.http.HttpStatus;
import uz.oyatjon.mapper.ATMMapper;
import uz.oyatjon.models.atm.ATMEntity;
import uz.oyatjon.models.atm.Cassette;
import uz.oyatjon.models.card.Cards;
import uz.oyatjon.response.ResponseEntity;
import uz.oyatjon.services.BaseAbstractService;
import uz.oyatjon.services.IBaseCrudService2;
import uz.oyatjon.utils.BaseUtils;
import uz.jl.utils.Color;
import uz.jl.utils.Input;
import uz.jl.utils.Print;

import java.util.List;
import java.util.Objects;

import static uz.jl.utils.Color.RED;
import static uz.jl.utils.Input.getNum;
import static uz.jl.utils.Input.getStr;
import static uz.jl.utils.Print.println;


public class AtmService extends BaseAbstractService<ATMEntity, ATMDao, ATMMapper> implements IBaseCrudService2<ATMEntity> {
    static ATMDao AtmEntity = new ATMDao();
    static List<ATMEntity> BaseList = AtmEntity.getALL();

    public AtmService(ATMDao repository, ATMMapper mapper) {
        super(repository, mapper);
    }

    private static AtmService service;

    public static AtmService getInstance(ATMDao repository, ATMMapper mapper) {
        if (Objects.isNull(service))
            return new AtmService(repository, mapper);
        return service;
    }

    public static void atmService() {
        list();
        String atmName = getStr("Atm name : ");
        ATMEntity atmEntity= findByAtmName(atmName);
        if (Objects.nonNull(atmEntity)){
        String code = getStr("Pin code: ");
        for (Cards card : Session.getInstance().getUser().getCards()) {
            if (Objects.nonNull(card) && card.getPassword().equals(code) && card.getStatus().equals(Status.ACTIVE)) {
                atmMenu(card,atmEntity);
                return;
            }else if (Objects.nonNull(card) && card.getPassword().equals(code)&& card.getStatus().equals(Status.BLOCKED)) {
                println(RED, "Card blocked");
                return;
            }
        }
        println(RED, "Wrong password");
        atmService();
        }else {
            println(RED,"Atm not found");
            atmService();
        }

    }



    private static void atmMenu(Cards card, ATMEntity atmEntity) {
        String choice = getStr("🔵Pays-1 \t🔵Balance-2\n🔵SMS code-3 🔵Change password-4\n🔵Withdraw-5\t  🔵Exit-6 => ");
        switch (choice) {
            case "1" -> pays(card);
            case "2" -> balance(card);
            case "3" -> smsCode(card);
            case "4" -> changePassword(card);
            case "5" -> withdraw(card,atmEntity);
            case "6" -> {
                println(Color.CYAN, "Bye");
                return;
            }
            default -> {
                println(RED, "Wrong choice");
            }
        }
        atmMenu(card, atmEntity);
    }

    private static void balance(Cards card) {
        Print.print(Color.BLACK,"Your balance: ");
        println(Color.RESET,card.getBalance());
    }

    private static void smsCode(Cards card) {
        if (Objects.isNull(card.getNumber())) {
            String number = getStr("SMS message on number: ");
            card.setNumber(number);
        }else if (Objects.nonNull(card.getNumber())){
            String number = getStr("SMS message off number: ");
            if (!card.getNumber().equals(number)){
                Print.print(RED,"Wrong number: ");
                return;
            }
            card.setNumber(null);
        }
        println(Color.BLUE,"SMS message successfully off: ");
    }

    private static void changePassword(Cards card) {
        String pass = getStr("Old password: ");
        if (!card.getPassword().equals(pass)){
            Print.print(RED,"Wrong password: ");
            return;
        }
        String password = getStr("New password: ");
        card.setPassword(password);
        Print.print(Color.BLUE,"Successfully changed: ");
    }

    private static void withdraw(Cards card, ATMEntity atmEntity){
       if (findByAtmCasseteCount(atmEntity)){
        long money = getNum("How mush withdraw money => ");
       long moneyAtm = findByAtmMoney(atmEntity);
       if (money > moneyAtm){
           println(RED,"Atmda pul buncha pul yoq : ");
       }else {
           xisob(money,atmEntity);
       }
       }
    }

    private static void xisob(long money, ATMEntity atmEntity) {
        int atmCassete1 = atmEntity.getCassettes().get(0).getCurrencyCount();
        int atmCassete2 = atmEntity.getCassettes().get(1).getCurrencyCount();
        int atmCassete3 = atmEntity.getCassettes().get(2).getCurrencyCount();
        int atmCassete4 = atmEntity.getCassettes().get(3).getCurrencyCount();
        long m100 = (money / 100000);
        long e100 = (money % 100000);
        long m50 = e100 / 50000;
        long e50 = e100 % 50000;
        long m10 = e50 / 10000;
        long e10 = e50 % 10000;
        long m5 = e10 / 5000;
        if (atmCassete1 < m100) {
            long qoldiqlar100 = (m100 - atmCassete1);
            e100 += qoldiqlar100;
            m50 = e100 / 50000;
            e50 = e100 % 50000;
            atmEntity.getCassettes().get(0).setCurrencyCount(Math.toIntExact((0)));
        } else atmEntity.getCassettes().get(0).setCurrencyCount(Math.toIntExact((atmCassete1 - m100)));
        if (m50 > atmCassete2) {
            long qoldiqlar50 = (m50 - atmCassete2);
            e50 += qoldiqlar50;
            m10 = e50 / 10000;
            e10 = e50 % 10000;
            atmEntity.getCassettes().get(1).setCurrencyCount(Math.toIntExact((0)));
        } else atmEntity.getCassettes().get(1).setCurrencyCount(Math.toIntExact((atmCassete2 - m50)));

        if (m10 > atmCassete3) {
            long qoldiqlar10 = (m10 - atmCassete3);
            e10 += qoldiqlar10;
            m5 = e10 / 5000;
            atmEntity.getCassettes().get(2).setCurrencyCount(Math.toIntExact((0)));
        } else atmEntity.getCassettes().get(2).setCurrencyCount(Math.toIntExact((atmCassete3 - m10)));

        if (atmCassete4 < m5) {
            println(RED, "Buncha pul yo'q !");
        } else atmEntity.getCassettes().get(3).setCurrencyCount(Math.toIntExact((atmCassete4 - m5)));
        FRWATMEntity.getInstance().writeAll(getAllAtm());
    }

    private static long findByAtmMoney(ATMEntity atmEntity) {
        long money=0;
        for (Cassette cassette : atmEntity.getCassettes()) {

          money +=((long) cassette.getCurrencyCount() * Long.parseLong(cassette.getCurrencyValue())) * 1000;

        }
        return money;
    }

    private static boolean findByAtmCasseteCount(ATMEntity atmEntity) {
        int a=0;
        for (Cassette cassette : atmEntity.getCassettes()) {
            if (cassette.getCurrencyCount()  <= 0){
                ++a;
            }
        }
        if (a==4){
            println(RED,"Atmda pul qolmagan !");
            return false;
        }
        return true;

    }

    private static ATMEntity findByAtmName(String atmName) {
        for (ATMEntity atmEntity : FRWATMEntity.getInstance().getAll()) {
            if (atmEntity.getName().equals(atmName) && atmEntity.getStatus().equals(ATMStatus.ACTIVE)){
               return atmEntity;
            }
        }
return null;
    }


    private static void pays(Cards card) {
        println(RED, "Profilaktika");
    }


    @Override
    public ResponseEntity<String> create(ATMEntity myAtm) {
        if (Objects.nonNull(checkAtm(myAtm.getName()))) {
            return new ResponseEntity<>("This atm already token", HttpStatus.HTTP_403);
        }
        ATMType atmType = ATMType.getAtmType();
        myAtm.setType(atmType);
        myAtm.setId(BaseUtils.genId());
        myAtm.setStatus(ATMStatus.ACTIVE);
        myAtm.setBankId(Session.getInstance().getUser().getBankId());
        myAtm.setBranchID(Session.getInstance().getUser().getId());
        myAtm.setLatitude(BaseUtils.getKoordinata());
        myAtm.setLongitude(BaseUtils.getKoordinata());
        getAddCassete(myAtm);
        FRWATMEntity.getInstance().writeAll(myAtm);
        return new ResponseEntity<>("successfully created", HttpStatus.HTTP_200);
    }


    public ResponseEntity<String> delete(String id) {
        list();
        String name = getStr("Atm Name: ");
        ATMEntity myAtm = checkAtm(name);
        if (Objects.isNull(myAtm)) {
            return new ResponseEntity<>("This atm not found", HttpStatus.HTTP_404);
        }
        myAtm.setDeleted(1);
        return new ResponseEntity<>("Deleted successfully", HttpStatus.HTTP_200);
    }

    @Override
    public ResponseEntity<String> get(String name) {
        ATMEntity myAtm = checkAtm(name);
        if (Objects.isNull(myAtm)) {
            return new ResponseEntity<>("This atm not found", HttpStatus.HTTP_404);
        }
        return new ResponseEntity<>("Success", HttpStatus.HTTP_200);
    }

    public static List<ATMEntity> getAllAtm() {
        return FRWATMEntity.getInstance().getAll();
    }

    @Override
    public ResponseEntity<List<ATMEntity>> getAll() {
      return null;
    }


    @Override
    public ResponseEntity<String> update(String name) {
        ATMEntity myAtm = checkAtm(name);
        if (Objects.isNull(myAtm)) {
            return new ResponseEntity<>("This  atm not found", HttpStatus.HTTP_404);
        }
        while (true) {
            String menu = getStr("🔵Name change-1 \t  🔵Change koordinata-2 \n🔵Change Cassete-3 ");
            switch (menu) {
                case "1" -> changeName(myAtm);
                case "2" -> changeKoordinata(myAtm);
                case "3" -> changeCassete(myAtm);
                default -> {
                    return new ResponseEntity<>("Wrong choice ", HttpStatus.HTTP_400);
                }
            }
            String sikl = getStr("Are you continue yes/no => (n..)/(..) ?");
            if (sikl.startsWith("n")) {
                return new ResponseEntity<>("Successfully Changed", HttpStatus.HTTP_200);
            }
        }
    }

    public static void list() {
        int index = 0;
        for (ATMEntity atmEntity : BaseList) {
            if (Objects.nonNull(atmEntity) && atmEntity.getStatus().equals(ATMStatus.ACTIVE) && atmEntity.getDeleted() == 0/* && atmEntity.getBranchID().equals(Session.getInstance().getUser().getBranchID())*/) {
                Print.print(Color.WHITE, (++index) + ". ");
                println(Color.BLUE, atmEntity.getATM());
            } else if (atmEntity.getStatus().equals(ATMStatus.BLOCKED) && atmEntity.getDeleted() == 0/*&& atmEntity.getBranchID().equals(Session.getInstance().getUser().getBranchID())*/) {
                Print.print(Color.WHITE, (++index) + ". ");
                println(RED, atmEntity.getATM());
            }
        }
        if (index == 0) {
            println(RED, "Atm not found");
        }
    }

    public static void block() {
        list();
        String name = getStr("Atm Name: ");
        ATMEntity myAtm = checkAtm(name);
        if (Objects.isNull(myAtm)) {
            println(RED, "This atm not found");
            return;
        }
        myAtm.setStatus(ATMStatus.BLOCKED);
        println(Color.BLACK, "Blocked successfully");
    }

    public static void unblock() {
        list();
        String name = getStr("Atm Name: ");
        ATMEntity myAtm = checkAtm(name);
        if (Objects.isNull(myAtm)) {
            println(RED, "This atm not found");
            return;
        }
        myAtm.setStatus(ATMStatus.ACTIVE);
        println(Color.BLACK, "Unblocked successfully");
    }

    public static void blockList() {
        int index = 0;
        for (ATMEntity atmEntity : BaseList) {
            if (Objects.nonNull(atmEntity) && atmEntity.getStatus().equals(ATMStatus.BLOCKED) /*&& atmEntity.getBranchID().equals(Session.getInstance().getUser().getBranchID())*/) {
                Print.print(Color.WHITE, (++index) + ". ");
                println(RED, atmEntity.getATM());
            }
        }
        if (index == 0) {
            println(RED, "Birorta ham Atm yo'q");
        }
    }

    private static void getAddCassete(ATMEntity myAtm) {
        String cassetemoney = getStr("🔵100ming - 100 \t 🔵50ming - 50 \n🔵10ming -10 \t 🔵5ming-5 \n exit - e => ? ");
        switch (cassetemoney) {
            case "100", "50", "10", "5" -> newCassete(cassetemoney, myAtm);
            case "e" -> {
                return;
            }
            default -> {
                println(RED, "Wrong choice ");
                return;
            }
        }
        String sikl = getStr("Will you continue yes/no => no/(...)=> ? ");
        if (sikl.equals("no")) {
            return;
        }
        getAddCassete(myAtm);
    }

    private static void newCassete(String cassetemoney, ATMEntity myAtm) {
        int number = Input.getNum("Necha dona kupyura qo'shmoqchisiz??? ");
        if (number < 0 || number > 100) {
            println(RED, "Siz buncha pul qo'sha olmaysiz");
            newCassete(cassetemoney, myAtm);
        }
        for (Cassette cassette : myAtm.getCassettes()) {
            if (cassette.getCurrencyValue().equals(cassetemoney)) {
                if (cassette.getCurrencyCount() + number > 100) {
                    println(RED, "Siz buncha pul qo'sha olmaysiz");
                    return;
                }
                cassette.setCurrencyCount(cassette.getCurrencyCount() + number);
                return;
            }
        }
        Cassette cassette = new Cassette(cassetemoney, CassetteStatus.ACTIVE, number);
        myAtm.getCassettes().add(cassette);
    }

    private static ATMEntity checkAtm(String name) {
        for (ATMEntity atmEntity : BaseList) {
            if (Objects.nonNull(atmEntity.getName()) && atmEntity.getName().equals(name)) {
                return atmEntity;
            }
        }
        return null;
    }

    private static void changeCassete(ATMEntity myAtm) {
        String menu = getStr("🔵Add Money-1 \t 🔵Change cassete status-2 \n exit - e");
        if (menu.equals("1")) {
            getAddCassete(myAtm);
        } else if (menu.equals("2")) {
            changeCassetestatus(myAtm);
        } else if (menu.equals("e")) {
            return;
        } else {
            println(RED, "Wrong choice ");
        }
        changeCassete(myAtm);
    }

    private static void changeCassetestatus(ATMEntity myAtm) {
        String cassetemoney = getStr("🔵100ming - 100 \t 🔵50ming - 50 \n🔵10ming -10 \t 🔵5ming-5 \n🔵exit - e => ? ");
        switch (cassetemoney) {
            case "100", "50", "10", "5" -> changeStatus(cassetemoney, myAtm);
            case "e" -> {
                return;
            }
            default -> {
                println(RED, "Wrong choice ");
                return;
            }
        }
        String sikl = getStr("Will you continue yes/no => no/(...)=> ? ");
        if (sikl.equals("no")) {
            return;
        }
        getAddCassete(myAtm);
    }

    private static void changeStatus(String cassetemoney, ATMEntity myAtm) {
        for (Cassette cassette : myAtm.getCassettes()) {
            if (cassette.equals(cassetemoney)) {
                if (cassette.getStatus().equals(CassetteStatus.ACTIVE)) {
                    cassette.setStatus(CassetteStatus.BLOCKED);
                } else {
                    cassette.setStatus(CassetteStatus.ACTIVE);
                }
            }
        }
    }

    private static void changeKoordinata(ATMEntity myAtm) {
        myAtm.setLatitude(BaseUtils.getKoordinata());
        myAtm.setLongitude(BaseUtils.getKoordinata());
    }

    private static void changeName(ATMEntity myAtm) {
        String lastName = getStr("LastName: ");
        if (!lastName.equals(myAtm.getName())) {
            println(RED, "Incorrect lastName");
            changeName(myAtm);
        }
        String nextName = getStr("NextName: ");
        myAtm.setName(nextName);
        println(Color.CYAN, "Name successfully changed");
    }
}
