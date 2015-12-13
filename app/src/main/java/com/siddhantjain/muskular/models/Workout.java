package com.siddhantjain.muskular.models;

import java.util.List;
import lombok.Data;

/**
 * Created by akash.jatangi on 10/31/15.
 */
@Data
public class Workout {
    String id;
    String name;
    String difficulty;
    List<DayExercises> exercises;
}
