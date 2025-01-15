#!/bin/bash

# Configuration
APP_NAME="Citation Downloader"
APP_VERSION="1.0.0"
MAIN_JAR="citation-downloader-1.0-SNAPSHOT.jar"
MAIN_CLASS="com.example.citationdownloader.UI.MainUI"
IDENTIFIER="com.example.citationdownloader"
VENDOR="Your Name"

# Clean and package with Maven
echo "Building with Maven..."
mvn clean package

# Create temp directory for packaging
echo "Creating packaging directory..."
mkdir -p target/packaging/libs

# Copy dependencies
echo "Copying dependencies..."
cp target/$MAIN_JAR target/packaging/
cp target/lib/* target/packaging/libs/ 2>/dev/null || :

# Create app image
echo "Creating application image..."
jpackage \
  --input target/packaging \
  --name "$APP_NAME" \
  --main-jar $MAIN_JAR \
  --main-class $MAIN_CLASS \
  --type dmg \
  --icon src/main/resources/icon.icns \
  --app-version $APP_VERSION \
  --vendor "$VENDOR" \
  --copyright "Copyright 2024 $VENDOR" \
  --mac-package-name "$APP_NAME" \
  --mac-package-identifier $IDENTIFIER \
  --mac-sign \
  --verbose

# Clean up
echo "Cleaning up..."
rm -rf target/packaging

echo "Done! DMG file created as '$APP_NAME-$APP_VERSION.dmg'" 