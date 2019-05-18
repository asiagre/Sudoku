package com.sudoku;

public class Prototype<T> implements Cloneable {
    public T clone() throws CloneNotSupportedException {
        return (T)super.clone();
    }
}
