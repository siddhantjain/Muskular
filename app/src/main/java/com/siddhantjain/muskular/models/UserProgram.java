package com.siddhantjain.muskular.models;
import lombok.Data;

/**
 * Created by akash.jatangi on 10/31/15.
 */
@Data
public class UserProgram {
    Program program;
    String userId;
    String uwyaId;
    String uwywId;
    Float dailyProtein;
    Float dailyCarbs;
    Float dailyCals;
    String upid;
    String startDate;
}
