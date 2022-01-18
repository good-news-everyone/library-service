package com.hometech.testref.common.configuration

import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class ObjectMapperConfiguration {

    @Bean
    @Primary
    fun objectMapper(): ObjectMapper {
        return ObjectMapper()
            .apply {
                findAndRegisterModules()
                enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
                configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            }
    }
}
