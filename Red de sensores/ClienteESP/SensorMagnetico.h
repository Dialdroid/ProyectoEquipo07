class SensorMagnetico{
    unsigned int pin;
    public:
    SensorMagnetico(unsigned int pin_):
      pin(pin_){}
    
    String puertaAbierta(){
        if(digitalRead((*this).pin) == HIGH){

          String abierto="Puerta abierta";
            return abierto;
            delay(10000);
        }else{
          String cerrado="Puerta Cerrada";
          return cerrado;
          delay(10000);
        }
    }
};
