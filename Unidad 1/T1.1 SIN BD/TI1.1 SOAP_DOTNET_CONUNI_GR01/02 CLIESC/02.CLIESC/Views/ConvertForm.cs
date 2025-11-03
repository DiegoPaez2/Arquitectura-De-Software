using System;
using System.Drawing;
using System.Globalization;
using System.IO;
using System.Windows.Forms;

namespace _02.CLIESC.Views
{
    public class ConvertForm : Form
    {
        public ComboBox CmbType { get; private set; }
        public TextBox TxtValue { get; private set; }
        public Button BtnConvert { get; private set; }
        public Label LblResult { get; private set; }
        public Button BtnLogout { get; private set; }

        public ConvertForm(string user)
        {
            Text = "Conversor - 02.CLIESC";
            ClientSize = new Size(600, 380);
            StartPosition = FormStartPosition.CenterScreen;
            FormBorderStyle = FormBorderStyle.FixedSingle;
            MaximizeBox = false;

            // ⬇️ Carga fondo desde Views\fondo2.jpeg
            BackgroundImage = LoadImageFromViews("fondo2.jpeg");
            BackgroundImageLayout = ImageLayout.Stretch;

            var panel = new Panel { BackColor = Color.FromArgb(120, Color.Black), Dock = DockStyle.Fill };
            Controls.Add(panel);

            var lblUser = new Label
            {
                Text = "Usuario: " + user,
                ForeColor = Color.White,
                AutoSize = true,
                Location = new Point(15, 10)
            };
            panel.Controls.Add(lblUser);

            BtnLogout = new Button { Text = "Cerrar sesión", Location = new Point(470, 10), Size = new Size(110, 28) };
            panel.Controls.Add(BtnLogout);

            var lblType = new Label
            {
                Text = "Tipo de conversión:",
                ForeColor = Color.White,
                AutoSize = true,
                Location = new Point(40, 90)
            };
            panel.Controls.Add(lblType);

            CmbType = new ComboBox { Location = new Point(180, 86), Width = 350, DropDownStyle = ComboBoxStyle.DropDownList };
            CmbType.Items.AddRange(new object[]
            {
                "1) Kilómetros → Millas",
                "2) Metros → Pies",
                "3) Pulgadas → Centímetros",
                "4) Kilogramos → Libras",
                "5) Gramos → Onzas",
                "6) Libras → Kilogramos",
                "7) Celsius → Fahrenheit",
                "8) Fahrenheit → Celsius",
                "9) Celsius → Kelvin"
            });
            CmbType.SelectedIndex = 0;
            panel.Controls.Add(CmbType);

            var lblVal = new Label
            {
                Text = "Valor:",
                ForeColor = Color.White,
                AutoSize = true,
                Location = new Point(120, 140)
            };
            panel.Controls.Add(lblVal);

            TxtValue = new TextBox { Location = new Point(180, 136), Width = 200 };
            panel.Controls.Add(TxtValue);

            BtnConvert = new Button { Text = "Convertir", Location = new Point(400, 134), Size = new Size(130, 30) };
            panel.Controls.Add(BtnConvert);

            LblResult = new Label
            {
                Text = "Resultado:",
                ForeColor = Color.White,
                Font = new Font("Segoe UI", 12, FontStyle.Bold),
                AutoSize = true,
                Location = new Point(180, 200)
            };
            panel.Controls.Add(LblResult);
        }

        public double? TryGetInput()
        {
            double v;
            if (double.TryParse(TxtValue.Text.Trim(), NumberStyles.Float, CultureInfo.InvariantCulture, out v))
                return v;

            MessageBox.Show("Ingrese un valor numérico válido.", "Validación",
                MessageBoxButtons.OK, MessageBoxIcon.Warning);
            return null;
        }

        public void ShowResult(string text)
        {
            LblResult.Text = "Resultado: " + text;
        }

        private static Image LoadImageFromViews(string fileName)
        {
            var baseDir = AppDomain.CurrentDomain.BaseDirectory;
            var path = Path.Combine(baseDir, "Views", fileName);
            if (File.Exists(path)) return Image.FromFile(path);

            var altJpeg = Path.ChangeExtension(path, ".jpeg");
            if (File.Exists(altJpeg)) return Image.FromFile(altJpeg);

            var altJpg = Path.ChangeExtension(path, ".jpg");
            if (File.Exists(altJpg)) return Image.FromFile(altJpg);

            return new Bitmap(1, 1);
        }
    }
}
