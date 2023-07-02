
#include <ESP8266WiFi.h>
#include <FirebaseESP8266.h>
#include <time.h>


#define FIREBASE_HOST "nodemcu-94e5b-default-rtdb.asia-southeast1.firebasedatabase.app/"
#define FIREBASE_AUTH "AIzaSyCHATkM-Iuwomndz6ZZrgaF2FxgfTS0Y7I"
#define WIFI_SSID "DINU"
#define WIFI_PASSWORD "Mayadunna755"

FirebaseData firebaseData;

int read_data = 0;
int send_data = 0;
int hour = 0;
int mins = 0;
int real_hour = 0;
int real_min = 0;
bool isbtnTrigger = false;

int timezone = 5 * 3600 + 1800;
int dst = 0;

void setup() {

  Serial.begin(9600);
  pinMode(D0, OUTPUT);

  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("Connecting to ");
  Serial.print(WIFI_SSID);

  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(500);
  }

  Serial.println();
  Serial.print("Connected to ");
  Serial.println(WIFI_SSID);
  Serial.print("IP Address is : ");
  Serial.println(WiFi.localIP());
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);

  Firebase.reconnectWiFi(true);

  Serial.println();
  delay(1000);

  configTime(timezone, dst, "pool.ntp.org", "time.nist.gov");
  Serial.println("\nWaiting for Internet time");

  while (!time(nullptr)) {
    Serial.print("*");
    delay(1000);
  }
  Serial.println("\nTime response....OK");

  Firebase.setInt(firebaseData, "/OnOff", 0);
  Firebase.setInt(firebaseData, "/timer/on", 0);
  Firebase.setInt(firebaseData, "/timer/hour", 0);
  Firebase.setInt(firebaseData, "/timer/min", 0);
  Firebase.setInt(firebaseData, "/timer/running", 0);
}

void loop() {

  time_t now = time(nullptr);
  struct tm* p_tm = localtime(&now);
  real_hour = p_tm->tm_hour;
  real_min = p_tm->tm_min;

  if (Firebase.getInt(firebaseData, "/OnOff")) {

    read_data = firebaseData.intData();
    Serial.print("Read_data = ");
    Serial.println(read_data);

    if (read_data == 1) {
      Serial.println("LED turned ON");
      digitalWrite(D0, LOW);
    } else if (read_data == 0) {
      Serial.println("LED turned OFF");
      digitalWrite(D0, HIGH);
    }
    Serial.println();
  } else {
    Serial.println(firebaseData.errorReason());
  }


  if (Firebase.getInt(firebaseData, "/timer/on")) {

    read_data = firebaseData.intData();
    Serial.print("Read_data = ");
    Serial.println(read_data);

    if (read_data == 1) {
      Firebase.setInt(firebaseData, "/timer/on", 0);
      Firebase.setInt(firebaseData, "/timer/running", 1);

      if (Firebase.getInt(firebaseData, "/timer/hour")) {
        hour = firebaseData.intData();
        hour = real_hour + hour;
      }


      if (Firebase.getInt(firebaseData, "/timer/min")) {
        mins = firebaseData.intData();
        mins = real_min + mins;
      }
    } else if (read_data == 0) {
    }
    Serial.println();
  } else {
    Serial.println(firebaseData.errorReason());
  }

  if (Firebase.getInt(firebaseData, "/timer/running")) {

    read_data = firebaseData.intData();
    

    if (read_data == 1) {
      isbtnTrigger = true; 
      if ((real_hour == 0) && hour >= 24) {
        hour = hour % 24;
        Serial.println("Target Hours = ");
        Serial.println(hour);
      }
      if ((real_min == 0) && mins >= 60) {
        mins = mins % 60;
        hour = hour + 1;
        Serial.print("Target Mins = ");
        Serial.println(mins);
        Serial.println("Target Hours = ");
        Serial.println(hour);
      }
      if ((real_hour >= hour) && (real_min >= mins)) {
        Firebase.setInt(firebaseData, "/OnOff", 0);
        Firebase.setInt(firebaseData, "/timer/running", 0);
      } else {
        Firebase.setInt(firebaseData, "/OnOff", 1);
      }
    }

    if(isbtnTrigger == true && read_data == 0){
      isbtnTrigger = false;
      Firebase.setInt(firebaseData, "/OnOff", 0);
    }
  }




  // TURN LED ON

  /* Serial.print("Minutes: ");
  Serial.println(p_tm->tm_min);
  Serial.print("Hours: ");
  Serial.println(p_tm->tm_hour); */


  // TURN LED OFF

  /* if ((p_tm->tm_hour >= 14) && (p_tm->tm_min >= 34)) {
    digitalWrite(D0, HIGH);
  } else {
    digitalWrite(D0, LOW);
  } */

  delay(1000);
}