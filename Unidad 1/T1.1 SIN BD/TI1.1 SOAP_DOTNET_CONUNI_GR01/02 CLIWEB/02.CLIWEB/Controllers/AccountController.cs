using System.Web.Mvc;
using _02.CLIWEB.Models;

namespace _02.CLIWEB.Controllers
{
    public class AccountController : Controller
    {
        private const string USER = "MONSTER";
        private const string PASS = "MONSTER9";

        [HttpGet]
        public ActionResult Login()
        {
            return View();
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Login(string username, string password)
        {
            if (username == USER && password == PASS)
            {
                Session["user"] = new UserSession { Username = username };
                return RedirectToAction("Index", "Conversion");
            }
            ViewBag.Error = "Usuario o contraseña incorrectos";
            return View();
        }

        public ActionResult Logout()
        {
            Session.Clear();
            return RedirectToAction("Login");
        }
    }
}
