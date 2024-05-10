package com.youth.request.User;

import com.youth.request.PageParamRequest;
import lombok.Data;

@Data
public class UserQueryDto extends PageParamRequest {
    private String username;
}
