#!/bin/bash

echo "🎃 Building Halloween Trick or Treat Plugin..."
echo ""

./gradlew clean build

if [ $? -eq 0 ]; then
    echo ""
    echo "✅ Build successful!"
    echo "📦 Plugin JAR location: build/libs/TrickOrTreat-1.0.0.jar"
    echo ""
    echo "To install:"
    echo "1. Copy the JAR file to your server's plugins folder"
    echo "2. (Optional) Install Vault for economy features"
    echo "3. Restart your server"
    echo ""
    echo "Happy Halloween! 🎃👻🍬"
else
    echo ""
    echo "❌ Build failed. Please check the errors above."
    exit 1
fi
