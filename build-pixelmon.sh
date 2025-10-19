#!/bin/bash

echo "Building Halloween Trick or Treat - Pixelmon Edition"
echo "======================================================"

# Backup the regular listener if it exists
if [ -f "src/main/java/com/halloween/trickortreat/listeners/PixelmonDeathListener.java" ]; then
    echo "Backing up existing PixelmonDeathListener.java..."
    mv src/main/java/com/halloween/trickortreat/listeners/PixelmonDeathListener.java \
       src/main/java/com/halloween/trickortreat/listeners/PixelmonDeathListener.java.backup
fi

# Restore the Pixelmon listener
if [ -f "src/main/java/com/halloween/trickortreat/listeners/PixelmonDeathListener.java.pixelmon" ]; then
    echo "Activating Pixelmon listener..."
    cp src/main/java/com/halloween/trickortreat/listeners/PixelmonDeathListener.java.pixelmon \
       src/main/java/com/halloween/trickortreat/listeners/PixelmonDeathListener.java
else
    echo "ERROR: PixelmonDeathListener.java.pixelmon not found!"
    exit 1
fi

# Temporarily replace the main build.gradle with Pixelmon version
echo "Switching to Pixelmon build configuration..."
mv build.gradle build.gradle.backup
cp build-pixelmon.gradle build.gradle

# Build with Pixelmon configuration
echo "Building with Pixelmon dependencies..."
./gradlew clean build

# Check if build was successful
if [ $? -eq 0 ]; then
    echo ""
    echo "Build successful!"
    echo "JAR file created: build/libs/TrickOrTreat-1.1.1-pixelmon.jar"
    
    # Clean up - restore original build.gradle and remove the active listener
    echo "Cleaning up..."
    mv build.gradle.backup build.gradle
    rm src/main/java/com/halloween/trickortreat/listeners/PixelmonDeathListener.java
    
    # Restore backup if it existed
    if [ -f "src/main/java/com/halloween/trickortreat/listeners/PixelmonDeathListener.java.backup" ]; then
        mv src/main/java/com/halloween/trickortreat/listeners/PixelmonDeathListener.java.backup \
           src/main/java/com/halloween/trickortreat/listeners/PixelmonDeathListener.java
    fi
else
    echo ""
    echo "Build failed! Check the error messages above."
    
    # Clean up on failure too
    mv build.gradle.backup build.gradle
    rm src/main/java/com/halloween/trickortreat/listeners/PixelmonDeathListener.java
    
    if [ -f "src/main/java/com/halloween/trickortreat/listeners/PixelmonDeathListener.java.backup" ]; then
        mv src/main/java/com/halloween/trickortreat/listeners/PixelmonDeathListener.java.backup \
           src/main/java/com/halloween/trickortreat/listeners/PixelmonDeathListener.java
    fi
    
    exit 1
fi
