import java.util.Properties

plugins {
	alias(libs.plugins.android.application)
	alias(libs.plugins.kotlin.android)
	alias(libs.plugins.kotlin.compose)
	id("com.google.dagger.hilt.android")
	kotlin("kapt")
}

android {
	namespace = "com.aswanth.rentease"
	compileSdk = 35

	defaultConfig {
		applicationId = "com.aswanth.rentease"
		minSdk = 26
		targetSdk = 35
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

		// Load properties correctly
		val properties = Properties()
		properties.load(project.rootProject.file("local.properties").inputStream())

		// Set manifestPlaceholders using Kotlin syntax
		manifestPlaceholders["GOOGLE_MAPS_API_KEY"] = properties.getProperty("GOOGLE_MAPS_API_KEY", "")

		// Set buildConfigField with the loaded property
		buildConfigField(
			"String",
			"GOOGLE_MAPS_API_KEY",
			"\"${properties.getProperty("GOOGLE_MAPS_API_KEY", "")}\""
		)
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"),
				"proguard-rules.pro"
			)
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_11
		targetCompatibility = JavaVersion.VERSION_11
	}
	kotlinOptions {
		jvmTarget = "11"
	}
	buildFeatures {
		compose = true
		buildConfig = true
	}
}

dependencies {

	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.lifecycle.runtime.ktx)
	implementation(libs.androidx.activity.compose)
	implementation(platform(libs.androidx.compose.bom))
	implementation(libs.androidx.ui)
	implementation(libs.androidx.ui.graphics)
	implementation(libs.androidx.ui.tooling.preview)
	implementation(libs.androidx.material3)
	implementation(libs.androidx.material.icons.extended)

	//ViewModel and StateFlow
	implementation(libs.androidx.lifecycle.viewmodel.compose)

	//Google Places API(for auto-suggestions)
	implementation(libs.places)

	// Dependency injection
//	implementation("androidx.hilt:hilt-compiler:1.2.0")
//	implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")
	implementation(libs.hilt.android)
	kapt(libs.hilt.android.compiler)

	testImplementation(libs.junit)
	androidTestImplementation(libs.androidx.junit)
	androidTestImplementation(libs.androidx.espresso.core)
	androidTestImplementation(platform(libs.androidx.compose.bom))
	androidTestImplementation(libs.androidx.ui.test.junit4)
	debugImplementation(libs.androidx.ui.tooling)
	debugImplementation(libs.androidx.ui.test.manifest)
}