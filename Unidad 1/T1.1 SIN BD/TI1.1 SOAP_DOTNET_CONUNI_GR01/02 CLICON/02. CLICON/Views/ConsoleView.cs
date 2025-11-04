using System;
using _02.CLICON.Models;
using System.Globalization;

namespace _02.CLICON.Views
{
    public class ConsoleView
    {
        public (string user, string pass) AskCredentials()
        {
            Console.Clear();
            Console.WriteLine("=== LOGIN CLIENTE SOAP (MVC) ===");
            Console.Write("Usuario: ");
            var u = (Console.ReadLine() ?? "").Trim();
            Console.Write("Contraseña: ");
            var p = ReadPassword();
            return (u, p);
        }

        public void ShowLoginResult(bool ok)
        {
            Console.WriteLine(ok ? "\n✅ Acceso concedido." : "\n❌ Credenciales incorrectas.");
            System.Threading.Thread.Sleep(800);
        }

        public void ShowMenu(string username)
        {
            Console.Clear();
            Console.WriteLine("Usuario: " + username);
            Console.WriteLine("=== CONVERSOR DE UNIDADES (SOAP WCF) ===");
            Console.WriteLine("1) Kilómetros → Millas");
            Console.WriteLine("2) Metros → Pies");
            Console.WriteLine("3) Pulgadas → Centímetros");
            Console.WriteLine("4) Kilogramos → Libras");
            Console.WriteLine("5) Gramos → Onzas");
            Console.WriteLine("6) Libras → Kilogramos");
            Console.WriteLine("7) Celsius → Fahrenheit");
            Console.WriteLine("8) Fahrenheit → Celsius");
            Console.WriteLine("9) Celsius → Kelvin");
            Console.WriteLine("0) Salir");
        }

        public string AskOption()
        {
            Console.Write("\nElige una opción: ");
            var s = Console.ReadLine();
            return s == null ? "" : s.Trim();
        }

        public double AskValue(string prompt)
        {
            while (true)
            {
                Console.Write(prompt);
                var s = Console.ReadLine();
                double v;
                if (double.TryParse(s, NumberStyles.Float, CultureInfo.InvariantCulture, out v))
                    return v;
                Console.WriteLine("Valor inválido. Intenta de nuevo.");
            }
        }

        public void ShowConversion(ConversionResult r)
        {
            Console.WriteLine("\n" + r.Input + " " + r.FromUnit + " = " + r.Output.ToString("F6", CultureInfo.InvariantCulture) + " " + r.ToUnit);
            Console.WriteLine("\nPresiona una tecla para continuar...");
            Console.ReadKey();
        }

        public void ShowError(string msg)
        {
            Console.WriteLine("\n❌ " + msg);
            Console.WriteLine("\nPresiona una tecla para continuar...");
            Console.ReadKey();
        }

        // Versión compatible con C# 7.3 (sin Index/Range)
        private static string ReadPassword()
        {
            string pass = "";
            ConsoleKeyInfo key;
            do
            {
                key = Console.ReadKey(true);
                if (key.Key == ConsoleKey.Backspace)
                {
                    if (pass.Length > 0)
                    {
                        pass = pass.Substring(0, pass.Length - 1);
                        Console.Write("\b \b");
                    }
                }
                else if (!char.IsControl(key.KeyChar))
                {
                    pass += key.KeyChar;
                    Console.Write("*");
                }
            } while (key.Key != ConsoleKey.Enter);
            return pass;
        }
    }
}
