package com.example.hrsystem.visitor;

public interface WorkforceItem {
    // Acepta un visitante que devuelve un tipo R y devuelve ese tipo R
    <R> R accept(Visitor<R> visitor);
}