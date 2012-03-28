package com.googlecode.qualitas.internal.model.converters;

import org.eclipse.persistence.mappings.DatabaseMapping;
import org.eclipse.persistence.mappings.converters.Converter;
import org.eclipse.persistence.sessions.Session;

import com.googlecode.qualitas.engines.api.configuration.ProcessType;

public class ProcessTypeConverter implements Converter {

    private static final long serialVersionUID = 8884167883233252104L;

    @Override
    public Object convertDataValueToObjectValue(Object dataValue, Session session) {
        Integer index = (Integer) dataValue;
        return ProcessType.values()[index];
    }

    @Override
    public Object convertObjectValueToDataValue(Object objectValue, Session session) {
        ProcessType processType = (ProcessType) objectValue;
        Integer index = processType.ordinal();
        return index;
    }

    @Override
    public void initialize(DatabaseMapping arg0, Session arg1) {
    }

    @Override
    public boolean isMutable() {
        return false;
    }

}
