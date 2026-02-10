DROP TABLE IF EXISTS terapia;
DROP TABLE IF EXISTS insulina;
DROP TABLE IF EXISTS paziente;
DROP TABLE IF EXISTS diabetologo;

CREATE TABLE diabetologo (
                             id_diabetologo INT AUTO_INCREMENT PRIMARY KEY,
                             nome VARCHAR(50) NOT NULL,
                             cognome VARCHAR(50) NOT NULL,
                             codice_fiscale VARCHAR(16) NOT NULL,
                             email VARCHAR(255) NOT NULL,
                             sesso VARCHAR(1) NOT NULL,
                             UNIQUE (codice_fiscale),
                             UNIQUE (email)
);

CREATE TABLE paziente (
                          id_paziente INT AUTO_INCREMENT PRIMARY KEY,
                          nome VARCHAR(50) NOT NULL,
                          cognome VARCHAR(50) NOT NULL,
                          codice_fiscale VARCHAR(16) NOT NULL,
                          data_nascita DATE NOT NULL,
                          email VARCHAR(255) NOT NULL,
                          sesso VARCHAR(1) NOT NULL,
                          id_diabetologo INT,
                          UNIQUE (codice_fiscale),
                          UNIQUE (email),
                          FOREIGN KEY (id_diabetologo) REFERENCES diabetologo(id_diabetologo) ON DELETE CASCADE
);

CREATE TABLE insulina (
                          id_glicemia INT AUTO_INCREMENT PRIMARY KEY,
                          id_paziente INT NOT NULL,
                          valore_glicemia DECIMAL(5,2) NOT NULL,
                          unita_di_misura VARCHAR(10) DEFAULT 'mg/dL',
                          orario TIMESTAMP NOT NULL,
                          periodo VARCHAR(30) NOT NULL,
                          sintomi TEXT,
                          FOREIGN KEY (id_paziente) REFERENCES paziente(id_paziente) ON DELETE CASCADE
);

CREATE TABLE terapia (
                         id_terapia INT AUTO_INCREMENT PRIMARY KEY,
                         id_paziente INT NOT NULL,
                         id_diabetologo INT,
                         id_farmaco INT NOT NULL,
                         dosaggio_quantità DECIMAL(10,2) NOT NULL,
                         dosaggio_unità VARCHAR(10) NOT NULL,
                         quanto INT NOT NULL,
                         periodicità VARCHAR(20) NOT NULL DEFAULT 'giorno',
                         data_inizio_terapia DATE NOT NULL,
                         data_fine_terapia DATE NOT NULL,
                         descrizione TEXT,
                         FOREIGN KEY (id_paziente) REFERENCES paziente(id_paziente) ON DELETE CASCADE,
                         FOREIGN KEY (id_diabetologo) REFERENCES diabetologo(id_diabetologo) ON DELETE CASCADE
);
