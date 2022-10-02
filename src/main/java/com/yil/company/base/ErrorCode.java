/*
 * Copyright (c) 2022. Tüm hakları Yasin Yıldırım'a aittir.
 */
package com.yil.company.base;

import lombok.Getter;

@Getter
public enum ErrorCode {
    CompanyNotFound(3000000, "Company not found");
    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
