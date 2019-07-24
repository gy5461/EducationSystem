-- MySQL dump 10.13  Distrib 8.0.16, for Win64 (x86_64)
--
-- Host: localhost    Database: systemdb
-- ------------------------------------------------------
-- Server version	8.0.16

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `course` (
  `cno` int(11) NOT NULL,
  `cName` varchar(20) NOT NULL,
  `cTeacherId` int(11) NOT NULL,
  `cPeriod` int(11) NOT NULL,
  `cSelectProperty` char(5) NOT NULL,
  `cCredit` double NOT NULL,
  PRIMARY KEY (`cno`),
  KEY `cTeacherId` (`cTeacherId`),
  CONSTRAINT `course_ibfk_1` FOREIGN KEY (`cTeacherId`) REFERENCES `teacher` (`tno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course`
--

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` VALUES (1,'计算机网络',8001,64,'必修',3),(2,'尼泊尔',8002,48,'必修',2.5),(3,'降龙十八掌',8003,48,'选修',3),(4,'数据库',8001,80,'必修',4),(5,'红烧肉制作',8001,48,'选修',2.5),(6,'植物学',8004,60,'必修',3.5),(7,'通识安全教育',8003,25,'必修',2),(8,'糕点制作',8001,60,'必修',3);
/*!40000 ALTER TABLE `course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc`
--

DROP TABLE IF EXISTS `sc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sc` (
  `sno` int(11) NOT NULL,
  `cno` int(11) NOT NULL,
  `grade` int(11) NOT NULL,
  PRIMARY KEY (`sno`,`cno`),
  KEY `cno` (`cno`),
  CONSTRAINT `sc_ibfk_1` FOREIGN KEY (`sno`) REFERENCES `student` (`sno`),
  CONSTRAINT `sc_ibfk_2` FOREIGN KEY (`cno`) REFERENCES `course` (`cno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc`
--

LOCK TABLES `sc` WRITE;
/*!40000 ALTER TABLE `sc` DISABLE KEYS */;
INSERT INTO `sc` VALUES (2017001,1,67),(2017001,3,80),(2017001,7,95),(2017002,2,89),(2017002,3,78),(2017002,7,89),(2017003,6,92),(2017003,7,82),(2017004,5,53),(2017004,7,91),(2017004,8,88),(2017005,5,77),(2017005,7,78),(2017005,8,79);
/*!40000 ALTER TABLE `sc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `student` (
  `sno` int(11) NOT NULL,
  `sName` varchar(20) NOT NULL,
  `sSex` varchar(10) NOT NULL,
  `sNation` varchar(20) DEFAULT NULL,
  `sCountry` varchar(20) DEFAULT NULL,
  `sMajor` varchar(20) DEFAULT NULL,
  `sStartYear` int(11) DEFAULT NULL,
  PRIMARY KEY (`sno`),
  CONSTRAINT `student_ibfk_1` FOREIGN KEY (`sno`) REFERENCES `users` (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (2017001,'沐伊','女','汉族','中国','软件工程',2019),(2017002,'Tom','男','大英民族','英国','尼泊尔语',2017),(2017003,'王大锤','女','汉族','中国','农林学',2017),(2017004,'裴熙','男','回族','中国','营养学',2018),(2017005,'郝好','女','汉族','中国','营养学',2018);
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacher`
--

DROP TABLE IF EXISTS `teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `teacher` (
  `tno` int(11) NOT NULL,
  `tName` varchar(20) NOT NULL,
  PRIMARY KEY (`tno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher`
--

LOCK TABLES `teacher` WRITE;
/*!40000 ALTER TABLE `teacher` DISABLE KEYS */;
INSERT INTO `teacher` VALUES (8001,'魏婴'),(8002,' Kim '),(8003,'薛洋'),(8004,'夏东海'),(9001,'god');
/*!40000 ALTER TABLE `teacher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userkind`
--

DROP TABLE IF EXISTS `userkind`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `userkind` (
  `kindID` int(11) NOT NULL,
  `kindMessage` varchar(20) NOT NULL,
  PRIMARY KEY (`kindID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userkind`
--

LOCK TABLES `userkind` WRITE;
/*!40000 ALTER TABLE `userkind` DISABLE KEYS */;
INSERT INTO `userkind` VALUES (1,'学生'),(2,'普通老师'),(3,'教务处老师');
/*!40000 ALTER TABLE `userkind` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `users` (
  `userID` int(11) NOT NULL,
  `userKey` varchar(20) NOT NULL,
  `kindID` int(11) NOT NULL,
  PRIMARY KEY (`userID`),
  KEY `kindID` (`kindID`),
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`kindID`) REFERENCES `userkind` (`kindID`),
  CONSTRAINT `users_ibfk_2` FOREIGN KEY (`kindID`) REFERENCES `userkind` (`kindID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (8001,'123456',2),(8002,'123456',2),(8003,'123456',2),(8004,'123456',2),(9001,'123456',3),(2017001,'123456',1),(2017002,'123456',1),(2017003,'123456',1),(2017004,'123456',1),(2017005,'123456',1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-07-19 14:34:32
