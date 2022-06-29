package com.example.dao.branch;

import com.example.exceptions.APIException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import com.example.dao.atm.BaseDao;
import com.example.dao.db.FRWBranch;
import com.example.enums.http.HttpStatus;
import com.example.models.atm.ATMEntity;
import com.example.models.branch.Branch;

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
