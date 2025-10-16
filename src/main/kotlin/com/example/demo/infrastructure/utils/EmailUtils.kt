package com.example.demo.infrastructure.utils


import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Component
import jakarta.mail.internet.InternetAddress
import org.springframework.beans.factory.annotation.Value

@Component
class EmailUtils(
    private val mailSender: JavaMailSender
) {

    @Value("\${spring.mail.username}")
    private lateinit var senderEmail: String

    /**
     * Send a generic email
     */
    fun sendEmail(to: String, subject: String, content: String, isHtml: Boolean = false): Boolean {
        return try {
            val mimeMessage = mailSender.createMimeMessage()
            val helper = MimeMessageHelper(mimeMessage, true, "UTF-8")

            helper.setFrom(InternetAddress(senderEmail, "Jariya Support"))
            helper.setTo(to)
            helper.setSubject(subject)
            helper.setText(content, isHtml)

            mailSender.send(mimeMessage)
            println("✅ Email sent successfully to $to")
            true
        } catch (e: Exception) {
            println("❌ Failed to send email: ${e.message}")
            e.printStackTrace()
            false
        }
    }

    /**
     * Send plain text OTP
     */
    fun sendOtpEmailPlain(to: String, otp: String): Boolean {
        val subject = "Your OTP Code"
        val body = """
            Hello,
            
            Your One-Time Password (OTP) is: $otp

            It will expire in 15 minutes. If you did not request this, please ignore it.

            – Jariya Team
        """.trimIndent()

        return sendEmail(to, subject, body, isHtml = false)
    }

    /**
     * Send HTML styled OTP email
     */
    fun sendOtpEmail(to: String, otp: String): Boolean {
        val subject = "Verify Your Email - OTP Inside"
        val htmlBody = """
            <html>
            <body style="margin:0; padding:0; font-family:Arial, sans-serif; background-color:#f4f4f4;">
                <table align="center" cellpadding="0" cellspacing="0" width="100%" style="padding:20px 0;">
                    <tr>
                        <td align="center">
                            <table width="100%" style="max-width:600px; background:#ffffff; padding:30px; border-radius:10px; box-shadow:0 4px 15px rgba(0,0,0,0.08);">
                                <tr>
                                    <td align="center" style="padding-bottom:20px;">
                                        <img src="https://api.jariya.net/.well-known/120.png" alt="Jariya Logo" width="120" style="display:block; margin:0 auto 10px;" />
                                        <h2 style="margin:10px 0 0; color:#222; font-size:22px; font-weight:600;">Jariya</h2>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="font-size:16px; color:#333333; padding-bottom:20px; line-height:1.6;">
                                        <p style="margin:0;">Hello,</p>
                                        <p style="margin:8px 0 0;">Please use the following OTP to verify your email:</p>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" style="padding:20px 0;">
                                        <div style="font-size:32px; letter-spacing:8px; font-weight:bold; color:#2c3e50; background:#f9fafc; padding:18px 36px; border:2px dashed #2980b9; border-radius:10px; display:inline-block;">
                                            $otp
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="font-size:14px; color:#555555; padding-top:20px; line-height:1.5;">
                                        <p style="margin:0;">⏳ This code will expire in <strong>15 minutes</strong>.</p>
                                        <p style="margin:5px 0 0;">If you didn’t request this, you can safely ignore it.</p>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" style="font-size:12px; color:#999999; padding-top:30px; line-height:1.4;">
                                        <p style="margin:0;">You’re receiving this because you signed up on <strong>jariya.app</strong></p>
                                        <p style="margin:5px 0 0;">Need help? Contact us at <a href="mailto:support@jariya.net" style="color:#2980b9; text-decoration:none;">support@jariya.net</a></p>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </body>
            </html>
        """.trimIndent()

        return sendEmail(to, subject, htmlBody, isHtml = true)
    }
}
