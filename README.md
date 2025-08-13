# PlaySongHymBook 🎵

A modern, bilingual (English/French) hymn book Android application with built-in audio player, featuring a clean Material 3 design and offline-first architecture.

## 📱 Features

### Core Functionality
- **Bilingual Support**: Complete English and French localization
- **Audio Playback**: Built-in ExoPlayer with play/pause/seek controls
- **Quick Navigation**: Number grid (1-400) for instant hymn access
- **Swipe Navigation**: Horizontal swipe between hymns
- **Search**: Full-text search across titles, numbers, and lyrics
- **Favorites**: Bookmark and manage favorite hymns
- **Recent Hymns**: Quick access to last 3 viewed hymns

### User Experience
- **Offline-First**: All hymn data stored locally for instant access
- **Material 3 Design**: Modern, accessible UI with dynamic theming
- **Font Scaling**: Adjustable text size (S/M/L/XL) for better readability
- **Theme Options**: System/Light/Dark theme support
- **Accessibility**: TalkBack support and proper contrast ratios

## 🏗️ Architecture

### Tech Stack
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM + Repository Pattern
- **Database**: Room with FTS (Full-Text Search)
- **Audio**: ExoPlayer with caching
- **Preferences**: DataStore
- **Dependency Injection**: Hilt
- **Image Loading**: Coil
- **Navigation**: Navigation Compose

### Project Structure
```
com.kottland.playsonghymbook/
├── app/                    # Application module
├── core/
│   ├── data/              # Database, repositories, preferences
│   ├── domain/            # Models and use cases
│   └── ui/                # Theme, components, navigation
└── feature/
    ├── home/              # Home screen with grid and recents
    ├── list/              # All hymns list with search
    ├── details/           # Hymn details with player
    ├── favorites/         # Favorites management
    └── settings/          # App settings and preferences
```

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog | 2023.1.1 or later
- JDK 17
- Android SDK 35
- Minimum Android 7.0 (API 24)

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/PlaySongHymBook.git
   cd PlaySongHymBook
   ```

2. Open the project in Android Studio

3. Sync the project with Gradle files

4. Run the app on an emulator or physical device

### Building
```bash
# Debug build
./gradlew assembleDebug

# Release build
./gradlew assembleRelease

# Run tests
./gradlew test
./gradlew connectedAndroidTest
```

## 📊 Data Model

### Hymn Entity
```kotlin
data class Hymn(
    val id: Long,
    val number: Int,
    val titleEn: String,
    val titleFr: String,
    val lyricsEn: String,
    val lyricsFr: String,
    val audioUrlEn: String?,
    val audioUrlFr: String?,
    val lastViewedAt: Long?,
    val isFavorite: Boolean = false
)
```

### Sample Data
The app includes 5 sample hymns for testing:
1. Amazing Grace / Grâce Étonnante
2. Holy, Holy, Holy / Saint, Saint, Saint
3. How Great Thou Art / Ô Dieu Que Tu Es Grand
4. It Is Well / Tout Est Bien
5. Great Is Thy Faithfulness / Grande Est Ta Fidélité

## 🎨 Design System

### Material 3 Implementation
- **Color Scheme**: Vintage-inspired palette with warm tones
- **Typography**: Optimized for hymn readability
- **Components**: Custom cards, buttons, and navigation elements
- **Responsive**: Adapts to different screen sizes and orientations

### Accessibility
- TalkBack screen reader support
- High contrast color ratios (WCAG AA compliant)
- Large touch targets (minimum 44dp)
- Dynamic font scaling

## 🔧 Configuration

### Settings Options
- **Language**: English/Français
- **Theme**: System/Light/Dark
- **Font Scale**: Small/Medium/Large/Extra Large
- **Audio**: Autoplay on open, Remember playback position
- **Data**: Clear recents, Export/Import favorites

## 🧪 Testing

### Test Coverage
- Unit tests for ViewModels and Repositories
- UI tests with Compose Testing
- Integration tests for database operations
- Accessibility tests

### Running Tests
```bash
# Unit tests
./gradlew test

# Instrumentation tests
./gradlew connectedAndroidTest

# UI tests
./gradlew connectedDebugAndroidTest
```

## 📈 Performance

### Optimization Features
- Lazy loading for large lists
- Image caching with Coil
- Audio caching with ExoPlayer
- Database indexing for fast search
- Efficient Compose recomposition

### Benchmarks
- Hymn opening: < 100ms
- Search results: < 200ms
- Smooth 60fps scrolling

## 🌍 Localization

### Supported Languages
- English (en)
- Français (fr)

### Adding New Languages
1. Create new string resources in `res/values-{locale}/`
2. Add locale to `PreferencesManager`
3. Update language selection in Settings
4. Test RTL layout if applicable

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Code Style
- Follow Kotlin coding conventions
- Use ktlint for formatting
- Write meaningful commit messages
- Include tests for new features

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- Material Design team for the excellent design system
- Jetpack Compose team for the modern UI toolkit
- ExoPlayer team for robust audio playback
- The open-source community for inspiration and libraries

## 📞 Support

For support, please open an issue on GitHub or contact the development team.

---

**Built with ❤️ using Jetpack Compose and Material 3**