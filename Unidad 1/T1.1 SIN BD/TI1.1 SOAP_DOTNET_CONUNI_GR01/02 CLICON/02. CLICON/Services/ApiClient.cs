using _02.CLICON.ConversionServiceReference;   // <- tu referencia WCF
using _02.CLICON.Models;

namespace _02.CLICON.Services
{
    public class ApiClient
    {
        private readonly ConversionServiceClient _client = new ConversionServiceClient();

        public double Convert(ConversionType type, double value)
        {
            switch (type)
            {
                case ConversionType.KmToMiles: return _client.KilometrosAMillas(value);
                case ConversionType.MToFeet: return _client.MetrosAPies(value);
                case ConversionType.InToCm: return _client.PulgadasACentimetros(value);
                case ConversionType.KgToLb: return _client.KilogramosALibras(value);
                case ConversionType.GToOz: return _client.GramosAOnzas(value);
                case ConversionType.LbToKg: return _client.LibrasAKilogramos(value);
                case ConversionType.CToF: return _client.CelsiusAFahrenheit(value);
                case ConversionType.FToC: return _client.FahrenheitACelsius(value);
                case ConversionType.CToK: return _client.CelsiusAKelvin(value);
                default: throw new System.ArgumentOutOfRangeException(nameof(type));
            }
        }

        public void Close()
        {
            try { _client.Close(); }
            catch { _client.Abort(); }
        }
    }
}
