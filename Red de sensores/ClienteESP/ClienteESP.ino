
#include "SensorDistancia.h"
#include "ConexionWiFi.h"
#include "SensorMagnetico.h"
#include "SensorMovimiento.h"
#include "SensorGas2.h"

#include <TimeLib.h>
#include <ArduinoJson.h>

#include "soc/rtc.h"
#include "WiFi.h"
#include <MQTT.h>


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
//ConexionUDP udp = ConexionUDP(1234);

const char broker[] = "iot.eclipse.org";

WiFiClient net;
MQTTClient client;

void connect() {
 Serial.print("checking wifi...");
 while (WiFi.status() != WL_CONNECTED) {
 Serial.print(".");
 delay(1000);
 }
 Serial.print("\nconnecting...");
 while (!client.connect("Test134568789", "try", "try")) {
 Serial.print(".");
 delay(1000);
 }
 Serial.println("\nconnected!");
 client.subscribe("grupo7/practica/#");
 
}

void messageReceived(String &topic, String &payload) {
 Serial.println("incoming: " + topic + " - " + payload);
}

void setup(){
    Serial.begin(115200);
    setTime (17, 45, 0, 22, 10, 2018); //hora minuto segundo dia mes año
    wifi.conectar();
    client.begin(broker, net);
    client.onMessage(messageReceived);
    connect();   
}


void loop(){

    //Se procesan todos los datos obtenidos de los sensores
    String fecha = String(day()) + "/" + String(month()) + "/" + String(year()) + "  " + String( hour()) + ":" + String(minute()) + ":" + String(second()); 
    String altura = String(distancia.calcularDistancia()) + "cm";
    String movi = String(movimiento.detectarMovimiento());
    String puerta= String(magn.puertaAbierta());
    String haygas=String(gas.leerGas());
    
    
    client.publish("grupo7/practica/gas",fecha);



    
    //Retardo de 1s entre medición y medición
    delay(1000);
}
