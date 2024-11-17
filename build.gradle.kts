plugins {
	java
	application
	id("org.springframework.boot") version "3.3.4"
	id("io.spring.dependency-management") version "1.1.6"
	id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "com.prop"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

application {
	mainClass.set("com.prop.prop12_1.Prop121Application")
}

repositories {
	mavenCentral()
}

configurations.all {
	exclude(group = "commons-logging", module = "commons-logging")
}

dependencies {
	implementation("com.google.guava:guava:33.3.1-jre")
	implementation ("org.apache.commons:commons-lang3:3.17.0")
	implementation ("org.apache.commons:commons-configuration2:2.11.0")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-tomcat")
	implementation("org.springframework.shell:spring-shell-starter")
	implementation("org.springframework.boot:spring-boot-starter-aop:3.3.3")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.shell:spring-shell-dependencies:3.3.3")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks {
	withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
		archiveBaseName.set("Prop121")
		archiveClassifier.set("all")
		archiveVersion.set("1.0.0")

		manifest {
			attributes(
				"Main-Class" to "com.prop.prop12_1.Prop121Application"
			)
		}
	}
}
