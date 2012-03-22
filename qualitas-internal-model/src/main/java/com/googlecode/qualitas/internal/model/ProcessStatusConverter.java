package com.googlecode.qualitas.internal.model;

import org.eclipse.persistence.mappings.DatabaseMapping;
import org.eclipse.persistence.mappings.converters.Converter;
import org.eclipse.persistence.sessions.Session;

import com.google.code.p.qualitas.engines.api.configuration.ProcessStatus;

public class ProcessStatusConverter implements Converter {

    private static final long serialVersionUID = -831135597711719103L;

    @Override
    public ProcessStatus convertDataValueToObjectValue(Object dataValue, Session session) {
        Integer index = (Integer) dataValue;
        return ProcessStatus.values()[index];
    }

    @Override
    public Integer convertObjectValueToDataValue(Object objectValue, Session session) {
        ProcessStatus processStatus = (ProcessStatus) objectValue;
        Integer index = processStatus.ordinal();
        return index;
    }

    @Override
    public void initialize(DatabaseMapping databaseMapping, Session session) {
    }

    @Override
    public boolean isMutable() {
        return false;
    }

}
