package com.code.camping.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Weather {
    private String date;
    private String time;
    private String name;
}
