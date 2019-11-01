# DSO2 Mobile

This repository contains files used for creating a mobile application for a college work, under the discipline of Object Oriented Systems Development 2 - hence the acronym DSO2, which stands for the same name in Portuguese.

### What does it do?
This application fetches data from the [Portal da Transparência](http://www.portaltransparencia.gov.br/) api, providing data regarding government related expenses. 
A main screen displays public agencies that can be searched through their code. The detail screen shows a list of trips made by the public agency's servers - both the main screen and the detail screen are a paginated list. Lastly, the trip details screen simply shows that trip's expenses.

### Techs used
A monolithic app, i tried to employ Google's [Jetpack Component](https://developer.android.com/jetpack) for most part of it. Other than that, dependencies include:
- [Retrofit](https://square.github.io/retrofit/)
- [Kotlin Coroutines](https://github.com/Kotlin/kotlinx.coroutines)
- [Moshi](https://github.com/square/moshi)

### To run this
Clone the repository, open it on Android Studio, and run it.
