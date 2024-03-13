import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.2.3"
	id("io.spring.dependency-management") version "1.1.4"
	kotlin("jvm") version "1.9.22"
	kotlin("plugin.spring") version "1.9.22"
	`maven-publish`
}

group = "ru.astondevs"
version = "0.0.1"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-aop")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}
publishing {
	publications {
		create<MavenPublication>("mavenKotlin") {
			from(components["kotlin"])
			versionMapping {
				usage("kotlin-api") {
					fromResolutionOf("runtimeClasspath")
				}
				usage("kotlin-runtime") {
					fromResolutionResult()
				}
			}
		}
	}
	repositories {
		mavenLocal()
	}
}
tasks.withType<Test> {
	useJUnitPlatform()
}
