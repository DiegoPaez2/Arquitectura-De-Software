using System;
using _02.CLICON.Controllers;
using _02.CLICON.Models;
using _02.CLICON.Services;
using _02.CLICON.Views;

namespace _02.CLICON
{
    internal class Program
    {
        static void Main(string[] args)
        {
            Console.Title = "02.CLICON - Cliente SOAP (MVC)";

            var view = new ConsoleView();
            var session = new UserSession();
            var auth = new AuthController(view, session);

            if (!auth.Login())
            {
                Console.WriteLine("\nPresiona una tecla para salir...");
                Console.ReadKey();
                return;
            }

            var api = new ApiClient();
            var conv = new ConversionController(view, api);

            bool keep = true;
            while (keep)
                keep = conv.MenuLoop(session.Username);

            api.Close();
            Console.WriteLine("\n¡Gracias! Presiona una tecla para cerrar...");
            Console.ReadKey();
        }
    }
}
