package com.siddhantjain.muskular.models;

import java.util.List;

import lombok.Data;

/**
 * Created by akash.jatangi on 10/17/15.
 */
@Data
public class UserProfilerResponseData {
    String userId;
    String uwyaId;
    String uwywId;
    List<ProgramMetadata> eligiblePrograms;
}
