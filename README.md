# DuckDuckDefine - Kotlin - Android


Originally an iOS MVC project ripped apart then put back together with MVP, translated to the Android framework, sharing common business logic between iOS and Android app architectures, but written in Kotlin!

My initial inspiration for this project was to refamiliarise my understanding of iOS development, bringing across refined architecture concepts and workflows used for developing Android apps, to bridge my knowledge-gap of iOS development. I was unable to find an iOS boilerplate project which combined MVP with Rx, Dependency Injection, near-auto JSON serialisation, and image loading & caching, all of which is commonly found in Android MVP boilerplate projects, and are mandatory in the Android apps I develop.

This project will benefit those who develop for either Android or iOS, and those who program in Java or Kotlin, which intend to transition to the alternative at some stage. I have linked the iOS-alternate, and Android-Java-alternate to this project with common libraries found in production apps today. Both projects share the same business logic and will aid in understanding the differences between Swift, Java & Kotlin syntax, and iOS & Android frameworks, learning through example.

[The Java-alternative project can be found here](https://github.com/teeeeeegz/DuckDuckDefine-Android)

[The iOS-alternative project can be found here](https://github.com/teeeeeegz/DuckDuckDefine-iOS)


### Libraries:
The following libraries were used, most of which have an iOS alternative:
 * [RxJava](https://github.com/ReactiveX/RxJava) to provide a reactive programming standard, with the same foundation as [RxSwift](https://github.com/ReactiveX/RxSwift)
 * [Retrofit](https://github.com/square/retrofit) and [OkHttp](https://github.com/square/okhttp) for API communication. Alternative to [Alamofire](https://github.com/Alamofire/Alamofire)
 * [Moshi](https://github.com/square/moshi) to serialise JSON to POJO's, and vice-versa. Alternative to [Gloss](https://github.com/hkellaway/Gloss)
 * [Dagger](https://github.com/google/dagger) for dependency injection. Alternative to [Swinject](https://github.com/Swinject/Swinject)
 * [Picasso](https://github.com/square/picasso) for image caching and loading. Alternative to [Kingfisher](https://github.com/onevcat/Kingfisher)
 * [Android Support Library](https://developer.android.com/topic/libraries/support-library/revisions.html) to provide common frameworks backportable to earlier versions of Android
 * [RxAndroid](https://github.com/ReactiveX/RxAndroid) which provides RxJava bindings relative to the Android framework
 * [RxKotlin](https://github.com/ReactiveX/RxKotlin) which provides Kotlin extensions for RxJava
 * [Timber](https://github.com/JakeWharton/timber) an improved logger which has an extensible API
 * [Dexcount](https://github.com/KeepSafe/dexcount-gradle-plugin) to keep tabs on your method reference count for each build


Original iOS project downloaded from Ray Wenderlich's site, [here](https://www.raywenderlich.com/109330/carthage-tutorial-getting-started).