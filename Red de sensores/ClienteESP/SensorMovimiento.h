class SensorMovimiento{
    private:
    unsigned int pin;
    public:
    SensorMovimiento(unsigned int pin_):
    pin(pin_){
        pinMode(pin , INPUT);
    }
    bool detectarMovimiento(){
        unsigned int value = digitalRead(pin);
        if(value == HIGH){
            Serial.println("Si");
            return true;
        }else{

          Serial.println("No");
          return false;
        }
    }
};
