//VERTICI

//Selezionare tutti gli eventi criminosi in un determinato distretto in quel determinato anno
"SELECT * FROM EVENTS WHERE is_crime=1 AND district_id=? AND YEAR(reported_date)=?"

//Selezionare tutti gli eventi NON CRIMINOSI in un determinato distretto in quel determinato anno
"SELECT * FROM EVENTS WHERE is_crime=0 AND district_id=? AND YEAR(reported_date)=?"

//Selezionare i vari distretti con la relativa posizione media (Lat,Lon) dei crimini avvenuti in quell'anno
"SELECT DISTINCT district_id, AVG(geo_lon) AS avgLon, AVG(geo_lat) AS avgLat FROM events WHERE YEAR(reported_date)=? GROUP BY district_id"

//Selezionare tutti i tipi di crimini effettuati in quell'anno e in quel distretto
"SELECT DISTINCT offense_type_id FROM EVENTS WHERE YEAR(reported_date)=? AND district_id=?"

//Selezionare i tutti i vari tipi di crimini con la relativa posizione media (Lat,Lon) avvenuti in quell'anno
"SELECT DISTINCT offense_type_id, AVG(geo_lon) AS avgLon, AVG(geo_lat) AS avgLat FROM events WHERE YEAR(reported_date)=? GROUP BY offense_type_id"

//Selezionare numero di crimini totali commessi in quell'anno per ogni distretto
"SELECT district_id AS id, COUNT(*) AS cnt FROM EVENTS WHERE YEAR(reported_date)=? AND is_crime=1 GROUP BY district_id"

//Selezionare tutti gli eventi riportati in quell'anno, quel mese e quel giorno
"SELECT * FROM events WHERE Year(reported_date)=? AND Month(reported_date)=? AND Day(reported_date)=?"

//Selezionare diversi tipi di eventi criminosi o non
"SELECT DISTINCT e.offense_type_id FROM events e WHERE e.is_crime = 1/0" ;

//Selezionare diversi tipi di eventi criminosi o non nell'anno x
"SELECT DISTINCT e.offense_type_id FROM events e WHERE e.is_crime = 1/0 AND year(e.reported_date) = ?";

//Selezionare distinct offense_code di eventi criminosi o non
"SELECT DISTINCT e.offense_code FROM EVENTS e WHERE e.is_crime = 1/0";

//Selezionare distinct precinct_id di eventi criminosi o non
"SELECT DISTINCT e.precinct_id FROM events e WHERE e.is_crime = 1/0";

//Selezionare distinct neighborhood_id di eventi criminosi o non 
"SELECT DISTINCT e.neighborhood_id FROM events e WHERE e.is_crime = 1/0";

//Selezionare eventi criminosi o non  offense_code/precinct_id/neighborhood_id/offense_type_id = x, nell' anno y e distretto z
"SELECT * FROM events e WHERE e.offense_code/precinct_id/neighborhood_id/offense_type_id = ? AND YEAR(e.reported_date) = ? AND e.district_id = ? AND e.is_crime = 1/0";

//Seleziona eventi di tipo all-other-crimes con offense_code = x
"SELECT * FROM EVENTS e WHERE e.offense_category_id = 'all-other-crimes' AND e.offense_code = ?"


//ARCHI

//Selezionare id,lat,lon di due eventi criminosi o meno avvenuti nello stesso giorno del mese dell'anno
"SELECT e.incident_id AS e1,e.geo_lat AS lat1, e.geo_lon AS lon1, e2.geo_lat AS lat2, e2.geo_lon AS lon2 ,e2.incident_id AS e2 FROM events e , events e2 WHERE YEAR(e.reported_date)=? AND MONTH(e.reported_date)=? AND DAY(e.reported_date)=? AND YEAR(e2.reported_date)=? AND MONTH(e2.reported_date)=? AND DAY(e2.reported_date)=? AND e.incident_id <> e2.incident_id GROUP BY e1,e2";

//Selezionare id,lat,lon di due eventi criminosi o meno avvenuti nello stesso giorno del mese dell'anno dello stesso tipo
"SELECT e.incident_id AS e1,e.geo_lat AS lat1, e.geo_lon AS lon1, e2.geo_lat AS lat2, e2.geo_lon AS lon2 ,e2.incident_id AS e2 FROM events e , events e2 WHERE YEAR(e.reported_date)=? AND MONTH(e.reported_date)=? AND DAY(e.reported_date)=? AND YEAR(e2.reported_date)=? AND MONTH(e2.reported_date)=? AND DAY(e2.reported_date)=? AND e.offense_code/precinct_id/neighborhood_id/offense_type_id = e2.offense_code/precinct_id/neighborhood_id/offense_type_id AND e.incident_id <> e2.incident_id AND e.is_crime = 1/0 GROUP BY e1,e2"