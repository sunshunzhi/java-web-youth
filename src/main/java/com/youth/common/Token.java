package com.youth.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class Token {

    @Schema(description = "访问令牌")
    private String accessToken;

    @Schema(description = "刷新令牌")
    private String refreshToken;

}
