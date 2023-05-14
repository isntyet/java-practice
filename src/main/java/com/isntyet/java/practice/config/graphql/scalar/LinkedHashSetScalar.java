package com.isntyet.java.practice.config.graphql.scalar;

import com.netflix.graphql.dgs.DgsScalar;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@DgsScalar(name = "LinkedHashSet")
public class LinkedHashSetScalar implements Coercing<LinkedHashSet<String>, List<String>> {

    @Override
    public List<String> serialize(Object value) throws CoercingSerializeException {
        if (value instanceof LinkedHashSet) {
            LinkedHashSet<String> set = (LinkedHashSet<String>) value;
            List<String> list = new ArrayList<>();
            for (String element : set) {
                list.add(element);
            }
            return list;
        }
        return new ArrayList<>();
    }

    @Override
    public LinkedHashSet<String> parseValue(Object input) throws CoercingParseValueException {
        if (input instanceof List) {
            List<?> inputList = (List<?>) input;
            LinkedHashSet<String> result = new LinkedHashSet<>();
            for (Object element : inputList) {
                if (element instanceof String) {
                    result.add((String) element);
                } else {
                    throw new CoercingParseValueException("Invalid input value: " + element);
                }
            }
            return result;
        }
        throw new CoercingParseValueException("Invalid input value: " + input);
    }

    @Override
    public LinkedHashSet<String> parseLiteral(Object input) throws CoercingParseLiteralException {
        if (input instanceof List) {
            List<?> inputList = (List<?>) input;
            LinkedHashSet<String> result = new LinkedHashSet<>();
            for (Object element : inputList) {
                if (element instanceof String) {
                    result.add((String) element);
                } else {
                    throw new CoercingParseValueException("Invalid input value: " + element);
                }
            }
            return result;
        }
        throw new CoercingParseValueException("Invalid input value: " + input);
    }
}
