package uz.oyatjon.services.branch;

import org.apache.commons.lang3.RandomStringUtils;
import uz.oyatjon.configs.Session;
import uz.oyatjon.dao.branch.BranchDao;
import uz.oyatjon.dao.db.FRWAuthUser;
import uz.oyatjon.dao.db.FRWBranch;
import uz.oyatjon.enums.atm.Status;
import uz.oyatjon.enums.auth.Role;
import uz.oyatjon.enums.branch.BranchStatus;
import uz.oyatjon.enums.card.CardType;
import uz.oyatjon.enums.extras.Gender;
import uz.oyatjon.enums.http.HttpStatus;
import uz.oyatjon.exceptions.APIException;
import uz.oyatjon.exceptions.APIRuntimeException;
import uz.oyatjon.mapper.BranchMapper;
import uz.oyatjon.models.auth.AuthUser;
import uz.oyatjon.models.branch.Branch;
import uz.oyatjon.models.card.Cards;
import uz.oyatjon.models.personal.Passport;
import uz.oyatjon.response.ResponseEntity;
import uz.oyatjon.services.BaseAbstractService;
import uz.oyatjon.services.IBaseCrudService;
import uz.oyatjon.utils.BaseUtils;
import uz.jl.utils.Color;
import uz.jl.utils.Input;
import uz.jl.utils.Print;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static uz.jl.utils.Input.getStr;


public class BranchService
        extends BaseAbstractService<Branch, BranchDao, BranchMapper>
        implements IBaseCrudService<Branch> {

    Role role = Session.getInstance().getUser().getRole();

    private static BranchService service;

    public static BranchService getInstance(BranchDao repository, BranchMapper mapper) {
        if (Objects.isNull(service)) {
            service = new BranchService(repository, mapper);
        }
        return service;
    }

    public BranchService(BranchDao repository, BranchMapper mapper) {
        super(repository, mapper);
    }

    public static void getNewCard() {
      //  if (Session.getInstance().equals(Role.CLIENT)) {
            String userName = getStr("Username-> ");
            String password = getStr("password-> ");
            String phoneNumber = getStr("Phone Number->");
            AuthUser user = getUser(userName);
            if (Objects.isNull(user)) {
                return ;
            }if (!user.getPassword().equals(password) || !user.getPhoneNumber().equals(phoneNumber)) {
                return ;
            }
            getNewPasspord(user);
            getNewCardBYPassport(user);

        FRWAuthUser.getInstance().writeAll(user);
     //   }

    }

    private static void getNewCardBYPassport(AuthUser user) {
        CardType cardType = getCardType();
        String expiry = getExpiry();
        String pan = getPan(cardType.getCode());
        String password = getPassword();
        Cards myCard = new Cards(pan,expiry,password,cardType, Status.ACTIVE,new BigDecimal(1000000)
                ,Session.getInstance().getUser().getBankId(),Session.getInstance().getUser().getId(),user.getPhoneNumber());
        user.setCards(new ArrayList<>(List.of(myCard)));
        Print.println(Color.BLUE,"You take card of our bank.Thank you ");

    }

    private static String getPassword() {
        String pasw = Input.getStr("Card password:(Only 4 numbers) ");
       try{
           int n = Integer.parseInt(pasw);
           return pasw;
       }catch (Exception e){
           Print.println(Color.RED, "Wrong choice");
           return getPassword();
       }
    }

    private static CardType getCardType() {
        String type = Input.getStr(" UZCARD(\"8600\"),\n" +
                "    HUMO(\"9860\"),\n" +
                "    MASTER_CARD(\"4853\"),\n" +
                "    VISA(\"4735\"),\n" +
                "    COBAGE(\"6330\"),\n" +
                "    UNION_PAY(\"6262\"): ");
        for (CardType value : CardType.values()) {
            if (Objects.nonNull(value) && value.getCode().equals(type)) {
                return value;
            }
        }
        return getCardType();
    }

    private static String getPan(String type) {
        return type + System.nanoTime() + RandomStringUtils.random(12, false, true);
    }

    private static String getExpiry() {
        int begin = ((int) (Math.random() * 11) + 1);
        int end = ((int) (Math.random() * 5) + 21);
        return begin + "/" + end;
    }

    private static void getNewPasspord(AuthUser user) {

        String firstName = getStr("FirstName: ");
        String lasstName = getStr("LastName: ");
        String serial = getStr("Serial: ");
        String number = getStr("Number: ");
        String fatherName = getStr("FatherName: ");
        Gender gender = getGender();
        Passport userPassport = new Passport(serial, number, gender, firstName, lasstName, fatherName, BaseUtils.genId());
        user.setPassport(userPassport);
    }

    private static Gender getGender() {

        String gender = getStr(">>> MALE FEMALE OTHER ??? ");
        for (Gender value : Gender.values()) {
            if (value.name().equalsIgnoreCase(gender))
                return value;
        }
        Print.println(Color.RED, "Wrong choice");
        return getGender();
    }

    public static AuthUser getUser(String userName) {
        List<AuthUser> users = FRWAuthUser.getInstance().getAll();
        for (AuthUser user : users) {
            if (Objects.nonNull(user) && user.getUsername().equals(userName))
                return user;
        }
        return null;
    }

    public static void deposit() {
        Print.println(Color.RED, "Profilaktika ishlari sababli,Bu xizmat vaqtincha ishlamayapti");
    }

    public ResponseEntity<String> create(String name) {
        if (!(Role.SUPER_ADMIN.equals(role) || Role.ADMIN.equals(role))) {
            return new ResponseEntity<>("Forbidden", HttpStatus.HTTP_403);
        }
        Branch branch = new Branch();
        branch.setName(name);
        branch.setStatus(BranchStatus.ACTIVE);
        branch.setCreatedAt(new Date());
        branch.setDeleted(0);
        return create(branch);
    }

    @Override
    public ResponseEntity<String> create(Branch branch) {
        if (repository.getByName(branch.getName())) {
            return new ResponseEntity<>("Already exists", HttpStatus.HTTP_406);
        }
        FRWBranch.getInstance().writeAll(branch);
        return new ResponseEntity<>("Successfully done", HttpStatus.HTTP_200);
    }

    public ResponseEntity<String> delete(String name) {
        if (!(Role.SUPER_ADMIN.equals(role) || Role.ADMIN.equals(role))) {
            return new ResponseEntity<>("Forbidden", HttpStatus.HTTP_403);
        }
        Branch branch;
        try {
            branch = repository.findByName(name);
        } catch (APIException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.getStatusByCode(e.getCode()));
        }
        return delete(branch);
    }

    @Override
    public ResponseEntity<String> delete(Branch branch) {
        if (branch.getDeleted() == 1) {
            return new ResponseEntity<>("Already done", HttpStatus.HTTP_406);
        }
        branch.setDeleted(1);
        FRWBranch.getInstance().writeAll(getAll());
        return new ResponseEntity<>("Successfully done", HttpStatus.HTTP_200);
    }

    public void list() {
        if (!(Role.SUPER_ADMIN.equals(role) || Role.ADMIN.equals(role))) {
            Print.println(Color.RED, "Forbidden");
            return;
        }
        int a = 0;
        for (Branch branch : FRWBranch.getInstance().getAll()) {
            if (branch.getDeleted() == 0) {
                if (branch.getStatus().equals(BranchStatus.BLOCKED))
                    Print.println(Color.RED, ++a + ". " + "Name :" + branch.getName());
                else
                    Print.println(Color.PURPLE, ++a + ". " + "Name :" + branch.getName());
            }
        }
    }


    @Override
    public Branch get(String id) {
        for (Branch branch : FRWBranch.getInstance().getAll()) {
            if (id.equals(branch.getId()))
                return branch;
        }
        throw new APIRuntimeException("Branch Not Found", HttpStatus.HTTP_404.getCode());
    }

    @Override
    public List<Branch> getAll() {
        return FRWBranch.getInstance().getAll();
    }

    @Override
    public ResponseEntity<String> update(String id, Branch branch) {
        branch.setUpdatedAt(new Date());
        branch.setUpdatedBy(id);
        FRWBranch.getInstance().writeAll(getAll());
        return new ResponseEntity<>("Successfully done", HttpStatus.HTTP_200);
    }

    public ResponseEntity<String> block(String name) {
        if (!(Role.SUPER_ADMIN.equals(role) || Role.ADMIN.equals(role))) {
            return new ResponseEntity<>("Forbidden", HttpStatus.HTTP_403);
        }
        if (unblockCount() == 0) {
            return new ResponseEntity<>("Not Found Any Unblocked Branch", HttpStatus.HTTP_404);
        }
        unblockList();
        try {
            Branch branch = repository.findByName(name);
            if (branch.getDeleted() == 1) {
                throw new APIException("Branch Not Found", HttpStatus.HTTP_404);
            }
            if (branch.getStatus().equals(BranchStatus.BLOCKED)) {
                return new ResponseEntity<>("Already done", HttpStatus.HTTP_406);
            }
            if (branch.getStatus().equals(BranchStatus.ACTIVE)) {
                branch.setStatus(BranchStatus.BLOCKED);
            }
            FRWBranch.getInstance().writeAll(getAll());
        } catch (APIException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.getStatusByCode(e.getCode()));
        }
        return new ResponseEntity<>("Successfully done", HttpStatus.HTTP_200);
    }


    public ResponseEntity<String> updateBranch(String name, String newBranchName) {
        if (!(Role.SUPER_ADMIN.equals(role) || Role.ADMIN.equals(role))) {
            return new ResponseEntity<>("Forbidden", HttpStatus.HTTP_403);
        }
        Branch branch = null;
        try {
            branch = BranchDao.getInstance().findByName(name);
            branch.setName(newBranchName);
            update(branch.getId(), branch);
        } catch (APIException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Successfully done", HttpStatus.HTTP_200);
    }


    public ResponseEntity<String> unblock(String name) {
        if (!(Role.SUPER_ADMIN.equals(role) || Role.ADMIN.equals(role))) {
            return new ResponseEntity<>("Forbidden", HttpStatus.HTTP_403);
        }
        if (blockCount() == 0) {
            return new ResponseEntity<>("Not Found Any Blocked Branch", HttpStatus.HTTP_404);
        }
        blockList();
        try {
            Branch branch = repository.findByName(name);
            if (branch.getDeleted() == 1) {
                throw new APIException("Branch Not Found", HttpStatus.HTTP_404);
            }
            if (branch.getStatus().equals(BranchStatus.ACTIVE)) {
                return new ResponseEntity<>("Already done", HttpStatus.HTTP_406);
            }
            if (branch.getStatus().equals(BranchStatus.BLOCKED)) {
                branch.setStatus(BranchStatus.ACTIVE);
            }
            FRWBranch.getInstance().writeAll(getAll());
        } catch (APIException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.getStatusByCode(e.getCode()));
        }
        return new ResponseEntity<>("Successfully done", HttpStatus.HTTP_200);
    }


    public void blockList() {
        if (!(Role.SUPER_ADMIN.equals(role) || Role.ADMIN.equals(role))) {
            Print.println(Color.RED, "Forbidden");
            return; // new ResponseEntity<>("Forbidden", HttpStatus.HTTP_403);
        }
        for (Branch branch : FRWBranch.getInstance().getAll()) {
            if (branch.getDeleted() == 0 && branch.getStatus().equals(BranchStatus.BLOCKED))
                Print.println(Color.RED, branch.getName());
        }
    }

    public void unblockList() {
        if (!(Role.SUPER_ADMIN.equals(role) || Role.ADMIN.equals(role))) {
            Print.println(Color.RED, "Forbidden");
            return; // new ResponseEntity<>("Forbidden", HttpStatus.HTTP_403);
        }
        for (Branch branch : FRWBranch.getInstance().getAll()) {
            if (branch.getDeleted() == 0 && branch.getStatus().equals(BranchStatus.ACTIVE))
                Print.println(Color.PURPLE, branch.getName());
        }
    }

    public Integer blockCount() {
        Integer count = 0;
        for (Branch branch : FRWBranch.getInstance().getAll()) {
            if (branch.getDeleted() == 0 && branch.getStatus().equals(BranchStatus.BLOCKED))
                count++;
        }
        return count;
    }

    public Integer unblockCount() {
        Integer count = 0;
        for (Branch branch : FRWBranch.getInstance().getAll()) {
            if (branch.getDeleted() == 0 && branch.getStatus().equals(BranchStatus.ACTIVE))
                count++;
        }
        return count;
    }
}
