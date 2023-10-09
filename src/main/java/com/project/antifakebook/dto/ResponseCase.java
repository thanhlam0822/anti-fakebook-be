package com.project.antifakebook.dto;

public interface ResponseCase {
    ResponseStatus OK = new ResponseStatus(1000, "OK");
    ResponseStatus INTERNAL_SERVER_ERROR = new ResponseStatus(500, "Internal Server error");
    ResponseStatus USER_EXISTED = new ResponseStatus(9996, "USER EXISTED");
    ResponseStatus INVALID_EMAIL = new ResponseStatus( "INVALID EMAIL");
    ResponseStatus INVALID_PASSWORD = new ResponseStatus( "INVALID PASSWORD");
    ResponseStatus EMAIL_IS_USED = new ResponseStatus(503, "Email is used");
    ResponseStatus INVALID_DEVICE_TYPE = new ResponseStatus(504, "Invalid device type");
    ResponseStatus EMPTY_PUSHNOTIFICATION_ARN = new ResponseStatus(505, "Empty push notification arn");
    ResponseStatus ACCESS_DENIED = new ResponseStatus(506, "Access denied");
    ResponseStatus INVALID_FILE_EXTENSION = new ResponseStatus(507, "In-valid file extension");
    ResponseStatus EXCEEDED_MAXIMUM_FILE_SIZE = new ResponseStatus(508, "Exceeded maximum file size");
    ResponseStatus ZIPCODE_NOTFOUND = new ResponseStatus(509, "Cannot find zipcode");
    ResponseStatus DUPLICATE_FILTER_NAME = new ResponseStatus(510, "Duplicate filter name");

    ResponseStatus ERROR = new ResponseStatus(600, "Error");

    ResponseStatus ERROR_TYPE_FILE = new ResponseStatus(601, "Error");
    ResponseStatus ERROR2 = new ResponseStatus(602, "Error");
    ResponseStatus INVALID_FORMAT = new ResponseStatus(603, "Invalid format");

    ResponseStatus STATISTIC_TIMEOUT = new ResponseStatus(604,"Statistic timeout");

    ResponseStatus EXISTED_NAME = new ResponseStatus(605, "Name Existed");

}
