package jjalsel.be_playground.common.utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;

@Converter
public class IntArrayConverter implements AttributeConverter<int[], String> {

    @Override
    public String convertToDatabaseColumn(int[] attribute) {
        if (attribute == null) {
            return null;
        }
        // 배열을 콤마로 구분된 문자열로 변환
        return Arrays.toString(attribute)
                .replaceAll("\\[|\\]|\\s", "");
    }

    @Override
    public int[] convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return new int[0];
        }
        // 문자열을 배열로 변환
        return Arrays.stream(dbData.split(","))
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}
