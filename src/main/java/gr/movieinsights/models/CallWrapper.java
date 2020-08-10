package gr.movieinsights.models;

import gr.movieinsights.domain.enumeration.TmdbEntityType;
import retrofit2.Call;

public class CallWrapper<E> {

    public static <E> CallWrapper<E> of(Call<E> call, TmdbEntityType type, int key) {
        return new CallWrapper<>(call,type,key);
    }

    public static <E> CallWrapper<E> of(Call<E> call, TmdbEntityType type, String key) {
        return new CallWrapper<>(call,type,key);
    }

    public final Call<E> call;
    public Integer intKey;
    public String stringKey;
    public TmdbEntityType type;

    public TmdbEntityType getType() {
        return type;
    }

    public Call<E> getCall() {
        return call;
    }

    public Integer getIntKey() {
        return intKey;
    }

    public String getStringKey() {
        return stringKey;
    }

    public CallWrapper(Call<E> call, TmdbEntityType type, int key) {
        this.call = call;
        this.intKey = key;
        this.type = type;
    }

    public CallWrapper(Call<E> call, TmdbEntityType type, String key) {
        this.call = call;
        this.type = type;
        this.stringKey = key;
    }
}
