package com.siddhantjain.muskular.models;

import com.google.gson.annotations.SerializedName;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by akash.jatangi on 9/19/15.
 */
@Data
public abstract class BasicResponse {
    String responseCode;
    public abstract Object getData();
    String errMsg;
}
