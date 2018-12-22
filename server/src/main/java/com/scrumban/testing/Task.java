package com.scrumban.testing;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Data
public class Task {

    private String id;
    private String content;
}
