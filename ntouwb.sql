-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- 主機: 127.0.0.1
-- 產生時間： 2017-06-25 06:16:55
-- 伺服器版本: 10.1.21-MariaDB
-- PHP 版本： 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 資料庫： `ntouwb`
--

-- --------------------------------------------------------

--
-- 資料表結構 `diary`
--

CREATE TABLE `diary` (
  `diaryID` int(10) NOT NULL,
  `memberID` int(5) NOT NULL,
  `date` date NOT NULL,
  `content` varchar(1000) NOT NULL,
  `replyer` int(5) DEFAULT NULL,
  `replyDate` date DEFAULT NULL,
  `reply` varchar(1000) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 資料表的匯出資料 `diary`
--

INSERT INTO `diary` (`diaryID`, `memberID`, `date`, `content`, `replyer`, `replyDate`, `reply`) VALUES
(1, 8, '2017-06-15', '我忘記交訓練日誌了\r\n最近身體不佳練球覺得很累還很不舒服\r\n但是又覺得這樣就休息很廢\r\n所以還是練下去了還好只練到七點如果再久一點我可能就不行了\r\n\r\n進攻方面\r\n我覺得因為教練教我們很多腳步還有試探步所以單打比較順（打海洋盃的時候）\r\n投球也比較順\r\n\r\n防守\r\n我覺得只有三線補的很難\r\n比較習慣二線關門抄球這樣\r\n但是二線我又太補了\r\n要記得自己的人\r\n跳二線也不夠快要再快一點', NULL, NULL, NULL),
(2, 8, '2017-06-17', '這禮拜也許是剛考完期中考的緣故，大家的體能與精神狀況不佳，雖然很喘，希望大家還是可以繼續撐下去，希望在大家不清楚狀況時我能夠提醒大家，做好自己該做的同時也可以幫助隊友。\n自從每次重訓完都喝乳清後，不知道是不是心理緣故，覺得肌肉量有變多，原本做不起來的伏地挺身現在可以做10多下。\n星期四下午重訓幾乎每項都做到掛，導致晚上練球有些無力，可能還要再調整一下。\n希望能夠跟大一的變熟，並讓他們愛上重訓。\n\n需改進：\n*進攻\n全場運球把球往前勾\n*防守\n滑步不要交叉腳\n記得喊聲\n退防交代清楚\n\n做到改進：\n*進攻\n上籃時有把球拿起來用力砍\n抓籃有將球拿至胸前', 1, '2017-06-17', '投籃部分記得跟我約時間 可以跟寧寧一起 這樣可以一起講 今年妳們兩個的責任重大 遇到問題時要互相幫忙 希望能攜手開創屬於妳們這屆無與倫比的回憶'),
(3, 8, '2017-06-20', '覺得有些動作做起來會有點喘，但是因為每個動作做的時間不太長所以不至於到累的程度。\n進攻：\n全場運球雙手快速帶球協調性不足。\n防守：\n還是不太OK，需要再加強，進攻方從中間上去的時候，慢一點再上去不然會被跑後門，滑步有時候跟不上就會變成交叉步。\n其他：練球時多提醒隊友，多喊聲。\n\n小動作：\n1.上籃踩腳步時球拿起來\n2.抓籃板球拿至胸前\n3.換手運球後要前進不要停在原地', NULL, NULL, NULL),
(4, 8, '2017-06-21', '11234', 1, '2017-06-21', '56456');

-- --------------------------------------------------------

--
-- 資料表結構 `game`
--

CREATE TABLE `game` (
  `gameID` int(10) NOT NULL,
  `date` date NOT NULL,
  `opponent` varchar(30) NOT NULL,
  `score` varchar(10) NOT NULL,
  `result` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 資料表的匯出資料 `game`
--

INSERT INTO `game` (`gameID`, `date`, `opponent`, `score`, `result`) VALUES
(1, '2016-08-21', 'Pegasus', '14:31', '輸'),
(2, '2016-09-18', '無哈米克斯', '26:27', '輸'),
(3, '2016-11-06', 'PUDDING', '30:47', '輸');

-- --------------------------------------------------------

--
-- 資料表結構 `game_record`
--

CREATE TABLE `game_record` (
  `gameID` int(10) NOT NULL,
  `memberID` int(5) NOT NULL,
  `game_2PA` int(10) NOT NULL,
  `game_2PM` int(10) NOT NULL,
  `game_3PA` int(10) NOT NULL,
  `game_3PM` int(10) NOT NULL,
  `game_FTA` int(10) NOT NULL,
  `game_FTM` int(10) NOT NULL,
  `game_STL` int(10) NOT NULL,
  `game_RB` int(10) NOT NULL,
  `game_AST` int(10) NOT NULL,
  `game_TO` int(10) NOT NULL,
  `game_FOUL` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 資料表的匯出資料 `game_record`
--

INSERT INTO `game_record` (`gameID`, `memberID`, `game_2PA`, `game_2PM`, `game_3PA`, `game_3PM`, `game_FTA`, `game_FTM`, `game_STL`, `game_RB`, `game_AST`, `game_TO`, `game_FOUL`) VALUES
(1, 2, 2, 1, 0, 0, 2, 1, 3, 2, 0, 2, 1),
(1, 6, 2, 0, 0, 0, 2, 1, 1, 0, 0, 0, 3),
(1, 7, 1, 0, 1, 1, 0, 0, 0, 2, 0, 8, 0),
(1, 8, 5, 0, 4, 0, 0, 0, 3, 2, 1, 4, 1),
(1, 9, 2, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1),
(1, 13, 3, 0, 0, 0, 0, 0, 0, 2, 0, 5, 2),
(1, 14, 2, 1, 2, 1, 2, 2, 1, 2, 0, 3, 3),
(2, 6, 8, 3, 0, 0, 6, 3, 1, 5, 0, 1, 1),
(2, 7, 2, 1, 4, 1, 0, 0, 2, 3, 1, 5, 2),
(2, 8, 0, 0, 3, 0, 0, 0, 0, 4, 0, 2, 2),
(2, 9, 2, 0, 0, 0, 0, 0, 0, 5, 0, 1, 0),
(2, 13, 8, 2, 0, 0, 2, 2, 2, 2, 0, 4, 3),
(2, 14, 5, 2, 0, 0, 2, 2, 0, 4, 0, 4, 3),
(2, 16, 4, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1),
(3, 2, 6, 0, 1, 1, 6, 3, 2, 6, 2, 3, 2),
(3, 4, 9, 1, 8, 4, 0, 0, 3, 2, 0, 2, 1),
(3, 5, 7, 1, 3, 0, 1, 0, 3, 1, 6, 3, 0),
(3, 6, 6, 1, 1, 0, 2, 1, 1, 5, 0, 0, 5),
(3, 14, 7, 2, 1, 0, 2, 1, 1, 5, 0, 2, 1);

-- --------------------------------------------------------

--
-- 資料表結構 `member`
--

CREATE TABLE `member` (
  `memberID` int(5) NOT NULL,
  `account` varchar(10) NOT NULL,
  `password` varchar(12) NOT NULL,
  `name` varchar(15) NOT NULL,
  `height` int(5) DEFAULT NULL,
  `number` int(5) DEFAULT NULL,
  `position` varchar(10) DEFAULT NULL,
  `identity` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 資料表的匯出資料 `member`
--

INSERT INTO `member` (`memberID`, `account`, `password`, `name`, `height`, `number`, `position`, `identity`) VALUES
(1, 'NTOUWB0000', '123456', '教練', NULL, NULL, NULL, 'coach'),
(2, 'NTOUWB0001', '123456', '張婉筠', 163, 1, '大前鋒', 'leader'),
(3, 'NTOUWB0002', '123456', '黃翊寧', 163, 2, '得分後衛', 'general'),
(4, 'NTOUWB0003', '123456', '劉佳茵', 164, 3, '得分後衛', 'graduated'),
(5, 'NTOUWB0004', '123456', '林曉蓁', 157, 4, '控球後衛', 'graduated'),
(6, 'NTOUWB0005', '123456', '李佩蓉', 157, 8, '小前鋒', 'graduated'),
(7, 'NTOUWB0006', '123456', '林芷寧', 147, 10, '控球後衛', 'graduated'),
(8, 'NTOUWB0007', '123456', '陳文欣', 162, 14, '小前鋒', 'general'),
(9, 'NTOUWB0008', '123456', '林奕伶', 163, 19, '小前鋒', 'general'),
(10, 'NTOUWB0009', '123456', '蕭尹芊', 163, 22, '得分後衛', 'general'),
(11, 'NTOUWB0010', '123456', '徐筱茜', 165, 27, '大前鋒', 'general'),
(12, 'NTOUWB0011', '123456', '尹牧千', 156, 32, '控球後衛', 'general'),
(13, 'NTOUWB0012', '123456', '陳映妤', 173, 34, '中鋒', 'general'),
(14, 'NTOUWB0013', '123456', '李芃', 172, 51, '大前鋒', 'general'),
(15, 'NTOUWB0014', '123456', '李冠儀', 163, 52, '得分後衛', 'general'),
(16, 'NTOUWB0015', '123456', '陳柔妤', 162, 98, '得分後衛', 'general');

-- --------------------------------------------------------

--
-- 資料表結構 `picture`
--

CREATE TABLE `picture` (
  `pictureID` int(10) NOT NULL,
  `pictureName` varchar(50) NOT NULL,
  `date` date NOT NULL,
  `picture` longblob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- 已匯出資料表的索引
--

--
-- 資料表索引 `diary`
--
ALTER TABLE `diary`
  ADD PRIMARY KEY (`diaryID`),
  ADD KEY `memberID` (`memberID`),
  ADD KEY `replyer` (`replyer`);

--
-- 資料表索引 `game`
--
ALTER TABLE `game`
  ADD PRIMARY KEY (`gameID`);

--
-- 資料表索引 `game_record`
--
ALTER TABLE `game_record`
  ADD PRIMARY KEY (`gameID`,`memberID`),
  ADD KEY `memberID` (`memberID`);

--
-- 資料表索引 `member`
--
ALTER TABLE `member`
  ADD PRIMARY KEY (`memberID`);

--
-- 資料表索引 `picture`
--
ALTER TABLE `picture`
  ADD PRIMARY KEY (`pictureID`);

--
-- 已匯出資料表的限制(Constraint)
--

--
-- 資料表的 Constraints `diary`
--
ALTER TABLE `diary`
  ADD CONSTRAINT `diary_ibfk_1` FOREIGN KEY (`memberID`) REFERENCES `member` (`memberID`),
  ADD CONSTRAINT `diary_ibfk_2` FOREIGN KEY (`replyer`) REFERENCES `member` (`memberID`);

--
-- 資料表的 Constraints `game_record`
--
ALTER TABLE `game_record`
  ADD CONSTRAINT `game_record_ibfk_1` FOREIGN KEY (`gameID`) REFERENCES `game` (`gameID`),
  ADD CONSTRAINT `game_record_ibfk_2` FOREIGN KEY (`memberID`) REFERENCES `member` (`memberID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
