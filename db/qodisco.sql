CREATE DATABASE  IF NOT EXISTS `qodisco` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `qodisco`;
-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: 127.0.0.1    Database: qodisco
-- ------------------------------------------------------
-- Server version	5.7.18

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
-- Table structure for table `tb_authority`
--

DROP TABLE IF EXISTS `tb_authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_authority` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_authority`
--

LOCK TABLES `tb_authority` WRITE;
/*!40000 ALTER TABLE `tb_authority` DISABLE KEYS */;
INSERT INTO `tb_authority` VALUES (1,'ROLE_USER');
/*!40000 ALTER TABLE `tb_authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_domain`
--

DROP TABLE IF EXISTS `tb_domain`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_domain` (
  `name` varchar(255) NOT NULL,
  `ontology_uri` varchar(255) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`name`),
  KEY `FK3ieuun88tsbgchn5xfvnecjd1` (`user_id`),
  CONSTRAINT `FK3ieuun88tsbgchn5xfvnecjd1` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_domain`
--

LOCK TABLES `tb_domain` WRITE;
/*!40000 ALTER TABLE `tb_domain` DISABLE KEYS */;
INSERT INTO `tb_domain` VALUES ('Default','http://consiste.dimap.ufrn.br/ontologies/qodisco',1);
/*!40000 ALTER TABLE `tb_domain` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_operation`
--

DROP TABLE IF EXISTS `tb_operation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_operation` (
  `repository_id` int(11) NOT NULL,
  `operations` varchar(255) DEFAULT NULL,
  KEY `FKtmuayt6dst2ws226l7bixx9px` (`repository_id`),
  CONSTRAINT `FKtmuayt6dst2ws226l7bixx9px` FOREIGN KEY (`repository_id`) REFERENCES `tb_repository` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_operation`
--

LOCK TABLES `tb_operation` WRITE;
/*!40000 ALTER TABLE `tb_operation` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_operation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_repository`
--

DROP TABLE IF EXISTS `tb_repository`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_repository` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `last_check` datetime DEFAULT NULL,
  `time` bigint(20) DEFAULT NULL,
  `url` varchar(255) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKe76fxcoabtt860aao0njo02bv` (`user_id`),
  CONSTRAINT `FKe76fxcoabtt860aao0njo02bv` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_repository`
--

LOCK TABLES `tb_repository` WRITE;
/*!40000 ALTER TABLE `tb_repository` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_repository` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_repository_domains`
--

DROP TABLE IF EXISTS `tb_repository_domains`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_repository_domains` (
  `repository_entity_id` int(11) NOT NULL,
  `domains_name` varchar(255) NOT NULL,
  PRIMARY KEY (`repository_entity_id`,`domains_name`),
  KEY `FKt8w82k3xce31aq3qwtqfygxd3` (`domains_name`),
  CONSTRAINT `FKab28b3wvho1nig1f6lf8gig7x` FOREIGN KEY (`repository_entity_id`) REFERENCES `tb_repository` (`id`),
  CONSTRAINT `FKt8w82k3xce31aq3qwtqfygxd3` FOREIGN KEY (`domains_name`) REFERENCES `tb_domain` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_repository_domains`
--

LOCK TABLES `tb_repository_domains` WRITE;
/*!40000 ALTER TABLE `tb_repository_domains` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_repository_domains` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_topic`
--

DROP TABLE IF EXISTS `tb_topic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_topic` (
  `topic` varchar(255) NOT NULL,
  `broker_address` varchar(255) NOT NULL,
  `query` longtext NOT NULL,
  `domain_name` varchar(255) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`topic`),
  KEY `FK716r376n7414xhi8cgjuetbj7` (`domain_name`),
  KEY `FK8shpfvcedp3aeyulrui83pjc0` (`user_id`),
  CONSTRAINT `FK716r376n7414xhi8cgjuetbj7` FOREIGN KEY (`domain_name`) REFERENCES `tb_domain` (`name`),
  CONSTRAINT `FK8shpfvcedp3aeyulrui83pjc0` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_topic`
--

LOCK TABLES `tb_topic` WRITE;
/*!40000 ALTER TABLE `tb_topic` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_topic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_user`
--
DROP TABLE IF EXISTS `tb_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_4wv83hfajry5tdoamn8wsqa6x` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_user`
--

LOCK TABLES `tb_user` WRITE;
/*!40000 ALTER TABLE `tb_user` DISABLE KEYS */;
INSERT INTO `tb_user` VALUES (1,'admin@mail.com','Admin','$2a$10$thiJPCPBgYFdW3DAf04rD.cHkEVbODDRfAtX0hpFZH9FPT2fEGJXq','admin');
/*!40000 ALTER TABLE `tb_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_user_permissions`
--

DROP TABLE IF EXISTS `tb_user_permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_user_permissions` (
  `user_entity_id` int(11) NOT NULL,
  `permissions_id` int(11) NOT NULL,
  PRIMARY KEY (`user_entity_id`,`permissions_id`),
  KEY `FKhs5vsskahr4du4gh3xuumhxkq` (`permissions_id`),
  CONSTRAINT `FK67n78sswymhevnhpaofw9p0xn` FOREIGN KEY (`user_entity_id`) REFERENCES `tb_user` (`id`),
  CONSTRAINT `FKhs5vsskahr4du4gh3xuumhxkq` FOREIGN KEY (`permissions_id`) REFERENCES `tb_authority` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_user_permissions`
--

LOCK TABLES `tb_user_permissions` WRITE;
/*!40000 ALTER TABLE `tb_user_permissions` DISABLE KEYS */;
INSERT INTO `tb_user_permissions` VALUES (1,1);
/*!40000 ALTER TABLE `tb_user_permissions` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-07-03 18:51:26

--
-- Creating user
--
CREATE USER `qodisco`@`localhost` IDENTIFIED BY 'qodisco' ;
GRANT ALL PRIVILEGES ON qodisco.* TO `qodisco`@`localhost`;
FLUSH PRIVILEGES;
