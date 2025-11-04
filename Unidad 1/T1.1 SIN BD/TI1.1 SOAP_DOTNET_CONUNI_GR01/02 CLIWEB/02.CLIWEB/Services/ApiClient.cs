using System;
using System.ServiceModel;
using _02.CLIWEB.Models;
using _02.CLIWEB.ConversionServiceReference;

namespace _02.CLIWEB.Services
{
    public class ApiClient : IDisposable
    {
        private readonly ConversionServiceClient _client;

        public ApiClient()
        {
            var url = "http://localhost:57003/ConversionService.svc"; // Cambia si tu WCF usa otro puerto
            var binding = new BasicHttpBinding { MaxReceivedMessageSize = 65536 };
            var address = new EndpointAddress(url);
            _client = new ConversionServiceClient(binding, address);
        }

        public double Convert(ConversionType t, double v)
        {
            switch (t)
            {
                case ConversionType.KmToMiles: return _client.KilometrosAMillas(v);
                case ConversionType.MToFeet: return _client.MetrosAPies(v);
                case ConversionType.InToCm: return _client.PulgadasACentimetros(v);
                case ConversionType.KgToLb: return _client.KilogramosALibras(v);
                case ConversionType.GToOz: return _client.GramosAOnzas(v);
                case ConversionType.LbToKg: return _client.LibrasAKilogramos(v);
                case ConversionType.CToF: return _client.CelsiusAFahrenheit(v);
                case ConversionType.FToC: return _client.FahrenheitACelsius(v);
                case ConversionType.CToK: return _client.CelsiusAKelvin(v);
                default: throw new ArgumentOutOfRangeException(nameof(t));
            }
        }

        public void Dispose()
        {
            try { _client.Close(); } catch { _client.Abort(); }
        }
    }
}
