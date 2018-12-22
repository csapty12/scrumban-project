package com.scrumban.testing;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@Getter
@Setter
@Data
@ToString
public class Column {
    private String id;
    private String title;
    private ArrayList<String> taskIds;
}
