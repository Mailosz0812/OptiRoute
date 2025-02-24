package org.locations.optiroute.Mappers;

import java.util.HashMap;
import java.util.List;

public interface Mapper<A,B> {
    A mapFrom(B b);
    B mapTo(A a);
    HashMap<String, A> mapFromAll(HashMap<String, B> addressesMap);
    HashMap<String, B> mapToAll(HashMap<String, A> addressesMap);
    HashMap<String, A> mapFromAll(List<B> addressesMap);
    HashMap<String, B> mapToAll(List<A> addressesMap);
}
