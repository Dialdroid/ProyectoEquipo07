
#include "SensorDistancia.h"
#include "ConexionWiFi.h"
#include "SensorMagnetico.h"
#include "SensorMovimiento.h"
#include "SensorGas2.h"
#include "ConexionUDP.h"

#include <TimeLib.h>
#include <ArduinoJson.h>

#include "soc/rtc.h"
#include "WiFi.h"
//#include <MQTT.h>


//SENSOR DE DISTANCIA  // (echoPin, triggerPin)
SensorDistancia distancia = SensorDistancia(12, 14);

//SENSOR MAGNÉTICO  //  pin de entrada
SensorMagnetico magn = SensorMagnetico(2);

//SENSOR MOVIMIENTO  //  pin de entrada
SensorMovimiento movimiento = SensorMovimiento(17);

//SENSOR PESO  //  (DOUT, CLK)

//SensorPeso peso = SensorPeso(4, 5);

//SENSOR GAS  //  pin de entrada
SensorGas2 gas = SensorGas2(35);

//CREDENCIALES CONEXIÓN WI-FI // (ssid, password)
ConexionWiFi wifi = ConexionWiFi("Grupo7", "123456789");

//Conexión UDP  // PUERTO
ConexionUDP udp = ConexionUDP(1234);

void setup(){
    Serial.begin(115200);
    setTime (17, 45, 0, 22, 10, 2018); //hora minuto segundo dia mes año
    wifi.conectar();
    udp.escuchar(wifi);
}


void loop(){
   StaticJsonBuffer<200> jsonBuffer;                 //tamaño maximo de los datos
   JsonObject& envio = jsonBuffer.createObject();    //creación del objeto "envio"

    //Se crea el array de caracteres que posteriormente
    //se rellenará con los datos a enviar
    char envioTxt[200];

    //Se procesan todos los datos obtenidos de los sensores
    String fecha = String(day()) + "/" + String(month()) + "/" + String(year()) + "  " + String( hour()) + ":" + String(minute()) + ":" + String(second()); 
    String altura = String(distancia.calcularDistancia()) + "cm";
    String gas2= String(gas.leerGas());

    //Se recopilan todos esos datos en un objeto JSON
    envio["Hora"]=fecha;
    envio["Altura"] = altura;
    envio["Movimiento"] = movimiento.detectarMovimiento();
    envio["Gas"]=gas2;

    //Se copia el objeto JSON a un array de carácteres
    envio.printTo(envioTxt);

    //Los datos se envían vía UDP en formato texto
    udp.enviarDatos(envioTxt);

    //Envío de datos a la Raspberry
    if(Serial.available() > 0){
    char command = (char)Serial.read();
    switch(command){
    case 'H':
      Serial.println("Hola Mundo!");
      //udp.enviarDatos("Hola Mundo!");
      break;
    case 'D':
      Serial.print("DISTANCIA: ");
      Serial.println(altura);
      break;
    }
    }
    //Retardo de 1s entre medición y medición
    delay(2000);
}
