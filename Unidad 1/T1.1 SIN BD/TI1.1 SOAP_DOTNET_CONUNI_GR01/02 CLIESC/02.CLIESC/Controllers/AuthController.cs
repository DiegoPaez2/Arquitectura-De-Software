using _02.CLIESC.Models;
using _02.CLIESC.Views;

namespace _02.CLIESC.Controllers
{
    public class AuthController
    {
        private const string USER = "MONSTER";
        private const string PASS = "MONSTER9";

        private readonly LoginForm _view;
        private readonly UserSession _session;

        public AuthController(LoginForm view, UserSession session)
        {
            _view = view; _session = session;

            _view.BtnLogin.Click += (s, e) =>
            {
                var u = _view.TxtUser.Text.Trim();
                var p = _view.TxtPass.Text.Trim();
                var ok = (u == USER && p == PASS);
                if (ok)
                {
                    _session.SignIn(u);
                    _view.ShowMessage("Acceso concedido.", true);
                    _view.Hide();
                    var conv = new ConvertForm(u);
                    var convCtrl = new ConversionController(conv, _session);
                    conv.FormClosed += (s2, e2) => _view.Close(); // cerrar app al cerrar conversor
                    conv.Show();
                }
                else
                {
                    _view.ShowMessage("Usuario o contraseña inválidos.", false);
                }
            };
        }
    }
}
