

# 📌Room Database in Android with Jetpack Compose

Welcome to this hands-on tutorial where we’ll unpack what Room is, why it’s important, and walk you through a **simple yet solid example** to get started.

🎥 **Watch the full video here**: [👉 YouTube Video Link](https://your-video-link-here)

---

## ✅ What You'll Learn

In this project, you'll discover how to:

- Understand what Room Database is and **why** it’s better than just using variables
- Define your **Entity**, **DAO**, and **Database** classes in a clean, Kotlin-friendly way
- Use **ViewModel** and **Flow** for reactive UI updates
- Connect everything with **Jetpack Compose UI**
- BONUS: Use **App Inspection in Android Studio** to view and debug your Room data live!

---

## 🆚 App Comparison

To help you understand Room better, we built and compared **two versions** of the app:

- ❌ **Without Room**: Just stores data in memory — it disappears when the app is closed
- ✅ **With Room**: Uses a local database — your data stays saved even after closing or restarting the app

---

## 🛠️ Project Setup

Starter project link is included in the [📌 video description](https://your-video-link-here).

We use:

- Jetpack Compose
- Room Database
- ViewModel + Flow
- Android Studio App Inspection

### 🔧 Dependencies

```kotlin
// ViewModel Compose
implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")

// Room Database
val roomVersion = "2.7.0"
implementation("androidx.room:room-runtime:$roomVersion")
ksp("androidx.room:room-compiler:$roomVersion")
implementation("androidx.room:room-ktx:$roomVersion")
```

Also apply the KSP plugin in your `build.gradle (Module)` file:

```kotlin
id("com.google.devtools.ksp") version "2.1.10-1.0.31"
```

---

## 📂 File Structure Overview

| File | Purpose |
|------|---------|
| `Task.kt` | The Entity – defines the data model |
| `TaskDao.kt` | The DAO – defines how to interact with the data |
| `TodoDatabase.kt` | The Room Database class – ties everything together |
| `TaskViewModel.kt` | The ViewModel – handles business logic and DB operations |
| `MainActivity.kt` | The UI entry point with Compose setup |

---

## 🧠 Key Concepts Covered

- `@Entity`, `@Dao`, `@Database` annotations
- Using `Flow` with Compose for reactive updates
- Background threading with `Dispatchers.IO`
- Using `viewModels()` in Compose activities
- Clean MVVM architecture for small apps

---

## 🔍 Bonus: App Inspection in Android Studio

At the end of the video, you’ll learn how to:

> 📊 Use **App Inspection Tool** to inspect your Room database tables and view live data inside Android Studio — like a built-in database browser!

---

## 🙌 Wrap-Up

We built a beautiful, beginner-friendly task app that **stores your tasks permanently**, all without needing a backend or internet connection.

Don’t forget to:

✅ Like the video  
✅ Subscribe for more tutorials  
💬 Drop a question in the comment

---

## 📬 Contact & Socials

**📧 Email:** [info@8bitstechnology.com](mailto:info@8bitstechnology.com)  
**🐦 X (Twitter):** [@mawulikazameti](https://x.com/mawulikazameti?t=aOyFIDx7caDogI27hsAXWw&s=09)  
**🎵 TikTok:** [@mawulikazameti](https://www.tiktok.com/@mawulikazameti?_t=ZM-8vfT0qioy7p&_r=1)  
**💼 LinkedIn:** [Mawuli Azameti](https://www.linkedin.com/in/ikmazameti?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app)




