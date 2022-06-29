package uz.oyatjon.dao.branch;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import uz.oyatjon.dao.atm.BaseDao;
import uz.oyatjon.dao.db.FRWBranch;
import uz.oyatjon.enums.http.HttpStatus;
import uz.oyatjon.exceptions.APIException;
import uz.oyatjon.models.atm.ATMEntity;
import uz.oyatjon.models.branch.Branch;

import java.util.List;
import java.util.Objects;



@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BranchDao extends BaseDao<Branch> {
    FRWBranch frwBranch = FRWBranch.getInstance();

    private static BranchDao dao;

    public static BranchDao getInstance() {
        if (Objects.isNull(dao))
            dao = new BranchDao();
        return dao;
    }

    public Branch findById(String id) throws APIException {
        for (Branch branch : frwBranch.getAll()) {
            if (id.equals(branch.getId()))
                if (branch.getDeleted() == 0) {
                    return branch;
                } else break;
        }
        throw new APIException("Branch Not Found", HttpStatus.HTTP_404);
    }

    public boolean getByName(String name) {
        for (Branch branch : frwBranch.getAll()) {
            if (name.equalsIgnoreCase(branch.getName())) {
                return true;
            }
        }
        return false;
    }

    public Branch findByName(String name) throws APIException {
        for (Branch branch : frwBranch.getAll()) {
            if (name.equalsIgnoreCase(branch.getName()))
                return branch;
        }
        throw new APIException("Branch Not Found", HttpStatus.HTTP_404);
    }

    public BranchDao(String path) {
        super(path);
    }

    @Override
    public List<ATMEntity> getALL() {
        return null;
    }

    @Override
    public void writerALL(List<ATMEntity> dataList) {

    }
}
