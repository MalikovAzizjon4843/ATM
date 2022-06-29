package uz.oyatjon.mapper;



/**
 * @param <E> E-> Entity
 * @param <D> D-> DTO (Data Transfer Object)
 */
public abstract class BaseMapper<E, D> {
    abstract E to(D d);

    abstract D from(E e);
}
