# PlaySongsHymBook Development Tasksheet

## Project Overview
**App Name**: PlaySongsHymBook  
**Platform**: Android  
**Tech Stack**: Kotlin, Jetpack Compose, MVVM, Room, Retrofit, Coil  
**Target SDK**: 35 (Android 14)  
**Min SDK**: 24 (Android 7.0)  
**Status**: ✅ **CORE IMPLEMENTATION COMPLETE**

## 🎯 Current Status Summary

### ✅ Completed Features
- **Project Setup**: Android project with all dependencies configured
- **Architecture**: MVVM + Repository pattern with Hilt DI
- **Database**: Room database with Hymn entity and DAO
- **UI Framework**: Jetpack Compose with Material 3 theming
- **Navigation**: Navigation Compose setup
- **Core Screens**: Home, All Hymns, Hymn Details, Favorites, Settings
- **Audio Player**: ExoPlayer integration with basic controls
- **Localization**: English/French language support
- **Theme System**: Light/Dark/System theme with font scaling
- **Data Storage**: DataStore for user preferences
- **Sample Data**: 5 hymns with bilingual content
- **Build System**: Gradle configuration with successful compilation

### 🚧 In Progress
- Performance optimization
- Comprehensive testing
- UI polish and accessibility improvements

### 📋 Next Steps
- Add more hymn data (expand from 5 to full collection)
- Implement advanced search features
- Add audio caching
- Performance benchmarking
- Play Store preparation  

PlaySongsHymBook – Senior Android Development Task Sheet

Project Overview

App Name: PlaySongsHymBookGoal: A bilingual (EN/FR) hymnbook with built‑in audio player, quick number grid (1–400), swipeable hymn details, favorites, search, and a bottom navigation home.Platform: Android (Phone + small tablets)Tech Stack: Kotlin, Jetpack Compose, MVVM + Repository, Room, ExoPlayer, DataStore, Hilt (or Koin), Coil, Navigation ComposeTarget SDK: 35Min SDK: 24Build Tools: Gradle KTS + Version CatalogCI/CD: GitHub Actions (assemble, lint, unit/UI tests, Play upload optional)

Data Source Description

Songs will be loaded into the local database from a hymns_raw.json file.

For initial testing/sample data, create a list of 5 popular old hymns.

Raw assets folder should be created to contain hymn songs, labeled as 1en.mp3, 1fr.mp3 for hymn number 1 (English and French versions respectively), and similarly for other hymns.

JSON structure example:

[
  {
    "number": 1,
    "titleEn": "Amazing Grace",
    "titleFr": "Grâce Étonnante",
    "lyricsEn": "Amazing grace! How sweet the sound...",
    "lyricsFr": "Grâce étonnante! Quel doux son...",
    "audioEn": "1en.mp3",
    "audioFr": "1fr.mp3"
  },
  {
    "number": 2,
    "titleEn": "Holy, Holy, Holy",
    "titleFr": "Saint, Saint, Saint",
    "lyricsEn": "Holy, holy, holy! Lord God Almighty...",
    "lyricsFr": "Saint, saint, saint! Seigneur Tout-Puissant...",
    "audioEn": "2en.mp3",
    "audioFr": "2fr.mp3"
  }
  // ...add 3 more hymns for sample data
]

Functional Requirements

Home

App bar with app name and language toggle (EN/FR)

Search field (title, number, full‑text lyrics)

Most recent 3 hymns (by last viewed)

Grid of numbers 1–400 (10 × 50). Tap → open Details for that hymn

Bottom Navigation: Home · All Hymns · Favorites · Settings

All Hymns

List of all hymns: number, localized title

Sticky fast scroller / index by hundreds (1–100, …)

Search section with debounce and query suggestions

Favorites

List/grid of favorited hymns

Add/Remove favorite from list and details

Hymn Details

Shows number, localized title, localized lyrics

Swipe left/right to go to previous/next hymn number

Play/Pause/Seek hymn audio (inline player)

Bookmark/Favorite toggle

Share hymn (title + number + optional verse excerpt)

Settings

Language: English / Français

Theme: System / Light / Dark

Font scale for lyrics (S · M · L · XL)

Player options: Autoplay on open (on/off), Remember playback position (on/off)

Data: Clear recents, Export/Import favorites (JSON)

Non‑Functional Requirements

Offline‑first: Hymn metadata & lyrics bundled (assets/JSON/Room prepack); audio may be local (assets) or remote with caching

Performance: Open any hymn < 100ms; scrolling 60fps on mid‑range devices

Accessibility: TalkBack labels, proper contrast, large text support

Localization: 100% strings/resources localized EN/FR; RTL‑safe layout

Testability: Clear boundaries (Domain/Data/Presentation), DI for fakes

Data Model & Storage

Entities (Room)

Hymn(
  id: Long,
  number: Int,
  titleEn: String,
  titleFr: String,
  lyricsEn: String,
  lyricsFr: String,
  audioUrlEn: String?, // or local asset key
  audioUrlFr: String?, // or local asset key
  lastViewedAt: Long?,
  isFavorite: Boolean = false
)

Optional FTS table: HymnFts(content=lyricsEn|lyricsFr|titleEn|titleFr) for fast full-text search.

DAOs

HymnDao: getByNumber, search(query, locale), recent(limit), favorites(), updateFavorite(), updateLastViewed()

HymnFtsDao: full-text search with ranking

Preferences (DataStore)

language (en|fr), theme, fontScale, autoplay, rememberPosition

Audio

ExoPlayer with CacheDataSource (SimpleCache) for offline replay

Support local raw/asset files fallback when remote unavailable

Architecture

MVVM + Repository with UseCases (optional for complex logic)

Presentation (Compose Screens & ViewModels)
   ↕ StateFlow/UiState
Domain (UseCases, Models)
   ↕ Repository
Data (Room, DataStore, Asset/Remote Audio, ExoPlayer)

Modules (nice-to-have split)

core-ui, core-data, feature-home, feature-list, feature-details, feature-favorites, feature-settings, app

Navigation

Routes: home, list, favorites, settings, details/{number}

Deep links: playsongs://hymn/{number}

Back stack behavior: Details preserves player position (if enabled)

UI/UX & Design System

Material 3

Typography tuned for hymn readability; dynamic type

Card-based recent hymns; large numeric grid buttons (min 44dp)

Player mini-bar collapsible on Details; full controls on expand

Haptics: light impact on grid tap & favorite toggle

Work Breakdown Structure (WBS)

0. Foundations



1. Design System & Theme



2. Data Layer



3. Audio Layer



4. Features

Home



All Hymns



Favorites



Details



Settings



5. Localization & Content



6. Quality & Testing

Unit Tests



UI Tests (Compose test)



Instrumentation



7. Performance & Accessibility



8. Release Readiness



9. Nice-to-Have Enhancements



Acceptance Criteria (per feature)

Open by Number: Tapping any grid number opens Details for that hymn within 150ms on a Moto G-class device.Swipe Navigation: Horizontal swipe changes hymn number ±1 with a 200ms transition and preserves scroll position in lyrics.Audio Playback: Play/Pause/Seek works; buffering indicator shown; resumes from saved position if enabled.Search: Queries by number/title/lyrics; returns localized results; debounced 300ms; shows empty state.Favorites: Toggling favorite persists; appears in Favorites screen and Details button reflects state.Localization: Switching EN/FR immediately updates UI strings and displayed hymn content; persisted across restarts.Recents: Last 3 hymns open appear on Home in reverse chronological order.

Data Ingestion Plan

Source format: hymns_raw.json with sample 5 hymns initially

Validator script: Checks number range, missing titles/lyrics, placeholder detection

Seeder: On first run, import into Room; build FTS index

Audio mapping: Map audio_key → local asset file (1en.mp3, 1fr.mp3 etc.)

Technical Decisions

Paging: Not required for 400 items; use LazyColumn/LazyVerticalGrid

Swipe Implementation: Pager from Accompanist or Compose Foundation Pager

Search: Room LIKE + FTS for lyrics relevance; highlight matches in results (optional)

DI: Hilt for ViewModel/Repo/Player

Error Handling: Typed domain errors → UI states; retry affordances

State Management: Single-source UiState via StateFlow; immutable data classes

Caching: ExoPlayer SimpleCache (100–200MB) with LRU eviction

Milestones & Estimates (indicative)

M1 Foundations & Design System – 2 days

M2 Data & Seeding (EN/FR, sample hymns) – 2 days

M3 Home + Grid + Recents – 2 days

M4 All Hymns + Search – 2 days

M5 Details + Swipe + Player – 3 days

M6 Favorites – 1 day

M7 Settings + DataStore – 1 day

M8 Tests, Perf, A11y – 2 days

M9 Release Prep – 1 day

Checklists

Security & Privacy



Telemetry (Optional)



Play Compliance



Example Package Structure

com.playsongs.hymbook
 ├─ app
 ├─ core
 │   ├─ data (db, dao, repo, datastore, audio)
 │   ├─ domain (models, usecases)
 │   └─ ui (theme, components)
 └─ feature
     ├─ home
     ├─ list
     ├─ details
     ├─ favorites
     └─ settings

Future Roadmap

Cloud sync for favorites/settings

Multi-language expansion (locales via remote config)

Verse-level playback markers

In-app purchases for premium audio packs