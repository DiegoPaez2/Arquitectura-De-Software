using _02.CLICON.Models;
using _02.CLICON.Services;
using _02.CLICON.Views;

namespace _02.CLICON.Controllers
{
    public class ConversionController
    {
        private readonly ConsoleView _view;
        private readonly ApiClient _api;

        public ConversionController(ConsoleView view, ApiClient api)
        {
            _view = view; _api = api;
        }

        public bool MenuLoop(string username)
        {
            _view.ShowMenu(username);
            var op = _view.AskOption();
            if (op == "0") return false;

            try
            {
                switch (op)
                {
                    case "1": DoConvert(ConversionType.KmToMiles, "km", "mi", "Ingresa km: "); break;
                    case "2": DoConvert(ConversionType.MToFeet, "m", "ft", "Ingresa m: "); break;
                    case "3": DoConvert(ConversionType.InToCm, "in", "cm", "Ingresa pulgadas: "); break;
                    case "4": DoConvert(ConversionType.KgToLb, "kg", "lb", "Ingresa kg: "); break;
                    case "5": DoConvert(ConversionType.GToOz, "g", "oz", "Ingresa g: "); break;
                    case "6": DoConvert(ConversionType.LbToKg, "lb", "kg", "Ingresa lb: "); break;
                    case "7": DoConvert(ConversionType.CToF, "°C", "°F", "Ingresa °C: "); break;
                    case "8": DoConvert(ConversionType.FToC, "°F", "°C", "Ingresa °F: "); break;
                    case "9": DoConvert(ConversionType.CToK, "°C", "K", "Ingresa °C: "); break;
                    default: _view.ShowError("Opción inválida."); break;
                }
            }
            catch (System.Exception ex)
            {
                _view.ShowError($"Error al invocar el servicio: {ex.Message}");
            }

            return true;
        }

        private void DoConvert(ConversionType type, string from, string to, string prompt)
        {
            double val = _view.AskValue(prompt);
            double outVal = _api.Convert(type, val);
            _view.ShowConversion(new ConversionResult { Input = val, Output = outVal, FromUnit = from, ToUnit = to });
        }
    }
}
