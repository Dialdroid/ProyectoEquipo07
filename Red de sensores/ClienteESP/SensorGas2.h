 const int RL_VALUE = 5;      // Resistencia RL del modulo en Kilo ohms
const int R0 = 10;          // Resistencia R0 del sensor en Kilo ohms
const int READ_SAMPLE_INTERVAL = 100;    // Tiempo entre muestras
const int READ_SAMPLE_TIMES = 5;       // Numero muestras
const float X0 = 200;
const float Y0 = 1.7;
const float X1 = 10000;
const float Y1 = 0.28;
const float punto0[] = { log10(X0), log10(Y0) };
const float punto1[] = { log10(X1), log10(Y1) };
const float scope = (punto1[1] - punto0[1]) / (punto1[0] - punto0[0]);
const float coord = punto0[1] - punto0[0] * scope;
//-------------------
class SensorGas2{
private:
unsigned int MQ_PIN;	// Pin del sensor

public:
SensorGas2(unsigned int MQ_PIN_):
MQ_PIN(MQ_PIN_){
  } // Constructor
//-------------------
unsigned int leerGas(){
 
float rs_med = readMQ(MQ_PIN);      // Obtener la Rs promedio
float concentration = getConcentration(rs_med/R0);   // Obtener la concentraci�n
return concentration;
} // leerGas
//-------------------
float readMQ(int mq_pin)
{
   float rs = 0;
   for (int i = 0;i<READ_SAMPLE_TIMES;i++) {
      rs += getMQResistance(analogRead(mq_pin));
      delay(READ_SAMPLE_INTERVAL);
   }
   return rs / READ_SAMPLE_TIMES;
} // readMQ
//-------------------
float getMQResistance(int raw_adc)
{
   return (((float)RL_VALUE / 1000.0*(1023 - raw_adc) / raw_adc));
} // getMQresistence
//-------------------
float getConcentration(float rs_ro_ratio)
{
   return pow(10, coord + scope * log(rs_ro_ratio));
} // getConcentracion
}; // Clase
