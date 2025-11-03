using System;
using System.Drawing;
using System.IO;
using System.Windows.Forms;

namespace _02.CLIESC.Views
{
    public class LoginForm : Form
    {
        public TextBox TxtUser { get; private set; }
        public TextBox TxtPass { get; private set; }
        public Button BtnLogin { get; private set; }

        public LoginForm()
        {
            Text = "Login - 02.CLIESC";
            ClientSize = new Size(420, 300);
            StartPosition = FormStartPosition.CenterScreen;
            FormBorderStyle = FormBorderStyle.FixedSingle;
            MaximizeBox = false;

            // ⬇️ Carga fondo desde Views\fondo1.jpg
            BackgroundImage = LoadImageFromViews("fondo1.jpg");
            BackgroundImageLayout = ImageLayout.Stretch;

            var panel = new Panel
            {
                BackColor = Color.FromArgb(140, Color.Black),
                Dock = DockStyle.Fill
            };
            Controls.Add(panel);

            var lblTitle = new Label
            {
                Text = "Inicio de sesión",
                ForeColor = Color.White,
                Font = new Font("Segoe UI", 16, FontStyle.Bold),
                AutoSize = true,
                Location = new Point(120, 30)
            };
            panel.Controls.Add(lblTitle);

            var lblUser = new Label
            {
                Text = "Usuario:",
                ForeColor = Color.White,
                Font = new Font("Segoe UI", 11, FontStyle.Bold),
                AutoSize = true,
                Location = new Point(60, 100)
            };
            panel.Controls.Add(lblUser);

            TxtUser = new TextBox { Location = new Point(150, 100), Width = 200 };
            panel.Controls.Add(TxtUser);

            var lblPass = new Label
            {
                Text = "Contraseña:",
                ForeColor = Color.White,
                Font = new Font("Segoe UI", 11, FontStyle.Bold),
                AutoSize = true,
                Location = new Point(40, 140)
            };
            panel.Controls.Add(lblPass);

            TxtPass = new TextBox { Location = new Point(150, 140), Width = 200, UseSystemPasswordChar = true };
            panel.Controls.Add(TxtPass);

            BtnLogin = new Button
            {
                Text = "Ingresar",
                Location = new Point(150, 190),
                Size = new Size(120, 35)
            };
            panel.Controls.Add(BtnLogin);

            AcceptButton = BtnLogin;
        }

        public void ShowMessage(string msg, bool ok)
        {
            MessageBox.Show(msg, ok ? "OK" : "Error",
                MessageBoxButtons.OK,
                ok ? MessageBoxIcon.Information : MessageBoxIcon.Error);
        }

        // Helper para cargar imágenes desde la carpeta Views del output
        private static Image LoadImageFromViews(string fileName)
        {
            var baseDir = AppDomain.CurrentDomain.BaseDirectory; // ...\bin\Debug\
            var path = Path.Combine(baseDir, "Views", fileName);
            if (File.Exists(path)) return Image.FromFile(path);

            // fallback por si la extensión difiere
            var altJpeg = Path.ChangeExtension(path, ".jpeg");
            if (File.Exists(altJpeg)) return Image.FromFile(altJpeg);

            var altJpg = Path.ChangeExtension(path, ".jpg");
            if (File.Exists(altJpg)) return Image.FromFile(altJpg);

            // si no existe, fondo liso
            return new Bitmap(1, 1);
        }
    }
}
