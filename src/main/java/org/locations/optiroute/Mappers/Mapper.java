package org.locations.optiroute.Mappers;

import org.locations.optiroute.entities.AddressEntity;

import java.util.HashMap;
import java.util.List;

public interface Mapper<A,B> {
    A mapFrom(B b);
    B mapTo(A a);
    HashMap<String, A> mapFromAll(HashMap<String, B> addressesMap);
    HashMap<String, B> mapToAll(HashMap<String, A> addressesMap);
}
