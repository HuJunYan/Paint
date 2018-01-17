#!/bin/sh

./gradlew assembleRelease

sed -i "s/baidu/m360/g" app/build.gradle
sed -i "s/channel_id = 1/channel_id = 2/g" app/src/main/java/com/koudai/operate/utils/Utils.java

./gradlew assembleRelease

sed -i "s/m360/baidu/g" app/build.gradle
sed -i "s/channel_id = 2/channel_id = 1/g" app/src/main/java/com/koudai/operate/utils/Utils.java
