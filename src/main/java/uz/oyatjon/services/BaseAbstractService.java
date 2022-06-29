package uz.oyatjon.services;



import uz.oyatjon.dao.atm.BaseDao;
import uz.oyatjon.models.BaseEntity;

/**
 * @param <E> E -> Entity
 * @param <R> R -> Repository
 * @param <M> M -> Mapper
 */
public abstract class BaseAbstractService
        <E extends BaseEntity, R extends BaseDao<E>, M> {
    protected R repository;
    protected M mapper;

    public BaseAbstractService(R repository, M mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

}
