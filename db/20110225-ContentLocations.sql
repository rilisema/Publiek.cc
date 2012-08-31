CREATE DATABASE  IF NOT EXISTS `pcc` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `pcc`;
-- MySQL dump 10.13  Distrib 5.1.34, for apple-darwin9.5.0 (i386)
--
-- Host: localhost    Database: pcc
-- ------------------------------------------------------
-- Server version	5.1.41

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `content_location`
--

DROP TABLE IF EXISTS `content_location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `content_location` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `content_name` varchar(255) NOT NULL,
  `content_type` varchar(255) NOT NULL,
  `location_active` bit(1) NOT NULL,
  `publisher` varchar(255) NOT NULL,
  `url` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `content_type` (`content_type`,`publisher`,`content_name`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `content_location`
--

LOCK TABLES `content_location` WRITE;
/*!40000 ALTER TABLE `content_location` DISABLE KEYS */;
INSERT INTO `content_location` VALUES (1,0,'Volledig productie','VAC','','ICTU','http://content.antwoord.nl/service/vacs/antwoord/1_2_2')
,(2,0,'SDU Producten','PDC','','SDU','http://xml.sduconnect.nl/product.xml?account_id=288&product_collection_id=977&view=collection_product_full&vers=3')
,(3,0,'Thema Indeling Overheid','TiO','','ICTU','http://standaarden.overheid.nl/owms/terms/ThemaindelingOverheid.xml'),(4,1,'TEST omgeving ICTU','VAC','\0','ICTU','http://alfred.acc.oha.overheid.asp4all.nl/service/vacs/antwoord/1_2_2')
,(5,3,'Test opperwoude','PDC','\0','Impactive','http://partners.impactive.nl/content/techniek/xml/demo_opusplus.xml')
,(6,1,'GPDC Impactive Rotterdam','PDC','\0','Impactive','http://www.opusservice.nl/getopusfeed/default.aspx?KID=96b57f8b-7f20-423c-a97d-cc073db0ef14&LID=ced1eb19-eee5-490c-932f-3d69fd2e6de8&CID=4')
,(7,3,'Rotterdam - Impactive theme (klantvragen)','Thema','\0','Impactive','http://www.opusservice.nl/getopusfeed/default.aspx?KID=96b57f8b-7f20-423c-a97d-cc073db0ef14&LID=ced1eb19-eee5-490c-932f-3d69fd2e6de8&CID=3')
,(8,2,'Rotterdam - Producten Samenwerkende Catalogi','PDC','\0','Impactive','http://www.opusservice.nl/getopusfeed/default.aspx?KID=96b57f8b-7f20-423c-a97d-cc073db0ef14&LID=ced1eb19-eee5-490c-932f-3d69fd2e6de8&CID=1')
,(9,0,'Opperwoude - Producten Samenwerkende Catalogi','PDC','','Impactive','http://www.opusservice.nl/getopusfeed/default.aspx?KID=9164b1d1-ada6-4087-ae6b-6a93014870b3&LID=ced1eb19-eee5-490c-932f-3d69fd2e6de8&CID=1')
,(10,0,'Opperwoude - GPDC Impactive','PDC','','Impactive','http://www.opusservice.nl/getopusfeed/default.aspx?KID=9164b1d1-ada6-4087-ae6b-6a93014870b3&LID=ced1eb19-eee5-490c-932f-3d69fd2e6de8&CID=4')
,(11,1,'Opperwoude - Impactive theme (klantvragen)','Thema','','Impactive','http://www.opusservice.nl/getopusfeed/default.aspx?KID=9164b1d1-ada6-4087-ae6b-6a93014870b3&LID=ced1eb19-eee5-490c-932f-3d69fd2e6de8&CID=3');

/*!40000 ALTER TABLE `content_location` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-02-25 19:03:24
