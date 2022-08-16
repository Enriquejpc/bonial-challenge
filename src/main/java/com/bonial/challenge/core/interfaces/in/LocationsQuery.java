package com.bonial.challenge.core.interfaces.in;

import com.bonial.challenge.domain.Coordinates;
import com.bonial.challenge.domain.Locations;

public interface LocationsQuery {

    Locations getLocations(Coordinates userCoordinates);
}
