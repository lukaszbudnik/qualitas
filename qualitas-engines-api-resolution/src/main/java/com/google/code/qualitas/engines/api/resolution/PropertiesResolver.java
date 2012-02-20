package com.google.code.qualitas.engines.api.resolution;

import com.google.code.qualitas.engines.api.component.Component;
import com.google.code.qualitas.engines.api.core.Bundle;

/**
 * The Interface PropertiesResolver.
 */
public interface PropertiesResolver extends Component {

    /**
     * Resolve.
     * 
     * @param bundle
     *            the bundle
     * @return the properties
     * @throws ResolutionException
     *             the resolution exception
     */
    Properties resolve(Bundle bundle) throws ResolutionException;

}
