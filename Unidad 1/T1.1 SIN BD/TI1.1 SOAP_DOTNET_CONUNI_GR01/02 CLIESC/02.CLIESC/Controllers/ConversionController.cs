using System;
using _02.CLIESC.Models;
using _02.CLIESC.Services;
using _02.CLIESC.Views;

namespace _02.CLIESC.Controllers
{
    public class ConversionController
    {
        private readonly ConvertForm _view;
        private readonly UserSession _session;
        private readonly ApiClient _api = new ApiClient();

        public ConversionController(ConvertForm view, UserSession session)
        {
            _view = view; _session = session;

            _view.BtnConvert.Click += (s, e) =>
            {
                var input = _view.TryGetInput();
                if (input == null) return;

                var type = (ConversionType)(_view.CmbType.SelectedIndex + 1);
                try
                {
                    double result = _api.Convert(type, input.Value);
                    string toUnit = GetToUnit(type);
                    _view.ShowResult(result.ToString("F6") + " " + toUnit);
                }
                catch (Exception ex)
                {
                    System.Windows.Forms.MessageBox.Show("Error al invocar el servicio:\n" + ex.Message,
                        "Error", System.Windows.Forms.MessageBoxButtons.OK, System.Windows.Forms.MessageBoxIcon.Error);
                }
            };

            _view.BtnLogout.Click += (s, e) =>
            {
                _session.SignOut();
                _api.Close();
                _view.Close(); // vuelve al LoginForm por el manejador configurado en AuthController
            };
        }

        private static string GetToUnit(ConversionType t)
        {
            switch (t)
            {
                case ConversionType.KmToMiles: return "mi";
                case ConversionType.MToFeet: return "ft";
                case ConversionType.InToCm: return "cm";
                case ConversionType.KgToLb: return "lb";
                case ConversionType.GToOz: return "oz";
                case ConversionType.LbToKg: return "kg";
                case ConversionType.CToF: return "°F";
                case ConversionType.FToC: return "°C";
                case ConversionType.CToK: return "K";
                default: return "";
            }
        }
    }
}
