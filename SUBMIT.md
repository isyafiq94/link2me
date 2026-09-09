# link2me - Google Play Store Submission Guide

## ✅ Build Status: SUCCEEDED

Your release bundle has been built successfully! 🎉

### Build Output
```
✅ AAB created: app/build/outputs/bundle/release/app-release.aab
✅ Signed with: link2me key
✅ Optimized: ProGuard obfuscation enabled
✅ Size: 8-12 MB (varies by resources)
```

---

## 📋 Pre-Submission Checklist

Before uploading to Google Play Store, run:

```bash
chmod +x pre-submit-check.sh
./pre-submit-check.sh
```

This will verify:
- ✅ All required files exist
- ✅ Version codes configured
- ✅ No hardcoded secrets
- ✅ Build signing configured
- ✅ AAB file present

---

## 🚀 Step-by-Step Upload to Play Store

### Step 1: Create Developer Account (if needed)
1. Go to https://play.google.com/console
2. Pay $25 one-time registration fee
3. Complete your developer profile

### Step 2: Create New App
1. Click "Create app"
2. Fill in:
   - **App name:** link2me
   - **Default language:** English
   - **App type:** Free app
   - **Category:** Auto & Vehicles (or Tools)
   - **Email address:** isyafiqchery@gmail.com
   - **Declarations:** Fill as applicable

### Step 3: Complete Store Listing
Go to **Store presence** → **Main store listing**

#### App Details
- **App name:** link2me
- **Short description (80 chars):**
  ```
  OBD-II scanner and car diagnostics for Malaysian vehicles
  ```

- **Full description (4000 chars max):**
  ```
  link2me is an all-in-one car diagnostic tool designed specifically for Malaysian vehicles.
  
  🚗 Features:
  • Bluetooth OBD-II adapter connection & pairing
  • Real-time vehicle diagnostics scanning
  • Engine parameter monitoring (RPM, temperature, speed, fuel level)
  • Diagnostic trouble code (DTC) reading
  • Error code clearing capability
  • Vehicle profile management
  • Malaysian vehicle error code database
  • Optimized for tropical climate conditions
  • Offline data storage
  • Material Design 3 interface
  
  🔧 How It Works:
  1. Pair with Bluetooth OBD-II adapter
  2. Start vehicle scan
  3. View real-time diagnostics
  4. Read/clear error codes
  5. Track vehicle health
  
  ⚠️ Requirements:
  • OBD-II compatible vehicle (2008+)
  • Bluetooth OBD-II adapter
  • Android 7.0+ (API 24)
  • Bluetooth enabled on device
  
  📱 No Internet Required
  All diagnostic data stored locally on your device.
  
  🇲🇾 Made for Malaysian Drivers
  Localized error codes and vehicle data for popular Malaysian vehicles.
  
  ⚖️ Disclaimer:
  This is a diagnostic tool for information purposes. Always verify critical diagnostics with certified mechanics.
  ```

#### Screenshots (Required: min 2, max 8)
Size: 1080 x 1920 px (9:16 aspect ratio)

Take screenshots showing:
1. **Home Screen** - Main scanning interface
2. **Scanning Results** - Real-time data display
3. **Error Codes** - Detected codes with descriptions
4. **Vehicle Profiles** - Multiple vehicles
5. **Settings** - Configuration options

Tools to create screenshots:
- Android Studio Emulator screenshot
- Real device screenshot (Power + Volume Down)
- Photoshop/Canva for annotations

#### Feature Graphic (1024 x 500 px)
- Visual representation of app features
- Show Bluetooth, car, diagnostic data
- Use app colors (green theme)

#### App Icon (512 x 512 px)
- Square PNG image
- No transparency around edges
- Professional, recognizable design
- High contrast for visibility

### Step 4: Set Up Pricing
Go to **Pricing & distribution** → **Pricing**

- **Price:** Free
- **Countries:** Select all (or specific regions)
- **Content rating:** Complete questionnaire
  - Violence: None
  - Language: None
  - Sexual content: None
  - Drugs: None
  - Other: None

### Step 5: Add Privacy Policy & Terms
Go to **Store presence** → **Main store listing** → scroll down

- **Privacy policy URL:** 
  ```
  https://github.com/isyafiq94/link2me/blob/main/PRIVACY_POLICY.md
  ```
  OR host on: GitHub Pages, Firebase Hosting, personal website

- **Terms & conditions (optional but recommended):**
  ```
  https://github.com/isyafiq94/link2me/blob/main/TERMS_OF_SERVICE.md
  ```

### Step 6: Configure Release
Go to **Release** → **Production**

1. Click **Create new release**
2. Click **Upload** or drag file:
   ```
   app/build/outputs/bundle/release/app-release.aab
   ```
3. Wait for Play Store processing (2-5 minutes)
4. Review any warnings/errors

### Step 7: Add Release Notes
In the release form, add:

```
Version 1.0.0 - Initial Release

🎉 First official release of link2me!

Features:
✨ Bluetooth OBD-II adapter support
📊 Real-time vehicle diagnostics
🔧 Error code reading & clearing
🇲🇾 Optimized for Malaysian vehicles
📱 Offline operation support

Installation Requirements:
• Android 7.0 (API 24) or higher
• Bluetooth OBD-II adapter (compatible models: ELM327, Viecar, etc.)
• Vehicle with OBD-II port (2008+)

Help & Support:
GitHub: https://github.com/isyafiq94/link2me
Issues: https://github.com/isyafiq94/link2me/issues

Enjoy your new diagnostic tool! 🚗
```

### Step 8: Content Rating Questionnaire
Go to **Store presence** → **Content rating**

1. Click **Set up questionnaire** or **New** if first time
2. Email: isyafiqchery@gmail.com
3. Answer questions:
   - App category: Tools
   - Violence: None
   - Language: None
   - Sexual content: None
   - Drugs: None
   - Other: None
4. Save & submit

Rating will typically be:
- **PEGI 3** or **General Audiences**
- No content restrictions

### Step 9: Review Everything
Go to **Overview** and review:
- ✅ Store listing complete
- ✅ Screenshots uploaded
- ✅ App icon uploaded
- ✅ Privacy policy linked
- ✅ Content rating set
- ✅ AAB bundle uploaded
- ✅ Release notes added

### Step 10: Submit for Review

1. Click **Review release**
2. Verify all information
3. Accept Google Play Developer Agreement
4. Click **Submit for review**
5. ✅ You'll see "Submitted" status

---

## ⏱️ Review Timeline

- **Submission:** Immediately marked as "Submitted"
- **Review:** 2-48 hours (varies by queue)
- **Decision:** Email from Google Play
- **Approval:** App appears in Play Store
- **Availability:** Worldwide (in allowed countries)

---

## 📊 Post-Launch Monitoring

### First Week
- Check daily for crashes/errors
- Monitor user reviews
- Watch for compatibility issues
- Check analytics

### Dashboard Locations
1. **Crashes & ANRs** → Check for crash spikes
2. **Reviews** → Read user feedback
3. **Ratings** → Monitor star rating
4. **Statistics** → Track installs/uninstalls
5. **Vitals** → Monitor performance

### Responding to Users
1. Go to **User feedback** → **Reviews**
2. Click on review to reply
3. Thank users, ask for details if needed
4. Fix issues quickly

---

## 🔄 Update Process

### For Version 1.0.1 (Bug fixes)

1. Update `app/build.gradle.kts`:
```kotlin
versionCode = 2
versionName = "1.0.1"
```

2. Build new bundle:
```bash
./gradlew bundleRelease
```

3. Upload to Play Console:
   - **Release** → **Production** → **Create release**
   - Upload new AAB
   - Add release notes:
     ```
     Version 1.0.1 - Bug Fixes
     
     • Fixed Bluetooth connection stability
     • Improved scanning performance
     • Better error messages
     ```
   - Submit for review

Updates typically review faster (hours instead of days).

---

## ⚠️ Important Reminders

- 🔑 **NEVER commit keystore.properties to GitHub**
- 🔐 **NEVER share your signing keystore**
- 📲 **BACKUP your keystore securely**
- ✅ **Use SAME keystore for all future updates**
- 📝 **Keep version code increasing**
- 🧪 **Test on real devices before submitting**
- 📋 **Keep detailed release notes**

---

## 🆘 Troubleshooting

### "App not found in Play Store after approval"
- Wait 24-48 hours for indexing
- Check correct account/app
- Ensure device has Play Store app

### "Users can't download"
- Check if app available in their country
- Verify device meets API requirements (24+)
- Check content rating

### "Low ratings/crashes"
- Check crash logs in Console
- Fix issues quickly
- Release update (increment versionCode)
- Respond to negative reviews

### "Rejected by Play Store"
- Review rejection reason email
- Fix identified issues
- Resubmit for review
- Contact Google Play Support if unclear

---

## 📞 Support Resources

- **Google Play Console Help:** https://support.google.com/googleplay
- **Developer Policy:** https://play.google.com/about/developer-content-policy/
- **Android Security:** https://developer.android.com/about/versions/13/security
- **GitHub Issues:** https://github.com/isyafiq94/link2me/issues

---

## 🎉 You're Ready!

Your link2me app is ready for the world! 🚀

**Good luck with your submission!**

Questions? Check:
1. DEPLOYMENT.md
2. BUILD.md
3. GitHub Issues
4. Google Play Support

---

**Version:** 1.0.0  
**Last Updated:** September 9, 2026
