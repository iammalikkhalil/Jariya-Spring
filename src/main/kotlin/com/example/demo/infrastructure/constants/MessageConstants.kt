package com.example.demo.infrastructure.constants


object MessageConstants {

    // -------------------------------------------------------------------------
    // GENERAL
    // -------------------------------------------------------------------------
    object General {
        const val SUCCESS = "Request completed successfully."
        const val FAILURE = "Something went wrong. Please try again later."
        const val INVALID_INPUT = "Invalid input. Please review and try again."
        const val INVALID_REQUEST_FORMAT = "The request format is invalid."
        const val UNEXPECTED_ERROR = "An unexpected error occurred. Please try again later."
        const val ACCESS_DENIED = "You are not authorized to perform this action."
        const val RESOURCE_NOT_FOUND = "The requested resource was not found."
        const val OPERATION_FAILED = "The operation could not be completed."
        const val SERVICE_UNAVAILABLE = "The service is temporarily unavailable."
    }

    // -------------------------------------------------------------------------
    // AUTHENTICATION & SECURITY
    // -------------------------------------------------------------------------
    object Auth {
        const val LOGIN_SUCCESS = "Login successful."
        const val LOGOUT_SUCCESS = "Logged out successfully."
        const val INVALID_CREDENTIALS = "The provided credentials are incorrect."
        const val ACCOUNT_NOT_VERIFIED = "Your account has not been verified yet."
        const val ACCOUNT_DISABLED = "Your account is currently disabled."
        const val TOKEN_EXPIRED = "Your session has expired. Please log in again."
        const val TOKEN_INVALID = "Invalid authentication token."
        const val PERMISSION_DENIED = "You do not have permission to access this resource."
        const val GOOGLE_REGISTER_SUCCESS = "Google account registered successfully."
        const val INVALID_GOOGLE_TOKEN = "Invalid Google token."
        const val EMAIL_NOT_FOUND = "Email not found in Google account."
        const val ACCOUNT_EXISTS_WITH_PROVIDER = "Account already exists with another provider:"
        const val MISSING_GOOGLE_CLIENT_ID = "Google Client ID is not configured on the server."
    }

    // -------------------------------------------------------------------------
    // USER MANAGEMENT
    // -------------------------------------------------------------------------
    object User {
        const val USER_NOT_FOUND = "User not found."
        const val USER_ALREADY_EXISTS = "An account with this email already exists."
        const val REGISTRATION_SUCCESS = "Account created successfully."
        const val PROFILE_UPDATED = "Profile updated successfully."
        const val PROFILE_NOT_FOUND = "Profile not found."
        const val REGISTRATION_FAILED = "User Registration Failed!"
    }

    // -------------------------------------------------------------------------
    // OTP / VERIFICATION
    // -------------------------------------------------------------------------
    object Otp {
        const val SENT = "A verification code has been sent to your email."
        const val RESENT = "A new verification code has been sent."
        const val INVALID = "The verification code is invalid."
        const val EXPIRED = "The verification code has expired."
        const val VERIFIED = "Verification completed successfully."
        const val ATTEMPTS_EXCEEDED = "Too many incorrect attempts. Please request a new code."
    }

    // -------------------------------------------------------------------------
    // PASSWORD MANAGEMENT
    // -------------------------------------------------------------------------
    object Password {
        const val RESET_SUCCESS = "Your password has been reset successfully."
        const val RESET_FAILED = "Unable to reset your password."
        const val CHANGED = "Your password has been changed successfully."
        const val INCORRECT = "The current password is incorrect."
    }

    // -------------------------------------------------------------------------
    // DATABASE & DATA OPERATIONS
    // -------------------------------------------------------------------------
    object Data {
        const val CONFLICT = "This record already exists."
        const val NOT_FOUND = "Requested data could not be found."
        const val SAVED = "Data saved successfully."
        const val UPDATED = "Data updated successfully."
        const val DELETED = "Data deleted successfully."
        const val ISSUE = "There was a problem processing your request."
        const val MISSING_FIELD = "Some required information is missing."
        const val FOREIGN_KEY_ERROR = "Invalid reference detected."
    }

    // -------------------------------------------------------------------------
    // EMAIL & COMMUNICATION
    // -------------------------------------------------------------------------
    object Email {
        const val SENT = "Email sent successfully."
        const val FAILED = "Unable to send the email. Please try again later."
        const val ALREADY_REGISTERED = "This email address is already registered."
        const val NOT_REGISTERED = "No account found with this email."
    }

    // -------------------------------------------------------------------------
    // REFERRAL / INVITES
    // -------------------------------------------------------------------------
    object Referral {
        const val INVALID = "The referral code is invalid or expired."
        const val SUCCESS = "Referral applied successfully."
        const val DUPLICATE = "You have already used a referral code."
    }

    // -------------------------------------------------------------------------
    // SYSTEM / ADMIN
    // -------------------------------------------------------------------------
    object System {
        const val ACTION_NOT_ALLOWED = "This action is not allowed."
        const val INTERNAL_ERROR = "Internal Error."
        const val FEATURE_UNAVAILABLE = "This feature is not available at the moment."
        const val MAINTENANCE_MODE = "The system is under maintenance. Please try again later."
    }
}
