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

    public String getDaysPerWeek(){return daysPerWeek;}
    public String getDifficulty() {
        return difficulty;
    }
    public String getId() {
        return id;
    }
    public Trainer getTrainer() {
        return author;
    }

}
