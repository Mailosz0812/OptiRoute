package org.locations.optiroute.Mappers;

public interface Mapper<A,B> {
    A mapFrom(B b);
    B mapTo(A a);
}
