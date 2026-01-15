# 📘 Logging Instructions & Standards (with Emojis)

This document defines the **official logging standard** for the project.  
Follow this **every time** you add or review logs. No re‑explanation needed.

---

## 🎯 Purpose of Logs
Logs must answer **quickly**:

1. **What happened?**
2. **Why did it happen?**
3. **Where did it fail (if it failed)?**

If logs do not clearly answer these → logs are incorrect.

---

## 🧱 Standard Log Format

```
[MODULE] EMOJI ACTION → RESULT | key=value
```

### Examples
```
[AUTH] 🔑 🚀 Login attempt → START | email=test@invotick.com
[JWT] 🪪 ✅ Token validation → SUCCESS | userId=12
[SECURITY] 🔐 ❌ FilterChain build → FAILED | reason=BeanMissing
```

---

## 🧠 What MUST Be Logged

### ✅ Always log
- Module / layer name
- Action being performed
- Final result (SUCCESS / FAILED / SKIPPED)
- Reason for failure (if any)
- Minimal identifying data (email, userId, endpoint)

### ❌ Never log
- Passwords
- JWT tokens
- Secrets / API keys
- Full object dumps

---

## 🏷️ Module Naming Convention

Use **fixed uppercase module names**:

```
[SECURITY]
[AUTH]
[JWT]
[CORS]
[API]
[REPO]
[SERVICE]
```

---

## 📦 Emoji Dictionary (FIXED)

### 🔹 Flow & State
| Emoji | Meaning |
|------|--------|
| 🚀 | Start / Init |
| 🔹 | Processing |
| ✅ | Success |
| ❌ | Failure |
| ⏭️ | Skipped |
| ⏹️ | Stopped |

---

### 🔐 Security & Auth
| Emoji | Meaning |
|------|--------|
| 🔐 | Security |
| 🔑 | Authentication |
| 🛡️ | Authorization |
| 🪪 | User identity |
| 🧾 | Credentials |

---

### 🌐 Network / Requests
| Emoji | Meaning |
|------|--------|
| 🌍 | CORS |
| 📥 | Incoming request |
| 📤 | Outgoing response |
| 🔗 | Endpoint |

---

### ⚠️ Log Level Helpers
| Emoji | Level |
|------|------|
| ℹ️ | INFO |
| ⚠️ | WARN |
| 🔴 | ERROR |
| 🐞 | DEBUG |

---

## 🔊 Log Level Rules

### 🟢 INFO
Normal, expected flow
```
[AUTH] 🔑 🔹 Login attempt → START | email=test@invotick.com
```

### 🟡 WARN
Suspicious but allowed
```
[JWT] ⚠️ 🔑 Token missing → REQUEST CONTINUES
```

### 🔴 ERROR
Hard failure, action stopped
```
[AUTH] 🔴 🔑 Authentication → FAILED | reason=UserNotFound | email=a@b.com
```

### 🔵 DEBUG
Deep internal details (non‑production critical)
```
[JWT] 🐞 🔹 Token expiry time=171233123
```

---

## 🧠 Layer‑Wise Logging Rules

### 🔐 Security / Auth
Log:
- Public vs protected endpoint
- Authentication success/failure
- Reason of denial

Never log credentials or tokens.

---

### 🪪 JWT Filter
Log:
- Token present / missing
- Token valid / expired
- User extracted from token

---

### 🧾 Repository
Log:
- Entity found / not found
- Identifier used (id/email)

---

### 🌐 API Layer
Log:
- Request start
- Request end
- HTTP status

---

## 🚀 Quick Debug Rule

You must be able to answer **only using logs**:

- Which request failed?
- For which user?
- In which module?
- For what reason?

If not → logs need fixing.

---

## 🧩 Recommended Constants (Optional)

```kotlin
const val SECURITY = "[SECURITY]"
const val AUTH = "[AUTH]"
const val JWT = "[JWT]"
const val CORS = "[CORS]"
```

Usage:
```kotlin
log.info("$AUTH 🔑 🚀 Login attempt → START | email={}", email)
```

---

## ✅ Final Rule (Non‑Negotiable)

> Same meaning → same emoji → same format → every time.

If consistency breaks, debugging speed breaks.

