# Gradle Build Guide

## ✅ Project Successfully Converted to Gradle!

The Halloween Trick or Treat plugin now uses Gradle instead of Maven.

## Build Commands

### Using Gradle Wrapper (Recommended)
```bash
./gradlew clean build
```

### Using Build Script
```bash
./build.sh
```

### Other Useful Commands
```bash
./gradlew clean
./gradlew jar
./gradlew tasks
```

## Output Location

The compiled plugin JAR will be located at:
```
build/libs/TrickOrTreat-1.0.0.jar
```

## Build Configuration

- **Build Tool**: Gradle 8.5
- **Java Version**: 17 (compatible with Java 21)
- **Plugin Type**: Spigot/Paper plugin
- **Dependencies**: 
  - Spigot API 1.20.1 (provided)
  - VaultAPI 1.7.1 (provided, optional)

## Project Structure

```
windsurf-project-5/
├── build.gradle              # Gradle build configuration
├── settings.gradle           # Project settings
├── gradlew                   # Gradle wrapper (Unix)
├── gradlew.bat              # Gradle wrapper (Windows)
├── gradle/                   # Gradle wrapper files
├── src/                      # Source code
└── build/                    # Build output
    └── libs/                 # Compiled JARs
```

## What Changed from Maven

1. **Build File**: `pom.xml` → `build.gradle`
2. **Output Directory**: `target/` → `build/libs/`
3. **Build Command**: `mvn clean package` → `./gradlew clean build`
4. **Version Variable**: `${project.version}` → `${version}` in plugin.yml

## Notes

- The `pom.xml` file is still present but no longer used
- Gradle wrapper is included, so no need to install Gradle
- Build is faster and more efficient than Maven
- All plugin features remain exactly the same

## Troubleshooting

**Build fails with Java version error?**
- The project requires Java 17 or higher
- Check your Java version: `java -version`

**Gradle wrapper not executable?**
- Run: `chmod +x gradlew`

**Clean build not working?**
- Delete the `build/` directory manually
- Run `./gradlew clean build --refresh-dependencies`

---

**Happy Halloween! 🎃👻🍬**
