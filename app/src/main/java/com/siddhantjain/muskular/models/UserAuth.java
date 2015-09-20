package com.siddhantjain.muskular.models;

import lombok.Data;

/**
 * Created by akash.jatangi on 9/19/15.
 */
@Data
public class UserAuth {
    String emailId;
    String userId;

    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("email id - " + this.emailId);
        s.append(" user id - " + this.userId);
        return String.valueOf(s);
    }
}
