 #include "HX711.h"

class SensorPeso{
  private: 
  unsigned int CLK;
  unsigned int DOUT;

  public:
  SensorPeso(unsigned int CLK_, unsigned int DOUT_):
  CLK(CLK_), DOUT(DOUT_){
    HX711 balanza(DOUT, CLK);
    
    }//--constructor

  int leerPeso(){
    
    double res;
    res= balanza.get_units(20),3
    return res;
  }
  };// --Clase
