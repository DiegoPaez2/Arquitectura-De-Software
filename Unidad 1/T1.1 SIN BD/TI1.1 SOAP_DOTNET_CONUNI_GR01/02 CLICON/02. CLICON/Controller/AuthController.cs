using _02.CLICON.Models;
using _02.CLICON.Views;

namespace _02.CLICON.Controllers
{
    public class AuthController
    {
        // Login “quemado”
        private const string USER = "MONSTER";
        private const string PASS = "MONSTER9";

        private readonly ConsoleView _view;
        private readonly UserSession _session;

        public AuthController(ConsoleView view, UserSession session)
        {
            _view = view; _session = session;
        }

        public bool Login()
        {
            var (u, p) = _view.AskCredentials();
            var ok = (u == USER && p == PASS);
            if (ok) _session.SignIn(u);
            _view.ShowLoginResult(ok);
            return ok;
        }
    }
}
