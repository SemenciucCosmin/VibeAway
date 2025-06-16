# 🌴 VibeAway – Personalized Vacation Recommendations Based on Personality

**VibeAway** is a smart Android travel app that recommends vacation **locations** and **activities** tailored to your **Big Five personality traits**. Built in **just one month**, it blends psychology, AI, and clean Android development practices to create a unique and elegant experience.

Designed with a smooth UX in **Figma** and powered by real-world APIs, VibeAway offers users scientifically grounded and highly personalized travel suggestions.

---

## 🚀 Features

### 🔐 Authentication
- Email & Google Sign-In (Firebase Auth)
- Seamless UI toggle between sign in / sign up
- Full error handling via toasts and inline field messages

### 🧠 Personality Profiling (BFI)
- Manually input Big Five personality scores
- Or complete an interactive in-app BFI questionnaire
- Data securely stored in Firebase Firestore

### 🧭 Vacation Recommendations
- Smart feed of personalized locations & activities
- Category screens for recommended and popular options
- Detail screens with maps, descriptions, and actionable insights

### 🤖 AI-Powered Matching
- **Google Gemini AI** used to **match activity categories** (from personality-based research) with real-world locations and activities via semantic string matching
- Ensures meaningful links between psychology and experiences

### 🌍 Live Data Integration
- **Google Places API** – location & point-of-interest data
- **Amadeus Travel API** – activities and tours in select destinations

### 📚 Research-Based Logic
- Personality-to-activity recommendations based on this [scientific study](https://pmc.ncbi.nlm.nih.gov/articles/PMC10183697/#Sec25)

---

## 🛠 Tech Stack

### Android
- **Jetpack Compose** – Declarative UI
- **Retrofit** – API & networking
- **Koin** – Dependency injection
- **Firebase Auth** – Email & Google sign-in
- **Firebase Firestore** – User data persistence

### Architecture
- MVVM + Clean Architecture Principles
- Modular package structure (`ui/`, `data/`, `domain/`, `model/`, `di/`, `utils/`)
- 3 activity flow: `AuthenticationActivity`, `OnboardingActivity`, `RecommendationActivity`

### Local Data (for prototyping)
- JSON files for:
  - BFI questions
  - Locations & activities
  - Correlation mappings
  - Regression weights

---

## 📊 Resources

- 🧠 Big Five correlation research paper [Link to study](https://pmc.ncbi.nlm.nih.gov/articles/PMC10183697/#Sec25)
- 🗺️ [Google Places API](https://developers.google.com/maps/documentation/places/web-service/overview)
- ✈️ [Amadeus Travel API](https://developers.amadeus.com/self-service/category/destination-experiences/api-doc/tours-and-activities/api-reference)
- 🤖 [Google Gemini AI](https://deepmind.google/technologies/gemini) for category-to-activity semantic matching

---

## 📈 Improvements in Progress

- 🔄 Migrate static JSON data to a cloud backend
- 🌐 Add backend user profile management and server-side auth
- 🧳 Add booking and flight search integration
- 👥 Enable social features and group vacation matching

---

## 🖼 Screenshots

- ✏️ [Figma](https://github.com/SemenciucCosmin/VibeAway/tree/main/figma)
<p float="left">
  <img src="https://github.com/SemenciucCosmin/VibeAway/blob/main/screenshots/vibe_away_sign_in.jpeg" width="180">
  <img src="https://github.com/SemenciucCosmin/VibeAway/blob/main/screenshots/vibe_away_sign_up.jpeg" width="180">
</p>

<p float="left">
  <img src="https://github.com/SemenciucCosmin/VibeAway/blob/main/screenshots/vibe_away_onboarding.jpeg" width="180">
  <img src="https://github.com/SemenciucCosmin/VibeAway/blob/main/screenshots/vibe_away_bfi_manual_input.jpeg" width="180">
  <img src="https://github.com/SemenciucCosmin/VibeAway/blob/main/screenshots/vibe_away_bfi_form.jpeg" width="180">
</p>

<p float="left">
  <img src="https://github.com/SemenciucCosmin/VibeAway/blob/main/screenshots/vibe_away_feed.jpeg" width="180">
  <img src="https://github.com/SemenciucCosmin/VibeAway/blob/main/screenshots/vibe_away_popular_locations.jpeg" width="180">
  <img src="https://github.com/SemenciucCosmin/VibeAway/blob/main/screenshots/vibe_away_settings.jpeg" width="180">
</p>

<p float="left">
  <img src="https://github.com/SemenciucCosmin/VibeAway/blob/main/screenshots/vibe_away_favourite_activities.jpeg" width="180">
  <img src="https://github.com/SemenciucCosmin/VibeAway/blob/main/screenshots/vibe_away_favourite_locations.jpeg" width="180">
</p>

<p float="left">
  <img src="https://github.com/SemenciucCosmin/VibeAway/blob/main/screenshots/vibe_away_activity_details.jpeg" width="180">
  <img src="https://github.com/SemenciucCosmin/VibeAway/blob/main/screenshots/vibe_away_location_details.jpeg" width="180">
</p>

---

