using System;
using System.Globalization;
using System.Web.Mvc;
using _02.CLIWEB.Models;
using _02.CLIWEB.Services;

namespace _02.CLIWEB.Controllers
{
    public class ConversionController : Controller
    {
        private UserSession Current => Session["user"] as UserSession;

        [HttpGet]
        public ActionResult Index()
        {
            if (Current == null || !Current.IsAuthenticated)
                return RedirectToAction("Login", "Account");

            ViewBag.User = Current.Username;
            return View();
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Index(int type, string value)
        {
            if (Current == null || !Current.IsAuthenticated)
                return RedirectToAction("Login", "Account");

            ViewBag.User = Current.Username;

            if (!double.TryParse(value, NumberStyles.Float, CultureInfo.InvariantCulture, out double v))
            {
                ViewBag.Error = "Ingrese un valor numérico válido.";
                return View();
            }

            var t = (ConversionType)type;
            try
            {
                using (var api = new ApiClient())
                {
                    var res = api.Convert(t, v);
                    ViewBag.Result = res.ToString("F4", CultureInfo.InvariantCulture);
                    ViewBag.ToUnit = GetToUnit(t);
                }
            }
            catch (Exception ex)
            {
                ViewBag.Error = "Error al consumir el servicio: " + ex.Message;
            }

            return View();
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
