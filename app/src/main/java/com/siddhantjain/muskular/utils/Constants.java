package com.siddhantjain.muskular.utils;

import lombok.Data;

/**
 * Created by akash.jatangi on 9/20/15.
 */
@Data
public class Constants {
    public static String sharedPrefName = "muskular_shared_pref";

    public static String getSharedPrefName() {
        return sharedPrefName;
    }
}
