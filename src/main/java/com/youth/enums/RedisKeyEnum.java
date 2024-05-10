package com.youth.enums;

import lombok.Getter;

@Getter
public enum RedisKeyEnum {

    TOKEN_BLACKLIST_PREFIX("TOKEN_BLACKLIST:", "黑名单机制"),

    TOKEN_REFRESH_PREFIX("TOKEN_REFRESH:", "刷新令牌与访问令牌关联");

    private String key;

    private String massage;

    RedisKeyEnum(String key, String massage) {
        this.key = key;
        this.massage = massage;
    }
}
