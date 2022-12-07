package com.probal.inventoryservice.dto.resposnse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {
    private int code;
    private String responseMessage;
    private T data;

    public Response<T> buildResponse(int code, String responseMessage, T data) {
        return (Response<T>) Response
                .builder()
                .code(code)
                .responseMessage(responseMessage)
                .data(data)
                .build();
    }
}
