package com.project.antifakebook.dto;

public interface ResponseCase {
    ResponseStatus OK = new ResponseStatus(1000, "OK");

    ResponseStatus USER_EXISTED = new ResponseStatus(9996, "USER EXISTED");
    ResponseStatus INVALID_EMAIL = new ResponseStatus("INVALID EMAIL");
    ResponseStatus INVALID_PASSWORD = new ResponseStatus("INVALID PASSWORD");
    ResponseStatus INVALID_EMAIL_AND_PASSWORD = new ResponseStatus("INVALID EMAIL AND PASSWORD");
    ResponseStatus USER_IS_NOT_VALIDATED = new ResponseStatus(9995, "USER_IS_NOT_VALIDATED");
    ResponseStatus ACTION_DONE_BEFORE = new ResponseStatus(1010, "ACTION_HAS_BEEN_DONE_PREVIOUS_BY_THIS_USER");
    ResponseStatus NOT_ACCESS = new ResponseStatus(1009, "NOT_ACCESS");

}
