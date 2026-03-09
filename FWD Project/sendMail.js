const nodemailer = require("nodemailer");
console.log(process.env.MAIL_USER ? "Mail user loaded ✅" : "Mail user missing ❌");

// transporter (already working)
const transporter = nodemailer.createTransport({
  service: "gmail",
  auth: {
    user: "sriramkanuri04@gmail.com",
    pass: "dqxxerowsavsrkra"
  }
});



// 🔐 PASSWORD RESET EMAIL FUNCTION
async function sendResetMail(email, token) {
  const link = `http://localhost:3000/reset-password/${token}`;

  await transporter.sendMail({
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
}

sendResetMail("sriramkanuri04@gmail.com", "TESTTOKEN123");

