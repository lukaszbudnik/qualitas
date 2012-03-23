package com.googlecode.qualitas.engines.api.resolution;

import com.googlecode.qualitas.engines.api.component.Component;
import com.googlecode.qualitas.engines.api.core.Bundle;

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
