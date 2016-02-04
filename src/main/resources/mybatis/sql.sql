repository 
CREATE TABLE `repository` (
  `repoId` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `repoName` varchar(50) NOT NULL,
  `repoUrl` varchar(100) NOT NULL,
  `isPrivate` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`repoId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1
//latin1 to be changed


user   
CREATE TABLE `user` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 

create table forks(
    -> forkId int(11) not null auto_increment,
    -> upstream varchar(50) not null,
    -> downstream varchar(50) not null,
    -> primary key (forkId)
    -> )ENGINE=InnoDB default charset=utf8;
