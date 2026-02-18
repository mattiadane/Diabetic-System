-- MySQL dump 10.13  Distrib 8.0.45, for Linux (x86_64)
--
-- Host: localhost    Database: progetto_ing
-- ------------------------------------------------------
-- Server version	8.0.45-0ubuntu0.24.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `assunzione_farmaco`
--

DROP TABLE IF EXISTS `assunzione_farmaco`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `assunzione_farmaco` (
  `id_assunzione` int NOT NULL AUTO_INCREMENT,
  `id_paziente` int NOT NULL,
  `id_farmaco` int NOT NULL,
  `dosaggio_quantità` decimal(10,2) NOT NULL,
  `dosaggio_unità` varchar(10) NOT NULL,
  `data_assunzione` datetime NOT NULL,
  PRIMARY KEY (`id_assunzione`),
  KEY `fk_assunzione_farmaco` (`id_farmaco`),
  KEY `fk_assunzione_paziente` (`id_paziente`),
  CONSTRAINT `fk_assunzione_farmaco` FOREIGN KEY (`id_farmaco`) REFERENCES `farmaco` (`id_farmaco`),
  CONSTRAINT `fk_assunzione_paziente` FOREIGN KEY (`id_paziente`) REFERENCES `paziente` (`id_paziente`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assunzione_farmaco`
--

LOCK TABLES `assunzione_farmaco` WRITE;
/*!40000 ALTER TABLE `assunzione_farmaco` DISABLE KEYS */;
/*!40000 ALTER TABLE `assunzione_farmaco` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chat`
--

DROP TABLE IF EXISTS `chat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat` (
  `id_messaggio` int NOT NULL AUTO_INCREMENT,
  `id_paziente` int NOT NULL,
  `id_diabetologo` int NOT NULL,
  `messaggio` text NOT NULL,
  `data_invio` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `mittente` enum('paziente','diabetologo') NOT NULL,
  PRIMARY KEY (`id_messaggio`),
  KEY `fk_messaggio_paziente` (`id_paziente`),
  KEY `fk_messaggio_diabetologo` (`id_diabetologo`),
  CONSTRAINT `fk_chat_diabetologo` FOREIGN KEY (`id_diabetologo`) REFERENCES `diabetologo` (`id_diabetologo`) ON DELETE CASCADE,
  CONSTRAINT `fk_chat_paziente` FOREIGN KEY (`id_paziente`) REFERENCES `paziente` (`id_paziente`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat`
--

LOCK TABLES `chat` WRITE;
/*!40000 ALTER TABLE `chat` DISABLE KEYS */;
/*!40000 ALTER TABLE `chat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comorbita`
--

DROP TABLE IF EXISTS `comorbita`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comorbita` (
  `id_comorbita` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) NOT NULL,
  `id_paziente` int NOT NULL,
  PRIMARY KEY (`id_comorbita`),
  KEY `id_paziente` (`id_paziente`),
  CONSTRAINT `comorbita_ibfk_1` FOREIGN KEY (`id_paziente`) REFERENCES `paziente` (`id_paziente`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comorbita`
--

LOCK TABLES `comorbita` WRITE;
/*!40000 ALTER TABLE `comorbita` DISABLE KEYS */;
/*!40000 ALTER TABLE `comorbita` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `diabetologo`
--

DROP TABLE IF EXISTS `diabetologo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `diabetologo` (
  `id_diabetologo` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) NOT NULL,
  `cognome` varchar(50) NOT NULL,
  `codice_fiscale` varchar(16) NOT NULL,
  `email` varchar(255) NOT NULL,
  `sesso` enum('M','F') NOT NULL,
  PRIMARY KEY (`id_diabetologo`),
  UNIQUE KEY `codice_fiscale` (`codice_fiscale`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `diabetologo`
--

LOCK TABLES `diabetologo` WRITE;
/*!40000 ALTER TABLE `diabetologo` DISABLE KEYS */;
/*!40000 ALTER TABLE `diabetologo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `farmaco`
--

DROP TABLE IF EXISTS `farmaco`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `farmaco` (
  `id_farmaco` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) DEFAULT NULL,
  `descrizione` text,
  PRIMARY KEY (`id_farmaco`),
  UNIQUE KEY `nome` (`nome`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `farmaco`
--

LOCK TABLES `farmaco` WRITE;
/*!40000 ALTER TABLE `farmaco` DISABLE KEYS */;
INSERT INTO `farmaco` VALUES (1,'Glucophage','Classe: Biguanide (Principio Attivo: Metformina). Agisce riducendo la produzione di glucosio da parte del fegato e migliorando la sensibilità all\'insulina. Si assume per via orale, tipicamente con o dopo i pasti.'),(2,'Januvia','Classe: Inibitore DPP-4 (Principio Attivo: Sitagliptin). Funziona potenziando l\'effetto di ormoni naturali (incretine) che stimolano il rilascio di insulina e riducono il glucagone. Si assume per via orale.'),(3,'Forxiga','Classe: SGLT2 Inibitore (Principio Attivo: Dapagliflozin). Agisce sui reni, aumentando l\'eliminazione di glucosio attraverso le urine. Si assume per via orale,solitamente al mattino.'),(4,'Ozempic','Classe: GLP-1 Agonista (Principio Attivo: Semaglutide). Mima l\'azione di un ormone naturale che aiuta a regolare la glicemia, rallenta lo svuotamento gastrico e favorisce il senso di sazietà. Si somministra tramite iniezione sottocutanea.'),(5,'Lantus','Classe: Insulina Basale (Principio Attivo: Insulina Glargine). È un\'insulina a lunga durata d\'azione che fornisce un controllo glicemico costante per circa 24 ore. Si somministra tramite iniezione sottocutanea'),(6,'Diamicron','Classe: Sulfonilurea (Principio Attivo: Gliclazide). Stimola il pancreas a produrre e rilasciare più insulina. Si assume per via orale.'),(7,'Actos','Classe: Tiazolidinedione (Principio Attivo: Pioglitazone). Migliora la sensibilità delle cellule all\'insulina, permettendo un migliore assorbimento del glucosio. Si assume per via orale.'),(8,'Glucobay','Classe: Inibitore dell\'Alfa-Glucosidasi (Principio Attivo: Acarbose). Rallenta la digestione e l\'assorbimento dei carboidrati nell\'intestino tenue, riducendo i picchi di glicemia dopo i pasti. Si assume per via orale.');
/*!40000 ALTER TABLE `farmaco` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fattore_rischio`
--

DROP TABLE IF EXISTS `fattore_rischio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fattore_rischio` (
  `id_fattore_rischio` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) NOT NULL,
  `id_paziente` int NOT NULL,
  PRIMARY KEY (`id_fattore_rischio`),
  KEY `id_paziente` (`id_paziente`),
  CONSTRAINT `fattore_rischio_ibfk_1` FOREIGN KEY (`id_paziente`) REFERENCES `paziente` (`id_paziente`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fattore_rischio`
--

LOCK TABLES `fattore_rischio` WRITE;
/*!40000 ALTER TABLE `fattore_rischio` DISABLE KEYS */;
/*!40000 ALTER TABLE `fattore_rischio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `insulina`
--

DROP TABLE IF EXISTS `insulina`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `insulina` (
  `id_glicemia` int NOT NULL AUTO_INCREMENT,
  `id_paziente` int NOT NULL,
  `valore_glicemia` decimal(5,2) NOT NULL,
  `unità_di_misura` varchar(10) DEFAULT 'mg/dL',
  `orario` datetime NOT NULL,
  `periodo` enum('prima della colazione','dopo la colazione','prima del pranzo','dopo pranzo','prima di cena','dopo cena') NOT NULL,
  `sintomi` text,
  `notificata` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id_glicemia`),
  KEY `fk_paziente_glicemia` (`id_paziente`),
  CONSTRAINT `fk_paziente_glicemia` FOREIGN KEY (`id_paziente`) REFERENCES `paziente` (`id_paziente`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `insulina`
--

LOCK TABLES `insulina` WRITE;
/*!40000 ALTER TABLE `insulina` DISABLE KEYS */;
/*!40000 ALTER TABLE `insulina` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `login`
--

DROP TABLE IF EXISTS `login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `login` (
  `id_login` int NOT NULL AUTO_INCREMENT,
  `id_paziente` int DEFAULT NULL,
  `id_diabetologo` int DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  `data_creazione` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_login`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `id_paziente` (`id_paziente`),
  UNIQUE KEY `id_diabetologo` (`id_diabetologo`),
  CONSTRAINT `fk_login_diabetologo` FOREIGN KEY (`id_diabetologo`) REFERENCES `diabetologo` (`id_diabetologo`) ON DELETE CASCADE,
  CONSTRAINT `fk_login_paziente` FOREIGN KEY (`id_paziente`) REFERENCES `paziente` (`id_paziente`) ON DELETE CASCADE,
  CONSTRAINT `login_chk_1` CHECK ((((`id_paziente` is null) and (`id_diabetologo` is null) and (`username` like _utf8mb4'admin')) or ((`id_paziente` is null) and (`id_diabetologo` is not null)) or ((`id_paziente` is not null) and (`id_diabetologo` is null))))
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login`
--

LOCK TABLES `login` WRITE;
/*!40000 ALTER TABLE `login` DISABLE KEYS */;
INSERT INTO `login` VALUES (1,NULL,NULL,'admin','admin','2026-01-07 10:01:10');
/*!40000 ALTER TABLE `login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patologia_pregressa`
--

DROP TABLE IF EXISTS `patologia_pregressa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patologia_pregressa` (
  `id_patologia_pregressa` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) NOT NULL,
  `id_paziente` int NOT NULL,
  PRIMARY KEY (`id_patologia_pregressa`),
  KEY `id_paziente` (`id_paziente`),
  CONSTRAINT `patologia_pregressa_ibfk_1` FOREIGN KEY (`id_paziente`) REFERENCES `paziente` (`id_paziente`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patologia_pregressa`
--

LOCK TABLES `patologia_pregressa` WRITE;
/*!40000 ALTER TABLE `patologia_pregressa` DISABLE KEYS */;
/*!40000 ALTER TABLE `patologia_pregressa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paziente`
--

DROP TABLE IF EXISTS `paziente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `paziente` (
  `id_paziente` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) NOT NULL,
  `cognome` varchar(50) NOT NULL,
  `codice_fiscale` varchar(16) NOT NULL,
  `data_nascita` date NOT NULL,
  `email` varchar(255) NOT NULL,
  `sesso` enum('M','F') NOT NULL,
  `id_diabetologo` int DEFAULT NULL,
  PRIMARY KEY (`id_paziente`),
  UNIQUE KEY `codice_fiscale` (`codice_fiscale`),
  UNIQUE KEY `email` (`email`),
  KEY `fk_paziente_diabetologo` (`id_diabetologo`),
  CONSTRAINT `fk_paziente_diabetologo` FOREIGN KEY (`id_diabetologo`) REFERENCES `diabetologo` (`id_diabetologo`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paziente`
--

LOCK TABLES `paziente` WRITE;
/*!40000 ALTER TABLE `paziente` DISABLE KEYS */;
/*!40000 ALTER TABLE `paziente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `terapia`
--

DROP TABLE IF EXISTS `terapia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `terapia` (
  `id_terapia` int NOT NULL AUTO_INCREMENT,
  `id_paziente` int NOT NULL,
  `id_diabetologo` int DEFAULT NULL,
  `id_farmaco` int NOT NULL,
  `dosaggio_quantità` decimal(10,2) NOT NULL,
  `dosaggio_unità` varchar(10) NOT NULL,
  `quanto` int NOT NULL,
  `periodicità` enum('giorno') NOT NULL DEFAULT 'giorno',
  `data_inizio_terapia` date NOT NULL,
  `data_fine_terapia` date NOT NULL,
  `descrizione` text,
  PRIMARY KEY (`id_terapia`),
  KEY `fk_terapia_paziente` (`id_paziente`),
  KEY `fk_terapia_diabetologo` (`id_diabetologo`),
  KEY `fk_terapia_farmaco` (`id_farmaco`),
  CONSTRAINT `fk_terapia_diabetologo` FOREIGN KEY (`id_diabetologo`) REFERENCES `diabetologo` (`id_diabetologo`) ON DELETE CASCADE,
  CONSTRAINT `fk_terapia_farmaco` FOREIGN KEY (`id_farmaco`) REFERENCES `farmaco` (`id_farmaco`),
  CONSTRAINT `fk_terapia_paziente` FOREIGN KEY (`id_paziente`) REFERENCES `paziente` (`id_paziente`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `terapia`
--

LOCK TABLES `terapia` WRITE;
/*!40000 ALTER TABLE `terapia` DISABLE KEYS */;
/*!40000 ALTER TABLE `terapia` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-02-17 18:10:42
