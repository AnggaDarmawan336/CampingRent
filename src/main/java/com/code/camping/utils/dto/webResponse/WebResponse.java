package com.code.camping.utils.dto.webResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebResponse<T> {

    private T data;
    private String message;

}
