package gr.movieinsights.service.util.wrappers.dataimport;

import gr.movieinsights.models.enumeration.ResponseResult;

public class ResponseWrapper<T> {
    public static <T> ResponseWrapper<T> Of(retrofit2.Response<T> response, ResponseResult result) {
        return new ResponseWrapper<>(response, result);
    }

    private retrofit2.Response<T> response;
    private ResponseResult result;

    private ResponseWrapper(retrofit2.Response<T> response, ResponseResult result) {
        this.response = response;
        this.result = result;
    }

    public retrofit2.Response<T> getResponse() {
        return response;
    }

    public ResponseResult getResult() {
        return result;
    }
}
