package com.example.hrsystem.visitor;

public interface WorkforceItem {
    <R> R accept(Visitor<R> visitor);
}