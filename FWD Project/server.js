const express = require("express");
const bodyParser = require("body-parser");
const crypto = require("crypto");
const cors = require("cors");
const nodemailer = require("nodemailer");

const app = express();
app.use(cors());
app.use(bodyParser.json());

// 🔔 Request logger (for debugging)
app.use((req, res, next) => {
  console.log("➡️", req.method, req.url);
  next();
});

// 📧 Gmail transporter
const transporter = nodemailer.createTransport({
  service: "gmail",
  auth: {
    user: "sriramkanuri04@gmail.com",
    pass: "dqxxerowsavsrkra"
  }
});

// 🔐 Reset route
app.post("/forgot-password", async (req, res) => {
  console.log("📩 Forgot password triggered");

  const { email } = req.body;

  if (!email) {
    return res.status(400).json({ message: "Email required" });
  }

  const token = crypto.randomBytes(20).toString("hex");

  const link = `http://127.0.0.1:5501/HTML/forgotpassword.html?token=${token}`;

console.log("Generated reset link:", link);

  try {
    const info = await transporter.sendMail({
      from: "sriramkanuri04@gmail.com",
      to: email,
      subject: "Reset Your Password",
      html: `
        <h2>Password Reset</h2>
        <p>Click below to reset your password:</p>
        <a href="${link}">Reset Password</a>
        <p>Valid for 15 minutes</p>
      `
    });

    console.log("📧 Mail sent:", info.response);

    res.json({ message: "Reset email sent successfully" });

  } catch (err) {
    console.log("❌ Mail error:", err);
    res.status(500).json({ message: "Email failed" });
  }
});

// ▶ Start server
app.listen(3000, () => {
  console.log("🚀 Backend running on http://127.0.0.1:3000");
});

