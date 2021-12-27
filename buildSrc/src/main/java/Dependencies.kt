object Versions {
    const val COMPILE_SDK = 31
    const val TARGET_SDK = 30
    const val MIN_SDK = 23

    const val APP_VERSION_CODE = 81

    const val MAJOR = 1
    const val MINOR = 18
    const val PATCH = 0

    const val KOTLIN = "1.4.32" //https://developer.android.com/jetpack/androidx/releases/core
    const val KOTLIN_COROUTINES = "1.5.0" //https://github.com/Kotlin/kotlinx.coroutines/releases/
    const val KOTLIN_COROUTINES_TEST = "1.3.2"
    const val ANDROIDX_CORE_KTX = "1.6.0" //https://developer.android.com/jetpack/androidx/releases/core
    const val ANDROIDX_COMPAT = "1.3.0" //https://developer.android.com/jetpack/androidx/releases/appcompat
    const val ANDROIDX_CONSTRAINT = "2.0.4" //https://developer.android.com/jetpack/androidx/releases/constraintlayout
    const val ANDROIDX_LIFECYCLE = "2.3.1" //https://developer.android.com/jetpack/androidx/releases/lifecycle
    const val ANDROIDX_RECYCLER_VIEW = "1.2.0"
    const val FRAGMENT_TESTING = "1.3.4"
    const val ANDROIDX_BIOMETRIC = "1.0.1" //https://developer.android.com/jetpack/androidx/releases/biometric
    const val ANDROIDX_TEST_JUNIT = "1.1.2"
    const val ANDROIDX_WORK = "2.7.1" //https://developer.android.com/jetpack/androidx/releases/work
    const val ANDROIDX_ROOM = "2.4.0-alpha03" //https://developer.android.com/jetpack/androidx/releases/room
    const val ANDROIDX_PAGING = "3.1.0-alpha01" //https://developer.android.com/jetpack/androidx/releases/paging
    const val ANDROIDX_FRAGMENT_KTX = "1.3.6" //https://developer.android.com/jetpack/androidx/releases/fragment
    const val ANDROIDX_TEST_CORE_KTX = "1.3.0"
    const val ANDROIDX_TEST_ESPRESSO = "3.3.0"
    const val ANDROIDX_TEST_RULES = "1.3.0"
    const val ANDROIDX_TEST_RUNNER = "1.3.0"
    const val ANDROIDX_TEST_UIAUTOMATOR = "2.2.0"
    const val ANDROIDX_PREF_KTX = "1.1.1"
    const val ANDROIDX_ARCH_CORE = "2.1.0" //https://developer.android.com/jetpack/androidx/releases/arch-core
    const val FIREBASE = "28.2.1" //https://firebase.google.com/support/release-notes/android
    const val DAGGER = "2.38.1" //https://github.com/google/dagger/releases
    const val HILT_ANDROID = "1.0.0" //https://developer.android.com/jetpack/androidx/releases/hilt
    const val JUNIT = "4.13.2"
    const val DROPBOX_SDK = "5.1.1" //https://github.com/dropbox/dropbox-sdk-java/releases
    const val PLAY_CORE = "1.10.0"
    const val MOCKITO_KOTLIN = "2.0.0"
    const val MOCKITO_ANDROID = "2.23.0"
    const val ROBOLECTRIC = "4.6.1" //https://github.com/robolectric/robolectric/releases/
    const val TRUTH = "1.1.3"
    const val MAVERICKS = "2.2.0" //https://github.com/airbnb/mavericks/releases
    const val THREE_TEN_ABP = "1.3.1" //https://github.com/JakeWharton/ThreeTenABP/tags
    const val MATERIAL = "1.4.0-rc01"
    const val OSS_LICENSES = "17.0.0"
    const val COMPACT_CAL_VIEW = "3.0.0"
    const val APACHE = "1.6"
    const val RX_JAVA = "2.2.9"
    const val RX_ANDROID = "2.1.1"
    const val RX_KOTLIN = "2.3.0"
    const val RX_BINDING = "2.1.1"
    const val ESPRESSO = "3.3.0"
    const val TEST_ORCHESTRATOR = "1.0.2"
}

object Libraries {
   const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.KOTLIN}"
   const val kotlin_coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.KOTLIN_COROUTINES}"
   const val androidx_core_ktx = "androidx.core:core-ktx:${Versions.ANDROIDX_CORE_KTX}"
   const val firebase_bom = "com.google.firebase:firebase-bom:${Versions.FIREBASE}"
   const val firebase_analytics_ktx = "com.google.firebase:firebase-analytics-ktx"
   const val firebase_crashlytics = "com.google.firebase:firebase-crashlytics"
   const val dagger = "com.google.dagger:dagger:${Versions.DAGGER}"
   const val dagger_compiler = "com.google.dagger:dagger-compiler:${Versions.DAGGER}"
   const val dagger_android_support = "com.google.dagger:dagger-android-support:${Versions.DAGGER}"
   const val dagger_android_processor = "com.google.dagger:dagger-android-processor:${Versions.DAGGER}"
   const val hilt = "com.google.dagger:hilt-android:${Versions.DAGGER}"
   const val hilt_compiler = "com.google.dagger:hilt-android-compiler:${Versions.DAGGER}"
   const val hilt_android_compiler = "androidx.hilt:hilt-compiler:${Versions.HILT_ANDROID}"
   const val mavericks = "com.airbnb.android:mavericks:${Versions.MAVERICKS}"
   const val mavericks_mocking = "com.airbnb.android:mavericks-mocking:${Versions.MAVERICKS}"
   const val androidx_compat = "androidx.appcompat:appcompat:${Versions.ANDROIDX_COMPAT}"
   const val androidx_constraint_layout = "androidx.constraintlayout:constraintlayout:${Versions.ANDROIDX_CONSTRAINT}"
   const val androidx_fragment = "androidx.fragment:fragment-ktx:${Versions.ANDROIDX_FRAGMENT_KTX}"
   const val androidx_lifecycle_compiler = "androidx.lifecycle:lifecycle-compiler:${Versions.ANDROIDX_LIFECYCLE}"
   const val androidx_viewmodel_ktx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.ANDROIDX_LIFECYCLE}"
   const val androidx_livedata_ktx = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.ANDROIDX_LIFECYCLE}"
   const val androidx_lifecycle_runtime_ktx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.ANDROIDX_LIFECYCLE}"
   const val androidx_recycler_view = "androidx.recyclerview:recyclerview:${Versions.ANDROIDX_RECYCLER_VIEW}"
   const val androidx_preference_ktx = "androidx.preference:preference-ktx:${Versions.ANDROIDX_PREF_KTX}"
   const val androidx_work_runtime_ktx = "androidx.work:work-runtime-ktx:${Versions.ANDROIDX_WORK}"
   const val androidx_hilt_work = "androidx.hilt:hilt-work:${Versions.HILT_ANDROID}"
   const val androidx_biometric = "androidx.biometric:biometric:${Versions.ANDROIDX_BIOMETRIC}"
   const val androidx_paging_runtime = "androidx.paging:paging-runtime:${Versions.ANDROIDX_PAGING}"
   const val androidx_room_ktx = "androidx.room:room-ktx:${Versions.ANDROIDX_ROOM}"
   const val androidx_room_runtime = "androidx.room:room-runtime:${Versions.ANDROIDX_ROOM}"
   const val androidx_room_compiler = "androidx.room:room-compiler:${Versions.ANDROIDX_ROOM}"
   const val three_ten_abp = "com.jakewharton.threetenabp:threetenabp:${Versions.THREE_TEN_ABP}"
   const val dropbox_sdk = "com.dropbox.core:dropbox-core-sdk:${Versions.DROPBOX_SDK}"
   const val play_core = "com.google.android.play:core:${Versions.PLAY_CORE}"
   const val material = "com.google.android.material:material:${Versions.MATERIAL}"
   const val play_services_oss_licenses = "com.google.android.gms:play-services-oss-licenses:${Versions.OSS_LICENSES}"
   const val compact_calendar_view = "com.github.sundeepk:compact-calendar-view:${Versions.COMPACT_CAL_VIEW}"
   const val apache_text = "org.apache.commons:commons-text:${Versions.APACHE}"
   const val apache_csv = "org.apache.commons:commons-csv:${Versions.APACHE}"
   const val rxjava = "io.reactivex.rxjava2:rxjava:${Versions.RX_JAVA}"
   const val rxandroid = "io.reactivex.rxjava2:rxandroid:${Versions.RX_ANDROID}"
   const val rxkotlin = "io.reactivex.rxjava2:rxkotlin:${Versions.RX_KOTLIN}" //TODO do we need?
   const val rxbinding = "com.jakewharton.rxbinding2:rxbinding:${Versions.RX_BINDING}"
}

object TestLibraries {
    const val junit = "junit:junit:${Versions.JUNIT}"
    const val kotlin_test_junit = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.KOTLIN}"
    const val kotlin_coroutines_test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.KOTLIN_COROUTINES_TEST}"
    const val mockito_kotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.MOCKITO_KOTLIN}"
    const val mockito_android = "org.mockito:mockito-android:${Versions.MOCKITO_ANDROID}"
    const val robolectric = "org.robolectric:robolectric:${Versions.ROBOLECTRIC}"
    const val truth = "com.google.truth:truth:${Versions.TRUTH}"
    const val fragment_testing = "androidx.fragment:fragment-testing:${Versions.FRAGMENT_TESTING}"
    const val mavericks_testing = "com.airbnb.android:mavericks-testing:${Versions.MAVERICKS}"
    const val androidx_arch_testing = "androidx.arch.core:core-testing:${Versions.ANDROIDX_ARCH_CORE}"
    const val androidx_test_junit = "androidx.test.ext:junit:${Versions.ANDROIDX_TEST_JUNIT}"
    const val androidx_test_core_ktx = "androidx.test:core-ktx:${Versions.ANDROIDX_TEST_CORE_KTX}"
    const val androidx_test_espresso_core = "androidx.test.espresso:espresso-core:${Versions.ANDROIDX_TEST_ESPRESSO}"
    const val androidx_test_espresso_contrib = "androidx.test.espresso:espresso-contrib:${Versions.ANDROIDX_TEST_ESPRESSO}"
    const val androidx_test_espresso_intents = "androidx.test.espresso:espresso-intents:${Versions.ANDROIDX_TEST_ESPRESSO}"
    const val androidx_test_runner = "androidx.test:runner:${Versions.ANDROIDX_TEST_RUNNER}"
    const val androidx_test_rules = "androidx.test:rules:${Versions.ANDROIDX_TEST_RULES}"
    const val androidx_test_uiautomator = "androidx.test.uiautomator:uiautomator:${Versions.ANDROIDX_TEST_UIAUTOMATOR}"
    const val androidx_room_testing = "androidx.room:room-testing:${Versions.ANDROIDX_ROOM}"
    const val androidx_work_testing = "androidx.work:work-testing:${Versions.ANDROIDX_WORK}"
    const val hilt_android_testing = "com.google.dagger:hilt-android-testing:${Versions.DAGGER}"
    const val espresso_core = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO}"
    const val espresso_contrib = "androidx.test.espresso:espresso-contrib:${Versions.ESPRESSO}"
    const val test_orchestrator = "com.android.support.test:orchestrator:${Versions.TEST_ORCHESTRATOR}"
    const val three_ten_abp = "org.threeten:threetenbp:${Versions.THREE_TEN_ABP}"
}