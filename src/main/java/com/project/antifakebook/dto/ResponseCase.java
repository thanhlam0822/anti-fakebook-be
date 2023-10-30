package com.project.antifakebook.dto;

public interface ResponseCase {
    ResponseStatus OK = new ResponseStatus(1000, "OK");

    ResponseStatus USER_EXISTED = new ResponseStatus(9996, "USER EXISTED");
    ResponseStatus INVALID_EMAIL = new ResponseStatus("INVALID EMAIL");
    ResponseStatus INVALID_PASSWORD = new ResponseStatus("INVALID PASSWORD");
    ResponseStatus INVALID_EMAIL_AND_PASSWORD = new ResponseStatus("INVALID EMAIL AND PASSWORD");
    ResponseStatus USER_IS_NOT_VALIDATED = new ResponseStatus(9995, "USER IS NOT VALIDATED");
    ResponseStatus ACTION_DONE_BEFORE = new ResponseStatus(1010, "ACTION HAS BEEN DONE PREVIOUS BY THIS USER");
    ResponseStatus NOT_ACCESS = new ResponseStatus(1009, "NOT ACCESS");
    ResponseStatus TOKEN_NULL = new ResponseStatus( "TOKEN NULL");
    ResponseStatus INVALID_TOKEN = new ResponseStatus(9998, "TOKEN IS INVALID");
    ResponseStatus INVALID_USER_NAME = new ResponseStatus("INVALID USERNAME");
    ResponseStatus NOT_ENOUGH_COINS = new ResponseStatus(9991,"NOT ENOUGH COINS");
    ResponseStatus INVALID_PARAMETER = new ResponseStatus(1004,"INVALID PARAMETER");
    ResponseStatus PARAMETER_IS_NOT_ENOUGH = new ResponseStatus(1002,"PARAMETER IS NOT ENOUGH");
    ResponseStatus INVALID_FILE_UPLOAD = new ResponseStatus("INVALID FILE UPLOAD");
    ResponseStatus POST_IS_NOT_EXISTED = new ResponseStatus(9992,"POST IS NOT EXISTED");
}
