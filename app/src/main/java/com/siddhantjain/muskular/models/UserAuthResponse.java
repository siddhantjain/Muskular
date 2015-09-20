package com.siddhantjain.muskular.models;

import lombok.Data;

/**
 * Created by akash.jatangi on 9/19/15.
 */
@Data
public class UserAuthResponse extends BasicResponse{
    UserAuth data;

    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("response code - " + this.getResponseCode());
        s.append("email id - " + this.getData().getEmailId());
        s.append(" user id - " + this.getData().getUserId());
        return String.valueOf(s);
    }
}
