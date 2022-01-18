package com.hometech.testref.common.configuration.converter

import java.time.Year

import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter(autoApply = true)
class YearConverter : AttributeConverter<Year, Int> {
    override fun convertToDatabaseColumn(attribute: Year): Int {
        return attribute.value
    }

    override fun convertToEntityAttribute(dbData: Int): Year {
        return Year.of(dbData)
    }
}
