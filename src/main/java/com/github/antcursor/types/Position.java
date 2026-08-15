package com.github.antcursor.types;

public class Position {
    public int rank;
    public int file;

    public Position(int rank, int file) {
        this.rank = rank;
        this.file = file;
    }

    @Override
    public boolean equals(Object other) {
        if(other == this) return true;
        if(other instanceof Position) {
            if(this.rank == other.rank && this.file == other.file) return true;
        }
        return false;
    }
}
