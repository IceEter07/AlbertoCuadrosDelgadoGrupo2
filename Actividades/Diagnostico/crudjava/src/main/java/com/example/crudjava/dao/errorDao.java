package com.example.crudjava.dao;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class errorDao {
    private String code;
    private  String message;
}
