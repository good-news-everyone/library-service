package com.hometech.testref

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.hometech.testref.config.RepositoryHolder
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import java.nio.charset.Charset

@ExtendWith(SpringExtension::class)
@ContextConfiguration
@ActiveProfiles("test")
@SpringBootTest(
    classes = [LibraryApp::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc(addFilters = false)
abstract class AbstractIntegrationTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var repositoryHolder: RepositoryHolder

    @BeforeEach
    fun cleanUp() {
        repositoryHolder.dropData()
    }

    fun Any.asJson(): String = objectMapper.writeValueAsString(this)

    final inline fun <reified T> MvcResult.asObject(): T {
        return this.response.getContentAsString(Charset.forName("UTF-8")).asObject()
    }

    final inline fun <reified T> String.asObject(): T {
        return objectMapper.readValue(this)
    }

    final inline fun <reified T> Any.save(): T {
        return repositoryHolder.save(this) as T
    }
}
