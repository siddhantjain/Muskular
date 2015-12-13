package com.siddhantjain.muskular.models;
import lombok.Data;

/**
 * Created by akash.jatangi on 10/31/15.
 */
@Data
public class ExerciseData {
    String workout;
    Integer dayOfPlan;
    String sets;
    String reps;
    String cardioDuration;
    String exType;
    Exercise exercise;
}
