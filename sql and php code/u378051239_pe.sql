
-- phpMyAdmin SQL Dump
-- version 3.5.2.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Mar 15, 2016 at 05:23 PM
-- Server version: 10.0.20-MariaDB
-- PHP Version: 5.2.17

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `u378051239_pe`
--

-- --------------------------------------------------------

--
-- Table structure for table `floorplan`
--

CREATE TABLE IF NOT EXISTS `floorplan` (
  `propertyid` int(11) NOT NULL,
  `bhk` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `area` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `cost` varchar(50) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `images`
--

CREATE TABLE IF NOT EXISTS `images` (
  `propertyid` int(11) NOT NULL,
  `url` varchar(6000) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `images`
--

INSERT INTO `images` (`propertyid`, `url`) VALUES
(1, 'http://anamexamplecafe.esy.es/buildings/b1.jpg'),
(2, 'http://anamexamplecafe.esy.es/buildings/b2.jpg'),
(3, 'http://anamexamplecafe.esy.es/buildings/b3.jpg'),
(4, 'http://anamexamplecafe.esy.es/buildings/b4.jpg'),
(5, 'http://anamexamplecafe.esy.es/buildings/b5.jpg'),
(6, 'http://anamexamplecafe.esy.es/buildings/b6.jpg'),
(7, 'http://anamexamplecafe.esy.es/buildings/b7.jpg'),
(8, 'http://anamexamplecafe.esy.es/buildings/b8.jpg'),
(9, 'http://anamexamplecafe.esy.es/buildings/b9.jpg'),
(10, 'http://anamexamplecafe.esy.es/buildings/b10.jpg'),
(11, 'http://anamexamplecafe.esy.es/buildings/b11.jpg'),
(12, 'http://anamexamplecafe.esy.es/buildings/b12.jpg'),
(13, 'http://anamexamplecafe.esy.es/buildings/b13.jpg'),
(14, 'http://anamexamplecafe.esy.es/buildings/b14.jpg'),
(15, 'http://anamexamplecafe.esy.es/buildings/b15.jpg'),
(16, 'http://anamexamplecafe.esy.es/buildings/b16.jpg'),
(17, 'http://anamexamplecafe.esy.es/buildings/b17.jpg'),
(18, 'http://anamexamplecafe.esy.es/buildings/b18.jpg'),
(19, 'http://anamexamplecafe.esy.es/buildings/b19.jpg'),
(20, 'http://anamexamplecafe.esy.es/buildings/b20.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `property`
--

CREATE TABLE IF NOT EXISTS `property` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `builder` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `address` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `bhk` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `price` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `phone` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `possession` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `details` mediumtext COLLATE utf8_unicode_ci NOT NULL,
  `type` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `url` mediumtext COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=21 ;

--
-- Dumping data for table `property`
--

INSERT INTO `property` (`id`, `name`, `builder`, `address`, `bhk`, `price`, `phone`, `possession`, `details`, `type`, `url`) VALUES
(1, 'Epic Tower', 'K.K.Jain', '36, Chaina Town', '2/3/4', '56lac-1.2Cr', '9804945122', 'Jun 2011', 'Very nice', 'buy', 'http://anamexamplecafe.esy.es/buildings/b1.jpg'),
(2, 'Dream Land', 'Jai Constructions', 'Sector-V, James Sarani', '2/3', '25lac-98lac', '9804945113', 'Aug 2008', 'Good', 'buy', 'http://anamexamplecafe.esy.es/buildings/b2.jpg'),
(3, 'Hights', 'R.J. Builders', 'Elgin Lane, Park Street', '2/3/4', '77lac-9.3Cr', '9804945102', 'Nov 2010', 'Lovely', 'buy', 'http://anamexamplecafe.esy.es/buildings/b3.jpg'),
(4, 'Sky Tower', 'Akash Construction', 'Maniktala, Shealdah', '2/3/4', '55lac-2Cr', '9804945112', 'Sept 2011', 'Awesome', 'buy', 'http://anamexamplecafe.esy.es/buildings/b4.jpg'),
(5, 'South City', 'Ganesh Pvt. Ltd.', 'Bypass, Highland Park', '1/2/3', '20lac-98lac', '9804945123', 'Jan 2013', 'Fantastic', 'buy', 'http://anamexamplecafe.esy.es/buildings/b5.jpg'),
(6, 'Silver Tower', 'K.K.Jain', 'Rajarhat,Lake Town', '2/3', '30K-80K', '9804945122', 'Sep 2008', 'Awesome', 'rent', 'http://anamexamplecafe.esy.es/buildings/b6.jpg'),
(7, 'Shakti Apartment', 'K.K.Jain', 'Peter lane,Park Street', '1/2/3', '30k-1lac', '9804945122', 'Dec 2004', 'Fantastic', 'rent', 'http://anamexamplecafe.esy.es/buildings/b7.jpg'),
(8, 'Northern Heights', 'Jai Constructions', 'Elgin Road,Central', '1/2', '20k-30K', '9804945122', 'Nov 2010', 'Awesome', 'rent', 'http://anamexamplecafe.esy.es/buildings/b8.jpg'),
(9, 'Rainbow Tower', 'Akash Construction', '45 School Rd, Netaji', '2/3/4', '35k-90k', '8981305046', 'Aug 2008\n', 'Awesome', 'rent', 'http://anamexamplecafe.esy.es/buildings/b9.jpg'),
(10, 'Dream City', 'R.J. Builders', '48 Babupara, Harisabha', '2/3', '25k-55k', '9804945122', 'Jan 2013', 'Fantastic', 'rent', 'http://anamexamplecafe.esy.es/buildings/b10.jpg'),
(11, 'Ghar', 'Ganesh Pvt. Ltd.', 'Chandni Chowk,central', '1/2', '60lac-1Cr', '9804945122', 'Nov 2010', 'Awesome', 'resale', 'http://anamexamplecafe.esy.es/buildings/b11.jpg'),
(12, 'Northern Lights', 'Jai Constructions', 'Rajarhat,Lake Town', '1/2/3', '20lac-98lac', '8981305046', 'Sept 2011', 'Awesome', 'resale', 'http://anamexamplecafe.esy.es/buildings/b12.jpg'),
(13, 'Sudhir Apartment', 'Ganesh Pvt. Ltd.', 'Maniktala, Shealdah', '2/3/4', '55lac-2Cr', '9804945122', 'Dec 2004', 'Awesome', 'resale', 'http://anamexamplecafe.esy.es/buildings/b13.jpg'),
(14, 'Home Land', 'Ganesh Pvt. Ltd.', '45 School Rd, Netaji', '2/3/4', '55lac-2Cr', '8981305046', 'Aug 2008\n', 'Lovely', 'resale', 'http://anamexamplecafe.esy.es/buildings/b14.jpg'),
(15, 'Mars', 'R.J. Builders', 'Elgin Road,Central', '1/2/3', '20lac-98lac', '9804945122', 'Nov 2010', 'Awesome', 'resale', 'http://anamexamplecafe.esy.es/buildings/b15.jpg'),
(16, 'Epic towers', 'Jai Constructions', 'Bypass,Highland', '2/3', '55lac-2Cr', '8981305046', 'Jan 2013', 'Lovely', 'project', 'http://anamexamplecafe.esy.es/buildings/b16.jpg'),
(17, 'Western Boundaries', 'Akash Construction', 'Elgin Road,Central', '1/2', '50lac-90lac', '9804945122', 'Jan 2013', 'Fantastic', 'project', 'http://anamexamplecafe.esy.es/buildings/b17.jpg'),
(18, 'Modern Apartment', 'K.K.Jain', 'Maniktala, Shealdah', '2/3/4', '55lac-2Cr', '8981305046', 'Nov 2010', 'Awesome', 'project', 'http://anamexamplecafe.esy.es/buildings/b18.jpg'),
(19, 'Aashra', 'R.J. Builders', '63 -Chaina Town', '1/2/3', '41lac-2.2Cr', '9804945122', 'Dec 2004', 'Lovely', 'project', 'http://anamexamplecafe.esy.es/buildings/b19.jpg'),
(20, 'Panaah', 'Jai Constructions', 'Rajarhat,Lake Town', '2/3', '20lac-98lac', '8981305046', 'Sept 2011', 'Fantastic', 'project', 'http://anamexamplecafe.esy.es/buildings/b20.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `name` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `profilepic` blob,
  PRIMARY KEY (`email`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`name`, `email`, `password`, `profilepic`) VALUES
('Deepankar', 'nix.sinha@gmail.com', '123', ''),
('Anam', 'anam.saba378@gmail.com', 'buddhu', ''),
('Akash', 'akx.sonu@gmail.com', 'akash', ''),
('Nimisha Jain', 'nimishajain95@gmail.com', 'nimisha123', ''),
('ankur garg', 'Nayangrg@gmail.com', 'kptoxs', ''),
('nikhil', 'nikhlgrg@gmail.com', 'nimisha', ''),
('rahul', 'rahulmanit6@gmail.com', '123', ''),
('rahul', 'rahulmanit5@gmail.com', '12', ''),
('Eram Saba', 'eramsaba24@gmail.com', 'eram', '');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
