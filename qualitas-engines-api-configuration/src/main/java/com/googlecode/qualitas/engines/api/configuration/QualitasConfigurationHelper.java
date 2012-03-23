package com.googlecode.qualitas.engines.api.configuration;

import static ch.lambdaj.Lambda.having;
import static ch.lambdaj.Lambda.on;
import static ch.lambdaj.Lambda.selectFirst;
import static org.hamcrest.Matchers.equalTo;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * The Class QualitasConfigurationHelper.
 */
public final class QualitasConfigurationHelper {

    /**
     * The Enum ParameterType.
     */
    public enum ParameterType {

        /** The METRIC. */
        METRIC,
        /** The WEIGHT. */
        WEIGHT
    }

    /**
     * Instantiates a new qualitas configuration helper.
     */
    private QualitasConfigurationHelper() {
    }

    /**
     * Gets the analyst parameter.
     * 
     * @param qualitasConfiguration
     *            the qualitas configuration
     * @param partnerName
     *            the partner name
     * @param serviceName
     *            the service name
     * @param parameterName
     *            the parameter name
     * @param parameterType
     *            the parameter type
     * @return the double
     */
    public static Double getAnalystParameter(
            QualitasConfiguration qualitasConfiguration, String partnerName,
            String serviceName, String parameterName,
            ParameterType parameterType) {
        Double weight = 0d;

        if (parameterType == ParameterType.WEIGHT) {
            parameterName = parameterName + "Weight";
        }

        AnalystConfiguration analystConfiguration = qualitasConfiguration
                .getAnalystConfiguration();

        AnalystGlobalParameters analystGlobalParameters = analystConfiguration
                .getGlobalParameters();

        // global parameters are required
        try {
            weight = (Double) PropertyUtils.getProperty(
                    analystGlobalParameters, parameterName);
        } catch (Exception e) {
            throw new IllegalArgumentException("'" + parameterName
                    + "' not found in qualitas configuration.");
        }

        // get partner
        AnalystPartner analystPartner = selectFirst(
                analystConfiguration.getPartners().getPartner(),
                having(on(AnalystPartner.class).getName(), equalTo(partnerName)));

        // there is no partner element, return global
        if (analystPartner == null) {
            return weight;
        }

        // get service
        AnalystService analystService = selectFirst(
                analystPartner.getServices().getService(),
                having(on(AnalystService.class).getName(), equalTo(serviceName)));

        // get partner parameter
        AnalystParameters analystParameters = analystPartner.getParameters();
        try {
            Double tempWeight = (Double) PropertyUtils.getProperty(
                    analystParameters, parameterName);

            // override weight only when tempWeight is not null
            if (tempWeight != null) {
                weight = tempWeight;
            }

        } catch (Exception e) {
            throw new IllegalArgumentException("'" + parameterName
                    + "' not found in qualitas configuration.");
        }

        // get service parameter
        analystParameters = analystService.getParameters();
        try {
            Double tempWeight = (Double) PropertyUtils.getProperty(
                    analystParameters, parameterName);

            // override weight only when tempWeight is not null
            if (tempWeight != null) {
                weight = tempWeight;
            }

        } catch (Exception e) {
            throw new IllegalArgumentException("'" + parameterName
                    + "' not found in qualitas configuration.");
        }

        return weight;
    }
}
