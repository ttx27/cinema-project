-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 16, 2021 at 04:45 AM
-- Server version: 10.4.18-MariaDB
-- PHP Version: 7.4.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cinema`
--

-- --------------------------------------------------------

--
-- Table structure for table `actor`
--

CREATE TABLE `actor` (
  `actor_id` bigint(20) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `fullname` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `actor`
--

INSERT INTO `actor` (`actor_id`, `created_date`, `modified_date`, `created_by`, `modified_by`, `fullname`) VALUES
(2, NULL, NULL, NULL, NULL, 'Trấn Thành'),
(3, NULL, NULL, NULL, NULL, 'Lewis Tan'),
(4, NULL, NULL, NULL, NULL, 'Jessica McNamee'),
(5, NULL, NULL, NULL, NULL, 'Millie Bobby Brown'),
(6, NULL, NULL, NULL, NULL, 'Alexander Skarsgård'),
(7, NULL, NULL, NULL, NULL, 'Tuấn Trần'),
(8, NULL, NULL, NULL, NULL, 'Ngân Chi');

-- --------------------------------------------------------

--
-- Table structure for table `agency`
--

CREATE TABLE `agency` (
  `agency_id` bigint(20) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `details` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `owner_user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `agency`
--

INSERT INTO `agency` (`agency_id`, `code`, `details`, `name`, `owner_user_id`) VALUES
(1, 'AGENCY-A', 'Reaching desitnations with ease', 'Green Mile Agency', 1);

-- --------------------------------------------------------

--
-- Table structure for table `bill`
--

CREATE TABLE `bill` (
  `bill_id` bigint(20) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `total` int(11) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `show_time_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `bill`
--

INSERT INTO `bill` (`bill_id`, `created_date`, `modified_date`, `created_by`, `modified_by`, `code`, `status`, `total`, `user_id`, `show_time_id`) VALUES
(1, NULL, NULL, NULL, NULL, 'B161816925162332', 1, 250000, 3, 2),
(2, NULL, NULL, NULL, NULL, 'B161817080251032', 1, 125000, 3, 2),
(5, NULL, NULL, NULL, NULL, 'B161819611137832', 1, 125000, 3, 2),
(6, NULL, NULL, NULL, NULL, 'B161819615962232', 1, 125000, 3, 2);

-- --------------------------------------------------------

--
-- Table structure for table `bill_detail`
--

CREATE TABLE `bill_detail` (
  `bill_detail_id` bigint(20) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `ticket_number` varchar(255) DEFAULT NULL,
  `bill_id` bigint(20) DEFAULT NULL,
  `seat_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `bill_detail`
--

INSERT INTO `bill_detail` (`bill_detail_id`, `created_date`, `modified_date`, `created_by`, `modified_by`, `status`, `ticket_number`, `bill_id`, `seat_id`) VALUES
(1, NULL, NULL, NULL, NULL, 1, 'B161816925170211', 1, 1),
(2, NULL, NULL, NULL, NULL, 1, 'B161816925171312', 1, 2),
(3, NULL, NULL, NULL, NULL, 1, 'B161817080254923', 2, 3),
(8, NULL, NULL, NULL, NULL, 1, 'B161819611138554', 5, 4),
(9, NULL, NULL, NULL, NULL, 1, 'B161819615963165', 6, 5);

-- --------------------------------------------------------

--
-- Table structure for table `bus`
--

CREATE TABLE `bus` (
  `bus_id` bigint(20) NOT NULL,
  `capacity` int(11) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `make` varchar(255) DEFAULT NULL,
  `agency_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `bus`
--

INSERT INTO `bus` (`bus_id`, `capacity`, `code`, `make`, `agency_id`) VALUES
(1, 60, 'AGENCY-A-1', NULL, 1);

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `category_id` bigint(20) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`category_id`, `created_date`, `modified_date`, `created_by`, `modified_by`, `code`, `name`, `status`) VALUES
(1, NULL, NULL, NULL, NULL, 'gia-dinh', 'Gia đình', 1),
(2, NULL, NULL, NULL, NULL, 'hai', 'Hài', 1),
(3, NULL, NULL, NULL, NULL, 'hanh-dong', 'Hành Động', 1),
(4, NULL, NULL, NULL, NULL, 'than-thoai', 'Thần thoại', 1),
(5, NULL, NULL, NULL, NULL, 'phieu-luu', 'Phiêu Lưu', 1);

-- --------------------------------------------------------

--
-- Table structure for table `cinema`
--

CREATE TABLE `cinema` (
  `cinema_id` bigint(20) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `city_id` int(11) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `cinema`
--

INSERT INTO `cinema` (`cinema_id`, `created_date`, `modified_date`, `created_by`, `modified_by`, `address`, `city`, `city_id`, `description`, `name`, `phone`, `status`) VALUES
(1, NULL, NULL, NULL, NULL, 'Hùng Vương Plaza', 'Hồ Chí Minh', 1, 'CGV Hùng Vương Plaza', 'CGV Hùng Vương Plaza', '0123456768', 1),
(2, NULL, NULL, NULL, NULL, 'Pandora City', 'Hồ Chí Minh', 1, 'CGV Pandora City', 'CGV Pandora City', '0123456768', 1),
(3, NULL, NULL, NULL, NULL, 'Vincom Center Bà Triệu', 'Hà Nội', 2, 'CGV Vincom Center Bà Triệu', 'CGV Vincom Center Bà Triệu', '0123456768', 1),
(4, NULL, NULL, NULL, NULL, 'Mipec Tower', 'Hà Nội', 2, 'CGV Mipec Tower', 'CGV Mipec Tower', '0123456768', 1),
(5, NULL, NULL, NULL, NULL, 'Hồ Gươm Plaza', 'Hà Nội', 2, 'CGV Hồ Gươm Plaza', 'CGV Hồ Gươm Plaza', '0123456768', 1),
(6, NULL, NULL, NULL, NULL, 'Vincom Nguyễn Chí Thanh', 'Hà Nội', 2, 'CGV Vincom Nguyễn Chí Thanh', 'CGV Vincom Nguyễn Chí Thanh', '0123456768', 1);

-- --------------------------------------------------------

--
-- Table structure for table `director`
--

CREATE TABLE `director` (
  `director_id` bigint(20) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `fullname` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `director`
--

INSERT INTO `director` (`director_id`, `created_date`, `modified_date`, `created_by`, `modified_by`, `fullname`) VALUES
(1, NULL, NULL, NULL, NULL, 'Simon McQuoid'),
(2, NULL, NULL, NULL, NULL, 'Adam Wingard'),
(3, NULL, NULL, NULL, NULL, 'Vũ Ngọc Đãng'),
(4, NULL, NULL, NULL, NULL, 'Trấn Thành');

-- --------------------------------------------------------

--
-- Table structure for table `language`
--

CREATE TABLE `language` (
  `language_id` bigint(20) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `language` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `language`
--

INSERT INTO `language` (`language_id`, `created_date`, `modified_date`, `created_by`, `modified_by`, `language`) VALUES
(1, NULL, NULL, NULL, NULL, 'Tiếng Anh'),
(2, NULL, NULL, NULL, NULL, 'Tiếng Việt');

-- --------------------------------------------------------

--
-- Table structure for table `movie`
--

CREATE TABLE `movie` (
  `movie_id` bigint(20) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `duration` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `rating` varchar(255) DEFAULT NULL,
  `release_date` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `movie`
--

INSERT INTO `movie` (`movie_id`, `created_date`, `modified_date`, `created_by`, `modified_by`, `description`, `duration`, `image`, `name`, `rating`, `release_date`) VALUES
(1, NULL, NULL, NULL, NULL, 'BỐ GIÀ 1', '128', 'https://www.cgv.vn/media/catalog/product/cache/1/image/c5f0a1eff4c394a251036189ccddaacd/1/2/1200x1800.jpg', 'BỐ GIÀ', 'C13 - PHIM CẤM KHÁN GIẢ DƯỚI 13 TUỔI', '12-03-2021'),
(2, NULL, NULL, NULL, NULL, 'GODZILLA VS. KONG', '113', 'https://www.cgv.vn/media/catalog/product/cache/1/image/c5f0a1eff4c394a251036189ccddaacd/g/o/godzilla_vs_1_.jpg', 'GODZILLA VS. KONG', 'C13 - PHIM CẤM KHÁN GIẢ DƯỚI 13 TUỔI', '26-03-2021'),
(3, NULL, NULL, NULL, NULL, 'MORTAL KOMBAT: CUỘC CHIẾN SINH TỬ', '110', 'https://www.cgv.vn/media/catalog/product/cache/1/image/c5f0a1eff4c394a251036189ccddaacd/m/o/mortal_kombat_-_vn_-_payoff_poster_1_1_.jpg', 'MORTAL KOMBAT: CUỘC CHIẾN SINH TỬ', 'C18 - PHIM CẤM KHÁN GIẢ DƯỚI 18 TUỔI', '09/04/2021');

-- --------------------------------------------------------

--
-- Table structure for table `movie_actor`
--

CREATE TABLE `movie_actor` (
  `movie_id` bigint(20) NOT NULL,
  `actor_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `movie_actor`
--

INSERT INTO `movie_actor` (`movie_id`, `actor_id`) VALUES
(2, 5),
(2, 6),
(1, 2),
(1, 7),
(1, 8),
(3, 3),
(3, 4);

-- --------------------------------------------------------

--
-- Table structure for table `movie_category`
--

CREATE TABLE `movie_category` (
  `movie_id` bigint(20) NOT NULL,
  `category_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `movie_category`
--

INSERT INTO `movie_category` (`movie_id`, `category_id`) VALUES
(2, 3),
(2, 4),
(1, 1),
(1, 2),
(3, 3),
(3, 5);

-- --------------------------------------------------------

--
-- Table structure for table `movie_director`
--

CREATE TABLE `movie_director` (
  `movie_id` bigint(20) NOT NULL,
  `director_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `movie_director`
--

INSERT INTO `movie_director` (`movie_id`, `director_id`) VALUES
(2, 2),
(1, 3),
(1, 4),
(3, 1);

-- --------------------------------------------------------

--
-- Table structure for table `movie_language`
--

CREATE TABLE `movie_language` (
  `movie_id` bigint(20) NOT NULL,
  `language_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `movie_language`
--

INSERT INTO `movie_language` (`movie_id`, `language_id`) VALUES
(2, 1),
(2, 2),
(1, 2),
(3, 1);

-- --------------------------------------------------------

--
-- Table structure for table `movie_room`
--

CREATE TABLE `movie_room` (
  `movie_room_id` bigint(20) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `movie_id` bigint(20) DEFAULT NULL,
  `room_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `movie_room`
--

INSERT INTO `movie_room` (`movie_room_id`, `created_date`, `modified_date`, `created_by`, `modified_by`, `movie_id`, `room_id`) VALUES
(1, NULL, NULL, NULL, NULL, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `movie_shift`
--

CREATE TABLE `movie_shift` (
  `movie_shift_id` bigint(20) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `time` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `movie_shift`
--

INSERT INTO `movie_shift` (`movie_shift_id`, `created_date`, `modified_date`, `created_by`, `modified_by`, `time`) VALUES
(1, NULL, NULL, NULL, NULL, 1),
(2, NULL, NULL, NULL, NULL, 2),
(3, NULL, NULL, NULL, NULL, 4);

-- --------------------------------------------------------

--
-- Table structure for table `movie_subtitle`
--

CREATE TABLE `movie_subtitle` (
  `movie_id` bigint(20) NOT NULL,
  `subtitle_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `movie_subtitle`
--

INSERT INTO `movie_subtitle` (`movie_id`, `subtitle_id`) VALUES
(2, 1),
(1, 2),
(3, 1);

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

CREATE TABLE `payment` (
  `payment_id` bigint(20) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
  `role_id` bigint(20) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`role_id`, `created_date`, `modified_date`, `created_by`, `modified_by`, `code`, `name`) VALUES
(1, NULL, NULL, NULL, NULL, 'ADMIN', 'Quản trị hệ thống'),
(2, NULL, NULL, NULL, NULL, 'USER', 'Người dùng'),
(3, NULL, NULL, NULL, NULL, 'STAFF', 'Nhân viên');

-- --------------------------------------------------------

--
-- Table structure for table `room`
--

CREATE TABLE `room` (
  `room_id` bigint(20) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `cinema_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `room`
--

INSERT INTO `room` (`room_id`, `created_date`, `modified_date`, `created_by`, `modified_by`, `description`, `name`, `status`, `cinema_id`) VALUES
(1, NULL, NULL, NULL, NULL, 'Rạp 2D', '1', 1, 1),
(2, NULL, NULL, NULL, NULL, 'Rạp 2D', '2', 1, 1),
(3, NULL, NULL, NULL, NULL, 'Rạp 2D', '3', 1, 1),
(4, NULL, NULL, NULL, NULL, 'Rạp GOLD CLASS', '4', 1, 1),
(5, NULL, NULL, NULL, NULL, 'Rạp 2D', '1', 1, 3),
(6, NULL, NULL, NULL, NULL, 'Rạp 2D', '2', 1, 3),
(7, NULL, NULL, NULL, NULL, 'Rạp 2D', '3', 1, 3),
(8, NULL, NULL, NULL, NULL, 'Rạp GOLD CLASS', '4', 1, 3),
(9, NULL, NULL, NULL, NULL, 'Rạp GOLD CLASS', '5', 1, 3);

-- --------------------------------------------------------

--
-- Table structure for table `seat`
--

CREATE TABLE `seat` (
  `seat_id` bigint(20) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `room_id` bigint(20) DEFAULT NULL,
  `seat_type_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `seat`
--

INSERT INTO `seat` (`seat_id`, `created_date`, `modified_date`, `created_by`, `modified_by`, `name`, `number`, `room_id`, `seat_type_id`) VALUES
(1, NULL, NULL, NULL, NULL, 'A', 1, 1, 1),
(2, NULL, NULL, NULL, NULL, 'A', 2, 1, 1),
(3, NULL, NULL, NULL, NULL, 'A', 3, 1, 1),
(4, NULL, NULL, NULL, NULL, 'A', 4, 1, 1),
(5, NULL, NULL, NULL, NULL, 'A', 5, 1, 1),
(6, NULL, NULL, NULL, NULL, 'B', 1, 1, 2),
(7, NULL, NULL, NULL, NULL, 'B', 2, 1, 2),
(8, NULL, NULL, NULL, NULL, 'B', 3, 1, 2);

-- --------------------------------------------------------

--
-- Table structure for table `seat_type`
--

CREATE TABLE `seat_type` (
  `seat_type_id` bigint(20) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `seat_type`
--

INSERT INTO `seat_type` (`seat_type_id`, `created_date`, `modified_date`, `created_by`, `modified_by`, `name`, `price`) VALUES
(1, NULL, NULL, NULL, NULL, 'Thường', 125000),
(2, NULL, NULL, NULL, NULL, 'VIP(Prime)', 130000);

-- --------------------------------------------------------

--
-- Table structure for table `showtime_movieshift`
--

CREATE TABLE `showtime_movieshift` (
  `show_time_id` bigint(20) NOT NULL,
  `movie_shift_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `showtime_movieshift`
--

INSERT INTO `showtime_movieshift` (`show_time_id`, `movie_shift_id`) VALUES
(2, 2),
(3, 2),
(4, 2);

-- --------------------------------------------------------

--
-- Table structure for table `show_time`
--

CREATE TABLE `show_time` (
  `show_time_id` bigint(20) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `movie_date` datetime DEFAULT NULL,
  `movie_time` varchar(255) DEFAULT NULL,
  `movie_room_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `show_time`
--

INSERT INTO `show_time` (`show_time_id`, `created_date`, `modified_date`, `created_by`, `modified_by`, `movie_date`, `movie_time`, `movie_room_id`) VALUES
(2, NULL, NULL, NULL, NULL, '2021-04-12 14:00:00', '14:00', 1),
(3, NULL, NULL, NULL, NULL, '2021-04-12 17:00:00', '17:00', 1),
(4, NULL, NULL, NULL, NULL, '2021-04-12 20:00:00', '20:00', 1);

-- --------------------------------------------------------

--
-- Table structure for table `stop`
--

CREATE TABLE `stop` (
  `stop_id` bigint(20) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `detail` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `stop`
--

INSERT INTO `stop` (`stop_id`, `code`, `detail`, `name`) VALUES
(1, 'STPA', 'Near hills', 'Stop A'),
(2, 'STPB', 'Near river', 'Stop B'),
(3, 'STPC', 'Near desert', 'Stop C'),
(4, 'STPD', 'Near lake', 'Stop D');

-- --------------------------------------------------------

--
-- Table structure for table `subtitle`
--

CREATE TABLE `subtitle` (
  `subtitle_id` bigint(20) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `subtitle` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `subtitle`
--

INSERT INTO `subtitle` (`subtitle_id`, `created_date`, `modified_date`, `created_by`, `modified_by`, `subtitle`) VALUES
(1, NULL, NULL, NULL, NULL, 'Phụ đề Tiếng Việt'),
(2, NULL, NULL, NULL, NULL, 'Phụ đề Tiếng Anh');

-- --------------------------------------------------------

--
-- Table structure for table `ticket`
--

CREATE TABLE `ticket` (
  `ticket_id` bigint(20) NOT NULL,
  `cancellable` bit(1) DEFAULT NULL,
  `journey_date` varchar(255) DEFAULT NULL,
  `seat_number` int(11) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `trip_schedule_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `trip`
--

CREATE TABLE `trip` (
  `trip_id` bigint(20) NOT NULL,
  `fare` int(11) NOT NULL,
  `journey_time` int(11) DEFAULT NULL,
  `agency_id` bigint(20) DEFAULT NULL,
  `bus_id` bigint(20) DEFAULT NULL,
  `dest_stop_id` bigint(20) DEFAULT NULL,
  `source_stop_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `trip`
--

INSERT INTO `trip` (`trip_id`, `fare`, `journey_time`, `agency_id`, `bus_id`, `dest_stop_id`, `source_stop_id`) VALUES
(1, 100, 60, 1, 1, 2, 1);

-- --------------------------------------------------------

--
-- Table structure for table `trip_schedule`
--

CREATE TABLE `trip_schedule` (
  `trip_schedule_id` bigint(20) NOT NULL,
  `available_seats` int(11) DEFAULT NULL,
  `trip_date` varchar(255) DEFAULT NULL,
  `trip_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `trip_schedule`
--

INSERT INTO `trip_schedule` (`trip_schedule_id`, `available_seats`, `trip_date`, `trip_id`) VALUES
(1, 60, '2021-04-10', 1);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` bigint(20) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `fullname` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `recovery_code` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `verify_code` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `created_date`, `modified_date`, `created_by`, `modified_by`, `email`, `fullname`, `password`, `recovery_code`, `status`, `verify_code`) VALUES
(1, NULL, NULL, NULL, NULL, 'admin@gmail.com', 'John Doe', '$2a$10$7PtcjEnWb/ZkgyXyxY1/Iei2dGgGQUbqIIll/dt.qJ8l8nQBWMbYO', NULL, 1, NULL),
(2, NULL, NULL, NULL, NULL, 'passenger@gmail.com', 'Mira Jane', '$2a$10$7PtcjEnWb/ZkgyXyxY1/Iei2dGgGQUbqIIll/dt.qJ8l8nQBWMbYO', NULL, 1, NULL),
(3, NULL, NULL, NULL, NULL, 'huynhtuanhuy1996@gmail.com', 'Huy', '$2a$10$qnv7uv9hQuWRvgdn5noMFe.X7O.Ds8CdYUcLkpLScoiVCAk5rx7Zi', NULL, 1, NULL),
(4, NULL, NULL, NULL, NULL, 'staff@gmail.com', 'Staff', '$2a$10$7PtcjEnWb/ZkgyXyxY1/Iei2dGgGQUbqIIll/dt.qJ8l8nQBWMbYO', NULL, 1, NULL),
(5, NULL, NULL, NULL, NULL, 'customer@gmail.com', 'Customer', '$2a$10$7PtcjEnWb/ZkgyXyxY1/Iei2dGgGQUbqIIll/dt.qJ8l8nQBWMbYO', NULL, 1, NULL),
(8, '2021-04-13 17:13:24', '2021-04-13 17:38:46', NULL, NULL, 'huyht.work@gmail.com', 'Huynh Tuan Huy', '$2a$10$3x1KrBNj/UYZEuZluQKL..dj9A.Y/JqMcJVifRiHHz/Z.d6IXBtGC', NULL, 1, ''),
(9, '2021-04-13 17:21:09', '2021-04-13 17:21:09', NULL, NULL, 'huynhtuanhuy19966666@gmail.com', 'Huynh Tuan Huy', '$2a$10$/DnwtoC7rTIGrEdMlL0Vs.yp8CPs3wLzrdFlgK1zcVhIzUlLXr8r2', NULL, 0, 'TXDRQZH');

-- --------------------------------------------------------

--
-- Table structure for table `user_role`
--

CREATE TABLE `user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user_role`
--

INSERT INTO `user_role` (`user_id`, `role_id`) VALUES
(1, 1),
(2, 2),
(3, 2),
(4, 3),
(5, 2),
(8, 2),
(9, 2);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `actor`
--
ALTER TABLE `actor`
  ADD PRIMARY KEY (`actor_id`);

--
-- Indexes for table `agency`
--
ALTER TABLE `agency`
  ADD PRIMARY KEY (`agency_id`),
  ADD UNIQUE KEY `idx_agency_code` (`code`),
  ADD KEY `FK9l6d70smopx8gvl8c6i7gh330` (`owner_user_id`);

--
-- Indexes for table `bill`
--
ALTER TABLE `bill`
  ADD PRIMARY KEY (`bill_id`),
  ADD UNIQUE KEY `idx_bill_code` (`code`),
  ADD KEY `FKs78seu7p3wfrnuhulmtpg707u` (`user_id`),
  ADD KEY `FK2i8kfjmcormer6rgh1nehlm6u` (`show_time_id`);

--
-- Indexes for table `bill_detail`
--
ALTER TABLE `bill_detail`
  ADD PRIMARY KEY (`bill_detail_id`),
  ADD UNIQUE KEY `idx_bill_ticket_number` (`ticket_number`),
  ADD KEY `FKeolgwyayei3o80bb7rj7t207q` (`bill_id`),
  ADD KEY `FK3o9oi4oa8ue0p0l7vjbarl1el` (`seat_id`);

--
-- Indexes for table `bus`
--
ALTER TABLE `bus`
  ADD PRIMARY KEY (`bus_id`),
  ADD UNIQUE KEY `idx_bus_code` (`code`),
  ADD KEY `FK64nil2xxuhqde813s57dp1n9t` (`agency_id`);

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`category_id`);

--
-- Indexes for table `cinema`
--
ALTER TABLE `cinema`
  ADD PRIMARY KEY (`cinema_id`);

--
-- Indexes for table `director`
--
ALTER TABLE `director`
  ADD PRIMARY KEY (`director_id`);

--
-- Indexes for table `language`
--
ALTER TABLE `language`
  ADD PRIMARY KEY (`language_id`);

--
-- Indexes for table `movie`
--
ALTER TABLE `movie`
  ADD PRIMARY KEY (`movie_id`);

--
-- Indexes for table `movie_actor`
--
ALTER TABLE `movie_actor`
  ADD KEY `FK69qnqd5hnjn2aykvxcj72r9i5` (`actor_id`),
  ADD KEY `FKhedvt8u16luotgyoel4fqy7t1` (`movie_id`);

--
-- Indexes for table `movie_category`
--
ALTER TABLE `movie_category`
  ADD KEY `FKhkem46gi7yq1019e1j8hlvp9y` (`category_id`),
  ADD KEY `FKdhlw8bp2rx2bhkp1orkg12lor` (`movie_id`);

--
-- Indexes for table `movie_director`
--
ALTER TABLE `movie_director`
  ADD KEY `FKgn1rkfh7ioiax467kc9dcrcrr` (`director_id`),
  ADD KEY `FKbay4b2v2db4yfaww2oocpld9m` (`movie_id`);

--
-- Indexes for table `movie_language`
--
ALTER TABLE `movie_language`
  ADD KEY `FKt5xiqk47u12b1iguqmx47c71f` (`movie_id`),
  ADD KEY `FKq12kjlsbj0g2ytd9if2pmu3mx` (`language_id`);

--
-- Indexes for table `movie_room`
--
ALTER TABLE `movie_room`
  ADD PRIMARY KEY (`movie_room_id`),
  ADD KEY `FKo1jdtjsqw4ox05a7e9twmr9l5` (`movie_id`),
  ADD KEY `FKpbxddlgvrgp4kst3bym2kf57p` (`room_id`);

--
-- Indexes for table `movie_shift`
--
ALTER TABLE `movie_shift`
  ADD PRIMARY KEY (`movie_shift_id`);

--
-- Indexes for table `movie_subtitle`
--
ALTER TABLE `movie_subtitle`
  ADD KEY `FK14b81g44y6hsb3b18yxkt37dn` (`movie_id`),
  ADD KEY `FKdax21irvjojvqnopmj356wds7` (`subtitle_id`);

--
-- Indexes for table `payment`
--
ALTER TABLE `payment`
  ADD PRIMARY KEY (`payment_id`),
  ADD KEY `FKmi2669nkjesvp7cd257fptl6f` (`user_id`);

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`role_id`);

--
-- Indexes for table `room`
--
ALTER TABLE `room`
  ADD PRIMARY KEY (`room_id`),
  ADD KEY `FK838jvntrkjvmbpm310wsdad1r` (`cinema_id`);

--
-- Indexes for table `seat`
--
ALTER TABLE `seat`
  ADD PRIMARY KEY (`seat_id`),
  ADD KEY `FKd7f42843rt05tt66t6vcb7s9u` (`room_id`),
  ADD KEY `FKd4mx49q62bd2apkk2rfkl8l9w` (`seat_type_id`);

--
-- Indexes for table `seat_type`
--
ALTER TABLE `seat_type`
  ADD PRIMARY KEY (`seat_type_id`);

--
-- Indexes for table `showtime_movieshift`
--
ALTER TABLE `showtime_movieshift`
  ADD KEY `FKph20mrk5w8bcbqp8k6gq07fi3` (`movie_shift_id`),
  ADD KEY `FKlxss416po4w9juwbd35dhm5t3` (`show_time_id`);

--
-- Indexes for table `show_time`
--
ALTER TABLE `show_time`
  ADD PRIMARY KEY (`show_time_id`),
  ADD KEY `FK4vboe9tlwm72cvw39cftp7a0x` (`movie_room_id`);

--
-- Indexes for table `stop`
--
ALTER TABLE `stop`
  ADD PRIMARY KEY (`stop_id`),
  ADD UNIQUE KEY `idx_stop_code` (`code`);

--
-- Indexes for table `subtitle`
--
ALTER TABLE `subtitle`
  ADD PRIMARY KEY (`subtitle_id`);

--
-- Indexes for table `ticket`
--
ALTER TABLE `ticket`
  ADD PRIMARY KEY (`ticket_id`),
  ADD KEY `FKmvugyjf7b45u0juyue7k3pct0` (`user_id`),
  ADD KEY `FKfhudhsxbslgtmbrbkd3uak4ha` (`trip_schedule_id`);

--
-- Indexes for table `trip`
--
ALTER TABLE `trip`
  ADD PRIMARY KEY (`trip_id`),
  ADD KEY `FKab03gksoo5t3lo12ga3ixnykk` (`agency_id`),
  ADD KEY `FKptvi61dd1hao1yig3in0gvcjs` (`bus_id`),
  ADD KEY `FK1evbxrekvflflkfscj2i0fwv0` (`dest_stop_id`),
  ADD KEY `FK5ln8w8n974euslk976dh6x7k5` (`source_stop_id`);

--
-- Indexes for table `trip_schedule`
--
ALTER TABLE `trip_schedule`
  ADD PRIMARY KEY (`trip_schedule_id`),
  ADD KEY `FKaqjflucdvoypakmjxtb7n2mmm` (`trip_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `idx_user_email` (`email`);

--
-- Indexes for table `user_role`
--
ALTER TABLE `user_role`
  ADD KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  ADD KEY `FKj345gk1bovqvfame88rcx7yyx` (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `actor`
--
ALTER TABLE `actor`
  MODIFY `actor_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `agency`
--
ALTER TABLE `agency`
  MODIFY `agency_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `bill`
--
ALTER TABLE `bill`
  MODIFY `bill_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `bill_detail`
--
ALTER TABLE `bill_detail`
  MODIFY `bill_detail_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `bus`
--
ALTER TABLE `bus`
  MODIFY `bus_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `category_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `cinema`
--
ALTER TABLE `cinema`
  MODIFY `cinema_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `director`
--
ALTER TABLE `director`
  MODIFY `director_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `language`
--
ALTER TABLE `language`
  MODIFY `language_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `movie`
--
ALTER TABLE `movie`
  MODIFY `movie_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `movie_room`
--
ALTER TABLE `movie_room`
  MODIFY `movie_room_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `movie_shift`
--
ALTER TABLE `movie_shift`
  MODIFY `movie_shift_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `payment`
--
ALTER TABLE `payment`
  MODIFY `payment_id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `role`
--
ALTER TABLE `role`
  MODIFY `role_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `room`
--
ALTER TABLE `room`
  MODIFY `room_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `seat`
--
ALTER TABLE `seat`
  MODIFY `seat_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `seat_type`
--
ALTER TABLE `seat_type`
  MODIFY `seat_type_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `show_time`
--
ALTER TABLE `show_time`
  MODIFY `show_time_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `stop`
--
ALTER TABLE `stop`
  MODIFY `stop_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `subtitle`
--
ALTER TABLE `subtitle`
  MODIFY `subtitle_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `ticket`
--
ALTER TABLE `ticket`
  MODIFY `ticket_id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `trip`
--
ALTER TABLE `trip`
  MODIFY `trip_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `trip_schedule`
--
ALTER TABLE `trip_schedule`
  MODIFY `trip_schedule_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `agency`
--
ALTER TABLE `agency`
  ADD CONSTRAINT `FK9l6d70smopx8gvl8c6i7gh330` FOREIGN KEY (`owner_user_id`) REFERENCES `users` (`user_id`);

--
-- Constraints for table `bill`
--
ALTER TABLE `bill`
  ADD CONSTRAINT `FK2i8kfjmcormer6rgh1nehlm6u` FOREIGN KEY (`show_time_id`) REFERENCES `show_time` (`show_time_id`),
  ADD CONSTRAINT `FKs78seu7p3wfrnuhulmtpg707u` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

--
-- Constraints for table `bill_detail`
--
ALTER TABLE `bill_detail`
  ADD CONSTRAINT `FK3o9oi4oa8ue0p0l7vjbarl1el` FOREIGN KEY (`seat_id`) REFERENCES `seat` (`seat_id`),
  ADD CONSTRAINT `FKeolgwyayei3o80bb7rj7t207q` FOREIGN KEY (`bill_id`) REFERENCES `bill` (`bill_id`);

--
-- Constraints for table `bus`
--
ALTER TABLE `bus`
  ADD CONSTRAINT `FK64nil2xxuhqde813s57dp1n9t` FOREIGN KEY (`agency_id`) REFERENCES `agency` (`agency_id`);

--
-- Constraints for table `movie_actor`
--
ALTER TABLE `movie_actor`
  ADD CONSTRAINT `FK69qnqd5hnjn2aykvxcj72r9i5` FOREIGN KEY (`actor_id`) REFERENCES `actor` (`actor_id`),
  ADD CONSTRAINT `FKhedvt8u16luotgyoel4fqy7t1` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`movie_id`);

--
-- Constraints for table `movie_category`
--
ALTER TABLE `movie_category`
  ADD CONSTRAINT `FKdhlw8bp2rx2bhkp1orkg12lor` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`movie_id`),
  ADD CONSTRAINT `FKhkem46gi7yq1019e1j8hlvp9y` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`);

--
-- Constraints for table `movie_director`
--
ALTER TABLE `movie_director`
  ADD CONSTRAINT `FKbay4b2v2db4yfaww2oocpld9m` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`movie_id`),
  ADD CONSTRAINT `FKgn1rkfh7ioiax467kc9dcrcrr` FOREIGN KEY (`director_id`) REFERENCES `director` (`director_id`);

--
-- Constraints for table `movie_language`
--
ALTER TABLE `movie_language`
  ADD CONSTRAINT `FK6po5hgd9abht63f8hlxkglpe2` FOREIGN KEY (`language_id`) REFERENCES `role` (`role_id`),
  ADD CONSTRAINT `FKq12kjlsbj0g2ytd9if2pmu3mx` FOREIGN KEY (`language_id`) REFERENCES `language` (`language_id`),
  ADD CONSTRAINT `FKt5xiqk47u12b1iguqmx47c71f` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`movie_id`);

--
-- Constraints for table `movie_room`
--
ALTER TABLE `movie_room`
  ADD CONSTRAINT `FKo1jdtjsqw4ox05a7e9twmr9l5` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`movie_id`),
  ADD CONSTRAINT `FKpbxddlgvrgp4kst3bym2kf57p` FOREIGN KEY (`room_id`) REFERENCES `room` (`room_id`);

--
-- Constraints for table `movie_subtitle`
--
ALTER TABLE `movie_subtitle`
  ADD CONSTRAINT `FK14b81g44y6hsb3b18yxkt37dn` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`movie_id`),
  ADD CONSTRAINT `FKdax21irvjojvqnopmj356wds7` FOREIGN KEY (`subtitle_id`) REFERENCES `subtitle` (`subtitle_id`),
  ADD CONSTRAINT `FKs92a763pwlsx5opmd0h2dkfeu` FOREIGN KEY (`subtitle_id`) REFERENCES `role` (`role_id`);

--
-- Constraints for table `payment`
--
ALTER TABLE `payment`
  ADD CONSTRAINT `FKmi2669nkjesvp7cd257fptl6f` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

--
-- Constraints for table `room`
--
ALTER TABLE `room`
  ADD CONSTRAINT `FK838jvntrkjvmbpm310wsdad1r` FOREIGN KEY (`cinema_id`) REFERENCES `cinema` (`cinema_id`);

--
-- Constraints for table `seat`
--
ALTER TABLE `seat`
  ADD CONSTRAINT `FKd4mx49q62bd2apkk2rfkl8l9w` FOREIGN KEY (`seat_type_id`) REFERENCES `seat_type` (`seat_type_id`),
  ADD CONSTRAINT `FKd7f42843rt05tt66t6vcb7s9u` FOREIGN KEY (`room_id`) REFERENCES `room` (`room_id`);

--
-- Constraints for table `showtime_movieshift`
--
ALTER TABLE `showtime_movieshift`
  ADD CONSTRAINT `FKlxss416po4w9juwbd35dhm5t3` FOREIGN KEY (`show_time_id`) REFERENCES `show_time` (`show_time_id`),
  ADD CONSTRAINT `FKph20mrk5w8bcbqp8k6gq07fi3` FOREIGN KEY (`movie_shift_id`) REFERENCES `movie_shift` (`movie_shift_id`);

--
-- Constraints for table `show_time`
--
ALTER TABLE `show_time`
  ADD CONSTRAINT `FK4vboe9tlwm72cvw39cftp7a0x` FOREIGN KEY (`movie_room_id`) REFERENCES `movie_room` (`movie_room_id`);

--
-- Constraints for table `ticket`
--
ALTER TABLE `ticket`
  ADD CONSTRAINT `FKfhudhsxbslgtmbrbkd3uak4ha` FOREIGN KEY (`trip_schedule_id`) REFERENCES `trip_schedule` (`trip_schedule_id`),
  ADD CONSTRAINT `FKmvugyjf7b45u0juyue7k3pct0` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

--
-- Constraints for table `trip`
--
ALTER TABLE `trip`
  ADD CONSTRAINT `FK1evbxrekvflflkfscj2i0fwv0` FOREIGN KEY (`dest_stop_id`) REFERENCES `stop` (`stop_id`),
  ADD CONSTRAINT `FK5ln8w8n974euslk976dh6x7k5` FOREIGN KEY (`source_stop_id`) REFERENCES `stop` (`stop_id`),
  ADD CONSTRAINT `FKab03gksoo5t3lo12ga3ixnykk` FOREIGN KEY (`agency_id`) REFERENCES `agency` (`agency_id`),
  ADD CONSTRAINT `FKptvi61dd1hao1yig3in0gvcjs` FOREIGN KEY (`bus_id`) REFERENCES `bus` (`bus_id`);

--
-- Constraints for table `trip_schedule`
--
ALTER TABLE `trip_schedule`
  ADD CONSTRAINT `FKaqjflucdvoypakmjxtb7n2mmm` FOREIGN KEY (`trip_id`) REFERENCES `trip` (`trip_id`);

--
-- Constraints for table `user_role`
--
ALTER TABLE `user_role`
  ADD CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`),
  ADD CONSTRAINT `FKj345gk1bovqvfame88rcx7yyx` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
