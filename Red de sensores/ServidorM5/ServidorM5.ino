#include <M5Stack.h>
#include "ConexionWiFi.h"
#include "AsyncUDP.h"
#include "ConexionUDP.h"
#include <ArduinoJson.h>
#include <MQTT.h>

const char ssid[] = "Grupo7";
const char pass[] = "123456789";

//CREDENCIALES CONEXIÓN WI-FI // (ssid, password)
ConexionWiFi wifi = ConexionWiFi("Grupo7", "123456789");

char texto[200];    //array para recibir los datos como texto
int hora;
boolean rec= 0; //De momento no se reciben datos

AsyncUDP udp;
ConexionUDP udp2 = ConexionUDP(1234);

void limpiarPantalla(){
  M5.Lcd.fillScreen(BLACK);
  M5.Lcd.setCursor(0,0);
  M5.Lcd.setTextColor(WHITE);
  M5.Lcd.setTextSize(2);
}

const char broker[] = "iot.eclipse.org";
WiFiClient net;
MQTTClient client;

unsigned long lastMillis = 0;

void connect() {
  Serial.print("checking wifi...");
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(1000);
  }

  Serial.print("\nconnecting...");
  while (!client.connect("ardu", "try", "try")) {
    Serial.print(".");
    delay(1000);
  }
  Serial.println("\nconnected!");
  client.subscribe("grupo7/practica/#");
 //client.unsubscribe("<usuario>/practica/#");
}

void messageReceived(String &topic, String &payload) {
  Serial.println("incoming: " + topic + " - " + payload);
}

void setup(){
    Serial.begin(115200);
    WiFi.begin(ssid, pass);
  client.begin(broker, net);
  client.onMessage(messageReceived);
  connect();
    M5.begin();
    M5.Lcd.setTextSize(2);
    M5.Lcd.println("Conectando...");
    wifi.conectar();
    M5.Lcd.println("Conectado a la red Wi-Fi");
    rec = udp2.recibirDatos(wifi, texto);
    
    if(udp.listen(1234)) {
        Serial.print("UDP Listening on IP: ");
        Serial.println(wifi.obtenerIP());
        udp.onPacket([](AsyncUDPPacket packet) {

            int i=200;
            while (i--) {*(texto+i)=*(packet.data()+i);}
            rec=1;      //recepcion de un mensaje

        });
    }

  
}

void loop(){
      client.loop();
      delay(10);  // <- fixes some issues with WiFi stability
   if (rec){
    rec=false;
    udp2.enviarDatos("Recibido"); //Confirmación
    udp2.enviarDatos(texto);      //reenvía lo recibido
    hora=atol(texto);                 //paso de texto a int
    
    StaticJsonBuffer<200> jsonBufferRecv; //definición del buffer para almacenar el objero JSON, 200 máximo
    JsonObject& recibo = jsonBufferRecv.parseObject(texto); //paso de texto a formato JSON
    
    recibo.printTo(Serial);       //envio por el puerto serie el objeto "recibido"                    
      
    Serial.println();             //nueva línea
    
    String distancia=recibo["Altura"];  
    String hora = recibo["Hora"];   
    String movi = recibo["Movimiento"];
    String puerta= recibo["Puerta"];
    String gas= recibo["Gas"];
    
    limpiarPantalla();
    M5.Lcd.println(hora );
    M5.Lcd.println("Distancia:"+ distancia);
    M5.Lcd.println("Hay movimiento:"+movi);
    M5.Lcd.println("Puerta:"+puerta);
    M5.Lcd.println("Gas: "+gas);

  if (!client.connected()) {
    connect();
  }
  // publish a message 
  client.publish("grupo7/practica/enviarFecha",hora);
  client.publish("grupo7/practica/enviarGas",gas);  


    //Envío de datos a la Raspberry
    if(Serial.available() > 0){
    char command = (char)Serial.read();
    switch(command){
    case 'H':
      Serial.print(hora);
      //recibo["Hora"].printTo(Serial);
      break;
    case 'D':
      Serial.print("Envio de DISTANCIA: ");
      recibo["Altura"].printTo(Serial);
      Serial.println(distancia);
      break;
    }
    }
    
    delay(3000);
  }
}

