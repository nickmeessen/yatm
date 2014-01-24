Yet Another Tasklist Manager, for Android.
=========
For a school project we were asked to build a simple Android application, this application should be made in two versions, one representing an example of how code should be written. This app will adhere to several guidelines. Including, but not limited to : 

- [Android Guidelines](http://source.android.com/source/code-style.html)
- [PMD](http://pmd.sourceforge.net/pmd-5.0.5/)
- [IFSQ](http://www.ifsq.org/level-2.html)

And one version that rebels against all above rules. Starting out with a hack-up job of a simple ToDo application, we started refactoring all the _bad smells_ out of this version, eventually ending up with this simple application.

It is developed for, and tested on Android 4.3. We can't guarantee any compatibility with future versions. The software is provided as is, you can install it from the app store here : [Google Play Store - YATM](https://play.google.com/store/apps/details?id=nl.enterprisecoding.android.sufficient)

Screenshots
=========
![](https://raw2.github.com/enterprisecoding/yatm/gh-pages/images/screenshot1.png =160x)
![](https://raw2.github.com/enterprisecoding/yatm/gh-pages/images/screenshot2.png =160x)
![](https://raw2.github.com/enterprisecoding/yatm/gh-pages/images/screenshot3.png =160x)

Compiling/Installing
=========
We used the following tools to build :
- [Maven 3.1.1](http://maven.apache.org)
- [Java JDK 1.7](http://www.oracle.com/technetwork/java/javase/overview/index.html)
- [Android SDK 22.3](http://developer.android.com) with 4.3 sources.
- [GIT 1.8](http://git-scm.com/downloads)
- [Maven-Android-SDK-Deployer (4a006823ab3ec0764ddfa652e38f60caa5b2cdfc)](https://github.com/mosabua/maven-android-sdk-deployer)

After installing all the tools, use [Maven-Android-SDK-Deployer](https://github.com/mosabua/maven-android-sdk-deployer) to install the Android 4.3 source for Maven (`mvn install -P 4.3`).

After pulling our code, run `mvn package` which will result in an installable .apk file.

Authors
=========
This application was realised by :
- [Nick Meessen](https://github.com/nickmeessen) (http://nickmeessen.nl)
- [Sjors Roelofs](https://github.com/sjorsroelofs) (http://www.sjorsroelofs.nl)
- Breunie Ploeg (http://www.bploeg.com)
- Ferry Wienholts
- Jasper Burgers
