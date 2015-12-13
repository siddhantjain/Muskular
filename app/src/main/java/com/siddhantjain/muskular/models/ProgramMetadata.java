package com.siddhantjain.muskular.models;

import lombok.Data;

/**
 * Created by akash.jatangi on 11/22/15.
 */
@Data
public class ProgramMetadata {
    String daysPerWeek;
    String difficulty;
    String id;
    Trainer author;
}
