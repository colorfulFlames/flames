#
# Copyright (c) 2023 Several Circles
#

echo "Updating Flames"
git fetch --all
git reset --hard origin/master
git pull
chmod +x ./gradlew
echo "Building Flames"
./gradlew build
echo "Starting Flames"
./gradlew run