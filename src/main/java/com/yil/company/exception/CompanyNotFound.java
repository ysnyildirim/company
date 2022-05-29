/*
 * Copyright (c) 2022. Tüm hakları Yasin Yıldırım'a aittir.
 */

package com.yil.company.exception;

import com.yil.company.base.ApiException;
import com.yil.company.base.ErrorCode;

import javax.persistence.EntityNotFoundException;

@ApiException(code = ErrorCode.CompanyNotFound)
public class CompanyNotFound extends EntityNotFoundException {
}
