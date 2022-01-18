package com.hometech.testref

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LibraryApp

fun main(args: Array<String>) {
	runApplication<LibraryApp>(*args)
}
