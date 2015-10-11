package com.siddhantjain.muskular.models;

import lombok.Data;

/**
 * Created by akash.jatangi on 9/19/15.
 */
@Data
public class UserAuth {
    String emailId;
    String userId;
    String lastSectionCompleted;

    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("email id - " + this.emailId);
        s.append(" user id - " + this.userId);
        s.append(" last section completed - " + this.lastSectionCompleted);
        return String.valueOf(s);
    }
}
