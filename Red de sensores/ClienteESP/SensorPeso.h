class SensorPeso{
  private: 
  unsigned int SCK;
  unsigned int DOUT;

  public:
  SensorPeso(unsigned int SCK_, unsigned int DOUT_):
  SCK(SCK_), DOUT(DOUT_){
    HX711 balanza(DOUT, CLK);
    rtc_clk_cpu_freq_set(RTC_CPU_FREQ_80M);
    balanza.set_scale(24825); // Establecemos la escala
    balanza.tare(20);  //El peso actual es considerado Tara.
    
    }//--constructor

  int leerPeso(){
    double res;
    res= balanza.get_units(20),3
    return res;
  }
  }// --Clase