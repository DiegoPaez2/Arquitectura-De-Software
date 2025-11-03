using System.ServiceModel;

namespace WS_SOAP_DOTNET_GR001.Services
{
    [ServiceContract]
    public interface IConversionService
    {
        [OperationContract] double KilometrosAMillas(double km);
        [OperationContract] double MetrosAPies(double m);
        [OperationContract] double PulgadasACentimetros(double p);

        [OperationContract] double KilogramosALibras(double kg);
        [OperationContract] double GramosAOnzas(double g);
        [OperationContract] double LibrasAKilogramos(double lb);

        [OperationContract] double CelsiusAFahrenheit(double c);
        [OperationContract] double FahrenheitACelsius(double f);
        [OperationContract] double CelsiusAKelvin(double c);
    }
}
