package org.locations.optiroute.Mappers;

import java.util.HashMap;
import java.util.List;

public interface Mapper<A,B> {
    A mapFrom(B b);
    B mapTo(A a);
    List<A> mapFromList(List<B> addressesList);
    List<B> mapToList(List<A> addressesList);
}
