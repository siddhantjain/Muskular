package com.siddhantjain.muskular.models;

import lombok.Data;

/**
 * Created by akash.jatangi on 9/20/15.
 */
@Data
public class UserCreateRequest {
    String emailId;
    String password;
}
