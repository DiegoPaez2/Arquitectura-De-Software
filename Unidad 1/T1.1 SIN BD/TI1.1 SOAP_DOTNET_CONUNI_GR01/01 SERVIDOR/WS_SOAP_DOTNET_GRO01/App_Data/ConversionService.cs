using System;
using WS_SOAP_DOTNET_GR001.Services;

namespace WS_SOAP_DOTNET_GR001.Services
{
    public class ConversionService : IConversionService
    {
        // Longitud
        public double KilometrosAMillas(double km) => km * 0.621371;
        public double MetrosAPies(double m) => m * 3.28084;
        public double PulgadasACentimetros(double p) => p * 2.54;

        // Masa
        public double KilogramosALibras(double kg) => kg * 2.20462;
        public double GramosAOnzas(double g) => g * 0.035274;
        public double LibrasAKilogramos(double lb) => lb * 0.45359237;

        // Temperatura
        public double CelsiusAFahrenheit(double c) => (c * 9.0 / 5.0) + 32.0;
        public double FahrenheitACelsius(double f) => (f - 32.0) * 5.0 / 9.0;
        public double CelsiusAKelvin(double c) => c + 273.15;
    }
}

