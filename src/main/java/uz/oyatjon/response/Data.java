package uz.oyatjon.response;


public class Data<T> {
    private T body;
    private Integer total;

    public Data(T body, Integer total) {
        this.body = body;
        this.total = total;
    }
}
