package com.siddhantjain.muskular.models;
import lombok.Data;

/**
 * Created by akash.jatangi on 10/31/15.
 */
@Data
public class Program {
    Workout workout;
    String id;
    Trainer author;
    Diet diet;
    Supplementation supplementation;
}
