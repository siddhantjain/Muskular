package com.siddhantjain.muskular.models;

import lombok.Data;

/**
 * Created by akash.jatangi on 10/17/15.
 */
@Data
public class UserProfilerRequest {
    String gender;
    String goal;
    String height;
    String weight;
//    String level;
    String yob;
}
