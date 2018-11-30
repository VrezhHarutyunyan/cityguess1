package com.tvo.cityguess.database;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    private static final String DB_NAME = "LieCatCityGuess";
    private static final int DB_VERSION = 1;

    private static final String COUNTRIES = "Countries";
    private static final String ID = "id";
    private static final String COUNTRY = "country";
    private static final String CAPITAL = "capital";
    private static final String DIFFICULTY = "difficulty";
    
    private static final String EASY = "easy";
    private static final String MEDIUM = "medium";
    private static final String HARD = "hard";

    private static Database sharedDatabase;

    private Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static Database sharedDatabase(Context context) {
        if (sharedDatabase == null) {
            sharedDatabase = new Database(context);
        }

        return sharedDatabase;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        StringBuilder createQuery = new StringBuilder();
        createQuery
                .append("CREATE TABLE IF NOT EXISTS ").append(COUNTRIES).append(" ( ")
                .append(ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(COUNTRY).append(" TEXT NOT NULL , ")
                .append(CAPITAL).append(" TEXT NOT NULL , ")
                .append(DIFFICULTY).append(" TEXT NOT NULL ")
                .append(" ); ");
        StringBuilder initQuery = new StringBuilder();
        initQuery
                .append("INSERT INTO ").append(COUNTRIES)
                .append(" (").append(COUNTRY).append(", ").append(CAPITAL).append(", ").append(DIFFICULTY).append(") VALUES ")
                .append(" ('Austria', 'Vienna', " + DatabaseUtils.sqlEscapeString(EASY) + "), ")
                .append(" ('Australia', 'Canberra', " + DatabaseUtils.sqlEscapeString(EASY) + "), ")
                .append(" ('Andorra', 'Andorra la Vella', " + DatabaseUtils.sqlEscapeString(EASY) + "), ")
                .append(" ('Belgium', 'Brussels', " + DatabaseUtils.sqlEscapeString(EASY) + "), ")
                .append(" ('Brazil', 'Brasília', " + DatabaseUtils.sqlEscapeString(EASY) + "), ")
                .append(" ('Canada', 'Ottawa', " + DatabaseUtils.sqlEscapeString(EASY) + "), ")
                .append(" ('Czech Republic', 'Prague', " + DatabaseUtils.sqlEscapeString(EASY) + "), ")
                .append(" ('Germany', 'Berlin', " + DatabaseUtils.sqlEscapeString(EASY) + "), ")
                .append(" ('China', 'Beijing', " + DatabaseUtils.sqlEscapeString(EASY) + "), ")
                .append(" ('Algeria', 'Algiers', " + DatabaseUtils.sqlEscapeString(EASY) + "), ")
                .append(" ('France', 'Paris', " + DatabaseUtils.sqlEscapeString(EASY) + "), ")
                .append(" ('Spain', 'Madrid', " + DatabaseUtils.sqlEscapeString(EASY) + "), ")
                .append(" ('United Kingdom', 'London', " + DatabaseUtils.sqlEscapeString(EASY) + "), ")
                .append(" ('Japan', 'Tokyo', " + DatabaseUtils.sqlEscapeString(EASY) + "), ")
                .append(" ('Guatemala', 'Guatemala City', " + DatabaseUtils.sqlEscapeString(EASY) + "), ")
                .append(" ('Hong Kong', 'Hong Kong', " + DatabaseUtils.sqlEscapeString(EASY) + "), ")
                .append(" ('Mexico', 'Mexico City', " + DatabaseUtils.sqlEscapeString(EASY) + "), ")
                .append(" ('Panama', 'Panama City', " + DatabaseUtils.sqlEscapeString(EASY) + "), ")
                .append(" ('Netherlands', 'Amsterdam', " + DatabaseUtils.sqlEscapeString(EASY) + "), ")
                .append(" ('Monaco', 'Monaco', " + DatabaseUtils.sqlEscapeString(EASY) + "), ")
                .append(" ('Macao', 'Macao', " + DatabaseUtils.sqlEscapeString(EASY) + "), ")
                .append(" ('Norway', 'Oslo', " + DatabaseUtils.sqlEscapeString(EASY) + "), ")
                .append(" ('Russia', 'Moscow', " + DatabaseUtils.sqlEscapeString(EASY) + "), ")
                .append(" ('San Marino', 'San Marino', " + DatabaseUtils.sqlEscapeString(EASY) + "), ")
                .append(" ('Italy', 'Rome', " + DatabaseUtils.sqlEscapeString(EASY) + "), ")
                .append(" ('Kuwait', 'Kuwait City', " + DatabaseUtils.sqlEscapeString(EASY) + "), ")
                .append(" ('El Salvador', 'San Salvador', " + DatabaseUtils.sqlEscapeString(EASY) + "), ")
                .append(" ('Tunisia', 'Tunis', " + DatabaseUtils.sqlEscapeString(EASY) + "), ")
                .append(" ('Argentina', 'Buenos Aires', " + DatabaseUtils.sqlEscapeString(EASY) + "), ")
                .append(" ('Denmark', 'Copenhagen', " + DatabaseUtils.sqlEscapeString(EASY) + "), ")
                .append(" ('Greece', 'Athens', " + DatabaseUtils.sqlEscapeString(EASY) + "), ")
                .append(" ('Iraq', 'Baghdad', " + DatabaseUtils.sqlEscapeString(EASY) + "), ")
                .append(" ('Iran', 'Tehran', " + DatabaseUtils.sqlEscapeString(EASY) + "), ")
                .append(" ('Sweden', 'Stockholm', " + DatabaseUtils.sqlEscapeString(EASY) + "), ")
                .append(" ('United States', 'Washington', " + DatabaseUtils.sqlEscapeString(EASY) + "), ")
                .append(" ('Venezuela', 'Caracas', " + DatabaseUtils.sqlEscapeString(EASY) + "), ")
                .append(" ('Afghanistan', 'Kabul', " + DatabaseUtils.sqlEscapeString(EASY) + "), ")
                .append(" ('Colombia', 'Bogotá', " + DatabaseUtils.sqlEscapeString(EASY) + "), ")
                .append(" ('Cuba', 'Havana', " + DatabaseUtils.sqlEscapeString(EASY) + "), ")
                .append(" ('Egypt', 'Cairo', " + DatabaseUtils.sqlEscapeString(EASY) + "), ")
                .append(" ('India', 'New Delhi', " + DatabaseUtils.sqlEscapeString(EASY) + "), ")
                .append(" ('Syria', 'Damascus', " + DatabaseUtils.sqlEscapeString(EASY) + "), ")
                .append(" ('Singapore', 'Singapore', " + DatabaseUtils.sqlEscapeString(EASY) + "), ")
                .append(" ('Thailand', 'Bangkok', " + DatabaseUtils.sqlEscapeString(EASY) + "), ")
                .append(" ('Vietnam', 'Hanoi', " + DatabaseUtils.sqlEscapeString(EASY) + "), ")
                .append(" ('Saint Pierre and Miquelon', 'Saint-Pierre', " + DatabaseUtils.sqlEscapeString(EASY) + "), ")
                .append(" ('Hungary', 'Budapest', " + DatabaseUtils.sqlEscapeString(EASY) + "), ")
                .append(" ('Indonesia', 'Jakarta', " + DatabaseUtils.sqlEscapeString(EASY) + "), ")
                .append(" ('Israel', 'Tel Aviv', " + DatabaseUtils.sqlEscapeString(EASY) + "), ")
                .append(" ('Gibraltar', 'Gibraltar', " + DatabaseUtils.sqlEscapeString(EASY) + "), ")
                .append(" ('Djibouti', 'Djibouti', " + DatabaseUtils.sqlEscapeString(EASY) + "), ")

                .append(" ('United Arab Emirates', 'Abu Dhabi', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Albania', 'Tirana', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Bulgaria', 'Sofia', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Switzerland', 'Berne', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Finland', 'Helsinki', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Ireland', 'Dublin', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Chile', 'Santiago', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Iceland', 'Reykjavik', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Kenya', 'Nairobi', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('South Korea', 'Seoul', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Latvia', 'Riga', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Peru', 'Lima', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Pakistan', 'Islamabad', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Libya', 'Tripoli', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Romania', 'Bucharest', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Turkey', 'Ankara', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Lebanon', 'Beirut', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Mongolia', 'Ulan Bator', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Taiwan', 'Taipei', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Poland', 'Warsaw', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Philippines', 'Manila', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Kosovo', 'Pristina', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Cocos [Keeling] Islands', 'West Island', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Ecuador', 'Quito', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('North Korea', 'Pyongyang', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Ukraine', 'Kyiv', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Lithuania', 'Vilnius', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Malaysia', 'Kuala Lumpur', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Paraguay', 'Asunción', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Uruguay', 'Montevideo', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Belarus', 'Minsk', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Armenia', 'Yerevan', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Azerbaijan', 'Baku', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Angola', 'Luanda', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Bosnia and Herzegovina', 'Sarajevo', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Montenegro', 'Podgorica', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Nicaragua', 'Managua', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Haiti', 'Port-au-Prince', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Bolivia', 'Sucre', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Costa Rica', 'San José', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Dominican Republic', 'Santo Domingo', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")

                .append(" ('Georgia', 'Tbilisi', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Honduras', 'Tegucigalpa', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Croatia', 'Zagreb', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Jamaica', 'Kingston', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Madagascar', 'Antananarivo', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Kazakhstan', 'Astana', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Cyprus', 'Nicosia', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Laos', 'Vientiane', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")

                .append(" ('Macedonia', 'Skopje', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Mali', 'Bamako', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Zimbabwe', 'Harare', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('South Africa', 'Pretoria', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Cambodia', 'Phnom Penh', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Puerto Rico', 'San Juan', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Saudi Arabia', 'Riyadh', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Sri Lanka', 'Colombo', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")

                .append(" ('Morocco', 'Rabat', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('New Zealand', 'Wellington', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Mozambique', 'Maputo', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Tajikistan', 'Dushanbe', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Estonia', 'Tallinn', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Ethiopia', 'Addis Ababa', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Kyrgyzstan', 'Bishkek', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Maldives', 'Malé', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Nepal', 'Kathmandu', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Palestine', 'Ramallah', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Portugal', 'Lisbon', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Serbia', 'Belgrade', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Slovakia', 'Bratislava', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Slovenia', 'Ljubljana', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Turkmenistan', 'Ashgabat', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Uzbekistan', 'Tashkent', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")
                .append(" ('Nagorno-Karabakh Republic', 'Stepanakert', " + DatabaseUtils.sqlEscapeString(MEDIUM) + "), ")

                .append(" ('Antigua and Barbuda', 'St. John''s', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Anguilla', 'The Valley', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('American Samoa', 'Pago Pago', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Aruba', 'Oranjestad', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Åland', 'Mariehamn', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Barbados', 'Bridgetown', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Bangladesh', 'Dhaka', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Burkina Faso', 'Ouagadougou', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Bahrain', 'Manama', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Burundi', 'Bujumbura', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Benin', 'Porto-Novo', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Saint Barthélemy', 'Gustavia', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Bermuda', 'Hamilton', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Brunei', 'Bandar Seri Begawan', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Bonaire', 'Kralendijk', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Bahamas', 'Nassau', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Bhutan', 'Thimphu', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Botswana', 'Gaborone', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Belize', 'Belmopan', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Democratic Republic of the Congo', 'Kinshasa', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Central African Republic', 'Bangui', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Republic of the Congo', 'Brazzaville', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Ivory Coast', 'Yamoussoukro', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Cook Islands', 'Avarua', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Cameroon', 'Yaoundé', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Cape Verde', 'Praia', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Curacao', 'Willemstad', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Christmas Island', 'The Settlement', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Dominica', 'Roseau', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Western Sahara', 'El Aaiún', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Eritrea', 'Asmara', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Fiji', 'Suva', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Falkland Islands', 'Stanley', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Micronesia', 'Palikir', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Faroe Islands', 'Tórshavn', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Gabon', 'Libreville', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Grenada', 'St. George''s', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('French Guiana', 'Cayenne', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Guernsey', 'St Peter Port', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Ghana', 'Accra', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Greenland', 'Nuuk', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Gambia', 'Banjul', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Guinea', 'Conakry', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Guadeloupe', 'Basse-Terre', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Equatorial Guinea', 'Malabo', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('South Georgia and the South Sandwich Islands', 'Grytviken', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Guam', 'Hagåtña', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Guinea-Bissau', 'Bissau', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Guyana', 'Georgetown', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Isle of Man', 'Douglas', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Jersey', 'Saint Helier', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Jordan', 'Amman', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Kiribati', 'Tarawa', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Comoros', 'Moroni', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Saint Kitts and Nevis', 'Basseterre', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Cayman Islands', 'George Town', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Saint Lucia', 'Castries', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Liechtenstein', 'Vaduz', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Liberia', 'Monrovia', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Lesotho', 'Maseru', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Moldova', 'Chişinău', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Saint Martin', 'Marigot', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Marshall Islands', 'Majuro', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Myanmar [Burma]', 'Nay Pyi Taw', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Northern Mariana Islands', 'Saipan', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Martinique', 'Fort-de-France', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Mauritania', 'Nouakchott', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Montserrat', 'Plymouth', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Malta', 'Valletta', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Mauritius', 'Port Louis', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Malawi', 'Lilongwe', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Namibia', 'Windhoek', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('New Caledonia', 'Noumea', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Niger', 'Niamey', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Norfolk Island', 'Kingston', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Nigeria', 'Abuja', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Nauru', 'Yaren', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Niue', 'Alofi', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Oman', 'Muscat', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('French Polynesia', 'Papeete', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Papua New Guinea', 'Port Moresby', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Pitcairn Islands', 'Adamstown', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Palau', 'Melekeok - Palau State Capital', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Qatar', 'Doha', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Réunion', 'Saint-Denis', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Rwanda', 'Kigali', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Solomon Islands', 'Honiara', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Seychelles', 'Victoria', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Sudan', 'Khartoum', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Saint Helena', 'Jamestown', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Svalbard and Jan Mayen', 'Longyearbyen', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Sierra Leone', 'Freetown', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Senegal', 'Dakar', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Somalia', 'Mogadishu', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Suriname', 'Paramaribo', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('South Sudan', 'Juba', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('São Tomé and Príncipe', 'São Tomé', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Sint Maarten', 'Philipsburg', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Swaziland', 'Mbabane', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Turks and Caicos Islands', 'Cockburn Town', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Chad', 'N''Djamena', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('French Southern Territories', 'Port-aux-Français', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Togo', 'Lomé', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Tokelau', 'Atafu', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('East Timor', 'Dili', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Tonga', 'Nuku''alofa', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Trinidad and Tobago', 'Port of Spain', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Tuvalu', 'Funafuti', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Tanzania', 'Dodoma', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Uganda', 'Kampala', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Saint Vincent and the Grenadines', 'Kingstown', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('British Virgin Islands', 'Road Town', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('U.S. Virgin Islands', 'Charlotte Amalie', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Vanuatu', 'Port Vila', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Wallis and Futuna', 'Mata-Utu', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Samoa', 'Apia', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Yemen', 'Sanaa', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Mayotte', 'Mamoutzou', " + DatabaseUtils.sqlEscapeString(HARD) + "), ")
                .append(" ('Zambia', 'Lusaka', " + DatabaseUtils.sqlEscapeString(HARD) + "); ");


        database.execSQL(createQuery.toString());
        database.execSQL(initQuery.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

    }

    public QuizLevel generateQuizLevel( String gameDifficulty ) {
        SQLiteDatabase database = getReadableDatabase();

        String query = "SELECT * FROM " + COUNTRIES + " WHERE " + DIFFICULTY + " = " + DatabaseUtils.sqlEscapeString(gameDifficulty)  +
                " ORDER BY RANDOM() LIMIT 4 ;";

        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();

        int countryIndex = cursor.getColumnIndex(COUNTRY);
        int capitalIndex = cursor.getColumnIndex(CAPITAL);

        ArrayList<String> guesses = new ArrayList<>();
        String question = cursor.getString(countryIndex);

        while (!cursor.isAfterLast()) {
            guesses.add(cursor.getString(capitalIndex));
            cursor.moveToNext();
        }

        cursor.close();

        return new QuizLevel(question, guesses);
    }

    public class QuizLevel {
        private String question;
        private ArrayList<String> guesses;

        public QuizLevel(String question, ArrayList<String> guesses) {
            this.question = question;
            this.guesses = guesses;
        }

        public String question() {
            return question;
        }

        public ArrayList<String> guesses() {
            return guesses;
        }
    }

    public ArrayList<ListItem> getCountriesWithCapitalsArray(){

        String query = "SELECT * FROM " + COUNTRIES + " ORDER BY " + COUNTRY;
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();

        int countryIndex = cursor.getColumnIndex(COUNTRY);
        int capitalIndex = cursor.getColumnIndex(CAPITAL);
        ArrayList<ListItem> countriesWithCapitalsList = new ArrayList<ListItem>();

        String country, capital;
        ListItem item = new ListItem(null, null);
        while (!cursor.isAfterLast()) {
            country = cursor.getString(countryIndex);
            capital = cursor.getString(capitalIndex);
            item = new ListItem(country, capital);
            countriesWithCapitalsList.add(item);
            cursor.moveToNext();
        }

        return countriesWithCapitalsList;
    }

    public class ListItem {
        private String country;
        private String capital;

        public ListItem(String country, String capital) {
            this.country = country;
            this.capital = capital;
        }

        public String getCountry(){
            return country;
        }

        public  String getCapital(){
            return capital;
        }
    }
}
