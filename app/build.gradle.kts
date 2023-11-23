plugins {
	id("com.android.application")
	id("org.jetbrains.kotlin.android")
	id("androidx.navigation.safeargs")
}

android {
	namespace = "com.kelvin.traveling"
	compileSdk = 34

	defaultConfig {
		applicationId = "com.kelvin.traveling"
		minSdk = 28
		targetSdk = 34
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
		sourceCompatibility = JavaVersion.VERSION_17
		targetCompatibility = JavaVersion.VERSION_17
	}

	buildFeatures {
		viewBinding = true
	}
}

dependencies {
	// Nav Component
	implementation("androidx.navigation:navigation-fragment-ktx:2.7.5")
	implementation("androidx.navigation:navigation-ui-ktx:2.7.5")

	// Safe Args
	implementation("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.5")
	implementation("net.java.dev.jna:jna-platform:5.6.0") {
		exclude(group = "net.java.dev.jna", module = "jna")
		exclude(group = "META-INF", module = "AL2.0")
	}

	implementation("net.java.dev.jna:jna:5.6.0")

	implementation("androidx.appcompat:appcompat:1.6.1")
	implementation("com.google.android.material:material:1.10.0")
	implementation("androidx.constraintlayout:constraintlayout:2.1.4")
	implementation("androidx.fragment:fragment-ktx:1.6.2")

	testImplementation("junit:junit:4.13.2")
	androidTestImplementation("androidx.test.ext:junit:1.1.5")
	androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}